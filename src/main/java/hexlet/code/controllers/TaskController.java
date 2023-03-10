package hexlet.code.controllers;

import com.querydsl.core.types.Predicate;
import hexlet.code.dtos.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public Iterable<Task> getAll(@QuerydslPredicate(root = Task.class) Predicate predicate) {
        return taskService.findAll(predicate);
    }

    @PostMapping
    public Task create(@RequestBody @Valid final TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable final long id) {
        return taskService.findTaskById(id);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable final long id,
                       @RequestBody @Valid final TaskDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id) {
        taskService.deleteTask(id);
    }
}
