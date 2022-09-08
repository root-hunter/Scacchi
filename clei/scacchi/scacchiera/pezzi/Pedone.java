package clei.scacchi.scacchiera.pezzi;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import clei.scacchi.scacchiera.Scacchiera;
import clei.scacchi.scacchiera.Stato;

public class Pedone extends Pezzo{

    public Pedone(boolean white) {
        super(LABEL_PEDONE, white);
    }

    public boolean spostamentoPotenziale(Stato s, int target){
        return listaSpostamentoPotenziale(s).contains(target);
    }

    public ArrayList<Integer> listaSpostamentoPotenziale (Stato s){
        ArrayList<Integer> possibiliCase = new ArrayList<>();

        int pos = s.scacchiera.get(this);
        
        int x = Scacchiera.getX(pos) + 1;
        int y = Scacchiera.getY(pos) + 1;

        if(y < Scacchiera.MAX){
            if(white && y < Scacchiera.MAX){
                if(y == 2){
                    if(s.scacchiera.isFree(Scacchiera.getPos(x, y + 2))) 
                        possibiliCase.add(Scacchiera.getPos(x, y + 2));    
                }
    
                if(s.scacchiera.isFree(Scacchiera.getPos(x, y + 1))) 
                    possibiliCase.add(Scacchiera.getPos(x, y + 1));
            }else{
                if(y == 7){
                    if(s.scacchiera.isFree(Scacchiera.getPos(x, y - 2))) 
                        possibiliCase.add(Scacchiera.getPos(x, y - 2));
                }
                if(s.scacchiera.isFree(Scacchiera.getPos(x, y - 1))) 
                    possibiliCase.add(Scacchiera.getPos(x, y - 1));
            }
        }

        return possibiliCase;
    }

    public boolean attacco(Stato s, int target){
        return listaAttacco(s).contains(target);
    }

    public ArrayList<Integer> listaAttacco(Stato s){
        ArrayList<Integer> possibiliAttacchi = new ArrayList<>();
        ArrayList<Integer> tmp = new ArrayList<>();

        int pos = s.scacchiera.get(this);
        
        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        //NW
        controlloDiagonale(s, true, tmp, false, true, x, y + 2, true);

        //NE
        controlloDiagonale(s, true, tmp, true, true, x + 2, y + 2, true);

        for(int i = 0; i < tmp.size(); ++i){
            int p = tmp.get(i);
            if(s.scacchiera.get(p) != null && s.scacchiera.get(p).white != white){
                possibiliAttacchi.add(p);
            }
        }

        //EN PASSANT
        if(!s.partita.mosse.isEmpty()){
            Stato vecchioStato = s.partita.mosse.get(s.partita.mosse.size() - 1);

            if(vecchioStato.checkEnPassant()){
                System.out.println("_____________________________");
                System.out.println(pos + 10);
                System.out.println(pos - 10);
                System.out.println(x);
                System.out.println(y);
                System.out.println(Scacchiera.getPos(x, y));
                System.out.println(vecchioStato.enPassantPos);

                System.out.println("_____________________________");
                


                if(white){
                    if(pos + 10 == vecchioStato.enPassantPos){
                        possibiliAttacchi.add(Scacchiera.getPos(x + 2, y + 2));
                    }else if(pos - 10 == vecchioStato.enPassantPos){
                        possibiliAttacchi.add(Scacchiera.getPos(x, y + 2));
                    }
                }else{
                    if(pos + 10 == vecchioStato.enPassantPos){
                        possibiliAttacchi.add(Scacchiera.getPos(x + 2, y));
                    }else if(pos - 10 == vecchioStato.enPassantPos){
                        possibiliAttacchi.add(Scacchiera.getPos(x, y));
                    }
                }
            }
        }
        

        return possibiliAttacchi;
    }

    @Override
    public Pezzo copy() {
        return new Pedone(white);
    }

    public Pezzo promozione(int promozione){
        switch(promozione){
            case 0:
                return new Regina(white);
            case 1:
                return new Cavallo(white);
            case 2:
                return new Alfiere(white);
            case 3:
                return new Torre(white);
            default:
                return null;
        }
    }
}
