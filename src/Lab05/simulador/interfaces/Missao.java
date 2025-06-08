package simulador.interfaces;

import simulador.Robo;
import simulador.excecoes.RoboDesligadoException;
import simulador.Ambiente;

public interface Missao {
    void executar(Robo robo, Ambiente ambiente) throws RoboDesligadoException;
    String getNome();
}