package simulador;

public class RoboTerrestre extends Robo {
    private int velocidade;
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima) {
        super(nome, id, posicaoX, posicaoY, ambiente);
        velocidade = 1;
        setVelocidadeMax(velocidadeMaxima);
    }

    @Override
    public String getDescricao() {
        return String.format("Robo Terrestre '%s' esta na posicao (%d, %d) apontado na direcao %s com velocidade %d e velocidade maxima permitida de %d.\n"
        , getNome(), getX(), getY(), getDirecao(), velocidade, velocidadeMaxima);
    }

    @Override
    public char getRepresentacao() {
        return 'T';
    }


    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException{
        // Compara velocidade do robo com a maxima dada
        if (velocidade <= velocidadeMaxima) 
            super.moverPara(x, y);
        // Não atualiza posicao caso tenha ultrapassado a velocidade
        else 
            System.out.printf("'%s' esta acima da velocidade maxima de %d.\n", getNome(), velocidadeMaxima);
    }

    public void atualizaSensores() {
        if (sensores != null) {
            // Atualiza a posicao do robo em cada sensor que o robo possui 
            for (Sensor sensor : sensores) {
                sensor.setX(getX());
                sensor.setY(getY());
            }
        }
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
        System.out.printf("'%s' - Velocidade atual: %d\n", getNome(), getVelocidade());
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
