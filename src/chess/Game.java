package chess;

//TODO: en passant
//TODO: promotion
//TODO: change Board.getPreviousBoards to public for en passant
//TODO: make move illegal if it puts your king in check
//TODO: make methods for: undoMove, resign, offerDraw


public class Game {


    Game() {
        Board board = new Board();
        this.playGame(board);
        this.gameOver(board);

    }

    private void playGame(Board board) {
        while (board.getGameState().equals("in progress")) {
            try {
                Move move = Utility.tempStringToMove(board);
                board.move(move);
            } catch (ChessException e){
                System.out.println(e.toString());
            }
            System.out.println(board.toString());
        }
    }

    private void gameOver(Board board) {
        if (board.getGameState().equals("in progress")) {
            throw new ChessException("The game is not over");
        }

        System.out.println("Game Over!");
        switch (board.getGameState()) {
            case "white win":
                System.out.println("White Wins!");
                break;
            case "black win":
                System.out.println("Black Wins!");
                break;
            case "draw":
                System.out.println("Its a draw!");
                break;
            default:
                throw new ChessException("Invalid game state");
        }
    }


}
