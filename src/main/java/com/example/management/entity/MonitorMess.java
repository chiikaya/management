package com.example.management.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MonitorMess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String course;
    @OneToMany
    private List<address> addresses;
    private State state;
    private Date date;


    //开始时间
    @Column(columnDefinition = "DATETIME NOT NULL " )
    private LocalDateTime adate;
    //结束时间
    @Column(columnDefinition = "DATETIME NOT NULL " )
    private LocalDateTime bdate;

    //以下数据不操作
    @OneToMany(mappedBy = "monitorMess",cascade = CascadeType.REMOVE)
    private List<UserMonitor> userMonitors;

}
