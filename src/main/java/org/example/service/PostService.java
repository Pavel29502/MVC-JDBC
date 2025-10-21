package org.example.service;

import org.example.model.Post;
import org.example.repository.PostRepository;
import java.util.List;
import java.util.Optional;

public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> getById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post update(Post post) {
        return postRepository.update(post);
    }

    public boolean delete(Long id) {
        return postRepository.deleteById(id);
    }
}
