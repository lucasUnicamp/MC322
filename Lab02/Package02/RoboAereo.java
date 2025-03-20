package Package02;

public class RoboAereo extends Robo {
    private int altitude;
    private int altitudeMaxima;
    
    public RoboAereo(String nome, String direcao, int posicaoX, int posicaoY, int altitude, int altitudeMaxima) {
        super(nome, direcao, posicaoX, posicaoY);
        this.altitude = altitude;
        this.altitudeMaxima = altitudeMaxima;
    }

    @Override public void exibirPosicao() {
        System.out.printf("O robô %s está em (%d, %d) na direção %s e %d acima do solo.\n", nome, posicaoX, posicaoY, direcao, altitude);
    }

    public void subir(int metros) {
        if(altitude + metros > altitudeMaxima)
            System.out.printf("%s ultrapassaria a altitude máxima permitida.\n", nome);
        else
            altitude += metros;

        System.out.printf("Altitude atual: %d\n", altitude);
    }

    public void descer(int metros) {
        if(altitude - metros < 0) {
            System.out.printf("%s espatifou-se no chão.\n", nome);
            altitude = 0;
        }
        else
            altitude -= metros;

        System.out.printf("Altitude atual: %d\n", altitude);
    }
}
