package simulador.interfaces;

import simulador.excecoes.RoboDesligadoException;
import simulador.excecoes.SemObstaculoDestrutivelException;

public interface Destrutivo {
    /**
     * Permite destruir um obstáculo nas posições dada, contanto que esteja adjacente ao robô (caso esse seja 
     * Terrestre) ou o robô esteja no ar (caso esse seja Aéreo)
     * @param x posição horizontal que se quer destruir
     * @param y posição vertical que se quer destruir
     * @throws SemObstaculoDestrutivelException caso não haja um obstáculo na posição dada
     * @throws RoboDesligadoException caso o robô esteja desligado
     */
    void destruirObstaculo(int x, int y) throws SemObstaculoDestrutivelException, RoboDesligadoException;
}
