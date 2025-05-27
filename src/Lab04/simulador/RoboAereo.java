package simulador;

import simulador.excecoes.DesceuDemaisException;
import simulador.excecoes.RoboDesligadoException;
import simulador.interfaces.Sensoreavel;

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
        return String.format("Robô Aéreo '%s' está %s e na posição (%d, %d, %d) apontado na direção %s com altitude máxima permitida de %d.\n", 
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getZ(), getDirecao(), altitudeMaxima);
    }

    @Override
    public char getRepresentacao() {
        return 'A';
    }

    /**
     * Override necessário pois o Robô Aéreo nao deve se mover se nao tiver um sensor. Caso não fizessemos 
     * o override, ele tentaria usar o 'moverSemSensor'
     */
    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException {
        int deltaX = x - getX();
        int deltaY = y - getY();
        int indice = temSensorTipo("SensorObstaculo");
        System.out.printf("Tentando mover o Robô '%s' para a posição (%d, %d).\n", getNome(), x, y);
        
        if (indice != -1) {
            super.moverComSensor(deltaX, deltaY, indice);
            System.out.printf("O Robô '%s' terminou o movimento na posição (%d, %d, %d).\n", getNome(), getX(), getY(), getZ());
        }
        else
            System.out.println("Não pode voar sem sensor de obstáculo, é muito perigoso.");
        
        atualizaSensores();
    }

    public void subir(int metros) throws RoboDesligadoException{
        if (estaLigado()) {
            // Compara altitude do Robô com a máxima dada
            if (getZ() + metros <= altitudeMaxima) {
                System.out.println("O Robo subiu com sucesso.");
                setZ(getZ() + metros);;
            }
            // Não atualiza a altitude caso tenha ultrapassado a máxima dada
            else
                System.out.printf("'%s' ultrapassaria a altitude máxima permitida.\n", getNome());

            exibirAltitude();
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    public void descer(int metros) throws RoboDesligadoException, DesceuDemaisException {
        if (estaLigado()) {
            int indice = temSensorTipo("SensorObstaculo");
            SensorObstaculo sensorObs;

            if (getZ() != 0) {
                if(indice == -1) {
                    System.out.println("Impossível descer com segurança, não há sensor de obstáculo.");
                    return;
                } 
                else
                    sensorObs = (SensorObstaculo) sensores.get(indice);

                // Compara a altitude do Robô com a disância ao chão (0)
                if (getZ() - metros >= 0 && !sensorObs.checarObstaculoPosicao(getX(), getY(), getZ() - metros)) {
                    System.out.println("O Robô desceu com sucesso.");
                    setZ(getZ() - metros);
                }
                // Joga um erro caso tenha descido demais e não há obtaculo abaixo
                else if (!sensorObs.checarObstaculoPosicao(getX(), getY(), 0)){
                    throw new DesceuDemaisException(getID());
                }
                // Não atualiza a altitude caso tenha obstáculos abaixo
                else {
                    System.out.printf("Há obstáculos abaixo de '%s', não tem como descer.\n", getNome());
                }
            }
            else
                System.out.println("O Robô já está no chão, não tem como descer mais.");
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

    @Override
    public void executarTarefa() {

        switch (getDirecao()) {
            case "Norte":
                break;
            case "Sul":
                break;
            case "Leste":
                break;
            case "Oeste":
                break;
        }
    }

    // Aciona todos os sensores ao mesmo tempo nas imediações adjacentes ao robô
    public void acionarSensores() throws RoboDesligadoException {
        if (estaLigado()) {
            for (int i = 0; i < sensores.size(); i++) {
                System.out.printf("\n%s:\n", sensores.get(i).getClass().getSimpleName().toUpperCase());

                for (int j = getX() - 1; j <= getX() + 1; j++) {
                    for (int k = getY() - 1; k <= getY() + 1; k++) {
                        System.out.print("     " );
                        if (sensores.get(i) instanceof SensorTemperatura)
                            ((SensorTemperatura) sensores.get(i)).exibirTempPonto(j, k);
                        else 
                            usarSensor(i, j, k);
                    }
                }
            }
        } else {
            System.out.println("");
            throw new RoboDesligadoException(getID());
        }
    }

    @Override
    public void exibirPosicao() {
        System.out.printf("O Robô '%s' está agora em (%d, %d) na direção %s e %d acima do solo.\n", getNome(), getX(), getY(), getDirecao(), altitude);
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
