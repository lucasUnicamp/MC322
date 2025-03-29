package simulador;

import java.lang.Math;

public class RoboSatelite extends RoboAereo {
    private double angulo;
    private double raioArea;

    public RoboSatelite(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, double angulo) {
        super(nome, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        setAngulo(angulo);
        setRaio();
    }

    @Override 
    public void subir(int metros) {
        super.subir(metros);
        setRaio();
        exibirRaio();
    }

    @Override
    public void info() {
        System.out.printf("Robô Satélite '%s' está na posição (%d, %d, %d) apontado na direção %s com altitude máxima permitida de %d e o ângulo do escaner é de %.1f°.\n\n"
        , getNome(), getX(),getY(), getAltitude(), getDirecao(), getAltitudeMax(), angulo);
    }

    public void escanear() {
        double distancia;

        for (int i = 0; i < getAmbiente().obstaculosLista.length; i++) {
            distancia = Math.sqrt(Math.pow(getAmbiente().obstaculosLista[i][0] - getX(), 2) + Math.pow(getAmbiente().obstaculosLista[i][1] - getY(), 2));
            if (distancia <= raioArea) {
                System.out.printf("Obstáculo escaneado em (%d, %d).\n", getAmbiente().obstaculosLista[i][0], getAmbiente().obstaculosLista[i][1]);
            }
        }
        System.out.printf("\n");
    }

    public void exibirRaio() {
        System.out.printf("O raio de alcance do escaner nessa altitude %d é de %.1f.\n\n", getAltitude(), raioArea);
    }

    public void setAngulo(double angulo) {
        if (angulo < 90)
            this.angulo = angulo;
        else
            this.angulo = 90;
    }

    public void setRaio() {
        raioArea = super.getAltitude() * Math.tan((Math.toRadians(angulo / 2)));
    }

    public double getAngulo() {
        return angulo;
    }

    public double getRaio() {
        return raioArea;
    }
}
