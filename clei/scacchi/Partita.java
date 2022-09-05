package clei.scacchi;

import java.util.ArrayList;

public class Partita {
    Stato statoCorrente;
    ArrayList<Stato> mosse = new ArrayList<>();
    int esito = 0;


    public Partita(){
        Giocatore bianco = new Giocatore("Lello");
        Giocatore nero = new Giocatore("Lello black");

        this.statoCorrente = new Stato(bianco, nero);
    }
}
