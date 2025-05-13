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
    public String getDescricao() {     
        return String.format("Robo Aereo '%s' está na posicao (%d, %d, %d) apontado na direcao %s com altitude maxima permitida de %d.\n"
        , getNome(), getX(),getY(), altitude, getDirecao(), altitudeMaxima);
    }

    /**
     * Override necessario pois o Robo Aereo nao deve se mover se nao tiver um sensor. Caso nao fizessemos 
     * o override, ele tentaria usar o 'moverSemSensor'
     */
     @Override
    public void mover(int deltaX, int deltaY){
        int indice = temSensorTipo("SensorObstaculo");
        System.out.printf("Tentando mover o Robo '%s' em %d no eixo x e em %d no y.\n", getNome(), deltaX, deltaY);
        
        if (indice != -1) {
            moverComSensor(deltaX, deltaY, indice);
            System.out.printf("O Robo '%s' terminou o movimento na posicao (%d, %d, %d).\n", getNome(), getX(), getY(), getAltitude());
        }
        else
            System.out.println("Nao pode voar sem sensor de obtaculo, eh muito perigoso.");
        
        atualizaSensores();
    }

    public void subir(int metros) {
        // Compara altitude do Robo com a maxima dada
        if (getAltitude() + metros <= altitudeMaxima) {
            System.out.println("O Robo subiu com sucesso.");
            setAltitude(getAltitude() + metros);;
        }
        // Nao atualiza a altitude caso tenha ultrapassado a maxima dada
        else
            System.out.printf("'%s' ultrapassaria a altitude maxima permitida.\n", getNome());

        exibirAltitude();
    }

    public void descer(int metros) {
        int indice = temSensorTipo("SensorObstaculo");
        SensorObstaculo sensorObs;

        if (getAltitude() != 0) {
            if(indice == -1) {
                System.out.println("Impossivel descer com segurança, nao ha sensor de obstaculo.");
                return;
            } 
            else
                sensorObs = (SensorObstaculo) sensores.get(indice);

            // Compara a altitude do Robo com a disância ao chao (0)
            if (getAltitude() - metros >= 0 && !sensorObs.checarObstaculoPosicao(getX(), getY(), getAltitude() - metros)) {
                System.out.println("O Robo desceu com sucesso.");
                setAltitude(getAltitude() - metros);
            }
            // Atualiza a altitude para 0 caso tenha descido demais e nao ha obtaculo abaixo
            else if (!sensorObs.checarObstaculoPosicao(getX(), getY(), 0)){
                System.out.printf("'%s' espatifou-se no chao.\n", getNome());
                setAltitude(0);
            }
            // Não Atualiza a altitude caso tenha obstaculos abaixo
            else {
                System.out.printf("Ha obstaculos abaixo de '%s', nao tem como descer.\n", getNome());
            }
        }
        else
            System.out.println("O Robo ja esta no chao, nao tem como descer mais.");
        exibirAltitude();
    }

    @Override
    public void atualizaSensores() {
        if(sensores != null) {
            for (Sensor sensor:sensores) {
                sensor.setX(getX());
                sensor.setY(getY());
                sensor.setAltitude(getAltitude());
            }
        }      
    }

    @Override
    public void exibirPosicao() {
        System.out.printf("O Robo '%s' está agora em (%d, %d) na direcao %s e %d acima do solo.\n", getNome(), getX(), getY(), getDirecao(), altitude);
    }

    public void exibirAltitude() {
        System.out.printf("'%s' - Altitude atual: %d\n", getNome(), getAltitude());
    }

    protected void setAltitude(int metros) {
        altitude = metros >= 0 ? metros : 0;        // Corrige altura contra valores negativos
        atualizaSensores();
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
