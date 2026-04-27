package Redes_comunicacao;
import java.util.ArrayDeque;

public class GerenciadorMensagem {
    private static final int LIMITE = 50;
    private ArrayDeque<String> historico = new ArrayDeque<>();

    public void adicionarMensagem(String mensagem) {
        if (historico.size() >= LIMITE) {
        historico.poll(); // remove o mais antigo
        }
        historico.add(mensagem);
    }

    public void enviarHistorico(ClientHandler handler){
        for (String mensagem: historico ){
            handler.enviarMensagem(mensagem); 
            }
        }

}
