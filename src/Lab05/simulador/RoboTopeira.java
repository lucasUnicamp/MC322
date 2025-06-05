package simulador;

import simulador.interfaces.Missao;

public class RoboTopeira extends AgenteInteligente{
    public RoboTopeira(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, Missao missao) {
        super(nome, id, posicaoX, posicaoY, ambiente, missao);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Topeira '%s' está %s e na posição (%d, %d) apontado na direção %s\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getDirecao());
    }

    @Override
    public char getRepresentacao() {
        return 'H';
    }

    @Override
    public void executarMissao(Ambiente ambiente) {

    }

    @Override
    public String getNomeTarefa() {
        return "'não tem tarefa'";
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

    public void executarTarefa() {

    }
}
