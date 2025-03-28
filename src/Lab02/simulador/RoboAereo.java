package simulador;

public class RoboAereo extends Robo {
    private int altitude;
    private int altitudeMaxima;
    
    public RoboAereo(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima) {
        super(nome, posicaoX, posicaoY, ambiente);
        this.altitude = altitude;
        this.altitudeMaxima = altitudeMaxima;
    }

    @Override
    public void info() {     
        System.out.printf("Robô Aéreo '%s' está na posição (%d, %d, %d) apontado na direção %s com altitude máxima permitida de %d.\n"
        , getNome(), getX(),getY(), altitude, getDirecao(), altitudeMaxima);
    }

    @Override
    public void exibirPosicao() {
        System.out.printf("O robô '%s' está em (%d, %d) na direção %s e %d acima do solo.\n", getNome(), getX(), getY(), getDirecao(), altitude);
    }

    @Override
    public void mover(int deltaX, int deltaY){
        if (getAltitude() == 0) 
            super.mover(deltaX, deltaY);
        else {
            if(getAmbiente().dentroDosLimites(getX() + deltaX, getY() + deltaY)){
                setX(getX() + deltaX);
                setY(getY() + deltaY);
                System.out.printf("Movendo robô '%s' em %d no eixo x e em %d no y.\n", getNome(), deltaX, deltaY);
                this.exibirPosicao();
            }
            else
                System.out.printf("'%s' não tem permissão para sair do ambiente.\n\n", getNome());
        }
    }

    public void subir(int metros) {
        // Compara altitude do robô com a máxima dada
        if (altitude + metros <= altitudeMaxima)
            altitude += metros;
        // Não atualiza a altitude caso tenha ultrapassado a máxima dada
        else
            System.out.printf("'%s' ultrapassaria a altitude máxima permitida.\n", getNome());

        System.out.printf("'%s' Altitude atual: %d\n", getNome(), altitude);
    }

    public void descer(int metros) {
        // Compara a altitude do robô com a disância ao chão (0)
        if (altitude - metros >= 0)
            altitude -= metros;
        // Atualiza a altitude para 0 caso tenha descido demais
        else {
            System.out.printf("'%s' espatifou-se no chão.\n", getNome());
            altitude = 0;
        }

        System.out.printf("(%s) Altitude atual: %d\n", getNome(), altitude);
    }

    public int getAltitude(){
        return altitude;
    }

    public int getAltitudeMax(){
        return altitudeMaxima;
    }
}
