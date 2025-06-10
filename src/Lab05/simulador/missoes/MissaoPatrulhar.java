package simulador.missoes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;

import simulador.ambiente.Ambiente;
import simulador.excecoes.RoboDesligadoException;
import simulador.interfaces.Missao;
import simulador.robos.Robo;

public class MissaoPatrulhar implements Missao {
    public void executar(Robo robo, Ambiente ambiente) {
        int x = robo.getX();
        int y = robo.getY();

        try {
            Formatter output = new Formatter(new FileWriter("logs/log.txt", true));
            
            output.format("Início da Missão Patrulhar pelo robô '%s':\n", robo.getNome());
            output.flush();

            System.out.println("\nExecutando missão de patrulha...");
            System.out.printf("'%s' vai tentar se mover em um quadrado de lado 10.\n", robo.getNome());
            robo.moverPara(robo.getX() + 10, robo.getY() + 10);
            robo.moverPara(robo.getX() - 10, robo.getY() - 10);

            if (x == robo.getX() && y == robo.getY()) {
                output.format("A Missão Patrulhar foi concluída com sucesso.\n");
                output.flush();
                System.out.println("A patrulha ocorreu com sucesso.");
            }
            else {
                output.format("A Missão Patrulhar não foi concluída com sucesso. '%s' não pode voltar à posição inicial.\n", robo.getNome());
                output.flush();
                System.out.println("O robô não consegiu fazer a patrulha completa.");
            }

            output.flush();
            output.close();
        } catch (IOException erro) {
            System.out.println(erro.getMessage());
        } catch (RoboDesligadoException erro) {
            System.err.println(erro.getMessage());
        }
    }

    public String getNome() {
        return "'patrulhar'";
    }
}