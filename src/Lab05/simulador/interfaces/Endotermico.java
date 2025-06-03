package simulador.interfaces;

import simulador.excecoes.RoboDesligadoException;

public interface Endotermico {
    /**
     * Move o robô que a implementa para o ponto de maior temperatura do ambiente
     * @throws RoboDesligadoException caso o robô esteja desligado
     */
    void moverParaQuente() throws RoboDesligadoException;
}
