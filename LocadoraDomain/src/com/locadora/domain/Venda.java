/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.locadora.domain;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Venda {
    private int idVenda;
    private int idCliente;
    private int idOperador;
    private int idCarro;
    private LocalDate dataVenda;
    private BigDecimal valorVenda;

    // Construtor padrão
    public Venda() {}

    // Construtor com todos os atributos
    public Venda(int idVenda, int idCliente, int idOperador, int idCarro, LocalDate dataVenda, BigDecimal valorVenda) {
        this.idVenda = idVenda;
        this.idCliente = idCliente;
        this.idOperador = idOperador;
        this.idCarro = idCarro;
        this.dataVenda = dataVenda;
        this.valorVenda = valorVenda;
    }

    // Getters e Setters
    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
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

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "idVenda=" + idVenda +
                ", idCliente=" + idCliente +
                ", idOperador=" + idOperador +
                ", idCarro=" + idCarro +
                ", dataVenda=" + dataVenda +
                ", valorVenda=" + valorVenda +
                '}';
    }
}

