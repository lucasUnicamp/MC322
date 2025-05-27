package simulador.interfaces;

import simulador.excecoes.RoboDesligadoException;

public interface Comunicavel{
    void enviarMensagem(Comunicavel destinatario, String mensagem)throws RoboDesligadoException;
    void receberMensagem(String mensagem) throws RoboDesligadoException;
}
