# **MC322**

Esse é o *Repositório Oficial*™ do grupo 8 para os laboratórios de MC322, onde se encontram os arquivos de cada tarefa e esse documento com explicações mais detalhadas sobre as ideias importantes do código. Para especificações dos métodos em si, recomenda-se ler os comentários. <br/>

Informações sobre os outros laboratórios podem ser encontradas na nossa [Wiki](https://github.com/lucasUnicamp/MC322/wiki)!

**Autoria de:**
- 276617 &emsp; Lucas Henrique Bertanha     
- 281289 &emsp; Leonardo Ferreira

## **- EXECUÇÃO**
Dentro da pasta MC322, usar os comandos para compilação e execução do laboratório mais recente:<br/>
- `javac -d bin src/Lab05/cliente/*.java ./src/Lab05/simulador/agentesinteligentes/*.java ./src/Lab05/simulador/ambiente/*.java ./src/Lab05/simulador/enums/*.java ./src/Lab05/simulador/excecoes/*.java ./src/Lab05/simulador/interfaces/*.java ./src/Lab05/simulador/missoes/*.java ./src/Lab05/simulador/robos/*.java ./src/Lab05/simulador/sensores/*.java`
- `java -cp bin cliente.Main`

<sup>Caso queira rodar laboratórios passados, basta substituir o número que segue `Lab`</sup>

Recomenda-se usar:
- **IDE:** VS Code
- **Versão do Java:** 21.0.6

## **- DIAGRAMA DE CLASSES**
![Diagrama de Classes do Lab05](assets/diagramaLab05.png)

## **- EXPLICAÇÕES**
#### --- AGENTE INTELIGENTE<br/>
Novo tipo de Robô que...

#### --- MENU INTERATIVO<br/>
O Menu Interativo agora está melhor formatado e tem mais informações. 

Já os robôs continuam com seus próprios submenus que agora também têm um subsubmenu chamado **Extras**, em que se pode realizar as tarefas específicas de cada robô e usar métodos das interfaces novas.

#### --- INTERFACES<br/>
**----- Pedidas**
* *Entidade*:
    - Tivemos que convencionar o X, Y e Z do obstáculo como a menor coordenada (ponta inferior do objeto) e usar suas dimensões posteriormente para fazer as operações;
    - Implementado por `Robo` e `Obstaculo`.
* *Comunicavel*:
    - Pode enviar mensagens de texto para outros comunicáveis, que vão para a Central de Comunicação. O horário que a mensagem foi enviada também é registrado!
    - Implementado por `RoboSatelite` e `RoboXadrez`.
* *Sensoreavel*:
    - Permite que acione todos os sensores de uma vez e monitore um quadrado em sua volta;
    - Implementado por `RoboAereo`.

#### --- MISSÕES<br/>
Cada tipo de robô controlável recebeu uma tarefa específica, que é uma ação temática  acessível pelo submenu **Extras**. Essas são:
* *Marchar em frente*:
    - O robô anda em linha reta na direção em que está 'olhando', parando apenas caso colida com um obstáculo ou saia do ambiente;
    - Implementado por `RoboTerrestre`.
* *Mover como Rainha*:
    - Permite que a próxima movimentação do robô seja feita como a peça Rainha do xadrez; 
    - Implementado por `RoboXadrez`.
* *Superdescansamento*:
    - O robô descansa e carrega suas energias ao máximo;
    - Implementado por `RoboPreguica`.
* *Teleportar*:
    - O robô viaja super rápido e se teletransporta para uma posição aleatória do ambiente;
    - Implementado por `RoboAereo`.
* *Trocar sentido do Planador*:
    - Passa a subir ao invés de descer enquanto se move. Pode ser usado novamente para reverter o sentido;
    - Implementado por `RoboPlanador`.
* *Carga ideal para Órbita*:
    - Calcula o intervalo perfeito de valores de carregamento para colocar o robô em órbita; 
    - Implementado por `RoboSatelite`.
