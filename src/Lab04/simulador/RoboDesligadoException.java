package simulador;

public class RoboDesligadoException extends Exception {
    public RoboDesligadoException (String id) {
        super(String.format("Robo %s esta deligado, nao pode executar essa açao.", id));
    }
}
