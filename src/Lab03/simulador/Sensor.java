package simulador;

public class Sensor {
    private double raio;

    public Sensor(double raio) {
        this.raio = raio;
    }  

    public int monitorar(Robo robo) {
        return 1;
    }

    public double getRaio() {
        return raio;
    }

}

