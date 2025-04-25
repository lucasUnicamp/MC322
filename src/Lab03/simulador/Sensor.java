package simulador;

import java.lang.Math;

abstract class Sensor {
    private double raio;
    private int posicaoX;
    private int posicaoY;
    private int altitude;
    private Ambiente ambiente;

    public Sensor(double raio, Ambiente ambiente) {
        setRaio(raio);
        setAmbiente(ambiente);
        altitude = 0; //padrão para caso robôs terrestres estejam usando o sensor
    }  

    public void info() {
        System.out.printf("raio: %.2f\n", getRaio());
    }

    public abstract int monitorar(int posX, int posY);

    public boolean dentroDoRaio(int posX, int posY) {
        // Deltas sao a distancia do sensor (ou seja, do robo ao qual o sensor esta atribuido) ate a posicao passada
        int deltaX = posX - posicaoX;
        int deltaY = posY - posicaoY;

        if (Math.pow(deltaX, 2) + Math.pow(deltaY, 2) <= Math.pow(raio, 2))
            return true;
        else
            return false;
    }

    public abstract String nomeDoSensor();

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public void setX(int posX) {
        posicaoX = posX;
    }

    public void setY(int posY) {
        posicaoY = posY;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
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

    public int getAltitude() {
        return altitude;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

}

