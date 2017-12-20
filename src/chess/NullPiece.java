package chess;

public class NullPiece extends Piece {


    NullPiece() {
        super("null", Utility.EMPTY, Utility.EMPTY);
    }

    boolean isLegalMove(Board board, Move move) {
        return false;
    }

    @Override
    public String toString() {
        return "-";
    }
}
