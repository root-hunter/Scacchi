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

    
        stato.scacchiera.scacchiera.get(3).get(3).pezzo = 
        stato.scacchiera.scacchiera.get(0).get(4).pezzo;
        stato.scacchiera.scacchiera.get(0).get(4).pezzo = null;

       

        stato.scacchiera.scacchiera.get(5).get(5).pezzo = 
        stato.scacchiera.scacchiera.get(7).get(5).pezzo;
        stato.scacchiera.scacchiera.get(7).get(5).pezzo = null;

        System.out.println(stato.scacchiera.toString());

    
        System.out.println(stato.scaccoMatto());
    }
}
