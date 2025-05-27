package simulador;

public class ErroComunicacaoException extends Exception {
    public ErroComunicacaoException (String id) {
        super(String.format("Robô %s nao eh comunicável, não pode receber uma mensagem.", id));
    }
}
