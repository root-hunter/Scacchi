package clei.scacchi.pezzi;
import java.util.ArrayList;

import clei.scacchi.Scacchiera;

import clei.scacchi.Pezzo;
import clei.scacchi.Stato;


public class Regina extends Pezzo {
    public Regina(boolean white){
        super(LABEL_REGINA, white);
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

        boolean king = false;
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
        return posizioniTrovate;
    }

    @Override
    public Pezzo copy() {
        return new Regina(white);
    }
}
