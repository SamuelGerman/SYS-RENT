package controller;

import model.LocadoraService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import model.ConexaoBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Carro;

public class LocadoraServiceImpl extends UnicastRemoteObject implements LocadoraService {

    private final List<String> veiculos;

    public LocadoraServiceImpl() throws RemoteException {
        super();
        // Inicializando veículos (exemplo)
        veiculos = new ArrayList<>();
        veiculos.add("Carro 1 - Disponível");
        veiculos.add("Carro 2 - Disponível");
        veiculos.add("Carro 3 - Reservado");
    }

    @Override
    public List<Carro> listarVeiculos() throws RemoteException {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT * FROM Carros";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getInt("id_carro"),
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getInt("ano"),
                        rs.getDouble("quilometragem"),
                        rs.getString("status"),
                        rs.getDouble("preco_venda"),
                        rs.getString("data_cadastro")
                );
                carros.add(carro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Erro ao listar veículos.", e);
        }
        return carros;
    }

    @Override
    public boolean reservarVeiculo(int id) throws RemoteException {
        String sql = "UPDATE Carros SET status = 'locado' WHERE id_carro = ? AND status = 'disponível'";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0; // Retorna true se o veículo foi reservado
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Erro ao reservar veículo.", e);
        }
    }

    @Override
    public boolean devolverVeiculo(int id) throws RemoteException {
        String sql = "UPDATE Carros SET status = 'disponível' WHERE id_carro = ? AND status = 'locado'";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0; // Retorna true se o veículo foi devolvido
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Erro ao devolver veículo.", e);
        }
    }

    @Override
    public List<Carro> listarCarrosVenda() throws RemoteException {
        List<Carro> carrosVenda = new ArrayList<>();
        String sql = "SELECT * FROM Carros WHERE status = 'venda'";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Carro carro = new Carro(
                        rs.getInt("id_carro"),
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getInt("ano"),
                        rs.getDouble("quilometragem"),
                        rs.getString("status"),
                        rs.getDouble("preco_venda"),
                        rs.getString("data_cadastro")
                );
                carrosVenda.add(carro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Erro ao listar veículos à venda.", e);
        }
        return carrosVenda;
    }

    @Override
    public boolean venderVeiculo(int id) throws RemoteException {
        String sql = "UPDATE Carros SET status = 'vendido' WHERE id_carro = ? AND status = 'venda'";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0; // Retorna true se o veículo foi vendido
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Erro ao vender veículo.", e);
        }
    }

}
