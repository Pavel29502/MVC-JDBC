package org.example.Controller;

import org.example.model.Writer;
import org.example.service.WriterService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class WriterController {

    private final WriterService writerService;
    private final Scanner scanner = new Scanner(System.in);

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    public void createWriter() {
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();

        Writer writer = writerService.create(firstName, lastName);
        System.out.println("Создан Writer с id: " + writer.getId());
    }

    public void getWriterById() {
        System.out.print("Введите ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<Writer> writer = writerService.getById(id);

        writer.ifPresentOrElse(
                w -> System.out.println("Writer: " + w.getFirstName() + " " + w.getLastName()),
                () -> System.out.println("Writer не найден")
        );
    }

    public void listAllWriters() {
        List<Writer> writers = writerService.getAll();
        for (Writer w : writers) {
            System.out.println(w.getId() + ": " + w.getFirstName() + " " + w.getLastName());
        }
    }

    public void updateWriter() {
        System.out.print("Введите ID для обновления: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Writer> writerOpt = writerService.getById(id);
        if (writerOpt.isEmpty()) {
            System.out.println("Writer не найден");
            return;
        }

        Writer writer = writerOpt.get();
        System.out.print("Введите новое имя (" + writer.getFirstName() + "): ");
        String firstName = scanner.nextLine();
        System.out.print("Введите новую фамилию (" + writer.getLastName() + "): ");
        String lastName = scanner.nextLine();

        if (!firstName.isBlank()) writer.setFirstName(firstName);
        if (!lastName.isBlank()) writer.setLastName(lastName);

        writerService.update(writer);
        System.out.println("Writer обновлён");
    }

    public void deleteWriter() {
        System.out.print("Введите ID для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());

        if (writerService.delete(id)) {
            System.out.println("Writer удалён");
        } else {
            System.out.println("Writer не найден");
        }
    }
}
