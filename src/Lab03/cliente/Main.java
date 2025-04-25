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
        salaTeste.adicionarObstaculos(new Obstaculo(30, 10, 40, 5, TipoObstaculo.THE_BEAN));

        // Instancia robos
        Robo roboGenerico = new Robo("Alfa", 15, 25, salaTeste);        // Cria o robo generico
        RoboTerrestre roboTerrestre = new RoboTerrestre("Beta", 25, 25, salaTeste, 60);     // Cria o robo terrestre generico
        roboTerrestre.adicionarSensor(new SensorObstaculo(10, salaTeste));
        roboTerrestre.adicionarSensor(new SensorObstaculo(30, salaTeste));
        RoboXadrez roboXadrez = new RoboXadrez("Theta", 40, 20, salaTeste, 6, 1);       // Cria o robo terrestre do tipo xadrez
        roboXadrez.adicionarSensor(new SensorObstaculo(5, salaTeste));
        RoboPreguica roboPreguica = new RoboPreguica("Delta", 60, 60, salaTeste, 25, 1);        // Cria o robo terrestre do tipo preguiça
        roboPreguica.adicionarSensor(new SensorObstaculo(30, salaTeste));
        roboPreguica.adicionarSensor(new SensorTemperatura(20, salaTeste));
        RoboAereo roboAereo = new RoboAereo("Gama", 5, 5, salaTeste, 40, 80);      // Cria o robo aereo generico
        roboAereo.adicionarSensor(new SensorTemperatura(10, salaTeste));
        RoboPlanador roboPlanador = new RoboPlanador("Phi", 5, 40, salaTeste, 0, 80, 50);      // Cria o robo aereo do tipo planador
        roboPlanador.adicionarSensor(new SensorObstaculo(20, salaTeste));
        RoboSatelite roboSatelite = new RoboSatelite("Sigma", 30, 40, salaTeste, 10, 50, 30, 0);        // Cria o robo aereo do tipo satelite
        roboSatelite.adicionarSensor(new SensorObstaculo(50, salaTeste));
        roboSatelite.adicionarSensor(new SensorTemperatura(50, salaTeste));
        
        /**
         * TESTES MANUAIS
         * Essas 3 situacoes devem gerar 3 avisos logo na criaçao dos robos.
         * O Robo Generico Alfa foi propositalmente colocado dentro de um obstaculo para testar a funcionalidade de recolocar robos em posicoes aleatorias;
         * O Robo Preguica Delta também foi posto em uma posicao invalida, fora do Ambiente, para o mesmo tipo de teste;
         * O Robo Satelite Sigma foi propositalemente posto numa altura menor do que a de orbita para testar se caia no chao;
         * 2 sensores iguais foram postos no Robo Generico Alfa para testar 
         * 
         */
        System.out.printf("**********************************************************************\n");

        /**
         * TESTES INTERATIVOS
         */
        Menu menu = new Menu(salaTeste, scan);
        menu.iniciarMenu();

        scan.close();
    }
}
