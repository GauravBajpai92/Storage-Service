package com.example.storageservice.config;

import com.example.storageservice.model.EmployeeDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;


@Configuration
    public class JmsConfig {
        public static final String MY_QUEUE = "employee";

        /**
         * @return
         */
        @Bean
        public MappingJackson2MessageConverter messageConverter() {
            MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
            converter.setTargetType(MessageType.BYTES);
            converter.setTypeIdPropertyName("_type");
            return converter;
        }
    }

