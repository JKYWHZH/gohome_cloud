package com.jkywhzh.discern.config;

import com.baidu.aip.ocr.AipOcr;
import com.jkywhzh.config.ORC_val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ORC_conf {

    @Bean
    public AipOcr aipOcr(){
        return new AipOcr(ORC_val.APP_ID, ORC_val.API_KEY, ORC_val.SECRET_KEY);
    }
}
