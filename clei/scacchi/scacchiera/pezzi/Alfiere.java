package clei.scacchi.scacchiera.pezzi;

import clei.scacchi.scacchiera.Stato;
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

        controlloAssiDiagonali(s, enemyScan, posizioniTrovate, false);
        
        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Alfiere(white);
    }
}
