package cliente;

import java.util.Scanner;

import simulador.Ambiente;
import simulador.CentralComunicacao;
import simulador.Obstaculo;
import simulador.RoboAereo;
import simulador.RoboPlanador;
import simulador.RoboPreguica;
import simulador.RoboSatelite;
import simulador.RoboTerrestre;
import simulador.RoboXadrez;
import simulador.SensorObstaculo;
import simulador.SensorTemperatura;
import simulador.TipoObstaculo;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        CentralComunicacao central = new CentralComunicacao();
        Ambiente salaTeste = new Ambiente(50, 50, 50, 3, central);        // Cria o ambiente para testes
        salaTeste.adicionarEntidade(new Obstaculo(10, 20, 20, 30, TipoObstaculo.ESTATUA_DE_ELEFANTE, salaTeste));
        salaTeste.adicionarEntidade(new Obstaculo(35, 30, 45, 50, TipoObstaculo.TORRE_DE_BABEL, salaTeste));
        salaTeste.adicionarEntidade(new Obstaculo(30, 5, 40, 10, TipoObstaculo.THE_BEAN, salaTeste));

        /**
         * TESTES MANUAIS
         * 
         */
        System.out.print("\n## CRIACAO ROBÔS ################################");
        // Instanciamento em massa de robôs e adição de sensores por composição (sensor eh instanciado dentro do robô, logo não existe fora dele)
        RoboTerrestre roboTerrestre = new RoboTerrestre("Beta", "RT01", 25, 25, salaTeste, 60);     // Cria o robô terrestre genérico
        roboTerrestre.adicionarSensor(new SensorObstaculo(10, salaTeste));
        roboTerrestre.adicionarSensor(new SensorObstaculo(30, salaTeste));
        roboTerrestre.getDescricao();
        RoboXadrez roboXadrez = new RoboXadrez("Theta", "RT02", 40, 20, salaTeste, 6, 1);       // Cria o robô terrestre do tipo xadrez
        roboXadrez.adicionarSensor(new SensorObstaculo(5, salaTeste));
        roboXadrez.getDescricao();
        RoboPreguica roboPreguica = new RoboPreguica("Delta", "RT03", 30, 42, salaTeste, 25, 1);        // Cria o robô terrestre do tipo preguiça
        roboPreguica.adicionarSensor(new SensorTemperatura(20, salaTeste));
        roboPreguica.getDescricao();
        RoboAereo roboAereo = new RoboAereo("Gama", "RA01", 5, 5, salaTeste, 40, 80);      // Cria o robô aéreo genérico
        roboAereo.adicionarSensor(new SensorObstaculo(40, salaTeste));
        roboAereo.adicionarSensor(new SensorTemperatura(10, salaTeste));
        RoboPlanador roboPlanador = new RoboPlanador("Phi", "RA02", 5, 40, salaTeste, 0, 80, 50);      // Cria o robô aéreo do tipo planador
        roboPlanador.adicionarSensor(new SensorObstaculo(20, salaTeste));
        roboPlanador.getDescricao();
        RoboSatelite roboSatelite = new RoboSatelite("Sigma", "RA03", 30, 40, salaTeste, 10, 50, 30, 0);        // Cria o robô aéreo do tipo satélite
        roboSatelite.adicionarSensor(new SensorObstaculo(50, salaTeste));
        roboSatelite.adicionarSensor(new SensorTemperatura(50, salaTeste));
        roboSatelite.getDescricao();

        /**
         * TESTES INTERATIVOS
         * 
         * Aqui o usuário pode fazer quantos testes quiser rapidamente, uma vez que ações podem ser feitas uma logo após a outra.
         */
        Menu menu = new Menu(salaTeste, scan);
        menu.iniciarMenuPrincipal();

        scan.close();
    }
}
