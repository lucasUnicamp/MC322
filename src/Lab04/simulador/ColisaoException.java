package simulador;

public class ColisaoException extends Exception {
    public ColisaoException (String id, int x, int y, int z) {
        super(String.format("Robo %s sofreu uma colisao em (%d, %d, %d).", id, x, y, z));
    }
}