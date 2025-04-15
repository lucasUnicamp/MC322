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
        // Operadores ternarios que dão ao ponto 1 as menores coordenadas X e Y, e ao ponto 2, as maiores
        this.posicaoX1 = posicaoX1 < posicaoX2 ? posicaoX1 : posicaoX2;
        this.posicaoX2 = posicaoX1 < posicaoX2 ? posicaoX2 : posicaoX1;
        this.posicaoY1 = posicaoY1 < posicaoY2 ? posicaoY1 : posicaoY2;
        this.posicaoY2 = posicaoY1 < posicaoY2 ? posicaoY2 : posicaoY1;
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
