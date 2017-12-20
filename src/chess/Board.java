package chess;

import com.sun.istack.internal.Nullable;

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
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 16; j++) {
                this.setSquare(Utility.pieces[i][j].getLocation(), Utility.pieces[i][j]);
            }
        }
        for (int i = 16; i < 48; i++) {
            this.setSquare(i, Utility.nullPiece);
        }
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
    private ArrayList<String> getPreviousBoards() {
        return this.previousBoards;
    }
    ArrayList<Move> getPreviousMoves() {
        return this.previousMoves;
    }
    public int getNumMoves() {
        return this.numMoves;
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

    private void appendPreviousBoards() {
        this.previousBoards.add(this.toString());
    }
    void appendPreviousMoves(Move move) {
        this.previousMoves.add(move);
    }
    private void incrementNumMoves() {
        this.numMoves++;
    }
    private void incrementTurn() {
        this.turn = 1 - this.turn;
    }
    void setGameState(String gameState) {
        if (gameState.equals("in progress") || gameState.equals("white win") || gameState.equals("black win") || gameState.equals("draw")) {
            this.gameState = gameState;
        }
    }
    private void incrementMslcopa() {
        this.mslcopa++;
    }
    private void resetMslcopa() {
        this.mslcopa = 0;
    }
    void setCheck(boolean check) {
        this.check = check;
    }

    void clear() {
        for (int i = 0; i < 64; i++) {
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
    private boolean boardRepetitionDraw() {
        for (int i = 0; i < this.getPreviousBoards().size(); i++) {
            int numRepeatedStates = 0;
            for (int j = 0; j < this.getPreviousBoards().size(); j++) {
                if (this.getPreviousBoards().get(i).equals(this.getPreviousBoards().get(j))) {
                    numRepeatedStates++;
                }
            }
            // >3 because it compares it to itself
            if (numRepeatedStates > 3) {
                return true;
            }
        }
        return false;
    }
    private boolean noLegalMoves(int color) {
        boolean availableMoves = false;
        int kingLocation = Utility.pieces[color][Utility.K].getLocation();
        for (Piece piece : this.getBoard()) {
            if (piece != null && piece.getColor() == color) {
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
    Piece getSquare(int location) {
        if (location >= 0 && location < 64) {
            return this.board[location];
        } else {
            return null;
        }

    }
    void setSquare(int location, Piece piece) {
        this.board[location] = piece;
    }
    private void updateGameState() {
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
    private boolean isCheckMated(int color) {
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
        }

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
        return (isChecked && !kingCanMove && !canBlock && !canCapture);
    }
    private boolean isDraw() {
        return (this.insufficientMaterial() || this.boardRepetitionDraw() || this.noLegalMoves(this.turn) || this.mslcopa >= 50);
    }
    private void updateAllMoves() {
        for (int i = 0; i < 2; i ++) {
            for (Piece piece : Utility.pieces[i]) {
                if (!piece.isCaptured()) {
                    piece.updateMoves(this);
                }
            }
        }
    }
    boolean move(Move move) {
        boolean capture =  move.getCapture();
        boolean check = false;
        Piece piece = move.getPiece();
        int location = move.getEnd();

        if (piece.isLegalMove(this, move)) {
            this.appendPreviousBoards();                    // Save to prev boards
            if (capture) {                                  // Update capture info
                this.getSquare(location).getCaptured();
            }
            this.setSquare(piece.getLocation(), null);        // Change piece and board locations
            this.setSquare(location, piece);
            piece.setLocation(location);

            if (!piece.toString().equals("P") && !piece.toString().equals("p") && !capture) { // Increment mslcopa
                this.incrementMslcopa();
            } else {
                this.resetMslcopa();
            }

            piece.setMoved();
            this.incrementTurn();       // Increment turn
            this.incrementNumMoves();   // Increment numMoves
            this.updateGameState();     // Update game state
            this.updateAllMoves();      // Update possible moves

            return true;
        } else {
            return false;
        }
    }

    //TODO: CREATE METHOD
    boolean undoMove() {
        if (this.getNumMoves() == 0) {
            return false;
        }
        Move move = this.previousMoves.get(this.previousMoves.size() - 1);
        this.previousMoves.remove(this.previousMoves.size() - 1);



        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < 64; i++) {
            if (this.getSquare(i).toString() != null) {
                result.append(this.getSquare(i).toString());
            } else {
                result.append("-");
            }

        }
        return new String(result);
    }

}
