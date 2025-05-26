package simulador;

public class Obstaculo implements Entidade {
    private final TipoObstaculo tipoObs;
    private int posicaoX1;
    private int posicaoY1;
    private int posicaoX2;
    private int posicaoY2;
    private Ambiente ambiente;

    public Obstaculo(int posicaoX1, int posicaoY1, int posicaoX2, int posicaoY2, TipoObstaculo tipo, Ambiente ambiente) {
        tipoObs = tipo;
        this.posicaoX1 = posicaoX1;
        this.posicaoY1 = posicaoY1;
        this.posicaoX2 = posicaoX2;
        this.posicaoY2 = posicaoY2;
        ordenaObstaculo(posicaoX1, posicaoY1, posicaoX2, posicaoY2);
    }

    public void ordenaObstaculo(int posX1, int posY1, int posX2, int posY2) {
        if (posX1 > posX2) {
            setPosicaoX2(posX1);
            setPosicaoX1(posX2);
        }
        if (posY1 > posY2) {
            setPosicaoY2(posY1);
            setPosicaoY1(posY2);
        }
    }
    
    public void setPosicaoX1(int pos) {
        posicaoX1 = pos;
        ambiente.moverEntidadeMapa(this, getPosicaoX1(), getPosicaoY1(), 0);
    }
    
    public void setPosicaoX2(int pos) {
        posicaoX2 = pos;
        ambiente.moverEntidadeMapa(this, getPosicaoX1(), getPosicaoY1(), 0);
    }
    
    public void setPosicaoY1(int pos) {
        posicaoY1 = pos;
        ambiente.moverEntidadeMapa(this, getPosicaoX1(), getPosicaoY1(), 0);
    }
    
    public void setPosicaoY2(int pos) {
        posicaoY2 = pos;
        ambiente.moverEntidadeMapa(this, getPosicaoX1(), getPosicaoY1(), 0);
    }
    
    public TipoObstaculo getTipoObstaculo() {
        return tipoObs;
    }


    
    public int getPosicaoX1() {
        return posicaoX1;
    }

    public int getPosicaoX2() {
        return posicaoX2;
    }

    public int getPosicaoY1() {
        return posicaoY1;
    }

    public int getPosicaoY2() {
        return posicaoY2;
    }

    /***************************
     ******** INTERFACE ********
     ***************************/

    // Definiu-se o ponto 'pivot' X do obstaculo como o de menor coordenada;
    // como as coordenadas foram ordenadas antes, esse ponto eh o X1
    public int getX() {
        return posicaoX1;
    }

    // Analogamente, a menor coordenada Y eh a Y1
    public int getY() {
        return posicaoY1;
    }

    // Nao ha obstaculos flutuantes, entao o 'pivot' vai estar sempre no chao (0)
    public int getZ() {
        return 0;
    }

    public int getLargura() {
        return getPosicaoX2() - getPosicaoX1();
    };
    public int getProfundidade(){
        return getPosicaoY2() - getPosicaoY1();
    };
    public int getAltura(){
        return getTipoObstaculo().getAltura();
    };

    public TipoEntidade getTipo() {
        return TipoEntidade.OBSTACULO;
    }

    public String getDescricao() {
        return String.format("Obstaculo do tipo: %s\n", tipoObs.toString());
    }

    public char getRepresentacao() {
        return '#';
    }
}
