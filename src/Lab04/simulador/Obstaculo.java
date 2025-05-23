package simulador;

public abstract class Obstaculo implements Entidade {
    private int posicaoX1;
    private int posicaoY1;
    private int posicaoX2;
    private int posicaoY2;
    private int posicaoZ;
    private final TipoObstaculo tipo;

    public Obstaculo(int posicaoX1, int posicaoY1, int posicaoX2, int posicaoY2, TipoObstaculo tipo) {
        this.tipo = tipo;
        this.posicaoX1 = posicaoX1;
        this.posicaoY1 = posicaoY1;
        this.posicaoX2 = posicaoX2;
        this.posicaoY2 = posicaoY2;
        this.posicaoZ = posicaoZ;
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
    
    public TipoObstaculo getTipoObstaculo() {
        return tipo;
    }

    public void setPosicaoX1(int pos) {
        posicaoX1 = pos;
    }

    public void setPosicaoX2(int pos) {
        posicaoX2 = pos;
    }

    public void setPosicaoY1(int pos) {
        posicaoY1 = pos;
    }

    public void setPosicaoY2(int pos) {
        posicaoY2 = pos;
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

    public int getX() {
        return posicaoX1;
    }

    public int getY() {
        return posicaoY1;
    }

    public int getZ() {
        return posicaoZ;
    }

    public TipoEntidade getTipo() {
        return TipoEntidade.ROBO;
    }

    public abstract String getDescricao();

    public char getRepresentacao() {
        return 'R';
    }
}
