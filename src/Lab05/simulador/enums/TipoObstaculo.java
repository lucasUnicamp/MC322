package simulador.enums;

public enum TipoObstaculo {
    TORRE_DE_BABEL(50) ,
    CICLO_BASICO(10) ,
    EUCALIPTO(3) ,
    PEDRA(1) ,
    ESTATUA_DE_ELEFANTE(4) ,
    THE_BEAN(11);
    
    private final int altura;

    TipoObstaculo(int altura) {
        this.altura = altura;
    }

    public int getAltura() {
        return altura;
    }
}
