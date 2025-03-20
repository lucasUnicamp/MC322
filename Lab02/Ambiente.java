import java.util.ArrayList;

public class Ambiente {
    private int largura;
    private int altura;
    private ArrayList<Robo> robosAtivos;

    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.robosAtivos = new ArrayList<>();
    }

    public void adicionarRobo(Robo r){
        robosAtivos.add(r);
    }

    public boolean dentroDosLimites(int x, int y) {
        return x >= 0 && x <= largura && y >= 0 && y <= altura;
    }
}
