package simulador;

public class RoboPreguica extends RoboTerrestre {
    private int energia;
    private int energiaMaxima;

    public RoboPreguica(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima, int energiaMaxima) {
        super(nome, id, posicaoX, posicaoY, ambiente, velocidadeMaxima);
        setEnergiaMax(energiaMaxima);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Preguica '%s' está %s e na posição (%d, %d) apontado na direção %s com velocidade %d, velocidade maxima permitida de %d e tem %d de energia de um maximo de %d.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getDirecao(), getVelocidade(), getVelocidadeMax(), energia, energiaMaxima);
    }

    @Override
    public char getRepresentacao() {
        return 'G';
    }

    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException {
        // Checa se o Robô tem energia para mover
        if (energia > 0) {
            super.moverPara(x, y);
            energia -= 1;
            exibirEnergia();  
        }
        else
            System.out.printf("'%s' não tem energia o suficiente, precisa descansar.\n", getNome());
    }

    public void descansar() {
        if (energia == energiaMaxima)
            System.out.printf("'%s' já está completamente descansado.\n", getNome());
        else {
            energia += 1;
            System.out.printf("'%s' descansou um pouco.\n", getNome());
            exibirEnergia();
        }
    }

    public void exibirEnergia() {
        System.out.printf("'%s' - Energia atual: %d\n", getNome(), energia);
    }

    public void setEnergia(int energiaTotal) {
        if (energiaTotal <= energiaMaxima)
            this.energia = energiaTotal;
        else 
            this.energia = energiaMaxima;
    }

    public void setEnergiaMax(int energiaMax) {
        if (energiaMax >= 1)
            this.energiaMaxima = energiaMax;
        else 
            this.energiaMaxima = 1;
    }

    public int getEnergia() {
        return energia;
    }

    public int getEnergiaMax() {
        return energiaMaxima;
    }
}
