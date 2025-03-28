package simulador;

import java.lang.Math;
/**
 * Robo que se movimenta como um cavalo e como um peão de xadrez
 */
public class RoboXadrez extends RoboTerrestre {
    private int tipoMovimento; // 1 - cavalo, 2 - peão 

    public RoboXadrez(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima, int tipoMovimento) {
        super(nome, posicaoX, posicaoY, ambiente, velocidadeMaxima);
        tipoMovimento = 1; // padrão (cavalo)
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
            switch (getDirecao()) {
                case "Norte":
                    if(deltaX == 0 && (deltaY == 2 || deltaY == 1)){
                        super.mover(deltaX, deltaY);
                        return;
                    }
                    break;
                case "Sul":
                    if(deltaX == 0 && (deltaY == -2 || deltaY == -1)) {
                        super.mover(deltaX, deltaY);
                        return;
                    }
                    break;
                case "Leste":
                    if((deltaX == 2 || deltaX == 1) && deltaY == 0) {
                        super.mover(deltaX, deltaY);
                        return;
                    }
                    break;
                case "Oeste":
                    if((deltaX == -2 || deltaX == -1) && deltaY == 0) {
                        super.mover(deltaX, deltaY);
                        return;
                    }
                    break;
                default:
                    break;
            }
        }

        System.out.println("Movimento invalidado pelas regras do xadrez.");
    }

    public void setTipoMovimento(int tipoMovimento) {
        if (tipoMovimento == 1 || tipoMovimento == 2)
            this.tipoMovimento = tipoMovimento;
    }

    public int getTipoMovimento() {
        return tipoMovimento;
    }
}
