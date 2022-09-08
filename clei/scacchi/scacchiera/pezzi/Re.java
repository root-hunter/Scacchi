package clei.scacchi.scacchiera.pezzi;

import java.util.ArrayList;

import clei.scacchi.scacchiera.Stato;

public class Re extends Pezzo{
    
    public Re(boolean white) {
        super(LABEL_RE, white);
    }

    Re(Re that) {
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

        controlloAssiDiagonali(s, enemyScan, posizioniTrovate, true);
        controlloAssiLaterali(s, enemyScan, posizioniTrovate, true);

        if(white){
            if(!s.arroccoBianco && s.checkArroccoBiancoDx()){
                posizioniTrovate.add(71);
            }

            if(!s.arroccoBianco && s.checkArroccoBiancoSx()){
                posizioniTrovate.add(31);
            }
        }else{
            if(!s.arroccoNero && s.checkArroccoNeroDx()){
                posizioniTrovate.add(78);
            }

            if(!s.arroccoNero && s.checkArroccoNeroSx()){
                posizioniTrovate.add(38);
            }
        }

        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Re(this);
    }
}
