package org.example.Controller;

import org.example.model.Post;
import org.example.model.PostStatus;
import org.example.service.PostService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PostController {

    private final PostService postService;
    private final Scanner scanner = new Scanner(System.in);

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public void createPost() {
        System.out.print("Введите контент поста: ");
        String content = scanner.nextLine();

        Post post = new Post();
        post.setContent(content);
        post.setCreated(LocalDate.now());
        post.setUpdated(LocalDate.now());
        post.setStatus(PostStatus.ACTIVE);

        Post savedPost = postService.create(post);
        System.out.println("Создан Post с id: " + savedPost.getId());
    }

    public void getPostById() {
        System.out.print("Введите ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<Post> post = postService.getById(id);

        post.ifPresentOrElse(
                p -> System.out.println("Post: " + p.getContent() + " | Status: " + p.getStatus()),
                () -> System.out.println("Post не найден")
        );
    }

    public void listAllPosts() {
        List<Post> posts = postService.getAll();
        for (Post p : posts) {
            System.out.println(p.getId() + ": " + p.getContent() + " | Status: " + p.getStatus());
        }
    }

    public void updatePost() {
        System.out.print("Введите ID для обновления: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<Post> postOpt = postService.getById(id);
        if (postOpt.isEmpty()) {
            System.out.println("Post не найден");
            return;
        }

        Post post = postOpt.get();
        System.out.print("Введите новый контент (" + post.getContent() + "): ");
        String content = scanner.nextLine();

        if (!content.isBlank()) post.setContent(content);
        post.setUpdated(LocalDate.now());

        postService.update(post);
        System.out.println("Post обновлён");
    }

    public void deletePost() {
        System.out.print("Введите ID для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());

        if (postService.delete(id)) {
            System.out.println("Post удалён");
        } else {
            System.out.println("Post не найден");
        }
    }
}
