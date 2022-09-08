package clei.scacchi.scacchiera.pezzi;
import java.util.ArrayList;

import clei.scacchi.scacchiera.Stato;

public class Regina extends Pezzo {
    public Regina(boolean white){
        super(LABEL_REGINA, white);
    }    

    Regina(Regina that) {
        super(that.label, that.white);
        this.mosso = that.mosso;
        this.eliminato = that.eliminato;
    }

    public boolean spostamentoPotenziale(Stato s, int target){
        return listaSpostamentoPotenziale(s).contains(target);
    }

    public ArrayList<Integer> listaSpostamentoPotenziale (Stato s){
        return scanScacchiera(s, false);
    }

    public boolean attacco(Stato s, int target){
        return listaAttacco(s).contains(target);
    }

    public ArrayList<Integer> listaAttacco(Stato s){
        return scanScacchiera(s, true);
    }

    private ArrayList<Integer> scanScacchiera(Stato s, boolean enemyScan) {
        ArrayList<Integer> posizioniTrovate = new ArrayList<>();
        
        //POSSIBILE ESECUZIONE IN PARALLELO (MULTI THREAD)
        
        controlloAssiDiagonali(s, enemyScan, posizioniTrovate, false);
        controlloAssiLaterali(s, enemyScan, posizioniTrovate, false);
       
        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Regina(this);
    }
}
