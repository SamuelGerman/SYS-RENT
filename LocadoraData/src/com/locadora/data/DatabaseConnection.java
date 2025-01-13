/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.locadora.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaLocadora";  // Substitua com o nome do banco
    private static final String USER = "root";  // Substitua com o seu usuário
    private static final String PASSWORD = "root";  // Substitua com a sua senha

    private static Connection connection;

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }

    // Método para fechar a conexão
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

