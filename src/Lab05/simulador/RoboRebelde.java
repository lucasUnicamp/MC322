package simulador;

import simulador.excecoes.RoboDesligadoException;
import java.lang.Math;

public class RoboRebelde extends AgenteInteligente{
    public RoboRebelde(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente) {
        super(nome, id, posicaoX, posicaoY, ambiente);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Rebelde '%s' está %s e na posição (%d, %d) apontado na direção %s.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getDirecao());
    }

    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException{
        int deltaXRebelde = (int) ((Math.random() * 4) - 2);
        int deltaYRebelde = (int) ((Math.random() * 4) - 2);
        x += deltaXRebelde;
        y += deltaYRebelde;
        System.out.printf("%s Quis se mover %d a mais em x e %d a mais em y\n", getID(), deltaXRebelde, deltaYRebelde);
        moverComLog(x - getX(), y - getY());
    }

    @Override
    public char getRepresentacao() {
        return 'D';
    }

    @Override
    public void executarMissao(Ambiente ambiente) throws RoboDesligadoException{
        double boaVontade = Math.random();
        if(boaVontade > 0.3) {
            getMissao().executar(this, ambiente);
        } else {
            System.out.printf("Robô rebelde %s não quer executar a missão\n", getNome());
        }
    }

    @Override 
    public String getNomeMissao() {
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
