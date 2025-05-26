package simulador;

public class RoboSatelite extends RoboAereo implements Comunicavel, Destrutivo {
    private int altitudeMinima;
    private int cargaLancamento;
    // Flag para validar os movimentos de sobe e desce, que so podem funcionar caso o robo esteja no ar
    private boolean emOrbita;

    public RoboSatelite(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int altitudeMinima, int cargaLancamento) {
        super(nome, id, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        this.altitudeMinima = altitudeMinima;
        this.cargaLancamento = cargaLancamento;
        emOrbita = false;
        checarQueda();

        ambiente.getCentral().adicionarComunicavel(this);
    }

    @Override
    public String getDescricao() {
        return String.format("Robo Satelite '%s' esta %s e na posicao (%d, %d, %d) apontado na direcao %s com %d de carga para o lancamento, altitude maxima permitida de %d e minima para orbita de %d.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getZ(), getDirecao(), getCargaLancamento(), getAltitudeMax(), getAltitudeMin());
    }
    
    @Override
    public char getRepresentacao() {
        return 'S';
    }

    @Override 
    public void subir(int metros) throws RoboDesligadoException{
        // Robo so sobe se esta em orbita, pois caso nao esteja sua altitude eh sempre 0
        if (emOrbita) {
            super.subir(metros);
        }
        else
            System.out.printf("O Robo '%s' nao esta em orbita para poder subir.\n", getNome());
    }

    // Tecnicamente nao precisaria do DesceuDemaisException pois ou ele desce exatamente a altura que esta
    // e cai para 0, ou desce uma quantidade menor que ainda o deixa em orbita
    @Override 
    public void descer(int metros) throws RoboDesligadoException, DesceuDemaisException {
        int altitudeNova = getZ() - metros;
        // Robo so desce se esta em orbita, pois caso nao esteja sua altitude eh sempre 0
        if (emOrbita) {
            // Se puder descer mas descer abaixo do limite de orbita, o robo cai
            if (altitudeNova < getAltitudeMin()) {
                System.out.printf("O Robo '%s' esta descendo abaixo da altitude de orbita para tentar pousar.\n", getNome());
                emOrbita = false;
                // Desce exatamente a altitude que esta no momento
                super.descer(getZ());
            }
            else
                super.descer(metros);
        }
        else {
            System.out.printf("O Robo '%s' nao esta em orbita para pode descer.\n", getNome());
        }
    }
    
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException {
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

    public void receberMensagem(String mensagem) throws RoboDesligadoException {
        if (estaLigado()) {
            getAmbiente().getCentral().registrarMensagem(getID(), mensagem);
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    // destroi o obstáciulo apenas se o satélite estiver no ar
    public void destruirObstaculo(int x, int y) throws SemObstaculoDestrutivel{
        if(getZ() > 0){
            for(Entidade e : getAmbiente().obstaculos)
                if((x >= e.getX() && x < e.getX() + e.getLargura()) &&
                    (y >= e.getY() && y < e.getY() + e.getProfundidade())){
                        getAmbiente().removerEntidade(e);
                        System.out.println("Obstáculo destruído");
                        return;
                }
            throw new SemObstaculoDestrutivel(x, y);
        }
        System.out.println("Precisa estar no ar!");
    }

    public void checarQueda() {
        // Serve para zerar a altitude inicial caso ela nao seja suficiente para botar o robo em orbita
        if (getZ() < getAltitudeMin()) {
            System.out.printf("!!! AVISO: Robo '%s' nao foi inicializado com altitude minima para orbita e despencou. !!!\n", getNome());
            setZ(0);
            exibirAltitude();
        }
    }

    public void carregar(int carga) throws RoboDesligadoException{
        if (estaLigado()){
            System.out.println("Robo carregado.");
            cargaLancamento += carga;
            exibirCarga();
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    public void descarregar(int carga) throws RoboDesligadoException{
        if (estaLigado()){
            System.out.println("Robo descarregado.");
            cargaLancamento -= carga;
            exibirCarga();
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    /**
     * Funcao encarregada de lancar o robo verticalmente baseado em uma forca carregada previamente.
     * Se o carregamento for exagerado, o robo 'bate no teto' do ambiente e cai;
     * se nao for o suficiente, o robo nao alcanca a altura minima de orbita e cai;
     * e se for o bastante, o robo entra em orbita e 'flutua' no espaco, podendo agora subir e descer livremente.
     */
    public void lancamento() throws RoboDesligadoException{
        if (estaLigado()){
            // Funcao de forca definida arbitrariamente
            float forcaLancamento = getAltitudeMax() / 10;
            int novaAltitude = Math.round(cargaLancamento * forcaLancamento);

            if (novaAltitude > getAltitudeMax()) {
                System.out.printf("O Robo '%s' foi lancado alto demais, atingiu o limite e caiu de volta para o chao.\n", getNome());
                setZ(0);
            }
            else if (novaAltitude < getAltitudeMin()) {
                System.out.printf("O Robo '%s' nao alcancou sua altura de orbita no lancamento e caiu de volta para o chao.\n", getNome());
                setZ(0);
            }
            else {
                System.out.printf("O Robo '%s' alcancou uma altura de orbita com sucesso em seu lancamento.\n", getNome());
                emOrbita = true;
                setZ(novaAltitude);
            }
            setCargaLancamento(0);
            exibirAltitude();
            descarregar(cargaLancamento);
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    public void exibirCarga() {
        System.out.printf("'%s' - Carga atual: %d\n", getNome(), cargaLancamento);
    }

    public void setAltitudeMinima(int metros) {
        altitudeMinima = metros <= getAltitudeMax() ? metros : getAltitudeMax();
    }

    public void setCargaLancamento(int carga) {
        cargaLancamento = carga;
    }

    public int getAltitudeMin() {
        return altitudeMinima;
    }

    public int getCargaLancamento() {
        return cargaLancamento;
    }
}
