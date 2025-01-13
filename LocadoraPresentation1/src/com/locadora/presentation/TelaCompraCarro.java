package com.locadora.presentation;

import com.locadora.data.CarroRepository;
import com.locadora.domain.Carro;
import com.locadora.domain.Carro.StatusCarro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaCompraCarro extends JFrame {

    private final CarroRepository carroRepository;
    private JTable tabelaCarros;
    private DefaultTableModel tableModel;

    public TelaCompraCarro(Connection connection) {
        this.carroRepository = new CarroRepository(connection);
        inicializarUI();
        carregarCarrosDisponiveis();
    }

    private void inicializarUI() {
        setTitle("Compra de Carros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabela de carros
        tabelaCarros = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"ID", "Placa", "Modelo", "Marca", "Ano", "Preço"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desabilitar edição de células
            }
        };
        tabelaCarros.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaCarros);
        add(scrollPane, BorderLayout.CENTER);

        // Botões
        JPanel panelBotoes = new JPanel();
        JButton btnComprar = new JButton("Comprar Carro");
        JButton btnAtualizar = new JButton("Atualizar Lista");
        panelBotoes.add(btnComprar);
        panelBotoes.add(btnAtualizar);
        add(panelBotoes, BorderLayout.SOUTH);

        // Eventos
        btnComprar.addActionListener(e -> comprarCarro());
        btnAtualizar.addActionListener(e -> carregarCarrosDisponiveis());
    }

    private void carregarCarrosDisponiveis() {
        try {
            List<Carro> carros = carroRepository.getAllCarros();
            tableModel.setRowCount(0); // Limpar tabela

            for (Carro carro : carros) {
                if (carro.getStatus() == StatusCarro.DISPONIVEL) {
                    tableModel.addRow(new Object[]{
                            carro.getIdCarro(),
                            carro.getPlaca(),
                            carro.getModelo(),
                            carro.getMarca(),
                            carro.getAno(),
                            carro.getPrecoVenda()
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar carros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void comprarCarro() {
        int selectedRow = tabelaCarros.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um carro para comprar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCarro = (int) tableModel.getValueAt(selectedRow, 0);
        String modelo = (String) tableModel.getValueAt(selectedRow, 2);
        String placa = (String) tableModel.getValueAt(selectedRow, 1);
        double preco = (double) tableModel.getValueAt(selectedRow, 5);

        int confirmacao = JOptionPane.showConfirmDialog(this,
                String.format("Confirma a compra do carro %s (%s) por R$ %.2f?", modelo, placa, preco),
                "Confirmar Compra",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                Carro carro = carroRepository.getCarroByPlaca(placa);
                if (carro != null && carro.getStatus() == StatusCarro.DISPONIVEL) {
                    carro.setStatus(StatusCarro.VENDA);
                    carroRepository.updateCarro(carro);
                    JOptionPane.showMessageDialog(this, "Compra realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarCarrosDisponiveis();
                } else {
                    JOptionPane.showMessageDialog(this, "Carro não disponível para compra.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao processar a compra: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
