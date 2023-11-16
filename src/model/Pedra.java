package model;

import ui.CasaUI;

public abstract class Pedra {
    protected Casa casa;

    public Pedra(Casa casa) {
        this.casa = casa;
        casa.colocarPeca(this);
    }

    public abstract void desenhar(CasaUI casaUI);

    public abstract boolean isMovimentoValido(Casa destino);

    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }
}
