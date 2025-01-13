package com.locadora.data;
import com.locadora.domain.Locacao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocacaoRepository {

    private Connection connection;

    public LocacaoRepository(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Locacao locacao) throws SQLException {
        String sql = "INSERT INTO locacao (idCliente, idOperador, idCarro, dataInicio, dataFim, valorLocacao) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, locacao.getIdCliente());
            statement.setInt(2, locacao.getIdOperador());
            statement.setInt(3, locacao.getIdCarro());
            statement.setDate(4, Date.valueOf(locacao.getDataInicio()));
            statement.setDate(5, Date.valueOf(locacao.getDataFim()));
            statement.setBigDecimal(6, locacao.getValorLocacao());
            statement.executeUpdate();
        }
    }

    public void atualizar(Locacao locacao) throws SQLException {
        String sql = "UPDATE locacao SET idCliente = ?, idOperador = ?, idCarro = ?, dataInicio = ?, dataFim = ?, valorLocacao = ? WHERE idLocacao = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, locacao.getIdCliente());
            statement.setInt(2, locacao.getIdOperador());
            statement.setInt(3, locacao.getIdCarro());
            statement.setDate(4, Date.valueOf(locacao.getDataInicio()));
            statement.setDate(5, Date.valueOf(locacao.getDataFim()));
            statement.setBigDecimal(6, locacao.getValorLocacao());
            statement.setInt(7, locacao.getIdLocacao());
            statement.executeUpdate();
        }
    }

    public void excluir(int idLocacao) throws SQLException {
        String sql = "DELETE FROM locacao WHERE idLocacao = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idLocacao);
            statement.executeUpdate();
        }
    }

    public Locacao buscarPorId(int idLocacao) throws SQLException {
        String sql = "SELECT * FROM locacao WHERE idLocacao = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idLocacao);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearLocacao(resultSet);
                }
            }
        }
        return null;
    }

    public List<Locacao> buscarTodos() throws SQLException {
        List<Locacao> locacoes = new ArrayList<>();
        String sql = "SELECT * FROM locacao";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                locacoes.add(mapearLocacao(resultSet));
            }
        }
        return locacoes;
    }

    private Locacao mapearLocacao(ResultSet resultSet) throws SQLException {
        int idLocacao = resultSet.getInt("idLocacao");
        int idCliente = resultSet.getInt("idCliente");
        int idOperador = resultSet.getInt("idOperador");
        int idCarro = resultSet.getInt("idCarro");
        LocalDate dataInicio = resultSet.getDate("dataInicio").toLocalDate();
        LocalDate dataFim = resultSet.getDate("dataFim").toLocalDate();
        BigDecimal valorLocacao = resultSet.getBigDecimal("valorLocacao");

        return new Locacao(idLocacao, idCliente, idOperador, idCarro, dataInicio, dataFim, valorLocacao);
    }

    public List<Locacao> buscarPorCliente(int idCliente) throws SQLException {
        List<Locacao> locacoes = new ArrayList<>();
        String sql = "SELECT * FROM locacao WHERE idCliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCliente);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    locacoes.add(mapearLocacao(resultSet));
                }
            }
        }
        return locacoes;
    }

    public List<Locacao> buscarPorCarro(int idCarro) throws SQLException {
        List<Locacao> locacoes = new ArrayList<>();
        String sql = "SELECT * FROM locacao WHERE idCarro = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCarro);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    locacoes.add(mapearLocacao(resultSet));
                }
            }
        }
        return locacoes;
    }

    public BigDecimal calcularTotalValorLocacaoPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT SUM(valorLocacao) FROM locacao WHERE idCliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCliente);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                }
            }
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal calcularTotalValorLocacaoPorCarro(int idCarro) throws SQLException {
        String sql = "SELECT SUM(valorLocacao) FROM locacao WHERE idCarro = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCarro);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                }
            }
        }
        return BigDecimal.ZERO;
    }
}
