package hexlet.code.controllers;

import hexlet.code.model.TaskStatus;
import hexlet.code.services.TaskStatusService;
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
@RequestMapping("/api/statuses")
public class TaskStatusController {

    @Autowired
    private TaskStatusService taskStatusService;

    @GetMapping
    public List<TaskStatus> getAll() {
        return taskStatusService.findAll();
    }

    @PostMapping
    public TaskStatus create(@RequestBody @Valid final TaskStatus taskStatus) {
        return taskStatusService.createStatus(taskStatus);
    }

    @GetMapping("/{id}")
    public TaskStatus getById(@PathVariable final Long id) {
        return taskStatusService.findStatusById(id);
    }

    @PutMapping("/{id}")
    public TaskStatus update(@PathVariable final long id,
                       @RequestBody @Valid final TaskStatus taskStatus) {
        return taskStatusService.updateStatus(id, taskStatus);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id) {
        taskStatusService.deleteStatus(id);
    }
}
