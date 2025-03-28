package simulador;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Ambiente salaTeste = new Ambiente(100, 100);        // Cria o ambiente para testes
        Scanner scan = new Scanner(System.in);
        int[][] obstaculos = {
            {9, 30},
            {10, 28},
            {0, 75},
            {50, 56},
            {98, 99},
            {40, 41},
        };

        Robo roboNormal = new Robo("Alfa", 0, 0, salaTeste);        // Cria o robô genérico
        RoboTerrestre roboTerra = new RoboTerrestre("Beta", 50, 50, salaTeste, 60);     // Cria o robô terrestre genérico
        RoboXadrez roboXadrez = new RoboXadrez("Theta", 30, 20, salaTeste, 6, 1);
        //
        RoboAereo roboAr = new RoboAereo("Gama", 100, 100, salaTeste, 40, 80);      // Cria o robô aéreo genérico
        RoboPlanador roboPlanador = new RoboPlanador("Phi", 60, 90, salaTeste, 0, 80, 50);
        //
        
        salaTeste.adicionarObstaculos(obstaculos);

        roboNormal.mover(10, 30);       // Teste que deve falhar devido aos obstáculos
        roboNormal.mover(40, 40);       // Teste que deve sucesseder
        roboNormal.mover(-31, -12);                   // Teste de 'deltas' negativos que deve falhar devido aos obstáculos
        roboNormal.mover(-5, 10);              // Teste de 'deltas' negativos que deve sucesseder 
        
        roboPlanador.subir(80);
        roboPlanador.subir(50);
        roboPlanador.mover(-30, -10);
        roboPlanador.mover(10, 40);

        roboXadrez.mover(5, 0);
        roboXadrez.mover(2, 1);
        roboXadrez.setTipoMovimento(2);
        roboXadrez.mover(10, 0);
        roboXadrez.mover(0, -2);
        roboXadrez.mover(0, 2);
        roboXadrez.setDirecao("Sul");
        roboXadrez.mover(0, -2);
        scan.close();
    }
}
