package simulador;

public class ErroComunicacaoException extends Exception {
    public ErroComunicacaoException (String id) {
        super(String.format("Robo %s nao eh comunicavel, nao pode receber uma mensagem.", id));
    }
}
