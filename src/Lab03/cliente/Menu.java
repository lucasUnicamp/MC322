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
import simulador.Obstaculo;

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
        int entradaRobo;

        while (true) {
            entradaRobo = escolherRobo(salaTeste, scan);
            
            if (entradaRobo == -1)
                break;
            else if (entradaRobo == -2) {
                imprimirAmbiente();
                continue;
            }

            Robo roboUsado = salaTeste.robosAtivos.get(entradaRobo);
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
        int entradaRobo;
        
        // Loop "infinito" para lidar com entradas invalidas
        while(true) {
                for (int i = 0; i < ambiente.robosAtivos.size(); i++) {
                    Robo robo = ambiente.robosAtivos.get(i);
                    System.out.printf("[%d] :: %s '%s'\n", i, robo.getClass().getSimpleName(), robo.getNome());
                }
                System.out.println("\n[-2] :: imprimir o ambiente.");
                System.out.println("[-1] :: encerrar o programa.\n");
                System.out.print("Digite um numero para prosseguir: ");
                entradaRobo = scan.nextInt();  
                
                // Se o numero da entrada esta entre as opçoes possiveis, eh valido
                if(entradaRobo < ambiente.robosAtivos.size() && entradaRobo >= -2) 
                    break;
                else 
                    System.out.println("Digite um numero valido.\n");
        }
        System.out.println("");
        return entradaRobo;
    }
    
    
    /**
     * Exibe as acoes possiveis para cada robo. Diferencia o tipo de robo pelo uso de 'instanceof' e tira proveito
     * do fato de robos serem filhos de outros para imprimir informaçoes mais gerais (como as do Robo, que sao validas
     * para todos os seus filhos) antes das mais especificas.
     * @param robo robo que foi escolhido na entrada
     * @return maximoAcoes, que sera usado para checar se a selecao da acao eh valida ou nao baseada em quantas opcoes
     * de acao aquele robo tem
     */
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
            System.out.println("[7] :: lançar");
            maximoAcoes = 7;
        }
        return maximoAcoes;
    }

    /**
     * Pega a entrada do usuario para qual acao do robo ele quer que realize acoes
     * @param robo robo que foi escolhido na entrada
     * @param maximoAcoes eh a quantidade de acoes que o robo pode fazer no total; como a entrada eh feita 
     * lendo inteiros, se for um numero maior que esse, sabemos que nao eh uma entrada valida
     * @param scan Scanner para ler entradas de usuario
     * @return inteiro representativo da acao escolhida pelo usuario para o robo fazer
     */
    public static int lerAcao(Robo robo, int maximoAcoes, Scanner scan) {
        int acao = scan.nextInt();
        if(acao <= maximoAcoes && acao >= -1) 
            return acao;
        else {
            System.out.println("Acao invalida. Tente novamente.\n");
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
                System.out.println("Sensor invalido digitado.\n");
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
                    System.out.println("Nenhum obstaculo detectado nesse ponto.");
                    break;
                case 1:
                    System.out.println("Ha um obstaculo nesse ponto.");
                    break;
                case 2:
                    System.out.println("Ponto fora dos limites do ambiente.");
                    break;
                case 3:
                    System.out.println("Ponto fora do raio de detecçao do sensor.");
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
                    System.out.println("Ponto fora dos limites do ambiente.");
                    break;
                case 3:
                    System.out.println("Ponto fora do raio de detecçao do sensor.");
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
                else if (robo instanceof RoboSatelite) {
                    System.out.print("[int] Quantos metros quer descer? ");
                    int metros = scan.nextInt();
                    ((RoboSatelite) robo).descer(metros);
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
                if (robo instanceof RoboPlanador) {
                    System.out.print("[int] Qual o novo tamanho da asa? ");
                    int novoTamanhoAsa = scan.nextInt();
                    ((RoboPlanador) robo).setTamanhoAsa(novoTamanhoAsa);
                }
                if (robo instanceof RoboSatelite) {
                    System.out.print("[int] Em quanto você quer carregar? ");
                    int cargaAdicionada = scan.nextInt();
                    ((RoboSatelite) robo).carregar(cargaAdicionada);
                }
                break;
            case 6:
                System.out.print("[int] Em quanto você quer descarregar? ");
                int cargaRemovida = scan.nextInt();
                ((RoboSatelite) robo).descarregar(cargaRemovida);
                break;
            case 7:
                ((RoboSatelite) robo).lancamento();
                break;
        }
    }

    private void imprimirAmbiente() {
        char [][] matriz = new char[salaTeste.getAltura() + 1][salaTeste.getLargura() + 1];

        for(int i = 0; i <= salaTeste.getAltura(); i++) 
            for(int j = 0; j <= salaTeste.getLargura(); j++)
                matriz[i][j] = '.';

        for(Obstaculo obstaculo:salaTeste.obstaculos)
            for(int i = obstaculo.getPosicaoX1(); i <= obstaculo.getPosicaoX2(); i++)
                for(int j = obstaculo.getPosicaoY1(); j <= obstaculo.getPosicaoY2(); j++)
                    matriz[i][j] = '#';

        for(Robo robo:salaTeste.robosAtivos)
            matriz[robo.getX()][robo.getY()] = 'o'; 

        for(int j = salaTeste.getLargura(); j >= 0; j--) {
            for(int i = 0; i <= salaTeste.getAltura(); i++) {
                System.out.printf("%c ", matriz[i][j]);
            }
            System.out.print("\n");
        }

        System.out.println("Legenda: .=vazio #=obstáculo o=robô");
    }
}
