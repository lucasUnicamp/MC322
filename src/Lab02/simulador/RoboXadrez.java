package simulador;

import java.lang.Math;
/**
 * Robo que se movimenta como um cavalo e como um peão de xadrez
 */
public class RoboXadrez extends RoboTerrestre {
    private int tipoMovimento; // 1 - cavalo, 2 - peão 

    public RoboXadrez(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima, int tipoMovimento) {
        super(nome, posicaoX, posicaoY, ambiente, velocidadeMaxima);
        tipoMovimento = 1; // padrão
        setTipoMovimento(tipoMovimento);
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        if(getTipoMovimento() == 1) {
            if ((Math.abs(deltaX) == 2 && Math.abs(deltaY) == 1)
                || (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2)) {
                super.mover(deltaX, deltaY);
                return;
            }
        } else {
            if ((Math.abs(deltaX) <= 2 && Math.abs(deltaY) == 0)
                || (Math.abs(deltaX) == 0 && Math.abs(deltaY) <= 2)) {
                super.mover(deltaX, deltaY);
                return;
            }
        }

        System.out.println("Movimento invalidado pelas regras do xadrez.");
    }

    public void setTipoMovimento(int tipoMovimento) {
        if (tipoMovimento == 0 || tipoMovimento == 1)
            this.tipoMovimento = tipoMovimento;
    }

    public int getTipoMovimento() {
        return tipoMovimento;
    }
}
