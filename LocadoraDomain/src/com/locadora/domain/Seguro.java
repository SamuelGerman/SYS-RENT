/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.locadora.domain;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Seguro {
    private int idSeguro;
    private int idLocacao;
    private String tipo;
    private BigDecimal valor;
    private LocalDate dataContratacao;

    // Construtor padrão
    public Seguro() {}

    // Construtor com todos os atributos
    public Seguro(int idSeguro, int idLocacao, String tipo, BigDecimal valor, LocalDate dataContratacao) {
        this.idSeguro = idSeguro;
        this.idLocacao = idLocacao;
        this.tipo = tipo;
        this.valor = valor;
        this.dataContratacao = dataContratacao;
    }

    // Getters e Setters
    public int getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }

    public int getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(int idLocacao) {
        this.idLocacao = idLocacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    @Override
    public String toString() {
        return "Seguro{" +
                "idSeguro=" + idSeguro +
                ", idLocacao=" + idLocacao +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", dataContratacao=" + dataContratacao +
                '}';
    }
}

