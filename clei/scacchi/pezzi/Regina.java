package clei.scacchi.pezzi;
import java.util.ArrayList;

import clei.scacchi.Pezzo;
import clei.scacchi.Stato;



public class Regina extends Pezzo {
    public Regina(boolean white){
        super("D", white);
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
