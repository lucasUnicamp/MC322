# **MC322**

Esse é o *Repositório Oficial*™ do grupo 8 para os laboratórios de MC322, onde se encontram os arquivos de cada tarefa e esse documento com explicações mais detalhadas sobre as ideias importantes do código.<br/>

**Autoria de:**
- 276617 &emsp; Lucas Henrique Bertanha     
- 281289 &emsp; Leonardo Ferreira

## **EXECUÇÃO**
Dentro da pasta MC322, usar os comandos para compilação e execução:<br/>
- `javac -d bin src/Lab03/cliente/*.java src/Lab03/simulador/*.java`
- `java -cp bin cliente.Main`

## **EXPLICAÇÕES**
> ### **Lab 03**
### Movimentação do Robô<br/>
Em razão da implementação de sensores, houve uma pequena mudança na movimentação do robô base. Agora, dado um `deltaX` e um `deltaY`, o robô irá sempre tentar percorrer todo o `deltaX` primeiro e depois o `deltaY`, parando de se mover:
- caso chegue ao seu destino.
- caso colida com algum obstáculo.
- caso chegue em um dos limites do ambiente.

Nota-se que agora o robô genérico faz os cheques de viabilidade de movimento *durante* essa movimentação. 

### Tipos de Robô<br/>
Novamente em razão dos sensores, um dos robôs foi alterado:
- *Robô Aéreo Satélite*
    - pode ser carregado com uma carga que o permite ser lançado verticalmente. Se tiver carga suficiente, entra em órbita e pode se movimentar como um robô aéreo normalmente nessa região.

Os outros tipos de robôs permanecem inalterados.

 ---
> ### **Lab 02**
### Movimentação do Robô<br/>
Consideramos que o robô pode tomar somente dois caminhos dado um `deltaX` e um `deltaY`, sendo esses:
- mover-se `deltaX` totalmente no eixo X primeiro e depois `deltaY` totalmente no eixo Y.
```
# # # # []
# # # # []
[][][][][]
```
- mover-se `deltaY` totalmente no eixo Y primeiro e depois `deltaX` totalmente no eixo X.
```
[][][][][]
[] # # # #
[] # # # #
```
<sup>Em que [ ] representa o caminho que o robô irá fazer.</sup>

### Tipos de Robô<br/>
Os 4 robôs adicionais que criamos são:
- *Robô Terrestre Xadrex*
    - move-se ou como a peça Cavalo[^1], que anda duas posições em uma direção e uma na outra, formando um L; ou um Peão[^2], que deve andar uma ou duas posições para alguma direção.
- *Robô Terrestre Preguiça*
    - move-se normalmente mas descarrega sua energia a cada movimento, tendo que descançar para reabastecê-la.
- *Robô Aéreo Planador*
    - move-se normalmente na horizontal e vertical, mas sua altitude vai diminuindo conforme é deslocado.
- *Robô Aéreo Satélite*
    - move-se normalmente e consegue escanear uma área circular à procura de obstáculos dado um ângulo de visão; quanto mais alto, mais consegue ver.

[^1]: [Cavalo (xadrez)](<https://pt.wikipedia.org/wiki/Cavalo_(xadrez)>)
[^2]: [Peão (xadrez)](<https://pt.wikipedia.org/wiki/Pe%C3%A3o_(xadrez)>)

