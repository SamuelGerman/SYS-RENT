package com.locadora.data;

import com.locadora.domain.Vistoria;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VistoriaRepository {

    private Connection connection;

    public VistoriaRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Vistoria vistoria) throws SQLException {
        String sql = "INSERT INTO vistoria (idCarro, idLocacao, idOperador, data, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vistoria.getIdCarro());
            statement.setInt(2, vistoria.getIdLocacao());
            statement.setInt(3, vistoria.getIdOperador());
            statement.setDate(4, Date.valueOf(vistoria.getData()));
            statement.setString(5, vistoria.getEstado());
            statement.executeUpdate();
        }
    }

    public void atualizar(Vistoria vistoria) throws SQLException {
        String sql = "UPDATE vistoria SET idCarro = ?, idLocacao = ?, idOperador = ?, data = ?, estado = ? WHERE idVistoria = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vistoria.getIdCarro());
            statement.setInt(2, vistoria.getIdLocacao());
            statement.setInt(3, vistoria.getIdOperador());
            statement.setDate(4, Date.valueOf(vistoria.getData()));
            statement.setString(5, vistoria.getEstado());
            statement.setInt(6, vistoria.getIdVistoria());
            statement.executeUpdate();
        }
    }

    public void excluir(int idVistoria) throws SQLException {
        String sql = "DELETE FROM vistoria WHERE idVistoria = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVistoria);
            statement.executeUpdate();
        }
    }

    public Vistoria buscarPorId(int idVistoria) throws SQLException {
        String sql = "SELECT * FROM vistoria WHERE idVistoria = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVistoria);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearVistoria(resultSet);
                }
            }
        }
        return null;
    }

    public List<Vistoria> buscarTodos() throws SQLException {
        List<Vistoria> vistorias = new ArrayList<>();
        String sql = "SELECT * FROM vistoria";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                vistorias.add(mapearVistoria(resultSet));
            }
        }
        return vistorias;
    }

    private Vistoria mapearVistoria(ResultSet resultSet) throws SQLException {
        int idVistoria = resultSet.getInt("idVistoria");
        int idCarro = resultSet.getInt("idCarro");
        int idLocacao = resultSet.getInt("idLocacao");
        int idOperador = resultSet.getInt("idOperador");
        LocalDate data = resultSet.getDate("data").toLocalDate();
        String estado = resultSet.getString("estado");

        return new Vistoria(idVistoria, idCarro, idLocacao, idOperador, data, estado);
    }
}
