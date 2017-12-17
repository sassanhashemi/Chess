package chess;

public class Bishop extends Piece {

    Bishop(int color, int location) {
        super(color, location);
    }
    public boolean isLegalMove(Board board, int location) {
        boolean obstructedEnd = (board.getSquare(location).getColor() == this.getColor());
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(this.getLocation())[0] - Utility.getRC(location)[0];
        boolean rightDirection = (Math.abs(dX) == Math.abs(dY));
        boolean obstructedPath = false;
        if (Utility.squaresBetween(this.getLocation(), location) != null) {
            for (int square : Utility.squaresBetween(this.getLocation(), location)) {
                if (board.getSquare(square) != null) {
                    obstructedPath = true;
                }
            }
        }
        return (rightDirection && !obstructedEnd && !obstructedPath);
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
