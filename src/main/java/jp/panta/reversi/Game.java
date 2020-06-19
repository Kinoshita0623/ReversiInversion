package jp.panta.reversi;

import org.jetbrains.annotations.NotNull;

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
    public final Board board = new Board();

    int winner = 0;

    public Game(){
        turn = Board.BLACK;
        board.init();
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

    public @NotNull int[][] settablePositions(){
        return board.settableDiskPositions(turn);
    }

    public boolean isProgressGame(){
        return board.discCount(1) + board.discCount(-1) < Math.pow(board.board.length, 2)
                && winner == 0;
    }

    public void surrender(int player){
        winner = player == Board.BLACK ? Board.WHITE : Board.BLACK;
    }

    public int getTurn() {
        return turn;
    }
}
