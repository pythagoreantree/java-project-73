package hexlet.code.controllers;

import hexlet.code.dtos.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @PostMapping
    public void create(@RequestBody @Valid final TaskDto taskDto) {
        taskService.createTask(taskDto);
    }

    @GetMapping("/{id}")
    public Task findById(Long id) {
        return taskService.findTaskById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable final long id,
                       @RequestBody @Valid final TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id) {
        taskService.deleteTask(id);
    }
}
