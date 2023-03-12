package hexlet.code.services;

import hexlet.code.dtos.TaskStatusDto;
import hexlet.code.model.TaskStatus;
import hexlet.code.repositories.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public List<TaskStatus> findAll() {
        return taskStatusRepository.findAll();
    }

    public TaskStatus findStatusById(Long id) {
        return taskStatusRepository.findById(id).get();
    }
    public TaskStatus createStatus(TaskStatusDto taskStatusDto) {
        TaskStatus taskStatus = new TaskStatus(taskStatusDto.getName());
        return taskStatusRepository.save(taskStatus);
    }

    public TaskStatus updateStatus(Long id, TaskStatus status) {
        TaskStatus existingStatus = findStatusById(id);
        existingStatus.setName(status.getName());
        return taskStatusRepository.save(existingStatus);
    }

    public void deleteStatus(Long id) {
        taskStatusRepository.deleteById(id);
    }
}
