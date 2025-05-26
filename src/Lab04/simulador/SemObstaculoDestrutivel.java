package simulador;

public class SemObstaculoDestrutivel extends Exception{
    public SemObstaculoDestrutivel(int x, int y){
        super(String.format("Sem obstáculo para destruir em (%d, %d)", x, y));
    }
}
