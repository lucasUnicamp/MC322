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
import simulador.Sensoreavel;
import simulador.Comunicavel;
import simulador.RoboDesligadoException;
import simulador.ErroComunicacaoException;

public class Menu {
    private final Ambiente ambiente;
    private final Scanner scan;

    public Menu(Ambiente ambiente, Scanner scan){
        this.ambiente = ambiente;
        this.scan = scan;
    }

    // Metodo que coordena os loops do menu interativo. Chama os outros metodos para construir submenus 
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
        System.out.println("== ROBOS ====================================");
        for (int i = 0; i < ambiente.robosAtivos.size(); i++) {
            Robo robo = ambiente.robosAtivos.get(i);
            System.out.printf("[%d] :: %-15s '%s'\n", i, robo.getClass().getSimpleName(), robo.getNome());
        }
        System.out.println("\n== OUTROS ====================================");
        System.out.printf("[-2] :: Ambiente\n");
        System.out.println("[-1] :: Encerrar o programa.\n");
    }
    
    /**
     * Le a entrada do usuario no menu principal. Tem tratamento de erro contra entradas que nao sejam numeros inteiros
     * @return inteiro correspondente a opcao selecionada
     */
    public int lerEscolhaMenuPrincipal() {
        int entradaPrincipal;

        System.out.println("Digite o numero corresponde a sua opçao:");
        // Loop para que o menu nao seja printado novamente caso a entrada seja invalida
        while (true) {
            System.out.print("> ");
            try {
                entradaPrincipal = scan.nextInt();          
                // Se o numero da entrada esta entre as opçoes possiveis, eh valido
                if (entradaPrincipal < ambiente.robosAtivos.size() && entradaPrincipal >= -2) 
                    break;
                else 
                    System.out.printf("!!! %d Nao eh uma opcao valida !!!\n", entradaPrincipal); 
            }
            catch (InputMismatchException entradaInvalidaMenu) {
                System.out.println("!!! Use apenas numeros !!!");
                scan.next();
            }
        }
        return entradaPrincipal;
    }

    // Metodo que coordena os loops do submenu do ambiente
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
    
    // Printa as opcoes do submenu do ambiente
    public void exibirEscolhaMenuAmbiente() {
        System.out.println("\n~~ AMBIENTE ~~~~~~~~~~~~~~~~~~");
        System.out.println("[0] :: Imprimir ambiente");
        System.out.println("[1] :: Listar robos");
        System.out.println("[2] :: Listar mensagens na central");

        System.out.println("\n[-1] :: Voltar");
    }

    /**
     * Le a entrada do usuario no submenu do ambiente. Tem tratamento de excessao contra entradas que nao sejam numeros inteiros
     * @return inteiro correspondente a opcao selecionada
     */
    public int lerEscolhaMenuAmbiente() {
        System.out.println("\nDigite o numero da acao escolhida:");
        // Loop para que o submenu nao seja printado novamente caso a entrada seja invalida
        while (true) {
            try {
                System.out.print("> ");
                int entradaAcao = scan.nextInt();

                if (entradaAcao <= 2 && entradaAcao >= -1) 
                    return entradaAcao;
                else {
                    System.out.printf("!!! %d Nao eh uma opcao valida !!!\n", entradaAcao);
                    continue;
                }
            } catch (InputMismatchException entradaInvalidaEsclhAcao) {
                System.out.println("!!! Use apenas numeros !!!");
                scan.next();
            }
        }
    }

    // Baseado na selecao do usario, chama os metodos necessarios do submenu do ambiente
    public void realizarAcoesMenuAmbiente(int entradaAcao) {
        switch (entradaAcao) {
            case 0:
                ambiente.visualizarAmbiente();
                break;
            case 1:
                ambiente.listarRobos();
                break;
            case 2:
                ambiente.getCentral().exibirMensagens();
                break;
        }
    }

    // Metodo que coordena os loops do submenu do ambiente
    public void iniciarMenuRobo(Robo roboUsado) {
        while (true) {
            // Exibe as opcoes do submenu e armazena a qntd de opcoes em 'maximoAcoes'
            int[] acoesMaximoMinimo = exibirEscolhaMenuRobo(roboUsado);
            int entradaRobo = lerEscolhaMenuRobo(roboUsado, acoesMaximoMinimo);

            switch (entradaRobo) {
                case -1:
                    return;
                case -2:
                    if (temInterfaceExtra(roboUsado)) {
                        iniciarMenuExtras(roboUsado);
                    }
                default:
                    realizarAcoesMenuRobo(roboUsado, entradaRobo);
                    break;
            }
        }
    }

    /**
     * Exibe as acoes possiveis para cada robo. Diferencia o tipo de robo pelo uso de 'instanceof' e tira proveito
     * do fato de robos serem filhos de outros para imprimir informaçoes mais gerais (como as do Robo, que sao validas
     * para todos os seus filhos) antes das mais especificas. Açoes especificas de cada robo aparecem como opcao 
     * valida apenas para o tipo de robo que as tem
     * @param robo robo que foi escolhido na entrada
     * @return vetor de duas posicoes cuja primeira casa sera usada para limitar qual o inteiro maximo que o usario
     * pode escolher e a segunda, qual o inteiro minimo. Necessario pois as opçoes variam dependendo do tipo de robo
     * e se esse tem alguma interface extra
     */
    public int[] exibirEscolhaMenuRobo(Robo robo) {
        int[] acoesMaximoMinimo = new int[2];
        
        System.out.printf("\n~~ %s ~~~~~~~~~~~~~~~~~~\n", robo.getClass().getSimpleName().toUpperCase());
        if (robo instanceof Robo) {
            System.out.println("[0] :: Informacoes");
            System.out.println("[1] :: Ligar/Desligar robo");
            System.out.println("[2] :: Mover robo");
            System.out.println("[3] :: Usar sensores");
            System.out.println("[4] :: Mudar direção");
            acoesMaximoMinimo[0] = 4;
        }
        if (robo instanceof RoboTerrestre) {
            System.out.println("[5] :: Aumentar velocidade");
            System.out.println("[6] :: Diminuir velocidade");
            acoesMaximoMinimo[0] = 6;
        }
        if (robo instanceof RoboXadrez) {
            System.out.println("[7] :: Mudar peça");
            acoesMaximoMinimo[0] = 7;
        }
        if (robo instanceof RoboPreguica) {
            System.out.println("[7] :: Descançar");
            acoesMaximoMinimo[0] = 7;
        }
        if (robo instanceof RoboAereo) {
            System.out.println("[5] :: Subir");
            System.out.println("[6] :: Descer");
            acoesMaximoMinimo[0] = 6;
        }
        if (robo instanceof RoboPlanador) {
            System.out.println("[7] :: Mudar tamanho da asa");
            acoesMaximoMinimo[0] = 7;
        }
        if (robo instanceof RoboSatelite) {
            System.out.println("[7] :: Carregar");
            System.out.println("[8] :: Descarregar");
            System.out.println("[9] :: Lançar");
            acoesMaximoMinimo[0] = 9;
        }

        if (temInterfaceExtra(robo)) {
            System.out.print("\n[-2] :: Extras");
            acoesMaximoMinimo[1] = -2;
        } else
            acoesMaximoMinimo[1] = -1;

        System.out.println("\n[-1] :: Voltar");
        return acoesMaximoMinimo;
    }

    /**
     * Le a entrada do usuario de qual acao especifica ele quer que o robo escolhido realize
     * @param robo robo que foi escolhido na entrada principal
     * @param acoesMaximoMinimo vetor de duas posicoes cuja primeira casa sera usada para limitar qual o inteiro maximo que o usario
     * pode escolher e a segunda, qual o inteiro minimo
     * @return inteiro representativo da acao escolhida pelo usuario para o robo fazer
     */
    public int lerEscolhaMenuRobo(Robo robo, int[] acoesMaximoMinimo) {
        System.out.println("\nDigite o numero da acao escolhida:");
        // Loop para que o submenu nao seja printado novamente caso a entrada seja invalida
        while (true) {
            try {
                System.out.print("> ");
                int entradaAcao = scan.nextInt();

                if (entradaAcao <= acoesMaximoMinimo[0] && entradaAcao >= acoesMaximoMinimo[1]) 
                    return entradaAcao;
                else {
                    System.out.printf("!!! %d Nao eh uma opcao valida !!!\n", entradaAcao);
                    continue;
                }
            } catch (InputMismatchException entradaInvalidaEsclhAcao) {
                System.out.println("!!! Use apenas numeros !!!");
                scan.next();
            }
        }
    }

     /**
     * Chama os metodos necessarios do robo baseado na acao escolhida no submenu. Tem tratamento de excessao contra entradas nao inteiras
     * e tambem para caso o robo nao esteja ligado
     * @param robo robo que foi escolhido na entrada principal
     * @param entradaAcao acao que foi escolhida na entrada anterior 
     */
    public void realizarAcoesMenuRobo(Robo robo, int entradaAcao) {
        // Loop para que o usuario fique 'preso' na opcao selecionada ate dar uma entrada valida
        while (true) {
            try {
                // Cada 'case' do 'switch' é uma possivel entrada valida, que deve ser separada em casos quando robos diferentes
                // compartilham um mesmo indice de acao (por exemplo 3 pode significar 'aumentarVelocidade' para um robo terrestre
                // ou 'subir' para um aereo)
                switch (entradaAcao) {
                    // Mesmo em todos os robos
                    case 0:
                        System.out.println("");
                        System.out.print(robo.getDescricao());
                        break;
                    
                    //Mesmo em todos os robos
                    case 1:
                        System.out.println("");
                        if (!robo.estaLigado())
                            robo.ligar();
                        else
                            robo.desligar();
                        break;

                    // Mesmo em todos os robos
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
                    
                    // Mesmo em todos os robos
                    case 3:
                        iniciarMenuSensores(robo);
                        break;
                    
                    // Mesmo em todos os robos  
                    case 4:
                        iniciarMenuDirecao(robo);
                        break;
                    
                    // Aumenta velocidade para robos terrestres
                    // Sobe para robos aereos
                    case 5:
                        if (robo instanceof RoboTerrestre) {
                            System.out.println("\n[int] Em quanto quer aumentar a velocidade?");
                            System.out.print("> ");
                            int vlc = scan.nextInt();
                            System.out.println("");
                            ((RoboTerrestre) robo).aumentarVelocidade(vlc);
                        }
                        else if (robo instanceof RoboAereo) {
                            System.out.print("\n[int] Quantos metros quer subir? ");
                            int metros = scan.nextInt();
                            System.out.println("");
                            ((RoboAereo) robo).subir(metros);
                        }
                        break;
                    
                    // Diminui velocidade para robos terrestres
                    // Desce para robos aereos
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
                    
                    // Muda o tipo de movimento para robos xadrez
                    // Descança energias para robo preguiça
                    // Muda o tamanho da asa para robos planadores
                    // Carrega o tanque para robos satelites
                    case 7:
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
                            System.out.println("\n[int] Qual o novo tamanho da asa?");
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
                    case 8:
                        System.out.println("\n[int] Em quanto quer descarregar?");
                        System.out.print("> ");
                        int cargaRemovida = scan.nextInt();
                        System.out.println("");
                        ((RoboSatelite) robo).descarregar(cargaRemovida);
                        break;

                    // Executa o lancamento para robos satelites
                    case 9:
                        System.out.println("");
                        ((RoboSatelite) robo).lancamento();
                        break;
                }
            } catch (InputMismatchException erro) {
                System.out.println("!!! Use apenas numeros !!!");
                scan.next();
                continue;
            } catch (RoboDesligadoException erro) {
                System.out.println(erro.getMessage());
            }
            break;
        }
    }

    public void iniciarMenuExtras(Robo robo) {
        while (true) {
            int[] listaInterfaces = exibirEscolhaMenuExtras(robo);
            int entradaExtras = lerEscolhaMenuExtras(listaInterfaces[0]);

            switch (entradaExtras) {
                case -1:
                    return;
                default:
                    realizarAcoesMenuExtras(robo, listaInterfaces, entradaExtras);
                    break;
            }
        }
    }

    public int[] exibirEscolhaMenuExtras(Robo robo) {
        int[] listaInterfaces = new int[3];
        int indice = 0;
        listaInterfaces[0] = 0;
        System.out.println("\n-- EXTRAS ---------");
        
        if (robo instanceof Comunicavel) {
            System.out.printf("[%d] :: Comunicar-se\n", indice);
            indice++;
            // Significa que a opçao indice do menu (listaInterfaces[indice], que nesse primeiro caso vai ser sempre 1) 
            // eh o Comunicavel (dado por 0)
            listaInterfaces[indice] = 0;        
        }
        if (robo instanceof Sensoreavel) {
            System.out.printf("[%d] :: Aciconar todos os sensores\n", indice);
            indice++;
            // Significa que a opçao indice do menu (listaInterfaces[indice], que nesse primeiro caso vai ser sempre 1) 
            // eh o Sensoreaevel (dado por 1)
            listaInterfaces[indice] = 1;        
        }
        /*
        if (robo instanceof Destrutivel) {
            System.out.printf("[%d] :: Destruir\n", indice);
            indice++;
            // Significa que a opçao indice do menu (listaInterfaces[indice]) eh o Destrutivel (dado por 1)
            listaInterfaces[indice] = 2;
        }
         */

        listaInterfaces[0] = indice;
        System.out.println("\n[-1] :: Voltar");

        return listaInterfaces;
    }

    public int lerEscolhaMenuExtras(int acoesMaximo) {
        System.out.println("\nQual acao quer realizar?");
        // Loop para que o submenu nao seja printado novamente caso a entrada seja invalida
        while (true) {
            try {
                System.out.print("> ");
                int entradaExtra = scan.nextInt();

                if (entradaExtra >= -1 && entradaExtra <= acoesMaximo){
                    return entradaExtra;
                }
                else {
                    System.out.printf("!!! %d Nao eh uma opcao valida !!!\n", entradaExtra);
                    continue;
                }
            } catch (InputMismatchException erro) {
                System.out.println("!!! Use apenas numeros !!!");
                scan.next();
            }
        }
    }

    public void realizarAcoesMenuExtras(Robo robo, int[] listaInterfaces, int entradaAcoes) {
        while (true) {
            try {
                switch (listaInterfaces[entradaAcoes + 1]) {
                    case 0:
                        System.out.println("[String] Para qual robo a mensagem deve ser enviada?");
                        System.out.print("> ");
                        String nome = scan.next();
                        Robo destinatarioRobo = ambiente.conferirNome(nome);

                        // Confere se o nome eh o de algum robo no ambiente 
                        if (destinatarioRobo != null) {
                            // Confere se o robo eh comunicavel, se nao, lança um erro
                            if (ambiente.getCentral().checarDestinatario(destinatarioRobo)) {
                                System.out.println("[String] Qual a mensagem?");
                                System.out.print("> ");
                                String mensagem = scan.next();
                                ((Comunicavel) robo).enviarMensagem(((Comunicavel) destinatarioRobo), mensagem);
                            }
                            break;
                        } else {
                            System.out.println("!!! Esse nao eh um robo valido !!!");
                            break;
                        }
                    case 1:
                        ((Sensoreavel) robo).acionarSensores();
                        
                }
            } catch (ErroComunicacaoException erro) {
                System.out.println(erro.getMessage());
            } catch (RoboDesligadoException erro) {
                System.out.println(erro.getMessage());
            }
            break;
        }
    }

    // Metodo que coordena os loops do submenu das direcoes
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

    // Printa as opcoes do submenu das direcoes
    public void exibirEscolhaMenuDirecao() {
        System.out.println("\n-- DIREÇOES ---------");
        System.out.println("[0] :: Norte");
        System.out.println("[1] :: Sul");
        System.out.println("[2] :: Leste");
        System.out.println("[3] :: Oeste");

        System.out.println("\n[-1] :: Voltar");
    }
    
    /**
     * Le a entrada do usuario no submenu das direcoes. Tem tratamento de excessao contra entradas que nao sejam numeros inteiros
     * @return inteiro correspondente a opcao selecionada
     */
    public int lerEscolhaMenuDirecao() {
        System.out.println("\nPara qual direção deseja mudar?");
        // Loop para que o submenu nao seja printado novamente caso a entrada seja invalida
        while (true) {
            try {
                System.out.print("> ");
                int entradaDirecao = scan.nextInt();

                if (entradaDirecao >= -1 && entradaDirecao <= 3){
                    return entradaDirecao;
                }
                else {
                    System.out.printf("!!! %d Nao eh uma opcao valida !!!\n", entradaDirecao);
                    continue;
                }
            } catch (InputMismatchException erro) {
                System.out.println("!!! Use apenas numeros !!!");
                scan.next();
            }
        }
    }

    // Metodo que coordena os loops do submenu dos sensores
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

    // Printa as opcoes do submenu dos sensores
    public void exibirEscolhaMenuSensores(Robo robo) {
        System.out.println("\n-- SENSORES ---------");
        robo.exibirSensores();

        System.out.println("\n[-1] voltar");
    }

    /**
     * Caso o usuario escolha usar um sensor de um robo, eh necessario checar se o robo tem o sensor especificado
     * e, depois disso; se tiver, deve ler a entrada de qual coordenada se quer monitorar com o sensor e exibir
     * uma resposta apropriada
     * @param robo robo que foi escolhido na entrada principal
     */
    public int lerEscolhaMenuSensores(Robo robo) {
        if (robo.sensores == null || robo.sensores.size() == 0) {
            System.out.println("\nNao ha sensores nesse robo. Tente com outro.");
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
                    System.out.printf("!!! %d nao eh um sensor valido !!!\n", entradaAcao);
                    continue;
                }
            } catch (InputMismatchException erro) {
                System.out.println("!!! Use apenas numeros !!!");
                scan.next();
            }
        }
    }

    /**
     * Pega a entrada do usuario de qual ponto deve ser 'monitorado' e usa o sensor especificado naquele ponto
     * @param robo robo que foi escolhido na entrada principal
     * @param entradaAcao posicao do sensor que se quer usar na lista de sensores do robo especificado
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
                System.out.println("!!! Use apenas numeros !!!");
                scan.next();
                continue;
            } catch (RoboDesligadoException erro) {
                System.out.println(erro.getMessage());
            }
            break;
        }
    }

    // !!!!!!!!!!!!!!!!!!!!!! ADICIONAR 'OU' OUTRAS INTERFACES AQUI !!!!!!!!!!!!!!!!!!!!!!!1
    public boolean temInterfaceExtra(Robo robo) {
        return (robo instanceof Comunicavel || robo instanceof Sensoreavel);
    }
}
