package org.example.repository;
import org.example.DataBase.DBConnection;
import org.example.model.Post;
import org.example.model.PostStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcPostRepositoryImpl implements PostRepository {

    public Post save(Post post) {
        String sql = "INSERT INTO post (content, created, updated, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, post.getContent());
            stmt.setDate(2, Date.valueOf(post.getCreated()));
            stmt.setDate(3, Date.valueOf(post.getUpdated()));
            stmt.setString(4, post.getStatus().name());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        Post post = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                post = new Post(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getDate("created").toLocalDate(),
                        rs.getDate("updated").toLocalDate(),
                        new ArrayList<>(), // пока без labels
                        PostStatus.valueOf(rs.getString("status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(post);
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM post";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                posts.add(new Post(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getDate("created").toLocalDate(),
                        rs.getDate("updated").toLocalDate(),
                        new ArrayList<>(),
                        PostStatus.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post update(Post post) {
        String sql = "UPDATE post SET content = ?, updated = ?, status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setString(1, post.getContent());
            preStat.setDate(2, Date.valueOf(post.getUpdated()));
            preStat.setString(3, post.getStatus().name());
            preStat.setLong(4, post.getId());
            preStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setLong(1, id);
            deleted = preStat.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

}