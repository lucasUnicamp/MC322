import java.util.ArrayList;

public class Ambiente {
    private int largura;
    private int comprimento;
    private ArrayList<Robo> robosAtivos;

    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.comprimento = altura;
        this.robosAtivos = new ArrayList<>();
    }

    public void adicionarRobo(Robo r){
        robosAtivos.add(r);
    }

    public boolean dentroDosLimites(Robo robo) {
        int x = robo.getX();
        int y = robo.getY();
        return x >= 0 && x <= largura && y >= 0 && y <= comprimento;
    }

    public boolean dentroDosLimites(RoboAereo robo){
        int x = robo.getX();
        int y = robo.getY();
        int z = robo.getAltitude();
        int altMax = robo.getAltMax();
        return (x >= 0 && x <= largura) && (y >= 0 && y <= comprimento) && (z >= 0 && z <= altMax);
    }
}
