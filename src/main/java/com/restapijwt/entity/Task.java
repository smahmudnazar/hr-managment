package com.restapijwt.entity;

import com.restapijwt.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(columnDefinition = "text") //blob(binary)  clob(character)
    private String description;

    @ManyToOne
    private User givenUser; //zadachani beruvchi

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NEW;

    @ManyToOne
    private User takenUser; //berilgan inson

    @CreationTimestamp
    private Timestamp issue;

    private Timestamp due;

    private boolean completed = false;
}
