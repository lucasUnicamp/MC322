package simulador.agentesinteligentes;

import simulador.ambiente.Ambiente;
import simulador.excecoes.RoboDesligadoException;
import simulador.sensores.Sensor;

import java.lang.Math;

public class RoboRebelde extends AgenteInteligente {
    public RoboRebelde(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente) {
        super(nome, id, posicaoX, posicaoY, ambiente);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Rebelde '%s' está %s e na posição (%d, %d) apontado na direção %s.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getDirecao());
    }

    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException {
        int deltaX = x - getX();
        int deltaY = y - getY();
        int deltaXRebelde = (int) ((Math.random() * 10));
        int deltaYRebelde = (int) ((Math.random() * 10));
        deltaX += deltaX < 0 ? -deltaXRebelde : deltaXRebelde;
        deltaY += deltaY < 0 ? -deltaYRebelde : deltaYRebelde;

        System.out.printf("Tentando mover o Robô '%s' para a posição (%d, %d).\n", getNome(), x, y);
        System.out.printf("'%s' quis se mover %d a mais em x e %d a mais em y.\n", getID(), deltaXRebelde, deltaYRebelde);
        super.moverComLog(deltaX, deltaY);
        atualizaSensores();
        System.out.printf("O Robô '%s' terminou o movimento na posição (%d, %d).\n", getNome(), getX(), getY());
    }

    @Override
    public char getRepresentacao() {
        return 'D';
    }

    @Override
    public void executarMissao(Ambiente ambiente) throws RoboDesligadoException {
        double boaVontade = Math.random();
        if (getMissao() == null)
            System.out.println("\nNão há missões disponível para esse robô.");
        else {
            if (boaVontade > 0.3) {
                getMissao().executar(this, ambiente);
            } else 
                System.out.printf("Robô rebelde %s não quer executar a missão.\n", getNome());
        }
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
