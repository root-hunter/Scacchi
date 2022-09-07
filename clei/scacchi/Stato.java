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
                ArrayList<Integer> possibiliMosse = pezzo.listaAttacco(this);
                    
                //I PEDONI SI MUOVONO IN MODO DIVERSO RISPETTO ALL'ATTACCANO
                if(pezzo instanceof Pedone){
                    for(int mossa : pezzo.listaSpostamentoPotenziale(this)){
                        possibiliMosse.add(mossa);
                    }
                }
                
                for(int mossa : possibiliMosse){
                    int mossaX = Scacchiera.getY(mossa);
                    int mossaY = Scacchiera.getX(mossa);

                    int oldX = Scacchiera.getY(scacchiera.get(pezzo));
                    int oldY = Scacchiera.getX(scacchiera.get(pezzo));
                    
                    //SIMULAZIONE MOSSA
                    Pezzo tmpMossa = scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo;
                    scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo = scacchiera.scacchiera.get(oldX).get(oldY).pezzo;
                    scacchiera.scacchiera.get(oldX).get(oldY).pezzo = null;

                    System.out.println(scacchiera.toString());

                    boolean r = !scacco();
                    
                    //RITORNO STATO ORIGINALE
                    scacchiera.scacchiera.get(oldX).get(oldY).pezzo = scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo;
                    scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo = tmpMossa;

                    if(r) return false;
                }
                System.out.println(pezzo.toString()+": " +possibiliMosse);
            }
            return true;
        }
        return false;
    }

    public boolean stallo(){
        return false;
    }

    //COSTRUTTORE PER DEEP COPY
    Stato(Stato that){
        this.bianco = that.bianco;
        this.nero = that.nero;

        this.giocatoreDiTurno = that.giocatoreDiTurno;

        this.scacchiera = new Scacchiera(that.scacchiera);
    }

    public Stato simulaSpostamentoOCattura(int from, int to, int promozione){
        Stato stato = new Stato(this);
        Pezzo pezzo = stato.scacchiera.get(from);

        if(pezzo != null 
            && ((giocatoreDiTurno.equals(bianco) && pezzo.white)
                || (giocatoreDiTurno.equals(nero) && !pezzo.white))){
            if(pezzo.listaAttacco(stato).contains(to)
                || pezzo.listaSpostamentoPotenziale(stato).contains(to)){
                    int fromX = Scacchiera.getX(from);
                    int fromY = Scacchiera.getY(from);

                    int toX = Scacchiera.getX(to);
                    int toY = Scacchiera.getY(to);

                    stato.scacchiera.scacchiera.get(toY).get(toX).pezzo 
                        = stato.scacchiera.scacchiera.get(fromY).get(fromX).pezzo;
                    stato.scacchiera.scacchiera.get(fromY).get(fromX).pezzo = null;

                    return stato;
                }
        }

        return null;
    }

    public Stato simulaSpostamentoOCattura(int from, int to){
        return simulaSpostamentoOCattura(from, to, 0);
    }
}
