package org.example.service;

import org.example.model.Label;
import org.example.repository.LabelRepository;
import java.util.List;
import java.util.Optional;

public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label create(String name) {
        Label label = new Label();
        label.setName(name);
        return labelRepository.save(label);
    }

    public Optional<Label> getById(Long id) {
        return labelRepository.findById(id);
    }

    public List<Label> getAll() {
        return labelRepository.findAll();
    }

    public Label update(Label label) {
        return labelRepository.update(label);
    }

    public boolean delete(Long id) {
        return labelRepository.deleteById(id);
    }
}
