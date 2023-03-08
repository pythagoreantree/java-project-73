package hexlet.code.controllers;

import hexlet.code.model.Label;
import hexlet.code.services.LabelService;
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
@RequestMapping("/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping
    public List<Label> getAll() {
        return labelService.findAll();
    }

    @PostMapping
    public Label create(@RequestBody @Valid final Label label) {
        return labelService.createLabel(label);
    }

    @GetMapping("/{id}")
    public Label findById(@PathVariable final long id) {
        return labelService.findLabelById(id);
    }

    @PutMapping("/{id}")
    public Label update(@PathVariable final long id,
                       @RequestBody @Valid final Label label) {
        return labelService.updateLabel(id, label);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id) {
        labelService.deleteLabel(id);
    }
}

