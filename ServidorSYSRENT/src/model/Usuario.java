/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author samuel
 */
public class Usuario {
    private String login;
    private String senha;
    private String papel;

    public Usuario(String login, String senha, String papel) {
        this.login = login;
        this.senha = senha;
        this.papel = papel;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getPapel() {
        return papel;
    }
}
