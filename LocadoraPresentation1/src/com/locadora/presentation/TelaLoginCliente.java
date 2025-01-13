package com.locadora.presentation;

import com.locadora.data.ClienteRepository;
import com.locadora.domain.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TelaLoginCliente extends JFrame {

    private JTextField cpfCnpjField;
    private JPasswordField senhaField;
    private JButton loginButton;

    private ClienteRepository clienteRepository;

    public TelaLoginCliente() {
        // Configuração da janela
        setTitle("Login do Cliente");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout da tela
        setLayout(new GridLayout(3, 2));

        // Campos de entrada
        add(new JLabel("CPF/CNPJ:"));
        cpfCnpjField = new JTextField();
        add(cpfCnpjField);

        add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        add(senhaField);

        // Botão de login
        loginButton = new JButton("Entrar");
        add(new JLabel()); // Espaço vazio
        add(loginButton);

        // Estabelecer a conexão com o banco de dados e criar o repositório
        Connection connection = criarConexao();
        clienteRepository = new ClienteRepository(connection);

        // Configurar ação do botão de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarCliente();
            }
        });
    }

    // Método para autenticar o cliente
    private void autenticarCliente() {
        String cpfCnpj = cpfCnpjField.getText();
        String senha = new String(senhaField.getPassword());

        try {
            Cliente cliente = clienteRepository.getClienteByCpfCnpj(cpfCnpj);
            if (cliente != null) {
                // Verificar a senha (para simplificar, assumindo senha padrão "123456")
                if ("123456".equals(senha)) {
                    JOptionPane.showMessageDialog(this, "Bem-vindo, " + cliente.getNome() + "!");
                    abrirTelaPrincipal();
                } else {
                    JOptionPane.showMessageDialog(this, "Senha incorreta. Tente novamente.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: " + ex.getMessage());
        }
    }

    // Método para abrir a tela principal após o login
    private void abrirTelaPrincipal() {
        // Exemplo de redirecionamento (substituir com lógica real)
        JOptionPane.showMessageDialog(this, "Redirecionando para o sistema...");
        dispose(); // Fecha a tela de login
    }

    // Método para criar a conexão com o banco de dados
    private Connection criarConexao() {
        try {
            // Substitua pelos valores reais do seu banco
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

    // Método principal para testar a tela de login
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaLoginCliente().setVisible(true);
            }
        });
    }
}
