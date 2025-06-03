package simulador.interfaces;

import simulador.Robo;
import simulador.Ambiente;

public interface Missao {
    void executar(Robo robo, Ambiente ambiente);
}