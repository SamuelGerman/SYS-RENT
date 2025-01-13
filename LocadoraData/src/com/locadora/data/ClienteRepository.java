package com.locadora.data;

import com.locadora.domain.Cliente;
import com.locadora.domain.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private Connection connection;

    // Construtor que recebe a conexão
    public ClienteRepository(Connection connection) {
        this.connection = connection;
    }

    // Método para adicionar um cliente
    public void addCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (nome, cpf_cnpj, endereco, telefone, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os clientes
    public List<Cliente> getAllClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("cpf_cnpj"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getTimestamp("data_cadastro").toLocalDateTime()
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    // Método para buscar cliente por CPF ou CNPJ
    public Cliente getClienteByCpfCnpj(String cpfCnpj) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM Clientes WHERE cpf_cnpj = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpfCnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("cpf_cnpj"),
                            rs.getString("endereco"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getTimestamp("data_cadastro").toLocalDateTime()
                    );
                }
            }
        }
        return cliente;
    }

    // Método para atualizar os dados de um cliente
    public void updateCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE Clientes SET nome = ?, endereco = ?, telefone = ?, email = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, cliente.getIdCliente());
            stmt.executeUpdate();
        }
    }

    // Método para excluir um cliente
    public void deleteCliente(int idCliente) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE id_cliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }
    }
}
