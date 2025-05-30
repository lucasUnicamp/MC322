package simulador.interfaces;

public interface Geologo {
    /**
     * Identifica o tipo de obstáculo presente nas adjacências do robô
     */
    void identificarTipoObstaculo();

    /**
     * Identifica as dimensões do obstáculo presente nas adjacências do robô
     */
    void identificarTamanhoObstaculo();
}
