package com.example.spring_todo_list;

import java.util.ArrayList;
import javax.validation.Valid;

public class TodoViewModel {

    @Valid
    private ArrayList<Todo> todoList = new ArrayList<Todo>();

    public TodoViewModel(Iterable<Todo> items) {
        items.forEach(todoList:: add);
    }

    public ArrayList<Todo> getTodo() {
        return todoList;
    }

    public void setTodo(ArrayList<Todo> todoList) {
        this.todoList = todoList;
    }
}