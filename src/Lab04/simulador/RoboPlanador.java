package simulador;

import java.lang.Math;

import simulador.excecoes.RoboDesligadoException;
import simulador.interfaces.Geologo;

public class RoboPlanador extends RoboAereo implements Geologo{
    private int tamanhoAsa;     // Quanto maior a asa, por mais tempo consegue planar e também consegue subir mais
    private int modoPlanar;     // Para identificar se está descendo (1) ou subindo (2) enquanto se move

    public RoboPlanador(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int tamanhoAsa){
        super(nome, id, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        setTamanhoAsa(tamanhoAsa);
        modoPlanar = 1;
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
            moverPlanando(((120 - tamanhoAsa)*deslocamento)/100);      // Cai lentamente quando deslocado
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
            System.out.println("Para 'subir negativamente', por favor use a função 'descer'.");
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
                if (indice == -1) {
                    System.out.println("Impossível descer com segurança, não há sensor de obstáculo.\n");
                    return;
                } 
                else
                    sensorObs = (SensorObstaculo) sensores.get(indice);

                // Compara a altitude do Robo com a distância ao chão (0)
                if (getZ() - metros >= 0 && !sensorObs.checarObstaculoPosicao(getX(), getY(), getZ() - metros)) {
                    if (getModoPlanar() == 1)
                        System.out.println("O Robô planou e desceu suavemente no processo.");
                    else if (getModoPlanar() == 2)
                        System.out.println("O Robô planou e subiu suavemente no processo.");
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

    public void identificarTamanhoObstaculo() throws RoboDesligadoException {
        if (estaLigado()) {
            for (int i = getX() - 1; i <= getX() + 1; i++) {
                for (int j = getY() - 1; j <= getY() + 1; j++) { 
                    for (int k = getZ() - 1; k <= getZ() + 1; k++) {
                        if (getAmbiente().estaOcupado(i, j, k)) {
                            for (Obstaculo obstaculo : getAmbiente().obstaculos) {
                                if ((obstaculo.getPosicaoX1() <= i && obstaculo.getPosicaoX2() >= i) &&
                                    (obstaculo.getPosicaoY1() <= j && obstaculo.getPosicaoY2() >= j)) {
                                        System.out.printf("\nObstáculo com largura %d, profundidade %d e altura %d encontrado nas adjacências do Robô.\n", 
                                        obstaculo.getLargura(), obstaculo.getProfundidade(), obstaculo.getAltura());
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            System.out.println("\nNenhum obstáculo encontrado ao redor do robô.");
        } else {
            System.out.println("");
            throw new RoboDesligadoException(getID());
        }
    }

    public void identificarTipoObstaculo() throws RoboDesligadoException {
        if (estaLigado()) {
            for(int i = getX() - 1; i <= getX() + 1; i++) {
                for (int j = getY() - 1; j <= getY() + 1; j++) {
                    for (int k = getZ() - 1; k <= getZ() + 1; k++) {
                        if (getAmbiente().estaOcupado(i, j, k)) 
                            for (Obstaculo obstaculo : getAmbiente().obstaculos) {
                                if ((obstaculo.getPosicaoX1() <= i && obstaculo.getPosicaoX2() >= i) &&
                                    (obstaculo.getPosicaoY1() <= j && obstaculo.getPosicaoY2() >= j)) {
                                        System.out.printf("\nObstáculo do tipo '%s' encontrado nas adjacências do Robô.\n", obstaculo.getTipoObstaculo().toString());
                                        return;
                                    }
                            }
                        }
                    }
                }
            System.out.println("\nNenhum obstáculo encontrado ao redor do robô.");
        } else {
            System.out.println("");
            throw new RoboDesligadoException(getID());
        }
    }

    /**
     * Muda o modo como o robô plana de perder altitude durante a movimentação para ganhar altitude durante a movimentação,
     * ou vice-versa
     */
    @Override
    public void executarTarefa() {
        setModoPlanar(getModoPlanar() == 1 ? 2 : 1);
        if (getTamanhoAsa() < 0)
            System.out.printf("\nO Robô '%s' agora irá subir enquanto se move.\n", getNome());
        else    
            System.out.printf("\nO Robô '%s' agora irá descer enquanto se move.\n", getNome());
    }

    @Override
    public String getNomeTarefa() {
        return "'trocar sentido do planador'";
    }

    // Método 'intermediário' para impedir que o Robô use o 'descer' após se mover no chão (altitude 0)
    public void moverPlanando(int metros) throws RoboDesligadoException {
        if (getZ() != 0) {
            if (getModoPlanar() == 1)
                descer(metros);
            else if (getModoPlanar() == 2)
                subir(metros);
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

    public void setModoPlanar(int modo) {
        if (modo == 1 || modo == 2)
            modoPlanar = modo;
        else
            modoPlanar = 1;
    }

    public int getTamanhoAsa() {
        return tamanhoAsa;
    }

    public int getModoPlanar() {
        return modoPlanar;
    }
}
