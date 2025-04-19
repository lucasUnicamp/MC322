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
        int deltaX = posicaoX - this.posicaoX;
        int deltaY = posicaoY - this.posicaoY;
        if(Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) > raio) {
            System.out.println("Fora do alcance do sensor.");
            return 2; // fora do alcance
        } else {
            for(Obstaculo obstaculo:ambiente.obstaculos) {
                if(obstaculo.getPosicaoX1() <= posicaoX &&
                obstaculo.getPosicaoX2() >= posicaoX &&
                obstaculo.getPosicaoY1() <= posicaoY &&
                obstaculo.getPosicaoY2() >= posicaoY ) {
                    return 1; // obstáculo detectado
                }
            }
            return 0; // obstáculo não detectado
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

