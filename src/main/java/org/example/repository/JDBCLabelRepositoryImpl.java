package org.example.repository;

import org.example.DataBase.DBConnection;
import org.example.model.Label;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCLabelRepositoryImpl implements LabelRepository {

    public Label save(Label label) {
        String sql = "INSERT INTO label (name) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preStat.setString(1, label.getName());
            preStat.executeUpdate();

            try (ResultSet generatedKeys = preStat.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    label.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    public Optional<Label> findById(Long id) {
        String sql = "SELECT * FROM label WHERE id = ?";
        Label label = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                label = new Label(rs.getLong("id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(label);
    }


    public List<Label> findAll() {
        List<Label> labels = new ArrayList<>();
        String sql = "SELECT * FROM label";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                labels.add(new Label(rs.getLong("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    public Label update(Label label) {
        String sql = "UPDATE label SET name = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, label.getName());
            stmt.setLong(2, label.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM label WHERE id = ?";
        boolean deleted = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            deleted = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }
}
