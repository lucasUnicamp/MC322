package simulador;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CentralComunicacao {
    public ArrayList<String> mensagens;

    public CentralComunicacao(){
        mensagens = new ArrayList<String>();
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
            for(String msg: mensagens){
                System.out.println(msg);
            }
        }
    }
}
