package simulador;

import java.util.ArrayList;

public class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private Ambiente ambiente;
    public ArrayList<Sensor> sensores;

    public Robo(String nome, int posicaoX, int posicaoY, Ambiente ambiente) {
        setNome(nome);
        setDirecao("Norte");
        setX(posicaoX);
        setY(posicaoY);
        setAmbiente(ambiente);
        ambiente.adicionarRobo(this);       // Adiciona o robo no ambiente logo que é criado
        
        sensores = new ArrayList<Sensor>();       // Inicializa array para os sensores

        System.out.printf("Robo '%s' criado\n", nome);
        checarPosicaoInicial(posicaoX, posicaoY);       // Checa se a posicao em que foi inicializado eh valida
    }

    public void info() {
        System.out.printf("Robo '%s' esta na posicao (%d, %d) apontado na direcao %s.\n\n", getNome(), getX(), getY(), direcao);
    }

    /**
     * Checa se o robo foi inicializado em uma posicao valida, ou seja, fora da regiao de um obstaculo e dentro do ambiente.
     * Caso nao, muda a posicao do robo para uma valida aleatoria
     * @param posX coordenada x da posicao inicial do robo
     * @param posY coordenada y da posicao inicial do robo
     */
    public void checarPosicaoInicial(int posX, int posY) {
        boolean ehValido = true;

        if (ambiente.ehObstaculo(posX, posY)) {
            System.out.printf("AVISO: Robo '%s' foi inicializado dentro de um obstaculo. Nao faca isso.\n", getNome());
            ehValido = false;
        }
        if (!ambiente.dentroDosLimites(posX, posY)) {
            System.out.printf("AVISO: Robo '%s' foi inicializado fora dos limites do ambiente. Nao faca isso.\n", getNome());
            ehValido = false;
        }

        if (!ehValido)
            moverParaValida();
    }

    /**
     * Metodo auxiliar de 'checarPosicaoValida', gera uma nova posicao aleatoria para colocar um robo 
     * que foi inicializado em uma invalida 
     */
    public void moverParaValida() {
        int posX = (int)(Math.random() * ambiente.getLargura());
        int posY = (int)(Math.random() * ambiente.getAltura());

        // Se a posicao gerada tambem nao for valida, tenta novamente
        if (ambiente.ehObstaculo(posX, posY) || !ambiente.dentroDosLimites(posX, posY))
            moverParaValida();
        else {
            setX(posX);
            setY(posY);
            System.out.printf("Robo '%s' foi mudado para a posicao (%d, %d).\n", getNome(), getX(), getY());
        }
    }

    /**
     * Tenta mover o robo totalmente na horizontal e depois totalmente na vertical ate que chegue em seu destino,
     * bata em um obstaculo ou chegue no limite do ambiente, conforme explicado no README
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     */
    public void mover(int deltaX, int deltaY) {
        int i = 0;
        int j = 0;
        System.out.printf("Tentando mover o robo '%s' em %d no eixo x e em %d no y.\n", nome, deltaX, deltaY);

        // Primeiro move o robo totalmente na horizontal
        // Caso deltaX positivo, anda na direcao Leste
        if (deltaX >= 0) {
            for ( ; i < deltaX; i++) {
                // Checa se a posicao em que vai andar eh um obstaculo
                if (getAmbiente().ehObstaculo(posicaoX + i, posicaoY)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento horizontal de '%s'.\n", getX() + i, getY(), nome);
                    i -= 1;
                    break;
                }
                // Checa se a posicao em que vai andar esta fora dos limites do ambiente
                else if (!getAmbiente().dentroDosLimites(posicaoX + i, posicaoY)) {
                    System.out.printf("O robo nao tem autorizacao para sair do ambiente.\n");
                    i -= 1;
                    break;
                }
            }
            // Atualiza posicao X do robo baseado em quanto conseguiu andar
            posicaoX += i;
        }
        // Caso deltaX negativo, anda na direcao Oeste.
        else if (deltaX < 0) {
            for ( ; i < -deltaX; i++) {
                if (getAmbiente().ehObstaculo(posicaoX - i, posicaoY)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento horizontal de '%s'.\n", getX() - i, getY(), nome);
                    i += 1;
                    break;
                }
                else if (!getAmbiente().dentroDosLimites(posicaoX - i, posicaoY)) {
                    System.out.printf("O robo nao tem autorizacao para sair do ambiente.\n");
                    i += 1;
                    break;
                }
            }
            posicaoX -= i;
        }
        // Depois move o robo totalmente na vertical
        // Caso deltaY positivo, anda na direcao Norte. Aqui checa o valor de i antes pois i so eh diferente de deltaX 
        // se o robo ja bateu em algum obstaculo, assim nao tem porque continuar checando na vertical
        if (deltaY >= 0 && i == Math.abs(deltaX)) {
            for ( ; j < deltaY; j++) {
                // Checa se a posicao em que vai andar eh um obstaculo
                if (getAmbiente().ehObstaculo(posicaoX, posicaoY + j)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento vertical de '%s'.\n", getX(), getY() + j, nome);
                    j -= 1;
                    break;
                }
                // Checa se a posicao em que vai andar esta fora dos limites do ambiente
                else if (!getAmbiente().dentroDosLimites(posicaoX, posicaoY + j)) {
                    System.out.printf("O robo nao tem autorizacao para sair do ambiente.\n");
                    j -= 1;
                    break;
                }
            }
            // Atualiza posicao X do robo baseado em quanto conseguiu andar
            posicaoY += j;
        }
        // Caso deltaY negativo, anda na direcao Sul
        else if (deltaY < 0 && i == Math.abs(deltaX)) {
            for ( ; j < -deltaY; j++) {
                if (getAmbiente().ehObstaculo(posicaoX, posicaoY - j)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento vertical de '%s'.\n", getX(), getY() - j, nome);
                    j += 1;
                    break;
                }
                else if (!getAmbiente().dentroDosLimites(posicaoX, posicaoY - j)) {
                    System.out.printf("O robo nao tem autorizacao para sair do ambiente.\n");
                    j += 1;
                    break;
                }
            }
            posicaoY -= j;
        }

        atualizaSensores();
        System.out.printf("O Robo '%s' terminou o movimento na posicao (%d, %d).\n\n", nome, posicaoX, posicaoY);
    }

    public void exibirPosicao() {
        System.out.printf("O robo '%s' esta em (%d, %d) na direcao %s.\n\n", nome, posicaoX, posicaoY, direcao);
    }

    /**
     * Adiciona um sensor de tipo especificado ao robo
     * @param tipoSensor tipo do sensor, o que ele vai monitorar
     * @param raio alcance maximo do sensor 
     */
    public void adicionarSensor(Sensor sensor) {
        if(sensor.getAmbiente() == getAmbiente()) {
            sensores.add(sensor);
        } else {
            System.out.println("Sensor de outro ambiente");
        }
    }

    public void atualizaSensores() {
        // Atualiza a posicao do robo em cada sensor que o robo possui 
        for (Sensor sensor:sensores) {
            sensor.setX(posicaoX);
            sensor.setY(posicaoY);
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDirecao(String drc) {
        if (drc == "Norte" || drc == "Sul" || drc == "Leste" || drc == "Oeste")
            direcao = drc;
        else
            direcao = "Norte";
    }

    protected void setX(int posX) {
        posicaoX = posX;
    }

    protected void setY(int posY) {
        posicaoY = posY;
    }

    protected void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public String getNome() {
        return nome;
    }
    
    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }

    public String getDirecao() {
        return direcao;
    }

    public Ambiente getAmbiente(){
        return ambiente;
    }
}