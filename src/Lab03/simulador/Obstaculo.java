package simulador;

public class Obstaculo {
    private int posicaoX1;
    private int posicaoY1;
    private int altura;
    private int posicaoX2;
    private int posicaoY2;
    TipoObstaculo tipo;

    public Obstaculo(int posicaoX1, int posicaoY1, int altura, int posicaoX2, int posicaoY2, TipoObstaculo tipo) {
        this.posicaoX1 = posicaoX1;
        this.posicaoY1 = posicaoY1;
        this.altura = altura;
        this.posicaoX2 = posicaoX2;
        this.posicaoY2 = posicaoY2;
        this.tipo = tipo;
    }
    
}
