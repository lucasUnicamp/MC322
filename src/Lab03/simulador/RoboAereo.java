package simulador;

public class RoboAereo extends Robo {
    private int altitude;
    private int altitudeMaxima;
    
    public RoboAereo(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima) {
        super(nome, posicaoX, posicaoY, ambiente);
        setAltitude(altitude);
        setAltitudeMaxima(altitudeMaxima);
    }

    @Override
    public void info() {     
        System.out.printf("Robo Aereo '%s' está na posicao (%d, %d, %d) apontado na direcao %s com altitude maxima permitida de %d.\n\n"
        , getNome(), getX(),getY(), altitude, getDirecao(), altitudeMaxima);
    }

    @Override
    public void mover(int deltaX, int deltaY){
        if (getAltitude() == 0) 
            super.mover(deltaX, deltaY);
        else {
            System.out.printf("Tentando mover o Robo '%s' em %d no eixo x e em %d no y.\n", getNome(), deltaX, deltaY);

            if(getAmbiente().dentroDosLimites(getX() + deltaX, getY() + deltaY)){
                setX(getX() + deltaX);
                setY(getY() + deltaY);
                System.out.println("Movimentado com sucesso.\n");
                this.exibirPosicao();
            }
            else
                System.out.printf("'%s' nao tem permissao para sair do ambiente.\n\n", getNome());
        }
    }

    public void subir(int metros) {
        // Compara altitude do Robo com a maxima dada
        if (altitude + metros <= altitudeMaxima) {
            System.out.println("O Robo subiu com sucesso.\n");
            altitude += metros;
        }
        // Nao atualiza a altitude caso tenha ultrapassado a maxima dada
        else
            System.out.printf("'%s' ultrapassaria a altitude maxima permitida.\n\n", getNome());

        exibirAltitude();
    }

    public void descer(int metros) {
        // Compara a altitude do Robo com a disância ao chao (0)
        if (altitude - metros >= 0) {
            System.out.println("O Robo desceu com sucesso.\n");
            altitude -= metros;
        }
        // Atualiza a altitude para 0 caso tenha descido demais
        else {
            System.out.printf("'%s' espatifou-se no chao.\n\n", getNome());
            altitude = 0;
        }

        exibirAltitude();
    }

    @Override
    public void exibirPosicao() {
        System.out.printf("O Robo '%s' está em (%d, %d) na direcao %s e %d acima do solo.\n\n", getNome(), getX(), getY(), getDirecao(), altitude);
    }

    public void exibirAltitude() {
        System.out.printf("'%s' Altitude atual: %d\n\n", getNome(), altitude);
    }

    protected void setAltitude(int metros) {
        altitude = metros >= 0 ? metros : 0;        // Corrige altura contra valores negativos
    }

    protected void setAltitudeMaxima(int metros) {
        altitudeMaxima = metros >= 0 ? metros : 0;
    }

    public int getAltitude(){
        return altitude;
    }

    public int getAltitudeMax(){
        return altitudeMaxima;
    }
}
