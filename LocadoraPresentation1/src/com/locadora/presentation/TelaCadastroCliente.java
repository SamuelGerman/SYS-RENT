package com.locadora.presentation;

import com.locadora.domain.Cliente;
import com.locadora.data.ClienteRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TelaCadastroCliente extends JFrame {
    private JTextField nomeField;
    private JTextField cpfCnpjField;
    private JTextField enderecoField;
    private JTextField telefoneField;
    private JTextField emailField;

    private JButton salvarButton;

    private ClienteRepository clienteRepository;

    public TelaCadastroCliente() {
        // Configuração da janela
        setTitle("Cadastro de Cliente");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        setLayout(new GridLayout(7, 2));

        // Criação dos campos de texto e rótulos
        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("CPF/CNPJ:"));
        cpfCnpjField = new JTextField();
        add(cpfCnpjField);

        add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        add(enderecoField);

        add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        add(telefoneField);

        add(new JLabel("E-mail:"));
        emailField = new JTextField();
        add(emailField);

        // Botão para salvar
        salvarButton = new JButton("Salvar");
        add(salvarButton);

        // Ação do botão Salvar
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCliente();
            }
        });

        // Estabelecer a conexão com o banco de dados e criar o repositório
        Connection connection = criarConexao();
        clienteRepository = new ClienteRepository(connection);
    }

    // Método para salvar os dados do cliente
    private void salvarCliente() {
        // Criar cliente com os dados fornecidos nos campos de texto
        Cliente cliente = new Cliente();
        cliente.setNome(nomeField.getText());
        cliente.setCpfCnpj(cpfCnpjField.getText());
        cliente.setEndereco(enderecoField.getText());
        cliente.setTelefone(telefoneField.getText());
        cliente.setEmail(emailField.getText());

        try {
            // Salvar cliente no banco de dados
            clienteRepository.addCliente(cliente);
            // Exibir mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            // Limpar os campos
            limparCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + ex.getMessage());
        }
    }

    // Método para limpar os campos após salvar
    private void limparCampos() {
        nomeField.setText("");
        cpfCnpjField.setText("");
        enderecoField.setText("");
        telefoneField.setText("");
        emailField.setText("");
    }

    // Método para criar a conexão com o banco de dados
    private Connection criarConexao() {
        try {
            // Substitua pelo URL do seu banco de dados
            String url = "jdbc:mysql://localhost:3306/sua_base_de_dados";
            String usuario = "seu_usuario";
            String senha = "sua_senha";
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.");
            return null;
        }
    }

    // Método principal para rodar a tela
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaCadastroCliente().setVisible(true);
            }
        });
    }
}
