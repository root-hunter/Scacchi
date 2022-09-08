package clei.scacchi;

import java.util.ArrayList;

import clei.scacchi.scacchiera.Scacchiera;
import clei.scacchi.scacchiera.Stato;

public class Partita {
    public Stato statoCorrente;
    public ArrayList<Stato> mosse = new ArrayList<>();

    static final int STATO_IN_CORSO = 0;
    static final int STATO_VITTORIA_BIANCO = 1;
    static final int STATO_VITTORIA_NERO = 2;
    static final int STATO_PATTA = 3;

    static final boolean GIOCATORE_BIANCO = true;
    static final boolean GIOCATORE_NERO = false;

    static public String getEsito(int esito){
        switch(esito){
            case 0:
                return "IN CORSO";
            case 1: 
                return "VITTORIA BIANCO";
            case 2: 
                return "VITTORIA NERO";
            case 3:
                return "PATTA";
            default:
                return "ERR";
        }
    }

    int esito = STATO_IN_CORSO;

    @Override
    public String toString() {
        String buffer = "";

        buffer += "INIZIO PARTITA\n";
        buffer += new Scacchiera().toString();
        for(Stato stato : mosse){
            buffer += "GIOCATORE DI TURNO: " + (stato.giocatoreDiTurno ? "BIANCO" : "NERO") + "\n";
            buffer += "MOSSA: "+stato.getUltimaMossa()+"\n";
            buffer += "STATO GIOCO: "+getEsito(stato.esito)+"\n";
            
            if(stato.checkEnPassant()){
                buffer += "CASA EN PASSANT: "+stato.enPassantPos+"\n";
            }
            
            buffer += stato.scacchiera.toString();
            buffer += "\n";
        }
        buffer += "STATO FINALE: "+getEsito(esito)+"\n";

        return buffer;
    }

    public Partita(){
        statoCorrente = new Stato(GIOCATORE_BIANCO, this);
    }

    public void eseguiMossa (int from, int to) throws EccezioneMossa{
        
        if(esito == STATO_VITTORIA_BIANCO){
            throw new EccezioneMossa("Mossa non valida: partita vinta dal BIANCO");
        }

        if(esito == STATO_VITTORIA_NERO){
            throw new EccezioneMossa("Mossa non valida: partita vinta dal NERO");
        }

        if(esito == STATO_PATTA){
            throw new EccezioneMossa("Mossa non valida: partita finita in PATTA");
        }

        boolean flag =  statoCorrente.eseguiMossa(from, to);

        checkEsito();

        if(!flag){
            throw new EccezioneMossa("Mossa non valida");
        }else{
            statoCorrente.esito = esito;
            mosse.add(new Stato(statoCorrente));
            
            statoCorrente.giocatoreDiTurno = !statoCorrente.giocatoreDiTurno;
        }
    }

    private void checkEsito() {
        if(statoCorrente.scaccoMatto()){
            esito = !statoCorrente.giocatoreDiTurno ? STATO_VITTORIA_BIANCO : STATO_VITTORIA_NERO;
        }else if(statoCorrente.stallo()){
            esito = STATO_PATTA;
        }
    }

    public void abbandona(){
        if(esito == STATO_IN_CORSO){
            esito = !statoCorrente.giocatoreDiTurno ? STATO_VITTORIA_BIANCO : STATO_VITTORIA_NERO;
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
