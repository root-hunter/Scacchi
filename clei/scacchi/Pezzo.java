package clei.scacchi;

import java.util.ArrayList;

interface IPezzo{
    public abstract boolean spostamentoPotenziale (Stato s, int target);
    public abstract ArrayList<Integer> listaSpostamentoPotenziale (Stato s);
    public abstract boolean attacco (Stato s, int target);
    public abstract ArrayList<Integer> listaAttacco (Stato s);
}

abstract public class Pezzo
 implements IPezzo{
    public String label;
    public boolean white;

    public static final String LABEL_PEDONE = "P";
    public static final String LABEL_CAVALLO = "C";
    public static final String LABEL_ALFIERE = "A";
    public static final String LABEL_TORRE = "T";
    public static final String LABEL_RE = "R";
    public static final String LABEL_REGINA = "D";

    public static final boolean WHITE = true;
    public static final boolean BLACK = false;


    public Pezzo(String label, boolean white){
        this.label = label;
        this.white = white;
    }

    @Override
    public String toString() {
        return white ? label.toUpperCase() : label.toLowerCase();
    }

    public abstract Pezzo copy();

    public abstract class CalcoloPosizione{
        public abstract int get(int i, int x, int y);
    }

    public void controlloDiagonale(Stato s, boolean enemyScan,
     ArrayList<Integer> posizioniTrovate,
      boolean incI, boolean incJ, int startI, int startJ, boolean king) {
        int pos = s.scacchiera.get(this);

        int i = startI;
        int j = startJ;
        
        while(((incI && i <= Scacchiera.MAX) || (!incI && i > 0)) 
                && ((incJ && j <= Scacchiera.MAX) || (!incJ && j > 0))){        
            int tmpPos = Scacchiera.getPos(i, j);

            if(s.scacchiera.isFree(tmpPos)){
                posizioniTrovate.add(tmpPos);
            }else{

                if(enemyScan && s.scacchiera.isNotFree(tmpPos) 
                    && s.scacchiera.isEnemy(pos, tmpPos)){
                    posizioniTrovate.add(tmpPos);
                }
                break;
            }
            if(incI) ++i; else --i;
            if(incJ) ++j; else --j;
            if(king) break;
        }
    }

    public void controlloLaterale(Stato s, boolean enemyScan, 
     ArrayList<Integer> posizioniTrovate,
      boolean inc, int start, boolean king, CalcoloPosizione calcolo) {
        int pos = s.scacchiera.get(this);

        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);
        
        int i = start;

        while((inc && i <= Scacchiera.MAX) || (!inc && i > 0)){
            int tmpPos = calcolo.get(i, x, y);

            if(s.scacchiera.isFree(tmpPos)){
                posizioniTrovate.add(tmpPos);
            }else{
                if(enemyScan && s.scacchiera.isNotFree(tmpPos) 
                    && s.scacchiera.isEnemy(pos, tmpPos)){
                    posizioniTrovate.add(tmpPos);
                }
                break;
            }
            if(inc) ++i; else --i;
            if(king) break;
        };               
    }
}



