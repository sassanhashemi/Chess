package chess;

public class Knight extends Piece {

    public Knight(int color, int location) {
        super(color, location);
    }
    public boolean isLegalMove(Board board, int location) {
        boolean obstructedEnd = (board.getSquare(location).getColor() == this.getColor());
        int dX = Utility.getRC(location)[1] - Utility.getRC(this.getLocation())[1];
        int dY = Utility.getRC(location)[0] - Utility.getRC(this.getLocation())[0];
        boolean rightDirection = (Math.abs(dX - dY) == 1 && (Math.abs(dX) == 2 || Math.abs(dY) == 2) && (Math.abs(dX) == 1 || Math.abs(dY) == 1));
        return (rightDirection && !obstructedEnd);
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
