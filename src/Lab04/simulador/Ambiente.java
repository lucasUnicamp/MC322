package simulador;

import java.util.ArrayList;

import simulador.interfaces.Entidade;

import java.lang.Math;

public class Ambiente {
    private final int largura;
    private final int profundidade;
    private final int altura;
    private CentralComunicacao central;
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
        inicializarMapa();
        gradienteTemperatura();
    }

    private void inicializarMapa() {
        mapa = new TipoEntidade[getLargura() + 1][getProfundidade() + 1][getAltura() + 1];
        for(int i = 0; i <= getLargura(); i++)
            for(int j = 0; j <= getProfundidade(); j++)
                for(int w = 0; w <= getAltura(); w++)
                    mapa[i][j][w] = TipoEntidade.VAZIO;
    }

    /**
     * Cria um 'gradiente' de temperatura para o Ambiente, atribuindo a um ponto aleatório um valor de temperatura máxima que
     * vai se reduzindo conforme se afasta desse ponto, seguindo uma função Gaussiana
     */
    public void gradienteTemperatura() {
        temperaturas = new double[getLargura() + 1][getProfundidade() + 1];
        double tempMax = (Math.random() * 100);        // Gera uma temperatura alatória para ser o máximo do ambiente
        int posX = (int)(Math.random() * getLargura());     // Gera coordenadas aleatórias para terem essa temperatura máxima
        int posY = (int)(Math.random() * getProfundidade());

        for (int i = 0; i <= getLargura(); i++) {
            for (int j = 0; j <= getProfundidade(); j++) {
                temperaturas[i][j] = gradienteGaussiano(tempMax, posX, posY, i, j, getLargura()/2, 2*getProfundidade());
            }
        }
    }

    /**
     * Usa a função gaussiana de duas dimensões para gerar um gradiente dado um ponto central e uma amplitude para a variação,
     * formando uma elipse que vai diminuindo de valor a partir do centro
     * @param amplitude valor máximo que a função pode ter
     * @param centroX coordenada x do centro 
     * @param centroY coordenada y do centro
     * @param x posição x qualquer
     * @param y posição y qualquer  
     * @param horizontal o quão 'rápido' ela vai diminuir na horizontal
     * @param vertical o quão 'rápido' ela vai diminuir na vertical
     * @return resultado da fórmula, que deve ser um número menor que a amplitude
     */
    public double gradienteGaussiano(double amplitude, int centroX, int centroY, int x, int y, int horizontal, int vertical) {
        return amplitude * (Math.pow(Math.E, -((Math.pow(Math.abs(x - centroX), 2))/(2 * Math.pow(horizontal, 2)) + 
        (Math.pow(Math.abs(y - centroY), 2))/(2 * Math.pow(vertical, 2)))));
    }

    private void adicionarRobo(Robo r) {
        robosAtivos.add(r);
    }

    private void removerRobo(Robo r) {
        for (int i = 0; i < robosAtivos.size(); i++) {
            if (robosAtivos.get(i) == r)
                robosAtivos.remove(i);
        }
    }

    private void adicionarObstaculos(Obstaculo o) {
        obstaculos.add(o);
    }

    private void removerObstaculo(Obstaculo o) {
        for (int i = 0; i < obstaculos.size(); i++) {
            if (obstaculos.get(i) == o)
                obstaculos.remove(i);
        }
    }

    public void adicionarEntidade(Entidade e) {
        entidades.add(e);
        if (e.getTipo() == TipoEntidade.ROBO){
            adicionarRobo((Robo) e);
            if (dentroDosLimites(e.getX(), e.getY(), e.getZ()))
                mapa[e.getX()][e.getY()][e.getZ()] = TipoEntidade.ROBO;
        } else if (e.getTipo() == TipoEntidade.OBSTACULO){
            adicionarObstaculos((Obstaculo) e);
            for (int i = e.getX(); i < e.getX() + e.getLargura(); i++)
                for (int j = e.getY(); j < e.getY() + e.getProfundidade(); j++)
                    for (int w = e.getZ(); w < e.getZ() + e.getAltura(); w++)
                        if (dentroDosLimites(i, j, w))
                            mapa[i][j][w] = TipoEntidade.OBSTACULO;
        }
    }

    public void removerEntidade(Entidade e) {
        for (int i = 0; i < entidades.size(); i++) {
            if (entidades.get(i) == e)
                entidades.remove(i);
        }
        if (e.getTipo() == TipoEntidade.ROBO){
            removerRobo((Robo) e);
            mapa[e.getX()][e.getY()][e.getZ()] = TipoEntidade.VAZIO;
        } else if (e.getTipo() == TipoEntidade.OBSTACULO){
            removerObstaculo((Obstaculo) e);;
            for (int i = e.getX(); i < e.getX() + e.getLargura(); i++)
                for (int j = e.getY(); j < e.getY() + e.getProfundidade(); j++)
                    for (int w = e.getZ(); w < e.getZ() + e.getAltura(); w++)
                        if (dentroDosLimites(i, j, w))
                            mapa[i][j][w] = TipoEntidade.VAZIO;
        }
    }

    public void moverEntidade(Entidade e, int novoX, int novoY, int novoZ) {
        moverEntidadeMapa(e, novoX, novoY, novoZ);
        if(e.getTipo() == TipoEntidade.ROBO){
            ((Robo) e).setX(novoX);
            ((Robo) e).setY(novoY);
            ((Robo) e).setZ(novoZ);
        } else if (e.getTipo() == TipoEntidade.OBSTACULO) {
            int largura = ((Obstaculo) e).getLargura();
            int profundidade = ((Obstaculo) e).getProfundidade();
            ((Obstaculo) e).setPosicaoX1(novoX);
            ((Obstaculo) e).setPosicaoY1(novoY);

            ((Obstaculo) e).setPosicaoX2(novoX + largura);
            ((Obstaculo) e).setPosicaoY2(novoY + profundidade);
        }
    }

    // Move a entidade na matriz, mas não tem o poder de mudar a condição absoluta da entidade
    public void moverEntidadeMapa(Entidade e,  int novoX, int novoY, int novoZ) {
        if (e.getTipo() == TipoEntidade.ROBO){ // Remove o robo da matriz
            if (dentroDosLimites(e.getX(), e.getY(), e.getZ()))
                mapa[e.getX()][e.getY()][e.getZ()] = TipoEntidade.VAZIO;
        } else if (e.getTipo() == TipoEntidade.OBSTACULO) { // Remove o obstáculo da matriz
            for (int i = e.getX(); i < e.getX() + e.getLargura(); i++)
                for (int j = e.getY(); j < e.getY() + e.getProfundidade(); j++)
                    for (int w = e.getZ(); w < e.getZ() + e.getAltura(); w++)
                        if (dentroDosLimites(i, j, w))
                            mapa[i][j][w] = TipoEntidade.VAZIO;
        }

        if (e.getTipo() == TipoEntidade.ROBO){  // Readiciona o robo a matriz
            if (dentroDosLimites(novoX, novoY, novoZ))
                mapa[novoX][novoY][novoZ] = TipoEntidade.ROBO;
        } else if (e.getTipo() == TipoEntidade.OBSTACULO){  // Readiciona o obstáculo a matriz
            for (int i = novoX; i < novoX + e.getLargura(); i++)
                for (int j = novoY; j < novoY + e.getProfundidade(); j++)
                    for (int w = 0; w < e.getAltura(); w++)
                        if (dentroDosLimites(i, j, w))
                            mapa[i][j][w] = TipoEntidade.OBSTACULO;
        }
    }

    public void listarRobos() {
        System.out.println("");
        System.out.println(" ID ÍCONE            TIPO     NOME        POSIÇÃO            STATUS");
        for (Robo robo:robosAtivos) {
            System.out.printf("%s  %c  aka%15s %-7s em  (%02d, %02d, %02d)  está  %s\n", robo.getID(), robo.getRepresentacao(), 
            robo.getClass().getSimpleName(), robo.getNome(), robo.getX(), robo.getY(), robo.getZ(), robo.getEstado());
        }
    }

    // Liga todos os robôs desligados do Ambiente
    public void ligarRobos() {
        System.out.println("");
        for (Robo robo:robosAtivos) {
            if (!robo.estaLigado())
                robo.ligar();
        }
    }

    public Robo conferirNome(String nome) {
        for(Robo robo:robosAtivos) {
            if (nome.equals(robo.getNome()) || nome.equals(robo.getID()) || nome.equals(robo.getClass().getSimpleName()))
                return robo;
        }
        return null;
    }

    /**
     * Checa se as coordenadas de um ponto estão contidas na região definida do ambiente
     * @param x valor da coordenada horizontal
     * @param y valor da coordenada vertical
     * @return true ou false dependendo se está ou não dentro do ambiente
     */
    public boolean dentroDosLimites(int x, int y) {
        return (x >= 0 && x <= profundidade) && (y >= 0 && y <= largura);
    }

    /**
     * Checa se as coordenadas de um ponto estão contidas na região definida do ambiente
     * @param x valor da coordenada x
     * @param y valor da coordenada y
     * @param z valor da coordenada z
     * @return true ou false dependendo se está ou não dentro do ambiente
     */
    public boolean dentroDosLimites(int x, int y, int z) {
        return (x >= 0 && x <= profundidade) && (y >= 0 && y <= largura) && (z >= 0 && z <= getAltura());
    }

    /**
     * Checa se as coordenadas de um robô aéreo estão contidas na região definida do ambiente, considerando também a altitude
     * @param robo objeto da classe robô que esta dentro do ambiente executando movimentos
     * @return true ou false dependendo se está ou não dentro do ambiente
     */
    public boolean dentroDosLimites(RoboAereo robo) {
        int x = robo.getX();
        int y = robo.getY();
        int z = robo.getZ();
        int altMax = robo.getAltitudeMax();
        return ((x >= 0 && x <= profundidade) && (y >= 0 && y <= largura) && (z >= 0 && z <= altMax)) &&
                dentroDosLimites(x, y, z);
    }

    /**
     * Previamente 'ehObstaculo'. Checa se as coordenadas do ponto dado já estão ocupadas
     * por alguma entidade válida (Robo ou Obstaculo)
     * @param x coordenada x da posição procurada
     * @param y coordenada y da posição procurada
     * @param z coordenada z da posição procurada
     * @return true ou false dependendo se a posição está ou não ocupada
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

    // Exibe o ambiente, considerando '.' como espaço vazios, '#' como obstáculos e letras como os robôs
    public void visualizarAmbiente() {
        char [][] matrizAmbiente = new char[getProfundidade() + 1][getLargura() + 1];

        // Loop para preencher a matrizAmbiente representativa do ambiente com espaço vazio '.' 
        for (int a = 0; a <= getProfundidade(); a++) 
            for (int b = 0; b <= getLargura(); b++)
                matrizAmbiente[a][b] = '.';

        // Loop para mudar as posições com obstáculos para '#'
        for (Obstaculo obs:obstaculos)
            for (int c = obs.getPosicaoX1(); c <= obs.getPosicaoX2(); c++)
                for (int d = obs.getPosicaoY1(); d <= obs.getPosicaoY2(); d++)
                    matrizAmbiente[c][d] = obs.getRepresentacao();

        // Loop para mudar as posições com robos para suas respectivas letras
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

        System.out.println(". = vazio    # = torre de babel    @ = ciclo básico    ! = eucalipto    $ = pedra    + = estátua de elefante    § = the bean");
        System.out.println("   T = robô terrestre    X = robô xadrez    G = robô preguica    A = robô aéreo    P = robô planador    S = robô satélite");
    }

    public CentralComunicacao getCentral() {
        return central;
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
