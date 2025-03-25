public class RoboTerrestre extends Robo {
    private int velocidade;
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima) {
        super(nome, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
    }

    @Override
    protected void imprimeCriacao(){
        System.out.printf("Robô terrestre '%s' criado na posição (%d, %d) apontado na direção %s com velocidade máxima de %d.\n"
        , nome, posicaoX, posicaoY, direcao, velocidadeMaxima);
    }

    public void setVelocidade(int veloc) {
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
