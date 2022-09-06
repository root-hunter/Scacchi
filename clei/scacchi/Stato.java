package clei.scacchi;

import clei.scacchi.pezzi.*;
import java.util.ArrayList;

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

    public boolean sottoAttacco(int pos, boolean white){
        for(Pezzo pezzo : (white ? scacchiera.pezziBianchi : scacchiera.pezziNeri)){
            System.out.println(pezzo.toString()+" NEMICO: "+pezzo.listaAttacco(this));
            if(pezzo.listaAttacco(this).contains(pos)) return true;
        }

        return false;
    }

    public boolean scacco(){
        if(giocatoreDiTurno.equals(bianco)){
            return sottoAttacco(scacchiera.get(scacchiera.reBianco), false);
        }else{
            return sottoAttacco(scacchiera.get(scacchiera.reNero), true);
        }
    }

    public boolean scaccoMatto(){
        if(scacco()){
            for(Pezzo pezzo : (giocatoreDiTurno.equals(bianco) ? scacchiera.pezziBianchi : scacchiera.pezziNeri)){
                if(!(pezzo instanceof Re)){
                    ArrayList<Integer> possibiliMosse = pezzo.listaAttacco(this);
                    
                    for(int mossa : possibiliMosse){
                        int mossaX = Scacchiera.getY(mossa);
                        int mossaY = Scacchiera.getX(mossa);

                        int oldX = Scacchiera.getY(scacchiera.get(pezzo));
                        int oldY = Scacchiera.getX(scacchiera.get(pezzo));

                        Pezzo tmpMossa = scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo;
                        scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo = scacchiera.scacchiera.get(oldX).get(oldY).pezzo;
                        scacchiera.scacchiera.get(oldX).get(oldY).pezzo = null;

                        System.out.println(scacchiera.toString());

                        boolean r = !scacco();

                        scacchiera.scacchiera.get(oldX).get(oldY).pezzo = scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo;
                        scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo = tmpMossa;

                        if(r) return false;

                        
                    }
                    System.out.println(pezzo.toString()+": " +possibiliMosse);

                }
               
            }
            return true;
        }
        return false;
    }
}
