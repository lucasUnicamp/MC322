package simulador;

public class SensorTemp extends Sensor {

    public SensorTemp(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }

    public int monitorar(int posX, int posY) {        
        if (!ambiente.dentroDosLimites(posX, posY))
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
        System.out.printf("A temperatura no ponto (%d, %d) eh %.1f.\n", ambiente.temperaturas[posX][posY]);
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
                    if (ambiente.temperaturas[i][j] > tempMax) {
                        tempMax = ambiente.temperaturas[i][j];
                        posX = i;
                        posY = j;
                    }
                }
            }
        }
        System.out.printf("A temperatura maxima encontrada num raio de %d eh de %.1f°C e esta na posicao (%d, %d).\n", getRaio(), tempMax, posX, posY);
    }
}
