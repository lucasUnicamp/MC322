package simulador.interfaces;

import simulador.excecoes.RoboDesligadoException;

/**
 * Aciona todos os sensores e checa todas as posições adjacentes ao robô, como um quadrado ao seu redor
 */
public interface Sensoreavel {
    void acionarSensores() throws RoboDesligadoException;
}
