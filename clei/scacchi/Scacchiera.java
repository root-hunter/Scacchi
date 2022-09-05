package clei.scacchi;

import java.util.ArrayList;

import clei.scacchi.pezzi.Colors;
import clei.scacchi.pezzi.*;

public class Scacchiera{
    ArrayList<ArrayList<Casella>> scacchiera = new ArrayList<ArrayList<Casella>>();
    
    public static int MAX = 8;

    public static int getX(int pos){
        return Integer.parseInt(String.valueOf(pos).substring(0, 1)) - 1;
    }

    public static int getY(int pos){
        return Integer.parseInt(String.valueOf(pos).substring(1, 2)) - 1;
    }

    public static int getPos(int x, int y){
        return (x * 10) + y;
    }

    Scacchiera(){
        //INIZIALIZZAZIONE CASELLE E INDICI
        for(int i = 0; i < MAX; ++i){
            ArrayList<Casella> tmp = new ArrayList<Casella>();

            for(int j = 0; j < MAX; ++j){
                tmp.add(new Casella(j + 1, i + 1, null));
            }
            scacchiera.add(tmp);
        }

        //CARICAMENTO PEDONI
        for(int i = 0; i < MAX; ++i){
            for(int j = 0; j < MAX; ++j){
                scacchiera.get(1).get(j).pezzo = new Pedone(Colors.WHITE);
                scacchiera.get(6).get(j).pezzo = new Pedone(Colors.BLACK);
            }
        }

        //SCHIERAMENTO TORRI
        scacchiera.get(0).get(0).pezzo = new Torre(Colors.WHITE);
        scacchiera.get(0).get(7).pezzo = new Torre(Colors.WHITE);

        scacchiera.get(7).get(0).pezzo = new Torre(Colors.BLACK);
        scacchiera.get(7).get(7).pezzo = new Torre(Colors.BLACK);
        
        //SCHIERAMENTO CAVALLI
        scacchiera.get(0).get(1).pezzo = new Cavallo(Colors.WHITE);
        scacchiera.get(0).get(6).pezzo = new Cavallo(Colors.WHITE);

        scacchiera.get(7).get(1).pezzo = new Cavallo(Colors.BLACK);
        scacchiera.get(7).get(6).pezzo = new Cavallo(Colors.BLACK);

        //SCHIERAMENTO ALFIERI
        scacchiera.get(0).get(2).pezzo = new Alfiere(Colors.WHITE);
        scacchiera.get(0).get(5).pezzo = new Alfiere(Colors.WHITE);

        scacchiera.get(7).get(2).pezzo = new Alfiere(Colors.BLACK);
        scacchiera.get(7).get(5).pezzo = new Alfiere(Colors.BLACK);

        //SCHIERAMENTO RE e REGINE
        scacchiera.get(0).get(4).pezzo = new Re(Colors.WHITE);
        scacchiera.get(7).get(4).pezzo = new Re(Colors.BLACK);

        scacchiera.get(0).get(3).pezzo = new Regina(Colors.WHITE);
        scacchiera.get(7).get(3).pezzo = new Regina(Colors.BLACK);
    }

    @Override
    public String toString() {
        String buffer = "";

        for(int i = MAX - 1; i >= 0; --i){
            ArrayList<Casella> tmp = scacchiera.get(i);
            for(int j = 0; j < 8; ++j){
                buffer += tmp.get(j).toString();
            }
            buffer += "\n";
        }
        return buffer;
    }

    public Pezzo get(int pos){

        return scacchiera
            .get(getY(pos))
            .get(getX(pos))
            .pezzo;
    }

    public int get(Pezzo p){
        for(int i = 0; i < MAX; ++i){
            ArrayList<Casella> tmp = scacchiera.get(i);
            for(int j = 0; j < MAX; ++j){
                if(tmp.get(j).pezzo != null 
                    && tmp.get(j).pezzo.equals(p)){
                    return tmp.get(j).getPos();
                }
            }
        }
        return 0;
    }

    public static boolean isValid(int x){
        return x >= 0 && x < MAX;
    }

    public static boolean isValidPos(int pos){
        return isValid(getX(pos)) && isValid(getY(pos));
    }

    public boolean isFree(int pos){
        return isValidPos(pos) && get(pos) == null;
    }

    public boolean isNotFree(int pos){
        return !isFree(pos) && get(pos) != null;
    }

    public boolean isEnemy(int ourPos, int enemyPos){
        return get(ourPos).white != get(enemyPos).white;
    }
}