package com.example.spring_todo_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Controller
public class TodoController {

    @Autowired
    private TodoRepository repository;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Todo> todoList = repository.findAll();
        model.addAttribute("todoList", new TodoViewModel(todoList));
        model.addAttribute("newTodo", new Todo());
        return "index";
    }

    @PostMapping("/")
    public String addTodo(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("dueTime") String dueTimeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date dueTime = null;
        try {
            dueTime = format.parse(dueTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        repository.save(new Todo(title, description, dueTime));
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String getTodo(Model model, @PathVariable Long id) {
        Todo todo = repository.findById(id).orElseThrow();
        model.addAttribute("todoId", id);
        model.addAttribute("todoTitle", todo.getTitle());
        model.addAttribute("todoDescription", todo.getDescription());
        model.addAttribute("todoDueTime", todo.getDueTime());
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTodo(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("dueTime") String dueTimeStr,
                             @PathVariable Long id) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date dueTime = new Date();
        try {
            dueTime = format.parse(dueTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<Todo> todoOptional = repository.findById(id);
        if (todoOptional.isPresent()) {
            Todo existingTodo = todoOptional.get();
            existingTodo.setTitle(title);
            existingTodo.setDescription(description);
            existingTodo.setDueTime(dueTime);
            System.out.println("here" + todoOptional.get().getId() + "op ex" +  existingTodo.getId());
            repository.saveAndFlush(existingTodo);
        }
        return "redirect:/edit/" + id;
    }

    @DeleteMapping("/edit/{id}")
    public String deleteTodo(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}