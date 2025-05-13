package simulador;

public interface Comunicavel {
    void enviarMensagem(Comunicavel destinatario, String mensagem);
    void receberMensagem(String mensagem);
}
