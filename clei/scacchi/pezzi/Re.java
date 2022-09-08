package clei.scacchi.pezzi;

import java.util.ArrayList;

import clei.scacchi.Pezzo;
import clei.scacchi.Scacchiera;
import clei.scacchi.Stato;


public class Re extends Pezzo{
    
    public Re(boolean white) {
        super(LABEL_RE, white);
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

        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        //POSSIBILE ESECUZIONE IN PARALLELO (MULTI THREAD)
        boolean king = true;
        
        //UP
        controlloLaterale(s, enemyScan, posizioniTrovate, true, y + 2, king,
            new CalcoloPosizione(){
                @Override
                public int get(int i, int x, int y) {
                    return Scacchiera.getPos(x + 1, i);
                }
        });

        //NW
        controlloDiagonale(s, enemyScan, posizioniTrovate, false, true, x, y + 2, king);

        //SX
        controlloLaterale(s, enemyScan, posizioniTrovate, false, x, king,
        new CalcoloPosizione(){
            @Override
            public int get(int i, int x, int y) {
                return Scacchiera.getPos(i, y + 1);
            }
        });

        //SW
        controlloDiagonale(s, enemyScan, posizioniTrovate, false, false, x, y, king);

        //DOWN
        controlloLaterale(s, enemyScan, posizioniTrovate, false, y, king,
            new CalcoloPosizione(){
                @Override
                public int get(int i, int x, int y) {
                    return Scacchiera.getPos(x + 1, i);
                }
        });

        //SE
        controlloDiagonale(s, enemyScan, posizioniTrovate, true, false, x + 2, y, king);

        //NE
        controlloDiagonale(s, enemyScan, posizioniTrovate, true, true, x + 2, y + 2, king);

        //DX
        controlloLaterale(s, enemyScan, posizioniTrovate, true, x + 2, king,
            new CalcoloPosizione(){
                @Override
                public int get(int i, int x, int y) {
                    return Scacchiera.getPos(i, y + 1);
                }
        });

        if(!enemyScan){
            if(white){
                //ARROCCO DX
                if(s.scacchiera.isFree(61)
                && s.scacchiera.isFree(71)
                && s.scacchiera.isNotFree(81)
                && s.scacchiera.get(81).label.equals("T")){
                    posizioniTrovate.add(71);
                }
    
                //ARROCCO SX
                if(s.scacchiera.isFree(41)
                && s.scacchiera.isFree(31)
                && s.scacchiera.isFree(21)
                && s.scacchiera.isNotFree(11)
                && s.scacchiera.get(11).label.equals("T")){
                    posizioniTrovate.add(31);
                }
            }else{
                //ARROCCO DX
                if(s.scacchiera.isFree(68)
                && s.scacchiera.isFree(78)
                && s.scacchiera.isNotFree(88)
                && s.scacchiera.get(88).label.equals("T")){
                    posizioniTrovate.add(88);
                }
    
                //ARROCCO SX
                if(s.scacchiera.isFree(48)
                && s.scacchiera.isFree(38)
                && s.scacchiera.isFree(28)
                && s.scacchiera.isNotFree(18)
                && s.scacchiera.get(18).label.equals("T")){
                    posizioniTrovate.add(38);
                }
            }
        }
        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Re(white);
    }
}
