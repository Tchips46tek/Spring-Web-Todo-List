package com.example.spring_todo_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.NoSuchElementException;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository repository;

    private Date checkReqField(StringBuilder errorMessage,
                               @RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("dueTime") String dueTimeStr) {
        if (title == null || description == null || dueTimeStr == null ||
                title.length() == 0 || description.length() == 0 || dueTimeStr.length() == 0) {
            errorMessage.append("A field is empty\n");
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date dueTime;
        try {
            dueTime = format.parse(dueTimeStr);
        } catch (ParseException e) {
            errorMessage.append("Can't parse date\n");
            return null;
        }
        return dueTime;
    }

    @GetMapping("/")
    public String index(Model model,
                        StringBuilder errorMessage) {
        Iterable<Todo> todoList = repository.findAll();
        model.addAttribute("todoList", new TodoViewModel(todoList));
        model.addAttribute("newTodo", new Todo());
        if (errorMessage != null && !errorMessage.isEmpty())
            model.addAttribute("errorMessage", errorMessage.toString());
        return "index";
    }

    @PostMapping("/")
    public String addTodo(Model model,
                          @RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("dueTime") String dueTimeStr) {
        StringBuilder errorMessage = new StringBuilder();
        Date dueTime = checkReqField(errorMessage, title, description, dueTimeStr);
        if (dueTime == null)
            return index(model, errorMessage);
        repository.save(new Todo(title, description, dueTime));
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String getTodo(Model model,
                          StringBuilder errorMessage,
                          @PathVariable Long id) {
        try {
            Todo todo = repository.findById(id).orElseThrow(NoSuchElementException::new);
            model.addAttribute("todoId", id);
            model.addAttribute("todoTitle", todo.getTitle());
            model.addAttribute("todoDescription", todo.getDescription());
            model.addAttribute("todoDueTime", todo.getDueTime());
            if (errorMessage != null && !errorMessage.isEmpty())
                model.addAttribute("errorMessage", errorMessage.toString());
            return "edit";
        } catch (NoSuchElementException e) {
            return index(model, new StringBuilder("Todo don't exist\n"));
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTodo(Model model,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("dueTime") String dueTimeStr,
                             @PathVariable Long id) {
        StringBuilder errorMessage = new StringBuilder();
        Date dueTime = checkReqField(errorMessage, title, description, dueTimeStr);
        if (dueTime == null)
            return getTodo(model, errorMessage, id);
        Optional<Todo> todoOptional = repository.findById(id);
        if (todoOptional.isPresent()) {
            Todo existingTodo = todoOptional.get();
            existingTodo.setTitle(title);
            existingTodo.setDescription(description);
            existingTodo.setDueTime(dueTime);
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