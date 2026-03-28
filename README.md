# Football Career Manager ⚽

Jogo de simulação de carreira de futebol em Java, rodando no terminal.

## Como rodar

1. Abra o projeto no IntelliJ IDEA
2. Configure o `Main.java` como classe principal
3. Rode o projeto

## Funcionalidades

- Criação de jogador (nome, país, posição)
- Fase de base simulada
- Progressão por temporadas com fator de idade
- Eventos aleatórios (lesão, artilheiro, título, escândalo...)
- Mercado de transferências com clubes de nível 1 a 5
- Aposentadoria automática e voluntária
- Salvar e carregar carreira

## Estrutura

```
model/     → entidades e enums do jogo
service/   → lógica de negócio
ui/        → interface de terminal e orquestrador
Main.java  → ponto de entrada
```
