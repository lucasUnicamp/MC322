package simulador;
import java.lang.Math;

public class RoboPlanador extends RoboAereo {
    private int tamanhoAsa;     // Quanto maior a asa mais consegue planar

    public RoboPlanador(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int tamanhoAsa){
        super(nome, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        setTamanhoAsa(tamanhoAsa);
    }

    @Override
    public void subir(int metros) {
        if (metros <= tamanhoAsa)
            super.subir(metros);
        else
            System.out.print("Asa muito curta");
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        int deslocamento = Math.abs(deltaY) + Math.abs(deltaX);
        super.mover(deltaX, deltaY);
        super.descer(((120 - tamanhoAsa)*deslocamento)/100);        // Cai lentamente quando deslocado
    }

    public void setTamanhoAsa(int tamanhoAsa) {
        if (tamanhoAsa <= 100)
            this.tamanhoAsa = tamanhoAsa;
        else
            this.tamanhoAsa = 100;
    }
}
