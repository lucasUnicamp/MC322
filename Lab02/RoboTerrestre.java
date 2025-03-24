public class RoboTerrestre extends Robo {
    private int velocidade;
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidadeMaxima) {
        super(nome, direcao, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
        System.out.printf("Robô terrestre '%s' criado na posição (%d, %d) apontado na direção %s com velocidade máxima de %d.\n", nome, posicaoX, posicaoY, direcao, velocidadeMaxima);
    }

    public void velocidade(int veloc) {
        velocidade = veloc;
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        if(velocidade <= velocidadeMaxima) {
            super.mover(deltaX, deltaY);
        }
        else
            System.out.printf("%s está acima da velocidade máxima de %d.\n", nome, velocidadeMaxima);
    }
}
