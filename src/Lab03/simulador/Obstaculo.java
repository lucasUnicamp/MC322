package simulador;

public class Obstaculo {
    private int posicaoX1;
    private int posicaoY1;
    private int posicaoX2;
    private int posicaoY2;
    TipoObstaculo tipo;

    public Obstaculo(int posicaoX1, int posicaoY1, int posicaoX2, int posicaoY2, TipoObstaculo tipo) {
        this.tipo = tipo;
        this.posicaoX1 = posicaoX1;
        this.posicaoX2 = posicaoY1;
        this.posicaoY1 = posicaoX2;
        this.posicaoY2 = posicaoY2;
        ordenarCoordenadas();
    }

    /**
     * Ordena as coordenadas do obstaculo para facilitar uso, tornando o ponto 1 o de menores
     * coordenadas e o ponto 2 o de maiores
     */
    public void ordenarCoordenadas() {
        setPosicaoX1(getPosicaoX1() < getPosicaoX2() ? getPosicaoX1() : getPosicaoX2());
        setPosicaoX2(getPosicaoX1() < getPosicaoX2() ? getPosicaoX2() : getPosicaoX1());
        setPosicaoY1(getPosicaoY1() < getPosicaoY2() ? getPosicaoY1() : getPosicaoY2());
        setPosicaoY2(getPosicaoY1() < getPosicaoY2() ? getPosicaoY2() : getPosicaoY1());
    }
    
    public TipoObstaculo getTipoObstaculo() {
        return tipo;
    }

    public void setPosicaoX1(int pos) {
        posicaoX1 = pos;
        ordenarCoordenadas();
    }

    public void setPosicaoX2(int pos) {
        posicaoX2 = pos;
        ordenarCoordenadas();
    }

    public void setPosicaoY1(int pos) {
        posicaoY1 = pos;
        ordenarCoordenadas();
    }

    public void setPosicaoY2(int pos) {
        posicaoY2 = pos;
        ordenarCoordenadas();
    }
    
    public int getPosicaoX1() {
        return posicaoX1;
    }

    public int getPosicaoX2() {
        return posicaoX2;
    }

    public int getPosicaoY1() {
        return posicaoY1;
    }

    public int getPosicaoY2() {
        return posicaoY2;
    }
}
