package hexlet.code.services;

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
    public void createStatus(TaskStatus status) {
        taskStatusRepository.save(status);
    }

    public void updateStatus(Long id, TaskStatus status) {
        TaskStatus statusInDB = taskStatusRepository.findById(id).get();
        statusInDB.setName(status.getName());
        taskStatusRepository.save(statusInDB);
    }

    public void deleteStatus(Long id) {
        taskStatusRepository.deleteById(id);
    }
}
