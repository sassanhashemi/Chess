package chess;

public class Knight extends Piece {

    public Knight(int color, int location) {
        super("Knight", color, location);
    }
    //TODO: simplify startsMatch by removing first part
    public boolean isLegalMove(Board board, Move move) {
        int location = move.getEnd();
        boolean obstructedEnd = (board.getSquare(location).getColor() == this.getColor());
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(location)[0];
        boolean startsMatch = this.getLocation() == move.getStart();
        boolean rightDirection = (Math.abs(Math.abs(dX) - Math.abs(dY)) == 1 && (Math.abs(dX) == 2 || Math.abs(dY) == 2) && (Math.abs(dX) == 1 || Math.abs(dY) == 1));
        boolean capture = (board.getSquare(move.getEnd()).getColor()) == 1 - this.getColor();
        boolean correctCapture = capture == move.getCapture();
        return (rightDirection && !obstructedEnd && correctCapture && startsMatch);
    }

    @Override
    public String toString() {
        if (this.getColor() == Utility.WHITE) {
            return "N";
        } else if (this.getColor() == Utility.BLACK) {
            return "n";
        } else {
            return null;
        }
    }

}
