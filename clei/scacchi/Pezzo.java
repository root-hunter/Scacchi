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

    public Pezzo(String label, boolean white){
        this.label = label;
        this.white = white;
    }

    @Override
    public String toString() {
        return white ? label.toUpperCase() : label.toLowerCase();
    }
}



