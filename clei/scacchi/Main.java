package clei.scacchi;

public class Main {
    public static void main(String[] args) {
        // Giocatore bianco = new Giocatore("BIANCO");
        // Giocatore nero = new Giocatore("NERO");

        // Stato stato = new Stato(true);

        // stato.scacchiera.scacchiera.get(3).get(3).pezzo = 
        // stato.scacchiera.scacchiera.get(0).get(4).pezzo;
        // stato.scacchiera.scacchiera.get(0).get(4).pezzo = null;
   
        // stato.scacchiera.scacchiera.get(5).get(5).pezzo = 
        // stato.scacchiera.scacchiera.get(7).get(5).pezzo;
        // stato.scacchiera.scacchiera.get(7).get(5).pezzo = null;

        // System.out.println(stato.scacchiera.toString());
        // System.out.println(stato.scacco());

        // System.out.println(stato.simulaSpostamentoOCattura(44, 45)
        // .scacco());


        Partita partita = new Partita();

        try{
            partita.eseguiMossa(22, 24);
            partita.eseguiMossa(27, 26);

            partita.eseguiMossa(42, 44);
            partita.eseguiMossa(47, 46);
            
            partita.eseguiMossa(41, 43);
            partita.eseguiMossa(48, 47);
        }catch(Exception e){
            System.err.println(e);
        }

        //System.out.println(partita.statoCorrente.scacchiera.toString());
        System.out.println(partita.toString());

        // System.out.println(stato.scaccoMatto());
    }
}
