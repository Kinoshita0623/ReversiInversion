package jp.panta.reversi.sample;

import jp.panta.reversi.Board;
import jp.panta.reversi.Bot;
import jp.panta.reversi.Game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        Game game = new Game();

        System.out.println("-------オセロゲーム-----------");
        Board.displayBoard(game.board);
        Bot bot = new Bot();
        bot.setDisc(Board.WHITE);

        while(game.isProgressGame()){
            String turn = game.getTurn() == Board.BLACK ? "黒" : "白";
            System.out.println("---------------------------------------------------------------");
            System.out.println("次は" + turn);
            if(game.getTurn() == bot.getDiscType()){
                bot.onTurned(game);
                Board.displayBoard(game.board);
                continue;
            }
            System.out.print("コマを置く座標を入力してください (x y), 配置可能な場所をみる場合は-1を指定");
            int n = Integer.parseInt(sc.next());
            if(n == -1){
                for(int[] a : game.settablePositions()){
                    if(a == null)
                        break;
                    System.out.println(String.format("x:%d, y:%d", a[0], a[1]));
                }
                continue;
            }
            int y = sc.nextInt();


            if(game.setDisc(game.getTurn(), n, y)){
                Board.displayBoard(game.board);
            }else{
                System.out.println(String.format("x%d, y%dは無効です", n, y));
            }
        }
    }


}
