package simulador;

import java.lang.Math;

public class RoboPlanador extends RoboAereo {
    private int tamanhoAsa;     // Quanto maior a asa, por mais tempo consegue planar e também consegue subir mais

    public RoboPlanador(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int tamanhoAsa){
        super(nome, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        setTamanhoAsa(tamanhoAsa);
    }

    @Override
    public void info() {
        System.out.printf("Robo Planador'%s' está na posicao (%d, %d, %d) apontado na direcao %s com altitude maxima permitida de %d e asa de tamanho %d.\n"
        , getNome(), getX(), getY(), getAltitude(), getDirecao(), getAltitudeMax(), tamanhoAsa);
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        if (getAmbiente().dentroDosLimites(getX() + deltaX, getY() + deltaY)){
            int deslocamento = Math.abs(deltaY) + Math.abs(deltaX);
            super.mover(deltaX, deltaY);
            super.descer(((120 - tamanhoAsa)*deslocamento)/100);        // Cai lentamente quando deslocado
        } 
        else 
            System.out.printf("'%s' não tem permissao para sair do ambiente.\n", getNome());
    }

    @Override
    public void subir(int metros) {
        if (metros <= tamanhoAsa)     // Quanto maior a asa mais pode subir
            super.subir(metros);
        else
            System.out.printf("'%s' tem as asas muito curtas.\n", getNome());
    }
 
    public void descerPlanando(int metros) {
        int indice = temSensorTipo("SensorObstaculo");
        SensorObstaculo sensorObs;

        if(indice == -1) {
            System.out.println("Impossivel descer com segurança, nao ha sensor de obstaculo.\n");
            return;
        } 
        else
            sensorObs = (SensorObstaculo) sensores.get(indice);

        // Compara a altitude do Robo com a disância ao chao (0)
        if (getAltitude() - metros >= 0 && !sensorObs.checarObstaculoPosicao(getX(), getY(), getAltitude() - metros)) {
            System.out.println("O Robo desceu com sucesso.");
            setAltitude(getAltitude() - metros);
        }
        // Atualiza a altitude para 0 caso tenha descido demais e não há obtáculo abaixo
        else if (!sensorObs.checarObstaculoPosicao(getX(), getY(), 0)){
            System.out.printf("'%s' espatifou-se no chao.\n", getNome());
            setAltitude(0);
        }
        // Não Atualiza a altitude caso tenha obstaculos abaixo
        else {
            System.out.printf("Ha obstaculos abaixo de '%s', nao tem como descer.\n", getNome());
        }
        exibirAltitude();
    }

    public void setTamanhoAsa(int tamanhoAsa) {
        if (tamanhoAsa <= 100)        // Tamanho maximo da asa de 100
            this.tamanhoAsa = tamanhoAsa;
        else
            this.tamanhoAsa = 100;
    }
}
