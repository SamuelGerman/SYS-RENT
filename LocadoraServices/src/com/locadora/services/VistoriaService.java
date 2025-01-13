package com.locadora.services;

import com.locadora.data.VistoriaRepository;
import com.locadora.domain.Vistoria;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VistoriaService {

    private final VistoriaRepository vistoriaRepository;

    public VistoriaService(VistoriaRepository vistoriaRepository) {
        this.vistoriaRepository = vistoriaRepository;
    }

    // Salvar uma nova vistoria
    public void salvarVistoria(Vistoria vistoria) throws SQLException {
        validarVistoria(vistoria);
        vistoriaRepository.salvar(vistoria);
    }

    // Atualizar uma vistoria existente
    public void atualizarVistoria(Vistoria vistoria) throws SQLException {
        if (vistoria.getIdVistoria() <= 0) {
            throw new IllegalArgumentException("ID da vistoria inválido.");
        }
        validarVistoria(vistoria);
        vistoriaRepository.atualizar(vistoria);
    }

    // Excluir uma vistoria pelo ID
    public void excluirVistoria(int idVistoria) throws SQLException {
        if (idVistoria <= 0) {
            throw new IllegalArgumentException("ID da vistoria inválido.");
        }
        vistoriaRepository.excluir(idVistoria);
    }

    // Buscar uma vistoria pelo ID
    public Vistoria buscarVistoriaPorId(int idVistoria) throws SQLException {
        if (idVistoria <= 0) {
            throw new IllegalArgumentException("ID da vistoria inválido.");
        }
        return vistoriaRepository.buscarPorId(idVistoria);
    }

    // Listar todas as vistorias
    public List<Vistoria> listarTodasVistorias() throws SQLException {
        return vistoriaRepository.buscarTodos();
    }

    // Validar os dados de uma vistoria
    private void validarVistoria(Vistoria vistoria) {
        if (vistoria.getIdCarro() <= 0) {
            throw new IllegalArgumentException("ID do carro inválido.");
        }
        if (vistoria.getIdLocacao() <= 0) {
            throw new IllegalArgumentException("ID da locação inválido.");
        }
        if (vistoria.getIdOperador() <= 0) {
            throw new IllegalArgumentException("ID do operador inválido.");
        }
        if (vistoria.getData() == null || vistoria.getData().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data da vistoria inválida.");
        }
        if (vistoria.getEstado() == null || vistoria.getEstado().isBlank()) {
            throw new IllegalArgumentException("Estado da vistoria inválido.");
        }
    }
}
