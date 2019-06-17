package com.example.management.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean isOpen;
    private String taskName;
    private String description;
    private LocalDateTime deadline;


    @OneToMany(mappedBy = "task",cascade = CascadeType.REMOVE)
    private List<TaskUser> taskUsers;
}
