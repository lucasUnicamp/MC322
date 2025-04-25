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
            if (checarObstaculoPosicao(posX, posY))
                return 1;       // Obstaculo detectado
            return 0;       // Obstaculo não detectado
        }
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

    public boolean checarObstaculoPosicao(int x, int y, int altititude) {
        // Percorre a ArrayList de obstaculos e checa se ha um obstaculo na posicao dada
        for (Obstaculo obstaculo:getAmbiente().obstaculos) {
            if (obstaculo.getTipoObstaculo().getAltura() <= altititude &&
                obstaculo.getPosicaoX1() <= x &&
                obstaculo.getPosicaoX2() >= x &&
                obstaculo.getPosicaoY1() <= y &&
                obstaculo.getPosicaoY2() >= y ) {
                return true;
            }
        }
        return false;
    }

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
            for (int a = 0; (caminhoBaixo || caminhoCima) && a < deltaX; a++) {
                if (checarObstaculoPosicao(posX + a, posY)) 
                    caminhoCima = false;
                if (checarObstaculoPosicao(posX + a, posY + deltaY)) 
                    caminhoBaixo = false;
            }
        }
        else {
            for (int b = 0; (caminhoBaixo || caminhoCima) && b > deltaX; b--) {
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
            for (int c = 0; (caminhoBaixo || caminhoCima) && c < deltaY; c++) {
                if (checarObstaculoPosicao(posX, posY + c)) 
                    caminhoBaixo = false;
                if (checarObstaculoPosicao(posX + deltaX, posY + c))
                    caminhoCima = false;
            }
        }
        else {
            for (int d = 0; (caminhoBaixo || caminhoCima) && d > deltaY; d--) {
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
