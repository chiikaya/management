package com.example.management.Component;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@Component
public class EncryptorComponent {
    @Value("${my.secretkey}")
    private String secretKey;
    @Value("${my.salt}")
    private String salt;
    @Autowired
    private ObjectMapper mapper;
    // 加密
    @Autowired
    MaptoStringController mapToString;

    public String encrypt(Map m) {
        return Encryptors.text(secretKey, salt).encrypt(mapToString.MapToString(m));
    }

    public Map decrypt(String s) {
        return mapToString.StringToMap(Encryptors.text(secretKey, salt).decrypt(s));
    }
}

