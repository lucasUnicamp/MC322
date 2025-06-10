package simulador.agentesinteligentes;

import simulador.interfaces.Missao;
import simulador.ambiente.Ambiente;
import simulador.excecoes.ColisaoException;
import simulador.excecoes.RoboDesligadoException;
import simulador.robos.Robo;

import java.util.Formatter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AgenteInteligente extends Robo {
    private Missao missao;

    public AgenteInteligente(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente) {
        super(nome, id, posicaoX, posicaoY, ambiente);
    }

    public abstract void executarMissao(Ambiente ambiente) throws RoboDesligadoException;

    public Missao getMissao(){
        return missao;
    }

    public void setMissao(Missao m) {
        missao = m;
    }
    
    // Classe abstrata para obrigar agentes inteligentes a reeimplementar moverPara realizando o log
    @Override
    public abstract void moverPara(int x, int y) throws RoboDesligadoException;

    public void moverComLog(int deltaX, int deltaY) throws RoboDesligadoException {
        if (estaLigado()) {
            int i = 0, j = 0;
            char[][] matrizAmbiente = getAmbiente().inicializarMatrizAmbiente();

            try {
                Formatter output = new Formatter(new FileWriter("logs/log.txt", true));
                output.format("Início do log de movimentação: %s indo de (%d, %d) para (%d, %d)\n", getID(), getX(), getY(), getX() + deltaX, getY() + deltaY);
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
                            // Para que a posição inicial seja representada diferentemente
                            if (i == 0)
                                matrizAmbiente[getX() + i][getY()] = '+';
                            else
                                matrizAmbiente[getX() + i][getY()] = '=';
                        }
                        setX(getX() + i - 1);
                        output.format("Robô está em (%d, %d)\n", getX(), getY());
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
                            output.format("Robô está em (%d, %d)\n", getX() - i, getY());
                            if (i == 0)
                                matrizAmbiente[getX() - i][getY()] = '+';
                            else
                                matrizAmbiente[getX() - i][getY()] = '=';
                        }
                        setX(getX() - i + 1);
                        output.format("Robô está em (%d, %d)\n", getX(), getY());
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
                            output.format("Robô está em (%d, %d)\n", getX(), getY() + j);
                            // Para que só imprima o caractere representativo do início da movimentação caso
                            // não tenha tido movimentação horizontal anterior
                            if (deltaX == 0 && j == 0)
                                matrizAmbiente[getX()][getY() + j] = '+';
                            else
                            matrizAmbiente[getX()][getY() + j] = '=';
                        }
                        setY(getY() + j - 1);
                        output.format("Robô está em (%d, %d)\n", getX(), getY());
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
                            output.format("Robô está em (%d, %d)\n", getX(), getY() - j);
                            if (deltaX == 0 && j == 0)
                                matrizAmbiente[getX()][getY() - j] = '+';
                            else
                            matrizAmbiente[getX()][getY() - j] = '=';
                        }
                        setY(getY() - j + 1);
                        output.format("Robô está em (%d, %d)\n", getX(), getY());
                        matrizAmbiente[getX()][getY()] = '-';
                    }
                } catch (ColisaoException erro) {
                    output.format("%s\n", erro.getMessage());
                    System.err.println(erro.getMessage());
                } finally {
                    output.format("\nMapa do caminho feito pelo robô:\n");
                    // Loop para efetivamente imprimir a matrizAmbiente
                    for (int e = getAmbiente().getLargura(); e >= 0; e--) {
                        for (int f = 0; f <= getAmbiente().getProfundidade(); f++)
                            output.format("%c ", matrizAmbiente[f][e]);
                        output.format("\n");
                    }
                    output.format("                + : início do caminho  = : caminho do robô  - : fim do caminho\n");
                    output.format("\nFim da movimentação: %s chegou em (%d, %d)\n\n", getID(), getX(), getY());
                    output.flush();
                    output.close();
                }
            } catch (IOException erro) {
                System.err.println(erro.getMessage());
            }
        } else {
            throw new RoboDesligadoException(getID());
        }
    }
}