package com.jkywhzh.discern.services;

import com.baidu.aip.ocr.AipOcr;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j(topic = "图片识别业务类")
public class DiscernService {

    //TODO 改成 @Resource 报错
    private static AipOcr aipOcr;

    private static String RESULT = "words_result";

    private static String WORDS = "words";

    public DiscernService(AipOcr aipOcr) {
        this.aipOcr = aipOcr;
    }

    /**
     * 获取图片内容
     * @param file 图片文件
     * @return 内容集合
     */
    public List<String> getContent(MultipartFile file) {
        HashMap<String,String> options = new HashMap<>(4);
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true"); // 检测图片朝上
        options.put("detect_language", "true");  // 检测语言,默认是不检查
        options.put("probability", "false");   //是否返回识别结果中每一行的置信度

        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = aipOcr.basicAccurateGeneral(bytes, options);
        JSONArray words_result = (JSONArray)jsonObject.get(RESULT);
        List<String> ans = new ArrayList<>();
        words_result.toList().forEach(object -> {
            Map map = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(object), Map.class);
            ans.add(map.get(WORDS).toString());
        });
        return  ans;
    }
}
