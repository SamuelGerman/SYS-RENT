package com.locadora.data;

import com.locadora.domain.Venda;
import java.math.BigDecimal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaRepository {

    private Connection connection;

    public VendaRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Venda venda) throws SQLException {
        String sql = "INSERT INTO venda (idCliente, idOperador, idCarro, dataVenda, valorVenda) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, venda.getIdCliente());
            statement.setInt(2, venda.getIdOperador());
            statement.setInt(3, venda.getIdCarro());
            statement.setDate(4, Date.valueOf(venda.getDataVenda()));
            statement.setBigDecimal(5, venda.getValorVenda());
            statement.executeUpdate();
        }
    }

    public void atualizar(Venda venda) throws SQLException {
        String sql = "UPDATE venda SET idCliente = ?, idOperador = ?, idCarro = ?, dataVenda = ?, valorVenda = ? WHERE idVenda = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, venda.getIdCliente());
            statement.setInt(2, venda.getIdOperador());
            statement.setInt(3, venda.getIdCarro());
            statement.setDate(4, Date.valueOf(venda.getDataVenda()));
            statement.setBigDecimal(5, venda.getValorVenda());
            statement.setInt(6, venda.getIdVenda());
            statement.executeUpdate();
        }
    }

    public void excluir(int idVenda) throws SQLException {
        String sql = "DELETE FROM venda WHERE idVenda = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVenda);
            statement.executeUpdate();
        }
    }

    public Venda buscarPorId(int idVenda) throws SQLException {
        String sql = "SELECT * FROM venda WHERE idVenda = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVenda);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearVenda(resultSet);
                }
            }
        }
        return null;
    }

    public List<Venda> buscarTodos() throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                vendas.add(mapearVenda(resultSet));
            }
        }
        return vendas;
    }

    private Venda mapearVenda(ResultSet resultSet) throws SQLException {
        int idVenda = resultSet.getInt("idVenda");
        int idCliente = resultSet.getInt("idCliente");
        int idOperador = resultSet.getInt("idOperador");
        int idCarro = resultSet.getInt("idCarro");
        LocalDate dataVenda = resultSet.getDate("dataVenda").toLocalDate();
        BigDecimal valorVenda = resultSet.getBigDecimal("valorVenda");

        return new Venda(idVenda, idCliente, idOperador, idCarro, dataVenda, valorVenda);
    }
}
