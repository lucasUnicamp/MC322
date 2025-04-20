package simulador;

public class SensorTemp extends Sensor {

    public SensorTemp(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }

    /**
     * Exibe a temperatura em um ponto especifico
     * @param posX coordenada x do ponto
     * @param posY coordenada y do ponto
     */
    public void temperaturaPonto(int posX, int posY) {
        if (monitorar(posX, posY) == 1) {
            System.out.printf("Nao eh possivel detectar a temperatura dentro de um obstaculo.\n");
        }
        else if (monitorar(posX, posY) == 0) {
            System.out.printf("Temperatura no ponto (%d, %d) é de %.1f°C.\n", posX, posY, ambiente.temperaturas[posX][posY]);
        }
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
                // Checa se a coordenada esta dentro do sensor e fora de um obstaculo
                if (dentroDoRaio(i, j) && !ambiente.ehObstaculo(i, j)) {
                    if (ambiente.temperaturas[i][j] > tempMax) {
                        tempMax = ambiente.temperaturas[i][j];
                        posX = i;
                        posY = j;
                    }
                }
            }
        }
        System.out.printf("A temperatura maxima encontrada de %.1f°C e esta na posicao (%d, %d).\n", tempMax, posX, posY);
    }
}
