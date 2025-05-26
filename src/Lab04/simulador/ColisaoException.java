package simulador;

public class ColisaoException extends Exception {
    public ColisaoException (String id) {
        super(String.format("Robo %s sofreu uma colisao.", id));
    }
}