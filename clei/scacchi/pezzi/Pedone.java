package clei.scacchi.pezzi;

import java.util.ArrayList;

import clei.scacchi.Pezzo;
import clei.scacchi.Scacchiera;
import clei.scacchi.Stato;

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

        int pos = s.scacchiera.get(this);
        
        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        if(white){
            if(Scacchiera.isValid(x + 1) && Scacchiera.isValid(y + 1)){
                int tmpPos = (x + 2)*10 + (y + 2);
                
                if(s.scacchiera.isFree(tmpPos) 
                    && (s.scacchiera.isNotFree(tmpPos) 
                        && s.scacchiera.isEnemy(pos, tmpPos))
                )
                    possibiliAttacchi.add(tmpPos);
            }
    
            if(Scacchiera.isValid(x - 1) && Scacchiera.isValid(y + 1)){
                int tmpPos = x*10 + (y + 2);
                
                if(Scacchiera.isValidPos(tmpPos)
                    && (s.scacchiera.isNotFree(tmpPos) 
                        && s.scacchiera.isEnemy(pos, tmpPos))
                )
                    possibiliAttacchi.add(tmpPos);
            }
        }else{
            if(Scacchiera.isValid(x - 1) && Scacchiera.isValid(y - 1)){
                int tmpPos = x*10 + y;
                
                if(Scacchiera.isValidPos(tmpPos)
                    && (s.scacchiera.isNotFree(tmpPos) 
                        && s.scacchiera.isEnemy(pos, tmpPos))
                )
                    possibiliAttacchi.add(tmpPos);
            }
    
            if(Scacchiera.isValid(x + 1) && Scacchiera.isValid(y - 1)){
                int tmpPos = (x + 2)*10 + y;
                
                if(Scacchiera.isValidPos(tmpPos)
                    && (s.scacchiera.isNotFree(tmpPos) 
                        && s.scacchiera.isEnemy(pos, tmpPos))
                )
                    possibiliAttacchi.add(tmpPos);
            }
        }

        return possibiliAttacchi;
    }

    @Override
    public Pezzo copy() {
        return new Pedone(white);
    }
}
