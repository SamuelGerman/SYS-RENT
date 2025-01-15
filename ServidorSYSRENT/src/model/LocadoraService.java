package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LocadoraService extends Remote {
    List<Carro> listarVeiculos() throws RemoteException;
    boolean reservarVeiculo(int id) throws RemoteException;
    boolean devolverVeiculo(int id) throws RemoteException;
}
