package clei.scacchi.pezzi;

import clei.scacchi.Pezzo;
import clei.scacchi.Scacchiera;
import clei.scacchi.Stato;
import java.util.ArrayList;


public class Alfiere extends Pezzo{

    public Alfiere(boolean white) {
        super(LABEL_ALFIERE, white);
    }

    public boolean spostamentoPotenziale(Stato s, int target){
        return listaSpostamentoPotenziale(s).contains(target);
    }

    public ArrayList<Integer> listaSpostamentoPotenziale (Stato s){
        ArrayList<Integer> possibiliCase = scanScacchiera(s, false);

        return possibiliCase;
    }

    public boolean attacco(Stato s, int target){
        return listaAttacco(s).contains(target);
    }

    public ArrayList<Integer> listaAttacco(Stato s){
        ArrayList<Integer> possibiliAttacchi = scanScacchiera(s, true);

        return possibiliAttacchi;
    }

    private ArrayList<Integer> scanScacchiera(Stato s, boolean enemyScan) {
        ArrayList<Integer> posizioniTrovate = new ArrayList<>();

        int pos = s.scacchiera.get(this);
        
        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        //POSSIBILE ESECUZIONE IN PARALLELO (MULTI THREAD)

        //NE
        controlloDiagonale(s, enemyScan, posizioniTrovate, true, true, x + 2, y + 2, false);

        //NW
        controlloDiagonale(s, enemyScan, posizioniTrovate, false, true, x, y + 2, false);

        //SW
        controlloDiagonale(s, enemyScan, posizioniTrovate, false, false, x, y, false);

        //SE
        controlloDiagonale(s, enemyScan, posizioniTrovate, true, false, x + 2, y, false);
        
        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Alfiere(white);
    }
}
