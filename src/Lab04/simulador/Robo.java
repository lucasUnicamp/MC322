package simulador;

import java.util.ArrayList;

public abstract class Robo implements Entidade {
    private String nome;
    private String id;
    private String direcao;
    private EstadoRobo estado;
    private final TipoEntidade tipoEnt;
    private int posicaoX;
    private int posicaoY;
    private int posicaoZ;
    private Ambiente ambiente;
    public ArrayList<Sensor> sensores;

    public Robo(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente) {
        setNome(nome);
        setID(id);
        setDirecao(0);
        setEstado(EstadoRobo.DESLIGADO);
        this.tipoEnt = TipoEntidade.ROBO;
        setX(posicaoX);
        setY(posicaoY);
        setZ(0);
        setAmbiente(ambiente);

        ambiente.adicionarEntidade(this);       // Adiciona o robo no ambiente logo que é criado
        sensores = new ArrayList<Sensor>();       // Inicializa array para os sensores
        System.out.printf("\nRobo '%s' criado.\n", nome);

        checarPosicaoInicial(posicaoX, posicaoY, posicaoZ);       // Checa se a posicao em que foi inicializado eh valida
    }

    /**
     * Checa se o robo foi inicializado em uma posicao valida, ou seja, fora da regiao de um obstaculo e dentro do ambiente.
     * Caso nao, muda a posicao do robo para uma valida aleatoria
     * @param posX coordenada x da posicao inicial do robo
     * @param posY coordenada y da posicao inicial do robo
     */
    public void checarPosicaoInicial(int posX, int posY, int posZ) {
        boolean ehValido = true;

        if (ambiente.estaOcupado(posX, posY, posZ)) {
            System.out.printf("!!! AVISO: Robo '%s' foi inicializado em uma posicao invalida. Nao faca isso. !!!\n", getNome());
            ehValido = false;
        }
        if (!ambiente.dentroDosLimites(posX, posY)) {
            System.out.printf("!!! AVISO: Robo '%s' foi inicializado fora dos limites do ambiente. Nao faca isso. !!!\n", getNome());
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
        int posY = (int)(Math.random() * ambiente.getProfundidade());

        // Se a posicao gerada tambem nao for valida, tenta novamente
        if (ambiente.estaOcupado(posX, posY, 0) || !ambiente.dentroDosLimites(posX, posY))
            moverParaValida();
        else {
            setX(posX);
            setY(posY);
            System.out.printf("Robo '%s' foi mudado para a posicao aleatoria (%d, %d).\n", getNome(), getX(), getY());
        }
    }

    /**
     * Movimentacao do robo que depende se tem ou nao um sensor de obstaculos. Se nao tiver, vai tentar apenas um caminho
     * e ir andando de 1 um a 1 ate chegar ao destino ou colidir; se tiver, vai checar antes se ha obstaculos obstruindo 
     * dois possiveis caminhos e nem vai tentar andar caso tenha
     * <p>
     * OBS.: em nossa implementacao, nao faz sentido mover o robo no eixo z, ja que apenas robos aereos e seus filhos
     * conseguem fazer isso
     * @param x posicao no eixo x final a qual se quer chegar
     * @param y posicao no eixo y final a qual se quer chegar
     */
    public void moverPara(int x, int y) throws RoboDesligadoException{
        if (estaLigado()){
            int deltaX = x - getX();
            int deltaY = y - getY();
            int indice = temSensorTipo("SensorObstaculo");
            System.out.printf("Tentando mover o robo '%s' para a posiçao (%d, %d).\n", nome, x, y);
            
            if (indice != -1)
                moverComSensor(deltaX, deltaY, indice);
            else
                moverSemSensor(deltaX, deltaY);
            
            atualizaSensores();
            System.out.printf("O Robo '%s' terminou o movimento na posicao (%d, %d).\n", nome, getX(), getY());
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    /**
     * Tenta mover o robo totalmente na horizontal e depois totalmente na vertical ate que chegue em seu destino,
     * bata em um obstaculo ou chegue no limite do ambiente, conforme explicado no README
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     */
    private void moverSemSensor(int deltaX, int deltaY) {
        int i = 0, j = 0;
        // Primeiro move o robo totalmente na horizontal
        // Caso deltaX positivo, anda na direcao Leste
        if (deltaX >= 0) {
            for ( ; i <= deltaX; i++) {
                // Checa se a posicao em que vai andar eh um obstaculo
                if (getAmbiente().estaOcupado(posicaoX + i, posicaoY, posicaoZ)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento horizontal de '%s'.\n", getX() + i, getY(), getNome());
                    break;
                }
                // Checa se a posicao em que vai andar esta fora dos limites do ambiente
                else if (!getAmbiente().dentroDosLimites(posicaoX + i, posicaoY)) {
                    System.out.println("O robo nao tem autorizacao para sair do ambiente.");
                    break;
                }
            }
            // Atualiza posicao X do robo baseado em quanto conseguiu andar
            i -= 1;
            posicaoX += i;
        }
        // Caso deltaX negativo, anda na direcao Oeste.
        else if (deltaX < 0) {
            for ( ; i <= -deltaX; i++) {
                if (getAmbiente().estaOcupado(posicaoX - i, posicaoY, posicaoZ)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento horizontal de '%s'.\n", getX() - i, getY(), getNome());
                    break;
                }
                else if (!getAmbiente().dentroDosLimites(posicaoX - i, posicaoY)) {
                    System.out.println("O robo nao tem autorizacao para sair do ambiente.");
                    break;
                }
            }
            i -= 1;
            posicaoX -= i;
        }
        // Depois move o robo totalmente na vertical
        // Caso deltaY positivo, anda na direcao Norte. Aqui checa o valor de i antes pois i so eh diferente de deltaX 
        // se o robo ja bateu em algum obstaculo, assim nao tem porque continuar checando na vertical
        if (deltaY >= 0 && i == Math.abs(deltaX)) {
            for ( ; j <= deltaY; j++) {
                // Checa se a posicao em que vai andar eh um obstaculo
                if (getAmbiente().estaOcupado(posicaoX, posicaoY + j, posicaoZ)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento vertical de '%s'.\n", getX(), getY() + j, getNome());
                    break;
                }
                // Checa se a posicao em que vai andar esta fora dos limites do ambiente
                else if (!getAmbiente().dentroDosLimites(posicaoX, posicaoY + j)) {
                    System.out.println("O robo nao tem autorizacao para sair do ambienten");
                    break;
                }
            }
            // Atualiza posicao X do robo baseado em quanto conseguiu andar
            j -= 1;
            posicaoY += j;
        }
        // Caso deltaY negativo, anda na direcao Sul
        else if (deltaY < 0 && i == Math.abs(deltaX)) {
            for ( ; j <= -deltaY; j++) {
                if (getAmbiente().estaOcupado(posicaoX, posicaoY - j, posicaoZ)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento vertical de '%s'.\n", getX(), getY() - j, getNome());
                    break;
                }
                else if (!getAmbiente().dentroDosLimites(posicaoX, posicaoY - j)) {
                    System.out.println("O robo nao tem autorizacao para sair do ambiente.");
                    break;
                }
            }
            j -= 1;
            posicaoY -= j;
        }
    }

    /**
     * Tenta mover o robo ou totalmente na vertical e depois na horizontal ou totalmente na horizontal e depois na vertical,
     * mas checa se ha obstaculos impedindo ambos os caminhos, conforme explicado no README
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     * @param indice posicao do SensorObstaculo na ArrayList de sensores do robo 
     */
    protected void moverComSensor(int deltaX, int deltaY, int indice) throws RoboDesligadoException{
        if(estaLigado()) {
            int novoX = posicaoX + deltaX;
            int novoY = posicaoY + deltaY;
            // Downcasting porque sei que esse elemento da ArrayList deve ser do tipo SensorObstaculo. Necessario pois preciso acessar o metodo
            // 'checarObstaculoCaminho' que nao seria possivel mantendo o objeto como da classeSensor Sensor
            SensorObstaculo sensorObs = (SensorObstaculo)sensores.get(indice);

            // Checa se o robo nao vai sair dos limites do ambiente apos se mover
            if (getAmbiente().dentroDosLimites(novoX, novoY)) {
                int haObstaculosCaminho = sensorObs.checarObstaculoCaminho(getX(), getY(), deltaX, deltaY);
                // Checa se nao ha obstaculos nos 2 caminhos até o ponto final
                if (haObstaculosCaminho == 1) {
                    posicaoX = novoX;
                    posicaoY = novoY;
                    System.out.println("Movimentado com sucesso.");
                    exibirPosicao();
                }
                else if (haObstaculosCaminho == 0)
                    System.out.printf("Caminho sai do raio do sensor do Robo '%s'. Como não eh possivel garantir sua segurança, preferiu ficar parado.\n", getNome(), getNome());
                else 
                    System.out.printf("Obstaculos que impediriam o movimento de '%s' foram detectados pelo sensor, '%s' permaneceu parado.\n", getNome(), getNome());
            } 
            // Não atualiza posição caso tenha saído dos limites
            else 
                System.out.printf("O sensor checou que essa posicao sairia dos limites do ambiente, e '%s' não tem permissão para fazer isso.\n", getNome());
        } else {
            throw new RoboDesligadoException(getID());
        }
   }

    /**
     * Adiciona um sensor de tipo especificado ao robo
     * @param tipoSensor tipo do sensor, o que ele vai monitorar
     * @param raio alcance maximo do sensor 
     */
    public void adicionarSensor(Sensor sensor) {
        // Checa se o robo ja nao tem o sensor dado
        if (sensores.size() == 0 || temSensorTipo(sensor.getClass().getSimpleName()) == -1) {
            if (sensor.getAmbiente() == getAmbiente()) {
                sensores.add(sensor);
                System.out.printf("%s adicionado ao Robo '%s' com sucesso.\n", sensor.nomeDoSensor(), getNome());
            }
            else 
                System.out.printf("Nao eh possivel adicionar um %s ao Robo '%s' pois esse eh de outro ambiente.\n", sensor.nomeDoSensor(), getNome());
        }
        else
            System.out.printf("Nao eh possivel adicionar mais de um %s ao Robo '%s'.\n", sensor.nomeDoSensor(), getNome());
        atualizaSensores();
    }

    public abstract void atualizaSensores();

    /**
     * Procura na lista de sensores do robo um sensor do tipo especificado
     * @param tipoSensor tipo de sensor que se procura, identificado pelo nome da classeSensor
     * @return o indice do sensor procurado na lista ou -1 caso o robo nao tenha aquele sensor. Retornar o indice faz com que a funcao possa
     * ser usada como booleana (se for diferente de -1, tem o sensor) e possamos acessar o sensor da lista de sensores
     */
    public int temSensorTipo(String classeSensor) {
        if (sensores != null) {
            for (int i = 0; i < sensores.size(); i++) {
                if (sensores.get(i).getClass().getSimpleName().equals(classeSensor))
                    return i;
            }
        }
        return -1;
    }

    /**
     * Aciona o metodo 'monitorar' do sensor especificado caso o robo o tenha
     * @param tipoSensor tipo de sensor que se quer usar, sendo 1 = obstaculo e 2 = temperatura
     */
    public void usarSensor(int indiceSensor, int posX, int posY) throws RoboDesligadoException{
        if(estaLigado()){
            // Switch case com o valor retornado pelo 'monitorar' do sensor
            switch(sensores.get(indiceSensor).monitorar(posX, posY)) {
                case 1:
                    System.out.println("Monitoramento ocorreu com sucesso.");
                    break;
                case 2:
                    System.out.println("Nao se pode monitorar posicoes fora do ambiente.");
                    break;
                case 3:
                    System.out.println("Nao se pode monitorar posicoes fora do alcance do sensor.");
                    break;
            }
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    public void ligar() {
        setEstado(EstadoRobo.LIGADO);
        System.out.printf("O Robo '%s' foi ligado.\n", getNome());
    }

    public void desligar() {
        setEstado(EstadoRobo.DESLIGADO);
        System.out.printf("O Robo '%s' foi desligado.\n", getNome());
    }

    public boolean estaLigado() {
        return getEstado() == EstadoRobo.LIGADO;
    }

    public void exibirPosicao() {
        System.out.printf("O Robo '%s' esta agora em (%d, %d) na direcao %s.\n", getNome(), getX(), getY(), getDirecao());
    }

    /**
     * Exibe os sensores presentes no robo, na ordem em que foram adicionados
     */
    public void exibirSensores() {
        Sensor sensor;
        if (sensores != null) {
            for (int i = 0; i < sensores.size(); i++) {
                sensor = sensores.get(i);
                System.out.printf("[%d] :: ", i);
                sensor.exibirRaio();
            }
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setDirecao(int drc) {
        // Para que nada seja impresso na inicializacao
        if (direcao == null) {
            direcao = "Norte";
            return;
        }
        switch (drc) {
            case 0:
                direcao = "Norte";
                System.out.println("\nDireçao alterada para Norte");
                break;
            case 1:
                direcao = "Sul";
                System.out.println("\nDireçao alterada para Sul");
                break;
            case 2:
                direcao = "Leste";
                System.out.println("\nDireçao alterada para Leste");
                break; 
            case 3:
                direcao = "Oeste";
                System.out.println("\nDireçao alterada para Oeste");
                break;       
        }
    }

    public void setEstado(EstadoRobo est) {
        estado = est;
    }

    protected void setX(int posX) {
        posicaoX = posX;
        ambiente.moverEntidade(this, getX(), getY(), getZ());
        atualizaSensores();
    }

    protected void setY(int posY) {
        posicaoY = posY;
        ambiente.moverEntidade(this, getX(), getY(), getZ());
        atualizaSensores();
    }

    protected void setZ(int posZ) {
        posicaoZ = posZ;
        posicaoZ = posZ >= 0 ? posZ : 0;        // Corrige altura contra valores negativos
        ambiente.moverEntidade(this, getX(), getY(), getZ());
        atualizaSensores();
    }

    protected void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public String getNome() {
        return nome;
    }

    public String getID() {
        return id;
    }

    public String getDirecao() {
        return direcao;
    }

    public EstadoRobo getEstado() {
        return estado;
    }

    public TipoEntidade getTipoEntidade() {
        return tipoEnt;
    }

    public Ambiente getAmbiente(){
        return ambiente;
    }
    
    /***************************
     ******** INTERFACE ********
     ***************************/

    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }

    public int getZ() {
        return posicaoZ;
    }

    public int getLargura() {
        return 0;
    };
    public int getProfundidade(){
        return 0;
    };
    public int getAltura(){
        return 0;
    };

    public TipoEntidade getTipo() {
        return TipoEntidade.ROBO;
    }

    public abstract String getDescricao();

    public abstract char getRepresentacao();
}