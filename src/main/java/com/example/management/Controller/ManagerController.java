package com.example.management.Controller;

import com.example.management.Component.EncryptorComponent;
import com.example.management.Service.*;
import com.example.management.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/management")
public class ManagerController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EncryptorComponent encryptorComponent;


    @Autowired
    private managerServiceImpl userService;
    @Autowired
    private MonitorServiceImpl monitorService;
    @Autowired
    private TUServiceImpl tuService;
    @Autowired
    private UMServiceImpl umService;
    @Autowired
    private taskServiceImpl taskService;


    //管理员注册新用户
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.insert(user);
        return "redirect:/login";
    }
    //登录，成功后跳转到index
    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletResponse response) {
        Optional.ofNullable(userService.ManagerCheck(user.getUsername()).get(0))
                .or(() -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
                })
                .ifPresent(u -> {
                    if (!passwordEncoder.matches(user.getPassword(),u.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
                    }
                    log.debug("***************"+u.getId());
                    Map map = Map.of("id", u.getId());
                    // 生成加密token
                    String token = encryptorComponent.encrypt(map);
                    // 在header创建自定义的权限
                    response.setHeader("Authorization", token);
                });

        return "redirect:/ManagerIndex";
    }

    @GetMapping("/ManagerIndex/{id}")
    public Map getIndex(@PathVariable int id) {
        User user = userService.check(id);
        return Map.of("user", user);
    }

    @DeleteMapping("/manage/delete/{id}")
    public String deleteUser(@PathVariable int id){
        userService.delete(id);
        return "redirect:/manage";
    }

    @PatchMapping("/manage/update")
    public String updateUser(@RequestBody User user){
        userService.update(user, user.getId());
        return "redirect:/manage";
    }

    @GetMapping("/manage")
    public void getUsers(){

    }

    //监考信息管理
    @PostMapping("/invigilate/insert")
    public String InsertM(@RequestBody MonitorMess monitorMess){
        monitorService.insert(monitorMess);
        return "redirect:/invigilate";
    }

    @GetMapping("/invigilate")
    public Map getM(){
        return Map.of("monitorMesses",monitorService.find());
    }

    @DeleteMapping("/invigilate/delete/{id}")
    public String deleteM(@PathVariable int id){
        monitorService.delete(id);
        return "redirect:/invigilate";
    }

    @PatchMapping("/invigilate/update")
    public String updateM(@RequestBody MonitorMess monitorMess){
        monitorService.update(monitorMess, monitorMess.getId());
        return "redirect:/invigilate";
    }

    //监考关联建立，删除，查询
    @PostMapping("/MUManage/insert")
    public void insertMU(@RequestBody UserMonitor userMonitor){
        umService.insert(userMonitor);
        monitorService.updateState(userMonitor.getMonitorMess().getId());
    }

    @DeleteMapping("/MUManage/delete/{id}")
    public void deleteMU(@PathVariable int id){
        umService.delete(id);
    }

    @GetMapping("/MUManage/checkByM/{mid}")
    public Map checkMU(@PathVariable int mid){
        return Map.of("UMList", umService.check2(mid));
    }

    //task建立查询删除更改
    @GetMapping("/taskManage")
    public Map checkT(){
        return Map.of("taskList", taskService.checkAll());
    }

    @PostMapping("/taskManage/insert")
    public String insertT(@RequestBody Task task){
        taskService.insert(task);
        return "redirect;/taskManage";
    }

    @PatchMapping("taskManage/update")
    public String updateT(@RequestBody Task task){
        taskService.update(task, task.getId());
        return "redirect;/taskManage";
    }
    @DeleteMapping("taskManage/delete/{id}")
    public String deleteT(@PathVariable int id){
        taskService.delete(id);
        return "redirect;/taskManage";
    }
    //更改task状态
    @PatchMapping("taskManage/state/{id}")
    public String changeState(@PathVariable int id){
        taskService.close(id);
        return "redirect;/taskManage";
    }

    //task关联管理
    @GetMapping("/TUManage/{tid}")
    public Map checkByTid(@PathVariable int tid){
        return Map.of("tuList", tuService.findByTid(tid));
    }

    @PostMapping("/TUManage/insert/{tid}")
    public void insertTU(@PathVariable int tid,@RequestBody int uid){
        tuService.insert(tid,uid);
    }

    @DeleteMapping("TUManage/delete/{id}")
    public String deletT(@PathVariable int id){
        tuService.delete(id);
        return "redirect;/TUManage";
    }

    @GetMapping("/changeRight/{id}")
    public void change(@PathVariable Integer id){
        userService.rightManage(id);
    }

}