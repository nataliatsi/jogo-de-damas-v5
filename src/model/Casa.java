package model;

public class Casa {
    private final int x;
    private final int y;
    private Pedra peca;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.peca = null;
    }
    public void colocarPeca(Pedra peca) {
        this.peca = peca;
    }

    public void removerPeca() {
        peca = null;
    }

    public Pedra getPeca() {
        return peca;
    }
    public boolean possuiPeca() {
        return peca != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
