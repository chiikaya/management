package com.example.management.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    //职称
    private Authority UserAuthority;
    private String phone;
    @Max(50)
    private String BasicInformation;


    //以下属性不被操作
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<UserMonitor> userMonitors;
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<TaskUser> taskUsers;

    @Column(columnDefinition = "DATETIME NOT NULL " +
            "DEFAULT CURRENT_TIMESTAMP ON UPDATE " +
            "CURRENT_TIMESTAMP",updatable = false,insertable = false)
    private LocalDateTime updateTime;
}
