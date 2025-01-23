package view;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Carro;
import model.LocadoraService;

public class Cliente {

    public static void main(String[] args) {
        try {
            LocadoraService service = (LocadoraService) Naming.lookup("rmi://localhost:1099/LocadoraService");

            // Cadastro de usuário
            //String login = "cliente7";
            //String senha = "senha123";
            //String papel = "cliente";
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                // Exibir o menu de opções
                System.out.println("\nEscolha um método para testar:");
                System.out.println("1 - Listar veículos");
                System.out.println("2 - Reservar veículo");
                System.out.println("3 - Devolver veículo");
                System.out.println("4 - Registrar locação");
                System.out.println("5 - Listar carros à venda");
                System.out.println("6 - Vender veículo");
                System.out.println("7 - Registrar venda");
                System.out.println("8 - Listar veículos pendentes de vistoria");
                System.out.println("9 - Registrar vistoria");
                System.out.println("10 - Listar veículos com manutenção pendente");
                System.out.println("11 - Registrar manutenção");
                System.out.println("12 - Cadastrar operador");
                System.out.println("13 - Cadastrar usuário");
                System.out.println("14 - Cadastrar cliente");
                System.out.println("0 - Sair");

                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        // Listar veículos
                        List<Carro> veiculos = service.listarVeiculos();
                        veiculos.forEach(carro -> System.out.println(carro));
                        break;

                    case 2:
                        // Reservar veículo (exemplo com ID fictício)
                        boolean reservado = service.reservarVeiculo(1); // ID de exemplo
                        System.out.println("Reserva " + (reservado ? "realizada" : "falhou"));
                        break;

                    case 3:
                        // Devolver veículo (exemplo com ID fictício)
                        boolean devolvido = service.devolverVeiculo(1); // ID de exemplo
                        System.out.println("Devolução " + (devolvido ? "realizada" : "falhou"));
                        break;

                    case 4:
                        // Registrar locação (exemplo com parâmetros fixos)
                        boolean locado = service.registrarLocacao(1, 1, 1, "2025-01-01", "2025-01-10", 1000.00);
                        System.out.println("Locação " + (locado ? "registrada" : "falhou"));
                        break;

                    case 5:
                        // Listar carros à venda
                        List<Carro> carrosVenda = service.listarCarrosVenda();
                        carrosVenda.forEach(carro -> System.out.println(carro));
                        break;

                    case 6:
                        // Vender veículo (exemplo com ID fictício)
                        boolean vendido = service.venderVeiculo(1); // ID de exemplo
                        System.out.println("Venda " + (vendido ? "realizada" : "falhou"));
                        break;

                    case 7:
                        // Registrar venda (exemplo com parâmetros fixos)
                        boolean vendaRegistrada = service.registrarVenda(1, 1, 1, 50000.00);
                        System.out.println("Venda " + (vendaRegistrada ? "registrada" : "falhou"));
                        break;

                    case 8:
                        // Listar veículos pendentes de vistoria
                        List<Carro> veiculosVistoria = service.listarVeiculosPendentesVistoria();
                        veiculosVistoria.forEach(carro -> System.out.println(carro));
                        break;

                    case 9:
                        // Registrar vistoria (exemplo com parâmetros fixos)
                        boolean vistoriaRegistrada = service.registrarVistoria(1, 1, 1, "Bom");
                        System.out.println("Vistoria " + (vistoriaRegistrada ? "registrada" : "falhou"));
                        break;

                    case 10:
                        // Listar veículos com manutenção pendente
                        List<Carro> veiculosManutencao = service.listarVeiculosComManutencaoPendente();
                        veiculosManutencao.forEach(carro -> System.out.println(carro));
                        break;

                    case 11:
                        // Registrar manutenção (exemplo com parâmetros fixos)
                        boolean manutencaoRegistrada = service.registrarManutencao(1, 1, "Troca de óleo", 200.00);
                        System.out.println("Manutenção " + (manutencaoRegistrada ? "registrada" : "falhou"));
                        break;

                    case 12:
                        // Cadastrar operador (exemplo com parâmetros fixos)
                        boolean operadorCadastrado = service.cadastrarOperador("Operador Teste", "12345678901", "987654321", "operador@teste.com", 1);
                        System.out.println("Operador " + (operadorCadastrado ? "cadastrado" : "falhou"));
                        break;

                    case 13:
                        // Cadastrar usuário (exemplo com parâmetros fixos)
                        int idUsuario = service.cadastrarUsuario("login", "senha", "operador");
                        System.out.println("Usuário cadastrado com ID: " + idUsuario);
                        break;

                    case 14:
                        // Cadastrar cliente (exemplo com parâmetros fixos)
                        boolean clienteCadastrado = service.cadastrarCliente("Cliente Teste", "12345678901", "Endereço Teste", "123456789", "cliente@teste.com", 1);
                        System.out.println("Cliente " + (clienteCadastrado ? "cadastrado" : "falhou"));
                        break;

                    case 0:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } while (opcao != 0);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
