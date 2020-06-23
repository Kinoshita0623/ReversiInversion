package jp.panta.reversi;

public abstract class Player {

    private int disc;

    public abstract void onTurned(Game game);

    public abstract void onSettlement(int winner);

    public void setDisc(int disc){
        this.disc = disc;
    }

    final public int getDiscType(){
        return this.disc;
    }

}
