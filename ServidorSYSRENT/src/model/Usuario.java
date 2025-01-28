package model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L; // Identificador da versão da classe

    private int id;
    private String login;
    private String senha;
    private String papel;

    public Usuario(int id, String login, String senha, String papel) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.papel = papel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "login='" + login + '\'' +
                ", papel='" + papel + '\'' +
                '}';
    }
}
