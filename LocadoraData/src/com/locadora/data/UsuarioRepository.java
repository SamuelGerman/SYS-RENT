package com.locadora.data;

import com.locadora.domain.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private Connection connection;

    public UsuarioRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (login, senha, papel, dataCriacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getLogin());
            statement.setString(2, usuario.getSenha());
            statement.setString(3, usuario.getPapel());
            statement.setTimestamp(4, Timestamp.valueOf(usuario.getDataCriacao()));
            statement.executeUpdate();
        }
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET login = ?, senha = ?, papel = ?, dataCriacao = ? WHERE idUsuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getLogin());
            statement.setString(2, usuario.getSenha());
            statement.setString(3, usuario.getPapel());
            statement.setTimestamp(4, Timestamp.valueOf(usuario.getDataCriacao()));
            statement.setInt(5, usuario.getIdUsuario());
            statement.executeUpdate();
        }
    }

    public void excluir(int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            statement.executeUpdate();
        }
    }

    public Usuario buscarPorId(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearUsuario(resultSet);
                }
            }
        }
        return null;
    }

    public List<Usuario> buscarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                usuarios.add(mapearUsuario(resultSet));
            }
        }
        return usuarios;
    }

    private Usuario mapearUsuario(ResultSet resultSet) throws SQLException {
        int idUsuario = resultSet.getInt("idUsuario");
        String login = resultSet.getString("login");
        String senha = resultSet.getString("senha");
        String papel = resultSet.getString("papel");
        LocalDateTime dataCriacao = resultSet.getTimestamp("dataCriacao").toLocalDateTime();

        return new Usuario(idUsuario, login, senha, papel, dataCriacao);
    }
}
