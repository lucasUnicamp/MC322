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
    public void descer(int metros) {
        super.descer(metros);
        setRaio();
        exibirRaio();
    }

    @Override
    public void info() {
        System.out.printf("Robo Satelite '%s' esta na posicao (%d, %d, %d) apontado na direcao %s com altitude maxima permitida de %d e o angulo do escaner eh de %.1f°.\n\n"
        , getNome(), getX(),getY(), getAltitude(), getDirecao(), getAltitudeMax(), angulo);
    }

    // O angulo definido para o escaner corresponde ao 'campo de visao' do Robo, que procura obstaculos abaixo dele e no interior
    // do circulo de raio dependente da altura e do angulo
    public void escanear() {
        double distancia;
        boolean temObstaculo = false;

        // Percorre a lista de coordenadas dos obstaculos e calcula se essas estao no interior do circulo de visao
        for (int i = 0; i < getAmbiente().obstaculosLista.length; i++) {
            // Usa formula da distancia entre dois pontos no plano
            distancia = Math.sqrt(Math.pow(getAmbiente().obstaculosLista[i][0] - getX(), 2) + Math.pow(getAmbiente().obstaculosLista[i][1] - getY(), 2));
            if (distancia <= raioArea) {
                System.out.printf("Obstaculo escaneado em (%d, %d).\n", getAmbiente().obstaculosLista[i][0], getAmbiente().obstaculosLista[i][1]);
                temObstaculo = true;
            }
        }
        if (!temObstaculo)
            System.out.printf("Nao ha obstaculos na area procurada.");
        System.out.printf("\n");
    }

    public void exibirRaio() {
        System.out.printf("O raio de alcance do escaner nessa altitude %d eh de %.1f.\n\n", getAltitude(), raioArea);
    }

    public void setAngulo(double angulo) {
        // Limite arbitrario do angulo do Robo
        if (angulo < 90)
            this.angulo = angulo;
        else
            this.angulo = 90;
        setRaio();
    }

    public void setRaio() {
        // Encontrado por trigonometria: tan = raioArea/altitude
        raioArea = super.getAltitude() * Math.tan((Math.toRadians(angulo / 2)));
    }

    public double getAngulo() {
        return angulo;
    }

    public double getRaio() {
        return raioArea;
    }
}
