package clei.scacchi.pezzi;

import java.util.ArrayList;

import clei.scacchi.Pezzo;
import clei.scacchi.Stato;


public class Re extends Pezzo{
    
    public Re(boolean white) {
        super("R", white);
    }

    public boolean spostamentoPotenziale(Stato s, int target){
        return false;
    }

    public ArrayList<Integer> listaSpostamentoPotenziale (Stato s){
        return new ArrayList<>();
    }

    public boolean attacco(Stato s, int target){
        return false;
    }

    public ArrayList<Integer> listaAttacco(Stato s){
        return new ArrayList<>();
    }

}
