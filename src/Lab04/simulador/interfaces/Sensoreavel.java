package simulador.interfaces;

import simulador.excecoes.RoboDesligadoException;

public interface Sensoreavel {
    void acionarSensores() throws RoboDesligadoException;
}
