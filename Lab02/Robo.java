public class Robo {
    protected String nome;
    protected String direcao;
    protected int posicaoX;
    protected int posicaoY;

    public Robo(String nome, int posicaoX, int posicaoY, Ambiente ambiente) {
        this.nome = nome;
        this.direcao = "Norte";
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        ambiente.adicionarRobo(this);
        imprimeCriacao();
    }

    // Usada para imprimir as informações úteis e checar se estão corretas após a criação do robô
    protected void imprimeCriacao(){
        System.out.printf("Robô padrão '%s' criado na posição (%d, %d) apontado na direção %s.\n"
        , nome, posicaoX, posicaoY, direcao);
    }

    public void mover(int deltaX, int deltaY) {
        // Checa se o robô não está entrando nos quadrantes negativos
        if((posicaoX + deltaX >= 0) && (posicaoY + deltaY >= 0)){
            posicaoX += deltaX;
            posicaoY += deltaY;
            System.out.printf("Movendo robô '%s' em %d no eixo x e em %d no y\n", nome, deltaX, deltaY);
        } else {
            System.out.println("Impossível ir para coordenadas negativas.");
        }
    }

    public void exibirPosicao() {
        System.out.printf("O robô %s está em (%d, %d) na direção %s.\n", nome, posicaoX, posicaoY, direcao);
    }

    public String pegaDirecao() {
        return direcao;
    }

    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }
}