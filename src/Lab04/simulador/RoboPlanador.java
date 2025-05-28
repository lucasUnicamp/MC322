package simulador;

import java.lang.Math;

import simulador.excecoes.RoboDesligadoException;

public class RoboPlanador extends RoboAereo {
    private int tamanhoAsa;     // Quanto maior a asa, por mais tempo consegue planar e também consegue subir mais

    public RoboPlanador(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int tamanhoAsa){
        super(nome, id, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        setTamanhoAsa(tamanhoAsa);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Planador '%s' está %s e na posição (%d, %d, %d) apontado na direção %s com altitude máxima permitida de %d e asa de tamanho %d.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getZ(), getDirecao(), getAltitudeMax(), tamanhoAsa);
    }

    @Override
    public char getRepresentacao() {
        return 'P';
    }

    /**
     * Override necessário para exibir que o Robô Planador 'planou' de uma altitude para outra entre tentar se mover e realmente se mover.
     * Caso apenas descêssemos o Robô após movimentação, a altitude exibida pelo 'moverComSensor' não estaria correta. Caso descẽssemos antes,
     * não faria sentido pois ele exibiria que já tinha descido antes de tentar se mover 
     */
    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException {
        int deltaX = x - getX();
        int deltaY = y - getY();
        int deslocamento = Math.abs(deltaX) + Math.abs(deltaY);
        int indice = temSensorTipo("SensorObstaculo");
        System.out.printf("Tentando mover o Robô '%s' para a posição (%d, %d).\n", getNome(), x, y);
        
        if (indice != -1) {
            descerPlanando(((120 - tamanhoAsa)*deslocamento)/100);      // Cai lentamente quando deslocado
            moverComSensor(deltaX, deltaY, indice);
            System.out.printf("O Robô '%s' terminou o movimento na posição (%d, %d, %d).\n", getNome(), getX(), getY(), getZ());
        }
        else
            System.out.println("Não pode voar sem sensor de obstáculo, é muito perigoso.");
        
        atualizaSensores();
    }

    @Override
    public void subir(int metros) throws RoboDesligadoException{
        if (metros < 0) {
            System.out.println("Para 'subir negativamente', por favor use a função 'subir'.");
            return;
        }
        if (metros <= tamanhoAsa)     // Quanto maior a asa mais pode subir
            super.subir(metros);
        else
            System.out.printf("'%s' tem as asas muito curtas.\n", getNome());
    }
 
    // Override feito para mudar o que é printado para o usuário e ficar mais condizente com o tipo do Robô
    @Override 
    public void descer(int metros) throws RoboDesligadoException {
        if (estaLigado()) {
            int indice = temSensorTipo("SensorObstaculo");
            SensorObstaculo sensorObs;

            if (metros < 0) {
                System.out.println("Para 'descer negativamente', por favor use a função 'subir'.");
                return;
            }

            if (getZ() != 0) {
                if (metros < 0) {
                    System.out.println("Para descer negativamente, por favor use a função 'subir'.");
                    return;
                }

                if (indice == -1) {
                    System.out.println("Impossível descer com segurança, não há sensor de obstáculo.\n");
                    return;
                } 
                else
                    sensorObs = (SensorObstaculo) sensores.get(indice);

                // Compara a altitude do Robo com a distância ao chão (0)
                if (getZ() - metros >= 0 && !sensorObs.checarObstaculoPosicao(getX(), getY(), getZ() - metros)) {
                    System.out.println("O Robô planou e desceu suavemente no processo.");
                    setZ(getZ() - metros);
                }
                // Atualiza a altitude para 0 caso tenha descido demais e não há obtáculo abaixo
                else if (!sensorObs.checarObstaculoPosicao(getX(), getY(), 0)){
                    System.out.printf("'%s' planou até chegar no chão e aterrissou.\n", getNome());
                    setZ(0);
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
    public void executarTarefa() {
        tamanhoAsa = -getTamanhoAsa();
        if (getTamanhoAsa() < 0)
            System.out.printf("O Robô '%s' agora irá subir enquanto se move.\n", getNome());
        else    
            System.out.printf("O Robô '%s' agora irá descer enquanto se move.\n", getNome());
    }

    @Override
    public String getNomeTarefa() {
        return "'trocar sentido do planador'";
    }

    // Método 'intermediário' para impedir que o Robô use o 'descer' após se mover no chão (altitude 0)
    public void descerPlanando(int metros) throws RoboDesligadoException {
        if (getZ() != 0) {
            descer(metros);
        }
    }

    public void setTamanhoAsa(int tamanhoAsa) {
        if (tamanhoAsa <= 100) {     // Tamanho máximo da asa de 100
            this.tamanhoAsa = tamanhoAsa;
            System.out.printf("Tamanho da asa alterado para %d.\n", this.tamanhoAsa);
        } else {
            this.tamanhoAsa = 100;
            System.out.print("Tamanho da asa alterado para 100 (máximo).\n");
        }
    }

    public int getTamanhoAsa() {
        return tamanhoAsa;
    }
}
