package simulador;

import java.util.ArrayList;

public class CentralComunicacao {
    public ArrayList<String> mensagens;

    public void registrarMensagem(String remetente, String msg) {
        mensagens.add(String.format("%s: %s", remetente, msg));
    }

    public void exibirMensagens() {
        for(String msg : mensagens){
            System.out.println(msg);
        }
    }
}
