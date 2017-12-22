package chess;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

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
    private boolean canCaptureEnPassant(Board board, Move move) {
        int direction = -2 * (this.getColor()) + 1; // if +1, moves up, if -1, moves down
        int dX = Utility.getRC(move.getEnd())[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(move.getEnd())[0];
        boolean enemyPawnExists = board.getSquare(move.getEnd() + 8 * direction).getColor() == 1 - this.getColor() && board.getSquare(move.getEnd() + 8 * direction).getType().equals("Pawn");
        boolean rightDirection = (dY == direction) && (Math.abs(dX) == 1);
        boolean startsMatch = this.getLocation() == move.getStart();
        boolean capture = (board.getSquare(move.getEnd()).getColor()) == 1 - this.getColor();
        boolean correctCapture = capture == move.getCapture();
        Move lastMove = board.getPreviousMoves().get(board.getNumMoves() - 1);
        boolean correctPawn = lastMove.getPiece() == board.getSquare(move.getEnd() + 8 * direction);
        boolean correctStart = lastMove.getStart() >= 8 && lastMove.getEnd() <= 15;
        boolean correctEnd = lastMove.getEnd() >= 24 && lastMove.getEnd() <= 31;
        boolean pawnMovedLast = correctPawn && correctStart && correctEnd;

        return (enemyPawnExists && rightDirection && startsMatch && correctCapture && pawnMovedLast);
    }

    //TODO: Add can capture en passant after testing
    public boolean isLegalMove(Board board, Move move) {
        return this.canCapture(board, move) || this.canAdvance(board, move);
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
