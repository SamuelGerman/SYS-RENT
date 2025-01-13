package com.locadora.services;

import com.locadora.domain.Carro;
import com.locadora.data.CarroRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CarroService {
    private CarroRepository carroRepository;

    // Construtor que recebe um repositório de Carro
    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    // Adicionar um novo carro
    public void adicionarCarro(Carro carro) {
        if (carro == null) {
            throw new IllegalArgumentException("O carro não pode ser nulo.");
        }
        try {
            carroRepository.addCarro(carro);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar o carro: " + e.getMessage(), e);
        }
    }

    // Atualizar as informações de um carro
    public void atualizarCarro(Carro carro) {
        if (carro == null) {
            throw new IllegalArgumentException("O carro não pode ser nulo.");
        }
        try {
            carroRepository.updateCarro(carro);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o carro: " + e.getMessage(), e);
        }
    }

    // Remover um carro
    public void removerCarro(int idCarro) {
        try {
            carroRepository.deleteCarro(idCarro);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover o carro: " + e.getMessage(), e);
        }
    }

    // Buscar todos os carros
    public List<Carro> buscarTodosCarros() {
        try {
            return carroRepository.getAllCarros();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os carros: " + e.getMessage(), e);
        }
    }

    // Buscar carro por ID
    public Optional<Carro> buscarCarroPorId(int idCarro) {
        try {
            List<Carro> carros = carroRepository.getAllCarros();
            return carros.stream()
                         .filter(carro -> carro.getIdCarro() == idCarro)
                         .findFirst();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o carro por ID: " + e.getMessage(), e);
        }
    }

    // Buscar carro por placa
    public Optional<Carro> buscarCarroPorPlaca(String placa) {
        try {
            Carro carro = carroRepository.getCarroByPlaca(placa);
            return Optional.ofNullable(carro);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o carro por placa: " + e.getMessage(), e);
        }
    }

    // Verificar disponibilidade de um carro para locação
    public boolean verificarDisponibilidade(int idCarro) {
        Optional<Carro> carro = buscarCarroPorId(idCarro);
        return carro.isPresent() && carro.get().getStatus() == Carro.StatusCarro.DISPONIVEL;
    }

    // Alterar o status de locação de um carro
    public void alterarStatusDeLocacao(int idCarro, Carro.StatusCarro novoStatus) {
        Optional<Carro> carro = buscarCarroPorId(idCarro);
        if (carro.isPresent()) {
            Carro c = carro.get();
            c.setStatus(novoStatus);
            atualizarCarro(c);
        } else {
            throw new IllegalArgumentException("Carro não encontrado.");
        }
    }

    // Calcular o valor do aluguel baseado no número de dias
    public BigDecimal calcularValorAluguel(int idCarro, int dias) {
        Optional<Carro> carro = buscarCarroPorId(idCarro);
        if (carro.isPresent()) {
            BigDecimal precoDiario = carro.get().getPrecoVenda().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);  // Exemplo: divisão para calcular aluguel
            return precoDiario.multiply(BigDecimal.valueOf(dias));
        }
        throw new IllegalArgumentException("Carro não encontrado.");
    }

    // Calcular o valor de venda do carro
    public BigDecimal calcularValorVenda(int idCarro) {
        Optional<Carro> carro = buscarCarroPorId(idCarro);
        if (carro.isPresent()) {
            return carro.get().getPrecoVenda();
        }
        throw new IllegalArgumentException("Carro não encontrado.");
    }

    // Buscar carros disponíveis para locação
    public List<Carro> buscarCarrosDisponiveisParaLocacao() {
        try {
            List<Carro> todosCarros = carroRepository.getAllCarros();
            return todosCarros.stream()
                              .filter(carro -> carro.getStatus() == Carro.StatusCarro.DISPONIVEL)
                              .toList();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar carros disponíveis para locação: " + e.getMessage(), e);
        }
    }

    // Buscar carros disponíveis para venda
    public List<Carro> buscarCarrosDisponiveisParaVenda() {
        try {
            List<Carro> todosCarros = carroRepository.getAllCarros();
            return todosCarros.stream()
                              .filter(carro -> carro.getStatus() == Carro.StatusCarro.DISPONIVEL)
                              .toList();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar carros disponíveis para venda: " + e.getMessage(), e);
        }
    }
}
