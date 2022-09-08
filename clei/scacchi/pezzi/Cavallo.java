package clei.scacchi.pezzi;

import java.util.ArrayList;

import clei.scacchi.Pezzo;
import clei.scacchi.Scacchiera;
import clei.scacchi.Stato;

public class Cavallo extends Pezzo{

    public Cavallo(boolean white) {
        super(LABEL_CAVALLO, white);
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

        int pos = s.scacchiera.get(this);
        int x = Scacchiera.getX(pos) + 1;
        int y = Scacchiera.getY(pos) + 1;

        if(Scacchiera.isValidPos(Scacchiera.getPos(x, y))){
            int[][] offsets = {
                {2, 1}, {2, -1},
                {1, 2}, {-1, 2},
                {1, -2}, {-1, -2},
                {-2, 1}, {-2, -1},
            };
        
            for(int[] k : offsets){
                int tmpPos = Scacchiera.getPos(x + k[0], y + k[1]);
                
                if(Scacchiera.isValidPos(tmpPos)){
                    if(s.scacchiera.isFree(tmpPos)){
                        posizioniTrovate.add(tmpPos);
                    }else{
                        if(enemyScan && s.scacchiera.isNotFree(tmpPos) 
                            && s.scacchiera.isEnemy(pos, tmpPos)){
                            posizioniTrovate.add(tmpPos);
                        }
                    }
                }
            }
        }
       
        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Cavallo(white);
    }

}
