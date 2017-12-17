package chess;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;

import static chess.Utility.*;

//TODO: fix all occurances of updateMvoes


public abstract class Piece {

    private int color;
    private int location;
    private ArrayList<Integer> moves;
    private boolean captured;
    private boolean moved;

    public Piece(int color, int location) {
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
    ArrayList<Integer> getMoves() {
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

    @Nullable
    void updateMoves(Board board) {
        ArrayList<Integer> newMoves = new ArrayList<>();
        if (this.isCaptured()) {
            this.moves = newMoves;
        } else {
            for (int i = 0; i < 64; i++) {
                if (this.isLegalMove(board, i)) {
                    moves.add(i);
                }
            }
        }
        this.moves = newMoves;
    }
    public boolean isSafe() {
        for (Piece piece : Utility.pieces[1 - this.getColor()]) {
            for (int move : piece.getMoves()) {
                if (move == this.getLocation()) {
                    return false;
                }
            }
        }
        return true;
    }
    void getCaptured() {
        this.captured = true;
        this.moves = new ArrayList<Integer>();
        this.setLocation(-1);
    }
    boolean hasMoved() {
        return this.moved;
    }

    @Nullable
    abstract boolean isLegalMove(Board board, int location);


}
