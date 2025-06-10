package simulador;

import simulador.excecoes.RoboDesligadoException;

public class RoboToupeira extends AgenteInteligente {
    public RoboToupeira(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente) {
        super(nome, id, posicaoX, posicaoY, ambiente);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Topeira '%s' está %s e na posição (%d, %d) apontado na direção %s.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getDirecao());
    }

    @Override
    public void moverPara(int x, int y)  throws RoboDesligadoException{
        int deltaX = x - getX();
        int deltaY = y - getY();
        int z = getZ();
        System.out.printf("Tentando mover o Robô '%s' para a posição (%d, %d).\n", getNome(), x, y);
        
        // Coloca o robô numa posição muito alta acima de tudo no ambiente para que não 
        // colida com obstáculos e depois recoloca-o na altura original
        setZ(1000);
        super.moverComLog(deltaX, deltaY);
        setZ(z);
        atualizaSensores();
        System.out.printf("O Robô '%s' terminou o movimento na posição (%d, %d).\n", getNome(), getX(), getY());
        
        if (getAmbiente().estaOcupado(getX(), getY(), getZ()))
            System.out.println("E ficou dentro de um obstáculo!");
    }

    @Override
    public char getRepresentacao() {
        return 'U';
    }

    @Override
    public void executarMissao(Ambiente ambiente) throws RoboDesligadoException {
        if (getMissao() == null) 
            System.out.println("\nNão há missões disponível para esse robô.");
        else
            getMissao().executar(this, ambiente);
    }

    @Override 
    public String getNomeMissao() {
        if (getMissao() == null)
            return "inexistente";
        else
            return getMissao().getNome();
    }

    public void atualizaSensores() {
        if (sensores != null) {
            // Atualiza a posicao do robo em cada sensor que o robo possui 
            for (Sensor sensor : sensores) {
                sensor.setX(getX());
                sensor.setY(getY());
            }
        }
    }

    @Override
    public void executarTarefa() {
        System.out.println("\nNão há tarefas designadas para esse Robô.");
    }

    @Override
    public String getNomeTarefa() {
        return "inexistente";
    }
}
