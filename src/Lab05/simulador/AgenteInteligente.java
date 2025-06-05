package simulador;

import simulador.interfaces.Missao;
import simulador.excecoes.ColisaoException;
import java.util.Formatter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AgenteInteligente extends Robo {
    private Missao missao;

    public AgenteInteligente(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, Missao missao) {
        super(nome, id, posicaoX, posicaoY, ambiente);
        this.missao = missao;
    }

    public abstract void executarMissao(Ambiente ambiente);

    public Missao getMissao(){
        return missao;
    }

    public void moverComLog(int deltaX, int deltaY) {
        int i = 0, j = 0;
        try {
            Formatter output = new Formatter(new FileWriter("logs/log.txt", true));
            try {
                if (deltaX > 0) {
                    for ( ; i <= deltaX; i++) {
                        if (getAmbiente().estaOcupado(getX() + i, getY(), getZ())) {
                            setX(getX() + i - 1);
                            throw new ColisaoException(getID(), getX() + 1, getY(), getZ());
                        }
                        else if (!getAmbiente().dentroDosLimites(getX() + i, getY())) {
                            System.out.println("O robô não tem autorização para sair do ambiente.");
                            break;
                        }
                        output.format("Robô está em (%d, %d)\n", getX() + i, getY());
                    }
                    setX(getX() + i - 1);
                    output.format("Robô está em (%d, %d)\n", getX(), getY());
                }
                else if (deltaX < 0) {
                    for ( ; i <= -deltaX; i++) {
                        if (getAmbiente().estaOcupado(getX() - i, getY(), getZ())) {
                            setX(getX() - i + 1);
                            throw new ColisaoException(getID(), getX() - 1, getY(), getZ());
                        }
                        else if (!getAmbiente().dentroDosLimites(getX() - i, getY())) {
                            System.out.println("O robô não tem autorização para sair do ambiente.");
                            break;
                        }
                        output.format("Robô está em (%d, %d)\n", getX() - i, getY());
                    }
                    setX(getX() - i + 1);
                    output.format("Robô está em (%d, %d)\n", getX(), getY());
                }

                if (deltaY > 0) {
                    for ( ; j <= deltaY; j++) {
                        if (getAmbiente().estaOcupado(getX(), getY() + j, getZ())) {
                            setY(getY() + j - 1);
                            throw new ColisaoException(getID(), getX(), getY() + 1, getZ());
                        }
                        else if (!getAmbiente().dentroDosLimites(getX(), getY() + j)) {
                            System.out.println("O robô não tem autorização para sair do ambiente.");
                            break;
                        }
                        output.format("Robô está em (%d, %d)\n", getX(), getY() + i);
                    }
                    setY(getY() + j - 1);
                    output.format("Robô está em (%d, %d)\n", getX(), getY());
                }
                else if (deltaY < 0) {
                    for ( ; j <= -deltaY; j++) {
                        if (getAmbiente().estaOcupado(getX(), getY() - j, getZ())) {
                            setY(getY() - j + 1);
                            throw new ColisaoException(getID(), getX(), getY() - 1, getZ());
                        }
                        else if (!getAmbiente().dentroDosLimites(getX(), getY() - j)) {
                            System.out.println("O robô não tem autorização para sair do ambiente.");
                            break;
                        }
                        output.format("Robô está em (%d, %d)\n", getX(), getY() - i);
                    }
                    setY(getY() - j + 1);
                    output.format("Robô está em (%d, %d)\n", getX(), getY());
                }
            } catch (ColisaoException erro) {
                output.format("%s\n", erro.getMessage());

            } finally {
                output.format("\n\n");
                output.close();
            }
        } catch (IOException erro) {
            
            System.err.println(erro.getMessage());
        }
    }
}