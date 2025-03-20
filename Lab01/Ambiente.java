public class Ambiente {
    private int largura;
    private int altura;

    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
    }

    public boolean dentroDosLimites(int x, int y) {
        return x > 0 && x < largura && y > 0 && y < altura;
    }
}
