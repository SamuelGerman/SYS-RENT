package com.locadora.data;

import com.locadora.domain.Seguro;
import java.math.BigDecimal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SeguroRepository {

    private Connection connection;

    public SeguroRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Seguro seguro) throws SQLException {
        String sql = "INSERT INTO seguro (idLocacao, tipo, valor, dataContratacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, seguro.getIdLocacao());
            statement.setString(2, seguro.getTipo());
            statement.setBigDecimal(3, seguro.getValor());
            statement.setDate(4, Date.valueOf(seguro.getDataContratacao()));
            statement.executeUpdate();
        }
    }

    public void atualizar(Seguro seguro) throws SQLException {
        String sql = "UPDATE seguro SET idLocacao = ?, tipo = ?, valor = ?, dataContratacao = ? WHERE idSeguro = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, seguro.getIdLocacao());
            statement.setString(2, seguro.getTipo());
            statement.setBigDecimal(3, seguro.getValor());
            statement.setDate(4, Date.valueOf(seguro.getDataContratacao()));
            statement.setInt(5, seguro.getIdSeguro());
            statement.executeUpdate();
        }
    }

    public void excluir(int idSeguro) throws SQLException {
        String sql = "DELETE FROM seguro WHERE idSeguro = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idSeguro);
            statement.executeUpdate();
        }
    }

    public Seguro buscarPorId(int idSeguro) throws SQLException {
        String sql = "SELECT * FROM seguro WHERE idSeguro = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idSeguro);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearSeguro(resultSet);
                }
            }
        }
        return null;
    }

    public List<Seguro> buscarTodos() throws SQLException {
        List<Seguro> seguros = new ArrayList<>();
        String sql = "SELECT * FROM seguro";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                seguros.add(mapearSeguro(resultSet));
            }
        }
        return seguros;
    }

    private Seguro mapearSeguro(ResultSet resultSet) throws SQLException {
        int idSeguro = resultSet.getInt("idSeguro");
        int idLocacao = resultSet.getInt("idLocacao");
        String tipo = resultSet.getString("tipo");
        BigDecimal valor = resultSet.getBigDecimal("valor");
        LocalDate dataContratacao = resultSet.getDate("dataContratacao").toLocalDate();

        return new Seguro(idSeguro, idLocacao, tipo, valor, dataContratacao);
    }
}
