package cliente;

import java.util.Scanner;

import simulador.Ambiente;
import simulador.Obstaculo;
import simulador.Robo;
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

        Ambiente salaTeste = new Ambiente(50, 50, 3);        // Cria o ambiente para testes
        salaTeste.adicionarObstaculos(new Obstaculo(10, 20, 20, 30, TipoObstaculo.ESTATUA_DE_ELEFANTE));
        salaTeste.adicionarObstaculos(new Obstaculo(45, 30, 35, 50, TipoObstaculo.TORRE_DE_BABEL));

        Robo roboGenerico = new Robo("Alfa", 10, 15, salaTeste);        // Cria o robo generico
        RoboTerrestre roboTerrestre = new RoboTerrestre("Beta", 25, 25, salaTeste, 60);     // Cria o robo terrestre generico
        RoboXadrez roboXadrez = new RoboXadrez("Theta", 30, 20, salaTeste, 6, 1);       // Cria o robo terrestre do tipo xadrez
        RoboPreguica roboPreguica = new RoboPreguica("Delta", 10, 50, salaTeste, 25, 1);        // Cria o robo terrestre do tipo preguiça
        RoboAereo roboAereo = new RoboAereo("Gama", 0, 0, salaTeste, 40, 80);      // Cria o robo aereo generico
        RoboPlanador roboPlanador = new RoboPlanador("Phi", 40, 10, salaTeste, 0, 80, 50);      // Cria o robo aereo do tipo planador
        RoboSatelite roboSatelite = new RoboSatelite("Sigma", 20, 20, salaTeste, 10, 50, 30, 0);        // Cria o robo aereo do tipo satelite
        
        // Adiciona sensores aos robos arbitrariamente
        roboTerrestre.adicionarSensor(new SensorObstaculo(10, salaTeste));
        roboXadrez.adicionarSensor(new SensorObstaculo(5, salaTeste));
        roboPreguica.adicionarSensor(new SensorObstaculo(30, salaTeste));
        roboPreguica.adicionarSensor(new SensorTemperatura(20, salaTeste));
        roboAereo.adicionarSensor(new SensorTemperatura(10, salaTeste));
        roboPlanador.adicionarSensor(new SensorObstaculo(20, salaTeste));
        roboSatelite.adicionarSensor(new SensorObstaculo(50, salaTeste));
        roboSatelite.adicionarSensor(new SensorTemperatura(50, salaTeste));

        System.out.printf("**********************************************************************\n");
        roboGenerico.mover(10, 10);

        scan.close();
    }
}
