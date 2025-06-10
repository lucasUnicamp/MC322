package simulador.interfaces;

import simulador.robos.Robo;
import simulador.ambiente.Ambiente;
import simulador.excecoes.RoboDesligadoException;

public interface Missao {
    void executar(Robo robo, Ambiente ambiente) throws RoboDesligadoException;
    String getNome();
}