package com.locadora.data;

import com.locadora.domain.Operador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperadorRepository {

    private Connection connection;

    public OperadorRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Operador operador) throws SQLException {
        String sql = "INSERT INTO operador (nome, cpf, telefone, email, idUsuario) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, operador.getNome());
            statement.setString(2, operador.getCpf());
            statement.setString(3, operador.getTelefone());
            statement.setString(4, operador.getEmail());
            statement.setInt(5, operador.getIdUsuario());
            statement.executeUpdate() ;
        }
    }

    public void atualizar(Operador operador) throws SQLException {
        String sql = "UPDATE operador SET nome = ?, cpf = ?, telefone = ?, email = ?, idUsuario = ? WHERE idOperador = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, operador.getNome());
            statement.setString(2, operador.getCpf());
            statement.setString(3, operador.getTelefone());
            statement.setString(4, operador.getEmail());
            statement.setInt(5, operador.getIdUsuario());
            statement.setInt(6, operador.getIdOperador());
            statement.executeUpdate();
        }
    }

    public void excluir(int idOperador) throws SQLException {
        String sql = "DELETE FROM operador WHERE idOperador = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idOperador);
            statement.executeUpdate();
        }
    }

    public Operador buscarPorId(int idOperador) throws SQLException {
        String sql = "SELECT * FROM operador WHERE idOperador = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idOperador);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearOperador(resultSet);
                }
            }
        }
        return null;
    }

    public List<Operador> buscarTodos() throws SQLException {
        List<Operador> operadores = new ArrayList<>();
        String sql = "SELECT * FROM operador";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                operadores.add(mapearOperador(resultSet));
            }
        }
        return operadores;
    }

    private Operador mapearOperador(ResultSet resultSet) throws SQLException {
        int idOperador = resultSet.getInt("idOperador");
        String nome = resultSet.getString("nome");
        String cpf = resultSet.getString("cpf");
        String telefone = resultSet.getString("telefone");
        String email = resultSet.getString("email");
        int idUsuario = resultSet.getInt("idUsuario");

        return new Operador(idOperador, nome, cpf, telefone, email, idUsuario);
    }
}
