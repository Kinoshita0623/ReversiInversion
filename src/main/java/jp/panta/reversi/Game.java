package jp.panta.reversi;


import java.util.ArrayList;

/**
 * ゲームの進行を取り仕切るクラス
 */
public class Game {

    private int turn;

    /**
     * ゲームの記録がここに入る
     */
    final ArrayList<int[]> log = new ArrayList<>();
    public final Board board;

    int winner = 0;

    public Game(){
        this(8);
    }

    public Game(int size){
        turn = Board.BLACK;
        board = new Board(size);
        board.init();
    }

    public boolean setDisc(int x, int y){
        return setDisc(turn, x, y);
    }

    public boolean setDisc(int player, int x, int y){
        if(turn != player){
            return false;
        }
        boolean result = board.setDisc(player, x, y);
        if(result) {
            turn = player == Board.BLACK ? Board.WHITE : Board.BLACK;
            log.add( new int[]{ player, x, y} );
            return true;
        }
        return false;
    }

    public int[][] settablePositions(){
        return board.settableDiskPositions(turn);
    }

    public boolean isProgressGame(){
        int blackCount = board.discCount(Board.BLACK);
        int whiteCount = board.discCount(Board.WHITE);
        return blackCount + whiteCount < Math.pow(board.board.length, 2)
                && winner == 0
                && blackCount > 0
                && whiteCount > 0;
    }

    public void surrender(int player){
        winner = player == Board.BLACK ? Board.WHITE : Board.BLACK;
    }

    public int getTurn() {
        return turn;
    }

    public void pass(int player){
        if(turn == player){
            turn = player == Board.BLACK ? Board.WHITE : Board.BLACK;
        }
    }
}
