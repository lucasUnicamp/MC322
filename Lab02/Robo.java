public class Robo {
    protected String nome;
    protected String direcao;
    protected int posicaoX;
    protected int posicaoY;

    public Robo(String nome, String direcao, int posicaoX, int posicaoY) {
        this.nome = nome;
        this.direcao = direcao;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }

    public void mover(int deltaX, int deltaY) {
        if((posicaoX + deltaX >= 0) && (posicaoY + deltaY >= 0)){
            posicaoX += deltaX;
            posicaoY += deltaY;
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