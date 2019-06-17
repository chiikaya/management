package com.example.management.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor
@Getter
public class TaskUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean completed;
    @ManyToOne
    private Task task;
    @ManyToOne
    private User user;

    @Column(columnDefinition = "DATETIME NOT NULL " +
            "DEFAULT CURRENT_TIMESTAMP",updatable = false,insertable = false)
    private LocalDateTime inserttime;
    @Column(columnDefinition = "DATETIME NOT NULL " +
            "DEFAULT CURRENT_TIMESTAMP ON UPDATE "  +
            "CURRENT_TIMESTAMP",updatable = false,insertable = false)
    private LocalDateTime updatetime;

    @OneToMany
    private List<TaskRecord> taskRecords;

}
