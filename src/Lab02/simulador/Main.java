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
        };      // Adiciona uma série de obstáculos no ambiente nas coordenadas {x, y} especificadas 

        Robo roboNormal = new Robo("Alfa", 0, 0, salaTeste);        // Cria o robô genérico
        RoboTerrestre roboTerra = new RoboTerrestre("Beta", 50, 50, salaTeste, 60);     // Cria o robô terrestre genérico
        RoboXadrez roboXadrez = new RoboXadrez("Theta", 30, 20, salaTeste, 6, 1);       // Cria o robô terrestre do tipo xadrez
        //
        RoboAereo roboAr = new RoboAereo("Gama", 100, 100, salaTeste, 40, 80);      // Cria o robô aéreo genérico
        RoboPlanador roboPlanador = new RoboPlanador("Phi", 60, 90, salaTeste, 0, 80, 50);      // Cria o robo aéreo do tipo planador
        //
        
        salaTeste.adicionarObstaculos(obstaculos);

        /* 
         * A seguir encontra-se vários comandos que testam o movimento dos robôs em diversas condições, 
         * como passar por obstáculos, ser barrado por obstáculos, ir para bordas, tentar fazer movimentos 
         * não permitidos, etc  
        */
        System.out.printf("\n********************************************************************\n");
        roboNormal.info();      // Inicia testes com robô genérico
        roboNormal.mover(10, 30);       // Deve falhar devido aos obstáculos
        roboNormal.mover(40, 40);       // Deve sucesseder
        roboNormal.mover(-31, -12);     // Teste de 'deltas' negativos que deve falhar devido aos obstáculos
        roboNormal.mover(-5, 10);       // Teste de 'deltas' negativos que deve sucesseder 

        System.out.printf("********************************************************************\n");
        roboTerra.info();      //Inicia testes com robô terrestre
        roboTerra.mover(-15, -25);       // Deve sucesseder
        roboTerra.aumentarVelocidade(70);       // Aumenta a velocidade do robô
        roboTerra.mover(50, 50);        // Deve falhar devido ao limite de velocidade

        System.out.printf("********************************************************************\n");
        roboXadrez.info();      // Inicia testes como robô terrestre xadrez
        roboXadrez.mover(5, 0);     // Deve falhar por não ser um movimento em L
        roboXadrez.mover(2, 1);     // Deve sucesseder
        roboXadrez.setTipoMovimento(2);     // Muda o tipo de movimento para Peão
        roboXadrez.mover(10, 0);        // Deve falhar por não ser um movimento de 1 ou 2 posições
        roboXadrez.mover(0, 2);     // Deve falhar por não estar apontado na direção correta
        roboXadrez.setDirecao("Sul");       // Muda a direção do robô
        roboXadrez.mover(0, -2);        // Teste de 'deltas' negativo que deve sucesseder

        System.out.printf("********************************************************************\n");
        roboAr.info();      //Inicia testes com robô aéreo
        roboAr.subir(20);       // Deve sucesseder
        roboAr.subir(70);       // Deve falhar devido ao limite de altura
        roboAr.mover(-91, -72);      // Teste para ignorar obstáculos por estar no ar
        roboAr.descer(10);      // Deve sucesseder
        roboAr.descer(70);      // Deve falhar por bater no chão 
        roboAr.mover(1, 2);     // Deve falhar por estar no chão e encontrar um obstáculo

        System.out.printf("********************************************************************\n");
        roboPlanador.info();        // Inicia testes com robô aéreo planador
        roboPlanador.subir(80);     // Deve falhar devido ao tamanho das asas
        roboPlanador.subir(50);     // Deve sucesseder
        roboPlanador.mover(-30, -10);       // Deve sucesseder

        scan.close();
    }
}
