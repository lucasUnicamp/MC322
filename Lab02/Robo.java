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

    // Usada para imprimir as informações úteis e checar se estão corretas após a criação do robô
    protected void imprimeCriacao() {
        System.out.printf("Robô padrão '%s' criado na posição (%d, %d) apontado na direção %s.\n"
        , nome, posicaoX, posicaoY, direcao);
    }

    public void mover(int deltaX, int deltaY) {
        int novoX = posicaoX + deltaX;
        int novoY = posicaoY + deltaY;

        // Checa se o robô não está saindo dos limites do ambiente
        if((novoX >= 0) && (novoY >= 0) && (novoX <= ambiente.largura) && (novoY <= ambiente.altura)) {
            posicaoX = novoX;
            posicaoY = novoY;
            System.out.printf("Movendo robô '%s' em %d no eixo x e em %d no y\n", nome, deltaX, deltaY);
        } 
        // Não atualiza posição caso tenha saído dos limites
        else 
            System.out.println("Impossível ir para coordenadas negativas.");
    }

    // public Boolean checarObstaculoCaminho(int deltaX, int deltaY) {
    //     int tempX = posicaoX;
    //     int tempY = posicaoY;

    //     for (int i = 0; i < deltaX; i++) {
    //         if (ambiente.obstaculos[tempX + 1][posicaoY] || ambiente.obstaculos[tempX - 1][posicaoY] || ambiente.obstaculos[tempX][posicaoY + 1] || ambiente.obstaculos[tempX][posicaoY - 1]) {

    //         }

    //         tempX++;
    //     }

    //     return true;
    // }

    public void exibirPosicao() {
        System.out.printf("O robô %s está em (%d, %d) na direção %s.\n", nome, posicaoX, posicaoY, direcao);
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