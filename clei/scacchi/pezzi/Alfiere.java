package clei.scacchi.pezzi;

import clei.scacchi.Pezzo;
import clei.scacchi.Stato;
import java.util.ArrayList;


public class Alfiere extends Pezzo{

    public Alfiere(boolean white) {
        super("A", white);
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
