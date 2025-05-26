package simulador;

public class RoboAereo extends Robo implements Sensoreavel{
    private int altitude;
    private int altitudeMaxima;
    
    public RoboAereo(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima) {
        super(nome, id, posicaoX, posicaoY, ambiente);
        setZ(altitude);
        setAltitudeMaxima(altitudeMaxima);
    }

    @Override
    public String getDescricao() {     
        return String.format("Robo Aereo '%s' esta %s e na posicao (%d, %d, %d) apontado na direcao %s com altitude maxima permitida de %d.\n", 
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getZ(), getDirecao(), altitudeMaxima);
    }

    @Override
    public char getRepresentacao() {
        return 'A';
    }

    /**
     * Override necessario pois o Robo Aereo nao deve se mover se nao tiver um sensor. Caso nao fizessemos 
     * o override, ele tentaria usar o 'moverSemSensor'
     */
    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException{
        int deltaX = x - getX();
        int deltaY = y - getY();
        int indice = temSensorTipo("SensorObstaculo");
        System.out.printf("Tentando mover o Robo '%s' para a posiçao (%d, %d).\n", getNome(), x, y);
        
        if (indice != -1) {
            super.moverComSensor(deltaX, deltaY, indice);
            System.out.printf("O Robo '%s' terminou o movimento na posicao (%d, %d, %d).\n", getNome(), getX(), getY(), getZ());
        }
        else
            System.out.println("Nao pode voar sem sensor de obtaculo, eh muito perigoso.");
        
        atualizaSensores();
    }

    public void subir(int metros) throws RoboDesligadoException{
        if (estaLigado()) {
            // Compara altitude do Robo com a maxima dada
            if (getZ() + metros <= altitudeMaxima) {
                System.out.println("O Robo subiu com sucesso.");
                setZ(getZ() + metros);;
            }
            // Nao atualiza a altitude caso tenha ultrapassado a maxima dada
            else
                System.out.printf("'%s' ultrapassaria a altitude maxima permitida.\n", getNome());

            exibirAltitude();
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    public void descer(int metros) throws RoboDesligadoException{
        if (estaLigado()){
            int indice = temSensorTipo("SensorObstaculo");
            SensorObstaculo sensorObs;

            if (getZ() != 0) {
                if(indice == -1) {
                    System.out.println("Impossivel descer com segurança, nao ha sensor de obstaculo.");
                    return;
                } 
                else
                    sensorObs = (SensorObstaculo) sensores.get(indice);

                // Compara a altitude do Robo com a disância ao chao (0)
                if (getZ() - metros >= 0 && !sensorObs.checarObstaculoPosicao(getX(), getY(), getZ() - metros)) {
                    System.out.println("O Robo desceu com sucesso.");
                    setZ(getZ() - metros);
                }
                // Atualiza a altitude para 0 caso tenha descido demais e nao ha obtaculo abaixo
                else if (!sensorObs.checarObstaculoPosicao(getX(), getY(), 0)){
                    System.out.printf("'%s' espatifou-se no chao.\n", getNome());
                    setZ(0);
                }
                // Não Atualiza a altitude caso tenha obstaculos abaixo
                else {
                    System.out.printf("Ha obstaculos abaixo de '%s', nao tem como descer.\n", getNome());
                }
            }
            else
                System.out.println("O Robo ja esta no chao, nao tem como descer mais.");
            exibirAltitude();
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    @Override
    public void atualizaSensores() {
        if(sensores != null) {
            for (Sensor sensor:sensores) {
                sensor.setX(getX());
                sensor.setY(getY());
                sensor.setAltitude(getZ());
            }
        }      
    }

    // Aciona todos os sensores ao mesmo tempo nas imediaçoes adjacentes ao robo
    public void acionarSensores() throws RoboDesligadoException {
        for (int i = 0; i < sensores.size(); i++) {
            System.out.printf("\n%s:\n", sensores.get(i).getClass().getSimpleName().toUpperCase());

            for (int j = getX() - 1; j <= getX() + 1; j++) {
                for (int k = getY() - 1; k <= getY() + 1; k++) {
                    if (sensores.get(i) instanceof SensorTemperatura)
                        ((SensorTemperatura) sensores.get(i)).exibirTempPonto(j, k);
                    else
                        usarSensor(i, j, k);
                }
            }
        }
    }

    @Override
    public void exibirPosicao() {
        System.out.printf("O Robo '%s' está agora em (%d, %d) na direcao %s e %d acima do solo.\n", getNome(), getX(), getY(), getDirecao(), altitude);
    }

    public void exibirAltitude() {
        System.out.printf("'%s' - Altitude atual: %d\n", getNome(), getZ());
    }

    protected void setAltitudeMaxima(int metros) {
        altitudeMaxima = metros >= 0 ? metros : 0;
    }

    public int getAltitudeMax(){
        return altitudeMaxima;
    }
}
