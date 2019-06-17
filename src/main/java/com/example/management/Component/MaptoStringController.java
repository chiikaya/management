package com.example.management.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MaptoStringController {
    @Autowired
    ObjectMapper objectMapper;
    public String MapToString(Map m){
        try {
            return objectMapper.writeValueAsString(m);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return null;
    }
    public Map<String,Object> StringToMap(String s){
        try {
            return objectMapper.readValue(s, Map.class);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return null;
    }
}
