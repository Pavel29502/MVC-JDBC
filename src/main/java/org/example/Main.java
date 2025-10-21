package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3307/crud?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Подключение к MySQL успешно!");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения:");
            e.printStackTrace();
        }
    }
}