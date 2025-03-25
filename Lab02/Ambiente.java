import java.util.ArrayList;

public class Ambiente {
    protected int largura;
    protected int altura;
    public ArrayList<Robo> robosAtivos;
    public boolean [][] obstaculos;

    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.robosAtivos = new ArrayList<>();
        obstaculos = new boolean[largura][altura];
    }

    public void adicionarRobo(Robo r){
        robosAtivos.add(r);
    }

    public void adicionarObstaculos(int [][] obstaculosNovos){
        for (int i = 0; i < obstaculosNovos.length; i++){
            obstaculos[obstaculosNovos[i][0]][obstaculosNovos[i][1]] = true;
        }
    }

    public boolean dentroDosLimites(Robo robo) {
        int x = robo.getX();
        int y = robo.getY();
        return (x >= 0 && x <= altura) && (y >= 0 && y <= largura);
    }

    public boolean dentroDosLimites(RoboAereo robo){
        int x = robo.getX();
        int y = robo.getY();
        int z = robo.getAltitude();
        int altMax = robo.getAltitudeMax();
        return (x >= 0 && x <= altura) && (y >= 0 && y <= largura) && (z >= 0 && z <= altMax);
    }
}
