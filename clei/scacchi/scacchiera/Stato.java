package clei.scacchi.scacchiera;

import java.util.ArrayList;

import clei.scacchi.scacchiera.pezzi.*;
import clei.scacchi.Partita;

public class Stato {
    public Scacchiera scacchiera = new Scacchiera();
    
    public boolean giocatoreDiTurno;

    private int[] ultimaMossa = {0, 0};
 
    public int esito = 0;

    public boolean arroccoBianco = false;
    public boolean arroccoNero = false;

    public int enPassantPos = 0;

    public Partita partita = null;

    public Stato(boolean giocatoreDiTurno, Partita partita){
        this.giocatoreDiTurno = giocatoreDiTurno;
        this.partita = partita;
    }

    //COSTRUTTORE PER DEEP COPY
    public Stato(Stato that){
        this.giocatoreDiTurno = that.giocatoreDiTurno;
        this.ultimaMossa[0] = that.ultimaMossa[0];
        this.ultimaMossa[1] = that.ultimaMossa[1];
        this.esito = that.esito;

        this.arroccoBianco = that.arroccoBianco;
        this.arroccoNero = that.arroccoNero;

        this.enPassantPos = that.enPassantPos;

        this.partita = that.partita;

        this.scacchiera = new Scacchiera(that.scacchiera);
    }

    public boolean checkArrocco(boolean white, int posTorre, int[] posCase){
        int posRe = white ? 51 : 58;
        
        if(scacchiera.isFree(posRe) 
        || scacchiera.reBianco.mosso 
        || scacchiera.get(scacchiera.reBianco) != posRe
        || scacchiera.isFree(posTorre)
        || !(scacchiera.get(posTorre) instanceof Torre)
        || scacchiera.get(posTorre).mosso) return false;


        for(int p : posCase){
            if(scacchiera.get(p) != null) return false;
        }

        return true;
    }

    public boolean checkEnPassant(){
        return enPassantPos != 0;
    }

    public boolean checkArroccoBiancoSx(){
        int[] posCase = {21, 31, 41};
        return checkArrocco(true, 11, posCase);
    }

    public boolean checkArroccoBiancoDx(){
        int[] posCase = {61, 71};
        return checkArrocco(true, 81, posCase);
    }

    public boolean checkArroccoNeroSx(){
        int[] posCase = {28, 38, 48};
        return checkArrocco(false, 18, posCase);
    }
    
    public boolean checkArroccoNeroDx(){
        int[] posCase = {68, 78};
        return checkArrocco(false, 88, posCase);
    }

