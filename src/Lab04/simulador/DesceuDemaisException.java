package simulador;

public class DesceuDemaisException extends Exception {
    public DesceuDemaisException(String id) {
        super(String.format("A coordenada z do Robo %s ficou abaixo de 0.", id));
    }
}
