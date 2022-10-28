package com.jkywhzh.work.services;

import com.jkywhzh.entity.WorkInfo;
import com.jkywhzh.work.utils.ExeclUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j(topic = "考勤业务类")
public class WorkService {

    /**
     * 邮件接收人
     */
    @Value("${mail.receivers}")
    private String receiver;

    public Map<String, List<WorkInfo>> getWork(List<String> paths) {
        Map<String, String> receivers = getReceivers(receiver);
        List<String> collect = receivers.keySet().stream().collect(Collectors.toList());
        Map<String, WorkInfo> path = getPath(collect, paths);
        Map<String, List<WorkInfo>> workInfos = getWorkInfos(path);
        return workInfos;
    }

    /**
     * 获取地址
     *
     * @param userNames 待匹配名
     * @return 匹配名对应工作路径
     */
    private static Map<String, WorkInfo> getPath(List<String> userNames, List<String> paths) {

        Map<String, WorkInfo> ans = new ConcurrentHashMap<>(userNames.size());
        //寻找匹配人文件
        CompletableFuture[] completableFutures = IntStream.rangeClosed(0, userNames.size() - 1)
                .mapToObj(i -> userNames.get(i))
                .parallel()
                .map(userName -> CompletableFuture.runAsync(() -> {
                    for (String path : paths) {
                        if (path.contains(userName)) {
                            WorkInfo workInfo = new WorkInfo();
                            workInfo.setName(userName);
                            workInfo.setPath(path);
                            ans.put(userName, workInfo);
                            break;
                        }
                    }
                })).toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(completableFutures).join();

        return ans;
    }

    /**
     * 获取考勤情况
     *
     * @return
     */
    private static Map<String, List<WorkInfo>> getWorkInfos(Map<String, WorkInfo> workInfoMap) {
        Map<String, List<WorkInfo>> ans = new ConcurrentHashMap<>(workInfoMap.size());
        workInfoMap.forEach((key, val) -> {
            WorkInfo workInfo = workInfoMap.get(key);
            if (workInfo != null) {
                try {
                    //获取execl中考勤情况
                    List<WorkInfo> workInfos = ExeclUtil.readExcel(workInfo.getPath());
                    ans.put(key, workInfos);
                } catch (IOException | ParseException e) {
                    log.error("获取用户[{}]考勤时，报错[{}]", key, e.getCause().getMessage(), e);
                }
            }
        });
        return ans;
    }


    /**
     * 解析邮件接收人
     * @param receiver 配置
     * @return map<邮件接收人, 邮箱>
     */
    private Map<String, String> getReceivers(String receiver){
        Map<String, String> ans = new HashMap<>();
        if (receiver.contains(",")) {
            String[] split = receiver.split(",");
            for (String tmp : split) {
                String[] tmpSplit = tmp.split(":");
                ans.put(tmpSplit[0].intern().trim(), tmpSplit[1].intern().trim());
            }
        }else{
            String[] tmpSplit = receiver.split(":");
            ans.put(tmpSplit[0].intern().trim(), tmpSplit[1].intern().trim());
        }
        return ans;
    }
}
