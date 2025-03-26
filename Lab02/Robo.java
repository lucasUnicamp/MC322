public class Robo {
    protected String nome;
    protected String direcao;
    protected int posicaoX;
    protected int posicaoY;
    protected Ambiente ambiente;

    public Robo(String nome, int posicaoX, int posicaoY, Ambiente ambiente) {
        this.nome = nome;
        this.direcao = "Norte";
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.ambiente = ambiente;
        ambiente.adicionarRobo(this);   // Adiciona o robô no ambiente logo que é criado
        imprimeCriacao();
    }

    public void imprimeCriacao() {
        System.out.printf("Robô padrão '%s' criado na posição (%d, %d) apontado na direção %s.\n"
        , nome, posicaoX, posicaoY, direcao);
    }

    /* MOVIMENTO DO ROBÔ ***************************************************************************************
     * Optamos por considerar que o robô pode tomar somente dois caminhos dado um deltaX e um deltaY, esses são:
     * - mover-se 'deltaX' totalmente no eixo X primeiro e depois 'deltaY' totalmente no eixo Y; ou
     * - mover-se 'deltaY' totalmente no eixo Y primeiro e depois 'deltaX' totalmente no eixo X; 
     *                            # # # # []                      [][][][][]
     *                            # # # # []          ou          [] # # # #
     *                            [][][][][]                      [] # # # #
     ******************************************************************************************************** */
    public void mover(int deltaX, int deltaY) {
        int novoX = posicaoX + deltaX;
        int novoY = posicaoY + deltaY;

        // Checa se o robô não está saindo dos limites do ambiente
        if((novoX >= 0) && (novoY >= 0) && (novoX <= ambiente.largura) && (novoY <= ambiente.altura)) {
            // Checa se não há obstáculos nos 2 caminhos até o ponto final
            if (checarObstaculoCaminho(deltaX, deltaY)) {
                posicaoX = novoX;
                posicaoY = novoY;
                System.out.printf("Movendo robô '%s' em %d no eixo x e em %d no y.\n", nome, deltaX, deltaY);
                this.exibirPosicao();
            } 
            else 
                System.out.printf("Há obstáculos impedindo o movimento de '%s'.\n", nome);
        } 
        // Não atualiza posição caso tenha saído dos limites
        else 
            System.out.printf("'%s' não tem permissão para sair do ambiente.\n", nome);
    }

    public Boolean checarObstaculoCaminho(int deltaX, int deltaY) {
        boolean caminhoCima = true, caminhoBaixo = true;

        // Checa se a linha reta da componente horizontal do movimento, partindo da posição atual do robô 
        // ou partindo da posição do robô após andar toda sua componente vertical, contém algum obstáculo;
        // O loop para se ambos os caminhos tiverem um obstáculo;
        for (int a = 0; (caminhoBaixo || caminhoCima) && a < deltaX; a++) {
            if (ambiente.obstaculos[posicaoX + a][posicaoY]) 
                caminhoCima = false;

            if (ambiente.obstaculos[posicaoX + a][posicaoY + deltaY]) 
                caminhoBaixo = false;
        }

        // Checa se a linha reta da componente vertical do movimento, partindo da posição atual do robô 
        // ou partindo da posição do robô após andar toda sua componente horizontal, contém algum obstáculo;
        for (int b = 0; (caminhoBaixo || caminhoCima) && b < deltaY; b++) {
            if (ambiente.obstaculos[posicaoX][posicaoY + b])
                caminhoBaixo = false;
            
            if (ambiente.obstaculos[posicaoX + deltaX][posicaoY + b])
                caminhoCima = false;
        }

        return caminhoBaixo || caminhoCima;
    }

    public void exibirPosicao() {
        System.out.printf("O robô '%s' está em (%d, %d) na direção %s.\n", nome, posicaoX, posicaoY, direcao);
    }

    public void setDirecao(String drc) {
        direcao = drc;
    }

    public String getDirecao() {
        return direcao;
    }

    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }
}