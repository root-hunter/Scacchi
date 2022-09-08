package clei.scacchi;

import clei.scacchi.pezzi.*;
import java.util.ArrayList;

public class Stato {
    public Scacchiera scacchiera = new Scacchiera();
    
    boolean giocatoreDiTurno;
    int[] ultimaMossa = {0, 0};

    Stato(boolean giocatoreDiTurno){
        this.giocatoreDiTurno = giocatoreDiTurno;
    }

    public boolean sottoAttacco(int pos, boolean white){
        for(Pezzo pezzo : (white ? scacchiera.pezziBianchi : scacchiera.pezziNeri)){
            //System.out.println(pezzo.toString()+" NEMICO: "+pezzo.listaAttacco(this));
            if(!pezzo.eliminato && pezzo.listaAttacco(this).contains(pos)) return true;
        }

        return false;
    }

    public boolean scacco(){
        if(giocatoreDiTurno){
            return sottoAttacco(scacchiera.get(scacchiera.reBianco), false);
        }else{
            return sottoAttacco(scacchiera.get(scacchiera.reNero), true);
        }
    }

    public boolean scaccoMatto(){
        if(scacco()){
            for(Pezzo pezzo : (giocatoreDiTurno ? scacchiera.pezziBianchi : scacchiera.pezziNeri)){
                if(!pezzo.eliminato){
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
                        scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo 
                            = scacchiera.scacchiera.get(oldX).get(oldY).pezzo;
                        scacchiera.scacchiera.get(oldX).get(oldY).pezzo = null;
    
                        //System.out.println(scacchiera.toString());
    
                        boolean r = !scacco();
                        
                        //RITORNO STATO ORIGINALE
                        scacchiera.scacchiera.get(oldX).get(oldY).pezzo 
                            = scacchiera.scacchiera.get(mossaX).get(mossaY).pezzo;
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

    public boolean stallo(){
        return false;
    }

    //COSTRUTTORE PER DEEP COPY
    Stato(Stato that){
        this.giocatoreDiTurno = that.giocatoreDiTurno;
        this.ultimaMossa[0] = that.ultimaMossa[0];
        this.ultimaMossa[1] = that.ultimaMossa[1];

        this.scacchiera = new Scacchiera(that.scacchiera);
    }

    public Stato simulaSpostamentoOCattura(int from, int to, int promozione){
        Stato stato = new Stato(this);
        Pezzo pezzo = stato.scacchiera.get(from);

        if(
            pezzo != null 
            && ((giocatoreDiTurno && pezzo.white)
                || (!giocatoreDiTurno && !pezzo.white))
            && (pezzo.listaAttacco(stato).contains(to)
                || pezzo.listaSpostamentoPotenziale(stato).contains(to))
                ){
                eseguiMossa(stato, from, to, promozione);
                return stato;
        }

        return null;
    }

    public Stato simulaSpostamentoOCattura(int from, int to){
        return simulaSpostamentoOCattura(from, to, 0);
    }

    public boolean mossaValida(int from, int to, int promozione){
        Stato stato = simulaSpostamentoOCattura(from, to, promozione);
        return !(stato == null || stato.scacco());
    }

    public boolean mossaValida(int from, int to){
        return mossaValida(from, to, 0);
    }

    public boolean eseguiMossa(int from, int to){
        return eseguiMossa(from, to, 0);
    }

    public boolean eseguiMossa(int from, int to, int promozione){
        if(mossaValida(from, to, promozione)){
            eseguiMossa(this, from, to, promozione);
            return true;
        }else{
            return false;
        }
    }

    private void eseguiMossa(Stato stato, int from, int to, int promozione){
        int fromX = Scacchiera.getX(from);
        int fromY = Scacchiera.getY(from);
        Casella casellaFrom = stato.scacchiera.scacchiera.get(fromY).get(fromX);

        int toX = Scacchiera.getX(to);
        int toY = Scacchiera.getY(to);
        Casella casellaTo = stato.scacchiera.scacchiera.get(toY).get(toX);
        
        if(casellaTo.pezzo != null){
            casellaTo.pezzo.elimina();
        }
        casellaTo.pezzo = casellaFrom.pezzo;
        casellaFrom.pezzo = null;

        ultimaMossa[0] = from;
        ultimaMossa[1] = to;
    }

    public String getUltimaMossa(){
        return ultimaMossa[0]+" -> "+ultimaMossa[1];
    }
}
