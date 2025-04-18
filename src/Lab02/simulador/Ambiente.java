package simulador;

import java.util.ArrayList;

public class Ambiente {
    private int largura;
    private int altura;
    public ArrayList<Robo> robosAtivos;
    public boolean [][] obstaculosMatriz;
    public int [][] obstaculosLista;
    
    public Ambiente(int largura, int altura, int qntdObstaculo) {
        this.largura = largura;
        this.altura = altura;
        this.robosAtivos = new ArrayList<>();
        obstaculosMatriz = new boolean[largura][altura];
        obstaculosLista = new int[qntdObstaculo][2];
    }

    public void adicionarRobo(Robo r) {
        robosAtivos.add(r);
    }

    public void adicionarObstaculos(int [][] obstaculosNovos) {
        for (int i = 0; i < obstaculosNovos.length; i++){
            obstaculosMatriz[obstaculosNovos[i][0]][obstaculosNovos[i][1]] = true;
            obstaculosLista[i][0] = obstaculosNovos[i][0];
            obstaculosLista[i][1] = obstaculosNovos[i][1];
        }
    }

    public boolean dentroDosLimites(int x, int y) {
        return (x >= 0 && x <= altura) && (y >= 0 && y <= largura);
    }

    /**
     * Checa se as coordenadas de um robô aéreo estão contidas na região definida do ambiente, considerando também a altitude
     * @param robo objeto da classe robô que está dentro do ambiente executando movimentos
     * @return true ou false dependendo se está ou não dentro do ambiente
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
