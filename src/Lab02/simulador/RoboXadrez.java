package simulador;

import java.lang.Math;

public class RoboXadrez extends RoboTerrestre {
    private int tipoMovimento;      // Tipo 1 move-se como a peça de xadreza Cavalo e tipo 2 como o Peão 

    public RoboXadrez(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima, int tipoMovimento) {
        super(nome, posicaoX, posicaoY, ambiente, velocidadeMaxima);
        tipoMovimento = 1;      // Tipo padrão
        setTipoMovimento(tipoMovimento);
    }

    @Override
    public void info() {
        System.out.printf("Robô Xadrez '%s' está na posição (%d, %d) apontado na direção %s com velocidade %d, velocidade máxima permitida de %d e com o tipo %d selecionado.\n\n"
        , getNome(), getX(), getY(), getDirecao(), getVelocidade(), getVelocidadeMax(), tipoMovimento);
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        if (getTipoMovimento() == 1) {
            // Cheque de validade do movimento de tipo Cavalo
            if ((Math.abs(deltaX) == 2 && Math.abs(deltaY) == 1) || (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2)) {
                super.mover(deltaX, deltaY);
                return;
            }
        } 
        else {
            // Cheques de validade do movimento de tipo Peão
            switch (getDirecao()) {
                case "Norte":
                    if (deltaX == 0 && (deltaY == 2 || deltaY == 1)){
                        super.mover(deltaX, deltaY);
                        return;
                    }
                    break;
                case "Sul":
                    if (deltaX == 0 && (deltaY == -2 || deltaY == -1)) {
                        super.mover(deltaX, deltaY);
                        return;
                    }
                    break;
                case "Leste":
                    if ((deltaX == 2 || deltaX == 1) && deltaY == 0) {
                        super.mover(deltaX, deltaY);
                        return;
                    }
                    break;
                case "Oeste":
                    if ((deltaX == -2 || deltaX == -1) && deltaY == 0) {
                        super.mover(deltaX, deltaY);
                        return;
                    }
                    break;
                default:
                    break;
            }
        }

        System.out.printf("Movimento invalidado pelas regras do xadrez.\n\n");
    }

    public void setTipoMovimento(int tipoMovimento) {
        if (tipoMovimento == 1 || tipoMovimento == 2)
            this.tipoMovimento = tipoMovimento;
    }

    public int getTipoMovimento() {
        return tipoMovimento;
    }
}
