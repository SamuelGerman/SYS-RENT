package com.locadora.presentation;

import com.locadora.data.CarroRepository;
import com.locadora.domain.Carro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class BuscaCarrosUI extends JFrame {

    private final CarroRepository carroRepository;
    private JTextField txtModelo;
    private JTable tabelaCarros;
    private DefaultTableModel tableModel;

    public BuscaCarrosUI(Connection connection) {
        this.carroRepository = new CarroRepository(connection);
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Busca de Carros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de busca
        JPanel panelBusca = new JPanel(new FlowLayout());
        JLabel lblModelo = new JLabel("Modelo:");
        txtModelo = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        panelBusca.add(lblModelo);
        panelBusca.add(txtModelo);
        panelBusca.add(btnBuscar);
        add(panelBusca, BorderLayout.NORTH);

        // Tabela de resultados
        tableModel = new DefaultTableModel(new Object[]{"ID", "Modelo", "Marca", "Ano", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desabilitar edição de células
            }
        };
        tabelaCarros = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaCarros);
        add(scrollPane, BorderLayout.CENTER);

        // Botão de detalhes
        JButton btnDetalhes = new JButton("Ver Detalhes");
        add(btnDetalhes, BorderLayout.SOUTH);

        // Eventos dos botões
        btnBuscar.addActionListener(e -> buscarCarros());
        btnDetalhes.addActionListener(e -> exibirDetalhesCarro());
    }

    private void buscarCarros() {
        String modelo = txtModelo.getText().trim();
        if (modelo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um modelo para buscar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Carro> carros = carroRepository.getCarrosByModelo(modelo);
            tableModel.setRowCount(0); // Limpar tabela

            if (carros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum carro encontrado com o modelo: " + modelo, "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Carro carro : carros) {
                    tableModel.addRow(new Object[]{
                            carro.getIdCarro(),
                            carro.getModelo(),
                            carro.getMarca(),
                            carro.getAno(),
                            carro.getStatus().name()
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar carros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirDetalhesCarro() {
        int selectedRow = tabelaCarros.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um carro para ver os detalhes.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idCarro = (int) tableModel.getValueAt(selectedRow, 0);
            Carro carro = carroRepository.getCarroById(idCarro); // Método assumido disponível

            String detalhes = String.format(
                    "ID: %d%nModelo: %s%nMarca: %s%nAno: %d%nQuilometragem: %.2f%nStatus: %s%nPreço de Venda: R$ %.2f%nData de Cadastro: %s",
                    carro.getIdCarro(), carro.getModelo(), carro.getMarca(), carro.getAno(),
                    carro.getQuilometragem(), carro.getStatus(), carro.getPrecoVenda(), carro.getDataCadastro()
            );

            JOptionPane.showMessageDialog(this, detalhes, "Detalhes do Carro", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter detalhes do carro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaLocadora", "root", "root")) {
                BuscaCarrosUI telaBusca = new BuscaCarrosUI(connection);
                telaBusca.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
