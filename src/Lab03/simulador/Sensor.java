package simulador;

import java.lang.Math;

public class Sensor {
    private double raio;
    private int posicaoX;
    private int posicaoY;
    Ambiente ambiente;

    public Sensor(double raio, Ambiente ambiente) {
        setRaio(raio);
        setAmbiente(ambiente);
    }  

    public int monitorar(int posicaoX, int posicaoY) {
        // Deltas sao a distancia do sensor (ou seja, do robo ao qual o sensor esta atribuido) ate a posicao passada
        int deltaX = posicaoX - this.posicaoX;
        int deltaY = posicaoY - this.posicaoY;

        // Usa teorema de pitagoras para calcular a distancia em linha reta e compara com o raio do sensor
        if (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) > raio) {
            System.out.println("Fora do alcance do sensor.\n");
            return 2;       // Fora do alcance
        }
        else {
            for (Obstaculo obstaculo:ambiente.obstaculos) {
                if (obstaculo.getPosicaoX1() <= posicaoX &&
                    obstaculo.getPosicaoX2() >= posicaoX &&
                    obstaculo.getPosicaoY1() <= posicaoY &&
                    obstaculo.getPosicaoY2() >= posicaoY ) {
                    return 1;       // Obstáculo detectado
                }
            }
            return 0;       // Obstáculo não detectado
        }
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public void setX(int posicaoX){
        this.posicaoX = posicaoX;
    }

    public void setY(int posicaoY){
        this.posicaoY = posicaoY;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

}

