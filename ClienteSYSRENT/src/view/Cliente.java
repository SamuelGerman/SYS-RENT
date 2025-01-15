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

            // Testando o método listarCarrosVenda
            System.out.println("Listando carros à venda:");
            List<Carro> carrosVenda = service.listarCarrosVenda();
            for (Carro carro : carrosVenda) {
                System.out.println(carro);
            }

            // Testando o método venderVeiculo
            System.out.println("\nVendendo veículo de id 1...");
            boolean vendido = service.venderVeiculo(1);
            if (vendido) {
                System.out.println("Veículo vendido com sucesso.");
            } else {
                System.out.println("Falha ao vender veículo.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
