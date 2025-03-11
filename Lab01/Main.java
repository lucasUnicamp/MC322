package Lab01;

public class Main {
    public static void main(String[] args) {
        Ambiente salaTeste = new Ambiente(100, 100);
        Robo roboAlfa = new Robo("Alfa", 50, 50);
        Robo roboBeta = new Robo("Beta", 0, 0);

        roboAlfa.mover(10, -10);
        System.out.printf("%b\n", salaTeste.dentroDosLimites(roboAlfa.getX(), roboAlfa.getY()));
        roboAlfa.exibirPosicao();

        roboBeta.mover(110, 42);
        System.out.printf("%b\n", salaTeste.dentroDosLimites(roboBeta.getX(), roboBeta.getY()));
        roboBeta.exibirPosicao();
    }
}
