/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.locadora.domain;

import java.time.LocalDateTime;

public class Usuario {
    private int idUsuario;
    private String login;
    private String senha;
    private String papel;
    private LocalDateTime dataCriacao;

    public Usuario() {}

    public Usuario(int idUsuario, String login, String senha, String papel, LocalDateTime dataCriacao) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.papel = papel;
        this.dataCriacao = dataCriacao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", login='" + login + '\'' +
                ", papel='" + papel + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
