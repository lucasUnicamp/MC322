package cliente;

import java.util.Scanner;

import simulador.Ambiente;
import simulador.Robo;
import simulador.RoboAereo;
import simulador.RoboPlanador;
import simulador.RoboPreguica;
import simulador.RoboSatelite;
import simulador.RoboTerrestre;
import simulador.RoboXadrez;

public class Main {
    public static void main(String[] args) {
        int[][] obstaculos = {
            {9, 30},
            {10, 28},
            {0, 75},
            {50, 56},
            {98, 99},
            {40, 41},
            {80, 72}
        };      // Adiciona uma série de obstáculos no ambiente nas coordenadas {x, y} especificadas
        Scanner scan = new Scanner(System.in);
        Ambiente salaTeste = new Ambiente(50, 50, obstaculos.length);        // Cria o ambiente para testes
 
        RoboTerrestre roboTerrestre = new RoboTerrestre("Beta", 25, 25, salaTeste, 60);     // Cria o robo terrestre generico
        RoboXadrez roboXadrez = new RoboXadrez("Theta", 30, 20, salaTeste, 6, 1);       // Cria o robo terrestre do tipo xadrez
        RoboPreguica roboPreguica = new RoboPreguica("Delta", 10, 50, salaTeste, 25, 1);        // Cria o robo terrestre do tipo preguiça
        RoboAereo roboAereo = new RoboAereo("Gama", 0, 0, salaTeste, 40, 80);      // Cria o robo aereo generico
        RoboPlanador roboPlanador = new RoboPlanador("Phi", 40, 10, salaTeste, 0, 80, 50);      // Cria o robo aereo do tipo planador
        RoboSatelite roboSatelite = new RoboSatelite("Sigma", 20, 20, salaTeste, 10, 50, 30, 0);        // Cria o robo aereo do tipo satelite

        roboSatelite.info();
        roboSatelite.mover(10, 10);
        roboSatelite.subir(10);
        roboSatelite.carregar(10);
        roboSatelite.lancamento();
        roboSatelite.subir(20);
        roboSatelite.descer(5);
        scan.close();
    }
}
