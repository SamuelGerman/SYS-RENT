package com.locadora.presentation;

import com.locadora.data.CarroRepository;
import com.locadora.data.LocacaoRepository;
import com.locadora.domain.Carro;
import com.locadora.domain.Locacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TelaLocacao extends JFrame {

    private final CarroRepository carroRepository;
    private final LocacaoRepository locacaoRepository;
    private JTable tabelaCarros;
    private DefaultTableModel tableModel;
    private List<Carro> carrosDisponiveis;

    public TelaLocacao(Connection connection) {
        this.carroRepository = new CarroRepository(connection);
        this.locacaoRepository = new LocacaoRepository(connection);
        inicializarUI();
        carregarCarrosDisponiveis();
    }

    private void inicializarUI() {
        setTitle("Locação de Veículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabela de carros
        tabelaCarros = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"ID", "Modelo", "Placa", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desabilitar edição de células
            }
        };
        tabelaCarros.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaCarros);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de ações
        JPanel panelAcoes = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField txtIdCliente = new JTextField();
        JTextField txtIdOperador = new JTextField();
        JTextField txtDataInicio = new JTextField("yyyy-MM-dd");
        JTextField txtDataFim = new JTextField("yyyy-MM-dd");
        JTextField txtValorLocacao = new JTextField();

        panelAcoes.add(new JLabel("ID do Cliente:"));
        panelAcoes.add(txtIdCliente);
        panelAcoes.add(new JLabel("ID do Operador:"));
        panelAcoes.add(txtIdOperador);
        panelAcoes.add(new JLabel("Data de Início:"));
        panelAcoes.add(txtDataInicio);
        panelAcoes.add(new JLabel("Data de Fim:"));
        panelAcoes.add(txtDataFim);
        panelAcoes.add(new JLabel("Valor da Locação:"));
        panelAcoes.add(txtValorLocacao);

        JButton btnLocar = new JButton("Realizar Locação");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        panelAcoes.add(btnLocar);
        panelAcoes.add(btnAtualizar);

        add(panelAcoes, BorderLayout.SOUTH);

        // Eventos dos botões
        btnLocar.addActionListener(e -> realizarLocacao(txtIdCliente, txtIdOperador, txtDataInicio, txtDataFim, txtValorLocacao));
        btnAtualizar.addActionListener(e -> carregarCarrosDisponiveis());
    }

    private void carregarCarrosDisponiveis() {
        try {
            carrosDisponiveis = carroRepository.getAllCarros();
            tableModel.setRowCount(0); // Limpar tabela

            for (Carro carro : carrosDisponiveis) {
                if (carro.getStatus() == Carro.StatusCarro.DISPONIVEL) {
                    tableModel.addRow(new Object[]{
                            carro.getIdCarro(),
                            carro.getModelo(),
                            carro.getPlaca(),
                            carro.getStatus().name()
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar carros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarLocacao(JTextField txtIdCliente, JTextField txtIdOperador, JTextField txtDataInicio, JTextField txtDataFim, JTextField txtValorLocacao) {
        int selectedRow = tabelaCarros.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um carro para locação.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Obter o carro selecionado com base no índice da tabela
            Carro carroSelecionado = carrosDisponiveis.get(selectedRow);

            int idCliente = Integer.parseInt(txtIdCliente.getText());
            int idOperador = Integer.parseInt(txtIdOperador.getText());
            LocalDate dataInicio = LocalDate.parse(txtDataInicio.getText());
            LocalDate dataFim = LocalDate.parse(txtDataFim.getText());
            BigDecimal valorLocacao = new BigDecimal(txtValorLocacao.getText());

            // Criar a locação
            Locacao locacao = new Locacao(0, idCliente, idOperador, carroSelecionado.getIdCarro(), dataInicio, dataFim, valorLocacao);
            locacaoRepository.salvar(locacao);

            // Atualizar o status do carro para LOCADO
            carroSelecionado.setStatus(Carro.StatusCarro.LOCADO);
            carroRepository.updateCarro(carroSelecionado);

            JOptionPane.showMessageDialog(this, "Locação realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarCarrosDisponiveis();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar locação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Conexão hipotética ao banco de dados
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:locadora", "sa", "");

                // Configuração inicial do banco (se necessário)
                // Aqui você pode inicializar tabelas ou dados fictícios para testes

                TelaLocacao telaLocacao = new TelaLocacao(connection);
                telaLocacao.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
