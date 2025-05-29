package simulador;

public class SensorTemperatura extends Sensor {

    public SensorTemperatura(double raio, Ambiente ambiente) {
        super(raio, ambiente);
    }
    /**
     * Nesse sensor, o monitorar checa e exibe a temperatura do ponto especificado 
     * e também a maior temperatura dentro de seu raio de alcance
     */
    public int monitorar(int posX, int posY) {        
        if (!getAmbiente().dentroDosLimites(posX, posY))
            return 2;       // Fora do ambiente
        else if (!dentroDoRaio(posX, posY)) 
            return 3;       // Fora do alcance
        else {
            exibirTempPonto(posX, posY);
            temperaturaMax();
            return 1;       // Sucesso no monitoramento
        }
    }

    public void exibirTempPonto(int posX, int posY) {
        System.out.printf("A temperatura no ponto (%d, %d) é %.1f°C.\n", posX, posY, getAmbiente().temperaturas[posX][posY]);
    }

    /**
     * Exibe a temperatura máxima dentro do círculo de raio definido e sua posição. No momento ela vai desconsiderar
     * temperaturas máxima iguais, exibindo a primeira instância dessa temperatura como máxima absoluta
     */
    public void temperaturaMax() {
        int[] info = new int[2];
        double tempMax = RetornaTemperaturaMax(info);
        System.out.printf("A temperatura máxima encontrada num raio de %.2f é de %.1f°C e está na posicao (%d, %d).\n", getRaio(), tempMax, info[0], info[1]);
    }

    /**
     * Retorna a temperatura máxima dentro do círculo de raio definido e sua posição. No momento ela vai desconsiderar
     * temperaturas máxima iguais, exibindo a primeira instância dessa temperatura como máxima absoluta. retorno é da forma
     * [posição X, posição Y].
     */
    
    public double RetornaTemperaturaMax(int[] retornoCoordenadas) {
        int limiteNorte = (int)(getY() + getRaio());
        int limiteSul = (int)(getY() - getRaio());
        int limiteLeste = (int)(getX() + getRaio());
        int limiteOeste = (int)(getX() - getRaio());
        int posX = 0;
        int posY = 0;
        double tempMax = -100.0;

        // Checa as coordenadas dentro do quadrado circunscrito no círculo de raio definido
        for (int i = limiteOeste; i <= limiteLeste; i++) {
            for (int j = limiteSul; j <= limiteNorte; j++) {
                // Checa se a coordenada esta dentro do sensor
                if (dentroDoRaio(i, j) && getAmbiente().dentroDosLimites(i, j)) {
                    if (getAmbiente().temperaturas[i][j] > tempMax) {
                        tempMax = getAmbiente().temperaturas[i][j];
                        posX = i;
                        posY = j;
                    }
                }
            }
        }
        retornoCoordenadas[0] = posX;
        retornoCoordenadas[1] = posY;
        return tempMax;
    }

    public String nomeDoSensor() {
        return "Sensor de Temperatura";
    } 
}
