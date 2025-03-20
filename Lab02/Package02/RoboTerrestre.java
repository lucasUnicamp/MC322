package Package02;

public class RoboTerrestre extends Robo {
    private int velocidade;
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidade, int velocidadeMaxima) {
        super(nome, direcao, posicaoX, posicaoY);
        this.velocidade = velocidade;
        this.velocidadeMaxima = velocidadeMaxima;
    }

    @Override public void mover(int deltaX, int deltaY) {
        if(velocidade <= velocidadeMaxima) {
            posicaoX += deltaX;
            posicaoY += deltaY;
        }
        else
            System.out.printf("%s está acima da velocidade máxima de %d.\n", nome, velocidadeMaxima);
    }

}
