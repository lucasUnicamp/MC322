package simulador;

public class RoboTerrestre extends Robo {
    private int velocidade;
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima) {
        super(nome, posicaoX, posicaoY, ambiente);
        velocidade = 1;
        setVelocidadeMax(velocidadeMaxima);
    }

    @Override
    public void info() {
        System.out.printf("Robô Terrestre '%s' está na posição (%d, %d) apontado na direção %s com velocidade %d e velocidade máxima permitida de %d.\n\n"
        , getNome(), getX(), getY(), getDirecao(), velocidade, velocidadeMaxima);
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        // Compara velocidade do robô com a máxima dada
        if (velocidade <= velocidadeMaxima) 
            super.mover(deltaX, deltaY);
        // Não atualiza posição caso tenha ultrapassado a velocidade
        else 
            System.out.printf("'%s' está acima da velocidade máxima de %d.\n\n", getNome(), velocidadeMaxima);
    }

    public void aumentarVelocidade(int vlc) {
        velocidade += vlc;
        System.out.printf("'%s' Velocidade atual: %d\n\n", getNome(), velocidade);
    }

    protected int setVelocidade() {
        return velocidade;
    }

    public void setVelocidadeMax(int velocidadeMaxima) {
        if(velocidadeMaxima >= 0)
            this.velocidadeMaxima = velocidadeMaxima;
        else
            this.velocidadeMaxima = 0;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public int getVelocidadeMax() {
        return velocidadeMaxima;
    }
}
