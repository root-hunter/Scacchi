package clei.scacchi.pezzi;

import java.util.ArrayList;

import clei.scacchi.Pezzo;
import clei.scacchi.Scacchiera;
import clei.scacchi.Stato;

public class Torre extends Pezzo{
    public Torre(boolean white) {
        super("T", white);
    }

    public boolean spostamentoPotenziale(Stato s, int target){
        return listaSpostamentoPotenziale(s).contains(target);
    }

    public ArrayList<Integer> listaSpostamentoPotenziale (Stato s){
        ArrayList<Integer> possibiliCase = new ArrayList<>();

        int pos = s.scacchiera.get(this);
        
        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        //POSSIBILE ESECUZIONE IN PARALLELO (MULTI THREAD)

        //DX
        for(int i = x + 2; i <= Scacchiera.MAX; ++i){
            if(s.scacchiera.isFree(Scacchiera.getPos(i, y + 1))){
                possibiliCase.add(Scacchiera.getPos(i, y + 1));
            }else{
                break;
            }
        }

        //SX
        for(int i = x; i > 0; --i){
            if(s.scacchiera.isFree(Scacchiera.getPos(i, y + 1))){
                possibiliCase.add(Scacchiera.getPos(i, y + 1));
            }else{
                break;
            }
        }
        
        //UP
        for(int i = y + 2; i <= Scacchiera.MAX; ++i){
            if(s.scacchiera.isFree(Scacchiera.getPos(x + 1, i))){
                possibiliCase.add(Scacchiera.getPos(x + 1, i));
            }else{
                break;
            }
        }
        
        //DOWN
        for(int i = y; i > 0; --i){
            if(s.scacchiera.isFree(Scacchiera.getPos(x + 1, i))){
                possibiliCase.add(Scacchiera.getPos(x + 1, i));
            }else{
                break;
            }
        }
        
        return possibiliCase;
    }

    public boolean attacco(Stato s, int target){
        return false;
    }

    public ArrayList<Integer> listaAttacco(Stato s){
        ArrayList<Integer> possibiliAttacchi = new ArrayList<>();

        int pos = s.scacchiera.get(this);

        int x = Scacchiera.getX(pos);
        int y = Scacchiera.getY(pos);

        //POSSIBILE ESECUZIONE IN PARALLELO (MULTI THREAD)

        //DX
        for(int i = x + 2; i <= Scacchiera.MAX; ++i){
            int tmpPos = Scacchiera.getPos(i, y + 1);

            if(s.scacchiera.isFree(tmpPos)){
                possibiliAttacchi.add(tmpPos);
            }else{
                if(s.scacchiera.isNotFree(tmpPos) 
                    && s.scacchiera.isEnemy(pos, tmpPos)){
                    possibiliAttacchi.add(tmpPos);
                }
                break;
            }
        }

        //SX
        for(int i = x; i > 0; --i){
            int tmpPos = Scacchiera.getPos(i, y + 1);

            if(s.scacchiera.isFree(tmpPos)){
                possibiliAttacchi.add(tmpPos);
            }else{
                if(s.scacchiera.isNotFree(tmpPos)
                    && s.scacchiera.isEnemy(pos, tmpPos)){
                    possibiliAttacchi.add(tmpPos);
                }
                break;
            }
        }
        
        //UP
        for(int i = y + 2; i <= Scacchiera.MAX; ++i){
            int tmpPos = Scacchiera.getPos(x + 1, i);

            if(s.scacchiera.isFree(tmpPos)){
                possibiliAttacchi.add(tmpPos);
            }else{
                if(s.scacchiera.isNotFree(tmpPos)
                    && s.scacchiera.isEnemy(pos, tmpPos)){
                    possibiliAttacchi.add(tmpPos);
                }
                break;
            }
        }
        
        //DOWN
        for(int i = y; i > 0; --i){
            int tmpPos = Scacchiera.getPos(x + 1, i);

            if(s.scacchiera.isFree(tmpPos)){
                possibiliAttacchi.add(tmpPos);
            }else{
                if(s.scacchiera.isNotFree(tmpPos)
                    && s.scacchiera.isEnemy(pos, tmpPos)){
                    possibiliAttacchi.add(tmpPos);
                }
                break;
            }
        }
        
        return possibiliAttacchi;  
      }
}
