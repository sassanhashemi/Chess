package chess;

//TODO: en passant
//TODO: promotion
//TODO: change Board.getPreviousBoards to public for en passant
//TODO: make move illegal if it puts your king in check


public class Game {

    public static void main(String[] args) {

        Board board = new Board();
        while (board.getGameState().equals("in progress")) {

            try {
                Move move = Utility.stringToMove(Utility.getMove(board.getTurn()), board.getTurn());
                board.move(move);
            } catch (ChessException e){
                System.out.println(e.toString());
            }
        }




        //while (board.getGameState().equals("in progress")) {
        //}
    }


}
