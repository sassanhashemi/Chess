package chess;

//TODO: en passant
//TODO: promotion
//TODO: change Board.getPreviousBoards to public for en passant
//TODO: make move illegal if it puts your king in check


public class Game {

    public static void main(String[] args) {

        System.out.println("Hi");
        Board board = new Board();

        System.out.println("Hello World");

        String move = Utility.getMove(board.getTurn());
        int location = Utility.moveToLocation(move);
        Piece piece = Utility.moveToPiece(board, move);
        System.out.println(piece.isLegalMove(board, location));
        board.move(piece, location);



        //while (board.getGameState().equals("in progress")) {
        //}
    }


}
