package chess;

public class Bishop extends Piece {

    Bishop(int color, int location) {
        super(color, location);
    }
    public boolean isLegalMove(Board board, Move move) {
        boolean obstructedEnd = (board.getSquare(move.getEnd()).getColor() == this.getColor());
        int dX = Utility.getRC(move.getEnd())[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(move.getEnd())[0];
        boolean rightDirection = (Math.abs(dX) == Math.abs(dY));
        boolean obstructedPath = false;
        boolean startsMatch = move.getStart() == this.getLocation();
        if (Utility.squaresBetween(this.getLocation(), move.getEnd()).size() > 0) {
            for (int square : Utility.squaresBetween(this.getLocation(), move.getEnd())) {
                if (board.getSquare(square) != null) {
                    obstructedPath = true;
                }
            }
        }
        boolean capture = (board.getSquare(move.getEnd()).getColor()) == 1 - this.getColor();
        boolean correctCapture = capture == move.getCapture();
        return (rightDirection && !obstructedEnd && !obstructedPath && startsMatch && correctCapture);
    }

    @Override
    public String toString() {
        if (this.getColor() == Utility.WHITE) {
            return "B";
        } else if (this.getColor() == Utility.BLACK) {
            return "b";
        } else {
            return null;
        }
    }

}
