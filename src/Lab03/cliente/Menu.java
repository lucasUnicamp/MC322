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
import simulador.SensorObstaculo;
import simulador.SensorTemperatura;

public class Menu {
    private final Ambiente salaTeste;
    private final Scanner scan;

    public Menu(Ambiente ambiente, Scanner scan){
        salaTeste = ambiente;
        this.scan = scan;
    }

    public void iniciarMenu() {
        int acao;
        int maximoAcoes;
        int indiceRobo;
        while (true) {
            indiceRobo = escolherRobo(salaTeste, scan);
            
            if(indiceRobo == -1) {
                break;
            }

            Robo roboUsado = salaTeste.robosAtivos.get(indiceRobo);
            acao = 0;
            
            while (acao != -1) {
                maximoAcoes = mostrarAcoes(roboUsado);
                acao = lerAcao(roboUsado, maximoAcoes, scan);
                realizarAcao(roboUsado, acao, scan);    
            }
        }
    }

    // Funcao para leitura da entrada de escolha do robo
    public static int escolherRobo(Ambiente ambiente, Scanner scan) {
        int indiceRobo;
        
        // Loop "infinito" para lidar com entradas invalidas
        while(true) {
                for (int i = 0; i < ambiente.robosAtivos.size(); i++) {
                    Robo robo = ambiente.robosAtivos.get(i);
                    System.out.printf("Robo %d :: %s '%s'\n", i, robo.getClass().getSimpleName(), robo.getNome());
                }
                System.out.println("Digite -1 para encerrar o programa.\n");

                System.out.print("Digite o numero do robo que quer usar: ");
                indiceRobo = scan.nextInt();  
                
                if(indiceRobo < ambiente.robosAtivos.size() && indiceRobo >= -1) 
                    break;
                else 
                    System.out.println("Digite um numero valido.\n");
        }
        System.out.println("");
        return indiceRobo;
    }
    
    
     // Exibe as acoes possiveis para o robo especificado
    public static int mostrarAcoes(Robo robo) {
        int maximoAcoes = 0;
        System.out.println("[-1] :: voltar");
        
        if (robo instanceof Robo) {
            System.out.println("[0] :: informacoes");
            System.out.println("[1] :: mover robo");
            System.out.println("[2] :: usar sensores");
            maximoAcoes = 2;
        }
        if (robo instanceof RoboTerrestre) {
            System.out.println("[3] :: aumentar velocidade");
            System.out.println("[4] :: diminuir velocidade");
            maximoAcoes = 4;
        }
        if (robo instanceof RoboXadrez) {
            System.out.println("[5] :: mudar peça");
            maximoAcoes = 5;
        }
        if (robo instanceof RoboPreguica) {
            System.out.println("[5] :: descançar");
            maximoAcoes = 5;
        }
        if (robo instanceof RoboAereo) {
            System.out.println("[3] :: subir");
            System.out.println("[4] :: descer");
            maximoAcoes = 4;
        }
        if (robo instanceof RoboPlanador) {
            System.out.println("[5] :: mudar tamanho da asa");
            maximoAcoes = 5;
        }
        if (robo instanceof RoboSatelite) {
            System.out.println("[5] :: carregar");
            System.out.println("[6] :: descarregar");
            maximoAcoes = 6;
        }
        return maximoAcoes;
    }

    /**
     * Pega a entrada do usuario para qual acao do robo ele quer fazer
     */
    public static int lerAcao(Robo robo, int maximoAcoes, Scanner scan) {
        int acao = scan.nextInt();
        if(acao <= maximoAcoes && acao >= -1) 
            return acao;
        else {
            System.out.println("\u001B[31mAcao invalida. Tente novamente.\u001B[0m\n");
            mostrarAcoes(robo);
            return lerAcao(robo, maximoAcoes, scan);
        }
    }

