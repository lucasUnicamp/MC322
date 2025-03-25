import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Ambiente salaTeste = new Ambiente(100, 100);    // Cria o ambiente para testes
        Scanner scan = new Scanner(System.in);

        Robo roboNormal = new Robo("Alfa", 0, 0, salaTeste); // Cria o robô genérico
        RoboTerrestre roboTerra = new RoboTerrestre("Beta", 50, 50, 60, salaTeste); // Cria o robô terrestre genérico
        //
        //
        RoboAereo roboAr = new RoboAereo("Gama", 100, 100, 40, 80, salaTeste); // Cria o robô aéreo genérico
        //
        //



        scan.close();
    }
}
