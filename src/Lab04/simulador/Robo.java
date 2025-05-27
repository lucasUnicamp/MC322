package simulador;

import java.util.ArrayList;

import simulador.excecoes.ColisaoException;
import simulador.excecoes.RoboDesligadoException;
import simulador.interfaces.Entidade;

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
        setAmbiente(ambiente);
        setX(posicaoX);
        setY(posicaoY);
        setZ(0);

        ambiente.adicionarEntidade(this);       // Adiciona o robô no ambiente logo que é criado
        sensores = new ArrayList<Sensor>();       // Inicializa array para os sensores
        System.out.printf("\nRobô '%s' criado.\n", nome);

        checarPosicaoInicial(posicaoX, posicaoY, posicaoZ);       // Checa se a posição em que foi inicializado é válida
    }

    /**
     * Checa se o robô foi inicializado em uma posição inválida, ou seja, fora da regiao de um obstáculo e dentro do ambiente.
     * Caso não, muda a posição do robô para uma válida aleatória
     * @param posX coordenada x da posicao inicial do robô
     * @param posY coordenada y da posicao inicial do robô
     */
    public void checarPosicaoInicial(int posX, int posY, int posZ) {
        boolean éValido = true;

        if (ambiente.estaOcupado(posX, posY, posZ)) {
            System.out.printf("!!! AVISO: Robô '%s' foi inicializado em uma posição inválida. Não faça isso. !!!\n", getNome());
            éValido = false;
        }
        if (!ambiente.dentroDosLimites(posX, posY)) {
            System.out.printf("!!! AVISO: Robô '%s' foi inicializado fora dos limites do ambiente. Não faça isso. !!!\n", getNome());
            éValido = false;
        }

        if (!éValido)
            moverParaValida();
    }

    /**
     * Método auxiliar de 'checarPosicaoValida', gera uma nova posição aleatória para colocar um robo 
     * que foi inicializado em uma inválida 
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
            System.out.printf("Robô '%s' foi mudado para a posição aleatória (%d, %d).\n", getNome(), getX(), getY());
        }
    }

    /**
     * Movimentacao do robô que depende se tem ou não um sensor de obstáculos. Se não tiver, vai tentar apenas um caminho
     * e ir andando de 1 um a 1 até chegar ao destino ou colidir; se tiver, vai checar antes se há obstáculos obstruindo 
     * dois possíveis caminhos e nem vai tentar andar caso tenha
     * <p>
     * OBS.: em nossa implementação, nao faz sentido mover o robô no eixo z, ja que apenas robôs aéreos e seus filhos
     * conseguem fazer isso
     * @param x posição no eixo x final a qual se quer chegar
     * @param y posição no eixo y final a qual se quer chegar
     */
    public void moverPara(int x, int y) throws RoboDesligadoException {
        if (estaLigado()){
            int deltaX = x - getX();
            int deltaY = y - getY();
            int indice = temSensorTipo("SensorObstaculo");
            System.out.printf("Tentando mover o Robô '%s' para a posição (%d, %d).\n", nome, x, y);
            
            if (indice != -1)
                moverComSensor(deltaX, deltaY, indice);
            else
                moverSemSensor(deltaX, deltaY);
            
            atualizaSensores();
            System.out.printf("O Robô '%s' terminou o movimento na posição (%d, %d).\n", nome, getX(), getY());
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    /**
     * Tenta mover o robô totalmente na horizontal e depois totalmente na vertical até que chegue em seu destino,
     * bata em um obstáculo ou chegue no limite do ambiente, conforme explicado no README
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     */
    private void moverSemSensor(int deltaX, int deltaY) {
        int i = 0, j = 0;

        try {
            // Primeiro move o robô totalmente na horizontal
            // Caso deltaX positivo, anda na direção Leste
            if (deltaX > 0) {
                for ( ; i <= deltaX; i++) {
                    // Checa se a posição em que vai andar é um obstáculo
                    if (getAmbiente().estaOcupado(posicaoX + i, posicaoY, posicaoZ)) {
                        setX(getX() + i - 1);
                        throw new ColisaoException(getID(), getX() + 1, getY(), getZ());
                    }
                    // Checa se a posição em que vai andar está fora dos limites do ambiente
                    else if (!getAmbiente().dentroDosLimites(posicaoX + i, posicaoY)) {
                        System.out.println("O robô não tem autorização para sair do ambiente.");
                        break;
                    }
                }
                // Atualiza posição X do robô baseado em quanto conseguiu andar
                setX(getX() + i - 1);
            }
            // Caso deltaX negativo, anda na direção Oeste.
            else if (deltaX < 0) {
                for ( ; i <= -deltaX; i++) {
                    if (getAmbiente().estaOcupado(posicaoX - i, posicaoY, posicaoZ)) {
                        setX(getX() - i + 1);
                        throw new ColisaoException(getID(), getX() - 1, getY(), getZ());
                    }
                    else if (!getAmbiente().dentroDosLimites(posicaoX - i, posicaoY)) {
                        System.out.println("O robô não tem autorização para sair do ambiente.");
                        break;
                    }
                }
                setX(getX() - i + 1);
            }
            // Depois move o robô totalmente na vertical
            // Caso deltaY positivo, anda na direção Norte. Aqui checa o valor de i antes pois i so é diferente de deltaX 
            // se o robo já bateu em algum obstáculo, assim não tem porque continuar checando na vertical
            if (deltaY > 0 && (i - 1) == Math.abs(deltaX)) {
                for ( ; j <= deltaY; j++) {
                    // Checa se a posição em que vai andar é um obstáculo
                    if (getAmbiente().estaOcupado(posicaoX, posicaoY + j, posicaoZ)) {
                        setY(getY() + j - 1);
                        throw new ColisaoException(getID(), getX(), getY() + 1, getZ());
                    }
                    // Checa se a posição em que vai andar está fora dos limites do ambiente
                    else if (!getAmbiente().dentroDosLimites(posicaoX, posicaoY + j)) {
                        System.out.println("O robô não tem autorização para sair do ambienten");
                        break;
                    }
                }
                // Atualiza posição Y do robô baseado em quanto conseguiu andar
                setY(getY() + j - 1);
            }
            // Caso deltaY negativo, anda na direção Sul
            else if (deltaY < 0 && (i - 1) == Math.abs(deltaX)) {
                for ( ; j <= -deltaY; j++) {
                    if (getAmbiente().estaOcupado(posicaoX, posicaoY - j, posicaoZ)) {
                        setY(getY() - j + 1);
                        throw new ColisaoException(getID(), getX(), getY() - 1, getZ());
                    }
                    else if (!getAmbiente().dentroDosLimites(posicaoX, posicaoY - j)) {
                        System.out.println("O robô não tem autorização para sair do ambiente.");
                        break;
                    }
                }
                setY(getY() - j + 1);
            }
        } catch (ColisaoException erro) {
            System.out.println(erro.getMessage());
        }
    }

    /**
     * Tenta mover o robô ou totalmente na vertical e depois na horizontal ou totalmente na horizontal e depois na vertical,
     * mas checa se há obstáculos impedindo ambos os caminhos, conforme explicado no README
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     * @param indice posicao do SensorObstaculo na ArrayList de sensores do robô 
     */
    protected void moverComSensor(int deltaX, int deltaY, int indice) throws RoboDesligadoException{
        if (estaLigado()) {
            int novoX = posicaoX + deltaX;
            int novoY = posicaoY + deltaY;
            // Downcasting porque sei que esse elemento da ArrayList deve ser do tipo SensorObstaculo. Necessário pois preciso acessar o método
            // 'checarObstaculoCaminho' que não seria possível mantendo o objeto como da classeSensor Sensor
            SensorObstaculo sensorObs = (SensorObstaculo)sensores.get(indice);

            // Checa se o robo não vai sair dos limites do ambiente após se mover
            if (getAmbiente().dentroDosLimites(novoX, novoY)) {
                int haObstaculosCaminho = sensorObs.checarObstaculoCaminho(getX(), getY(), deltaX, deltaY);
                // Checa se não há obstáculos nos 2 caminhos até o ponto final
                if (haObstaculosCaminho == 1) {
                    setX(novoX);
                    setY(novoY);
                    System.out.println("Movimentado com sucesso.");
                    exibirPosicao();
                }
                else if (haObstaculosCaminho == 0)
                    System.out.printf("Caminho sai do raio do sensor do Robô '%s'. Como não é possivel garantir sua segurança, preferiu ficar parado.\n", getNome(), getNome());
                else 
                    System.out.printf("Obstáculos que impediriam o movimento de '%s' foram detectados pelo sensor, '%s' permaneceu parado.\n", getNome(), getNome());
            } 
            // Não atualiza posição caso tenha saído dos limites
            else 
                System.out.printf("O sensor checou que essa posição sairia dos limites do ambiente, e '%s' não tem permissão para fazer isso.\n", getNome());
        } else {
            throw new RoboDesligadoException(getID());
        }
   }

    /**
     * Adiciona um sensor de tipo especificado ao robô
     * @param tipoSensor tipo do sensor, o que ele vai monitorar
     * @param raio alcance máximo do sensor 
     */
    public void adicionarSensor(Sensor sensor) {
        // Checa se o robô já não tem o sensor dado
        if (sensores.size() == 0 || temSensorTipo(sensor.getClass().getSimpleName()) == -1) {
            if (sensor.getAmbiente() == getAmbiente()) {
                sensores.add(sensor);
                System.out.printf("%s adicionado ao Robô '%s' com sucesso.\n", sensor.nomeDoSensor(), getNome());
            }
            else 
                System.out.printf("Não é possivel adicionar um %s ao Robô '%s' pois esse é de outro ambiente.\n", sensor.nomeDoSensor(), getNome());
        }
        else
            System.out.printf("Não é possivel adicionar mais de um %s ao Robô '%s'.\n", sensor.nomeDoSensor(), getNome());
        atualizaSensores();
    }

    public abstract void atualizaSensores();

    /**
     * Procura na lista de sensores do robô um sensor do tipo especificado
     * @param tipoSensor tipo de sensor que se procura, identificado pelo nome da classeSensor
     * @return o indice do sensor procurado na lista ou -1 caso o robo nao tenha aquele sensor. Retornar o índice faz com que a função possa
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
     * Aciona o método 'monitorar' do sensor especificado caso o robô o tenha
     * @param indiceSensor tipo de sensor que se quer usar, sendo 1 = obstáculo e 2 = temperatura
     * @param posX coordenada x em que se vai usar o sensor
     * @param posY coordenada y em que se vai usar o sensor
     * @throws RoboDesligadoException não funciona caso o robô esteja desligado
     */
    public void usarSensor(int indiceSensor, int posX, int posY) throws RoboDesligadoException{
        if (estaLigado()) {
            // Switch case com o valor retornado pelo 'monitorar' do sensor
            switch(sensores.get(indiceSensor).monitorar(posX, posY)) {
                case 1:
                    System.out.println("Monitoramento ocorreu com sucesso.");
                    break;
                case 2:
                    System.out.println("Não se pode monitorar posicões fora do ambiente.");
                    break;
                case 3:
                    System.out.println("Não se pode monitorar posicões fora do alcance do sensor.");
                    break;
            }
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    public abstract void executarTarefa();

    public void ligar() {
        setEstado(EstadoRobo.LIGADO);
        System.out.printf("O Robô '%s' foi ligado.\n", getNome());
    }

    public void desligar() {
        setEstado(EstadoRobo.DESLIGADO);
        System.out.printf("O Robô '%s' foi desligado.\n", getNome());
    }

    public boolean estaLigado() {
        return getEstado() == EstadoRobo.LIGADO;
    }

    public void exibirPosicao() {
        System.out.printf("O Robô '%s' está agora em (%d, %d) na direção %s.\n", getNome(), getX(), getY(), getDirecao());
    }

    /**
     * Exibe os sensores presentes no robô, na ordem em que foram adicionados
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
        // Para que nada seja impresso na inicialização
        if (direcao == null) {
            direcao = "Norte";
            return;
        }
        switch (drc) {
            case 0:
                direcao = "Norte";
                System.out.println("\nDireção alterada para Norte");
                break;
            case 1:
                direcao = "Sul";
                System.out.println("\nDireção alterada para Sul");
                break;
            case 2:
                direcao = "Leste";
                System.out.println("\nDireção alterada para Leste");
                break; 
            case 3:
                direcao = "Oeste";
                System.out.println("\nDireção alterada para Oeste");
                break;       
        }
    }

    public void setEstado(EstadoRobo est) {
        estado = est;
    }

    protected void setX(int posX) {
        ambiente.moverEntidadeMapa(this, posX, getY(), getZ());
        posicaoX = posX;
        atualizaSensores();
    }

    protected void setY(int posY) {
        ambiente.moverEntidadeMapa(this, getX(), posY, getZ());
        posicaoY = posY;
        atualizaSensores();
    }

    protected void setZ(int posZ) {
        ambiente.moverEntidadeMapa(this, getX(), getY(), posZ);
        posicaoZ = posZ;
        posicaoZ = posZ >= 0 ? posZ : 0;        // Corrige altura contra valores negativos
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