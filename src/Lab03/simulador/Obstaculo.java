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
        this.posicaoY1 = posicaoY1;
        this.posicaoX2 = posicaoX2;
        this.posicaoY2 = posicaoY2;
    }
    
    public TipoObstaculo getTipoObstaculo() {
        return tipo;
    }

    public void setPosicaoX1(int pos) {
        posicaoX1 = pos;
    }

    public void setPosicaoX2(int pos) {
        posicaoX2 = pos;
    }

    public void setPosicaoY1(int pos) {
        posicaoY1 = pos;
    }

    public void setPosicaoY2(int pos) {
        posicaoY2 = pos;
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
