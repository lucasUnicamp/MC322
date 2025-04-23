package simulador;

public class SensorObstaculo extends Sensor {

    public SensorObstaculo(double raio, Ambiente ambiente) {
        super(raio, ambiente);
        setTipo(1);
    }

    public int monitorar(int posX, int posY) {
        if (!ambiente.dentroDosLimites(posX, posY))
            return 2;       // Fora do ambiente
        else if (!dentroDoRaio(posX, posY))
            return 3;       // Fora do alcance
        else {
            for (Obstaculo obstaculo:ambiente.obstaculos) {
                if (obstaculo.getPosicaoX1() <= posX &&
                    obstaculo.getPosicaoX2() >= posX &&
                    obstaculo.getPosicaoY1() <= posY &&
                    obstaculo.getPosicaoY2() >= posY ) {
                    return 1;       // Obstaculo detectado
                }
            }
            return 0;       // Obstaculo não detectado
        }
    }

    public boolean checarObstaculoCaminho(int x1, int y1, int x2, int y2) {
        
        return false;
    }
    
}
