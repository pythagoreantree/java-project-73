package hexlet.code.services;

import hexlet.code.model.Label;
import hexlet.code.repositories.LabelRepository;
import hexlet.code.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    public Label createLabel(Label label) {
        return labelRepository.save(label);
    }

    public Label findLabelById(long id) {
        return labelRepository.findById(id).get();
    }

    public Label updateLabel(long id, Label label) {
        Label existingLabel = findLabelById(id);
        existingLabel.setName(label.getName());
        return labelRepository.save(existingLabel);
    }

    public void deleteLabel(long id) {
        labelRepository.deleteById(id);
    }
}