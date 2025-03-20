import java.util.Scanner;
import Package02.Ambiente;
import Package02.Robo;
import Package02.RoboTerrestre;

public class Main {
    public static void main(String[] args) {
        Ambiente salaTeste = new Ambiente(100, 100);
        Scanner scan = new Scanner(System.in);

        Robo roboAlfa = new Robo("Alfa", "Norte", 50, 50);
        roboAlfa.mover(10, -10);
        roboAlfa.exibirPosicao();
        System.out.printf("O robô está nos limites: %b\n\n", salaTeste.dentroDosLimites(roboAlfa.getX(), roboAlfa.getY()));
        
        System.out.printf("Qual a velocidade do Robô Terrestre? ");
        int userVelo = scan.nextInt();

        RoboTerrestre roboBeta = new RoboTerrestre("Beta", "Sul", 0, 0, userVelo, 15);
        roboBeta.mover(150, 0);
        roboBeta.exibirPosicao();
        System.out.printf("O robô está nos limites: %b\n\n", salaTeste.dentroDosLimites(roboBeta.getX(), roboBeta.getY()));
        
        scan.close();
    }
}
