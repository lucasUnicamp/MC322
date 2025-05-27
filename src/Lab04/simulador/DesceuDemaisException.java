package simulador;

public class DesceuDemaisException extends Exception {
    public DesceuDemaisException(String id) {
        super(String.format("Robô %s nao pode descer tudo isso, sua coordenada z ficaria abaixo de 0.", id));
    }
}
