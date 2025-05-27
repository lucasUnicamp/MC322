package simulador;

public class SemObstaculoDestrutivelException extends Exception{
    public SemObstaculoDestrutivelException(int x, int y){
        super(String.format("Sem obstáculo para destruir em (%d, %d).\n", x, y));
    }
}
