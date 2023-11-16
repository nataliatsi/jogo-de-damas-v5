package ui;

import java.awt.Color;
import javax.swing.JPanel;

import model.*;
import controller.Jogo;

public class TabuleiroUI extends JPanel {

    private JanelaPrincipalUI janela;
    private CasaUI[][] casas;

    public TabuleiroUI() {
    }

    public TabuleiroUI(JanelaPrincipalUI janela) {
        this.janela = janela;
        initComponents();
        criarCasas();
    }

    private void criarCasas() {
        casas = new CasaUI[8][8];
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                Color cor = calcularCor(x, y);
                CasaUI casa = new CasaUI(x, y, cor, this);
                casas[x][y] = casa;
                add(casa);
            }
        }
    }

    private Color calcularCor(int x, int y) {
        if (x % 2 == 0) {
            if (y % 2 == 0) {
                return CasaUI.COR_ESCURA;
            }
            else {
                return CasaUI.COR_CLARA;
            }
        }
        else {
            if (y % 2 == 0) {
                return CasaUI.COR_CLARA;
            }
            else {
                return CasaUI.COR_ESCURA;
            }
        }
    }

    public void atualizar(Jogo jogo) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                CasaUI casaUI = casas[x][y];
                Tabuleiro tabuleiro = jogo.getTabuleiro();
                Casa casa = tabuleiro.getCasa(x, y);

                atualizarCasa(casa, casaUI);
            }
        }
    }

    private void atualizarCasa(Casa casa, CasaUI casaUI) {
        if (casa.possuiPeca()) {
            Pedra peca = casa.getPeca();
            peca.desenhar(casaUI);
        } else {
            casaUI.apagarPeca();
        }
    }

    public JanelaPrincipalUI getJanela() {
        return janela;
    }

    private void initComponents() {
        setLayout(new java.awt.GridLayout(8, 8));
    }
}
