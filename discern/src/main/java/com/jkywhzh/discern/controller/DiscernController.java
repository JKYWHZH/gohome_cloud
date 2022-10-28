package com.jkywhzh.discern.controller;

import com.jkywhzh.discern.services.DiscernService;
import com.jkywhzh.discern.utils.DayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j(topic = "图片识别接口")
@RestController
public class DiscernController {

    @Resource
    private DiscernService discernService;

    @PostMapping(value = "/discern/get")
    public List<String> get(MultipartFile file) {
        return discernService.getContent(file);
    }

    @PostMapping(value = "/discern/getDaily")
    public String getDaily(MultipartFile file){
        //获取图片中文本内容
        List<String> content = discernService.getContent(file);
        //区分节假日
        List<String> daytData = DayUtil.get(content);
        String ans = "";
        for (int i = 0; i < daytData.size(); i++) {
            ans = ans.concat(daytData.get(i)).concat("\n");
        }
        return ans;

    }
}
