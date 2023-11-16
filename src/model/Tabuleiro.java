package model;

public class Tabuleiro {
    public static final int MAX_LINHAS = 8;
    public static final int MAX_COLUNAS = 8;

    public static final int X_ESQUERDA = -1;
    public static final int X_DIREITA = 1;
    public static final int Y_BAIXO = -1;
    public static final int Y_CIMA = 1;

    private Casa[][] casas;

    public Tabuleiro() {
        montarTabuleiro();
    }

    private void montarTabuleiro() {
        casas = new Casa[MAX_LINHAS][MAX_COLUNAS];
        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 0; y < MAX_COLUNAS; y++) {
                Casa casa = new Casa(x, y);
                casas[x][y] = casa;
            }
        }

        colocarPecas();
    }

    public void colocarPecas() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    new PedraBranca(casas[x][y]);
                }
            }
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 5; y < 8; y++) {
                if ((x % 2 != 0 && y % 2 != 0) || (x % 2 == 0 && y % 2 == 0)) {
                    new PedraVermelha(casas[x][y]);
                }
            }
        }
    }

    public Casa getCasa(int x, int y) {
        return casas[x][y];
    }
}
