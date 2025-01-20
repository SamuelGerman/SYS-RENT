package view;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import model.Carro;
import model.LocadoraService;

public class Cliente {

    public static void main(String[] args) {
        try {
            // Obtendo o serviço remoto
            LocadoraService service = (LocadoraService) Naming.lookup("rmi://localhost/LocadoraService");

            // Listar veículos com manutenção pendente
            System.out.println("Listando veículos com manutenção pendente...");
            List<Carro> veiculos = service.listarVeiculosComManutencaoPendente();
            if (veiculos.isEmpty()) {
                System.out.println("Nenhum veículo com manutenção pendente.");
            } else {
                for (Carro carro : veiculos) {
                    System.out.println(carro);
                }
            }

            // Registrar uma manutenção fictícia
            System.out.println("\nRegistrando manutenção...");
            int idCarro = 1;  // Exemplo de ID de carro
            int idOperador = 6;  // Exemplo de ID de operador
            String tipo = "disponivel";  // Tipo de manutenção
            double custo = 500.00;  // Custo da manutenção
            
            boolean sucesso = service.registrarManutencao(idCarro, idOperador, tipo, custo);
            
            if (sucesso) {
                System.out.println("Manutenção registrada com sucesso e status do carro atualizado.");
            } else {
                System.out.println("Erro ao registrar a manutenção.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
