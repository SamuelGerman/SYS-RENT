package com.locadora.services;

import com.locadora.data.ManutencaoRepository;
import com.locadora.domain.Manutencao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManutencaoService {

    private ManutencaoRepository manutencaoRepository;

    public ManutencaoService(ManutencaoRepository manutencaoRepository) {
        this.manutencaoRepository = manutencaoRepository;
    }

    // Método para salvar uma nova manutenção
    public void salvarManutencao(Manutencao manutencao) throws SQLException {
        if (manutencao.getCusto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O custo da manutenção deve ser maior que zero.");
        }
        manutencaoRepository.salvar(manutencao);
    }

    // Método para atualizar uma manutenção existente
    public void atualizarManutencao(Manutencao manutencao) throws SQLException {
        if (manutencao.getIdManutencao() <= 0) {
            throw new IllegalArgumentException("ID da manutenção inválido.");
        }
        if (manutencao.getCusto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O custo da manutenção deve ser maior que zero.");
        }
        manutencaoRepository.atualizar(manutencao);
    }

    // Método para excluir uma manutenção
    public void excluirManutencao(int idManutencao) throws SQLException {
        if (idManutencao <= 0) {
            throw new IllegalArgumentException("ID da manutenção inválido.");
        }
        manutencaoRepository.excluir(idManutencao);
    }

    // Buscar manutenção por ID
    public Manutencao buscarManutencaoPorId(int idManutencao) throws SQLException {
        if (idManutencao <= 0) {
            throw new IllegalArgumentException("ID da manutenção inválido.");
        }
        return manutencaoRepository.buscarPorId(idManutencao);
    }

    // Buscar todas as manutenções
    public List<Manutencao> listarTodasManutencoes() throws SQLException {
        return manutencaoRepository.buscarTodos();
    }

    // Buscar manutenções por carro
    public List<Manutencao> listarManutencoesPorCarro(int idCarro) throws SQLException {
        if (idCarro <= 0) {
            throw new IllegalArgumentException("ID do carro inválido.");
        }
        return manutencaoRepository.buscarPorCarro(idCarro);
    }

    // Buscar manutenções por operador
    public List<Manutencao> listarManutencoesPorOperador(int idOperador) throws SQLException {
        if (idOperador <= 0) {
            throw new IllegalArgumentException("ID do operador inválido.");
        }
        return manutencaoRepository.buscarPorOperador(idOperador);
    }

    // Calcular custo total de manutenções para um carro
    public BigDecimal calcularCustoTotalPorCarro(int idCarro) throws SQLException {
        List<Manutencao> manutencoes = listarManutencoesPorCarro(idCarro);
        return manutencoes.stream()
                .map(Manutencao::getCusto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calcular custo total de manutenções realizadas por um operador
    public BigDecimal calcularCustoTotalPorOperador(int idOperador) throws SQLException {
        List<Manutencao> manutencoes = listarManutencoesPorOperador(idOperador);
        return manutencoes.stream()
                .map(Manutencao::getCusto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Verificar se uma manutenção é recente (últimos 30 dias)
    public boolean isManutencaoRecente(Manutencao manutencao) {
        return manutencao.getData().isAfter(LocalDate.now().minusDays(30));
    }
}
