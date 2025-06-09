package simulador;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;

import simulador.excecoes.RoboDesligadoException;
import simulador.interfaces.Missao;

import java.lang.Math;

// Procura um obstáculo no raio de busca do sensor, caso não exista anda para tentar encontrar o obstáculo
public class MissaoBuscaObstaculo implements Missao{
    public void executar(Robo robo, Ambiente ambiente) throws RoboDesligadoException{
        try {
            Formatter output = new Formatter(new FileWriter("logs/log.txt", true));
            int indiceSensor = robo.temSensorTipo("SensorObstaculo");

            output.format("INÍCIO MISSÃO BUSCAR OBSTÁCULO:\n");
            output.flush();

            if(indiceSensor != -1) {
                SensorObstaculo sensor = (SensorObstaculo) robo.sensores.get(indiceSensor);
                int [] coordenadaObstaculo = sensor.procuraObstaculoRaio();
                output.format("Usando sensor de obstáculo no raio...\n");
                output.flush();

                if(coordenadaObstaculo[0] == -1) {
                    executarRecursivo(robo, output, 1, sensor);
                } else {
                    output.format("Obstáculo encontrado em (%d, %d)\n\n", coordenadaObstaculo[0], coordenadaObstaculo[1]);
                    System.out.printf("Obstáculo encontrado em (%d, %d)\n", coordenadaObstaculo[0], coordenadaObstaculo[1]);
                }
            } else {
                System.out.printf("Sem sensor de obstáculo no robô %s\n", robo.getID());
            }

            output.flush();
            output.close();
        } catch(IOException erro) {
            System.err.println(erro.getMessage());
        }
    }

    // Anda até encontrar um obstáculo ou até ter tentado mais de 10 vezes;
    private void executarRecursivo(Robo robo, Formatter output, int tentativa, SensorObstaculo sensor) throws RoboDesligadoException{
        if(tentativa >= 10) {
            output.format("O robô andou muito e não achou nenhum obstáculo.\n");
            System.out.println("O robô andou muito e não achou nenhum obstáculo.");
            return;
        }
        int deslocamentoX = (int) ((Math.random() * sensor.getRaio()*2) - sensor.getRaio()); // deslocamento aleatório em x
        int deslocamentoY = (int) ((Math.random() * sensor.getRaio()*2) - sensor.getRaio()); // deslocamento aleatório em y
        robo.moverPara(robo.getX() + deslocamentoX, robo.getY() + deslocamentoY);
        int [] coordenadaObstaculo = sensor.procuraObstaculoRaio();
        output.format("Usando sensor de obstáculo no raio...\n");
        output.flush();

        if(coordenadaObstaculo[0] == -1) {
            executarRecursivo(robo, output, tentativa + 1, sensor);
        } else {
            output.format("Obstáculo encontrado em (%d, %d). Robô está agora em (%d, %d).\n\n", coordenadaObstaculo[0], coordenadaObstaculo[1], robo.getX(), robo.getY());
            System.out.printf("Obstáculo encontrado em (%d, %d). Robô está agora em (%d, %d).\n", coordenadaObstaculo[0], coordenadaObstaculo[1], robo.getX(), robo.getY());
        }
        return;
    }

    public String getNome() {
        return "'buscar obstáculos'";
    }
}
