package com.example.spring_todo_list;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date due_time;

    public Todo() {
    }
    public Todo(String title, String description, Date due_time) {
        this.title = title;
        this.description = description;
        this.due_time = due_time;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDueTime() {
        return due_time;
    }
    public void setDueTime(Date due_time) {
        this.due_time = due_time;
    }
}