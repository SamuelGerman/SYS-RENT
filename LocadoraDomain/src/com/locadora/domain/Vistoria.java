/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.locadora.domain;
import java.time.LocalDate;

public class Vistoria {
    private int idVistoria;
    private int idCarro;
    private int idLocacao;
    private int idOperador;
    private LocalDate data;
    private String estado;

    // Construtor padrão
    public Vistoria() {}

    // Construtor com todos os atributos
    public Vistoria(int idVistoria, int idCarro, int idLocacao, int idOperador, LocalDate data, String estado) {
        this.idVistoria = idVistoria;
        this.idCarro = idCarro;
        this.idLocacao = idLocacao;
        this.idOperador = idOperador;
        this.data = data;
        this.estado = estado;
    }

    // Getters e Setters
    public int getIdVistoria() {
        return idVistoria;
    }

    public void setIdVistoria(int idVistoria) {
        this.idVistoria = idVistoria;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public int getIdLocacao() {
        return idLocacao;
    }

    public void setIdLocacao(int idLocacao) {
        this.idLocacao = idLocacao;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Vistoria{" +
                "idVistoria=" + idVistoria +
                ", idCarro=" + idCarro +
                ", idLocacao=" + idLocacao +
                ", idOperador=" + idOperador +
                ", data=" + data +
                ", estado='" + estado + '\'' +
                '}';
    }
}
