package view;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import model.Carro;
import model.LocadoraService;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Obtendo o serviço remoto a partir do RMI Registry
            LocadoraService service = (LocadoraService) Naming.lookup("rmi://localhost/LocadoraService");

            // Testando o método listarVeiculos
            System.out.println("Listando veículos disponíveis:");
            List<Carro> carros = service.listarVeiculos();
            for (Carro carro : carros) {
                System.out.println(carro);
            }

            // Testando o método reservarVeiculo
            System.out.println("\nReservando veículo de id 1...");
            boolean reservado = service.reservarVeiculo(1);
            if (reservado) {
                System.out.println("Veículo reservado com sucesso.");
            } else {
                System.out.println("Falha ao reservar veículo.");
            }

            // Testando o método devolverVeiculo
            System.out.println("\nDevolvendo veículo de id 1...");
            boolean devolvido = service.devolverVeiculo(1);
            if (devolvido) {
                System.out.println("Veículo devolvido com sucesso.");
            } else {
                System.out.println("Falha ao devolver veículo.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
