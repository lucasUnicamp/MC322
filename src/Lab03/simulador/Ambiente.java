package simulador;

import java.util.ArrayList;
import java.lang.Math;

public class Ambiente {
    private int largura;
    private int altura;
    public ArrayList<Robo> robosAtivos;
    public ArrayList<Obstaculo> obstaculos;
    public double[][] temperaturas;
    
    public Ambiente(int largura, int altura, int qntdObstaculo) {
        this.largura = largura;
        this.altura = altura;
        this.robosAtivos = new ArrayList<Robo>();
        obstaculos = new ArrayList<Obstaculo>();
        gradienteTemperatura();
    }

    public void gradienteTemperatura() {
        temperaturas = new double[getLargura()][getAltura()];
        double tempMax = (Math.random() * 36);        // Gera uma temperatura aleatoria para ser o maximo do ambiente
        int posX = (int)(Math.random() * getLargura());
        int posY = (int)(Math.random() * getAltura());

        for (int i = 0; i < getLargura(); i++) {
            for (int j = 0; j < getAltura(); j++) {
                temperaturas[i][j] = gradienteGaussiano(tempMax, posX, posY, i, j, getLargura()/2, 2*getAltura());
            }
        }

        System.out.printf("%.1f, %d, %d\n", tempMax, posX, posY);
        for (int i = 0; i < getLargura(); i++) {
            for (int j = 0; j < getAltura(); j++) {
                System.out.printf("%03.1f ", temperaturas[i][j]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * Usa a funcao gaussiana de duas dimensoes para gerar uma especie de gradiente dado um ponto central e uma amplitude para a variacao
     * @param amplitude
     * @param centroX
     * @param centroY
     * @param x
     * @param y
     * @param horizontal
     * @param vertical
     * @return
     */
    public double gradienteGaussiano(double amplitude, int centroX, int centroY, int x, int y, int horizontal, int vertical) {
        return amplitude * (Math.pow(Math.E, -((Math.pow(Math.abs(x - centroX), 2))/(2 * Math.pow(horizontal, 2)) + 
        (Math.pow(Math.abs(y - centroY), 2))/(2 * Math.pow(vertical, 2)))));
    }

    public void adicionarRobo(Robo r) {
        robosAtivos.add(r);
    }

    public void removerRobo(Robo r) {
        for (int i = 0; i < robosAtivos.size(); i++) {
            if (robosAtivos.get(i) == r)
                robosAtivos.remove(i);
        }
    }

    public void adicionarObstaculos(Obstaculo obstaculo) {
        obstaculos.add(obstaculo);
    }

    /**
     * Checa se as coordenadas de um ponto estão contidas na região definida do ambiente
     * @param x valor da coordenada horizontal
     * @param y valor da coordenada vertical
     * @return true ou false dependendo se esta ou nao dentro do ambiente
     */
    public boolean dentroDosLimites(int x, int y) {
        return (x >= 0 && x <= altura) && (y >= 0 && y <= largura);
    }

    /**
     * Checa se as coordenadas de um robo aereo estão contidas na região definida do ambiente, considerando tambem a altitude
     * @param robo objeto da classe robo que esta dentro do ambiente executando movimentos
     * @return true ou false dependendo se esta ou nao dentro do ambiente
     */
    public boolean dentroDosLimites(RoboAereo robo) {
        int x = robo.getX();
        int y = robo.getY();
        int z = robo.getAltitude();
        int altMax = robo.getAltitudeMax();
        return (x >= 0 && x <= altura) && (y >= 0 && y <= largura) && (z >= 0 && z <= altMax);
    }

    /**
     * Checa se as coordenadas do ponto dado estao no interior do obstaculo retangular
     * @param x coordenada x da posicao procurada
     * @param y coordenada y da posicao procurada
     * @return true ou false dependendo se o ponto esta dentro de um obstaculo
     */
    public boolean ehObstaculo(int x, int y) {
        for (int i = 0; i < obstaculos.size(); i++) {
            Obstaculo obs = obstaculos.get(i);
            // Reorganiza os pontos para serem um com as menores coordenadas e outro com as maiores para facilitar o cheque em seguida
            int menorPosX = obs.getPosicaoX1() > obs.getPosicaoX2() ? obs.getPosicaoX2() : obs.getPosicaoX1();
            int maiorPosX = obs.getPosicaoX1() > obs.getPosicaoX2() ? obs.getPosicaoX1() : obs.getPosicaoX2();
            int menorPosY = obs.getPosicaoY1() > obs.getPosicaoY2() ? obs.getPosicaoY2() : obs.getPosicaoY1();
            int maiorPosY = obs.getPosicaoY1() > obs.getPosicaoY2() ? obs.getPosicaoY1() : obs.getPosicaoY2();

            if (menorPosX <= x && x <= maiorPosX &&
                menorPosY <= y && y <= maiorPosY) {
                    return true;
                }
        }
        return false;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public double[][] getTemperaturas() {
        return temperaturas;
    }
}
