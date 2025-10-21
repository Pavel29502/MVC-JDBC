package org.example.service;

import org.example.model.Writer;
import org.example.repository.WriterRepository;
import java.util.List;
import java.util.Optional;

public class WriterService {

    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer create(String firstName, String lastName) {
        Writer writer = new Writer();
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        return writerRepository.save(writer);
    }

    public Optional<Writer> getById(Long id) {
        return writerRepository.findById(id);
    }

    public List<Writer> getAll() {
        return writerRepository.findAll();
    }

    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }

    public boolean delete(Long id) {
        return writerRepository.deleteById(id);
    }
}
