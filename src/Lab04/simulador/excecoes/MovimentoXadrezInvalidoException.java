package simulador.excecoes;

public class MovimentoXadrezInvalidoException extends Exception {
    public MovimentoXadrezInvalidoException (String id) {
        super(String.format("Segundo as regras do xadrez, %s não pode se movimentar assim.", id));
    }
}