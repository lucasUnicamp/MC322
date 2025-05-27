# **MC322**

Esse é o *Repositório Oficial*™ do grupo 8 para os laboratórios de MC322, onde se encontram os arquivos de cada tarefa e esse documento com explicações mais detalhadas sobre as ideias importantes do código.<br/>
Informações sobre os outros laboratórios podem ser encontradas na nossa [Wiki](https://github.com/lucasUnicamp/MC322/wiki)!

**Autoria de:**
- 276617 &emsp; Lucas Henrique Bertanha     
- 281289 &emsp; Leonardo Ferreira

## **- EXECUÇÃO**
Dentro da pasta MC322, usar os comandos para compilação e execução do laboratório mais recente:<br/>
- `javac -d bin src/Lab04/cliente/*.java src/Lab04/simulador/*.java`
- `java -cp bin cliente.Main`

<sup>Caso queira rodar laboratórios passados, basta substituir o número que segue `Lab`</sup>

Recomenda-se usar:
- **IDE:** VS Code
- **Versão do Java:** 21.0.6

## **- DIAGRAMA DE CLASSES**
![Diagrama de Classes do Lab04](assets/diagramaLab04.png)

## **- EXPLICAÇÕES**
#### --- ROBÔ<br/>
Robô agora é uma classe abstrata. Também agora tem um ID e um Estado (ligado/desligado). 

O ID é dado na inicialização, enquanto que o Estado pode ser mudado pelo submenu do robô. Enquanto desligado, o robô tem suas ações limitadas (não pode se mover, por exemplo).

#### --- MENU INTERATIVO<br/>
O Menu Interativo agora está melhor formatado e tem mais informações. 

O Ambiente recebeu um submenu próprio (ler abaixo) e dentro do submenu dos robôs, caso esse tenha implementado alguma interface, um *subsubmenu* irá aparecer para que os métodos dessas interfaces possam ser chamados.

#### --- AMBIENTE<br/>
O Ambiente agora conta com uma matriz 3D de Entidades, que permite armazenar as posições dos robôs e dos obstáculos dinâmicamente. Também tem seu próprio submenu, no qual foram implementados novas funções:
* `Visualizar Ambiente`: permite a visualização *top-down 2D* do Ambiente
* `Listar Robôs`: printa as informações úteis de todos robôs do ambiente (char de representação, id, tipo, nome, posição e status)
* `Exibir Mensagens`: printa as mensagens que foram trocadas entre robôs comunicáveis

Esse último acontece pois a Central de Comunicação foi posta como propriedade do Ambiente.

#### --- INTERFACES<br/>
**----- Pedidas**
* *Entidade*:
    - Tivemos que convencionar o X, Y e Z do obstáculo como a menor coordenada (ponta inferior do objeto) e usar suas dimensões posteriormente para fazer as operações
    - Implementado por `Robo` e `Obstaculo`
* *Comunicavel*:
    - Pode enviar mensagens de texto para outros comunicáveis, que vão para a Central de Comunicação. O horário que a mensagem foi enviada também é registrado!
    - Implementado por `RoboSatelite` e `RoboXadrez`
* *Sensoreavel*:
    - Permite que acione todos os sensores de uma vez e monitore um quadrado em sua volta
    - Implementado por `RoboAereo`

**----- Personalizadas**

...

#### --- EXCEÇÕES<br/>
**----- Pedidas**
* *RoboDesligado*:
    - Caso o robô tente fazer ações como movimentação ou usar sensores enquanto desligado, gera um erro
    - Implementado por `Robo`, lançado em `moverPara`, `moverComSensor`, `usarSensor`, `subir`, `descer`, `carregar`, `descarregar`, `lancamento`, `enviarMensagem` e `receberMensagem`
* *ErroComunicacao*:
    - Quando um robô com a interface Comunicável tenta enviar uma mensagem para outro robô que não tenha a interface
    - Implementado por `CentralComunicacao`, lançado em `checarDestinatario`
* *Colisao*:
    - Quando o robô colide com um obstáculo durante a movimentação, gera o erro
    - Implementado por `Robo`, lançado em `moverSemSensor`

**----- Personalizadas**
* *SemObstaculoDestrutivel*:
    - Implementado por ...
* *DesceuDemais*:
    - Quando um robô Aéreo desce uma quantidade maior do que a altidude em que está, gera um erro para não chegar a altidudes negativas
    - Implementado por `RoboAereo`, lançado em `descer`
* *MovimentoXadrezInvalidoException*:
    - Quando um robô Xadrez tenta se mover sem seguir a movimentação correta da pedra que representa (Peão ou Cavalo).
    - Implementado por `RoboXadrez`, lançado em `mover`

#### --- TAREFAS<br/>
...

