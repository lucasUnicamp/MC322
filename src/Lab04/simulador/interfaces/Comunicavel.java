package simulador.interfaces;

import simulador.excecoes.RoboDesligadoException;

public interface Comunicavel{
    /**
     * Tenta enviar uma mensagem de texto para outro robô 
     * @param destinatario Robô que vai receber a mensagem
     * @param mensagem mensagem de texto
     * @throws RoboDesligadoException acontece caso o robô que manda a mensagem esteja desligado
     */
    void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException;

    /**
     * Recebe uma mensagem de outro robô e a armazena na central de comunicações do ambiente que o robô está
     * @param remetente Robô que recebeu a mensagem
     * @param mensagem mensagem de texto
     * @throws RoboDesligadoException acontece caso o robô que recebe a mensagem esteja desligado 
     */
    void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException;
}
