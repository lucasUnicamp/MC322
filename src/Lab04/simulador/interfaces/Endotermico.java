package simulador.interfaces;

import simulador.excecoes.RoboDesligadoException;

public interface Endotermico {
    void moverParaQuente() throws RoboDesligadoException; // move para o ponto mais quente possível
}
