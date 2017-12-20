package chess;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;

import static chess.Utility.*;

//TODO: fix all occurances of updateMvoes


public abstract class Piece {

    private int color;
    private int location;
    private ArrayList<Move> moves;
    private boolean captured;
    private boolean moved;
    private String type;

    public Piece(String type, int color, int location) {
        this.color = color;
        this.location = location;
        this.captured = false;
        this.moves = new ArrayList<>();
    }

    int getColor() {
        return this.color;
    }
    public int getLocation() {
        return this.location;
    }
    String getType() {
        return this.type;
    }
    ArrayList<Move> getMoves() {
        return this.moves;
    }
    boolean isCaptured() {
        return this.captured;
    }
    public void setLocation(int location) {
        this.location = location;
    }
    public void setMoved() {
        this.moved = true;
    }
    void setType(String type) {
        if (type.equals("King") || type.equals("Pawn") || type.equals("Queen") || type.equals("Rook")  || type.equals("Bishop")  || type.equals("Knight")) {
            this.type = type;
        } else {
            throw new Exception("Invalid type cast");
        }
    }

    void updateMoves(Board board) {
        ArrayList<Move> newMoves = new ArrayList<>();
        if (this.isCaptured()) {
            this.moves = newMoves;
        } else {
            for (int i = 0; i < 64; i++) {
                if (this.isLegalMove(board, new Move(this, this.getLocation(), i))) {
                    boolean capture = false, promotion = false;
                    if (board.getSquare(i).getColor() == 1 - this.getColor()) {
                        capture = true;
                    }
                    //TODO: add condition for promotion
                    moves.add(new Move(this, this.getLocation(), i, capture, promotion));
                }
            }
            this.moves = newMoves;
        }
    }
    public boolean isSafe() {
        for (Piece piece : Utility.pieces[1 - this.getColor()]) {
            for (Move move : piece.getMoves()) {
                if (move.getEnd() == this.getLocation()) {
                    return false;
                }
            }
        }
        return true;
    }
    void getCaptured() {
        this.captured = true;
        this.moves = new ArrayList<Move>();
        this.setLocation(-1);
    }
    boolean hasMoved() {
        return this.moved;
    }

    abstract boolean isLegalMove(Board board, Move move);


}
