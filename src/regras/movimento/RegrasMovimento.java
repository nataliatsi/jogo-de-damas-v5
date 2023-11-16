package regras.movimento;

import controller.Jogo;
import model.*;

import java.util.ArrayList;

public class RegrasMovimento {
    private final Jogo jogo;
    private final ArrayList<Casa> pecasAComer;

    public RegrasMovimento(Jogo jogo){
        this.jogo = jogo;
        pecasAComer = new ArrayList<>();
    }
    public ArrayList<Casa> getPecasAComer() {
        return pecasAComer;
    }
    public boolean simularMovimentoEValidar(Casa origem, Casa destino) {
        Pedra peca = origem.getPeca();
        int casasComPecaSeguidas = 0;

        if (destino.getPeca() != null) return false;

        // SENTIDO DO MOVIMENTO E DISTÂNCIA DO MOVIMENTO
        int sentidoX = (destino.getX() - origem.getX());
        int sentidoY = (destino.getY() - origem.getY());
        int distanciaX = Math.abs(sentidoX);
        int distanciaY = Math.abs(sentidoY);

        if ((distanciaX == 0) || (distanciaY == 0)) return false;

        sentidoX = sentidoX/distanciaX;
        sentidoY = sentidoY/distanciaY;

        // REGRA DE MOVIMENTO DAS PEDRAS NO TABULEIRO CASO A DISTÂNCIA ATÉ A CASA CLICADA SEJA DE 2 BLOCOS
        if ((distanciaX == 2 && distanciaY == 2) &&
                ((peca instanceof PedraBranca) || (peca instanceof  PedraVermelha))) {

            Casa casa = jogo.getTabuleiro().getCasa((destino.getX() - sentidoX), (destino.getY() - sentidoY));
            if (casa.getPeca() == null) return false;
        } else {

            // REGRA DE MOVIMENTO DAS PEDRAS NO TABULEIRO CASO A DISTÂNCIA ATÉ A CASA CLICADA SEJA DE 1 BLOCO
            if (peca instanceof PedraBranca) {
                if ((distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY) && sentidoY == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                // REGRA DE MOVIMENTO DAS PEDRAS VERMELHAS
                if (peca instanceof PedraVermelha) {
                    if ((distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY) && sentidoY == -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        //PERCORRER AS CASAS E VERIFICAR:
        // 1 - SE HÁ MAIS DE UMA PEÇA SEGUIDA NO CAMINHO (VERDADEIRO RETORNA FALSO)
        // 2 - SE HÁ UMA PEÇA NO CAMINHO E É DA MESMA COR (VERDADEIRO RETORNA FALSO)
        int i = origem.getX();
        int j = origem.getY();

        while (!((i == destino.getX()) || (j == destino.getY()))) {
            i += sentidoX;
            j += sentidoY;

            Casa alvo = jogo.getTabuleiro().getCasa(i, j);
            Pedra pecaAlvo = alvo.getPeca();

            if (!(pecaAlvo == null)) {
                casasComPecaSeguidas += 1;

                // VE SE TEM UMA PECA DO MESMO TIPO NO CAMNHO, CASO TENHA, RETORNA FALSE
                if ((peca instanceof PedraBranca || peca instanceof  DamaBranca) &&
                        (pecaAlvo instanceof PedraBranca || pecaAlvo instanceof DamaBranca)) {
                    if (!pecasAComer.isEmpty()) pecasAComer.removeAll(pecasAComer);
                    return false;
                }

                if ((peca instanceof  PedraVermelha || peca instanceof DamaVermelha) &&
                        (pecaAlvo instanceof PedraVermelha || pecaAlvo instanceof  DamaVermelha)) {
                    if (!pecasAComer.isEmpty()) pecasAComer.removeAll(pecasAComer);
                    return false;
                }

            } else {

                // VE SE HÁ PEÇA PARA COMER NO CAMINHO E PASSAR A CASA À COLEÇÃO pecasAComer() PARA DEPOIS COME-LAS
                if (casasComPecaSeguidas == 1) {
                    Casa casa = jogo.getTabuleiro().getCasa((alvo.getX() - sentidoX), (alvo.getY() - sentidoY));
                    pecasAComer.add(casa);
                }
                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                if (!pecasAComer.isEmpty()) pecasAComer.removeAll(pecasAComer);
                return false;
            }
        }
        return true;
    }
    private boolean isMovimentoValido(Casa origem, int deltaX, int deltaY) {
        Pedra peca = origem.getPeca();
        int x = origem.getX();
        int y = origem.getY();
        int pecasSeguidasNoCaminho = 0;

        try {
            while (isDentroDosLimites(x, y)) {
                x += deltaX;
                y += deltaY;

                if (isCasaVazia(x, y)) {
                    return pecasSeguidasNoCaminho == 1;
                }

                Pedra pecaAtual = jogo.getTabuleiro().getCasa(x, y).getPeca();
                if (isPecaIncompativel(peca, pecaAtual, x, y, deltaX, deltaY)) {
                    return false;
                }

                pecasSeguidasNoCaminho++;
                if (pecasSeguidasNoCaminho > 1) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
    private boolean isDentroDosLimites(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
    private boolean isCasaVazia(int x, int y) {
        return jogo.getTabuleiro().getCasa(x, y).getPeca() == null;
    }
    private boolean isPecaIncompativel(Pedra pecaOrigem, Pedra pecaAtual, int x, int y, int deltaX, int deltaY) {
        if (pecaOrigem instanceof PedraBranca || pecaOrigem instanceof PedraVermelha) {
            return pecaAtual != null || jogo.getTabuleiro().getCasa(x + deltaX, y + deltaY).getPeca() != null;
        }

        return false;
    }
    public boolean deveContinuarJogando(Casa origem) {
        int[][] direcoes = {{Tabuleiro.X_ESQUERDA, Tabuleiro.Y_CIMA},
                {Tabuleiro.X_DIREITA, Tabuleiro.Y_CIMA},
                {Tabuleiro.X_DIREITA, Tabuleiro.Y_BAIXO},
                {Tabuleiro.X_ESQUERDA, Tabuleiro.Y_BAIXO}};

        for (int[] direcao : direcoes) {
            if (isMovimentoValido(origem, direcao[0], direcao[1])) {
                return true;
            }
        }

        return false;
    }

}
