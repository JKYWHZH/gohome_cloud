package com.jkywhzh.work.services;

import com.jkywhzh.entity.WorkInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(name = "mail-service")
public interface MailService {

    @PostMapping(value = "/mail/send")
    boolean send(Map<String, List<WorkInfo>> work) throws Exception;
}
