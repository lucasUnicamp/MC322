package simulador;

public class SensorObstaculo extends Sensor {
    
    public SensorObstaculo(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }

    public int monitorar(int posX, int posY) {
        if (!ambiente.dentroDosLimites(posX, posY)) {
            System.out.println("Fora dos limites do ambiente.\n");
            return 2;       // Fora do ambiente
        }
        // Usa teorema de pitagoras para calcular a distancia em linha reta e compara com o raio do sensor
        else if (!dentroDoRaio(posX, posY)) {
            System.out.println("Fora do alcance do sensor.\n");
            return 3;       // Fora do alcance
        }
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
    
}
