package cliente;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

import simulador.Ambiente;
import simulador.CentralComunicacao;
import simulador.MissaoBuscaObstaculo;
import simulador.MissaoPatrulhar;
import simulador.Obstaculo;
import simulador.RoboAereo;
import simulador.RoboPlanador;
import simulador.RoboPreguica;
import simulador.RoboRebelde;
import simulador.RoboSatelite;
import simulador.RoboTerrestre;
import simulador.RoboToupeira;
import simulador.RoboXadrez;
import simulador.SensorObstaculo;
import simulador.SensorTemperatura;
import simulador.TipoObstaculo;
import simulador.excecoes.RoboDesligadoException;

public class Main {

    public static void main(String[] args) {
        try {
            Formatter arquivoLog  = new Formatter(new FileWriter("logs/log.txt", false));
            arquivoLog.format("################################ LOG DOS AGENTES INTELIGENTES ################################\n");
            arquivoLog.flush();
            arquivoLog.close();
        } catch (IOException erro) {
            System.err.println(erro.getMessage());
        }

        Scanner scan = new Scanner(System.in);
        CentralComunicacao central = new CentralComunicacao();
        Ambiente salaTeste = new Ambiente(50, 50, 50, 5, central);        // Cria o ambiente para testes
        salaTeste.adicionarEntidade(new Obstaculo(35, 30, 45, 50, TipoObstaculo.TORRE_DE_BABEL, salaTeste));
        salaTeste.adicionarEntidade(new Obstaculo(5, 40, 13, 48, TipoObstaculo.CICLO_BASICO, salaTeste));
        salaTeste.adicionarEntidade(new Obstaculo(2, 10, 4, 23, TipoObstaculo.EUCALIPTO, salaTeste));
        salaTeste.adicionarEntidade(new Obstaculo(9, 3, 15, 10, TipoObstaculo.PEDRA, salaTeste));
        salaTeste.adicionarEntidade(new Obstaculo(10, 20, 20, 30, TipoObstaculo.ESTATUA_DE_ELEFANTE, salaTeste));
        salaTeste.adicionarEntidade(new Obstaculo(30, 5, 45, 8, TipoObstaculo.THE_BEAN, salaTeste));

        /**
         * TESTES MANUAIS
         * 
         * Como os métodos foram formatados pensando em seu uso pelo menu ao invés de chamadas individuais, além do fato que alguns jogam exceções que necessitariam 
         * de 'try's e 'catch's, os testes manuais apenas testam a inicialização dos robôs. Porém, as ações dos robôs podem ser facilmente testadas pelo menu interativo
         * por comandos simples.
         */
        System.out.print("\n## CRIACAO ROBÔS ################################");
        // Instanciamento em massa de robôs e adição de sensores por composição (sensor eh instanciado dentro do robô, logo não existe fora dele)
        RoboTerrestre roboTerrestre = new RoboTerrestre("Beta", "RT01", 25, 25, salaTeste, 60);     // Cria o robô terrestre genérico
        roboTerrestre.adicionarSensor(new SensorObstaculo(10, salaTeste));
        roboTerrestre.adicionarSensor(new SensorObstaculo(30, salaTeste));      // Erro de tentar adicionar dois sensores iguais
        roboTerrestre.adicionarSensor(new SensorTemperatura(10, salaTeste));
        roboTerrestre.getDescricao();
        RoboXadrez roboXadrez = new RoboXadrez("Theta", "RT02", 40, 20, salaTeste, 6, 1);       // Cria o robô terrestre do tipo xadrez
        roboXadrez.adicionarSensor(new SensorObstaculo(15, salaTeste));
        roboXadrez.adicionarSensor(new SensorTemperatura(25, salaTeste));
        roboXadrez.getDescricao();
        RoboPreguica roboPreguica = new RoboPreguica("Delta", "RT03", 30, 42, salaTeste, 25, 10);        // Cria o robô terrestre do tipo preguiça
        roboPreguica.adicionarSensor(new SensorTemperatura(20, salaTeste));
        roboPreguica.getDescricao();
        RoboAereo roboAereo = new RoboAereo("Gama", "RA01", 5, 5, salaTeste, 40, 80);      // Cria o robô aéreo genérico
        roboAereo.adicionarSensor(new SensorObstaculo(40, salaTeste));
        roboAereo.adicionarSensor(new SensorTemperatura(10, salaTeste));
        RoboPlanador roboPlanador = new RoboPlanador("Phi", "RA02", 5, 40, salaTeste, 0, 80, 50);      // Cria o robô aéreo do tipo planador em uma posição inválida. É mudado para uma aleatória
        roboPlanador.adicionarSensor(new SensorObstaculo(20, salaTeste));
        roboPlanador.getDescricao();
        RoboSatelite roboSatelite = new RoboSatelite("Sigma", "RA03", 30, 40, salaTeste, 10, 50, 30, 0);        // Cria o robô aéreo do tipo satélite em uma altitude inválida. É posto na altitude 0
        roboSatelite.adicionarSensor(new SensorObstaculo(50, salaTeste));
        roboSatelite.adicionarSensor(new SensorTemperatura(50, salaTeste));
        roboSatelite.getDescricao();
        RoboToupeira roboTopeira = new RoboToupeira("Kris", "AI01", 25, 25, salaTeste);
        roboTopeira.adicionarSensor(new SensorObstaculo(5, salaTeste));
        RoboRebelde roboRebelde = new RoboRebelde("Susie", "AI02", 30, 35, salaTeste);
        roboRebelde.adicionarSensor(new SensorObstaculo(10, salaTeste));
        // roboTopeira.ligar();
        // roboRebelde.ligar();
        // try{
        //     roboTopeira.moverPara(10, 40);
        //     roboTopeira.moverPara(40, 19);
        //     roboTopeira.setMissao(new MissaoBuscaObstaculo());
        //     roboRebelde.setMissao(new MissaoPatrulhar());
        //     roboTopeira.executarMissao(salaTeste);
        //     roboRebelde.executarMissao(salaTeste);
        // } catch (RoboDesligadoException erro) {
        //     System.err.println(erro.getMessage());
        // }

        /**
         * TESTES INTERATIVOS
         */
        Menu menu = new Menu(salaTeste, scan);
        menu.iniciarMenuPrincipal();

        scan.close();
    }
}
