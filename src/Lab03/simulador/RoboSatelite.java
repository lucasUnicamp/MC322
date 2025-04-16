package simulador;

public class RoboSatelite extends RoboAereo {
    private int altitudeMinima;
    private int cargaLancamento;
    private boolean emOrbita;

    public RoboSatelite(String nome, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int altitudeMinima, int cargaLancamento) {
        super(nome, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        this.altitudeMinima = altitudeMinima;
        this.cargaLancamento = cargaLancamento;
        emOrbita = false;
        checarQueda();
    }

    @Override 
    public void subir(int metros) {
        if (emOrbita) {
            super.subir(metros);
        }
        else
            System.out.printf("O Robo '%s' nao esta em orbita para poder subir.\n\n", getNome());
    }

    @Override 
    public void descer(int metros) {
        int altitudeNova = getAltitude() - metros;
        // Robo so desce se esta em orbita, pois caso nao esteja sua altitude eh sempre 0
        if (emOrbita) {
            if (altitudeNova < getAltitudeMin()) {
                System.out.printf("O Robo '%s' desceu abaixo da altitude de orbita; preparando-se para o pouso.\n");
                emOrbita = false;
                setAltitude(0);
            }
            else
                super.descer(metros);
        }
    }

    @Override
    public void info() {
        System.out.printf("Robo Satelite '%s' esta na posicao (%d, %d, %d) apontado na direcao %s com %d de carga para o lancamento, altitude maxima permitida de %d e minima para orbita de %d.\n\n"
        , getNome(), getX(), getY(), getAltitude(), getDirecao(), getCargaLancamento(), getAltitudeMax(), getAltitudeMin());
    }

    public void checarQueda() {
        if (getAltitude() < getAltitudeMin()) {
            System.out.printf("Altitude minima para orbita nao alcancada, '%s' despencou.\n", getNome());
            setAltitude(0);
            exibirAltitude();
        }
    }

    public void carregar(int carga) {
        System.out.printf("Robo carregado.\n");
        cargaLancamento += carga;
        exibirCarga();
    }

    public void descarregar(int carga) {
        System.out.printf("Robo descarregado.\n");
        cargaLancamento -= carga;
        exibirCarga();
    }

    public void lancamento() {
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
        System.out.printf("'%s' Carga atual: %d\n\n", getNome(), cargaLancamento);
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

/* 
    // O angulo definido para o escaner corresponde ao 'campo de visao' do Robo, que procura obstaculos abaixo dele e no interior
    // do circulo de raio dependente da altura e do angulo
    public void escanear() {
        double distancia;
        boolean temObstaculo = false;

        // Percorre a lista de coordenadas dos obstaculos e calcula se essas estao no interior do circulo de visao
        for (int i = 0; i < getAmbiente().obstaculos.size(); i++) {
            // Usa formula da distancia entre dois pontos no plano
            distancia = Math.sqrt(Math.pow(getAmbiente().obstaculosLista[i][0] - getX(), 2) + Math.pow(getAmbiente().obstaculosLista[i][1] - getY(), 2));
            if (distancia <= raioArea) {
                System.out.printf("Obstaculo escaneado em (%d, %d).\n", getAmbiente().obstaculosLista[i][0], getAmbiente().obstaculosLista[i][1]);
                temObstaculo = true;
            }
        }
        if (!temObstaculo)
            System.out.printf("Nao ha obstaculos na area procurada.");
        System.out.printf("\n");
    }

    public void exibirRaio() {
        System.out.printf("O raio de alcance do escaner nessa altitude %d eh de %.1f.\n\n", getAltitude(), raioArea);
    }

    public void setAngulo(double angulo) {
        // Limite arbitrario do angulo do Robo
        if (angulo < 90)
            this.angulo = angulo;
        else
            this.angulo = 90;
        setRaio();
    }

    public void setRaio() {
        // Encontrado por trigonometria: tan = raioArea/altitude
        raioArea = super.getAltitude() * Math.tan((Math.toRadians(angulo / 2)));
    }

    public double getAngulo() {
        return angulo;
    }

    public double getRaio() {
        return raioArea;
    }
 */
}
