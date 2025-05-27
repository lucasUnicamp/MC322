package simulador;

public class SemObstaculoDestrutivelException extends Exception{
    public SemObstaculoDestrutivelException(int x, int y){
        super(String.format("Sem obstaculo para destruir em (%d, %d)", x, y));
    }
}
