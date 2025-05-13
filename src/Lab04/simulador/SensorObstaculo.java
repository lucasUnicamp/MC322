package simulador;

public class SensorObstaculo extends Sensor {

    public SensorObstaculo(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }

    /**
     * Nesse sensor, o monitorar checa se ha um obstaculo no ponto especificado
     */
    public int monitorar(int posX, int posY) {
        if (!getAmbiente().dentroDosLimites(posX, posY))
            return 2;       // Fora do ambiente
        else if (!dentroDoRaio(posX, posY))
            return 3;       // Fora do alcance
        else {
            if (checarObstaculoPosicao(posX, posY)) {
                exibirObsPonto(posX, posY);
                return 1;       // Obstaculo detectado
            }
            exibirNenhumObs(posX, posY);
            return 0;       // Obstaculo não detectado
        }
    }

    public void exibirObsPonto(int posX, int posY) {
        System.out.printf("O sensor detectou um obstaculo no ponto (%d, %d).\n", posX, posY);
    }

    public void exibirNenhumObs(int posX, int posY) {
        System.out.printf("O sensor nao detectou nenhum obstaculo no ponto (%d, %d).\n", posX, posY);
    }

    /**
     * Checa se na posicao especificada ha um obstaculo, independente do raio do sensor
     * @param x coordenada x da posicao especificada
     * @param y coordenada y da posicao especificada
     * @return true ou false dependendo se ha ou nao um obstaculo
     */
    public boolean checarObstaculoPosicao(int x, int y) {
        // Percorre a ArrayList de obstaculos e checa se ha um obstaculo na posicao dada
        for (Obstaculo obstaculo:getAmbiente().obstaculos) {
            if (obstaculo.getTipoObstaculo().getAltura() > getAltitude() &&
                obstaculo.getPosicaoX1() <= x &&
                obstaculo.getPosicaoX2() >= x &&
                obstaculo.getPosicaoY1() <= y &&
                obstaculo.getPosicaoY2() >= y ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overload da funcao anterior, diferenca eh que essa eh usada para Robos Aereos e
     * recebe uma altitude para comparar com a do obstaculo; que nao necessariamente eh a 
     * propria altitude do robo (ja que isso ja eh checado no 'if'), mas uma altura apos 
     * certo movimento de descer, por exemplo
     */
    public boolean checarObstaculoPosicao(int x, int y, int altititude) {
        // Percorre a ArrayList de obstaculos e checa se ha um obstaculo na posicao dada
        for (Obstaculo obstaculo:getAmbiente().obstaculos) {
            if (obstaculo.getTipoObstaculo().getAltura() >= altititude &&
                obstaculo.getPosicaoX1() <= x &&
                obstaculo.getPosicaoX2() >= x &&
                obstaculo.getPosicaoY1() <= y &&
                obstaculo.getPosicaoY2() >= y ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checa se ha algum obstaculo em ambos os caminhos possiveis a serem percorridos ate o ponto dado.
     * Esses caminhos estao explicados no README, mas basicamente sao: mover totalmente em uma das
     * duas direcoes e depois na outra.
     * e depois na outra.
     * @param posX posicao horizontal do Robo no momento
     * @param posY posicao vertical do Robo no momento
     * @param deltaX o quanto se quer mover na horizontal
     * @param deltaY o quanto se quer mover na vertical
     * @return 0 caso caso o ponto em que se quer chegar esteja fora do raio do sensor, -1 caso haja
     * obstaculos em ambos os caminhos ou 1 caso qualquer um dos dois caminhos estiver livre 
     */
    public int checarObstaculoCaminho(int posX, int posY, int deltaX, int deltaY) {
        boolean caminhoCima = true, caminhoBaixo = true;
        int novoX = posX + deltaX;
        int novoY = posY + deltaY;

        if (!dentroDoRaio(novoX, novoY)) {
            return 0;
        }

        // Checa se a linha reta da componente horizontal do movimento, partindo da posição atual do robô 
        // ou partindo da posição do robô após andar toda sua componente vertical, contém algum obstáculo;
        // O loop eh interrompido se ambos os caminhos tiverem um obstáculo;
        if (deltaX > 0) {
            for (int a = 0; (caminhoBaixo || caminhoCima) && a <= deltaX; a++) {
                if (checarObstaculoPosicao(posX + a, posY)) 
                    caminhoCima = false;
                if (checarObstaculoPosicao(posX + a, posY + deltaY)) 
                    caminhoBaixo = false;
            }
        }
        else {
            for (int b = 0; (caminhoBaixo || caminhoCima) && b >= deltaX; b--) {
                if (checarObstaculoPosicao(posX + b, posY)) 
                    caminhoCima = false;
                if (checarObstaculoPosicao(posX + b, posY + deltaY)) 
                    caminhoBaixo = false;
            }
        }

        // Checa se a linha reta da componente vertical do movimento, partindo da posição atual do robô 
        // ou partindo da posição do robô após andar toda sua componente horizontal, contém algum obstáculo;
        // O loop eh interrompido se ambos os caminhos tiverem um obstáculo;
        if (deltaY > 0) {
            for (int c = 0; (caminhoBaixo || caminhoCima) && c <= deltaY; c++) {
                if (checarObstaculoPosicao(posX, posY + c)) 
                    caminhoBaixo = false;
                if (checarObstaculoPosicao(posX + deltaX, posY + c))
                    caminhoCima = false;
            }
        }
        else {
            for (int d = 0; (caminhoBaixo || caminhoCima) && d >= deltaY; d--) {
                if (checarObstaculoPosicao(posX, posY + d))
                    caminhoBaixo = false;
                if (checarObstaculoPosicao(posX + deltaX, posY + d))
                    caminhoCima = false;
            }
        }

        if(caminhoBaixo || caminhoCima) {
            return 1;
        } else {
            return -1;
        }
    }

    public String nomeDoSensor() {
        return "Sensor de Obstaculo";
    }
}
