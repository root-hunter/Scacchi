package clei.scacchi;

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

            //TEST ARROCCO
            // partita.eseguiMossa(52, 54);
            // partita.eseguiMossa(57, 55);

            // partita.eseguiMossa(61, 43);
            // partita.eseguiMossa(27, 25);

            // partita.eseguiMossa(71, 83);
            // partita.eseguiMossa(78, 86);

            // partita.eseguiMossa(51, 71);
            // partita.eseguiMossa(17, 16);

            //TEST EN PASSANT
            partita.eseguiMossa(52, 54);
            partita.eseguiMossa(67, 65);

            partita.eseguiMossa(54, 55);
            partita.eseguiMossa(47, 45);

            partita.eseguiMossa(55, 46);
            partita.eseguiMossa(17, 16);

            partita.eseguiMossa(46, 57);
            partita.eseguiMossa(38, 47);

            partita.eseguiMossa(41, 85);
            partita.eseguiMossa(77, 76);

            partita.eseguiMossa(85, 76);



        }catch(Exception e){
            System.err.println(e);
        }

        //System.out.println(partita.statoCorrente.scacchiera.toString());
        System.out.println(partita.toString());

        // System.out.println(stato.scaccoMatto());
    }
}
