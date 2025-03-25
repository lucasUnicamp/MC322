import java.util.ArrayList;

public class Ambiente {
    private int largura;
    private int comprimento;
    public ArrayList<Robo> robosAtivos;
    public int [][] obstaculos;

    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.comprimento = altura;
        this.robosAtivos = new ArrayList<>();
        obstaculos = new int[largura][altura];
    }

    public void adicionarRobo(Robo r){
        robosAtivos.add(r);
    }

    public boolean dentroDosLimites(Robo robo) {
        int x = robo.getX();
        int y = robo.getY();
        return (x >= 0 && x <= largura) && (y >= 0 && y <= comprimento);
    }

    public boolean dentroDosLimites(RoboAereo robo){
        int x = robo.getX();
        int y = robo.getY();
        int z = robo.getAltitude();
        int altMax = robo.getAltitudeMax();
        return (x >= 0 && x <= largura) && (y >= 0 && y <= comprimento) && (z >= 0 && z <= altMax);
    }
}
