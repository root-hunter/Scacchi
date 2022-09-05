package clei.scacchi;

public class Stato {
    public Scacchiera scacchiera = new Scacchiera();
    
    Giocatore bianco;
    Giocatore nero;

    Giocatore giocatoreDiTurno;

    Stato(Giocatore bianco, Giocatore nero){
        this.bianco = bianco;
        this.nero = nero;
        this.giocatoreDiTurno = bianco;
    }
}
