import java.util.Arrays;

public class Main {

    public static void main(String[] args){
        Board board = new Board();
        board.init();
        displayBoard(board.board);
        board.setDisc(Board.WHITE, 3, 2);
        System.out.println();
        displayBoard(board.board);
        for(int i = 0; i < board.board.length; i ++){
            for(int j = 0; j < board.board[i].length; j ++){
                board.setDisc(Board.BLACK, j, i);
            }
        }
        System.out.println();
        displayBoard(board.board);


    }

    public static void displayBoard(int[][] board){
        for(int[] w: board){
            System.out.println(Arrays.toString(w));
        }
    }
}
