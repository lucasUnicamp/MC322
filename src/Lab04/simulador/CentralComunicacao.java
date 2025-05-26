package simulador;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CentralComunicacao {
    public ArrayList<String> mensagens;
    public ArrayList<Comunicavel> comunicaveis;

    public CentralComunicacao(){
        mensagens = new ArrayList<String>();
        comunicaveis = new ArrayList<Comunicavel>();
    }
    
    public void registrarMensagem(String remetente, String msg) {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = time.format(format);

        mensagens.add(String.format("(%s) %s enviou: %s", formattedTime, remetente, msg));
    }

    public void exibirMensagens() {
        if (mensagens == null || mensagens.size() == 0) {
            System.out.println("\nNao ha mensagens na central. Volte mais tarde.");
        }
        else {
            System.out.println("Mensagens armazenadas na central:");
            for (String msg: mensagens){
                System.out.println(msg);
            }
        }
    }

    public void adicionarComunicavel(Comunicavel com) {
        comunicaveis.add(com);
    }

    public boolean checarDestinatario(Robo destinatario) throws ErroComunicacaoException {
        for (Comunicavel com: comunicaveis) {
            if (com == destinatario)
                return true;
        }
        throw new ErroComunicacaoException(destinatario.getID());
    }

    public Comunicavel getComunicavel(Robo robo) {
        for (Comunicavel com: comunicaveis) {
            if (com == robo)
                return com;
        }
        return null;
    }
}
