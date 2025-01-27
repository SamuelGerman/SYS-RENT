package view;

import java.rmi.Naming;
import java.util.Scanner;
import model.LocadoraService;

public class Cliente {

    public static void main(String[] args) {
        try {
            LocadoraService service = (LocadoraService) Naming.lookup("rmi://localhost:1099/LocadoraService");
            Scanner scanner = new Scanner(System.in);
            

            boolean autenticado = false;
            String papel = "";

            // Loop para autenticação
            while (!autenticado) {
                System.out.print("Login: ");
                String login = scanner.next();
                System.out.print("Senha: ");
                String senha = scanner.next();

                if (service.autenticarUsuario(login, senha)) {
                    System.out.println("Autenticação bem-sucedida!");
                    papel = service.obterPapelUsuario(login); // Obtém o papel do usuário
                    autenticado = true;
                } else {
                    System.out.println("Credenciais inválidas. Tente novamente.");
                }
            }

            // Exibe o menu com base no papel do usuário
            int opcao;
            do {
                exibirMenu(papel);
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();

                switch (papel) {
                    case "cliente":
                        menuCliente(service, opcao);
                        break;
                    case "operador":
                        menuOperador(service, opcao);
                        break;
                    default:
                        System.out.println("Papel desconhecido. Encerrando o programa.");
                        opcao = 0;
                        break;
                }
            } while (opcao != 0);

            System.out.println("Saindo...");
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para exibir o menu com base no papel
    private static void exibirMenu(String papel) {
        System.out.println("\nMenu de opções:");
        if ("cliente".equals(papel)) {
            System.out.println("1 - Alugar veículo");
            System.out.println("2 - Devolver veículo");
            System.out.println("3 - Vender veículo");
            System.out.println("0 - Sair");
        } else if ("operador".equals(papel) || "administrador".equals(papel)) {
            System.out.println("1 - Registrar manutenção");
            System.out.println("2 - Registrar vistoria");
            System.out.println("0 - Sair");
        }
    }

    // Menu de opções para cliente
    private static void menuCliente(LocadoraService service, int opcao) {
        try {
            switch (opcao) {
                case 1:
                    System.out.println("Alugando veículo...");
                    service.listarVeiculos();
                    service.reservarVeiculo(2); //exemplo
                    break;
                case 2:
                    System.out.println("Devolvendo veículo...");
                    service.devolverVeiculo(2);
                    break;
                case 3:
                    System.out.println("Vendendo veículo...");
                    service.venderVeiculo(3);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Menu de opções para operador
    private static void menuOperador(LocadoraService service, int opcao) {
        try {
            switch (opcao) {
                case 1:
                    System.out.println("Registrando manutenção...");
                    // Implementação da lógica para registrar manutenção
                    break;
                case 2:
                    System.out.println("Registrando vistoria...");
                    // Implementação da lógica para registrar vistoria
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
