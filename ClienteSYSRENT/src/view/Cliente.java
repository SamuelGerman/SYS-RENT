package view;

import java.net.MalformedURLException;
import model.Carro;
import model.Usuario;
import model.LocadoraService;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Cliente {

    private final LocadoraService locadoraService;
    private Usuario usuarioAtual;

    public Cliente(LocadoraService locadoraService) {
        this.locadoraService = locadoraService;
    }

    public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException {
        // Aqui você deve inicializar o LocadoraService e criar uma instância de Cliente
        LocadoraService Service = (LocadoraService) Naming.lookup("rmi://localhost:1099/LocadoraService");
        Cliente cliente = new Cliente(Service);

        try {
            cliente.iniciar();
        } catch (RemoteException e) {
            System.err.println("Erro ao iniciar o cliente: " + e.getMessage());
        }
    }

    public void iniciar() throws RemoteException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Bem-vindo à Locadora ===");
            System.out.println("1. Login");
            System.out.println("2. Registro");
            System.out.println("0. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1 ->
                    realizarLogin(scanner);
                case 2 ->
                    realizarRegistro(scanner);
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default ->
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void realizarLogin(Scanner scanner) {
        System.out.print("Digite o login: ");
        String login = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        try {
            if (locadoraService.autenticarUsuario(login, senha)) {
                usuarioAtual = locadoraService.buscarUsuarioPorLogin(login);
                System.out.println("Login realizado com sucesso!");
                apresentarMenuPorPapel(scanner);
            } else {
                System.out.println("Credenciais inválidas. Tente novamente.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
        }
    }

    private void realizarRegistro(Scanner scanner) throws RemoteException {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF/CNPJ: ");
        String cpfCnpj = scanner.nextLine();
        System.out.print("Digite o endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Digite o e-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite o login: ");
        String login = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        int idUsuario = locadoraService.cadastrarUsuario(login, senha, "cliente");
        boolean sucesso = locadoraService.cadastrarCliente(nome, cpfCnpj, endereco, telefone, email, idUsuario);
        if (sucesso) {
            System.out.println("Registro realizado com sucesso! Faça login para continuar.");
        } else {
            System.out.println("Erro ao registrar usuário.");
        }
    }

    private void apresentarMenuPorPapel(Scanner scanner) {
        String papel = usuarioAtual.getPapel();

        switch (papel) {
            case "administrador" ->
                menuAdministrador(scanner);
            case "operador" ->
                menuOperador(scanner);
            case "cliente" ->
                menuCliente(scanner);
            default ->
                System.out.println("Papel de usuário desconhecido.");
        }
    }

    private void menuAdministrador(Scanner scanner) {
        while (true) {
            System.out.println("=== Menu Administrador ===");
            System.out.println("1. Exibir relatórios de vendas");
            System.out.println("2. Exibir relatórios de locações");
            System.out.println("3. Listar veículos com manutenção pendente");
            System.out.println("4. Registrar manutenção");
            System.out.println("0. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1 ->
                        locadoraService.exibirRelatorioVendas();
                    case 2 ->
                        locadoraService.exibirRelatorioLocacoes();
                    case 3 ->
                        listarVeiculosManutencao();
                    case 4 ->
                        registrarManutencao(scanner);
                    case 0 -> {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    }
                    default ->
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private void menuOperador(Scanner scanner) {
        while (true) {
            System.out.println("=== Menu Operador ===");
            System.out.println("1. Listar veículos pendentes de vistoria");
            System.out.println("2. Registrar vistoria");
            System.out.println("3. Registrar venda");
            System.out.println("0. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1 ->
                        listarVeiculosPendentesVistoria();
                    case 2 ->
                        registrarVistoria(scanner);
                    case 3 ->
                        registrarVenda(scanner);
                    case 0 -> {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    }
                    default ->
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private void menuCliente(Scanner scanner) {
        while (true) {
            System.out.println("=== Menu Cliente ===");
            System.out.println("1. Listar veículos disponíveis para locação");
            System.out.println("2. Reservar veículo");
            System.out.println("3. Devolver veículo");
            System.out.println("4. Listar veículos à venda");
            System.out.println("0. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1 ->
                        listarVeiculosDisponiveis();
                    case 2 ->
                        reservarVeiculo(scanner);
                    case 3 ->
                        devolverVeiculo(scanner);
                    case 4 ->
                        listarCarrosVenda();
                    case 0 -> {
                        System.out.println("Voltando ao menu principal...");
                        return;
                    }
                    default ->
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private void listarVeiculosDisponiveis() throws RemoteException {
        List<Carro> carros = locadoraService.listarVeiculosDisponiveisParaLocacao();
        if (carros.isEmpty()) {
            System.out.println("Não há veículos disponíveis para locação.");
        } else {
            carros.forEach(System.out::println);
        }
    }

    private void listarCarrosVenda() throws RemoteException {
        List<Carro> carros = locadoraService.listarCarrosVenda();
        if (carros.isEmpty()) {
            System.out.println("Não há veículos disponíveis para venda.");
        } else {
            carros.forEach(System.out::println);
        }
    }

    private void reservarVeiculo(Scanner scanner) throws RemoteException {
        System.out.print("Digite o ID do veículo a reservar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        // Verificar se o veículo existe e está disponível
        Carro carro = locadoraService.buscarCarroPorId(id);
        if (carro == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        if (!"disponível".equals(carro.getStatus())) {
            System.out.println("Veículo não está disponível para reserva.");
            return;
        }

        if (locadoraService.reservarVeiculo(id)) {
            System.out.println("Veículo reservado com sucesso.");
        } else {
            System.out.println("Erro ao reservar veículo.");
        }
    }

    private void devolverVeiculo(Scanner scanner) throws RemoteException {
        System.out.print("Digite o ID do veículo a devolver: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (locadoraService.devolverVeiculo(id)) {
            System.out.println("Veículo devolvido com sucesso.");
        } else {
            System.out.println("Erro ao devolver veículo.");
        }
    }

    private void listarVeiculosManutencao() throws RemoteException {
        List<Carro> carros = locadoraService.listarVeiculosComManutencaoPendente();
        if (carros.isEmpty()) {
            System.out.println("Não há veículos com manutenção pendente.");
        } else {
            carros.forEach(System.out::println);
        }
    }

    private void registrarManutencao(Scanner scanner) throws RemoteException {
        System.out.print("Digite o ID do veículo para manutenção: ");
        int idCarro = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o tipo de manutenção: ");
        String tipo = scanner.nextLine();
        System.out.print("Digite o custo da manutenção: ");
        double custo = scanner.nextDouble();
        scanner.nextLine();

        if (locadoraService.registrarManutencao(idCarro, usuarioAtual.getId(), tipo, custo)) {
            System.out.println("Manutenção registrada com sucesso.");
        } else {
            System.out.println("Erro ao registrar manutenção.");
        }
    }

    private void listarVeiculosPendentesVistoria() throws RemoteException {
        List<Carro> carros = locadoraService.listarVeiculosPendentesVistoria();
        if (carros.isEmpty()) {
            System.out.println("Não há veículos pendentes de vistoria.");
        } else {
            carros.forEach(System.out::println);
        }
    }

    private void registrarVistoria(Scanner scanner) throws RemoteException {
        System.out.print("Digite o ID do veículo para vistoria: ");
        int idCarro = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        System.out.print("Digite o ID da locação associada à vistoria: ");
        int idLocacao = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        System.out.print("Digite o estado da vistoria (aprovado/manutenção): ");
        String estado = scanner.nextLine();

        // Verificar se o estado é válido
        if (!estado.equalsIgnoreCase("aprovado") && !estado.equalsIgnoreCase("manutenção")) {
            System.out.println("Estado inválido. Use 'aprovado' ou 'manutenção'.");
            return;
        }

        // Chamar o método do serviço para registrar a vistoria
        try {
            if (locadoraService.registrarVistoria(idCarro, idLocacao, usuarioAtual.getId(), estado)) {
                System.out.println("Vistoria registrada com sucesso.");
            } else {
                System.out.println("Erro ao registrar vistoria.");
            }
        } catch (RemoteException e) {
            System.err.println("Erro ao registrar vistoria: " + e.getMessage());
        }
    }

    private void registrarVenda(Scanner scanner) throws RemoteException {
        System.out.print("Digite o ID do cliente: ");
        int idCliente = scanner.nextInt();
        System.out.print("Digite o ID do veículo vendido: ");
        int idCarro = scanner.nextInt();
        System.out.print("Digite o valor da venda: ");
        double valorVenda = scanner.nextDouble();
        scanner.nextLine();

        if (locadoraService.registrarVenda(idCliente, usuarioAtual.getId(), idCarro, valorVenda)) {
            System.out.println("Venda registrada com sucesso.");
        } else {
            System.out.println("Erro ao registrar venda.");
        }
    }
}
