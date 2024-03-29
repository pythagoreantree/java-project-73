package hexlet.code.services;

import hexlet.code.dtos.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.repositories.LabelRepository;
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

    public Label createLabel(LabelDto labelDto) {
        Label label = new Label();
        label.setName(labelDto.getName());
        return labelRepository.save(label);
    }

    public Label findLabelById(long id) {
        return labelRepository.findById(id).get();
    }

    public Label updateLabel(long id, LabelDto label) {
        Label existingLabel = findLabelById(id);
        existingLabel.setName(label.getName());
        return labelRepository.save(existingLabel);
    }

    public void deleteLabel(long id) {
        labelRepository.deleteById(id);
    }
}
