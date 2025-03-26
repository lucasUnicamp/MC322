public class RoboAereo extends Robo {
    private int altitude;
    private int altitudeMaxima;
    
    public RoboAereo(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima) {
        super(nome, posicaoX, posicaoY, ambiente);
        this.altitude = altitude;
        this.altitudeMaxima = altitudeMaxima;
    }

    @Override
    public void imprimeCriacao(){
        System.out.printf("Robô aéreo '%s' criado na posição (%d, %d, %d) apontado na direção %s com altitude máxima permitida de %d.\n"
        , nome, posicaoX, posicaoY, altitude, direcao, altitudeMaxima);
    }

    @Override
    public void exibirPosicao() {
        System.out.printf("O robô '%s' está em (%d, %d) na direção %s e %d acima do solo.\n", nome, posicaoX, posicaoY, direcao, altitude);
    }

    public void subir(int metros) {
        // Compara altitude do robô com a máxima dada
        if(altitude + metros <= altitudeMaxima)
            altitude += metros;
        // Não atualiza a altitude caso tenha ultrapassado a máxima dada
        else
            System.out.printf("'%s' ultrapassaria a altitude máxima permitida.\n", nome);

        System.out.printf("Altitude atual: %d\n", altitude);
    }

    public void descer(int metros) {
        // Compara a altitude do robô com a disância ao chão (0)
        if(altitude - metros >= 0)
            altitude -= metros;
        // Atuaçiza a altitude para 0 caso tenha descido demais
        else {
            System.out.printf("'%s' espatifou-se no chão.\n", nome);
            altitude = 0;
        }

        System.out.printf("Altitude atual: %d\n", altitude);
    }

    public int getAltitude(){
        return altitude;
    }

    public int getAltitudeMax(){
        return altitudeMaxima;
    }
}
