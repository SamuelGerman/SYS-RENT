package com.locadora.data;

import com.locadora.domain.Manutencao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManutencaoRepository {

    private Connection connection;

    public ManutencaoRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Manutencao manutencao) throws SQLException {
        String sql = "INSERT INTO manutencao (idCarro, idOperador, data, tipo, custo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, manutencao.getIdCarro());
            statement.setInt(2, manutencao.getIdOperador());
            statement.setDate(3, Date.valueOf(manutencao.getData()));
            statement.setString(4, manutencao.getTipo());
            statement.setBigDecimal(5, manutencao.getCusto());
            statement.executeUpdate();
        }
    }

    public void atualizar(Manutencao manutencao) throws SQLException {
        String sql = "UPDATE manutencao SET idCarro = ?, idOperador = ?, data = ?, tipo = ?, custo = ? WHERE idManutencao = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, manutencao.getIdCarro());
            statement.setInt(2, manutencao.getIdOperador());
            statement.setDate(3, Date.valueOf(manutencao.getData()));
            statement.setString(4, manutencao.getTipo());
            statement.setBigDecimal(5, manutencao.getCusto());
            statement.setInt(6, manutencao.getIdManutencao());
            statement.executeUpdate();
        }
    }

    public void excluir(int idManutencao) throws SQLException {
        String sql = "DELETE FROM manutencao WHERE idManutencao = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idManutencao);
            statement.executeUpdate();
        }
    }

    public Manutencao buscarPorId(int idManutencao) throws SQLException {
        String sql = "SELECT * FROM manutencao WHERE idManutencao = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idManutencao);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearManutencao(resultSet);
                }
            }
        }
        return null;
    }

    public List<Manutencao> buscarTodos() throws SQLException {
        List<Manutencao> manutencoes = new ArrayList<>();
        String sql = "SELECT * FROM manutencao";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                manutencoes.add(mapearManutencao(resultSet));
            }
        }
        return manutencoes;
    }

    public List<Manutencao> buscarPorCarro(int idCarro) throws SQLException {
        List<Manutencao> manutencoes = new ArrayList<>();
        String sql = "SELECT * FROM manutencao WHERE idCarro = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCarro);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    manutencoes.add(mapearManutencao(resultSet));
                }
            }
        }
        return manutencoes;
    }

    public List<Manutencao> buscarPorOperador(int idOperador) throws SQLException {
        List<Manutencao> manutencoes = new ArrayList<>();
        String sql = "SELECT * FROM manutencao WHERE idOperador = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idOperador);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    manutencoes.add(mapearManutencao(resultSet));
                }
            }
        }
        return manutencoes;
    }

    private Manutencao mapearManutencao(ResultSet resultSet) throws SQLException {
        int idManutencao = resultSet.getInt("idManutencao");
        int idCarro = resultSet.getInt("idCarro");
        int idOperador = resultSet.getInt("idOperador");
        LocalDate data = resultSet.getDate("data").toLocalDate();
        String tipo = resultSet.getString("tipo");
        BigDecimal custo = resultSet.getBigDecimal("custo");

        return new Manutencao(idManutencao, idCarro, idOperador, data, tipo, custo);
    }
}
