package simulador;

import java.util.ArrayList;
import java.lang.Math;

public class Ambiente {
    private final int largura;
    private final int profundidade;
    private final int altura;
    public CentralComunicacao central;
    public ArrayList<Entidade> entidades;
    public ArrayList<Robo> robosAtivos;
    public ArrayList<Obstaculo> obstaculos;
    public TipoEntidade[][][] mapa;
    public double[][] temperaturas;
    
    public Ambiente(int largura, int profundidade, int altura, int qntdObstaculo, CentralComunicacao central) {
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
        this.central = central;
        robosAtivos = new ArrayList<Robo>();
        obstaculos = new ArrayList<Obstaculo>();
        entidades = new ArrayList<Entidade>();
        gradienteTemperatura();
    }

    /**
     * Cria um 'gradiente' de temperatura para o Ambiente, atribuindo a um ponto aleatorio um valor de temperatura maxima que
     * vai se reduzindo conforme se afasta desse ponto, seguindo uma funcao Gaussiana
     */
    public void gradienteTemperatura() {
        temperaturas = new double[getLargura() + 1][getProfundidade() + 1];
        double tempMax = (Math.random() * 100);        // Gera uma temperatura aleatoria para ser o maximo do ambiente
        int posX = (int)(Math.random() * getLargura());     // Gera coordenadas aleatorias para terem essa temperatura maxima
        int posY = (int)(Math.random() * getProfundidade());

        for (int i = 0; i <= getLargura(); i++) {
            for (int j = 0; j <= getProfundidade(); j++) {
                temperaturas[i][j] = gradienteGaussiano(tempMax, posX, posY, i, j, getLargura()/2, 2*getProfundidade());
            }
        }
    }

    /**
     * Usa a funcao gaussiana de duas dimensoes para gerar uma especie de gradiente dado um ponto central e uma amplitude para a variacao,
     * formando uma elipse que vai diminuindo de valor a partir do centro
     * @param amplitude valor maximo que a funcao pode ter
     * @param centroX coordenada x do centro 
     * @param centroY coordenada y do centro
     * @param x posicao x qualquer
     * @param y posicao y qualquer  
     * @param horizontal o quao 'rapido' ela vai diminuir na horizontal
     * @param vertical o quao 'rapido' ela vai diminuir na vertical
     * @return resultado da formula, que deve ser um numero menor que a amplitude
     */
    public double gradienteGaussiano(double amplitude, int centroX, int centroY, int x, int y, int horizontal, int vertical) {
        return amplitude * (Math.pow(Math.E, -((Math.pow(Math.abs(x - centroX), 2))/(2 * Math.pow(horizontal, 2)) + 
        (Math.pow(Math.abs(y - centroY), 2))/(2 * Math.pow(vertical, 2)))));
    }

    public void adicionarRobo(Robo r) {
        robosAtivos.add(r);
        adicionarEntidade(r);
    }

    public void removerRobo(Robo r) {
        for (int i = 0; i < robosAtivos.size(); i++) {
            if (robosAtivos.get(i) == r)
                robosAtivos.remove(i);
        }
    }

    public void adicionarObstaculos(Obstaculo o) {
        obstaculos.add(o);
        adicionarEntidade(o);
    }

    public void removerObstaculo(Obstaculo o) {
        for (int i = 0; i < obstaculos.size(); i++) {
            if (obstaculos.get(i) == o)
                obstaculos.remove(i);
        }
    }

    public void adicionarEntidade(Entidade e) {
        entidades.add(e);
    }

    public void removerEntidade(Entidade e) {
        for (int i = 0; i < entidades.size(); i++) {
            if (entidades.get(i) == e)
                entidades.remove(i);
        }
    }

    public void listarRobos() {
        System.out.println("");
        System.out.println(" ID ICONE            TIPO     NOME        POSICAO            STATUS");
        for (Robo robo:robosAtivos) {
            System.out.printf("%s  %c  aka%15s %-7s em  (%02d, %02d, %02d)  esta  %s\n", robo.getID(), robo.getRepresentacao(), 
            robo.getClass().getSimpleName(), robo.getNome(), robo.getX(), robo.getY(), robo.getZ(), robo.getEstado());
        }
    }

    /**
     * Checa se as coordenadas de um ponto estão contidas na região definida do ambiente
     * @param x valor da coordenada horizontal
     * @param y valor da coordenada vertical
     * @return true ou false dependendo se esta ou nao dentro do ambiente
     */
    public boolean dentroDosLimites(int x, int y) {
        return (x >= 0 && x <= profundidade) && (y >= 0 && y <= largura);
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
        return (x >= 0 && x <= profundidade) && (y >= 0 && y <= largura) && (z >= 0 && z <= altMax);
    }

    /**
     * Previamente 'ehObstaculo'. Checa se as coordenadas do ponto dado ja estao ocupadas
     * por alguma entidade valida (Robo ou Obstaculo)
     * @param x coordenada x da posicao procurada
     * @param y coordenada y da posicao procurada
     * @param z coordenada z da posicao procurada
     * @return true ou false dependendo se a posicao esta ou nao ocupada
     */
    public boolean estaOcupado(int x, int y, int z) {
        for (int i = 0; i < obstaculos.size(); i++) {
            Obstaculo obs = obstaculos.get(i);

            if (obs.getPosicaoX1() <= x && x <= obs.getPosicaoX2() &&
                obs.getPosicaoY1() <= y && y <= obs.getPosicaoY2()) {
                    return true;
                }
        }
        return false;
    }

    // Exibe o ambiente, considerando '.' como espacos vazios, '#' como obstaculos e letras como os robos
    public void visualizarAmbiente() {
        char [][] matrizAmbiente = new char[getProfundidade() + 1][getLargura() + 1];

        // Loop para preencher a matrizAmbiente representativa do ambiente com espacos vazio '.' 
        for (int a = 0; a <= getProfundidade(); a++) 
            for (int b = 0; b <= getLargura(); b++)
                matrizAmbiente[a][b] = '.';

        // Loop para mudar as posicoes com obstaculos para '#'
        for (Obstaculo obs:obstaculos)
            for (int c = obs.getPosicaoX1(); c <= obs.getPosicaoX2(); c++)
                for (int d = obs.getPosicaoY1(); d <= obs.getPosicaoY2(); d++)
                    matrizAmbiente[c][d] = obs.getRepresentacao();

        // Loop para mudar as posicoes com robos para suas respectivas letras
        for (Robo robo:robosAtivos) {
            matrizAmbiente[robo.getX()][robo.getY()] = robo.getRepresentacao();
        }

        System.out.println("");
        // Loop para efetivamente imprimir a matrizAmbiente
        for (int e = getLargura(); e >= 0; e--) {
            for (int f = 0; f <= getProfundidade(); f++)
                System.out.printf("%c ", matrizAmbiente[f][e]);
            System.out.print("\n");
        }

        System.out.println("Legenda: . = vazio    # = obstaculo    T = robo terrestre    X = robo xadrez    G = robo preguica");
        System.out.println("                A = robo aereo    P = robo planador    S = robo satelite");
    }

    public int getLargura() {
        return largura;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public int getAltura() {
        return altura;
    }

    public double[][] getTemperaturas() {
        return temperaturas;
    }
}
