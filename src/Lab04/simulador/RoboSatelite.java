package simulador;

public class RoboSatelite extends RoboAereo {
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
    }

    @Override
    public String getDescricao() {
        return String.format("Robo Satelite '%s' esta na posicao (%d, %d, %d) apontado na direcao %s com %d de carga para o lancamento, altitude maxima permitida de %d e minima para orbita de %d.\n"
        , getNome(), getX(), getY(), getAltitude(), getDirecao(), getCargaLancamento(), getAltitudeMax(), getAltitudeMin());
    }
    
    @Override
    public char getRepresentacao() {
        return 'S';
    }

    @Override 
    public void subir(int metros) {
        // Robo so sobe se esta em orbita, pois caso nao esteja sua altitude eh sempre 0
        if (emOrbita) {
            super.subir(metros);
        }
        else
            System.out.printf("O Robo '%s' nao esta em orbita para poder subir.\n", getNome());
    }

    @Override 
    public void descer(int metros) {
        int altitudeNova = getAltitude() - metros;
        // Robo so desce se esta em orbita, pois caso nao esteja sua altitude eh sempre 0
        if (emOrbita) {
            // Se puder descer mas descer abaixo do limite de orbita, o robo cai
            if (altitudeNova < getAltitudeMin()) {
                System.out.printf("O Robo '%s' esta descendo abaixo da altitude de orbita para tentar pousar.\n", getNome());
                emOrbita = false;
                super.descer(getAltitude());
            }
            else
                super.descer(metros);
        }
        else {
            System.out.printf("O Robo '%s' nao esta em orbita para pode descer.\n", getNome());
        }
    }

    public void checarQueda() {
        // Serve para zerar a altitude inicial caso ela nao seja suficiente para botar o robo em orbita
        if (getAltitude() < getAltitudeMin()) {
            System.out.printf("!!! AVISO: Robo '%s' nao foi inicializado com altitude minima para orbita e despencou. !!!\n", getNome());
            setAltitude(0);
            exibirAltitude();
        }
    }

    public void carregar(int carga) {
        System.out.println("Robo carregado.");
        cargaLancamento += carga;
        exibirCarga();
    }

    public void descarregar(int carga) {
        System.out.println("Robo descarregado.");
        cargaLancamento -= carga;
        exibirCarga();
    }

    /**
     * Funcao encarregada de lancar o robo verticalmente baseado em uma forca carregada previamente.
     * Se o carregamento for exagerado, o robo 'bate no teto' do ambiente e cai;
     * se nao for o suficiente, o robo nao alcanca a altura minima de orbita e cai;
     * e se for o bastante, o robo entra em orbita e 'flutua' no espaco, podendo agora subir e descer livremente.
     */
    public void lancamento() {
        // Funcao de forca definida arbitrariamente
        float forcaLancamento = getAltitudeMax() / 10;
        int novaAltitude = Math.round(cargaLancamento * forcaLancamento);

        if (novaAltitude > getAltitudeMax()) {
            System.out.printf("O Robo '%s' foi lancado alto demais, atingiu o limite e caiu de volta para o chao.\n", getNome());
            setAltitude(0);
        }
        else if (novaAltitude < getAltitudeMin()) {
            System.out.printf("O Robo '%s' nao alcancou sua altura de orbita no lancamento e caiu de volta para o chao.\n", getNome());
            setAltitude(0);
        }
        else {
            System.out.printf("O Robo '%s' alcancou uma altura de orbita com sucesso em seu lancamento.\n", getNome());
            emOrbita = true;
            setAltitude(novaAltitude);
        }
        setCargaLancamento(0);
        exibirAltitude();
        descarregar(cargaLancamento);
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
