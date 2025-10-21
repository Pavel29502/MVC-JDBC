package org.example.Controller;

import org.example.model.Label;
import org.example.service.LabelService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LabelController {

    private final LabelService labelService;
    private final Scanner scanner = new Scanner(System.in);

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    public void createLabel() {
        System.out.print("Введите название лейбла: ");
        String name = scanner.nextLine();

        Label label = labelService.create(name);
        System.out.println("Создан Label с id: " + label.getId());
    }

    public void getLabelById() {
        System.out.print("Введите ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<Label> label = labelService.getById(id);

        label.ifPresentOrElse(
                l -> System.out.println("Label: " + l.getName()),
                () -> System.out.println("Label не найден")
        );
    }

    public void listAllLabels() {
        List<Label> labels = labelService.getAll();
        for (Label l : labels) {
            System.out.println(l.getId() + ": " + l.getName());
        }
    }

    public void updateLabel() {
        System.out.print("Введите ID для обновления: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Label> labelOpt = labelService.getById(id);
        if (labelOpt.isEmpty()) {
            System.out.println("Label не найден");
            return;
        }

        Label label = labelOpt.get();
        System.out.print("Введите новое название (" + label.getName() + "): ");
        String name = scanner.nextLine();

        if (!name.isBlank()) label.setName(name);
        labelService.update(label);
        System.out.println("Label обновлён");
    }

    public void deleteLabel() {
        System.out.print("Введите ID для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());

        if (labelService.delete(id)) {
            System.out.println("Label удалён");
        } else {
            System.out.println("Label не найден");
        }
    }
}
