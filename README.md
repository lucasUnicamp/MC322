# **MC322**

Esse é o *Repositório Oficial*™ do grupo 8 para os laboratórios de MC322, onde se encontram os arquivos de cada tarefa e esse documento com explicações mais detalhadas sobre as ideias importantes do código.<br/>

**Autoria de:**
- 276617 &emsp; Lucas Henrique Bertanha     
- 281289 &emsp; Leonardo Ferreira

## **Execução**
Dentro da pasta MC322, usar os comandos:<br/>
`javac -d bin src/Lab02/simulador/*.java` e `java -cp bin simulador.Main`

## **Explicações**
### **= Lab 02 ====**
#### **- Movimento do Robô --**<br/>
Optamos por considerar que o robô pode tomar somente dois caminhos dado um `deltaX` e um `deltaY`, sendo esses:
- mover-se `deltaX` totalmente no eixo X primeiro e depois `deltaY` totalmente no eixo Y
```
# # # # []
# # # # []
[][][][][]
```
- mover-se `deltaY` totalmente no eixo Y primeiro e depois `deltaX` totalmente no eixo X
```
[][][][][]
[] # # # #
[] # # # #
```
<sup>Em que [ ] representa o caminho que o robô irá fazer</sup>

#### **- Tipos de Robô --**<br/>
Os 4 robôs adicionais que criamos são:
- *Robô Terrestre Xadrex*
    - move-se ou como a peça Cavalo[^1], que anda duas posições em uma direção e uma na outra, formando um L; ou um Peão[^2], que deve andar uma ou duas posições para alguma direção
- *Robô Terrestre Preguiça*
    - move-se normalmente mas descarrega sua energia a cada movimento, tendo que descançar para reabastecê-la
- *Robô Aéreo Planador*
    - move-se normalmente na horizontal e vertical, mas sua altitude vai diminuindo conforme é deslocado
- *Robô Aéreo Satélite*
    - move-se normalmente e consegue escanear uma área circular à procura de obstáculos dado um ângulo de visão; quanto mais alto, mais consegue ver
___
[^1]: <https://pt.wikipedia.org/wiki/Cavalo_(xadrez)>
[^2]: <https://pt.wikipedia.org/wiki/Pe%C3%A3o_(xadrez)>