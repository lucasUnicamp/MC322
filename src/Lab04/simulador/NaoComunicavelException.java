package simulador;

public class NaoComunicavelException extends Exception {
    public NaoComunicavelException (String id) {
        super(String.format("Robo %s nao eh comunicavel, nao pode receber uma mensagem.", id));
    }
}
