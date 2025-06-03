package simulador.excecoes;

public class RoboDesligadoException extends Exception {
    public RoboDesligadoException (String id) {
        super(String.format("Robô %s está desligado, não pode executar essa ação.", id));
    }
}
