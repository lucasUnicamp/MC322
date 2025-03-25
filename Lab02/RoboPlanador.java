public class RoboPlanador extends RoboAereo{
    private int velocidadeVento;

    public RoboPlanador(String nome, int posicaoX, int posicaoY, int altitude, int altitudeMaxima, Ambiente ambiente){
        super(nome, posicaoX, posicaoY, altitude, altitudeMaxima, ambiente);
    }
}
