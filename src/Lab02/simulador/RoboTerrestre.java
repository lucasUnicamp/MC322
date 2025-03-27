package simulador;
public class RoboTerrestre extends Robo {
    private int velocidade;
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima) {
        super(nome, posicaoX, posicaoY, ambiente);
        this.velocidadeMaxima = velocidadeMaxima;
        
        System.out.printf("Robô terrestre '%s' criado na posição (%d, %d) apontado na direção %s com velocidade máxima permitida de %d.\n"
        , nome, posicaoX, posicaoY, getDirecao(), velocidadeMaxima);
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        // Compara velocidade do robô com a máxima dada
        if (velocidade <= velocidadeMaxima) {
            super.mover(deltaX, deltaY);
        }
        // Não atualiza posição caso tenha ultrapassado a velocidade
        else {
            System.out.printf("'%s' está acima da velocidade máxima de %d.\n", getNome(), velocidadeMaxima);
        }
    }

    public void setVelocidade(int vlc) {
        velocidade = vlc;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public int getVelocidadeMax() {
        return velocidade;
    }
}
