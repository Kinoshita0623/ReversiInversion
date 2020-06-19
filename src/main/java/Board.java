
import java.util.Arrays;

public class Board {

    static final int WHITE = 1;
    static final int BLACK = -1;

    /**
     * 縦軸に
     * 横横横横横横横横配置をする
     */
    int[][] board;

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

    public void setDisc(final int player, final int xDisc, final int yDisc, final int[] opposite){
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
    public int[] oppositeDiscX(int player, int xDisc, int yDisc,  boolean isRightTo){
        int[] xArray = board[yDisc];
        int increment = isRightTo ? 1 : -1;
        // int opposite = player == WHITE ? BLACK : WHITE;
        for( int i = xDisc; i < xArray.length && i >= 0;  i += increment){
            if( i == xDisc && xArray[i] == player){
                // 既に配置されていた場合は配置することができないのでNULLを返す
                return null;
            }else if( i == xDisc + increment && xArray[i] == player){
                return null;
            }else if( xArray[i] == player ){
                return new int[]{ i, yDisc };
            }
        }
        return null;
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
    public int[] oppositeDiscY(final int player, final int xDisc, final int yDisc, final boolean isUpTo){
        final int increment = isUpTo ? 1 : -1;
        //System.out.println(String.format("xDisc:%d, yDisc:%d, increment:%d", xDisc, yDisc, increment));
        for( int i = yDisc; i < board.length && i >= 0; i += increment){
            if( i == yDisc && board[i][xDisc] == player){
                // 既に配置されていた場合は配置することができないのでNULLを返す
                return null;
            }else if( i == yDisc + increment && board[i][xDisc] == player){
                return null;
            }else if( board[i][xDisc] == player ){
                return new int[]{ xDisc, i };
            }
        }
        return null;
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
    public int[] oppositeDiagonal(final int player, final int xDisc, final int yDisc, final boolean isUpTo, final boolean isRightTo){

        int xIncrement = isRightTo ? 1 : -1;
        int yIncrement = isUpTo ? -1 : 1;
        int yIndex = yDisc;
        int xIndex = xDisc;
        //System.out.println(String.format("xDisc:%d, yDisc:%d, xIncrement:%d, yIncrement:%d", xDisc, yDisc, xIncrement, yIncrement));

        while(
                yIndex >= 0
                        && yIndex < board.length
                        && xIndex >= 0
                        && xIndex < board[yIndex].length
        ){
            //System.out.println(String.format("x:%d, y:%d", xIndex, yIndex));
            if( yIndex == yDisc && yIndex == xDisc && board[yIndex][xIndex] == player && board[yIndex][xIndex] == player){
                // 既に配置されていた場合は配置することができないのでNULLを返す
                return null;
            }else if( yDisc + yIncrement == yIndex&& xDisc + xIncrement == xIndex && board[yIndex][xIndex] == player){
                return null;
            }else if( board[yIndex][xIndex] == player ){
                return new int[]{ xIndex, yIndex };
            }
            yIndex += yIncrement;
            xIndex += xIncrement;
        }

        return null;
    }


    public static void displayBoard(int[][] board){
        for(int[] w: board){
            for(int n : w){
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


}
