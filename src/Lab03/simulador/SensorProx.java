package simulador;
import java.lang.Math;

public class SensorProx extends Sensor{
    SensorProx(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }

    public double monitorar() {
        double distancia;
        double distanciaAtual;
        if (getAmbiente().obstaculos.size() == 0 ||
            (getAmbiente().obstaculos.size() == 1 && calculaDistancia(getAmbiente().obstaculos.get(0)) > getRaio())) {
            System.out.println("Não há obstáculos próximos");
            return -1; // nenhum obatáculo no alcance
        } else {
            distancia = calculaDistancia(getAmbiente().obstaculos.get(0));
            for (Obstaculo obstaculo:getAmbiente().obstaculos) {
                distanciaAtual = calculaDistancia(obstaculo);
                if (distanciaAtual > distancia)
                    distancia = distanciaAtual;           
            }
        }
        System.out.printf("Há um obtáculo há %f de distância", distancia);
        return distancia;
    }

    private double calculaDistancia(Obstaculo obstaculo) {
        double deltaX = (double) Math.max(Math.max(obstaculo.getPosicaoX1() - getX(), 0), getX() - obstaculo.getPosicaoX2());
        double deltaY = (double) Math.max(Math.max(obstaculo.getPosicaoY1() - getY(), 0), getY() - obstaculo.getPosicaoY2());
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }
}
