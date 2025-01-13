package com.locadora.services;

import com.locadora.data.VendaRepository;
import com.locadora.domain.Venda;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VendaService {

    private VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    // Salvar uma nova venda
    public void salvarVenda(Venda venda) throws SQLException {
        validarVenda(venda);
        vendaRepository.salvar(venda);
    }

    // Atualizar uma venda existente
    public void atualizarVenda(Venda venda) throws SQLException {
        if (venda.getIdVenda() <= 0) {
            throw new IllegalArgumentException("ID da venda inválido.");
        }
        validarVenda(venda);
        vendaRepository.atualizar(venda);
    }

    // Excluir uma venda pelo ID
    public void excluirVenda(int idVenda) throws SQLException {
        if (idVenda <= 0) {
            throw new IllegalArgumentException("ID da venda inválido.");
        }
        vendaRepository.excluir(idVenda);
    }

    // Buscar uma venda pelo ID
    public Venda buscarVendaPorId(int idVenda) throws SQLException {
        if (idVenda <= 0) {
            throw new IllegalArgumentException("ID da venda inválido.");
        }
        return vendaRepository.buscarPorId(idVenda);
    }

    // Listar todas as vendas
    public List<Venda> listarTodasVendas() throws SQLException {
        return vendaRepository.buscarTodos();
    }

    // Calcular o total de vendas realizadas em uma data específica
    public BigDecimal calcularTotalVendasPorData(LocalDate data) throws SQLException {
        List<Venda> vendas = listarTodasVendas();
        return vendas.stream()
                .filter(venda -> venda.getDataVenda().isEqual(data))
                .map(Venda::getValorVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Validar os dados de uma venda
    private void validarVenda(Venda venda) {
        if (venda.getIdCliente() <= 0) {
            throw new IllegalArgumentException("ID do cliente inválido.");
        }
        if (venda.getIdOperador() <= 0) {
            throw new IllegalArgumentException("ID do operador inválido.");
        }
        if (venda.getIdCarro() <= 0) {
            throw new IllegalArgumentException("ID do carro inválido.");
        }
        if (venda.getDataVenda() == null || venda.getDataVenda().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data da venda inválida.");
        }
        if (venda.getValorVenda().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da venda deve ser maior que zero.");
        }
    }
}
