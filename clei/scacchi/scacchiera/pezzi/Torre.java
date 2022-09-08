package clei.scacchi.scacchiera.pezzi;

import java.util.ArrayList;

import clei.scacchi.scacchiera.Stato;

public class Torre extends Pezzo{
    public Torre(boolean white) {
        super(LABEL_TORRE, white);
    }

    Torre(Torre that) {
        super(that.label, that.white);
        this.mosso = that.mosso;
        this.eliminato = that.eliminato;
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

        controlloAssiLaterali(s, enemyScan, posizioniTrovate, false);
      
        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Torre(this);
    }

}
