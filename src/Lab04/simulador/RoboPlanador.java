package simulador;

import java.lang.Math;

public class RoboPlanador extends RoboAereo {
    private int tamanhoAsa;     // Quanto maior a asa, por mais tempo consegue planar e também consegue subir mais

    public RoboPlanador(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int tamanhoAsa){
        super(nome, id, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        setTamanhoAsa(tamanhoAsa);
    }

    @Override
    public String getDescricao() {
        return String.format("Robo Planador'%s' está na posicao (%d, %d, %d) apontado na direcao %s com altitude maxima permitida de %d e asa de tamanho %d.\n"
        , getNome(), getX(), getY(), getAltitude(), getDirecao(), getAltitudeMax(), tamanhoAsa);
    }

    @Override
    public char getRepresentacao() {
        return 'P';
    }


    /**
     * Override necessario para exibir que o Robo Planador 'planou' de uma altitude para outra entre tentar se mover e realmente se mover.
     * Caso apenas descessemos o Robo apos movimentacao, a altitude exibida pelo 'moverComSensor' nao estaria correta. Caso descessemos antes,
     * nao faria sentido pois ele exibiria que ja tinha descido antes de tentar se mover 
     */
    @Override
    public void moverPara(int x, int y) {
        int deltaX = x - getX();
        int deltaY = y - getY();
        int deslocamento = Math.abs(deltaX) + Math.abs(deltaY);
        int indice = temSensorTipo("SensorObstaculo");
        System.out.printf("Tentando mover o Robo '%s' para a posicao (%d, %d).\n", getNome(), deltaX, deltaY);
        
        if (indice != -1) {
            descerPlanando(((120 - tamanhoAsa)*deslocamento)/100);      // Cai lentamente quando deslocado
            moverComSensor(deltaX, deltaY, indice);
            System.out.printf("O Robo '%s' terminou o movimento na posicao (%d, %d, %d).\n", getNome(), getX(), getY(), getAltitude());
        }
        else
            System.out.println("Nao pode voar sem sensor de obtaculo, eh muito perigoso.");
        
        atualizaSensores();
    }

    @Override
    public void subir(int metros) {
        if (metros <= tamanhoAsa)     // Quanto maior a asa mais pode subir
            super.subir(metros);
        else
            System.out.printf("'%s' tem as asas muito curtas.\n", getNome());
    }
 
    // Override feito para mudar o que eh printado para o usuario e ficar mais condizente com o tipo do Robo
    @Override 
    public void descer(int metros) {
        int indice = temSensorTipo("SensorObstaculo");
        SensorObstaculo sensorObs;

        if (getAltitude() != 0) {
            if (indice == -1) {
                System.out.println("Impossivel descer com segurança, nao ha sensor de obstaculo.\n");
                return;
            } 
            else
                sensorObs = (SensorObstaculo) sensores.get(indice);

            // Compara a altitude do Robo com a disância ao chao (0)
            if (getAltitude() - metros >= 0 && !sensorObs.checarObstaculoPosicao(getX(), getY(), getAltitude() - metros)) {
                System.out.println("O Robo planou e desceu suavemente no processo.");
                setAltitude(getAltitude() - metros);
            }
            // Atualiza a altitude para 0 caso tenha descido demais e não há obtáculo abaixo
            else if (!sensorObs.checarObstaculoPosicao(getX(), getY(), 0)){
                System.out.printf("'%s' planou ate chegar no chao e aterrissou.\n", getNome());
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

    // Metodo 'intermediario' para impedir que o Robo use o 'descer' apos se mover no chao (altitude 0)
    public void descerPlanando(int metros) {
        if (getAltitude() != 0) {
            descer(metros);
        }
    }

    public void setTamanhoAsa(int tamanhoAsa) {
        if (tamanhoAsa <= 100) {     // Tamanho maximo da asa de 100
            this.tamanhoAsa = tamanhoAsa;
            System.out.printf("Tamanho da asa alterado para %d.\n", this.tamanhoAsa);
        } else {
            this.tamanhoAsa = 100;
            System.out.print("Tamanho da asa alterado para 100 (máximo).\n");
        }
    }
}
