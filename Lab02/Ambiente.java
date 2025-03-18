import java.util.ArrayList;

public class Ambiente {
    private int largura;
    private int comprimento;
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

    public boolean dentroDosLimites(int x, int y, int z) {
        return x >= 0 && x <= largura && y >= 0 && y <= comprimento && z >= 0 && z <= altura;
    }
}
