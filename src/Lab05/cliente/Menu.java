package cliente;

import java.util.InputMismatchException;
import java.util.Scanner;

import simulador.Ambiente;
import simulador.Robo;
import simulador.RoboAereo;
import simulador.RoboPlanador;
import simulador.RoboPreguica;
import simulador.RoboSatelite;
import simulador.RoboTerrestre;
import simulador.RoboXadrez;
import simulador.AgenteInteligente;
import simulador.excecoes.DesceuDemaisException;
import simulador.excecoes.ErroComunicacaoException;
import simulador.excecoes.RoboDesligadoException;
import simulador.excecoes.SemObstaculoDestrutivelException;
import simulador.interfaces.Comunicavel;
import simulador.interfaces.Destrutivo;
import simulador.interfaces.Endotermico;
import simulador.interfaces.Geologo;
import simulador.interfaces.Sensoreavel;

public class Menu {
    private final Ambiente ambiente;
    private final Scanner scan;

    public Menu(Ambiente ambiente, Scanner scan){
        this.ambiente = ambiente;
        this.scan = scan;
    }

    // Método que coordena os loops do menu interativo. Chama os outros métodos para construir submenus 
    public void iniciarMenuPrincipal() {
        while (true) {
            exibirEscolhaMenuPrincipal();
            int entradaPrincipal = lerEscolhaMenuPrincipal();

            switch (entradaPrincipal) {
                case -1:
                    System.out.println("\nObrigado por usar nosso sistema!\n");
                    return;
                case -2:
                    iniciarMenuAmbiente();
                    break;
                default:
                    Robo roboUsado = ambiente.robosAtivos.get(entradaPrincipal);
                    iniciarMenuRobo(roboUsado);
                    break;           
            }            
        }
    }

    // Printa as opcoes do menu principal
    public void exibirEscolhaMenuPrincipal() {
        System.out.println("\n## MENU INTERATIVO ################################################");
        System.out.println("== ROBÔS ====================================");
        for (int i = 0; i < ambiente.robosAtivos.size(); i++) {
            Robo robo = ambiente.robosAtivos.get(i);
            System.out.printf("[%d] :: %-15s '%s'\n", i, robo.getClass().getSimpleName(), robo.getNome());
        }
        System.out.println("\n== OUTROS ====================================");
        System.out.printf("[-2] :: Ambiente\n");
        System.out.println("[-1] :: Encerrar o programa.\n");
    }
    
