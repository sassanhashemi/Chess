// Board.java
// Sassan Hashemi

package chess;

import com.sun.istack.internal.Nullable;

import javax.rmi.CORBA.Util;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Board {

    private Piece[] board = new Piece[64];
    private ArrayList<String> previousBoards;
    private ArrayList<Move> previousMoves;
    private int numMoves;
    private int turn;
    private String gameState; // either "in progress", "black win", "white win", "draw"
    private int mslcopa; // moves since last capture or pawn advance
    private boolean check;

    Board() {
        this.reset();
        this.previousBoards = new ArrayList<String>();
        this.previousMoves = new ArrayList<Move>();
        this.numMoves = 0;
        this.turn = Utility.WHITE;
        this.gameState = "in progress";
        this.mslcopa = 0;
        this.check = false;
        //this.updateAllMoves();
    }

    public Piece[] getBoard() {
        return this.board;
    }
    ArrayList<String> getPreviousBoards() {
        return this.previousBoards;
    }
    ArrayList<Move> getPreviousMoves() {
        return this.previousMoves;
    }
    public int getNumMoves() throws ChessException {
        if (numMoves >= 0) {
            return this.numMoves;
        } else {
            throw new ChessException("Number of moves is < 0");
        }

    }
    int getTurn() {
        return this.turn;
    }
    String getGameState() {
        return this.gameState;
    }
    public int getMslcopa() {
        return this.mslcopa;
    }
    boolean getCheck() {
        return this.check;
    }

    void appendPreviousBoards() {
        this.previousBoards.add(this.toString());
    }
    void appendPreviousMoves(Move move) {
        this.previousMoves.add(move);
    }
    void incrementNumMoves() {
        this.numMoves++;
    }
    void incrementTurn() {
        this.turn = 1 - this.turn;
    }
    void setGameState(String gameState) throws ChessException {
        if (gameState.equals("in progress") || gameState.equals("white win") || gameState.equals("black win") || gameState.equals("draw")) {
            this.gameState = gameState;
        } else {
            throw new ChessException("Invalid gameState");
        }
    }
    void incrementMslcopa(boolean increment) {
        if (increment) {
            this.mslcopa++;
        } else {
            this.mslcopa = 0;
        }
    }
    void setCheck(boolean check) {
        this.check = check;
    }
    String printBoard() {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < 64; i++) {
            result.append(this.getSquare(i).toString());
            result.append(" ");
            if (i % 8 == 7) {
                result.append("\n");
            }
        }
        return new String(result);
    }

    void clear() {
        for (int i = 0; i < 64; i++) {
            this.setSquare(i, Utility.nullPiece);
        }
    }
    void reset() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 16; j++) {
                this.setSquare(Utility.pieces[i][j].getLocation(), Utility.pieces[i][j]);
            }
        }
        for (int i = 16; i < 48; i++) {
            this.setSquare(i, Utility.nullPiece);
        }
    }
    boolean insufficientMaterial() {
        int whiteMaterial = 0, blackMaterial = 0;
        for (Piece piece : this.board) {
            if (piece != null) {
                if (piece.toString().equals("R") || piece.toString().equals("Q") || piece.toString().equals("P")) {
                    return false;
                } else if (piece.toString().equals("r") || piece.toString().equals("q") || piece.toString().equals("p")) {
                    return false;
                } else if (piece.toString().equals("B") || piece.toString().equals("N")) {
                    whiteMaterial++;
                } else if (piece.toString().equals("b") || piece.toString().equals("n")) {
                    blackMaterial++;
                }
            }
        }
        return (whiteMaterial <= 1 || blackMaterial <= 1);
    }
    boolean boardRepetitionDraw() {
        for (int i = 0; i < this.getPreviousBoards().size(); i++) {
            int numRepeatedStates = 0;
            for (int j = 0; j < this.getPreviousBoards().size(); j++) {
                if (this.getPreviousBoards().get(i).equals(this.getPreviousBoards().get(j))) {
                    numRepeatedStates++;
                }
            }
            if (numRepeatedStates >= 3) {
                return true;
            }
        }
        return false;
    }
    boolean noLegalMoves(int color) {
        boolean availableMoves = false;
        int kingLocation = Utility.pieces[color][Utility.K].getLocation();
        for (Piece piece : this.getBoard()) {
            if (piece.getColor() == color) {
                if (piece.getMoves().size() > 0) {
                    availableMoves = true;
                }
            }
        }
        return (!availableMoves && this.isSafe(kingLocation, color));
    }
    boolean isSafe(int location, int color) {
        for (Piece piece : Utility.pieces[1 - color]) {
            if (!piece.isCaptured()) {
                for (Move move : piece.getMoves()) {
                    if (move.getEnd() == location) {
                        return false;
                    }
                }

            }
        }
        return true;
    }
    Piece getSquare(int location) throws ChessException {
        if (location >= 0 && location < 64) {
            return this.board[location];
        } else {
            throw new ChessException("Invalid square number");
        }

    }
    void setSquare(int location, Piece piece) throws ChessException {
        if (location < 64 && location >= 0) {
            this.board[location] = piece;
        } else {
            throw new ChessException("Invalid location number");
        }

    }
    void updateGameState() {
        if (this.isDraw()) {
            this.gameState = "draw";
        } else if (isCheckMated(Utility.BLACK)) {
            this.gameState = "white win";
        } else if (isCheckMated(Utility.WHITE)) {
            this.gameState = "black win";
        } else {
            this.gameState = "in progress";
        }
    }
    boolean isCheckMated(int color) {
        /* 1. King is in check
     * 2. King cannot move
     * 3. If there is only one checking piece:
              if possible block or capture:
                  no check mate
              else:
                  check mate

          Else:
              check mate
     */
        boolean isChecked, kingCanMove, canBlock = false, canCapture = false;
        ArrayList<Piece> checkingPieces = new ArrayList<>();

        int kingLocation = Utility.pieces[color][Utility.K].getLocation();
        isChecked = !this.isSafe(kingLocation, color);

        kingCanMove = !(this.getSquare(kingLocation).getMoves().size() == 0);

        for (Piece piece : Utility.pieces[1 - color]) {
            for (Move move : piece.getMoves()) {
                if (move.getEnd() == kingLocation) {
                    checkingPieces.add(piece);
                }
            }
        }

        if (checkingPieces.size() > 1) {
            return (isChecked && !kingCanMove);
        } else if (checkingPieces.size() == 1) {
            // Find possible captures or blocks
            for (Piece piece : Utility.pieces[color]) {
                for (Move move : piece.getMoves()) {
                    if (move.getEnd() == checkingPieces.get(0).getLocation()) {
                        canCapture = true;
                    }
                }
            }

            for (Piece piece : Utility.pieces[color]) {
                for (Move move : piece.getMoves()) {
                    for (int square : Utility.squaresBetween(checkingPieces.get(0).getLocation(), kingLocation)) {
                        if (move.getEnd() == square) {
                            canBlock = true;
                        }
                    }
                }
            }
        }
        return (isChecked && !kingCanMove && !canBlock && !canCapture);
    }
    boolean isDraw() {
        return (this.insufficientMaterial() || this.boardRepetitionDraw() || this.noLegalMoves(this.turn) || this.mslcopa >= 50);
    }
    void updateAllMoves() {
        for (int i = 0; i < 2; i ++) {
            for (Piece piece : Utility.pieces[i]) {
                if (!piece.isCaptured()) {
                    piece.updateMoves(this);
                }
            }
        }
    }
    void castle(Move move) {
        assert(move.isCastling());
        if (move.getEnd() == 2) {
            Rook rook = (Rook) Utility.pieces[move.getPiece().getColor()][Utility.R1];
            this.setSquare(3, rook);
            this.setSquare(rook.getLocation(), Utility.nullPiece);
            rook.setLocation(3);
        } else if (move.getEnd() == 6) {
            Rook rook = (Rook) Utility.pieces[move.getPiece().getColor()][Utility.R2];
            this.setSquare(5, rook);
            this.setSquare(rook.getLocation(), Utility.nullPiece);
            rook.setLocation(5);
        } else if (move.getEnd() == 58) {
            Rook rook = (Rook) Utility.pieces[move.getPiece().getColor()][Utility.R1];
            this.setSquare(59, rook);
            this.setSquare(rook.getLocation(), Utility.nullPiece);
            rook.setLocation(59);
        } else if (move.getEnd() == 62) {
            Rook rook = (Rook) Utility.pieces[move.getPiece().getColor()][Utility.R2];
            this.setSquare(61, rook);
            this.setSquare(rook.getLocation(), Utility.nullPiece);
            rook.setLocation(61);
        }
        this.setSquare(move.getPiece().getLocation(), Utility.nullPiece);
        this.setSquare(move.getEnd(), move.getPiece());
        move.getPiece().setLocation(move.getEnd());
    }
    void captureEnPassant(Move move) {
        int direction = -2 * (move.getPiece().getColor()) + 1; // if +1, moves up, if -1, moves down
        this.setSquare(move.getPiece().getLocation(), Utility.nullPiece);           // Set capturing pawn's square to null
        this.setSquare(move.getEnd(), move.getPiece());                             // Set end square to pawn
        this.getSquare(move.getEnd() + 8 * direction).getCaptured();        // Update captured pawns' attributes
        this.setSquare(move.getEnd() + 8 * direction, Utility.nullPiece);   // Set captured pawn's square to null
        move.getPiece().setLocation(move.getEnd());
    }
    void move(Move move) {
        boolean capture =  move.getCapture();
        Piece piece = move.getPiece();
        int location = move.getEnd();

        if (piece.isLegalMove(this, move)) {
            this.appendPreviousBoards();                    // Save to prev boards
            this.appendPreviousMoves(move);
            if (capture) {                                  // Update capture info
                this.getSquare(location).getCaptured();
            }

            //Special moves
            if (move.isCastling()) {
                this.castle(move);
            } else if (move.isEnPassant(this)) {
                this.captureEnPassant(move);
            } else {
                this.setSquare(piece.getLocation(), Utility.nullPiece);
                this.setSquare(location, piece);
                piece.setLocation(location);
            }

            if (move.isPromotion()){
                Utility.promote(this, (Pawn) piece, "Queen");
                //TODO: change pieceType to move.getPromotion() after stringToMove func is done
            }

            //TODO: add promotion above


            this.incrementMslcopa(!piece.toString().equals("P") && !piece.toString().equals("p") && !capture);
            piece.setMoved();
            this.incrementTurn();       // Increment turn
            this.incrementNumMoves();   // Increment numMoves
            this.updateAllMoves();      // Update possible moves
            this.updateGameState();     // Update game state
        } else {
            throw new ChessException("Invalid move");
        }
    }



    //TODO: CREATE METHOD
    /*
    boolean undoMove() {
        if (this.getNumMoves() == 0) {
            return false;
        }
        Move move = this.previousMoves.get(this.previousMoves.size() - 1);
        this.previousMoves.remove(this.previousMoves.size() - 1);       // Remove move from previousMoves
        this.previousBoards.remove(this.previousMoves.size() - 1);      // Remove board from previousBoards

        this.setSquare(move.getStart(), move.getPiece());                   // Move piece back to original square
        if (move.getCapture()) {
            String pieceType  =
            this.setSquare(move.getEnd(), )
        } else {
            this.setSquare(move.getEnd(), Utility.nullPiece);
        }


        this.numMoves--;

        this.updateAllMoves();



        return true;
    }
    */

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < 64; i++) {
            result.append(this.getSquare(i).toString());
            if (i % 8 == 7) {
                result.append("\n");
            }
        }
        return new String(result);
    }

}
