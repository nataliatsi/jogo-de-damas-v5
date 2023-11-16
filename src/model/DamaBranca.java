package model;

import ui.CasaUI;

public class DamaBranca extends Pedra{
    public DamaBranca(Casa casa) {
        super(casa);
    }

    @Override
    public void desenhar(CasaUI casaUI) {
        casaUI.desenharDamaBranca();
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs((destino.getX() - casa.getX()));
        int distanciaY = Math.abs((destino.getY() - casa.getY()));

        if (distanciaX == distanciaY) return true;

        return false;
    }

}
