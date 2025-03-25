public class RoboTerrestre extends Robo {
    private int velocidade;
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, int posicaoX, int posicaoY, int velocidadeMaxima, Ambiente ambiente) {
        super(nome, posicaoX, posicaoY, ambiente);
        this.velocidadeMaxima = velocidadeMaxima;
    }

    // Usada para imprimir as informações úteis e checar se estão corretas após a criação do robô
    @Override
    protected void imprimeCriacao(){
        System.out.printf("Robô terrestre '%s' criado na posição (%d, %d) apontado na direção %s com velocidade máxima de %d.\n"
        , nome, posicaoX, posicaoY, direcao, velocidadeMaxima);
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        // Compara velocidade do robô com a máxima dada
        if(velocidade <= velocidadeMaxima) {
            super.mover(deltaX, deltaY);
        }
        // Não atualiza posição caso tenha ultrapassado a velocidade
        else {
            System.out.printf("%s está acima da velocidade máxima de %d.\n", nome, velocidadeMaxima);
        }
    }

    public void setVelocidade(int vlc) {
        velocidade = vlc;
    }
}
