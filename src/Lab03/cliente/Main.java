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
    
    // Funcao para leitura da entrada de escolha do robo
    public static int escolherRobo(Ambiente ambiente, Scanner scan) {
        int indiceRobo;
        
        // Loop "infinito" para lidar com entradas invalidas
        while(true) {
                for (int i = 0; i < ambiente.robosAtivos.size(); i++) {
                    Robo robo = ambiente.robosAtivos.get(i);
                    System.out.printf("Robo %d :: %s '%s'\n", i, robo.getClass().getName(), robo.getNome());
                }
                System.out.print("\nDigite o numero do robo que quer usar: ");
                indiceRobo = scan.nextInt();  
                if(indiceRobo < ambiente.robosAtivos.size() && indiceRobo >= 0) 
                    break;
                else 
                    System.out.println("Digite um numero valido.\n");
        }
        return indiceRobo;
    }

    public static void mostrarAcoes(Robo robo) {
        if (robo instanceof Robo) {
            System.out.printf("[0] :: informacoes\n");
            System.out.println("[1] :: mover robo\n");
            System.out.println("[2] :: usar sensores\n");
        }
        if (robo instanceof RoboTerrestre) {
            System.out.println("[3] :: aumentar velocidade\n");
            System.out.println("[4] :: diminuir velocidade\n");
        }
        if (robo instanceof RoboXadrez) {
            System.out.println("[5] :: mudar peça\n");
        }
        if (robo instanceof RoboPreguica) {
            System.out.println("[5] :: descançar\n");
        }
        if (robo instanceof RoboAereo) {
            System.out.println("[3] :: subir\n");
            System.out.println("[4] :: descer\n");
        }
        if (robo instanceof RoboPlanador) {
            System.out.println("[5] :: mudar tamanho da asa\n");
        }
        if (robo instanceof RoboSatelite) {
            System.out.println("[5] :: carregar\n");
            System.out.println("[6] :: descarregar\n");
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Ambiente salaTeste = new Ambiente(50, 50, 3);        // Cria o ambiente para testes
        salaTeste.adicionarObstaculos(new Obstaculo(10, 20, 20, 30, TipoObstaculo.ESTATUA_DE_ELEFANTE));
        salaTeste.adicionarObstaculos(new Obstaculo(45, 30, 35, 50, TipoObstaculo.TORRE_DE_BABEL));

        // Instancia robos
        Robo roboGenerico = new Robo("Alfa", 15, 25, salaTeste);        // Cria o robo generico
        RoboTerrestre roboTerrestre = new RoboTerrestre("Beta", 25, 25, salaTeste, 60);     // Cria o robo terrestre generico
        RoboXadrez roboXadrez = new RoboXadrez("Theta", 40, 20, salaTeste, 6, 1);       // Cria o robo terrestre do tipo xadrez
        RoboPreguica roboPreguica = new RoboPreguica("Delta", 60, 60, salaTeste, 25, 1);        // Cria o robo terrestre do tipo preguiça
        RoboAereo roboAereo = new RoboAereo("Gama", 5, 5, salaTeste, 40, 80);      // Cria o robo aereo generico
        RoboPlanador roboPlanador = new RoboPlanador("Phi", 5, 40, salaTeste, 0, 80, 50);      // Cria o robo aereo do tipo planador
        RoboSatelite roboSatelite = new RoboSatelite("Sigma", 30, 40, salaTeste, 10, 50, 30, 0);        // Cria o robo aereo do tipo satelite
        
        // Adiciona sensores aos robos arbitrariamente
        roboTerrestre.adicionarSensor(new SensorObstaculo(10, salaTeste));
        roboXadrez.adicionarSensor(new SensorObstaculo(5, salaTeste));
        roboPreguica.adicionarSensor(new SensorObstaculo(30, salaTeste));
        roboPreguica.adicionarSensor(new SensorTemperatura(20, salaTeste));
        roboAereo.adicionarSensor(new SensorTemperatura(10, salaTeste));
        roboPlanador.adicionarSensor(new SensorObstaculo(20, salaTeste));
        roboSatelite.adicionarSensor(new SensorObstaculo(50, salaTeste));
        roboSatelite.adicionarSensor(new SensorTemperatura(50, salaTeste));
        
        /**
         * Inicio dos testes manuais
         * O Robo Generico foi propositalmente colocado dentro de um obstaculo para testar a funcionalidade de recolocar robos em posicoes aleatorias;
         * O Robo Preguica também foi posto em uma posicao invalida (fora do Ambiente) para o mesmo tipo de teste;
         * O Robo Satelite foi propositalemente posto numa altura menor do que a de orbita para testar se caia no chao;
         * Essas 3 situacoes devem gerar 3 avisos logo na criacao dos robos.
         */
        System.out.printf("**********************************************************************\n");

        /**
         * Inicio dos testes interativos
         */
        while (true) {
            Robo roboUsado = salaTeste.robosAtivos.get(escolherRobo(salaTeste, scan));
            mostrarAcoes(roboUsado);
            break;
        }

        scan.close();
    }
}
