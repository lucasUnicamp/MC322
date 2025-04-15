package simulador;

public class Obstaculo {
    private int posicaoX1;
    private int posicaoY1;
    private int altura;
    private int posicaoX2;
    private int posicaoY2;
    TipoObstaculo tipo;

    public Obstaculo(int posicaoX1, int posicaoY1, int altura, int posicaoX2, int posicaoY2, TipoObstaculo tipo) {
        this.altura = altura;

        this.tipo = tipo;
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
