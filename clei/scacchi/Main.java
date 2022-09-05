package clei.scacchi;

public class Main {
    public static void main(String[] args) {
        Giocatore bianco = new Giocatore("BIANCO");
        Giocatore nero = new Giocatore("NERO");

        Stato stato = new Stato(bianco, nero);

        
        /* 
        System.out.println(
            stato.scacchiera.get(35)
            .spostamentoPotenziale(stato, 36)
            );
        */

        stato.scacchiera.scacchiera.get(6).get(7).pezzo = 
            stato.scacchiera.scacchiera.get(0).get(0).pezzo;
        stato.scacchiera.scacchiera.get(0).get(0).pezzo = null;
        
        System.out.println(stato.scacchiera.toString());
        System.out.println(stato.scacchiera.get(87).listaAttacco(stato));
    }
}
