package simulador;

public class SensorTemp extends Sensor {

    public SensorTemp(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }

    @Override
    public int monitorar(int posX, int posY) {
        if (!ambiente.dentroDosLimites(posX, posY)) {
            System.out.println("Fora dos limites do ambiente.\n");
            return 1;       // Fora do ambiente
        }
        else if (!dentroDoRaio(posX, posY)) {
            System.out.println("Fora do alcance do sensor.\n");
            return 2;       // Fora do alcance
        }
        else {
            return 1;       // Posicao valida para monitoramento
        }
    }

    /**
     * Exibe a temperatura em um ponto especifico
     * @param posX coordenada x do ponto
     * @param posY coordenada y do ponto
     */
    public void temperaturaPonto(int posX, int posY) {
        if (monitorar(posX, posY) == 1) {
            System.out.printf("Temperatura no ponto (%d, %d) é de %.1f°C.\n", posX, posY, ambiente.temperaturas[posX][posY]);
        }
    }

    /**
     * Exibe a temperatura maxima e sua posicao
     */
    public void temperaturaMax() {

    }
    
}
