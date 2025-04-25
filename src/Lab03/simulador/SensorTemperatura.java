package simulador;

public class SensorTemperatura extends Sensor {

    public SensorTemperatura(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }
    /**
     * Nesse sensor, o monitorar checa e exibe a temperatura do ponto especificado 
     * e tambem a maior temperatura dentro de seu raio de alcance
     */
    public int monitorar(int posX, int posY) {        
        if (!getAmbiente().dentroDosLimites(posX, posY))
            return 2;       // Fora do ambiente
        else if (!dentroDoRaio(posX, posY)) 
            return 3;       // Fora do alcance
        else {
            temperaturaPonto(posX, posY);
            temperaturaMax();
            return 1;       // Sucesso no monitoramento
        }
    }

    public void temperaturaPonto(int posX, int posY) {
        System.out.printf("A temperatura no ponto (%d, %d) eh %.1f.\n", getAmbiente().temperaturas[posX][posY]);
    }

    /**
     * Exibe a temperatura maxima dentro do circulo de raio definido e sua posicao. No momento ela vai desconsiderar
     * temperaturas maximas iguais, exibindo a primeira instancia dessa temperatura como maxima absoluta
     */
    public void temperaturaMax() {
        int limiteNorte = (int)(getY() + getRaio());
        int limiteSul = (int)(getY() - getRaio());
        int limiteLeste = (int)(getX() + getRaio());
        int limiteOeste = (int)(getX() - getRaio());
        int posX = 0;
        int posY = 0;
        double tempMax = -100.0;

        // Checa as coordenadas dentro do quadrado circunscrito no circulo de raio definido
        for (int i = limiteOeste; i <= limiteLeste; i++) {
            for (int j = limiteSul; j <= limiteNorte; j++) {
                // Checa se a coordenada esta dentro do sensor
                if (dentroDoRaio(i, j)) {
                    if (getAmbiente().temperaturas[i][j] > tempMax) {
                        tempMax = getAmbiente().temperaturas[i][j];
                        posX = i;
                        posY = j;
                    }
                }
            }
        }
        System.out.printf("A temperatura maxima encontrada num raio de %d eh de %.1f°C e esta na posicao (%d, %d).\n", getRaio(), tempMax, posX, posY);
    }

    public String nomeDoSensor() {
        return "Sensor de Temperatura";
    } 
}
