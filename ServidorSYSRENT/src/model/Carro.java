package model;

import java.io.Serializable;

public class Carro implements Serializable {
    private int idCarro;
    private String placa;
    private String modelo;
    private String marca;
    private int ano;
    private double quilometragem;
    private String status;
    private Double precoVenda; // Pode ser null
    private String dataCadastro;

    public Carro(int idCarro, String placa, String modelo, String marca, int ano, double quilometragem, String status, Double precoVenda, String dataCadastro) {
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
    public int getIdCarro() { return idCarro; }
    public void setIdCarro(int idCarro) { this.idCarro = idCarro; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public double getQuilometragem() { return quilometragem; }
    public void setQuilometragem(double quilometragem) { this.quilometragem = quilometragem; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(Double precoVenda) { this.precoVenda = precoVenda; }
    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }
    
     @Override
    public String toString() {
        return "Carro [ID=" + idCarro + ", Placa=" + placa + ", Modelo=" + modelo + ", Marca=" + marca + ", Ano=" + ano + ", Quilometragem=" + quilometragem + " km, Status=" + status + ", Preço Venda=" + precoVenda + " R$, Data Cadastro=" + dataCadastro + "]";
    }
}
