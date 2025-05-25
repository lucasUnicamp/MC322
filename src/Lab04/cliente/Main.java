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
        salaTeste.adicionarObstaculos(new Obstaculo(10, 20, 20, 30, TipoObstaculo.ESTATUA_DE_ELEFANTE));
        salaTeste.adicionarObstaculos(new Obstaculo(35, 30, 45, 50, TipoObstaculo.TORRE_DE_BABEL));
        salaTeste.adicionarObstaculos(new Obstaculo(30, 5, 40, 10, TipoObstaculo.THE_BEAN));

        /**
         * TESTES MANUAIS
         * 
         * Essas 4 situacoes devem gerar 4 avisos logo na criaçao dos robos.
         * O Robo Generico Alfa foi propositalmente colocado dentro de um obstaculo para testar a funcionalidade de recolocar robos em posicoes aleatorias;
         * O Robo Preguica Delta também foi posto em uma posicao invalida, fora do Ambiente, para o mesmo tipo de teste;
         * O Robo Satelite Sigma foi propositalemente posto numa altura menor do que a de orbita para testar se caia no chao;
         * 2 sensores iguais foram postos no Robo Generico Alfa para testar funcionalidade de rejeicao a sensores repetidos;
         * 
         * Acreditamos que esses sao os unicos 'casos' que podem ser testados que nao dependem de entradas de usuario (assim como foi pedido nas instruçoes do lab), 
         * ja que dependem de onde instanciamos os robos. Outros acontecimentos, como colidir com obstaculo, nao ter carga suficiente, monitorar uma posicao, etc,
         * podem todos serem feitos facilmente pelo menu interativo com poucas entradas. 
         */
        System.out.printf("\n********************************************CRIACAO**ROBOS********************************************");
        // Instanciamento em massa de robos e adicao de sensores por composiçao (sensor eh instanciado dentro do robo, logo nao existe fora dele)
        RoboTerrestre roboTerrestre = new RoboTerrestre("Beta", "RT01", 25, 25, salaTeste, 60);     // Cria o robo terrestre generico
        roboTerrestre.adicionarSensor(new SensorObstaculo(10, salaTeste));
        roboTerrestre.adicionarSensor(new SensorObstaculo(30, salaTeste));
        roboTerrestre.getDescricao();
        RoboXadrez roboXadrez = new RoboXadrez("Theta", "RT02", 40, 20, salaTeste, 6, 1);       // Cria o robo terrestre do tipo xadrez
        roboXadrez.adicionarSensor(new SensorObstaculo(5, salaTeste));
        roboXadrez.getDescricao();
        RoboPreguica roboPreguica = new RoboPreguica("Delta", "RT03", 60, 60, salaTeste, 25, 1);        // Cria o robo terrestre do tipo preguiça
        roboPreguica.adicionarSensor(new SensorObstaculo(30, salaTeste));
        roboPreguica.adicionarSensor(new SensorTemperatura(20, salaTeste));
        roboPreguica.getDescricao();
        RoboAereo roboAereo = new RoboAereo("Gama", "RA01", 5, 5, salaTeste, 40, 80);      // Cria o robo aereo generico
        roboAereo.adicionarSensor(new SensorTemperatura(10, salaTeste));
        RoboPlanador roboPlanador = new RoboPlanador("Phi", "RA02", 5, 40, salaTeste, 0, 80, 50);      // Cria o robo aereo do tipo planador
        roboPlanador.adicionarSensor(new SensorObstaculo(20, salaTeste));
        roboPlanador.getDescricao();
        RoboSatelite roboSatelite = new RoboSatelite("Sigma", "RA03", 30, 40, salaTeste, 10, 50, 30, 0);        // Cria o robo aereo do tipo satelite
        roboSatelite.adicionarSensor(new SensorObstaculo(50, salaTeste));
        roboSatelite.adicionarSensor(new SensorTemperatura(50, salaTeste));
        roboSatelite.getDescricao();

        System.out.println("");
        roboSatelite.enviarMensagem(roboXadrez, "E aí, bonitão");
        roboXadrez.enviarMensagem(roboSatelite, "Já fez o lab?");
        salaTeste.getCentral().exibirMensagens();

        /**
         * TESTES INTERATIVOS
         * 
         * Aqui o usuario pode fazer quantos testes quiser rapidamente, uma vez que açoes podem ser feitas uma logo apos a outra.
         */
        Menu menu = new Menu(salaTeste, scan);
        menu.iniciarMenu();

        scan.close();
    }
}
