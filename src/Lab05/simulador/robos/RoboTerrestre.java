package simulador.robos;

import simulador.excecoes.RoboDesligadoException;
import simulador.interfaces.Endotermico;
import simulador.ambiente.Ambiente;
import simulador.sensores.*;

public class RoboTerrestre extends Robo implements Endotermico{
    private int velocidade;
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima) {
        super(nome, id, posicaoX, posicaoY, ambiente);
        velocidade = 1;
        setVelocidadeMax(velocidadeMaxima);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Terrestre '%s' está %s e na posição (%d, %d) apontado na direção %s com velocidade %d e velocidade máxima permitida de %d.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getDirecao(), velocidade, velocidadeMaxima);
    }

    @Override
    public char getRepresentacao() {
        return 'T';
    }

    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException {
        // Compara velocidade do robo com a maxima dada
        if (velocidade <= velocidadeMaxima) 
            super.moverPara(x, y);
        // Não atualiza posicao caso tenha ultrapassado a velocidade
        else 
            System.out.printf("'%s' está acima da velocidade máxima de %d.\n", getNome(), velocidadeMaxima);
    }

    public void moverParaQuente() throws RoboDesligadoException {
        int indiceSensor = temSensorTipo("SensorTemperatura");

        if (estaLigado()) {
            if (indiceSensor != -1) {
                SensorTemperatura sensorTemperatura = ((SensorTemperatura) sensores.get(indiceSensor));
                int[] coordQuente = new int[2];
                double tempMax  = 0;
                int posXInicial, posYInicial;

                // 10 tentativas de chegar no ponto mais quente
                for (int i = 0; i < 10; i++) { 
                    posXInicial = getX();
                    posYInicial = getY();
                    tempMax = sensorTemperatura.retornaTemperaturaMax(coordQuente);

                    System.out.printf("\nO Robô sentiu %.1f°C na posição (%d, %d) e vai tentar ir para lá.\n", tempMax, coordQuente[0], coordQuente[1]);
                    moverSemSensor(coordQuente[0] - getX(), coordQuente[1] - getY());
                    // Robô está em no ponto mais quente
                    if (coordQuente[0] == posXInicial && coordQuente[1] == posYInicial){
                        System.out.printf("\nVocê chegou ao ponto (%d, %d), o mais quente desse raio, com temperatura %.1f°C,\n", getX(), getY(), tempMax);
                        return;
                    }
                    // Robô não conseguiu se mover 
                    else if (posXInicial == getX() && posYInicial == getY()) { 
                        System.out.printf("\nO ponto quente em (%d, %d) é inalcançável.\n", coordQuente[0], coordQuente[1]);
                        return;
                    }
                }
                System.out.printf("\nApós 10 tentativas, o Robô chegou apenas no ponto (%d, %d) com temperatura %.1f°C.\n", getX(), getY(), tempMax);
                atualizaSensores();
                System.out.printf("O Robô '%s' terminou o movimento na posição (%d, %d).\n", getNome(), getX(), getY());
            } else {
                System.out.println("\nSem sensor de temperatura no Robô.");
            }
        } else {
            System.out.println("");
            throw new RoboDesligadoException(getID());
        }
    }
    
    /**
     * Move o robô em uma linha reta na direção que está "olhando", parando apenas caso encontre um obstáculo
     * ou saia do ambiente
     */
    @Override
    public void executarTarefa() {
        int deltaX = 0;
        int deltaY = 0;
        System.out.println("");
        try {    
            if (estaLigado()) {
                switch (getDirecao()) {
                    case "Norte":
                        deltaY = getAmbiente().getProfundidade() - getY();
                        break;
                    case "Sul":
                        deltaY = 0 - getY();
                        break;
                    case "Leste":
                        deltaX = getAmbiente().getLargura() - getX();
                        break;
                    case "Oeste":
                        deltaX = 0 - getX();
                        break;
                }
                System.out.printf("O Robô vai andar em linha reta na direção %s.\n", getDirecao());
                moverSemSensor(deltaX, deltaY);

                atualizaSensores();
                    System.out.printf("O Robô '%s' terminou o movimento na posição (%d, %d).\n", getNome(), getX(), getY());
            } else {
                throw new RoboDesligadoException(getID());
            }
        } catch (RoboDesligadoException erro) {
            System.out.println(erro.getMessage());
        }
    }

    @Override
    public String getNomeTarefa() {
        return "'marchar em frente'";
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
