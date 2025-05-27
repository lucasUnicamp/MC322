package simulador.excecoes;

public class ColisaoException extends Exception {
    public ColisaoException (String id, int x, int y, int z) {
        super(String.format("Robô %s sofreu uma colisão em (%d, %d, %d).", id, x, y, z));
    }
}