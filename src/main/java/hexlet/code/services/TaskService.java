package hexlet.code.services;

import hexlet.code.dtos.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    public void createTask(TaskDto taskDto) {
        Task task = new Task();
        taskRepository.save(task);
    }

    public void updateTask(Long id, Task task) {

    }

    public void deleteTask(Long id) {

    }
}
