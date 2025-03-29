package simulador;

public class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private Ambiente ambiente;

    public Robo(String nome, int posicaoX, int posicaoY, Ambiente ambiente) {
        setNome(nome);
        setDirecao(direcao);
        setX(posicaoX);
        setY(posicaoY);
        setAmbiente(ambiente);
        ambiente.adicionarRobo(this);       // Adiciona o robô no ambiente logo que é criado

        System.out.printf("Robô '%s' criado\n", nome);
    }

    public void info() {
        System.out.printf("Robô '%s' está na posição (%d, %d) apontado na direção %s.\n\n", getNome(), getX(), getY(), direcao);
    }

    /**
     * Move o robô no nas direções horizontal e vertical conforme definido
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     */
    public void mover(int deltaX, int deltaY) {
        int novoX = posicaoX + deltaX;
        int novoY = posicaoY + deltaY;

        System.out.printf("Tentando mover o robô '%s' em %d no eixo x e em %d no y.\n", nome, deltaX, deltaY);

        // Checa se o robô não está saindo dos limites do ambiente
        if (getAmbiente().dentroDosLimites(novoX, novoY)) {
            // Checa se não há obstáculos nos 2 caminhos até o ponto final
            if (checarObstaculoCaminho(deltaX, deltaY)) {
                posicaoX = novoX;
                posicaoY = novoY;
                System.out.printf("Movimentado com sucesso.\n");
                this.exibirPosicao();
            } 
            else 
                System.out.printf("Há obstáculos impedindo o movimento de '%s'.\n\n", nome);
        } 
        // Não atualiza posição caso tenha saído dos limites
        else 
            System.out.printf("'%s' não tem permissão para sair do ambiente.\n\n", nome);
    }

    /**
     * Checa se há algum obstáculo impedindo a movimentação definida, sendo essa movimentação explicada no README 
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     * @return true ou false dependendo se há ou não obstáculos
     */
    public Boolean checarObstaculoCaminho(int deltaX, int deltaY) {
        boolean caminhoCima = true, caminhoBaixo = true;

        // Checa se a linha reta da componente horizontal do movimento, partindo da posição atual do robô 
        // ou partindo da posição do robô após andar toda sua componente vertical, contém algum obstáculo;
        // O loop para se ambos os caminhos tiverem um obstáculo;
        if (deltaX > 0) {
            for (int a = 0; (caminhoBaixo || caminhoCima) && a < deltaX; a++) {
                if (ambiente.obstaculosMatriz[posicaoX + a][posicaoY]) 
                    caminhoCima = false;

                if (ambiente.obstaculosMatriz[posicaoX + a][posicaoY + deltaY]) 
                    caminhoBaixo = false;
            }
        }
        else {
            for (int b = 0; (caminhoBaixo || caminhoCima) && b > deltaX; b--) {
                if (ambiente.obstaculosMatriz[posicaoX + b][posicaoY]) 
                    caminhoCima = false;

                if (ambiente.obstaculosMatriz[posicaoX + b][posicaoY + deltaY]) 
                    caminhoBaixo = false;
            }
        }

        // Checa se a linha reta da componente vertical do movimento, partindo da posição atual do robô 
        // ou partindo da posição do robô após andar toda sua componente horizontal, contém algum obstáculo;
        if (deltaY > 0) {
            for (int c = 0; (caminhoBaixo || caminhoCima) && c < deltaY; c++) {
                if (ambiente.obstaculosMatriz[posicaoX][posicaoY + c])
                    caminhoBaixo = false;
                
                if (ambiente.obstaculosMatriz[posicaoX + deltaX][posicaoY + c])
                    caminhoCima = false;
            }
        }
        else {
            for (int d = 0; (caminhoBaixo || caminhoCima) && d > deltaY; d--) {
                if (ambiente.obstaculosMatriz[posicaoX][posicaoY + d])
                    caminhoBaixo = false;
                
                if (ambiente.obstaculosMatriz[posicaoX + deltaX][posicaoY + d])
                    caminhoCima = false;
            }
        }

        return caminhoBaixo || caminhoCima;
    }

    public boolean identificarObstaculo() {
        boolean temObstaculo = false;

        // Checa se há algum obstáculo nas 4 adjacentes ao robô
        if(!(getAmbiente().dentroDosLimites(posicaoX + 1, posicaoY) && getAmbiente().dentroDosLimites(posicaoX - 1, posicaoY)
            || getAmbiente().dentroDosLimites(posicaoX, posicaoY + 1) || getAmbiente().dentroDosLimites(posicaoX, posicaoY - 1)))
            temObstaculo = true;
        else if (ambiente.obstaculosMatriz[posicaoX + 1][posicaoY] || ambiente.obstaculosMatriz[posicaoX - 1][posicaoY]
        || ambiente.obstaculosMatriz[posicaoX][posicaoY + 1] || ambiente.obstaculosMatriz[posicaoX][posicaoY - 1])
            temObstaculo = true;
        
        return temObstaculo;
    }

    public boolean podeMover(int deltaX, int deltaY) {
        return getAmbiente().dentroDosLimites(getX() + deltaX, getY() + deltaY) && checarObstaculoCaminho(deltaX, deltaY);
    }

    public void exibirPosicao() {
        System.out.printf("O robô '%s' está em (%d, %d) na direção %s.\n\n", nome, posicaoX, posicaoY, direcao);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDirecao(String drc) {
        if (drc == "Norte" || drc == "Sul" || drc == "Leste" || drc == "Oeste")
            direcao = drc;
    }

    protected void setX(int x) {
        posicaoX = x;
    }

    protected void setY(int y) {
        posicaoY = y;
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