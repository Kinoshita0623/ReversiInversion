import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        Game game = new Game();

        System.out.println("-------オセロゲーム-----------");
        while(game.board.isFill()){
            String turn = game.turn == Board.BLACK ? "黒" : "白";
            System.out.println("---------------------------------------------------------------");
            System.out.println("次は" + turn);
            System.out.print("コマを置く座標を入力してください (x y), 配置可能な場所をみる場合は-1を指定");
            int n = Integer.parseInt(sc.next());
            if(n == -1){
                for(int[] a : game.settablePositions(game.turn)){
                    if(a == null)
                        break;
                    System.out.println(String.format("x:%d, y:%d", a[0], a[1]));
                }
                continue;
            }
            int y = sc.nextInt();


            game.setDisc(game.turn, n, y);
            Board.displayBoard(game.board.board);
        }
    }


}
