package simulador.interfaces;

import simulador.enums.TipoEntidade;

public interface Entidade {
    int getX();
    int getY();
    int getZ();
    int getLargura();
    int getProfundidade();
    int getAltura();
    TipoEntidade getTipo();
    String getDescricao();
    char getRepresentacao();
}

