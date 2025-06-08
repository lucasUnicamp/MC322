package simulador;

import simulador.interfaces.Missao;
import simulador.excecoes.ColisaoException;
import simulador.excecoes.RoboDesligadoException;

import java.util.Formatter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AgenteInteligente extends Robo {
    private Missao missao;

    public AgenteInteligente(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, Missao missao) {
        super(nome, id, posicaoX, posicaoY, ambiente);
        this.missao = missao;
    }

    public abstract void executarMissao(Ambiente ambiente) throws RoboDesligadoException;

    public Missao getMissao(){
        return missao;
    }

    public void setMissao(Missao m) {
        missao = m;
    }
    
    public void moverComLog(int deltaX, int deltaY) {
        int i = 0, j = 0;
        char[][] matrizAmbiente = getAmbiente().inicializarMatrizAmbiente();

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
                        output.format("Robô está em (%d, %d).\n", getX() + i, getY());
                        // Para que a posição inicial seja representada diferentemente
                        if (i == 0)
                            matrizAmbiente[getX() + i][getY()] = '+';
                        else
                            matrizAmbiente[getX() + i][getY()] = '=';
                    }
                    setX(getX() + i - 1);
                    output.format("Robô está em (%d, %d).\n", getX(), getY());
                    // Para que, caso o robô ainda vá se mover na vertical, não imprima o caractere que
                    // representa o final da movimentação
                    if (deltaY == 0)
                        matrizAmbiente[getX()][getY()] = '-';
                    else 
                        matrizAmbiente[getX()][getY()] = '=';
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
                        output.format("Robô está em (%d, %d).\n", getX() - i, getY());
                        if (i == 0)
                            matrizAmbiente[getX() - i][getY()] = '+';
                        else
                            matrizAmbiente[getX() - i][getY()] = '=';
                    }
                    setX(getX() - i + 1);
                    output.format("Robô está em (%d, %d).\n", getX(), getY());
                    if (deltaY == 0)
                        matrizAmbiente[getX()][getY()] = '-';
                    else 
                        matrizAmbiente[getX()][getY()] = '=';
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
                        output.format("Robô está em (%d, %d).\n", getX(), getY() + j);
                        // Para que só imprima o caractere representativo do início da movimentação caso
                        // não tenha tido movimentação horizontal anterior
                        if (deltaX == 0 && j == 0)
                            matrizAmbiente[getX()][getY() + j] = '+';
                        else
                           matrizAmbiente[getX()][getY() + j] = '=';
                    }
                    setY(getY() + j - 1);
                    output.format("Robô está em (%d, %d).\n", getX(), getY());
                    matrizAmbiente[getX()][getY()] = '-';
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
                        output.format("Robô está em (%d, %d).\n", getX(), getY() - j);
                        if (deltaX == 0 && j == 0)
                            matrizAmbiente[getX()][getY() - j] = '+';
                        else
                           matrizAmbiente[getX()][getY() - j] = '=';
                    }
                    setY(getY() - j + 1);
                    output.format("Robô está em (%d, %d).\n", getX(), getY());
                    matrizAmbiente[getX()][getY()] = '-';
                }
            } catch (ColisaoException erro) {
                output.format("%s\n", erro.getMessage());

            } finally {
                System.out.println("");
                output.format("\nMapa com o caminho feito pelo robô:\n");
                // Loop para efetivamente imprimir a matrizAmbiente
                for (int e = getAmbiente().getLargura(); e >= 0; e--) {
                    for (int f = 0; f <= getAmbiente().getProfundidade(); f++)
                        output.format("%c ", matrizAmbiente[f][e]);
                    output.format("\n");
                }
                output.format("                + : início do caminho  = : caminho do robô  - : fim do caminho\n\n\n");
                output.flush();
                output.close();
            }
        } catch (IOException erro) {
            System.err.println(erro.getMessage());
        }
    }
}