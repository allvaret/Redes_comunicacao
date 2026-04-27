package Redes_comunicacao;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServidorChat {
    public static void main(String[] args){
        // Criação do servidor
        ServerSocket servidor = null;
        try {
            servidor = new ServerSocket(12345);
            System.out.println("Servidor iniciado. Aguardando conexões...");
            CopyOnWriteArrayList<ClientHandler> Lista_clientes = new CopyOnWriteArrayList<>();        
            GerenciadorMensagem gerenciador = new GerenciadorMensagem();


            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());
                ClientHandler handler = new ClientHandler(cliente, Lista_clientes, gerenciador); 
                new Thread(handler).start();
            }
        } 
        catch (IOException e) {
                e.printStackTrace();
        }
    }
}