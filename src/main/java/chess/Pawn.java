package chess;

public class Pawn extends Piece {

    Pawn(int color, int location) {
        super("Pawn", color, location);
    }
    private boolean canCapture(Board board, Move move) {
        int location = move.getEnd();
        int direction = -2 * (this.getColor()) + 1; // if +1, moves up, if -1, moves down
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(location)[0];
        boolean enemyPieceExists = board.getSquare(location).getColor() == 1 - this.getColor();
        boolean rightDirection = (dY == direction) && (Math.abs(dX) == 1);
        boolean startsMatch = this.getLocation() == move.getStart();
        boolean capture = (board.getSquare(move.getEnd()).getColor()) == 1 - this.getColor();
        boolean correctCapture = capture == move.getCapture();
        return (enemyPieceExists && rightDirection && startsMatch && correctCapture);
    }
    private boolean canAdvance(Board board, Move move) {
        int location = move.getEnd();
        int direction = -2 * (this.getColor()) + 1; // if +1, moves up, if -1, moves down
        boolean obstructedEnd = !(board.getSquare(location) instanceof NullPiece);
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(location)[0];
        boolean legalAdvanceOne = (dX == 0) && (dY/direction == 1);
        boolean legalAdvanceTwo = ((Utility.getRC(location)[0] - (0.5 * direction) == 3.5) && (dX == 0) && (board.getSquare(location + 8 * direction) instanceof NullPiece));
        boolean startsMatch = this.getLocation() == move.getStart();
        return (!obstructedEnd && (legalAdvanceOne || legalAdvanceTwo) && startsMatch);
    }
    boolean canCaptureEnPassant(Board board, Move move) {
        if (move.getEnd() < 16 || move.getEnd() > 48 || (move.getEnd() > 23 && move.getEnd() < 32)) {
            return false;
        }
        int direction = -2 * (this.getColor()) + 1; // if +1, moves up, if -1, moves down
        int dX = Utility.getRC(move.getEnd())[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(move.getEnd())[0];
        String pieceStr = board.getSquare(move.getEnd() + 8 * direction).toString();
        boolean enemyPawnExists = board.getSquare(move.getEnd() + 8 * direction).getColor() == 1 - this.getColor() && (pieceStr == "P" || pieceStr == "p");
        boolean rightDirection = (dY == direction) && (Math.abs(dX) == 1);
        boolean startsMatch = this.getLocation() == move.getStart();
        boolean pawnMovedLast;
        Move lastMove;
        if (board.getNumMoves() >= 1) {
            lastMove = board.getPreviousMoves().get(board.getNumMoves() - 1);
            boolean correctPawn = lastMove.getPiece() == board.getSquare(move.getEnd() + 8 * direction);
            boolean correctStart = lastMove.getStart() >= 8 && lastMove.getStart() <= 15;
            boolean correctEnd = lastMove.getEnd() >= 24 && lastMove.getEnd() <= 31;
            pawnMovedLast = correctPawn && correctStart && correctEnd;
        } else {
            pawnMovedLast = false;
        }
        return (enemyPawnExists && rightDirection && startsMatch && pawnMovedLast);
    }

    public boolean isLegalMove(Board board, Move move) {
        return this.canCapture(board, move) || this.canAdvance(board, move) || canCaptureEnPassant(board, move);
    }



    @Override
    public String toString() {
        if (this.getColor() == Utility.WHITE) {
            return "P";
        } else if (this.getColor() == Utility.BLACK) {
            return "p";
        } else {
            return null;
        }
    }
}
