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
        int entradaPrincipal;

        while (true) {
            entradaPrincipal = menuPrincipal(salaTeste, scan);
            
            if (entradaPrincipal == -1)
                break;
            else if (entradaPrincipal == -2) {
                imprimirAmbiente();
                continue;
            }

            Robo roboUsado = salaTeste.robosAtivos.get(entradaPrincipal);
            acao = 0;
            
            while (true) {
                maximoAcoes = exibirEscolhaAcoes(roboUsado);
                acao = lerEscolhaAcoes(roboUsado, maximoAcoes, scan);
                if (acao == -1) {
                    break;
                }
                realizarAcao(roboUsado, acao, scan);    
            }
        }
    }

    /**
     * Exibe as escolhas que o usuario pode fazer no menu inicial, que sao selecionar um robo, imprimir ambiente
     * ou encerrar o programa, e pega a entrada em seguida
     * @param ambiente ambiente onde os testes serao feitos
     * @param scan Scanner para ler entradas de usuarios
     * @return 'entradaPrincipal', inteiro representativo do robo escolhido
     */
    public static int menuPrincipal(Ambiente ambiente, Scanner scan) {
        int entradaPrincipal;
        
        // Loop "infinito" para lidar com entradas invalidas
        while(true) {
            System.out.printf("\n*******************************************MENU*INTERATIVO*******************************************\n");
            for (int i = 0; i < ambiente.robosAtivos.size(); i++) {
                Robo robo = ambiente.robosAtivos.get(i);
                System.out.printf("[%d] :: %s '%s'\n", i, robo.getClass().getSimpleName(), robo.getNome());
            }
            System.out.println("\n[-2] :: imprimir o ambiente.");
            System.out.println("[-1] :: encerrar o programa.");
            System.out.println("Digite um numero para prosseguir:");
            System.out.print("> ");
            entradaPrincipal = scan.nextInt();  
            
            // Se o numero da entrada esta entre as opçoes possiveis, eh valido
            if(entradaPrincipal < ambiente.robosAtivos.size() && entradaPrincipal >= -2) 
                break;
            else 
                System.out.println("Digite um numero valido.");
        }
        return entradaPrincipal;
    }
    
    /**
     * Exibe as acoes possiveis para cada robo. Diferencia o tipo de robo pelo uso de 'instanceof' e tira proveito
     * do fato de robos serem filhos de outros para imprimir informaçoes mais gerais (como as do Robo, que sao validas
     * para todos os seus filhos) antes das mais especificas. Açoes especificas de cada robo aparecem como opcao 
     * valida apenas para o tipo de robo que as tem
     * @param robo robo que foi escolhido na entrada
     * @return que sera usado para checar se a selecao da acao eh valida ou nao baseada em quantas opcoes
     * de acao aquele robo tem
     */
    public static int exibirEscolhaAcoes(Robo robo) {
        int maximoAcoes = 0;
        System.out.printf("\n**********************************************MENU*ROBO**********************************************\n");
        if (robo instanceof Robo) {
            System.out.println("[0] :: informacoes");
            System.out.println("[1] :: mover robo");
            System.out.println("[2] :: usar sensores");
            System.out.println("[3] :: mudar direção");
            maximoAcoes = 3;
        }
        if (robo instanceof RoboTerrestre) {
            System.out.println("[4] :: aumentar velocidade");
            System.out.println("[5] :: diminuir velocidade");
            maximoAcoes = 5;
        }
        if (robo instanceof RoboXadrez) {
            System.out.println("[6] :: mudar peça");
            maximoAcoes = 6;
        }
        if (robo instanceof RoboPreguica) {
            System.out.println("[6] :: descançar");
            maximoAcoes = 6;
        }
        if (robo instanceof RoboAereo) {
            System.out.println("[4] :: subir");
            System.out.println("[5] :: descer");
            maximoAcoes = 5;
        }
        if (robo instanceof RoboPlanador) {
            System.out.println("[6] :: mudar tamanho da asa");
            maximoAcoes = 6;
        }
        if (robo instanceof RoboSatelite) {
            System.out.println("[6] :: carregar");
            System.out.println("[7] :: descarregar");
            System.out.println("[8] :: lançar");
            maximoAcoes = 8;
        }
        System.out.println("\n[-1] :: voltar");
        return maximoAcoes;
    }

    /**
     * Pega a entrada do usuario de qual acao especifica ele quer que o robo escolhido realize
     * @param robo robo que foi escolhido na entrada principal
     * @param maximoAcoes eh a quantidade de acoes que o robo pode fazer no total; como a entrada eh feita 
     * lendo inteiros, se for um numero maior que esse, sabemos que nao eh uma entrada valida
     * @param scan Scanner para ler entradas de usuario
     * @return inteiro representativo da acao escolhida pelo usuario para o robo fazer
     */
    public static int lerEscolhaAcoes(Robo robo, int maximoAcoes, Scanner scan) {
        System.out.println("Digite um numero para realizar uma acao:");
        System.out.print("> ");
        int entradaAcao = scan.nextInt();
        if(entradaAcao <= maximoAcoes && entradaAcao >= -1) 
            return entradaAcao;
        else {
            System.out.println("Acao invalida. Tente novamente.");
            exibirEscolhaAcoes(robo);
            return lerEscolhaAcoes(robo, maximoAcoes, scan);
        }
    }

     /**
     * Pega a entrada do usuario para qual acao do robo escolhido ele quer realizar. Apos uma entrada valida, pode pegar 
     * outra entrada do usuario que servira como parametro da acao que foi escolhida anteriormente (ou nao, caso a acao 
     * nao necessite de paramentros)
     * @param robo robo que foi escolhido na entrada principal
     * @param entradaAcao acao que foi escolhida na entrada anterior 
     * @param scan Scanner para ler entradas de usuarios
     */
    public static void realizarAcao(Robo robo, int entradaAcao, Scanner scan) {
        // Cada 'case' do 'switch' é uma possivel entrada valida, que deve ser separada em casos quando robos diferentes
        // compartilham um mesmo indice de acao (por exemplo 3 pode significar 'aumentarVelocidade' para um robo terrestre
        // ou 'subir' para um aereo) 
        switch (entradaAcao) {
            // Mesmo em todos os robos
            case 0:
                robo.info();
                break;

            // Mesmo em todos os robos
            case 1:
                System.out.println("[int] Quanto quer andar na horizontal (eixo x)?");
                System.out.print("> ");
                int deltaX = scan.nextInt();
                System.out.println("[int] Quanto quer andar na vertical (eixo y)?");
                System.out.print("> ");
                int deltaY = scan.nextInt();
                System.out.println("");
                robo.mover(deltaX, deltaY);
                break;
            
            // Mesmo em todos os robos
            case 2:
                acaoSensor(robo, scan);
                break;
            
            // Mesmo em todos os robos  
            case 3:
                acaoDirecao(robo, scan);
                break;
            
            // Aumenta velocidade para robos terrestres
            // Sobe para robos aereos
            case 4:
                if (robo instanceof RoboTerrestre) {
                    System.out.println("[int] Em quanto quer aumentar a velocidade?");
                    System.out.print("> ");
                    int vlc = scan.nextInt();
                    System.out.println("");
                    ((RoboTerrestre) robo).aumentarVelocidade(vlc);
                }
                else if (robo instanceof RoboAereo) {
                    System.out.print("[int] Quantos metros quer subir? ");
                    int metros = scan.nextInt();
                    System.out.println("");
                    ((RoboAereo) robo).subir(metros);
                }
                break;
            
            // Diminui velocidade para robos terrestres
            // Desce para robos aereos
            case 5:
                if (robo instanceof RoboTerrestre) {
                    System.out.println("[int] Em quanto quer diminuir a velocidade?");
                    System.out.print("> ");
                    int vlc = scan.nextInt();
                    System.out.println("");
                    ((RoboTerrestre) robo).diminuirVelocidade(vlc);
                }
                else if (robo instanceof RoboAereo) {
                    System.out.println("[int] Quantos metros quer descer?");
                    System.out.print("> ");
                    int metros = scan.nextInt();
                    System.out.println("");
                    ((RoboAereo) robo).descer(metros);
                }
                break;
            
            // Muda o tipo de movimento para robos xadrez
            // Descança energias para robo preguiça
            // Muda o tamanho da asa para robos planadores
            // Carrega o tanque para robos satelites
            case 6:
                if (robo instanceof RoboXadrez) {
                    System.out.println("");
                    ((RoboXadrez) robo).alternaTipoMovimento();
                    if (((RoboXadrez)robo).getTipoMovimento() == 1)
                        System.out.println("Tipo de peça mudado para Cavalo\n");
                    else
                        System.out.println("Tipo de peça mudado para Peao\n");
                }
                else if (robo instanceof RoboPreguica) {
                    System.out.println("");
                    ((RoboPreguica)robo).descansar();
                }
                else if (robo instanceof RoboPlanador) {
                    System.out.println("[int] Qual o novo tamanho da asa?");
                    System.out.print("> ");
                    int novoTamanhoAsa = scan.nextInt();
                    System.out.println("");
                    ((RoboPlanador) robo).setTamanhoAsa(novoTamanhoAsa);
                }
                else if (robo instanceof RoboSatelite) {
                    System.out.println("[int] Em quanto quer carregar?");
                    System.out.print("> ");
                    int cargaAdicionada = scan.nextInt();
                    System.out.println("");
                    ((RoboSatelite) robo).carregar(cargaAdicionada);
                }
                break;

            // Descarrega o tanque para robos satelites
            case 7:
                System.out.println("[int] Em quanto quer descarregar?");
                System.out.print("> ");
                int cargaRemovida = scan.nextInt();
                System.out.println("");
                ((RoboSatelite) robo).descarregar(cargaRemovida);
                break;

            // Executa o lancamento para robos satelites
            case 8:
                System.out.println("");
                ((RoboSatelite) robo).lancamento();
                break;
        }
    }

    public static void acaoDirecao(Robo robo, Scanner scan) {
        while(true) {
            System.out.printf("\n******************************************MENU*DIRECOES**********************************************\n");
            System.out.println("[0] :: Norte");
            System.out.println("[1] :: Sul");
            System.out.println("[2] :: Leste");
            System.out.println("[3] :: Oeste");
            System.out.println("\n[-1] :: voltar");
            System.out.println("Para qual direção deseja mudar?");
            System.out.print("> ");
            int direcao = scan.nextInt();

            if (direcao == -1)
                break;
            else
                robo.setDirecao(direcao);
        }
    }

    /**
     * Caso o usuario escolha usar um sensor de um robo, eh necessario checar se o robo tem o sensor especificado
     * e, depois disso; se tiver, deve pegar a entrada de qual coordenada se quer monitorar com o sensor e exibir
     * uma resposta apropriada
     * @param robo robo que foi escolhido na entrada principal
     * @param scan Scanner para ler entradas de usuarios
     */
    public static void acaoSensor(Robo robo, Scanner scan) {
        int indiceSensor;

        while (true) {
            if (robo.sensores != null && robo.sensores.size() == 0) {
                indiceSensor = -1;
                System.out.println("Nao ha sensores nesse robo.");
                break;
            } 

            System.out.printf("\n******************************************MENU*SENSORES**********************************************\n");
            robo.exibirSensores();
            System.out.println("\n[-1] voltar");
            System.out.print("> ");
            indiceSensor = scan.nextInt();
            
            if(indiceSensor == -1)
                break;
            if(indiceSensor >= 0 && indiceSensor < robo.sensores.size())
                break;
            else
                System.out.println("Esse nao eh um sensor valido.");
        }

        if(indiceSensor == -1)
            return;

        acaoMonitorar(robo, indiceSensor, scan);
    }

    /**
     * Pega a entrada do usuario de qual ponto deve ser 'monitorado' e usa o sensor especificado naquele ponto
     * @param robo robo que foi escolhido na entrada principal
     * @param indiceSensor posicao do sensor que se quer usar na lista de sensores do robo especificado
     * @param scan Scanner para ler entradas de usuarios
     */
    public static void acaoMonitorar(Robo robo, int indiceSensor, Scanner scan) {
        System.out.println("[int] Qual coordenada x gostaria de monitorar?");
        System.out.print("> ");
        int posX = scan.nextInt();
        System.out.println("[int] Qual coordenada y gostaria de monitorar?");
        System.out.print("> ");
        int posY = scan.nextInt();
        System.out.println("");
        robo.usarSensor(indiceSensor, posX, posY);
    }

    // Exibe o ambiente, considerando '.' como espacos vazios, '#' como obstaculos e letras como os robos
    private void imprimirAmbiente() {
        char [][] matrizAmbiente = new char[salaTeste.getAltura() + 1][salaTeste.getLargura() + 1];

        // Loop para preencher a matrizAmbiente representativa do ambiente com espacos vazio '.' 
        for (int a = 0; a <= salaTeste.getAltura(); a++) 
            for (int b = 0; b <= salaTeste.getLargura(); b++)
                matrizAmbiente[a][b] = '.';

        // Loop para mudar as posicoes com obstaculos para '#'
        for (Obstaculo obstaculo:salaTeste.obstaculos)
            for (int c = obstaculo.getPosicaoX1(); c <= obstaculo.getPosicaoX2(); c++)
                for (int d = obstaculo.getPosicaoY1(); d <= obstaculo.getPosicaoY2(); d++)
                    matrizAmbiente[c][d] = '#';

        // Loop para mudar as posicoes com robos para suas respectivas letras
        for (Robo robo:salaTeste.robosAtivos) {
            if (robo instanceof RoboSatelite)
                matrizAmbiente[robo.getX()][robo.getY()] = 'S';
            else if (robo instanceof RoboPlanador)
                matrizAmbiente[robo.getX()][robo.getY()] = 'P'; 
            else if (robo instanceof RoboAereo)
                matrizAmbiente[robo.getX()][robo.getY()] = 'A';
            else if (robo instanceof RoboPreguica)
                matrizAmbiente[robo.getX()][robo.getY()] = 'G';
            else if (robo instanceof RoboXadrez)
                matrizAmbiente[robo.getX()][robo.getY()] = 'X';
            else if (robo instanceof RoboTerrestre)
                matrizAmbiente[robo.getX()][robo.getY()] = 'T';
            else if (robo instanceof Robo)
                matrizAmbiente[robo.getX()][robo.getY()] = 'R';
        }

        System.out.println("");
        // Loop para efetivamente imprimir a matrizAmbiente
        for (int e = salaTeste.getLargura(); e >= 0; e--) {
            for (int f = 0; f <= salaTeste.getAltura(); f++)
                System.out.printf("%c ", matrizAmbiente[f][e]);
            System.out.print("\n");
        }

        System.out.println("Legenda: . = vazio    # = obstaculo    R = robo generico    T = robo terrestre    X = robo xadrez");
        System.out.println("         G = robo preguica    A = robo aereo    P = robo planador    S = robo satelite");
    }
}
