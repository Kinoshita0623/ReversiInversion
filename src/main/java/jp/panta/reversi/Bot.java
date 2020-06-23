package jp.panta.reversi;

import java.util.Arrays;
import java.util.Comparator;

public class Bot extends Player{

    @Override
    public void onSettlement(int winner) {

    }

    @Override
    public void onTurned(Game game) {
        int[][] settablePositions = game.settablePositions();
        Arrays.sort(settablePositions, Comparator.nullsLast(
                (o1, o2) -> {
                    if(o1 == null && o2 == null){
                        return 0;
                    }

                    int x0 = 0;
                    int y0 = 0;
                    if(o1 != null){
                        x0 = o1[0];
                        y0 = o1[1];
                    }

                    int x1 = 0;
                    int y1 = 0;
                    if(o2 != null){
                        x1 = o2[0];
                        y1 = o2[1];
                    }

                    int point1 = evaPosPoint(game, x0, y0);
                    int point2 = evaPosPoint(game, x1, y1);
                    return Integer.compare(point2, point1);
                }
        ));

        if(settablePositions[0] == null){
            game.pass(game.getTurn());
            return;
        }
        game.setDisc(settablePositions[0][0], settablePositions[0][1]);

    }

    int evaPosPoint(Game game, int x, int y){
        final int boardSize = game.board.board.length - 1;

        if(x == 0 && ( y == 0 || y == boardSize) || x == boardSize  && ( y == 0 || y == boardSize)){
            return 4;
        }

        if(x == 1 || x == boardSize - 1){
            if(y == 1 || y == boardSize - 1){
                return 0;
            }
        }

        if((x == 1 || x == boardSize - 1) || (y == 1 || y == boardSize - 1)){
            return 1;
        }

        if(( x == 0 || x == boardSize) || (y == 0 || y == boardSize)){
            return 3;
        }

        return 2;

    }


}
