package simulador;

import java.lang.Math;

public class SensorTemp extends Sensor {

    public SensorTemp(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }

    @Override
    public int monitorar(int posicaoX, int posicaoY) {
        // Deltas sao a distancia do sensor (ou seja, do robo ao qual o sensor esta atribuido) ate a posicao passada
        int deltaX = posicaoX - getX();
        int deltaY = posicaoY - getY();

        if (!ambiente.dentroDosLimites(posicaoX, posicaoY)) {
            System.out.println("Fora dos limites do ambiente.\n");
            return 1;       // Fora do ambiente
        }
        else if (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) > getRaio()) {
            System.out.println("Fora do alcance do sensor.\n");
            return 2;       // Fora do alcance
        }
        else {
            return 1;       // Posicao valida para monitoramento
        }
    }

    public void temperaturaPonto(int posX, int posY) {
        if (monitorar(posX, posY) == 1) {
            System.out.printf("Temperatura no ponto (%d, %d) é de %.1f°C.\n", posX, posY, ambiente.temperaturas[posX][posY]);
        }
    }
    
}
