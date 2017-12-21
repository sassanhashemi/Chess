package chess;

//TODO: en passant
//TODO: promotion
//TODO: change Board.getPreviousBoards to public for en passant
//TODO: make move illegal if it puts your king in check
//TODO: make methods for: undoMove, resign, offerDraw


public class Game {

    public static void main(String[] args) {

        Board board = new Board();
        try {
            Move move = Utility.tempStringToMove(board, Utility.getMove(board.getTurn()), board.getTurn());
            board.move(move);
        } catch (ChessException e){
            System.out.println(e.toString());
        }

        System.out.println("Done");
        while (board.getGameState().equals("in progress")) {


        }




        //while (board.getGameState().equals("in progress")) {
        //}
    }


}
