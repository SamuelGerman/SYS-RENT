/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.locadora.domain;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Manutencao {
    private int idManutencao;
    private int idCarro;
    private int idOperador;
    private LocalDate data;
    private String tipo;
    private BigDecimal custo;

    // Construtor padrão
    public Manutencao() {}

    // Construtor com todos os atributos
    public Manutencao(int idManutencao, int idCarro, int idOperador, LocalDate data, String tipo, BigDecimal custo) {
        this.idManutencao = idManutencao;
        this.idCarro = idCarro;
        this.idOperador = idOperador;
        this.data = data;
        this.tipo = tipo;
        this.custo = custo;
    }

    // Getters e Setters
    public int getIdManutencao() {
        return idManutencao;
    }

    public void setIdManutencao(int idManutencao) {
        this.idManutencao = idManutencao;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    @Override
    public String toString() {
        return "Manutencao{" +
                "idManutencao=" + idManutencao +
                ", idCarro=" + idCarro +
                ", idOperador=" + idOperador +
                ", data=" + data +
                ", tipo='" + tipo + '\'' +
                ", custo=" + custo +
                '}';
    }
}

