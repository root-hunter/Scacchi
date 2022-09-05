package clei.scacchi.pezzi;

import java.util.ArrayList;

import clei.scacchi.Pezzo;
import clei.scacchi.Scacchiera;
import clei.scacchi.Stato;

public class Pedone extends Pezzo{

    public Pedone(boolean white) {
        super("P", white);
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

        int pos = s.scacchiera.get(this);
        
        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        if(white){
            if(Scacchiera.isValid(x + 1) && Scacchiera.isValid(y + 1)){
                int attDx = (x + 2)*10 + (y + 2);
                
                if(s.scacchiera.isFree(attDx) 
                    || (s.scacchiera.isNotFree(attDx) 
                        && s.scacchiera.isEnemy(pos, attDx))
                )
                    possibiliAttacchi.add(attDx);
            }
    
            if(Scacchiera.isValid(x - 1) && Scacchiera.isValid(y + 1)){
                int attSx = x*10 + (y + 2);
                
                if(Scacchiera.isValidPos(attSx)
                    || (s.scacchiera.isNotFree(attSx) 
                        && s.scacchiera.isEnemy(pos, attSx))
                )
                    possibiliAttacchi.add(attSx);
            }
        }else{
            if(Scacchiera.isValid(x - 1) && Scacchiera.isValid(y - 1)){
                int attDx = x*10 + y;
                
                if(Scacchiera.isValidPos(attDx)
                    || (s.scacchiera.isNotFree(attDx) 
                        && s.scacchiera.isEnemy(pos, attDx))
                )
                    possibiliAttacchi.add(attDx);
            }
    
            if(Scacchiera.isValid(x + 1) && Scacchiera.isValid(y - 1)){
                int attSx = (x + 2)*10 + y;
                
                if(Scacchiera.isValidPos(attSx)
                    || (s.scacchiera.isNotFree(attSx) 
                        && s.scacchiera.isEnemy(pos, attSx))
                )
                    possibiliAttacchi.add(attSx);
            }
        }

        return possibiliAttacchi;
    }
}
