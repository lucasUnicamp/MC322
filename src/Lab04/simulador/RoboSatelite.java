package simulador;

import java.lang.Math;

import simulador.excecoes.DesceuDemaisException;
import simulador.excecoes.RoboDesligadoException;
import simulador.excecoes.SemObstaculoDestrutivelException;
import simulador.interfaces.Comunicavel;
import simulador.interfaces.Destrutivo;
import simulador.interfaces.Entidade;

public class RoboSatelite extends RoboAereo implements Comunicavel, Destrutivo {
    private int altitudeMinima;
    private int cargaLancamento;
    private double forcaLancamento;
    // Flag para validar os movimentos de sobe e desce, que so podem funcionar caso o robo esteja no ar
    private boolean emOrbita;

    public RoboSatelite(String nome, String id, int posicaoX, int posicaoY, Ambiente ambiente, int altitude, int altitudeMaxima, int altitudeMinima, int cargaLancamento) {
        super(nome, id, posicaoX, posicaoY, ambiente, altitude, altitudeMaxima);
        this.altitudeMinima = altitudeMinima;
        this.cargaLancamento = cargaLancamento;
        // Força definida aleatoriamente
        forcaLancamento = getAltitudeMax() / ((Math.random() + 1) * 5);
        emOrbita = false;
        checarQueda();

        ambiente.getCentral().adicionarComunicavel(this);
    }

    @Override
    public String getDescricao() {
        return String.format("Robô Satélite '%s' está %s e na posição (%d, %d, %d) apontado na direção %s com %d de carga para o lançamento, altitude máxima permitida de %d e mínima para órbita de %d.\n",
        getNome(), getEstado().toString().toLowerCase(), getX(), getY(), getZ(), getDirecao(), getCargaLancamento(), getAltitudeMax(), getAltitudeMin());
    }
    
    @Override
    public char getRepresentacao() {
        return 'S';
    }

    @Override 
    public void subir(int metros) throws RoboDesligadoException{
        // Robô so sobe se está em órbita, pois caso não esteja sua altitude é sempre 0
        if (emOrbita) {
            super.subir(metros);
        }
        else
            System.out.printf("O Robô '%s' não está em órbita para poder subir.\n", getNome());
    }

    // Tecnicamente não precisaria do DesceuDemaisException pois ou ele desce exatamente a altura que está
    // e cai para 0, ou desce uma quantidade menor que ainda o deixa em órbita
    @Override 
    public void descer(int metros) throws RoboDesligadoException, DesceuDemaisException {
        int altitudeNova = getZ() - metros;
        // Robo so desce se esta em órbita, pois caso não esteja sua altitude é sempre 0
        if (emOrbita) {
            // Se puder descer mas descer abaixo do limite de órbita, o robô cai
            if (altitudeNova < getAltitudeMin()) {
                System.out.printf("O Robô '%s' está descendo abaixo da altitude de órbita para tentar pousar.\n", getNome());
                emOrbita = false;
                // Desce exatamente a altitude que está no momento
                super.descer(getZ());
            }
            else
                super.descer(metros);
        }
        else {
            System.out.printf("O Robô '%s' não está em órbita para pode descer.\n", getNome());
        }
    }
    
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException {
        if (estaLigado()) {
            destinatario.receberMensagem(mensagem);
            System.out.println("A mensagem foi enviada com sucesso.");
        } else {
            throw new RoboDesligadoException(getID());
        }
    }
    
    @Override
    public void receberMensagem(String mensagem) throws RoboDesligadoException {
        if (estaLigado()) {
            getAmbiente().getCentral().registrarMensagem(getID(), mensagem);
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    @Override
    public void executarTarefa() {
        int cargaMinima = Math.round(getAltitudeMin() / (int) getForcaLancamento());
        int cargaMaxima = Math.round(getAltitudeMax() / (int) getForcaLancamento());
        System.out.printf("\nA carga de '%s' deve estar entre %d e %d para chegar em órbita.\n", getNome(), cargaMinima, cargaMaxima);
    }

    @Override
    public String getNomeTarefa() {
        return "achar carga para órbita";
    }
    
    // Destroi o obstáciulo apenas se o satélite estiver no ar
    public void destruirObstaculo(int x, int y) throws SemObstaculoDestrutivelException, RoboDesligadoException {
        if (estaLigado()) {
            if (getZ() > 0){
                for (Entidade e : getAmbiente().obstaculos)
                    if ((x >= e.getX() && x < e.getX() + e.getLargura()) &&
                        (y >= e.getY() && y < e.getY() + e.getProfundidade())) {
                            getAmbiente().removerEntidade(e);
                            System.out.printf("O obstáculo em (%d, %d) foi destruído.\n", x, y);
                            return;
                    }
                throw new SemObstaculoDestrutivelException(x, y);
            }
            System.out.println("Precisa estar no ar!");
        }
        throw new RoboDesligadoException(getID());
    }

    public void checarQueda() {
        // Serve para zerar a altitude inicial caso ela não seja suficiente para botar o robô em órbita
        if (getZ() < getAltitudeMin()) {
            System.out.printf("!!! AVISO: Robô '%s' não foi inicializado com altitude mínima para órbita e despencou. !!!\n", getNome());
            setZ(0);
            exibirAltitude();
        }
    }

    public void carregar(int carga) throws RoboDesligadoException {
        if (estaLigado()){
            System.out.println("Robô carregado.");
            cargaLancamento += carga;
            exibirCarga();
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    public void descarregar(int carga) throws RoboDesligadoException {
        if (estaLigado()){
            System.out.println("Robô descarregado.");
            cargaLancamento -= carga;
            exibirCarga();
        } else {
            throw new RoboDesligadoException(getID());
        }
    }

    /**
     * Função encarregada de lancar o robô verticalmente baseado em uma forca carregada previamente.
     * Se o carregamento for exagerado, o robô 'bate no teto' do ambiente e cai;
     * se não for o suficiente, o robô não alcanca a altura mínima de órbita e cai;
     * e se for o bastante, o robô entra em órbita e 'flutua' no espaço, podendo agora subir e descer livremente.
     */
    public void lancamento() throws RoboDesligadoException {
        if (estaLigado()){
            int novaAltitude = Math.round(cargaLancamento * (int) getForcaLancamento());

            if (novaAltitude > getAltitudeMax()) {
                System.out.printf("O Robô '%s' foi lançado alto demais, atingiu o limite e caiu de volta para o chão.\n", getNome());
                setZ(0);
            }
            else if (novaAltitude < getAltitudeMin()) {
                System.out.printf("O Robô '%s' não alcancou sua altura de órbita no lançamento e caiu de volta para o chão.\n", getNome());
                setZ(0);
            }
            else {
                System.out.printf("O Robô '%s' alcançou uma altura de órbita com sucesso em seu lançamento.\n", getNome());
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

    public double getForcaLancamento() {
        return forcaLancamento;
    }
}