    public boolean sottoAttacco(int pos, boolean white){
        for(Pezzo pezzo : (white ? scacchiera.pezziBianchi : scacchiera.pezziNeri)){
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
                        Stato tmp = simulaSpostamentoOCattura(scacchiera.get(pezzo), mossa);
                        if(tmp != null && !tmp.scacco()) return false;
                    }
                    System.out.println(pezzo.toString()+": " +possibiliMosse);
                }
            }
            return true;
        }
        return false;
    }

    public boolean stallo(){
        int k = 0;

        //CONTROLLO PER LA REGOLA DELLA TRIPLICE RIPETIZIONE
        for(Stato stato : partita.mosse){
            boolean flag = true;

            for(int i = 0; i < Scacchiera.MAX; ++i){
                for(int j = 0; j < Scacchiera.MAX; ++j){
                    Pezzo pezzoA = stato.scacchiera.getCasella(i, j).pezzo;
                    Pezzo pezzoB = scacchiera.getCasella(i, j).pezzo;
                    
                    if(pezzoA != null && pezzoB != null){
                        if(pezzoA.label != pezzoB.label 
                            || pezzoA.white != pezzoB.white){
                                flag = false;
                            }
                    }else{
                        if(pezzoA != null || pezzoB != null){
                            flag = false;
                        }
                    }
                }
                if(!flag) break;
            }

            if(flag) ++k;
            if(k == 3) return true;
        }


        for(Pezzo pezzo : (giocatoreDiTurno ? scacchiera.pezziBianchi : scacchiera.pezziNeri)){
            if(
                !pezzo.eliminato 
                && (!pezzo.listaAttacco(this).isEmpty()
                    || !pezzo.listaSpostamentoPotenziale(this).isEmpty()
                    || !scacco()
                )
            ) return false;
        }
        return true;   
     }

    public Stato simulaSpostamentoOCattura(int from, int to, int promozione){
        Stato stato = new Stato(this);
        Pezzo pezzo = stato.scacchiera.get(from);

        System.out.println(pezzo.listaAttacco(stato));
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
        stato.enPassantPos = 0;

        Casella casellaFrom = stato.scacchiera
            .scacchiera.get(Scacchiera.getY(from)).get(Scacchiera.getX(from));

        Casella casellaTo = stato.scacchiera
            .scacchiera.get(Scacchiera.getY(to)).get(Scacchiera.getX(to));

        if(casellaTo.pezzo != null){
            casellaTo.pezzo.elimina();
        }
        
        if( 
            casellaFrom.pezzo != null
            && casellaFrom.pezzo instanceof Pedone
        ){
            Pedone pedone = (Pedone)casellaFrom.pezzo;

            if(
                (stato.giocatoreDiTurno && (to - from) == 2)
                || (!stato.giocatoreDiTurno && (from - to) == 2)
            ){
                stato.enPassantPos = to;
            }
            
            if(!stato.partita.mosse.isEmpty()){
                Stato vecchioStato = stato.partita.mosse.get(stato.partita.mosse.size() - 1);
    
                if(vecchioStato.checkEnPassant()){
                    int tmpEnPassantPos = giocatoreDiTurno 
                            ? vecchioStato.enPassantPos + 1 
                            : vecchioStato.enPassantPos - 1;
                    
                    if(to == tmpEnPassantPos){
                        int x = Scacchiera.getX(vecchioStato.enPassantPos);
                        int y = Scacchiera.getY(vecchioStato.enPassantPos);

                        stato.scacchiera.getCasella(x, y).pezzo.elimina();
                        stato.scacchiera.getCasella(x, y).pezzo = null;
                    }
                                    
                }
            }

            if((stato.giocatoreDiTurno 
            ? stato.scacchiera.casePromozioneBianco.contains(casellaTo)
             : stato.scacchiera.casePromozioneNero.contains(casellaTo))
             ){
    
                    stato.scacchiera.pezziBianchi.remove(pedone);
                    casellaTo.pezzo = pedone.promozione(promozione);
    
                    stato.scacchiera.pezziBianchi.add(casellaTo.pezzo);
                    casellaFrom.pezzo = null;
            }else{
                switchCasella(casellaFrom, casellaTo);
            }
        }else if( 
            casellaFrom.pezzo != null
            && casellaFrom.pezzo instanceof Re
        ){
            if(casellaFrom.pezzo.white && from == 51){
                if(to == 71 && checkArroccoBiancoDx()){
                    switchCasellaArrocco(stato, true, 7, 0, 5, 0);
                }else if(to == 31 && checkArroccoBiancoSx()){
                    switchCasellaArrocco(stato, true, 0, 0, 2, 0);
                }
            }else if(!casellaFrom.pezzo.white && from == 58){
                if(to == 78 && checkArroccoNeroDx()){
                    switchCasellaArrocco(stato, false, 7, 7, 5, 7);
                }else if(to == 38 && checkArroccoNeroSx()){
                    switchCasellaArrocco(stato, false, 0, 7, 2, 7);
                }
            }
            switchCasella(casellaFrom, casellaTo);
        } else {
            switchCasella(casellaFrom, casellaTo);
        }
       
        ultimaMossa[0] = from;
        ultimaMossa[1] = to;
    }

    private void switchCasellaArrocco(Stato stato, boolean white, int x1, int y1, int x2, int y2) {
        Casella c1 = stato.scacchiera.getCasella(x1, y1);
        Casella c2 = stato.scacchiera.getCasella(x2, y2);
        
        switchCasella(c1, c2);
        if(white)
            stato.arroccoBianco = true;
        else
           stato.arroccoNero = true;
    }

    private void switchCasella(Casella casellaFrom, Casella casellaTo) {
        casellaTo.pezzo = casellaFrom.pezzo;
        casellaTo.pezzo.mosso = true;
        casellaFrom.pezzo = null;
    }

    public String getUltimaMossa(){
        return ultimaMossa[0]+" -> "+ultimaMossa[1];
    }
}
