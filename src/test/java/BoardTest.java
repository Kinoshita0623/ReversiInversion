import jp.panta.reversi.Board;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class BoardTest {

    @Test
    public void boardSetDiscTest(){

        Board board = new Board();
        board.init();
        Board.displayBoard(board);
        int[] opposite = board.oppositeDiscY(Board.WHITE, 4, 2, false);
        System.out.println(Arrays.toString(opposite));
        Assert.assertNotEquals(opposite, null);

        int[] opposite2 = board.oppositeDiscX(Board.WHITE, 5, 3, false);
        System.out.println(Arrays.toString(opposite2));
        Assert.assertArrayEquals(opposite2, new int[]{3, 3});

        boolean result = board.setDisc(Board.WHITE, 5, 3);
        board.setDisc(Board.WHITE, 4, 2, new int[]{4, 4});
        //board.board[2][4] = Board.WHITE;
        //System.out.println(result);
        Board.displayBoard(board);

        board.setDisc(Board.BLACK, 3, 2, new int[]{3, 4});
        Board.displayBoard(board);

        // 斜めの挙動がおかしい
        board.setDisc(Board.WHITE, 2, 1, new int[]{ 4, 3 });
        Board.displayBoard(board);
    }

    @Test
    public void normalSetDiscTest(){
        Board board = new Board();
        board.init();
        Board.displayBoard(board);
        System.out.println();


        board.setDisc(Board.WHITE, 4, 2);
        Board.displayBoard(board);
        System.out.println();

        board.setDisc(Board.BLACK, 3, 2);
        Board.displayBoard(board);
        System.out.println();


        board.setDisc(Board.WHITE, 2, 1);
        Board.displayBoard(board);
        System.out.println();

        board.setDisc(Board.BLACK, 3, 1);
        Board.displayBoard(board);
        System.out.println();

        board.setDisc(Board.WHITE, 4, 1);
        Board.displayBoard(board);
        System.out.println();

        //board.oppositeDiscX(Board.WHITE, 3, 0, true);
        //board.oppositeDiscX(Board.WHITE, 3, 0, false);
        int[] o = board.oppositeDiagonal(Board.WHITE, 3, 0, false, true);

        System.out.println(String.format("opponent:%s", Arrays.toString(o)));

        Assert.assertNull(null, o);
        board.setDisc(Board.WHITE, 3, 0);
        board.setDisc(Board.BLACK, 0, 0);

        Board.displayBoard(board);

        int[][] settable = board.settableDiskPositions(Board.BLACK);
        for(int[] element : settable){
            if(element == null){
                break;
            }
            System.out.println(String.format("x:%d, y:%d", element[0], element[1]));
        }

        int[] opponent2 = board.oppositeDiagonal(Board.BLACK, 3, 2, true, true);
        Assert.assertNull(opponent2);

    }
}
