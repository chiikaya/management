package com.example.management.Controller;

import com.example.management.Component.EncryptorComponent;
import com.example.management.Service.*;
import com.example.management.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EncryptorComponent encryptorComponent;


    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RecordServiceImpl recordService;
    @Autowired
    private TUServiceImpl tuService;
    @Autowired
    private UMServiceImpl umService;


    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.insert(user);
        return "redirect:/login";
    }
    //登录，成功后跳转到index
    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletResponse response) {
        Optional.ofNullable(userService.check2(user.getUsername()).get(0))
                .or(() -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
                })
                .ifPresent(u -> {
                    if (!passwordEncoder.matches(user.getPassword(),u.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
                    }
                    Map map = Map.of("id", u.getId());
                    // 生成加密token
                    String token = encryptorComponent.encrypt(map);
                    // 在header创建自定义的权限
                    response.setHeader("Authorization", token);
                });
        return "redirect:/UserIndex";
    }

    @GetMapping("/UserIndex")
    public Map getIndex(@RequestAttribute int id) {
        User user = userService.check(id);
        return Map.of("user", user);
    }

    @PatchMapping("/userUpdate")
    public String updateUser(@RequestBody User user){
        userService.update(user, user.getId());
        return "redirect:/UserIndex";
    }
    //上面是用户的基本信息管理 查询

    //用户查询监考信息
    @PostMapping("/checkM")
    public Map checkM(@RequestBody int uid){
        List<UserMonitor> userMonitors = umService.check1(uid);
        List<MonitorMess> monitorMesses = new ArrayList<>();
        for (UserMonitor u:userMonitors){
            monitorMesses.add(u.getMonitorMess());
        }
        return Map.of("monitorMesses", monitorMesses);
    }

    //用户查询任务
    @PostMapping("/checkT")
    public Map checkT(@RequestBody int uid){
        List<Task> tasks = new ArrayList<>();
        for (TaskUser t:tuService.findByUid(uid)){
            tasks.add(t.getTask());
        }
        return Map.of("taskList", tasks);
    }

    //用户查询某任务下自己提交的所有历史记录
    @PostMapping("/checkR")
    public Map checkR(@RequestBody int uid){
        return Map.of("records",recordService.findbyTUid(uid));
    }

    //用户查询某结束任务下，公开的所有人的记录
    @PostMapping("/checkByTid")
    public Map checkByTid(@RequestBody int tid){
        List<Task> tasks = new ArrayList<>();
        for (TaskUser t:tuService.findByTid(tid)){
            tasks.add(t.getTask());
        }
        return Map.of("taskList", tasks);
    }

    //用户提交任务
    @PostMapping("/insertT")
    public void insertT(@RequestBody TaskRecord taskRecord){
        recordService.insert(taskRecord);
    }




}