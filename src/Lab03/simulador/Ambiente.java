package simulador;

import java.util.ArrayList;

public class Ambiente {
    private int largura;
    private int altura;
    public ArrayList<Robo> robosAtivos;
    public ArrayList<Obstaculo> obstaculos;

    // Gradiente de temperatura logo na criacao. Gerar aleatoriamente uma temperatura e uma posicao e ir diminuindo-a em todas as direcoes
    
    public Ambiente(int largura, int altura, int qntdObstaculo) {
        this.largura = largura;
        this.altura = altura;
        this.robosAtivos = new ArrayList<>();
        obstaculos = new ArrayList<>();
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

    public boolean dentroDosLimites(int x, int y) {
        return (x >= 0 && x <= altura) && (y >= 0 && y <= largura);
    }

    /**
     * Checa se as coordenadas de um robo aereo estão contidas na região definida do ambiente, considerando tambem a altitude
     * @param robo objeto da classe robo que esta dentro do ambiente executando movimentos
     * @return true ou false dependendo se esta ou não dentro do ambiente
     */
    public boolean dentroDosLimites(RoboAereo robo) {
        int x = robo.getX();
        int y = robo.getY();
        int z = robo.getAltitude();
        int altMax = robo.getAltitudeMax();
        return (x >= 0 && x <= altura) && (y >= 0 && y <= largura) && (z >= 0 && z <= altMax);
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}
