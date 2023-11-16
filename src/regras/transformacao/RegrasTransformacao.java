package regras.transformacao;

import model.Casa;
import model.DamaBranca;
import model.DamaVermelha;
import model.Pedra;
import model.PedraBranca;
import model.PedraVermelha;

public class RegrasTransformacao {

    public boolean podeTransformarParaDama(Casa casa) {
        Pedra peca = casa.getPeca();

        if (peca instanceof PedraBranca) {
            return casa.getY() == 7;
        } else if (peca instanceof PedraVermelha) {
            return casa.getY() == 0;
        }

        return false;
    }

    public void transformarPedraParaDama(Casa casa) {
        Pedra peca = casa.getPeca();

        if (peca instanceof PedraBranca) {
            DamaBranca dama = new DamaBranca(casa);
            casa.colocarPeca(dama);
        } else if (peca instanceof PedraVermelha) {
            DamaVermelha dama = new DamaVermelha(casa);
            casa.colocarPeca(dama);
        }
    }
}
