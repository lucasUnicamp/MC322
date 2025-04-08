package simulador;

public enum TipoObstaculo {
    PAREDE (3 , true ) ,
    ARVORE (5 , true ) ,
    PREDIO (10 , true ) ,
    BURACO (0 , true );

    private final int alturaPadrao ;
    private final boolean bloqueiaPassagem ;

    TipoObstaculo (int alturaPadrao , boolean bloqueiaPassagem ) {
        this.alturaPadrao = alturaPadrao ;
        this.bloqueiaPassagem = bloqueiaPassagem ;
    }
}
