package jp.panta.reversi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    public static final int WHITE = 1;
    public static final int BLACK = -1;

    /**
     * 縦軸に
     * 横横横横横横横横配置をする
     */
    final int[][] board;

    public Board(int size) throws IllegalArgumentException{
        if((size & 1) == 1){
            throw new IllegalArgumentException("偶数を指定してください");
        }
        if(size <= 1){
            throw new IllegalArgumentException("適切な数値を指定してください");
        }
        board = new int[size][size];
    }

    public Board(){
        this(8);
    }

    public void init(){
        int size = board.length;

        int[] topW = board[size / 2 - 1];
        int[] bottomW = board[size / 2];

        for(int[] widthArray : board){
            Arrays.fill(widthArray, 0);
        }
        topW[ size / 2 - 1] = WHITE;
        topW[ size / 2] = BLACK;
        bottomW[ size / 2 - 1 ] = BLACK;
        bottomW[ size / 2 ] = WHITE;
    }

    public boolean setDisc(int player, int x, int y){

        // y軸判定
        int[] top = oppositeDiscY(player, x, y, true);
        int[] bottom = oppositeDiscY(player, x, y, false);

        // x軸判定
        int[] right = oppositeDiscX(player, x, y, true);

        int[] left = oppositeDiscX(player, x, y, false);


        // 斜め判定
        // 斜め左上
        int[] leftTop = oppositeDiagonal(player, x, y, true, false);

        // 斜め右上
        int[] rightTop = oppositeDiagonal(player, x, y, true, true);

        // 斜め左下
        int[] leftBottom = oppositeDiagonal(player, x, y, false, false);

        //斜め右下
        int[] rightBottom = oppositeDiagonal(player, x, y, false, true);

        int[][] array = {top, bottom, right, left, leftTop, rightTop, leftBottom, rightBottom};

        boolean isSuccess = false;
        for( int[] opponent : array ){
            if(opponent != null){
                isSuccess = true;
                setDisc(player, x, y, opponent);
            }
        }
        return isSuccess;
    }

    public void setDisc(final int player, final int xDisc, final int yDisc, @NotNull final int[] opposite){
        if( opposite == null )
            return;

        int x = opposite[0];
        int y = opposite[1];

        int xIncrementSize = Integer.compare(x - xDisc, 0);
        int yIncrementSize = Integer.compare(y - yDisc, 0);

        int xIndex = xDisc;
        int yIndex = yDisc;
        while(
                (yIndex != y || xIndex != x)
                        && yIndex >= 0
                        && yIndex < board.length
                        && xIndex >= 0
                        && xIndex < board[yIndex].length
        ){
            //System.out.println(String.format("xIndex:%d, yIndex:%d", xIndex, yIndex));
            board[yIndex][xIndex] = player;

            xIndex += xIncrementSize;
            yIndex += yIncrementSize;
        }


    }

    /*public boolean detection(int player, int xDisc, int yDisc, int xIncreaseValue, int yIncreaseValue){

        return oppositeDiscXY(player, xDisc, yDisc, xIncreaseValue, yIncreaseValue) != null;
    }*/

    /**
     * 相手のコマを挟み挟み込む現在のコマの反対側のコマの座標を返す
     * 縦軸の検証を担当する。
     * @param player 1, -1の現在のプレイヤー
     * @param xDisc 配置しようとしているコマのx座標
     * @param yDisc 配置しようとしているコマy座標
     * @param isRightTo 上に向かって走査する場合は true
     * @return 有効なコマが対照的に存在していればその座標を{ x, y }で返し、無効であれば nullを返す
     */
    public @Nullable int[] oppositeDiscX(int player, int xDisc, int yDisc,  boolean isRightTo){
        int increment = isRightTo ? 1 : -1;
        return oppositeDiagonal(player, xDisc, yDisc, increment, 0);
    }

    /**
     * 相手のコマを挟み挟み込む現在のコマの反対側のコマの座標を返す
     * 横の検証を担当する。
     * @param player 1, -1の現在のプレイヤー
     * @param xDisc 配置しようとしているコマのx座標
     * @param yDisc 配置しようとしているコマy座標
     * @param isUpTo 右側に向かって走査するのであればtrue、左側に向かって操作するのであればfalse
     * @return 有効なコマが対照的に存在していればその座標を{ x, y }で返し、無効であれば nullを返す
     */
    public @Nullable int[] oppositeDiscY(final int player, final int xDisc, final int yDisc, final boolean isUpTo){
        final int increment = isUpTo ? 1 : -1;
        return oppositeDiagonal(player, xDisc, yDisc, 0, increment);
    }

    /**
     * 相手のコマを挟み込む現在のコマの斜め方向に反対側のコマの座標を返す
     * @param player 1, -1の現在のプレイヤー
     * @param xDisc 配置しようとしているコマのx座標
     * @param yDisc 配置しようとしているコマのY座標
     * @param isUpTo 斜め上に走査しようとしている場合はtrue
     * @param isRightTo 斜め右に走査しようとしている場合はtrue
     * @return 有効なコマが対照的に存在していれば、その座標を{ x, y }で返し、無効であればnullを返す
     */
    public @Nullable int[] oppositeDiagonal(final int player, final int xDisc, final int yDisc, final boolean isUpTo, final boolean isRightTo){
        int opposite = player == WHITE ? BLACK : WHITE;

        int xIncrement = isRightTo ? 1 : -1;
        int yIncrement = isUpTo ? -1 : 1;
        return oppositeDiagonal(player, xDisc, yDisc, xIncrement, yIncrement);
    }

    @Nullable int[] oppositeDiagonal(final int player, final int xDisc, final int yDisc,  final int xIncrement, final int yIncrement){
        int opposite = player == WHITE ? BLACK : WHITE;

        int yIndex = yDisc;
        int xIndex = xDisc;
        //System.out.println(String.format("xDisc:%d, yDisc:%d, xIncrement:%d, yIncrement:%d", xDisc, yDisc, xIncrement, yIncrement));
        if(board[yIndex][xIndex] != 0){
            return null;
        }
        while(
                yIndex >= 0
                        && yIndex < board.length
                        && xIndex >= 0
                        && xIndex < board[yIndex].length
        ){
            //System.out.println(String.format("x:%d, y:%d", xIndex, yIndex));
            if( yDisc + yIncrement == yIndex&& xDisc + xIncrement == xIndex && board[yIndex][xIndex] != opposite){
                return null;
            }else if( board[yIndex][xIndex] == player ){
                return new int[]{ xIndex, yIndex };
            }
            yIndex += yIncrement;
            xIndex += xIncrement;
        }

        return null;
    }



    public static void displayBoard(Board b){
        int[][] board = b.board;
        for(int i = 0; i < board.length; i ++){
            if(i == 0){
                System.out.println("    0  1  2  3  4  5  6  7");
            }

            for(int j = 0; j < board[i].length; j++){
                if(j == 0){
                    System.out.print(String.format(" %d ", i));
                }
                int n = board[i][j];
                if(n == WHITE){
                    System.out.print("[●]");
                }else if(n == BLACK){
                    System.out.print("[○]");
                }else{
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }

    }

    public @NotNull int[][] settableDiskPositions(int player){
        int[][] array = new int[board.length * board.length][3];
        Arrays.fill(array, null);

        int counter = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                // y軸判定
                int[] top = oppositeDiscY(player, j, i, true);
                int[] bottom = oppositeDiscY(player, j, i, false);
                int[] right = oppositeDiscX(player, j, i, true);
                int[] left = oppositeDiscX(player, j, i, false);
                int[] leftTop = oppositeDiagonal(player, j, i, true, false);
                int[] rightTop = oppositeDiagonal(player, j, i, true, true);
                int[] leftBottom = oppositeDiagonal(player, j, i, false, false);
                int[] rightBottom = oppositeDiagonal(player, j, i, false, true);

                boolean isSettable = !(top == null
                        && bottom == null
                        && right == null
                        && left == null
                        && leftTop == null
                        && rightTop == null
                        && leftBottom == null
                        && rightBottom == null);
                if(isSettable){
                    array[counter] = new int[]{ j, i };
                    counter ++;
                }
            }
        }
        return array;
    }

    public boolean isFill(){

        boolean isNotFill = false;
        for( int[] y : board ){
            for( int box : y ){
                isNotFill = box != 0;
                if(isNotFill){
                    break;
                }
            }
        }

        return !isNotFill;
    }

    public @NotNull int[][] getBoard(){
        return board.clone();
    }

    public int discCount(int player){
        int count = 0;
        for(int[] a : board){
            for(int box : a){
                if(box == player){
                    count ++;
                }
            }
        }
        return count;
    }
}
