package com.locadora.service;

import com.locadora.data.LocacaoRepository;
import com.locadora.domain.Locacao;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LocacaoService {

    private LocacaoRepository locacaoRepository;

    public LocacaoService(LocacaoRepository locacaoRepository) {
        this.locacaoRepository = locacaoRepository;
    }

    // Método para salvar uma locação
    public void salvarLocacao(Locacao locacao) throws SQLException {
        if (locacao.getDataFim().isBefore(locacao.getDataInicio())) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início.");
        }
        locacaoRepository.salvar(locacao);
    }

    // Método para atualizar uma locação
    public void atualizarLocacao(Locacao locacao) throws SQLException {
        if (locacao.getDataFim().isBefore(locacao.getDataInicio())) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início.");
        }
        locacaoRepository.atualizar(locacao);
    }

    // Método para excluir uma locação
    public void excluirLocacao(int idLocacao) throws SQLException {
        locacaoRepository.excluir(idLocacao);
    }

    // Método para buscar locação por ID
    public Locacao buscarLocacaoPorId(int idLocacao) throws SQLException {
        return locacaoRepository.buscarPorId(idLocacao);
    }

    // Método para buscar todas as locações
    public List<Locacao> buscarTodasLocacoes() throws SQLException {
        return locacaoRepository.buscarTodos();
    }

    // Método para buscar locações por cliente
    public List<Locacao> buscarLocacoesPorCliente(int idCliente) throws SQLException {
        return locacaoRepository.buscarPorCliente(idCliente);
    }

    // Método para buscar locações por carro
    public List<Locacao> buscarLocacoesPorCarro(int idCarro) throws SQLException {
        return locacaoRepository.buscarPorCarro(idCarro);
    }

    // Método para calcular o total gasto em locações por um cliente
    public BigDecimal calcularTotalValorLocacaoPorCliente(int idCliente) throws SQLException {
        return locacaoRepository.calcularTotalValorLocacaoPorCliente(idCliente);
    }

    // Método para calcular o total de valor gerado pelas locações de um carro
    public BigDecimal calcularTotalValorLocacaoPorCarro(int idCarro) throws SQLException {
        return locacaoRepository.calcularTotalValorLocacaoPorCarro(idCarro);
    }

    // Método para verificar se a locação está ativa com base nas datas
    public boolean isLocacaoAtiva(Locacao locacao) {
        LocalDate hoje = LocalDate.now();
        return !locacao.getDataInicio().isAfter(hoje) && !locacao.getDataFim().isBefore(hoje);
    }

    // Método para calcular o valor da locação com base na duração e no preço do carro
    public BigDecimal calcularValorLocacao(Locacao locacao, BigDecimal precoDiarioCarro) {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(locacao.getDataInicio(), locacao.getDataFim());
        return precoDiarioCarro.multiply(BigDecimal.valueOf(dias));
    }
}
