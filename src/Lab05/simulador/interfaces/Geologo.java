package simulador.interfaces;

import simulador.excecoes.RoboDesligadoException;

public interface Geologo {
    /**
     * Identifica o tipo de obstáculo presente nas adjacências do robô
     */
    void identificarTipoObstaculo() throws RoboDesligadoException;

    /**
     * Identifica as dimensões do obstáculo presente nas adjacências do robô
     */
    void identificarTamanhoObstaculo() throws RoboDesligadoException;
}
