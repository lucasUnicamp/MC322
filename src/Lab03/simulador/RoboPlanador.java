package simulador;

import java.lang.Math;
/**
 * Robo que perde altitude a medida que se move
 */
public class RoboPlanador extends RoboAereo {
    private int tamanhoAsa;     // Quanto maior a asa, por mais tempo consegue planar e também consegue subir mais

    public RoboPlanador(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int tamanhoAsa){
        super(nome, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        setTamanhoAsa(tamanhoAsa);
    }

    @Override
    public void info() {
        System.out.printf("Robo Planador'%s' está na posicao (%d, %d, %d) apontado na direcao %s com altitude maxima permitida de %d e asa de tamanho %d.\n\n"
        , getNome(), getX(),getY(), getAltitude(), getDirecao(), getAltitudeMax(), tamanhoAsa);
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        if (getAmbiente().dentroDosLimites(getX() + deltaX, getY() + deltaY)){
            int deslocamento = Math.abs(deltaY) + Math.abs(deltaX);
            super.descer(((120 - tamanhoAsa)*deslocamento)/100);        // Cai lentamente quando deslocado
            super.mover(deltaX, deltaY);
        } 
        else 
            System.out.printf("'%s' não tem permissao para sair do ambiente.\n\n", getNome());
    }

    @Override
    public void subir(int metros) {
        if (metros <= tamanhoAsa)     // Quanto maior a asa mais pode subir
            super.subir(metros);
        else
            System.out.printf("'%s' tem as asas muito curtas.\n\n", getNome());
    }

    public void setTamanhoAsa(int tamanhoAsa) {
        if (tamanhoAsa <= 100)        // Tamanho maximo da asa de 100
            this.tamanhoAsa = tamanhoAsa;
        else
            this.tamanhoAsa = 100;
    }
}
