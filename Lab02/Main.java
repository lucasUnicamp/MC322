import java.util.Scanner;
import Package02.*;

public class Main {
    public static void main(String[] args) {
        Ambiente salaTeste = new Ambiente(100, 100);
        Scanner scan = new Scanner(System.in);

        System.out.print("\n");
        Robo roboAlfa = new Robo("Alfa", "Norte", 50, 50);

        roboAlfa.mover(10, -10);
        roboAlfa.exibirPosicao();
        System.out.printf("O robô está nos limites: %b\n", salaTeste.dentroDosLimites(roboAlfa.pegaX(), roboAlfa.pegaY()));
        
        System.out.printf("\n");
        RoboTerrestre roboBeta = new RoboTerrestre("Beta", "Sul", 0, 0, 15);
        
        System.out.printf("Qual a velocidade do Robô Terrestre? ");
        int userVelo = scan.nextInt();
        roboBeta.velocidade(userVelo);
        roboBeta.mover(150, 0);
        roboBeta.exibirPosicao();
        System.out.printf("O robô está nos limites: %b\n", salaTeste.dentroDosLimites(roboBeta.pegaX(), roboBeta.pegaY()));

        System.out.printf("\n");
        RoboAereo roboGama = new RoboAereo("Gama", "Leste", 30, 70, 20, 50);
        
        roboGama.exibirPosicao();
        System.out.printf("Quanto o Robô Aereo vai subir? ");
        int userSubir = scan.nextInt();
        roboGama.subir(userSubir);
        System.out.printf("Quanto ele vai descer? ");
        int userDescer = scan.nextInt();
        roboGama.descer(userDescer);

        scan.close();
    }
}
