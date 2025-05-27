package simulador.interfaces;

import simulador.Robo;
import simulador.excecoes.RoboDesligadoException;
import simulador.excecoes.SemObstaculoDestrutivelException;

public interface Destrutivo {
    void destruirObstaculo(int x, int y) throws SemObstaculoDestrutivelException, RoboDesligadoException;
}
