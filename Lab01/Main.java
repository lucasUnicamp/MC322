public class Main {
    public static void main(String[] args) {
        Ambiente salaTeste = new Ambiente(100, 100);
        Robo roboAlfa = new Robo("Alfa", 50, 50);
        Robo roboBeta = new Robo("Beta", 0, 0);

        roboAlfa.mover(10, -10);
        roboAlfa.exibirPosicao();
        System.out.printf("O robô está nos limites: %b\n", salaTeste.dentroDosLimites(roboAlfa.getX(), roboAlfa.getY()));
        

        roboBeta.mover(120, 0);
        roboBeta.exibirPosicao();
        System.out.printf("O robô está nos limites: %b\n\n", salaTeste.dentroDosLimites(roboBeta.getX(), roboBeta.getY()));
        
    }
}
