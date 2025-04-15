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
        Ambiente salaTeste = new Ambiente(100, 100, obstaculos.length);        // Cria o ambiente para testes
 
        RoboTerrestre roboTerrestre = new RoboTerrestre("Beta", 50, 50, salaTeste, 60);     // Cria o robo terrestre generico
        RoboXadrez roboXadrez = new RoboXadrez("Theta", 30, 20, salaTeste, 6, 1);       // Cria o robo terrestre do tipo xadrez
        RoboPreguica roboPreguica = new RoboPreguica("Delta", 30, 80, salaTeste, 25, 1);        // Cria o robo terrestre do tipo preguiça
        RoboAereo roboAereo = new RoboAereo("Gama", 100, 100, salaTeste, 40, 80);      // Cria o robo aereo generico
        RoboPlanador roboPlanador = new RoboPlanador("Phi", 60, 90, salaTeste, 0, 80, 50);      // Cria o robo aereo do tipo planador
    


        scan.close();
    }
}