    public static void acaoSensor(Robo robo, Scanner scan) {
        int indiceSensor;
        int n_sensores;
        while (true) {
            System.out.println("[-1] voltar");
            robo.mostrarSensores();

            n_sensores = 0;
            if(robo.sensores != null)
                n_sensores = robo.sensores.size();
            if(n_sensores == 0) {
                indiceSensor = -1;
                System.out.println("Nao há sensores nesse robo");
                break;
            } 

            indiceSensor = scan.nextInt();
            
            
            if(indiceSensor == -1)
                break;
            if(indiceSensor >= 0 && indiceSensor < n_sensores)
                break;
            else
                System.out.println("\u001B[31mSensor invalido digitado\u001B[0m");
        }
        if(indiceSensor == -1) 
            return;
        System.out.print("[int] Qual coordenada x gostaria de monitorar? ");
        int posicaoX = scan.nextInt();
        System.out.print("[int] Qual coordenada y gostaria de monitorar? ");
        int posicaoY = scan.nextInt();
        int monitoramento;
        if(robo.sensores.get(indiceSensor) instanceof SensorObstaculo) {
            SensorObstaculo sensorUsado = ((SensorObstaculo) robo.sensores.get(indiceSensor));
            monitoramento = sensorUsado.monitorar(posicaoX, posicaoY);
            switch (monitoramento) {
                case 0:
                    System.out.println("Nenhum obstáculo detectado nesse ponto");
                    break;
                case 1:
                    System.out.println("Há um obstáculo nesse ponto");
                    break;
                case 2:
                    System.out.println("Ponto fora dos limites do ambiente");
                    break;
                case 3:
                    System.out.println("Ponto fora do raio de detecçao do sensor");
                    break;
            }
        }

        if(robo.sensores.get(indiceSensor) instanceof SensorTemperatura) {
            SensorTemperatura sensorUsado = ((SensorTemperatura) robo.sensores.get(indiceSensor));
            monitoramento = sensorUsado.monitorar(posicaoX, posicaoY);
            switch (monitoramento) {
                case 1:
                    break;
                case 2:
                    System.out.println("Ponto fora dos limites do ambiente");
                    break;
                case 3:
                    System.out.println("Ponto fora do raio de detecçao do sensor");
                    break;
            }
        }
    }
    public static void realizarAcao(Robo robo, int acao, Scanner scan) {
        switch (acao) {
            case 0:
                robo.info();
                break;

            case 1:
                System.out.print("[int] Quanto quer andar na horizontal (eixo x)? ");
                int deltaX = scan.nextInt();
                System.out.print("[int] Quanto quer andar na vertical (eixo y)? ");
                int deltaY = scan.nextInt();
                robo.mover(deltaX, deltaY);
                break;
            
            case 2:
                acaoSensor(robo, scan);
                break;

            case 3:
                if (robo instanceof RoboTerrestre) {
                    System.out.print("[int] Em quanto quer aumentar a velocidade? ");
                    int vlc = scan.nextInt();
                    ((RoboTerrestre) robo).aumentarVelocidade(vlc);
                }
                else if (robo instanceof RoboAereo) {
                    System.out.print("[int] Quantos metros quer subir? ");
                    int metros = scan.nextInt();
                    ((RoboAereo) robo).subir(metros);
                }
                break;
                
            case 4:
                if (robo instanceof RoboTerrestre) {
                    System.out.print("[int] Em quanto quer diminuir a velocidade? ");
                    int vlc = scan.nextInt();
                    ((RoboTerrestre) robo).diminuirVelocidade(vlc);
                }
                else if (robo instanceof RoboAereo) {
                    System.out.print("[int] Quantos metros quer descer? ");
                    int metros = scan.nextInt();
                    ((RoboAereo) robo).descer(metros);
                }
                break;

            case 5:
                if (robo instanceof RoboXadrez) {
                    ((RoboXadrez) robo).alteraTipoMovimento();
                    if (((RoboXadrez)robo).getTipoMovimento() == 1)
                        System.out.println("Tipo de peça mudado para Cavalo\n");
                    else
                        System.out.println("Tipo de peça mudado para Peao\n");
                }

                if (robo instanceof RoboPreguica) {
                    ((RoboPreguica)robo).descansar();
                }
                
                break;
            case 6:
                
                break;
        }
    }
}
