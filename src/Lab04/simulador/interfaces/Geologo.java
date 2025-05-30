package simulador.interfaces;

public interface Geologo {
    /**
     * Identifica o tipo de obstáculo presente nas coordenadas dadas
     * @param x coordenada horizontal que se quer identificar
     * @param y coordenada vertical que se quer identificar
     */
    void identificarTipoObstaculo(int x, int y);

    /**
     * Identifica as dimensões do obstáculo presente nas coordenadas dadas
     * @param x coordenada horizontal que se quer identificar
     * @param y coordenada vertical que se quer identificar
     */
    void identificarTamanhoObstaculo(int x, int y);
}
