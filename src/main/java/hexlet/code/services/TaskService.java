package hexlet.code.services;

import hexlet.code.dtos.TaskDto;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private UserService userService;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    public void createTask(TaskDto taskDto) {
        Task task = fromDto(taskDto);
        taskRepository.save(task);
    }

    public void updateTask(Long id, Task task) {

    }

    public void deleteTask(Long id) {

    }

    private Task fromDto(final TaskDto dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());

        Long taskStatusId = dto.getTaskStatusId();
        TaskStatus taskStatus = taskStatusService.findStatusById(taskStatusId);
        //if task status null then exception
        task.setTaskStatus(taskStatus);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorEmail = auth.getName();
        User author = userService.findUserByEmail(authorEmail);
        //if author null then exception
        task.setAuthor(author);

        Long executorId = dto.getExecutorId();
        if (executorId != null) {
            User executor = userService.findUserById(executorId);
            task.setExecutor(executor);
        }

        return task;
    }
}
