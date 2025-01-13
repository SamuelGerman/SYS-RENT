/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.locadora.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Locacao {
    private int idLocacao;
    private int idCliente;
    private int idOperador;
    private int idCarro;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorLocacao;

    // Construtor padrão
    public Locacao() {}

    // Construtor com todos os atributos
    public Locacao(int idLocacao, int idCliente, int idOperador, int idCarro, LocalDate dataInicio, LocalDate dataFim, BigDecimal valorLocacao) {
        this.idLocacao = idLocacao;
        this.idCliente = idCliente;
        this.idOperador = idOperador;
        this.idCarro = idCarro;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorLocacao = valorLocacao;
    }

    // Getters e Setters
    public int getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(int idLocacao) {
        this.idLocacao = idLocacao;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(BigDecimal valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    @Override
    public String toString() {
        return "Locacao{" +
                "idLocacao=" + idLocacao +
                ", idCliente=" + idCliente +
                ", idOperador=" + idOperador +
                ", idCarro=" + idCarro +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", valorLocacao=" + valorLocacao +
                '}';
    }
}

