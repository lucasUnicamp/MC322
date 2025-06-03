package simulador;

import java.util.ArrayList;

import simulador.excecoes.ErroComunicacaoException;
import simulador.interfaces.Comunicavel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CentralComunicacao {
    public ArrayList<String> mensagens;
    public ArrayList<Comunicavel> comunicaveis;

    public CentralComunicacao(){
        mensagens = new ArrayList<String>();
        comunicaveis = new ArrayList<Comunicavel>();
    }

    /**
     * Armazena a mensagem enviada entre dois robôs, bem como quem a mandou, quem a recebeu e quando essa foi enviada
     * @param remetente Robô que enviou a mensagem
     * @param destinatario Robô que recebeu a mensagem
     * @param msg mensagem de texto
     */
    public void registrarMensagem(String remetente, String destinatario, String msg) {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = time.format(format);

        mensagens.add(String.format("(%s) %s enviou para %s: %s", formattedTime, remetente, destinatario, msg));
    }

    /**
     * Printa para o usuário as mensagens todas as mensagens trocadas entre robô no ambiente
     */
    public void exibirMensagens() {
        if (mensagens == null || mensagens.size() == 0) {
            System.out.println("\nNão há mensagens na central. Volte mais tarde.");
        }
        else {
            System.out.println("\nMensagens armazenadas na central:");
            for (String msg: mensagens){
                System.out.println(msg);
            }
        }
    }

    // Adiciona um (robo) comunicavel numa lista 
    public void adicionarComunicavel(Comunicavel com) {
        comunicaveis.add(com);
    }

    /** 
     * Checa se o robo destinatário da mensagem tem a interface comunicavel, se não tiver, joga um erro
     * @param destinatario Robô que recebe a mensagem  
     */
    public boolean checarDestinatario(Robo destinatario) throws ErroComunicacaoException {
        for (Comunicavel com: comunicaveis) {
            if (com == destinatario)
                return true;
        }
        throw new ErroComunicacaoException(destinatario.getID());
    }
}
