package simulador;

public class RoboPreguica extends RoboTerrestre {
    private int energiaMaxima;
    private int energia;

    public RoboPreguica(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima, int energiaMaxima) {
        super(nome, posicaoX, posicaoY, ambiente, velocidadeMaxima);
        this.energiaMaxima = energiaMaxima;
    }

    @Override
    public void mover(int deltaX, int deltaY) {
        // Checa se o robô tem energia para mover
        if (energia > 0) {
            super.mover(deltaX, deltaY);
            // Checa se o robô pôde se mover
            if (podeMover(deltaX, deltaY)) {
                energia -= 1;
                exibirEnergia();  
            }
        }
        else
            System.out.printf("'%s' não tem energia o suficiente, precisa descansar.\n\n", getNome());
    }

    public void descansar() {
        if (energia == energiaMaxima)
            System.out.printf("'%s' já está completamente descansado.\n\n", getNome());
        else {
            energia += 1;
            System.out.printf("'%s' descansou um pouco.\n\n", getNome());
            exibirEnergia();
        }
    }

    public void setEnergia(int energiaTotal) {
        if (energiaTotal <= energiaMaxima)
            this.energia = energiaTotal;
        else 
            this.energia = energiaMaxima;
    }

    public void exibirEnergia() {
        System.out.printf("'%s' Energia atual: %d\n\n", getNome(), energia);
    }
}
