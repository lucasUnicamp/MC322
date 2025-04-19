package simulador;

import java.util.ArrayList;

public class Robo {
    private String nome;
    private String direcao;
    private int posicaoX;
    private int posicaoY;
    private Ambiente ambiente;
    public ArrayList<Sensor> sensores;

    public Robo(String nome, int posicaoX, int posicaoY, Ambiente ambiente) {
        setNome(nome);
        setDirecao("Norte");
        setX(posicaoX);
        setY(posicaoY);
        setAmbiente(ambiente);
        ambiente.adicionarRobo(this);       // Adiciona o robo no ambiente logo que é criado
        
        sensores = new ArrayList<>();
        adicionarSensor(1, 5);
        atualizaSensores();

        System.out.printf("Robo '%s' criado\n", nome);
        checarPosicao(posicaoX, posicaoY);
    }

    public void info() {
        System.out.printf("Robo '%s' esta na posicao (%d, %d) apontado na direcao %s.\n\n", getNome(), getX(), getY(), direcao);
    }

    public void checarPosicao(int posX, int posY) {
        if (ambiente.ehObstaculo(posX, posY)) {
            System.out.printf("AVISO: Robo '%s' foi inicializado dentro de um obstaculo. Nao faca isso.\n", getNome());
            // Colocar ele numa posicao valida
        }
    }

    /**
     * Tenta mover o robo totalmente na horizontal e depois totalmente na vertical ate que chegue em seu destino,
     * bata em um obstaculo ou chegue no limite do ambiente, conforme explicado no README
     * @param deltaX inteiro do quanto deve se mover na horizontal
     * @param deltaY inteiro do quanto deve se mover na vertical
     */
    public void mover(int deltaX, int deltaY) {
        int i = 0;
        int j = 0;
        System.out.printf("Tentando mover o robo '%s' em %d no eixo x e em %d no y.\n", nome, deltaX, deltaY);

        // Primeiro move o robo totalmente na horizontal
        // Caso deltaX positivo, anda na direcao Leste
        if (deltaX >= 0) {
            for ( ; i < deltaX; i++) {
                // Checa se a posicao em que vai andar eh um obstaculo
                if (getAmbiente().ehObstaculo(posicaoX + i, posicaoY)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento horizontal de '%s'.\n", getX() + i, getY(), nome);
                    i -= 1;
                    break;
                }
                // Checa se a posicao em que vai andar esta fora dos limites do ambiente
                else if (!getAmbiente().dentroDosLimites(posicaoX + i, posicaoY)) {
                    System.out.printf("O robo nao tem autorizacao para sair do ambiente.\n\n");
                    i -= 1;
                    break;
                }
            }
            // Atualiza posicao X do robo baseado em quanto conseguiu andar
            posicaoX += i;
        }
        // Caso deltaX negativo, anda na direcao Oeste.
        else if (deltaX < 0) {
            for ( ; i < -deltaX; i++) {
                if (getAmbiente().ehObstaculo(posicaoX - i, posicaoY)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento horizontal de '%s'.\n", getX() - i, getY(), nome);
                    i += 1;
                    break;
                }
                else if (!getAmbiente().dentroDosLimites(posicaoX - i, posicaoY)) {
                    System.out.printf("O robo nao tem autorizacao para sair do ambiente.\n\n");
                    i += 1;
                    break;
                }
            }
            posicaoX -= i;
        }
        // Depois move o robo totalmente na vertical
        // Caso deltaY positivo, anda na direcao Norte. Aqui checa o valor de i antes pois i so eh diferente de deltaX 
        // se o robo ja bateu em algum obstaculo, assim nao tem porque continuar checando na vertical
        if (deltaY >= 0 && i == Math.abs(deltaX)) {
            for ( ; j < deltaY; j++) {
                // Checa se a posicao em que vai andar eh um obstaculo
                if (getAmbiente().ehObstaculo(posicaoX, posicaoY + j)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento vertical de '%s'.\n", getX(), getY() + j, nome);
                    j -= 1;
                    break;
                }
                // Checa se a posicao em que vai andar esta fora dos limites do ambiente
                else if (!getAmbiente().dentroDosLimites(posicaoX, posicaoY + j)) {
                    System.out.printf("O robo nao tem autorizacao para sair do ambiente.\n\n");
                    j -= 1;
                    break;
                }
            }
            // Atualiza posicao X do robo baseado em quanto conseguiu andar
            posicaoY += j;
        }
        // Caso deltaY negativo, anda na direcao Sul
        else if (deltaY < 0 && i == Math.abs(deltaX)) {
            for ( ; j < -deltaY; j++) {
                if (getAmbiente().ehObstaculo(posicaoX, posicaoY - j)) {
                    System.out.printf("Ha um obstaculo em (%d, %d) impedindo o movimento vertical de '%s'.\n", getX(), getY() - j, nome);
                    j += 1;
                    break;
                }
                else if (!getAmbiente().dentroDosLimites(posicaoX, posicaoY - j)) {
                    System.out.printf("O robo nao tem autorizacao para sair do ambiente.\n\n");
                    j += 1;
                    break;
                }
            }
            posicaoY -= j;
        }
        atualizaSensores();
        System.out.printf("O Robo '%s' terminou o movimento na posicao (%d, %d).\n\n", nome, posicaoX, posicaoY);
    }

