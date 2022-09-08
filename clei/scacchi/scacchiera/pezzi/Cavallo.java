package clei.scacchi.scacchiera.pezzi;

import java.util.ArrayList;

import clei.scacchi.scacchiera.Scacchiera;
import clei.scacchi.scacchiera.Stato;

public class Cavallo extends Pezzo{
    public Cavallo(boolean white) {
        super(LABEL_CAVALLO, white);
    }

    Cavallo(Cavallo that) {
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

    private static int[][] offsetsMovimento = {
        {2, 1}, {2, -1},
        {1, 2}, {-1, 2},
        {1, -2}, {-1, -2},
        {-2, 1}, {-2, -1},
    };

    private ArrayList<Integer> scanScacchiera(Stato s, boolean enemyScan) {
        ArrayList<Integer> posizioniTrovate = new ArrayList<>();

        int pos = s.scacchiera.get(this);
        int x = Scacchiera.getX(pos) + 1;
        int y = Scacchiera.getY(pos) + 1;

        if(Scacchiera.isValidPos(Scacchiera.getPos(x, y))){
            for(int[] k : offsetsMovimento){
                int tmpPos = Scacchiera.getPos(x + k[0], y + k[1]);
                
                if(Scacchiera.isValidPos(tmpPos)){
                    if(s.scacchiera.isFree(tmpPos)){
                        posizioniTrovate.add(tmpPos);
                    }else if(enemyScan 
                            && s.scacchiera.isNotFree(tmpPos) 
                            && s.scacchiera.isEnemy(pos, tmpPos)){
                        posizioniTrovate.add(tmpPos);
                    }
                }
            }
        }

        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Cavallo(this);
    }

}
