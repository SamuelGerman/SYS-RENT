package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LocadoraService extends Remote {

    List<Carro> listarVeiculos() throws RemoteException;

    boolean reservarVeiculo(int id) throws RemoteException;

    boolean devolverVeiculo(int id) throws RemoteException;

    boolean registrarLocacao(int idCliente, int idOperador, int idCarro, String dataInicio, String dataFim, double valorLocacao) throws RemoteException;

    List<Carro> listarCarrosVenda() throws RemoteException;

    boolean venderVeiculo(int id) throws RemoteException;

    boolean registrarVenda(int idCliente, int idOperador, int idCarro, double valorVenda) throws RemoteException;

    List<Carro> listarVeiculosPendentesVistoria() throws RemoteException;

    public boolean registrarVistoria(int idCarro, int idLocacao, int idOperador, String estado) throws RemoteException;
    
    List<Carro> listarVeiculosComManutencaoPendente() throws RemoteException;

    boolean registrarManutencao(int idCarro, int idOperador, String tipo, double custo) throws RemoteException;
}
