package clei.scacchi;

import clei.scacchi.partita.Partita;

public class Main {
    public static void main(String[] args) {
        Partita partita = new Partita();

        try{
            // TEST PROMOZIONE PEDONE BIANCO IN REGINA
            // partita.eseguiMossa(32, 34);
            // partita.eseguiMossa(47, 45);

            // partita.eseguiMossa(34, 45);
            // partita.eseguiMossa(57, 55);

            // partita.eseguiMossa(45, 46);
            // partita.eseguiMossa(48, 84);

            // partita.eseguiMossa(46, 47);
            // partita.eseguiMossa(58, 57);

            // partita.eseguiMossa(47, 48);
            // partita.eseguiMossa(57, 56);

            // partita.eseguiMossa(48, 43);
            // partita.eseguiMossa(55, 54);


            // TEST SCACCO MATTO IN 3 MOSSE
            // partita.eseguiMossa(52, 54);
            // partita.eseguiMossa(67, 65);

            // partita.eseguiMossa(54, 65);
            // partita.eseguiMossa(77, 75);

            // partita.eseguiMossa(41, 85);
            // partita.eseguiMossa(48, 57);

            System.out.println(partita.statoCorrente.scaccoMatto());


        }catch(Exception e){
            System.err.println(e);
        }

        //System.out.println(partita.statoCorrente.scacchiera.toString());
        System.out.println(partita.toString());

        // System.out.println(stato.scaccoMatto());
    }
}
