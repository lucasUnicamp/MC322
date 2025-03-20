package Package02;

public class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;

    public Robo(String nome, String direcao, int posicaoX, int posicaoY) {
        this.nome = nome;
        this.direcao = direcao;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }

    public void mover(int deltaX, int deltaY) {
        posicaoX += deltaX;
        posicaoY += deltaY;
    }

    // public void identificarObstaculo() {
        
    // }

    public void exibirPosicao() {
        System.out.printf("\nO robô %s está em (%d, %d) na direção %s.\n", nome, posicaoX, posicaoY, direcao);
    }

    public String getDirecao() {
        return direcao;
    }

    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }
}