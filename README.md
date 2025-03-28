# **MC322**
Esse é o *Repositório Oficial*™ do grupo 8 para os laboratórios de MC322, onde se encontram os arquivos de cada tarefa e esse documento com explicações mais detalhadas sobre as ideias importantes do código.<br/>

(¬_¬") &emsp; (╯‵□′)╯︵┻━┻ &emsp; Σ(っ °Д °;)っ

**Autoria de:**
- 276617 &emsp; Lucas Henrique Bertanha     
- 281289 &emsp; Leonardo Ferreira

## **Explicações**
### **= Lab 02 ====**
**- MOVIMENTO DO ROBÔ --**<br/>
 &emsp; Optamos por considerar que o robô pode tomar somente dois caminhos dado um `deltaX` e um `deltaY`, sendo esses:
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

**- TIPOS DE ROBÔ --**<br/>
 &emsp; Os 4 robôs adicionais que criamos são:
- *Robô Terrestre Xadrex*
    - move-se ou como a peça Cavalo, que anda duas posições em uma direção e uma na outra, formando um L; ou um Peão, que deve andar uma ou duas posições para alguma direção
- *Robô Terrestre*
    - faz isso
- *Robô Aéreo Planador*
    - move-se normalmente na horizontal e vertical, mas sua altitude vai diminuindo conforme é deslocado
- *Robô Aéreo *
    - faz aquilo

<br/>
