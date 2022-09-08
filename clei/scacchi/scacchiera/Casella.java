package clei.scacchi.scacchiera;

import clei.scacchi.scacchiera.pezzi.Pezzo;

public class Casella {
    public int x = 0;
    public int y = 0;

    public Pezzo pezzo = null;

    @Override
    public String toString() {
        return pezzo != null ? pezzo.toString()+" " : ". ";
    }

    int getPos(){
        return Scacchiera.getPos(x, y);
    }


    Casella(int x, int y, Pezzo pezzo){
        this.x = x;
        this.y = y;

        this.pezzo = pezzo;
    }

    Casella(Casella that){
        this.x = that.x;
        this.y = that.y;

        this.pezzo = that.pezzo != null ? that.pezzo.copy() : null;
    }
}
