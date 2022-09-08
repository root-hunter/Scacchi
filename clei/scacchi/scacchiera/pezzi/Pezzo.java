package clei.scacchi.scacchiera.pezzi;

import java.util.ArrayList;

import clei.scacchi.scacchiera.Scacchiera;
import clei.scacchi.scacchiera.Stato;

interface IPezzo{
    public abstract boolean spostamentoPotenziale (Stato s, int target);
    public abstract ArrayList<Integer> listaSpostamentoPotenziale (Stato s);
    public abstract boolean attacco (Stato s, int target);
    public abstract ArrayList<Integer> listaAttacco (Stato s);
}

public abstract class Pezzo
 implements IPezzo{
    protected static final String LABEL_PEDONE = "P";
    protected static final String LABEL_CAVALLO = "C";
    protected static final String LABEL_ALFIERE = "A";
    protected static final String LABEL_TORRE = "T";
    protected static final String LABEL_RE = "R";
    protected static final String LABEL_REGINA = "D";
   
    protected String label;
    
    public boolean white;
    public boolean eliminato = false;

    public static final boolean WHITE = true;
    public static final boolean BLACK = false;

    public boolean mosso = false;

    public Pezzo(String label, boolean white){
        this.label = label;
        this.white = white;
    }

    @Override
    public String toString() {
        return white ? label.toUpperCase() : label.toLowerCase();
    }

    public abstract Pezzo copy();

    abstract class CalcoloPosizione{
        public abstract int get(int i, int x, int y);
    }

    public void elimina(){
        this.eliminato = true;
    }

    protected void controlloDiagonale(Stato s, boolean enemyScan,
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

    protected void controlloLaterale(Stato s, boolean enemyScan, 
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

    protected void controlloAssiLaterali(Stato s, boolean enemyScan, ArrayList<Integer> posizioniTrovate, boolean king){
        int pos = s.scacchiera.get(this);

        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        //POSSIBILE ESECUZIONE IN PARALLELO (MULTI THREAD)
        
        CalcoloPosizione calcoloAsseX = new CalcoloPosizione(){
            @Override
            public int get(int i, int x, int y) {
                return Scacchiera.getPos(i, y + 1);
            }
        };

        CalcoloPosizione calcoloAsseY = new CalcoloPosizione(){
            @Override
            public int get(int i, int x, int y) {
                return Scacchiera.getPos(x + 1, i);
            }
        };

        //DX
        controlloLaterale(s, enemyScan, posizioniTrovate, true, x + 2, king, calcoloAsseX);

        //SX
        controlloLaterale(s, enemyScan, posizioniTrovate, false, x, king, calcoloAsseX);
        
        //UP
        controlloLaterale(s, enemyScan, posizioniTrovate, true, y + 2, king, calcoloAsseY);
        
        //DOWN
        controlloLaterale(s, enemyScan, posizioniTrovate, false, y, king, calcoloAsseY);
    }

    protected void controlloAssiDiagonali(Stato s, boolean enemyScan, ArrayList<Integer> posizioniTrovate, boolean king){
        int pos = s.scacchiera.get(this);
        
        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        //POSSIBILE ESECUZIONE IN PARALLELO (MULTI THREAD)

        //NE
        controlloDiagonale(s, enemyScan, posizioniTrovate, true, true, x + 2, y + 2, king);

        //NW
        controlloDiagonale(s, enemyScan, posizioniTrovate, false, true, x, y + 2, king);

        //SW
        controlloDiagonale(s, enemyScan, posizioniTrovate, false, false, x, y, king);

        //SE
        controlloDiagonale(s, enemyScan, posizioniTrovate, true, false, x + 2, y, king);
    }
}



