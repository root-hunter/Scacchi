package clei.scacchi;

import java.util.ArrayList;

import clei.scacchi.pezzi.*;

public class Scacchiera{
    ArrayList<ArrayList<Casella>> scacchiera = new ArrayList<ArrayList<Casella>>();

    Pezzo reBianco;
    ArrayList<Pezzo> pezziBianchi = new ArrayList<>();

    Pezzo reNero;
    ArrayList<Pezzo> pezziNeri = new ArrayList<>();

    public static int MAX = 8;

    public static int getX(int pos){
        return Integer.parseInt(String.valueOf(pos).substring(0, 1)) - 1;
    }

    public static int getY(int pos){
        return Integer.parseInt(String.valueOf(pos).substring(1, 2)) - 1;
    }

    public static int getPos(int x, int y){
        if(x <= 0 || y <= 0){
            return 0;
        }
        return (x * 10) + y;
    }

    void addPezzo(int x, int y, Pezzo pezzo){
        scacchiera.get(x).get(y).pezzo = pezzo;

        if(pezzo.white){
            pezziBianchi.add(pezzo);
        }else{
            pezziNeri.add(pezzo);
        }
    }

    //COSTRUTTORE PER DEEP COPY
    Scacchiera(Scacchiera that){
        initCaselle();

        for(int i = 0; i < MAX; ++i){
            for(int j = 0; j < MAX; ++j){
                Pezzo tmp = that.get(getPos(j + 1, i + 1));

                if(tmp != null){
                    Pezzo tmpCopy = tmp.copy();
                    if(tmp.white){
                        if(tmpCopy instanceof Re){
                            reBianco = tmpCopy;
                        }
                        pezziBianchi.add(tmpCopy);
                    }else{
                        if(tmpCopy instanceof Re){
                            reNero = tmpCopy;
                        }
                        pezziNeri.add(tmpCopy);
                    }
                    
                    scacchiera.get(i).get(j).pezzo = tmpCopy;
                }
            }
        }
    }

    Scacchiera(){
        //INIZIALIZZAZIONE CASELLE E INDICI
        initCaselle();

        //CARICAMENTO PEDONI
        for(int i = 0; i < MAX; ++i){
            addPezzo(1, i, new Pedone(Pezzo.WHITE));
            addPezzo(6, i, new Pedone(Pezzo.BLACK));
        }

        //SCHIERAMENTO TORRI
        addPezzo(0, 0, new Torre(Pezzo.WHITE));
        addPezzo(0, 7, new Torre(Pezzo.WHITE));

        addPezzo(7, 0, new Torre(Pezzo.BLACK));
        addPezzo(7, 7, new Torre(Pezzo.BLACK));

        //SCHIERAMENTO CAVALLI
        addPezzo(0, 1, new Cavallo(Pezzo.WHITE));
        addPezzo(0, 6, new Cavallo(Pezzo.WHITE));

        addPezzo(7, 1, new Cavallo(Pezzo.BLACK));
        addPezzo(7, 6, new Cavallo(Pezzo.BLACK));

        //SCHIERAMENTO ALFIERI
        addPezzo(0, 2, new Alfiere(Pezzo.WHITE));
        addPezzo(0, 5, new Alfiere(Pezzo.WHITE));

        addPezzo(7, 2, new Alfiere(Pezzo.BLACK));
        addPezzo(7, 5, new Alfiere(Pezzo.BLACK));

        //SCHIERAMENTO REGINE
        addPezzo(0, 3, new Regina(Pezzo.WHITE));
        addPezzo(7, 3, new Regina(Pezzo.BLACK));

        //SCHIERAMENTO RE
        reBianco = new Re(Pezzo.WHITE);
        addPezzo(0, 4, reBianco);
        
        reNero = new Re(Pezzo.BLACK);
        addPezzo(7, 4, reNero);
    }

    private void initCaselle() {
        for(int i = 0; i < MAX; ++i){
            ArrayList<Casella> tmp = new ArrayList<Casella>();

            for(int j = 0; j < MAX; ++j){
                tmp.add(new Casella(j + 1, i + 1, null));
            }
            scacchiera.add(tmp);
        }
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
        return pos != 0 && isValid(getX(pos)) && isValid(getY(pos));
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