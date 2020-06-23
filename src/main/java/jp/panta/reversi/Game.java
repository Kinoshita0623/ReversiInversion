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

    Player whitePlayer;
    Player blackPlayer;

    int winner = 0;

    public Game(Player black, Player white){
        this(8, black, white);
    }

    public Game(int size, Player black, Player white){
        turn = Board.BLACK;
        board = new Board(size);
        blackPlayer = black;
        whitePlayer = white;
        if(black != null){
            black.setDisc(Board.BLACK);
        }
        if(white != null){
            white.setDisc(Board.WHITE);
        }
        board.init();
    }

    public void setDisc(int x, int y){
        setDisc(turn, x, y);
        if(turn == Board.BLACK && blackPlayer != null){
            blackPlayer.onTurned(this);
        }else if(turn == Board.WHITE && whitePlayer != null){
            whitePlayer.onTurned(this);
        }
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
