package simulador;

import java.lang.Math;

import simulador.excecoes.MovimentoXadrezInvalidoException;
import simulador.excecoes.RoboDesligadoException;
import simulador.interfaces.Comunicavel;

public class RoboXadrez extends RoboTerrestre implements Comunicavel {
    private int tipoMovimento;      // Tipo 1 move-se como a peça de xadrez Cavalo e tipo 2 como o Peão 

    public RoboXadrez(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima, int tipoMovimento) {
        super(nome, id, posicaoX, posicaoY, ambiente, velocidadeMaxima);
        tipoMovimento = 1;      // Padrão (Cavalo)
        setTipoMovimento(tipoMovimento);

        ambiente.getCentral().adicionarComunicavel(this);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Xadrez '%s' está %s e na posição (%d, %d) apontado na direção %s com velocidade %d, velocidade máxima permitida de %d e com o tipo %d selecionado.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getDirecao(), getVelocidade(), getVelocidadeMax(), tipoMovimento);
    }

    @Override
    public char getRepresentacao() {
        return 'X';
    }

    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException {
        try {
            if (estaLigado()){
                int deltaX = x - getX();
                int deltaY = y - getY();
                
                switch (getTipoMovimento()) {
                    case 1:
                        moverPeao(x, y, deltaX, deltaY);
                        break;

                    case 2:
                        moverCavalo(x, y, deltaX, deltaY);
                        break;
                    
                    case 3:
                        moverRainha(x, y, deltaX, deltaY);
                        break;
                }             
            } else {
                throw new RoboDesligadoException(getID());
            }
        } catch (MovimentoXadrezInvalidoException erro) {
            System.out.println(erro.getMessage());
        }
    }
    
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException{
        if (estaLigado()) {
            destinatario.receberMensagem(mensagem);
            System.out.println("A mensagem foi enviada com sucesso.");
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    @Override
    public void receberMensagem(String mensagem) throws RoboDesligadoException{
        if (estaLigado()) {
            getAmbiente().getCentral().registrarMensagem(getID(), mensagem);
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    @Override
    public void executarTarefa() {
        setTipoMovimento(3);
        System.out.printf("\nO próximo movimento do Robô '%s' poderá ser feito como o de uma Rainha.\n", getNome());
    }

    @Override
    public String getNomeTarefa() {
        return "'mover como Rainha'";
    }

    public void moverPeao(int x, int y, int deltaX, int deltaY) throws RoboDesligadoException, MovimentoXadrezInvalidoException {
        if ((Math.abs(deltaX) == 2 && Math.abs(deltaY) == 1) || (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2)) {
            super.moverPara(x, y);
            return;
        } else {
            throw new MovimentoXadrezInvalidoException(getID());
        }
    }

    public void moverCavalo(int x, int y, int deltaX, int deltaY) throws RoboDesligadoException, MovimentoXadrezInvalidoException {
        switch (getDirecao()) {
            case "Norte":
                if (deltaX == 0 && (deltaY == 2 || deltaY == 1)){
                    super.moverPara(x, y);
                    return;
                } else {
                    throw new MovimentoXadrezInvalidoException(getID());
                }
            case "Sul":
                if (deltaX == 0 && (deltaY == -2 || deltaY == -1)) {
                    super.moverPara(x, y);
                    return;
                } else {
                    throw new MovimentoXadrezInvalidoException(getID());
                }
            case "Leste":
                if ((deltaX == 2 || deltaX == 1) && deltaY == 0) {
                    super.moverPara(x, y);
                    return;
                } else {
                    throw new MovimentoXadrezInvalidoException(getID());
                }
            case "Oeste":
                if ((deltaX == -2 || deltaX == -1) && deltaY == 0) {
                    super.moverPara(x, y);
                    return;
                } else {
                    throw new MovimentoXadrezInvalidoException(getID());
                }
            default:
                break;
        }
    }

    public void moverRainha(int x, int y, int deltaX, int deltaY) throws RoboDesligadoException, MovimentoXadrezInvalidoException {
        if ((Math.abs(deltaX) == Math.abs(deltaY))
            || (Math.abs(deltaX) != 0 && deltaY == 0)
            || (Math.abs(deltaY) != 0 && deltaX == 0)) {
                super.moverPara(x, y);
                setTipoMovimento(1);
                System.out.println("O Robô voltou a mover-se como um Cavalo.");
            } else {
                throw new MovimentoXadrezInvalidoException(getID());
            }
    }

    public void setTipoMovimento(int tipoMovimento) {
        if (tipoMovimento == 1 || tipoMovimento == 2 || tipoMovimento == 3)
            this.tipoMovimento = tipoMovimento;
    }

    public void alternaTipoMovimento() {
        if (getTipoMovimento() == 1) {
            setTipoMovimento(2);
        } else {
            setTipoMovimento(1);
        }
    }

    public int getTipoMovimento() {
        return tipoMovimento;
    }
}
