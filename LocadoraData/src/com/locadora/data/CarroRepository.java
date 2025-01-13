package com.locadora.data;

import com.locadora.domain.Carro;
import com.locadora.domain.Carro.StatusCarro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroRepository {

    private Connection connection;

    // Construtor que recebe a conexão
    public CarroRepository(Connection connection) {
        this.connection = connection;
    }

    // Método para adicionar um carro
    public void addCarro(Carro carro) throws SQLException {
        String sql = "INSERT INTO Carros (placa, modelo, marca, ano, quilometragem, status, preco_venda, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, carro.getPlaca());
            stmt.setString(2, carro.getModelo());
            stmt.setString(3, carro.getMarca());
            stmt.setInt(4, carro.getAno());
            stmt.setDouble(5, carro.getQuilometragem());
            stmt.setString(6, carro.getStatus().name());  // Salva o enum como string
            stmt.setBigDecimal(7, carro.getPrecoVenda());
            stmt.setTimestamp(8, carro.getDataCadastro()); // Salva o Timestamp
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os carros
    public List<Carro> getAllCarros() throws SQLException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM Carros";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getInt("id_carro"),
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getInt("ano"),
                        rs.getDouble("quilometragem"),
                        StatusCarro.valueOf(rs.getString("status")), // Converte o valor do banco para o enum
                        rs.getBigDecimal("preco_venda"),
                        rs.getTimestamp("data_cadastro")
                );
                carros.add(carro);
            }
        }
        return carros;
    }

    public Carro getCarroById(int idCarro) throws SQLException {
    Carro carro = null;
    String sql = "SELECT * FROM Carros WHERE id_carro = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, idCarro);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                carro = new Carro(
                        rs.getInt("id_carro"),
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getInt("ano"),
                        rs.getDouble("quilometragem"),
                        StatusCarro.valueOf(rs.getString("status")),
                        rs.getBigDecimal("preco_venda"),
                        rs.getTimestamp("data_cadastro")
                );
            }
        }
    }
    return carro;
}

    // Método para buscar carro por placa
    public Carro getCarroByPlaca(String placa) throws SQLException {
        Carro carro = null;
        String sql = "SELECT * FROM Carros WHERE placa = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    carro = new Carro(
                            rs.getInt("id_carro"),
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getString("marca"),
                            rs.getInt("ano"),
                            rs.getDouble("quilometragem"),
                            StatusCarro.valueOf(rs.getString("status")),
                            rs.getBigDecimal("preco_venda"),
                            rs.getTimestamp("data_cadastro")
                    );
                }
            }
        }
        return carro;
    }

    // Método para atualizar os dados de um carro
    public void updateCarro(Carro carro) throws SQLException {
        String sql = "UPDATE Carros SET modelo = ?, marca = ?, ano = ?, quilometragem = ?, status = ?, preco_venda = ?, data_cadastro = ? WHERE id_carro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, carro.getModelo());
            stmt.setString(2, carro.getMarca());
            stmt.setInt(3, carro.getAno());
            stmt.setDouble(4, carro.getQuilometragem());
            stmt.setString(5, carro.getStatus().name());  // Atualiza o enum como string
            stmt.setBigDecimal(6, carro.getPrecoVenda());
            stmt.setTimestamp(7, carro.getDataCadastro());
            stmt.setInt(8, carro.getIdCarro());
            stmt.executeUpdate();
        }
    }
    
    // Método para buscar carros por modelo
    public List<Carro> getCarrosByModelo(String modelo) throws SQLException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM Carros WHERE modelo LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + modelo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Carro carro = new Carro(
                            rs.getInt("id_carro"),
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getString("marca"),
                            rs.getInt("ano"),
                            rs.getDouble("quilometragem"),
                            StatusCarro.valueOf(rs.getString("status")),
                            rs.getBigDecimal("preco_venda"),
                            rs.getTimestamp("data_cadastro")
                    );
                    carros.add(carro);
                }
            }
        }
        return carros;
    }


    // Método para excluir um carro
    public void deleteCarro(int idCarro) throws SQLException {
        String sql = "DELETE FROM Carros WHERE id_carro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCarro);
            stmt.executeUpdate();
        }
    }
}
