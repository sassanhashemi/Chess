package chess;

public class Rook extends Piece {


    Rook(int color, int location) {
        super("Rook", color, location);
    }
    public boolean isLegalMove(Board board, Move move) {
        int location = move.getEnd();
        boolean obstructedEnd = (board.getSquare(location).getColor() == this.getColor());
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(location)[0];
        boolean rightDirection = (dX == 0 || dY == 0);
        boolean obstructedPath = false;
        boolean startsMatch = move.getStart() == this.getLocation();
        for (int square : Utility.squaresBetween(this.getLocation(), location)) {
            if (!(board.getSquare(square) instanceof NullPiece)) {
                obstructedPath = true;
            }
        }
        boolean capture = (board.getSquare(move.getEnd()).getColor()) == 1 - this.getColor();
        boolean correctCapture = capture == move.getCapture();
        return (rightDirection && !obstructedEnd && !obstructedPath && startsMatch && correctCapture);
    }

    @Override
    public String toString() {
        if (this.getColor() == Utility.WHITE) {
            return "R";
        } else if (this.getColor() == Utility.BLACK){
            return "r";
        } else {
            return null;
        }
    }
}
