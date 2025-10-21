package org.example.repository;

import org.example.DataBase.DBConnection;
import org.example.model.Writer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    public Writer save(Writer writer) {
        String sql = "INSERT INTO writer (first_name, last_name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preStat.setString(1, writer.getFirstName());
            preStat.setString(2, writer.getLastName());
            preStat.executeUpdate();

            try (ResultSet resultKeys = preStat.getGeneratedKeys()) {
                if (resultKeys.next()) {
                    long id = resultKeys.getLong(1);
                    writer.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public Optional<Writer> findById(Long id) {
        String sql = "SELECT * FROM writer WHERE id = ?";
        Writer writer = null;

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setLong(1, id);
            ResultSet rs = preStat.executeQuery();

            if (rs.next()) {
                writer = new Writer(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        new ArrayList<>()
                );
            }

        } catch(SQLException e) {
                e.printStackTrace();
            }
        return Optional.ofNullable(writer);
        }

        public List<Writer> findAll() {
        List<Writer> writers = new ArrayList<>();
        String sql = "SELECT * FROM writer";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement preStat = conn.prepareStatement(sql);
        ResultSet rs = preStat.executeQuery()) {
            while(rs.next()) {
                writers.add(new Writer(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        new ArrayList<>()
                ));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return writers;
        }

        public Writer update(Writer writer) {
        String sql = "UPDATE writer SET first_name = ?, last_name = ? WHERE id = ?";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement preStat = conn.prepareStatement(sql)) {
            preStat.setString(1, writer.getFirstName());
            preStat.setString(2, writer.getLastName());
            preStat.setLong(3, writer.getId());
            preStat.executeUpdate();

        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return writer;
        }

        public boolean deleteById(Long id) {
        String sql = "DELETE FROM writer Where id = ?";
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


