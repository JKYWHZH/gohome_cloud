package com.jkywhzh.mail.controller;

import com.jkywhzh.entity.WorkInfo;
import com.jkywhzh.mail.services.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "邮件接口")
@RestController
public class MailController {

    @Resource
    private MailService mailService;

    @PostMapping(value = "/mail/send")
    public boolean send(Map<String, List<WorkInfo>> data){
        try {
            mailService.send(data);
            return true;
        } catch (MessagingException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
