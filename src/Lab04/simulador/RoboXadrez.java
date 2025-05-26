package simulador;

import java.lang.Math;

public class RoboXadrez extends RoboTerrestre implements Comunicavel {
    private int tipoMovimento;      // Tipo 1 move-se como a peça de xadrez Cavalo e tipo 2 como o Peao 

    public RoboXadrez(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int velocidadeMaxima, int tipoMovimento) {
        super(nome, id, posicaoX, posicaoY, ambiente, velocidadeMaxima);
        tipoMovimento = 1;      // Padrao (Cavalo)
        setTipoMovimento(tipoMovimento);

        ambiente.getCentral().adicionarComunicavel(this);
    }

    @Override
    public String getDescricao() {
        return String.format("Robo Xadrez '%s' esta %s e na posicao (%d, %d) apontado na direcao %s com velocidade %d, velocidade maxima permitida de %d e com o tipo %d selecionado.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getDirecao(), getVelocidade(), getVelocidadeMax(), tipoMovimento);
    }

    @Override
    public char getRepresentacao() {
        return 'X';
    }

    @Override
    public void moverPara(int x, int y) throws RoboDesligadoException {
        if (estaLigado()){
            int deltaX = x - getX();
            int deltaY = y - getY();
            if (getTipoMovimento() == 1) {
                // Cheques de validade do movimento de tipo Cavalo
                if ((Math.abs(deltaX) == 2 && Math.abs(deltaY) == 1) || (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2)) {
                    super.moverPara(x, y);
                    return;
                }
            } 
            else {
                // Cheques de validade do movimento de tipo Peao
                switch (getDirecao()) {
                    case "Norte":
                        if (deltaX == 0 && (deltaY == 2 || deltaY == 1)){
                            super.moverPara(deltaX, deltaY);
                            return;
                        }
                        break;
                    case "Sul":
                        if (deltaX == 0 && (deltaY == -2 || deltaY == -1)) {
                            super.moverPara(deltaX, deltaY);
                            return;
                        }
                        break;
                    case "Leste":
                        if ((deltaX == 2 || deltaX == 1) && deltaY == 0) {
                            super.moverPara(deltaX, deltaY);
                            return;
                        }
                        break;
                    case "Oeste":
                        if ((deltaX == -2 || deltaX == -1) && deltaY == 0) {
                            super.moverPara(deltaX, deltaY);
                            return;
                        }
                        break;
                    default:
                        break;
                }
            }

            System.out.printf("Movimento invalidado pelas regras do xadrez.\n");
        } else {
            throw new RoboDesligadoException(getID());
        }
    }
    
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException{
        if (estaLigado()) {
            try {
                destinatario.receberMensagem(mensagem);
                System.out.println("A mensagem foi enviada com sucesso.");
            } catch (RoboDesligadoException erro) {
                System.out.println("A mensagem nao foi enviada, robo destinatario desligado.");
            }
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

    public void setTipoMovimento(int tipoMovimento) {
        if (tipoMovimento == 1 || tipoMovimento == 2)
            this.tipoMovimento = tipoMovimento;
    }

    public void alternaTipoMovimento() {
        if(getTipoMovimento() == 1) {
            setTipoMovimento(2);
        } else {
            setTipoMovimento(1);
        }
    }

    public int getTipoMovimento() {
        return tipoMovimento;
    }
}
