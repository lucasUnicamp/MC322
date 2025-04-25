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
        System.out.printf("Robo Terrestre '%s' esta na posicao (%d, %d) apontado na direcao %s com velocidade %d e velocidade maxima permitida de %d.\n\n"
        , getNome(), getX(), getY(), getDirecao(), velocidade, velocidadeMaxima);
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        // Compara velocidade do robo com a maxima dada
        if (velocidade <= velocidadeMaxima) 
            super.mover(deltaX, deltaY);
        // Não atualiza posicao caso tenha ultrapassado a velocidade
        else 
            System.out.printf("'%s' esta acima da velocidade maxima de %d.\n\n", getNome(), velocidadeMaxima);
    }

    public void aumentarVelocidade(int vlc) {
        velocidade += vlc;
        exibirVelocidade();
    }

    public void diminuirVelocidade(int vlc) {
        velocidade -= vlc;
        exibirVelocidade();
    }

    public void exibirVelocidade() {
        System.out.printf("'%s' Velocidade atual: %d\n\n", getNome(), getVelocidade());
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
