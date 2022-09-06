package clei.scacchi.pezzi;

import java.util.ArrayList;

import clei.scacchi.Pezzo;
import clei.scacchi.Scacchiera;
import clei.scacchi.Stato;

public class Torre extends Pezzo{
    public Torre(boolean white) {
        super("T", white);
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

        //DX
        controlloLaterale(s, enemyScan, posizioniTrovate, true, x + 2, false,
            new CalcoloPosizione(){
                @Override
                public int get(int i, int x, int y) {
                    return Scacchiera.getPos(i, y + 1);
                }
        });

        //SX
        controlloLaterale(s, enemyScan, posizioniTrovate, false, x, false,
            new CalcoloPosizione(){
                @Override
                public int get(int i, int x, int y) {
                    return Scacchiera.getPos(i, y + 1);
                }
        });
        
        //UP
        controlloLaterale(s, enemyScan, posizioniTrovate, true, y + 2, false,
            new CalcoloPosizione(){
                @Override
                public int get(int i, int x, int y) {
                    return Scacchiera.getPos(x + 1, i);
                }
        });
        
        //DOWN
        controlloLaterale(s, enemyScan, posizioniTrovate, false, y, false,
            new CalcoloPosizione(){
                @Override
                public int get(int i, int x, int y) {
                    return Scacchiera.getPos(x + 1, i);
                }
        });
      
        return posizioniTrovate;
    }

}
