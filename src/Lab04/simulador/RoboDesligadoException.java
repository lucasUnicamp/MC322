package simulador;

public class RoboDesligadoException extends Exception {
    public RoboDesligadoException () {
        super("Robô está deligado, não pode executar essa ação");
    }
}