    /**
     * Lê a entrada do usuário no menu principal. Tem tratamento de erro contra entradas que não sejam números inteiros
     * @return inteiro correspondente à opção selecionada
     */
    public int lerEscolhaMenuPrincipal() {
        int entradaPrincipal;

        System.out.println("Digite o número corresponde a sua opção:");
        // Loop para que o menu não seja printado novamente caso a entrada seja inválida
        while (true) {
            System.out.print("> ");
            try {
                entradaPrincipal = scan.nextInt();          
                // Se o número da entrada esta entre as opções possíveis, é válido
                if (entradaPrincipal < ambiente.robosAtivos.size() && entradaPrincipal >= -2) 
                    break;
                else 
                    System.out.printf("!!! %d Não é uma opção válida !!!\n", entradaPrincipal); 
            }
            catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
            }
        }
        return entradaPrincipal;
    }

    // Método que coordena os loops do submenu do ambiente
    public void iniciarMenuAmbiente() {
        while (true) {
            exibirEscolhaMenuAmbiente();
            int entradaAmbiente = lerEscolhaMenuAmbiente();

            switch (entradaAmbiente) {
                case -1:
                    return;
                default:
                    realizarAcoesMenuAmbiente(entradaAmbiente);
                    break;
            }
        }
    }
    
    // Printa as opções do submenu do ambiente
    public void exibirEscolhaMenuAmbiente() {
        System.out.println("\n~~ AMBIENTE ~~~~~~~~~~~~~~~~~~");
        System.out.println("[0] :: Visualizar ambiente");
        System.out.println("[1] :: Listar robôs");
        System.out.println("[2] :: Ligar todos os robôs");
        System.out.println("[3] :: Listar mensagens na central");

        System.out.println("\n[-1] :: Voltar");
    }

    /**
     * Lê a entrada do usuário no submenu do ambiente. Tem tratamento de exceção contra entradas que não sejam números inteiros
     * @return inteiro correspondente à opção selecionada
     */
    public int lerEscolhaMenuAmbiente() {
        System.out.println("\nDigite o numero da ação escolhida:");
        // Loop para que o submenu não seja printado novamente caso a entrada seja inválida
        while (true) {
            try {
                System.out.print("> ");
                int entradaAcao = scan.nextInt();

                if (entradaAcao <= 3 && entradaAcao >= -1) 
                    return entradaAcao;
                else {
                    System.out.printf("!!! %d Não é uma opção válida !!!\n", entradaAcao);
                    continue;
                }
            } catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
            }
        }
    }

    // Baseado na seleção do usuário, chama os métodos necessários do submenu do ambiente
    public void realizarAcoesMenuAmbiente(int entradaAcao) {
        switch (entradaAcao) {
            case 0:
                ambiente.visualizarAmbiente();
                break;
            case 1:
                ambiente.listarRobos();
                break;
            case 2:
                ambiente.ligarRobos();
                break;
            case 3:
                ambiente.getCentral().exibirMensagens();
                break;
        }
    }

    // Método que coordena os loops do submenu do ambiente
    public void iniciarMenuRobo(Robo roboUsado) {
        while (true) {
            // Exibe as opções do submenu e armazena a qntd de opções em 'maximoAcoes'
            int acoesMaximo = exibirEscolhaMenuRobo(roboUsado);
            int entradaRobo = lerEscolhaMenuRobo(roboUsado, acoesMaximo);

            switch (entradaRobo) {
                case -1:
                    return;

                case -2:
                    iniciarMenuExtras(roboUsado);
            
                default:
                    realizarAcoesMenuRobo(roboUsado, entradaRobo);
                    break;
            }
        }
    }

    /**
     * Exibe as ações possíveis para cada robo. Diferencia o tipo de robô pelo uso de 'instanceof' e tira proveito
     * do fato de robôs serem filhos de outros para imprimir informações mais gerais (como as do Robô, que são válidas
     * para todos os seus filhos) antes das mais específicas. Ações específicas de cada robô aparecem como opção 
     * válida apenas para o tipo de robô que as têm
     * @param robo robô que foi escolhido na entrada
     * @return vetor de duas posições cuja primeira casa sera usada para limitar qual o inteiro máximo que o usuário
     * pode escolher e a segunda, qual o inteiro mínimo. Necessário pois as opções variam dependendo do tipo de robô
     * e se esse tem alguma interface extra
     */
    public int exibirEscolhaMenuRobo(Robo robo) {
        int acoesMaximo = 0;
        
        System.out.printf("\n~~ %s ~~~~~~~~~~~~~~~~~~\n", robo.getClass().getSimpleName().toUpperCase());
        if (robo instanceof Robo) {
            System.out.println("[0] :: Informações");
            System.out.println("[1] :: Ligar/Desligar robô");
            System.out.println("[2] :: Mover robô");
            System.out.println("[3] :: Usar sensores");
            System.out.println("[4] :: Mudar direção");
            acoesMaximo = 4;
        }
        if (robo instanceof RoboTerrestre) {
            System.out.println("[5] :: Aumentar velocidade");
            System.out.println("[6] :: Diminuir velocidade");
            acoesMaximo = 6;
        }
        if (robo instanceof RoboXadrez) {
            System.out.println("[7] :: Mudar peça");
            acoesMaximo = 7;
        }
        if (robo instanceof RoboPreguica) {
            System.out.println("[7] :: Descançar");
            acoesMaximo = 7;
        }
        if (robo instanceof RoboAereo) {
            System.out.println("[5] :: Subir");
            System.out.println("[6] :: Descer");
            acoesMaximo = 6;
        }
        if (robo instanceof RoboPlanador) {
            System.out.println("[7] :: Mudar tamanho da asa");
            acoesMaximo = 7;
        }
        if (robo instanceof RoboSatelite) {
            System.out.println("[7] :: Carregar");
            System.out.println("[8] :: Descarregar");
            System.out.println("[9] :: Lançar");
            acoesMaximo = 9;
        }

        System.out.print("\n[-2] :: Extras");
        System.out.println("\n[-1] :: Voltar");
        return acoesMaximo;
    }

    /**
     * Lê a entrada do usuário de qual ação específica ele quer que o robô escolhido realize
     * @param robo robô que foi escolhido na entrada principal
     * @param acoesMaximo limita qual o inteiro máximo que o usuário pode escolher baseado no tipo de robô controlado
     * @return inteiro representativo da ação escolhida pelo usuário para o robô fazer
     */
    public int lerEscolhaMenuRobo(Robo robo, int acoesMaximo) {
        System.out.println("\nDigite o número da ação escolhida:");
        // Loop para que o submenu não seja printado novamente caso a entrada seja inválida
        while (true) {
            try {
                System.out.print("> ");
                int entradaAcao = scan.nextInt();

                if (entradaAcao <= acoesMaximo && entradaAcao >= -2) 
                    return entradaAcao;
                else {
                    System.out.printf("!!! %d Não é uma opção válida !!!\n", entradaAcao);
                    continue;
                }
            } catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
            }
        }
    }

     /**
     * Chama os métodos necessários do robo baseado na ação escolhida no submenu. Tem tratamento de exceção contra entradas não inteiras
     * e também para caso o robô não esteja ligado
     * @param robo robô que foi escolhido na entrada principal
     * @param entradaAcao ação que foi escolhida na entrada anterior 
     */
    public void realizarAcoesMenuRobo(Robo robo, int entradaAcao) {
        // Loop para que o usuário fique 'preso' na opção selecionada ate dar uma entrada válida
        while (true) {
            try {
                // Cada 'case' do 'switch' é uma possivel entrada válida, que deve ser separada em casos quando robos diferentes
                // compartilham um mesmo índice de ação (por exemplo 3 pode significar 'aumentarVelocidade' para um robô terrestre
                // ou 'subir' para um aéreo)
                switch (entradaAcao) {
                    // Mesmo em todos os robôs
                    case 0:
                        System.out.println("");
                        System.out.print(robo.getDescricao());
                        break;
                    
                    //Mesmo em todos os robôs
                    case 1:
                        System.out.println("");
                        if (!robo.estaLigado())
                            robo.ligar();
                        else
                            robo.desligar();
                        break;

                    // Mesmo em todos os robôs
                    case 2:
                        System.out.println("\n[int] Para qual coordenada horizontal quer ir (eixo x)?");
                        System.out.print("> ");
                        int x = scan.nextInt();
                        System.out.println("[int] Para qual coordenada vertical quer ir (eixo y)?");
                        System.out.print("> ");
                        int y = scan.nextInt();
                        System.out.println("");
                        robo.moverPara(x, y);
                        break;
                    
                    // Mesmo em todos os robôs
                    case 3:
                        iniciarMenuSensores(robo);
                        break;
                    
                    // Mesmo em todos os robôs  
                    case 4:
                        iniciarMenuDirecao(robo);
                        break;
                    
                    // Aumenta velocidade para robôs terrestres
                    // Sobe para robôs aéreos
                    case 5:
                        if (robo instanceof RoboTerrestre) {
                            System.out.println("\n[int] Em quanto quer aumentar a velocidade?");
                            System.out.print("> ");
                            int vlc = scan.nextInt();
                            System.out.println("");
                            ((RoboTerrestre) robo).aumentarVelocidade(vlc);
                        }
                        else if (robo instanceof RoboAereo) {
                            System.out.println("\n[int] Quantos metros quer subir? ");
                            System.out.print("> ");
                            int metros = scan.nextInt();
                            System.out.println("");
                            ((RoboAereo) robo).subir(metros);
                        }
                        break;
                    
                    // Diminui velocidade para robôs terrestres
                    // Desce para robôs aéreos
                    case 6:
                        if (robo instanceof RoboTerrestre) {
                            System.out.println("\n[int] Em quanto quer diminuir a velocidade?");
                            System.out.print("> ");
                            int vlc = scan.nextInt();
                            System.out.println("");
                            ((RoboTerrestre) robo).diminuirVelocidade(vlc);
                        }
                        else if (robo instanceof RoboAereo) {
                            System.out.println("\n[int] Quantos metros quer descer?");
                            System.out.print("> ");
                            int metros = scan.nextInt();
                            System.out.println("");
                            ((RoboAereo) robo).descer(metros);
                        }
                        break;
                    
                    // Muda o tipo de movimento para robôs xadrez
                    // Descança energias para robôs preguiça
                    // Muda o tamanho da asa para robôs planadores
                    // Carrega o tanque para robôs satelites
                    case 7:
                        if (robo instanceof RoboXadrez) {
                            System.out.println("");
                            ((RoboXadrez) robo).alternaTipoMovimento();
                            if (((RoboXadrez)robo).getTipoMovimento() == 1)
                                System.out.println("Tipo de peça mudado para Cavalo.");
                            else
                                System.out.println("Tipo de peça mudado para Peao.");
                        }
                        else if (robo instanceof RoboPreguica) {
                            System.out.println("");
                            ((RoboPreguica)robo).descansar();
                        }
                        else if (robo instanceof RoboPlanador) {
                            System.out.println("\n[int] Qual o novo tamanho da asa?");
                            System.out.print("> ");
                            int novoTamanhoAsa = scan.nextInt();
                            System.out.println("");
                            ((RoboPlanador) robo).setTamanhoAsa(novoTamanhoAsa);
                        }
                        else if (robo instanceof RoboSatelite) {
                            System.out.println("\n[int] Em quanto quer carregar?");
                            System.out.print("> ");
                            int cargaAdicionada = scan.nextInt();
                            System.out.println("");
                            ((RoboSatelite) robo).carregar(cargaAdicionada);
                        }
                        break;

                    // Descarrega o tanque para robôs satelites
                    case 8:
                        System.out.println("\n[int] Em quanto quer descarregar?");
                        System.out.print("> ");
                        int cargaRemovida = scan.nextInt();
                        System.out.println("");
                        ((RoboSatelite) robo).descarregar(cargaRemovida);
                        break;

                    // Executa o lancamento para robôs satelites
                    case 9:
                        System.out.println("");
                        ((RoboSatelite) robo).lancamento();
                        break;
                }
            } catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
                continue;
            } catch (RoboDesligadoException erro) {
                System.err.println(erro.getMessage());
            } catch (DesceuDemaisException erro) {
                System.err.println(erro.getMessage());
            }
            break;
        }
    }

    public void iniciarMenuExtras(Robo robo) {
        while (true) {
            try {
                int[] listaInterfaces = exibirEscolhaMenuExtras(robo);
                int entradaExtras = lerEscolhaMenuExtras(listaInterfaces[0]);

                switch (entradaExtras) {
                    case -1:
                        return;
                    case 0:
                        robo.executarTarefa();
                        break;
                    case 1:
                        realizarMissoesMenuExtras(robo);
                        break;
                    case 2:
                        atribuirMissoesMenuExtras(robo);
                        break;
                    default:
                        realizarAcoesMenuExtras(robo, listaInterfaces, entradaExtras);
                        break;
                }
            } catch(RoboDesligadoException erro) {
                System.err.println(erro.getMessage());
            }
        }
    }

    public int[] exibirEscolhaMenuExtras(Robo robo) {
        int[] listaInterfaces = new int[6];
        int indice = 1;
        listaInterfaces[0] = 0;
        System.out.println("\n-- EXTRAS ---------");
        System.out.printf("[0] :: Tarefa %s\n", robo.getNomeTarefa());
        System.out.printf("[1] :: Missão %s\n", robo.getNomeMissao());
        System.out.printf("[2] :: Atribuir nova missão\n", robo.getNomeMissao());

        
        if (robo instanceof Comunicavel) {
            System.out.printf("[%d] :: Comunicar-se\n", indice + 1);
            indice++;
            // Significa que a opção índice do menu (listaInterfaces[indice], que nesse primeiro caso vai ser sempre 1) 
            // é o Comunicavel (dado por 0)
            listaInterfaces[indice] = 0;        
        }
        if (robo instanceof Sensoreavel) {
            System.out.printf("[%d] :: Acionar todos os sensores\n", indice + 1);
            indice++;
            // Significa que a opção índice do menu (listaInterfaces[indice]) é o Sensoreaevel (dado por 1)
            listaInterfaces[indice] = 1;        
        }
        if (robo instanceof Destrutivo) {
            System.out.printf("[%d] :: Destruir\n", indice + 1);
            indice++;
            // Significa que a opção índice do menu (listaInterfaces[indice]) é o Destrutivel (dado por 2)
            listaInterfaces[indice] = 2;
        }
        if (robo instanceof Endotermico) {
            System.out.printf("[%d] :: Mover para mais quente\n", indice + 1);
            indice++;
            // Significa que a opção índice do menu (listaInterfaces[indice]) é o Endotermico (dado por 3)
            listaInterfaces[indice] = 3;
        }
        if (robo instanceof Geologo) {
            System.out.printf("[%d] :: Tipo do obstáculo\n", indice + 1);
            indice++;
            // Significa que a opção índice do menu (listaInterfaces[indice]) é o Geologo (dado por 4)
            listaInterfaces[indice] = 4;
        }
        if (robo instanceof Geologo) {
            System.out.printf("[%d] :: Tamanho do obstáculo\n", indice + 1);
            indice++;
            // Significa que a opção índice do menu (listaInterfaces[indice]) é o Geologo (dado por 5)
            listaInterfaces[indice] = 5;
        }

        listaInterfaces[0] = indice;
        System.out.println("\n[-1] :: Voltar");

        return listaInterfaces;
    }

    public int lerEscolhaMenuExtras(int acoesMaximo) {
        System.out.println("\nQual ação quer realizar?");
        // Loop para que o submenu não seja printado novamente caso a entrada seja inválida
        while (true) {
            try {
                System.out.print("> ");
                int entradaExtra = scan.nextInt();
                scan.nextLine();

                if (entradaExtra >= -1 && entradaExtra <= acoesMaximo){
                    return entradaExtra;
                }
                else {
                    System.out.printf("!!! %d Não é uma opção válida !!!\n", entradaExtra);
                    continue;
                }
            } catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
            }
        }
    }

    public void realizarMissoesMenuExtras(Robo robo) throws RoboDesligadoException{
        if (robo instanceof AgenteInteligente) {
            ((AgenteInteligente) robo).executarMissao(ambiente);
        }
        else {
            System.out.println("\nO robô não é inteligente o bastante para realizar uma missão.");
        }
    }

    public void realizarAcoesMenuExtras(Robo robo, int[] listaInterfaces, int entradaAcoes) {
        while (true) {
            try {
                switch (listaInterfaces[entradaAcoes]) {
                    case 0:
                        System.out.println("\n[String] Para qual robô a mensagem deve ser enviada?");
                        System.out.print("> ");
                        String nome = scan.nextLine();
                        Robo destinatarioRobo = ambiente.conferirNome(nome);

                        // Confere se o nome é o de algum robô no ambiente 
                        if (destinatarioRobo != null) {
                            // Confere se o robo é comunicavel, se não, lança um erro
                            if (ambiente.getCentral().checarDestinatario(destinatarioRobo)) {
                                System.out.println("[String] Qual a mensagem?");
                                System.out.print("> ");
                                String mensagem = scan.nextLine();
                                System.out.println("");
                                ((Comunicavel) robo).enviarMensagem(((Comunicavel) destinatarioRobo), mensagem);
                            }
                            break;
                        } else {
                            System.out.println("!!! Esse não é um robo valido !!!");
                            break;
                        }

                    case 1:
                        ((Sensoreavel) robo).acionarSensores();
                        break;

                    case 2:
                        System.out.println("\n[int] Qual coordenada x deseja destruir?");
                        System.out.print("> ");
                        int xDestruicao = scan.nextInt();
                        System.out.println("[int] Qual coordenada y deseja destruir?");
                        System.out.print("> ");
                        int yDestruicao = scan.nextInt();
                        System.out.println("");
                        ((Destrutivo) robo).destruirObstaculo(xDestruicao, yDestruicao);
                        break;

                    case 3:
                        ((Endotermico) robo).moverParaQuente();
                        break;

                    case 4:
                        ((Geologo) robo).identificarTipoObstaculo();
                        break;

                    case 5:
                        ((Geologo) robo).identificarTamanhoObstaculo();
                        break;
                }
                break;
            } catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
            } catch (RoboDesligadoException erro) {
                System.err.println(erro.getMessage());
                break;
            } catch (ErroComunicacaoException erro) {
                System.err.println(erro.getMessage());
                break;
            } catch (SemObstaculoDestrutivelException erro) {
                System.err.println(erro.getMessage());
                break;
            }
        }
    }

    // Método que coordena os loops do submenu das direções
    public void iniciarMenuDirecao(Robo robo) {
        while (true) {
            exibirEscolhaMenuDirecao();
            int entradaDirecao = lerEscolhaMenuDirecao();

            switch (entradaDirecao) {
                case -1:
                    return;
                default:
                    robo.setDirecao(entradaDirecao);
                    break;
            }
        }
    }

    // Printa as opções do submenu das direcoes
    public void exibirEscolhaMenuDirecao() {
        System.out.println("\n-- DIREÇÕES ---------");
        System.out.println("[0] :: Norte");
        System.out.println("[1] :: Sul");
        System.out.println("[2] :: Leste");
        System.out.println("[3] :: Oeste");

        System.out.println("\n[-1] :: Voltar");
    }
    
    /**
     * Lê a entrada do usuário no submenu das direcoes. Tem tratamento de exceção contra entradas que não sejam numeros inteiros
     * @return inteiro correspondente à opção selecionada
     */
    public int lerEscolhaMenuDirecao() {
        System.out.println("\nPara qual direção deseja mudar?");
        // Loop para que o submenu não seja printado novamente caso a entrada seja inválida
        while (true) {
            try {
                System.out.print("> ");
                int entradaDirecao = scan.nextInt();

                if (entradaDirecao >= -1 && entradaDirecao <= 3){
                    return entradaDirecao;
                }
                else {
                    System.out.printf("!!! %d Não é uma opção válida !!!\n", entradaDirecao);
                    continue;
                }
            } catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
            }
        }
    }

    // Método que coordena os loops do submenu dos sensores
    public void iniciarMenuSensores(Robo robo) {
        while (true) {
            exibirEscolhaMenuSensores(robo);
            int entradaSensor = lerEscolhaMenuSensores(robo);

            switch (entradaSensor) {
                case -1:
                    return;
                default:
                    realizarAcaoMenuSensor(robo, entradaSensor);
                    break;
            }
        }
    }

    // Printa as opções do submenu dos sensores
    public void exibirEscolhaMenuSensores(Robo robo) {
        System.out.println("\n-- SENSORES ---------");
        robo.exibirSensores();

        System.out.println("\n[-1] voltar");
    }

    /**
     * Caso o usuário escolha usar um sensor de um robô, é necessário checar se o robô tem o sensor especificado
     * e, depois disso, se tiver, deve ler a entrada de qual coordenada se quer monitorar com o sensor e exibir
     * uma resposta apropriada
     * @param robo robô que foi escolhido na entrada principal
     */
    public int lerEscolhaMenuSensores(Robo robo) {
        if (robo.sensores == null || robo.sensores.size() == 0) {
            System.out.println("\nNao há sensores nesse robô. Tente com outro.");
            return -1;
        }
        System.out.println("\nQual sensor deseja usar?");
        while (true) {
            try {
                System.out.print("> ");
                int entradaAcao = scan.nextInt();

                if (entradaAcao >= -1 && entradaAcao < robo.sensores.size())
                    return entradaAcao;
                else {
                    System.out.printf("!!! %d nao é um sensor válido !!!\n", entradaAcao);
                    continue;
                }
            } catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
            }
        }
    }

    /**
     * Pega a entrada do usuário de qual ponto deve ser 'monitorado' e usa o sensor especificado naquele ponto
     * @param robo robô que foi escolhido na entrada principal
     * @param entradaAcao posicao do sensor que se quer usar na lista de sensores do robô especificado
     */
    public void realizarAcaoMenuSensor(Robo robo, int entradaAcao) {
        while (true) {
            try {
                System.out.println("\n[int] Qual coordenada x gostaria de monitorar?");
                System.out.print("> ");
                int posX = scan.nextInt();
                System.out.println("[int] Qual coordenada y gostaria de monitorar?");
                System.out.print("> ");
                int posY = scan.nextInt();
                System.out.println("");
                robo.usarSensor(entradaAcao, posX, posY);
            } catch (InputMismatchException erro) {
                System.err.println("!!! Use apenas números !!!");
                scan.next();
                continue;
            } catch (RoboDesligadoException erro) {
                System.err.println(erro.getMessage());
            }
            break;
        }
    }
}
