package simulador;

public class RoboDesligadoException extends Exception {
    public RoboDesligadoException (String id) {
        super(String.format("Robô %s está deligado, não pode executar essa ação", id));
    }
}
