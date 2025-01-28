package controller;

import java.sql.Statement;
import model.LocadoraService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import model.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import model.Carro;
import model.Usuario;

public class LocadoraServiceImpl extends UnicastRemoteObject implements LocadoraService {

    private final List<String> veiculos = null;
    private final Map<String, Usuario> usuarios = new HashMap<>();

    public LocadoraServiceImpl() throws RemoteException {
        super();
        carregarUsuariosDoBanco();
    }

    private void carregarUsuariosDoBanco() {
        String query = "SELECT login, senha, papel FROM Usuarios";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                String papel = rs.getString("papel");

                usuarios.put(login, new Usuario(id,login, senha, papel)); // Adiciona ao HashMap
                System.out.println("Usuário carregado: " + login + ", Papel: " + papel);
            }
            System.out.println("Usuários carregados com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao carregar usuários do banco: " + e.getMessage());
        }
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
                        rs.getDate("data_cadastro")
                );
                carros.add(carro);
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao listar veículos.", e);
        }
        return carros;
    }

    @Override
    public List<Carro> listarVeiculosDisponiveisParaLocacao() throws RemoteException {
        List<Carro> carrosDisponiveis = new ArrayList<>();
        String sql = "SELECT * FROM Carros WHERE status = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "disponível"); // Filtra apenas veículos disponíveis

            try (ResultSet rs = stmt.executeQuery()) {
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
                            rs.getDate("data_cadastro")
                    );
                    carrosDisponiveis.add(carro);
                }
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao listar veículos disponíveis para locação.", e);
        }
        return carrosDisponiveis;
    }

    @Override
    public boolean reservarVeiculo(int id) throws RemoteException {
        String sql = "UPDATE Carros SET status = 'locado' WHERE id_carro = ? AND status = 'disponível'";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0; // Retorna true se o veículo foi reservado
        } catch (SQLException e) {
            throw new RemoteException("Erro ao reservar veículo.", e);
        }
    }

    @Override
    public boolean registrarLocacao(int idCliente, int idOperador, int idCarro, String dataInicio, String dataFim, double valorLocacao) throws RemoteException {
        String sql = "INSERT INTO Locacoes (id_cliente, id_operador, id_carro, data_inicio, data_fim, valor_locacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idOperador);
            stmt.setInt(3, idCarro);
            stmt.setString(4, dataInicio);
            stmt.setString(5, dataFim);
            stmt.setDouble(6, valorLocacao);

            return stmt.executeUpdate() > 0; // Retorna true se a locação foi registrada com sucesso
        } catch (SQLException e) {
            throw new RemoteException("Erro ao registrar locação.", e);
        }
    }

    @Override
    public boolean devolverVeiculo(int id) throws RemoteException {
        String sql = "UPDATE Carros SET status = 'vistoria' WHERE id_carro = ? AND status = 'locado'";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0; // Retorna true se o veículo foi devolvido
        } catch (SQLException e) {
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
                        rs.getDate("data_cadastro")
                );
                carrosVenda.add(carro);
            }
        } catch (SQLException e) {
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
            throw new RemoteException("Erro ao vender veículo.", e);
        }
    }

    @Override
    public boolean registrarVenda(int idCliente, int idOperador, int idCarro, double valorVenda) throws RemoteException {
        String sql = "INSERT INTO Vendas (id_cliente, id_operador, id_carro, data_venda, valor_venda) VALUES (?, ?, ?, NOW(), ?)";
        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idOperador);
            stmt.setInt(3, idCarro);
            stmt.setDouble(4, valorVenda);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            throw new RemoteException("Erro ao registrar venda.", e);
        }
    }

    @Override
    public List<Carro> listarVeiculosPendentesVistoria() throws RemoteException {
        List<Carro> carrosPendentes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Carros WHERE necessita_vistoria = TRUE";

            Connection conn = ConexaoBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Consulta para buscar carros que precisam de vistoria
            ResultSet rs = stmt.executeQuery();

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
                        rs.getTimestamp("data_cadastro")
                );
                carrosPendentes.add(carro);
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao listar veículos pendentes de vistoria.", e);
        }
        return carrosPendentes;
    }

    @Override
    public boolean registrarVistoria(int idCarro, int idLocacao, int idOperador, String estado) throws RemoteException {
        try {
            Connection connection = ConexaoBD.getConnection();

            // Inserir registro da vistoria na tabela Vistorias
            String sql = "INSERT INTO Vistorias (id_carro, id_locacao, id_operador, data, estado) VALUES (?, ?, ?, CURDATE(), ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idCarro);
            stmt.setInt(2, idLocacao);
            stmt.setInt(3, idOperador);
            stmt.setString(4, estado);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Atualizar o carro dependendo do estado da vistoria
                if (estado.equalsIgnoreCase("manutenção")) {
                    // Caso a vistoria identifique que o carro precisa de manutenção, não liberar para 'disponível'
                    String updateSql = "UPDATE Carros SET necessita_vistoria = TRUE WHERE id_carro = ? AND status = 'vistoria'";
                    PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                    updateStmt.setInt(1, idCarro);
                    updateStmt.executeUpdate();
                } else {
                    // Caso a vistoria não identifique necessidade de manutenção, liberar o carro
                    String updateSql1 = "UPDATE Carros SET status = 'disponível' WHERE id_carro = ? AND status = 'vistoria'";
                    PreparedStatement updateStmt1 = connection.prepareStatement(updateSql1);
                    updateStmt1.setInt(1, idCarro);
                    updateStmt1.executeUpdate();

                    // Atualizar o campo 'necessita_vistoria' para FALSE, já que a vistoria foi bem-sucedida
                    String updateSql2 = "UPDATE Carros SET necessita_vistoria = FALSE WHERE id_carro = ? AND status = 'disponível'";
                    PreparedStatement updateStmt2 = connection.prepareStatement(updateSql2);
                    updateStmt2.setInt(1, idCarro);
                    updateStmt2.executeUpdate();
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao registrar vistoria.", e);
        }
        return false;
    }

    @Override
    public List<Carro> listarVeiculosComManutencaoPendente() throws RemoteException {
        List<Carro> veiculos = new ArrayList<>();

        String sql = "SELECT * FROM Carros WHERE status = 'manutencao'";

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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
                        rs.getDate("data_cadastro")
                );
                veiculos.add(carro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veiculos;
    }

    // Implementação do método registrarManutencao
    @Override
    public boolean registrarManutencao(int idCarro, int idOperador, String tipo, double custo) throws RemoteException {
        try (Connection connection = ConexaoBD.getConnection()) {
            // Registrar a manutenção
            String sql = "INSERT INTO Manutencoes (id_carro, id_operador, data, tipo, custo) VALUES (?, ?, CURDATE(), ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idCarro);
            stmt.setInt(2, idOperador);
            stmt.setString(3, tipo);
            stmt.setDouble(4, custo);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Atualizar o status do carro para 'disponível' ou 'venda'
                String updateSql = "UPDATE Carros SET status = ? WHERE id_carro = ? AND status = 'manutencao'";
                PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setString(1, tipo);
                updateStmt.setInt(2, idCarro);
                updateStmt.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cadastrarOperador(String nome, String cpf, String telefone, String email, int idUsuario) throws RemoteException {
        try (Connection connection = ConexaoBD.getConnection()) {
            String sql = "INSERT INTO Operadores (nome, cpf, telefone, email, id_usuario) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            stmt.setInt(5, idUsuario);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se o operador foi cadastrado com sucesso
        } catch (SQLException e) {
            throw new RemoteException("Erro ao cadastrar operador.", e);
        }
    }

    @Override
    public int cadastrarUsuario(String login, String senha, String papel) throws RemoteException {
        try (Connection connection = ConexaoBD.getConnection()) {
            String sql = "INSERT INTO Usuarios (login, senha, papel) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, login);
            stmt.setString(2, senha); // Idealmente, hash da senha
            stmt.setString(3, papel);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID do usuário recém-criado
                }
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao cadastrar usuário.", e);
        }
        return -1; // Retorna -1 em caso de falha
    }

    @Override
    public boolean cadastrarCliente(String nome, String cpfCnpj, String endereco, String telefone, String email, int idUsuario) throws RemoteException {
        try {
            Connection connection = ConexaoBD.getConnection();

            // Valida se o idUsuario existe na tabela Usuarios
            String validaUsuarioSql = "SELECT COUNT(*) FROM Usuarios WHERE id_usuario = ?";
            PreparedStatement validaStmt = connection.prepareStatement(validaUsuarioSql);
            validaStmt.setInt(1, idUsuario);
            ResultSet resultSet = validaStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                throw new RemoteException("ID de usuário não encontrado. O cliente não pode ser cadastrado.");
            }

            // Insere os dados do cliente na tabela Clientes
            String sql = "INSERT INTO Clientes (nome, cpf_cnpj, endereco, telefone, email, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cpfCnpj);
            stmt.setString(3, endereco);
            stmt.setString(4, telefone);
            stmt.setString(5, email);
            stmt.setInt(6, idUsuario);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente cadastrado com sucesso.");
                return true;
            }
        } catch (SQLException e) {
            throw new RemoteException("Erro ao cadastrar cliente.", e);
        }
        return false;
    }

    @Override
    public boolean autenticarUsuario(String login, String senha) throws RemoteException {
        Usuario usuario = usuarios.get(login); // Busca o usuário pelo login
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return true; // Credenciais válidas
        }
        return false; // Login não encontrado ou senha inválida
    }

    @Override
    public String obterPapelUsuario(String login) throws RemoteException {
        Usuario usuario = usuarios.get(login); // Busca o usuário pelo login
        if (usuario != null) {
            return usuario.getPapel(); // Retorna o papel do usuário
        }
        throw new RemoteException("Usuário não encontrado.");
    }

    @Override
    public Usuario buscarUsuarioPorLogin(String login) throws RemoteException {
        if (usuarios.containsKey(login)) {
            return usuarios.get(login); // Retorna o objeto Usuario do HashMap
        } else {
            throw new RemoteException("Usuário não encontrado.");
        }
    }

    @Override
    public void exibirRelatorioVendas() throws RemoteException {
        String query = """
        SELECT 
            Vendas.id_venda, 
            Clientes.nome AS cliente, 
            Operadores.nome AS operador, 
            Carros.modelo AS carro, 
            Vendas.data_venda, 
            Vendas.valor_venda
        FROM Vendas
        JOIN Clientes ON Vendas.id_cliente = Clientes.id_cliente
        JOIN Operadores ON Vendas.id_operador = Operadores.id_operador
        JOIN Carros ON Vendas.id_carro = Carros.id_carro
        ORDER BY Vendas.data_venda DESC;
    """;

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            System.out.println("=== Relatório de Vendas ===");
            while (rs.next()) {
                System.out.printf("Venda ID: %d | Cliente: %s | Operador: %s | Carro: %s | Data: %s | Valor: R$ %.2f%n",
                        rs.getInt("id_venda"),
                        rs.getString("cliente"),
                        rs.getString("operador"),
                        rs.getString("carro"),
                        rs.getDate("data_venda"),
                        rs.getBigDecimal("valor_venda")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de vendas: " + e.getMessage());
        }
    }

    @Override
    public void exibirRelatorioLocacoes() throws RemoteException {
        String query = """
        SELECT 
            Locacoes.id_locacao, 
            Clientes.nome AS cliente, 
            Operadores.nome AS operador, 
            Carros.modelo AS carro, 
            Locacoes.data_inicio, 
            Locacoes.data_fim, 
            Locacoes.valor_locacao
        FROM Locacoes
        JOIN Clientes ON Locacoes.id_cliente = Clientes.id_cliente
        JOIN Operadores ON Locacoes.id_operador = Operadores.id_operador
        JOIN Carros ON Locacoes.id_carro = Carros.id_carro
        ORDER BY Locacoes.data_inicio DESC;
    """;

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            System.out.println("=== Relatório de Locações ===");
            while (rs.next()) {
                System.out.printf("Locação ID: %d | Cliente: %s | Operador: %s | Carro: %s | Início: %s | Fim: %s | Valor: R$ %.2f%n",
                        rs.getInt("id_locacao"),
                        rs.getString("cliente"),
                        rs.getString("operador"),
                        rs.getString("carro"),
                        rs.getDate("data_inicio"),
                        rs.getDate("data_fim"),
                        rs.getBigDecimal("valor_locacao")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de locações: " + e.getMessage());
        }
    }

}
