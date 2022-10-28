package com.jkywhzh.work.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@FeignClient(name = "discern-service")
public interface DiscernService {

    /**
     * 获取图片文字
     * @param file
     * @return
     */
    @PostMapping(value = "/discern/get")
    List<String> get(MultipartFile file);

    /**
     * 获取图片文字，处理好节假日调休
     * @param file
     * @return
     */
    @PostMapping(value = "/discern/getDaily")
    String getDaily(MultipartFile file);
}
