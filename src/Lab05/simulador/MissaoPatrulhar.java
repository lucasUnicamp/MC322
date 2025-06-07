package simulador;

import simulador.excecoes.RoboDesligadoException;
import simulador.interfaces.Missao;

public class MissaoPatrulhar implements Missao {
    public void executar(Robo robo, Ambiente ambiente) {
        int x = robo.getX();
        int y = robo.getY();

        try {
            System.out.printf("Executando missão de patrulha. '%s' vai tentar se mover em um quadrado de lado 10", robo.getNome());
            robo.moverPara(robo.getX(), robo.getY() + 10);
            robo.moverPara(robo.getX() + 10, robo.getY());
            robo.moverPara(robo.getX(), robo.getY() - 10);
            robo.moverPara(robo.getX() - 10, robo.getY());

            if (x == robo.getX() && y == robo.getY())
                System.out.println("A patrulha ocorreu com sucesso.");
            else
                System.out.println("O robô não consegiu fazer a patrulha completa.");
        } catch (RoboDesligadoException erro) {
            System.err.println(erro.getMessage());
        }
    }
}