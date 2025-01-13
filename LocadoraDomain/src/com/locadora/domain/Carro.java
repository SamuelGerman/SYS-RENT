/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.locadora.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Carro {
    private int idCarro;
    private String placa;
    private String modelo;
    private String marca;
    private int ano;
    private double quilometragem;
    private StatusCarro status;
    private BigDecimal precoVenda;
    private Timestamp dataCadastro;

    // Enum para representar os status do carro
    public enum StatusCarro {
        DISPONIVEL,
        LOCADO,
        MANUTENCAO,
        VENDA
    }

    // Construtor padrão
    public Carro() {}

    // Construtor com todos os atributos
    public Carro(int idCarro, String placa, String modelo, String marca, int ano, double quilometragem, 
                 StatusCarro status, BigDecimal precoVenda, Timestamp dataCadastro) {
        this.idCarro = idCarro;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.status = status;
        this.precoVenda = precoVenda;
        this.dataCadastro = dataCadastro;
    }

    // Getters e Setters
    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public StatusCarro getStatus() {
        return status;
    }

    public void setStatus(StatusCarro status) {
        this.status = status;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Timestamp dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "idCarro=" + idCarro +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", ano=" + ano +
                ", quilometragem=" + quilometragem +
                ", status=" + status +
                ", precoVenda=" + precoVenda +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}

