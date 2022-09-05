package clei.scacchi;

public class Casella {
    public int x = 0;
    public int y = 0;

    public Pezzo pezzo = null;

    @Override
    public String toString() {
        //return pezzo != null ? pezzo.toString()+" " : x+""+y+" ";
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
}
