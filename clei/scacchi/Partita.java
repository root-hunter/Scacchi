package clei.scacchi;

import java.util.ArrayList;

public class Partita {
    public Stato statoCorrente;
    ArrayList<Stato> mosse = new ArrayList<>();

    static final int STATO_IN_CORSO = 0;
    static final int STATO_VITTORIA_BIANCO = 1;
    static final int STATO_VITTORIA_NERO = 2;
    static final int STATO_PATTA = 3;

    static final boolean GIOCATORE_BIANCO = true;
    static final boolean GIOCATORE_NERO = false;

    int esito = STATO_IN_CORSO;

    @Override
    public String toString() {
        String buffer = "";

        for(Stato stato : mosse){
            buffer += "GIOCATORE DI TURNO: " + (stato.giocatoreDiTurno ? "BIANCO" : "NERO") + "\n";
            buffer += "MOSSA: "+stato.getUltimaMossa()+"\n";
            
            buffer += stato.scacchiera.toString();
            buffer += "\n";
        }

        return buffer;
    }

    public Partita(){
        statoCorrente = new Stato(GIOCATORE_BIANCO);
    }

    public void eseguiMossa (int from, int to) throws EccezioneMossa{
        boolean flag =  statoCorrente.eseguiMossa(from, to);

        if(!flag){
            throw new EccezioneMossa("Mossa non valida");
        }else{
            mosse.add(new Stato(statoCorrente));
            statoCorrente.giocatoreDiTurno = !statoCorrente.giocatoreDiTurno;
        }
    }

    public void abbandona(){
        if(esito == STATO_IN_CORSO){
            if(statoCorrente.giocatoreDiTurno){
                esito = STATO_VITTORIA_NERO;
            }else{
                esito = STATO_VITTORIA_BIANCO;
            }
        }
    }

    public boolean inCorso(){
        return esito == STATO_IN_CORSO;
    }

    public boolean vittoriaBianco(){
        return esito == STATO_VITTORIA_BIANCO;
    }

    public boolean vittoriaNero(){
        return esito == STATO_VITTORIA_NERO;
    }

    public boolean patta(){
        return esito == STATO_PATTA;
    }
}
