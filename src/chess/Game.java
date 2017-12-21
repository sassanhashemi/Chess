package chess;

//TODO: en passant
//TODO: promotion
//TODO: change Board.getPreviousBoards to public for en passant
//TODO: make move illegal if it puts your king in check
//TODO: make methods for: undoMove, resign, offerDraw


public class Game {

    public static void main(String[] args) {

        Board board = new Board();

        while (board.getGameState().equals("in progress")) {
            try {
                Move move = Utility.tempStringToMove(board);
                board.move(move);
            } catch (ChessException e){
                System.out.println(e.toString());
            }
            System.out.println(board.getGameState());
            System.out.println(board.toString());


        }

        System.out.println("Done");
        System.out.println(board.toString());





        //while (board.getGameState().equals("in progress")) {
        //}
    }


}
