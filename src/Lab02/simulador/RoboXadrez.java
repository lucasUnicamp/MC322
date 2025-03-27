package simulador;
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

    public void setTipoMovimento(int tipoMovimento) {
        if (tipoMovimento == 0 || tipoMovimento == 1)
            this.tipoMovimento = tipoMovimento;
    }
}
