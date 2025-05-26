package simulador;

public class MovimentoXadrezInvalidoException extends Exception {
    public MovimentoXadrezInvalidoException (String id) {
        super(String.format("Segundo as regras do xadrez, %s nao pode se movimentar assim.", id));
    }
}