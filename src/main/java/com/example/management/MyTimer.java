package com.example.management;

import com.example.management.Repository.MonitorRepository;
import com.example.management.Repository.UserMonitorRepository;
import com.example.management.entity.MonitorMess;
import com.example.management.entity.State;
import com.example.management.entity.UserMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MyTimer{
    private UserMonitorRepository umr;
    private MonitorRepository mr;
    @Scheduled(cron = "0 0 20 * * *")
    public void sedMessage(){
        //拿到当前的日期
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date=calendar.getTime();
        log.debug("日期+1天后结果："+date.toString());


        //隐式查询拿到第一个用户的phone,封装短信信息
        Map<String,String> messages=new HashMap<>();
        List<MonitorMess> monitorMesses = mr.findByDate(date).stream()
                .filter(monitorMess -> monitorMess.getState()== State.distributed).collect(Collectors.toList());
        for (MonitorMess m:monitorMesses){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("明天的监考信息如下：监考开始时间：").append(m.getAdate()).append("，结束时间：").append(m.getBdate())
                    .append("。监考人员有：");

            for(UserMonitor u:m.getUserMonitors()){
                stringBuilder.append(u.getUser().getUsername()).append(" ");
            }

            stringBuilder.append("。请按时参加监考。");
            messages.put(m.getUserMonitors().get(0).getUser().getPhone(),stringBuilder.toString());
        }
    }
}
