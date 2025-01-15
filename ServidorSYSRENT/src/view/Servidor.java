package view;

import controller.LocadoraServiceImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import model.LocadoraService;

public class Servidor {
    public static void main(String[] args) {
        try {
            LocadoraService service = new LocadoraServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LocadoraService", service);
            System.out.println("Servidor RMI em execução...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
