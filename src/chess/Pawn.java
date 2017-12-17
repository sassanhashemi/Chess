package chess;

public class Pawn extends Piece {

    Pawn(int color, int location) {
        super(color, location);
    }
    private boolean canCapture(Board board, int location) {
        int direction = -2 * (this.getColor()) + 1; // if +1, moves up, if -1, moves down
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(location)[0] - Utility.getRC(this.getLocation())[0];
        boolean enemyPieceExists = board.getSquare(location).getColor() == 1 - this.getColor();
        boolean rightDirection = (dY == direction) && (Math.abs(dX) == 1);
        return (enemyPieceExists && rightDirection);
    }
    private boolean canAdvance(Board board, int location) {
        int direction = -2 * (this.getColor()) + 1; // if +1, moves up, if -1, moves down
        boolean obstructedEnd = (board.getSquare(location).getColor() == this.getColor());
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(location)[0] - Utility.getRC(this.getLocation())[0];
        boolean legalAdvanceOne = (dX == 0) && (dY/direction == 1);
        boolean legalAdvanceTwo = ((this.getLocation()/8) - (2.5 * direction) == 4) && (dX == 0) && board.getSquare(location - 8 * direction) == null;

        return (!obstructedEnd && (legalAdvanceOne || legalAdvanceTwo));
    }
    public boolean isLegalMove(Board board, int location) {
        return this.canCapture(board, location) || this.canAdvance(board, location);
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