    // /**
    //  * Checa se ha algum obstaculo impedindo a movimentacao definida, sendo essa movimentacao explicada no README 
    //  * @param deltaX inteiro do quanto deve se mover na horizontal
    //  * @param deltaY inteiro do quanto deve se mover na vertical
    //  * @return true ou false dependendo se ha ou nao obstaculos
    //  */
    // public Boolean checarObstaculoCaminho(int deltaX, int deltaY) {
    //     boolean caminhoCima = true, caminhoBaixo = true;

    //     // Checa se a linha reta da componente horizontal do movimento, partindo da posicao atual do robo 
    //     // ou partindo da posicao do robo após andar toda sua componente vertical, contem algum obstaculo;
    //     // O loop para se ambos os caminhos tiverem um obstaculo;
    //     if (deltaX > 0) {
    //         for (int a = 0; (caminhoBaixo || caminhoCima) && a < deltaX; a++) {
    //             if (ambiente.obstaculosMatriz[posicaoX + a][posicaoY]) 
    //                 caminhoCima = false;

    //             if (ambiente.obstaculosMatriz[posicaoX + a][posicaoY + deltaY]) 
    //                 caminhoBaixo = false;
    //         }
    //     }
    //     else {
    //         for (int b = 0; (caminhoBaixo || caminhoCima) && b > deltaX; b--) {
    //             if (ambiente.obstaculosMatriz[posicaoX + b][posicaoY]) 
    //                 caminhoCima = false;

    //             if (ambiente.obstaculosMatriz[posicaoX + b][posicaoY + deltaY]) 
    //                 caminhoBaixo = false;
    //         }
    //     }

    //     // Checa se a linha reta da componente vertical do movimento, partindo da posicao atual do robo 
    //     // ou partindo da posicao do robo após andar toda sua componente horizontal, contem algum obstaculo;
    //     if (deltaY > 0) {
    //         for (int c = 0; (caminhoBaixo || caminhoCima) && c < deltaY; c++) {
    //             if (ambiente.obstaculosMatriz[posicaoX][posicaoY + c])
    //                 caminhoBaixo = false;
                
    //             if (ambiente.obstaculosMatriz[posicaoX + deltaX][posicaoY + c])
    //                 caminhoCima = false;
    //         }
    //     }
    //     else {
    //         for (int d = 0; (caminhoBaixo || caminhoCima) && d > deltaY; d--) {
    //             if (ambiente.obstaculosMatriz[posicaoX][posicaoY + d])
    //                 caminhoBaixo = false;
                
    //             if (ambiente.obstaculosMatriz[posicaoX + deltaX][posicaoY + d])
    //                 caminhoCima = false;
    //         }
    //     }

    //     return caminhoBaixo || caminhoCima;
    // }

    // public boolean identificarObstaculo() {
    //     boolean temObstaculo = false;

    //     // Checa se ha algum obstaculo nas 4 adjacentes ao robo
    //     if(!(getAmbiente().dentroDosLimites(posicaoX + 1, posicaoY) && getAmbiente().dentroDosLimites(posicaoX - 1, posicaoY)
    //         || getAmbiente().dentroDosLimites(posicaoX, posicaoY + 1) || getAmbiente().dentroDosLimites(posicaoX, posicaoY - 1)))
    //         temObstaculo = true;
    //     else if (ambiente.obstaculosMatriz[posicaoX + 1][posicaoY] || ambiente.obstaculosMatriz[posicaoX - 1][posicaoY]
    //     || ambiente.obstaculosMatriz[posicaoX][posicaoY + 1] || ambiente.obstaculosMatriz[posicaoX][posicaoY - 1])
    //         temObstaculo = true;
        
    //     return temObstaculo;
    // }

    // public boolean podeMover(int deltaX, int deltaY) {
    //     return getAmbiente().dentroDosLimites(getX() + deltaX, getY() + deltaY) && checarObstaculoCaminho(deltaX, deltaY);
    // }

    public void exibirPosicao() {
        System.out.printf("O robo '%s' esta em (%d, %d) na direcao %s.\n\n", nome, posicaoX, posicaoY, direcao);
    }

    public void adicionarSensor(int tipoSensor, int raio) {
        if (tipoSensor == 1) {
            sensores.add(new Sensor(raio, ambiente));
        }
        else if (tipoSensor == 2) {
            sensores.add(new Sensor(raio, ambiente));
        }
    }

    public void atualizaSensores(){
        for(Sensor sensor:sensores) {
            sensor.setX(posicaoX);
            sensor.setY(posicaoY);
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDirecao(String drc) {
        if (drc == "Norte" || drc == "Sul" || drc == "Leste" || drc == "Oeste")
            direcao = drc;
        else
            direcao = "Norte";
    }

    protected void setX(int x) {
        posicaoX = x;
    }

    protected void setY(int y) {
        posicaoY = y;
    }

    protected void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public String getNome() {
        return nome;
    }
    
    public int getX() {
        return posicaoX;
    }

    public int getY() {
        return posicaoY;
    }

    public String getDirecao() {
        return direcao;
    }

    public Ambiente getAmbiente(){
        return ambiente;
    }
}