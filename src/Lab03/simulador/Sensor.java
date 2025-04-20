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

    public boolean dentroDoRaio(int posX, int posY) {
        // Deltas sao a distancia do sensor (ou seja, do robo ao qual o sensor esta atribuido) ate a posicao passada
        int deltaX = posX - posicaoX;
        int deltaY = posY - posicaoY;

        if (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) <= raio)
            return true;
        else
            return false;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public void setX(int posX) {
        posicaoX = posX;
    }

    public void setY(int posY) {
        posicaoY = posY;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public double getRaio() {
        return raio;
    }

    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }

}

