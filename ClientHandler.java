package Redes_comunicacao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private CopyOnWriteArrayList<ClientHandler> clientes;
    private GerenciadorMensagem gerenciador;
    
    public ClientHandler(Socket socket, CopyOnWriteArrayList<ClientHandler> clientes, GerenciadorMensagem gerenciador) throws IOException {
        this.socket = socket;
        this.clientes = clientes;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.gerenciador = gerenciador;
    
    }
    
    @Override
    public void run() {
        // Adiciona à lista, depois de tudo inicializado
        clientes.add(this);
        gerenciador.enviarHistorico(this);
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            
            String mensagem;
            while ((mensagem = in.readLine()) != null) {
                // Adiciona ao histórico
                gerenciador.adicionarMensagem(mensagem);
                // distribui pra todos
                broadcast(mensagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientes.remove(this);  // saiu do ar — remove da lista
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(String mensagem) {
        for (ClientHandler cliente : clientes) {
            cliente.enviarMensagem(mensagem);
        }
    }
    
    public void enviarMensagem(String mensagem) {
        // método público para os outros handlers chamarem
        out.println(mensagem);
    }
}
    