package chess;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.BufferedReader;

//TODO: moves to location and moves to piece
//TODO: Get input in getMove


class Utility {

    final static int EMPTY = -1;
    final static int WHITE = 0;
    final static int BLACK = 1;

    final static int K = 0;
    final static int R1 = 2;
    final static int R2 = 3;
    final static int B1 = 4;
    /*
    public final static int Q = 1;
    public final static int B1 = 4;
    public final static int B2 = 5;
    public final static int N1 = 6;
    public final static int N2 = 7;
    public final static int P1 = 8;
    public final static int P2 = 9;
    public final static int P3 = 10;
    public final static int P4 = 11;
    public final static int P5 = 12;
    public final static int P6 = 13;
    public final static int P7 = 14;
    public final static int P8 = 15;
    */

    // Piece Definitions
    public static Piece nullPiece = new NullPiece();

    private static Rook br1 = new Rook(BLACK, 0);
    private static Knight bn1 = new Knight(BLACK, 1);
    private static Bishop bb1 = new Bishop(BLACK, 2);
    private static Queen bq = new Queen(BLACK, 3);
    private static King bk = new King(BLACK, 4);
    private static Bishop bb2 = new Bishop(BLACK, 5);
    private static Knight bn2 = new Knight(BLACK, 6);
    private static Rook br2 = new Rook(BLACK, 7);

    private static Pawn bp1 = new Pawn(BLACK, 8);
    private static Pawn bp2 = new Pawn(BLACK, 9);
    private static Pawn bp3 = new Pawn(BLACK, 10);
    private static Pawn bp4 = new Pawn(BLACK, 11);
    private static Pawn bp5 = new Pawn(BLACK, 12);
    private static Pawn bp6 = new Pawn(BLACK, 13);
    private static Pawn bp7 = new Pawn(BLACK, 14);
    private static Pawn bp8 = new Pawn(BLACK, 15);


    private static Pawn wp1 = new Pawn(WHITE, 48);
    private static Pawn wp2 = new Pawn(WHITE, 49);
    private static Pawn wp3 = new Pawn(WHITE, 50);
    private static Pawn wp4 = new Pawn(WHITE, 51);
    private static Pawn wp5 = new Pawn(WHITE, 52);
    private static Pawn wp6 = new Pawn(WHITE, 53);
    private static Pawn wp7 = new Pawn(WHITE, 54);
    private static Pawn wp8 = new Pawn(WHITE, 55);

    private static Rook wr1 = new Rook(WHITE, 56);
    private static Knight wn1 = new Knight(WHITE, 57);
    private static Bishop wb1 = new Bishop(WHITE, 58);
    private static Queen wq = new Queen(WHITE, 59);
    private static King wk = new King(WHITE, 60);
    private static Bishop wb2 = new Bishop(WHITE, 61);
    private static Knight wn2 = new Knight(WHITE, 62);
    private static Rook wr2 = new Rook(WHITE, 63);


    static Piece[][] pieces = {{wk, wq, wr1, wr2, wb1, wb2, wn1, wn2, wp1, wp2, wp3, wp4, wp5, wp6, wp7, wp8},
                               {bk, bq, br1, br2, bb1, bb2, bn1, bn2, bp1, bp2, bp3, bp4, bp5, bp6, bp7, bp8}};



    static int[] getRC(int location) {
        int result[] = new int[2];
        result[0] = (location / 8);
        result[1] = (location % 8);
        return result;
    }
    static ArrayList<Integer> squaresBetween(int start, int end) {
        ArrayList<Integer> squares = new ArrayList<>();
        int startRow = getRC(start)[0];
        int startCol = getRC(start)[1];
        int endRow = getRC(end)[0];
        int endCol = getRC(end)[1];
        boolean vertical = (startCol == endCol);
        boolean horizontal = (startRow == endRow);
        boolean diagonal = (java.lang.Math.abs(endRow - startRow) == Math.abs(endCol - startCol));
        int dY = startRow - endRow;
        int dX = endCol - startCol;

        if (vertical && dY > 1) {                   // Up
            for (int i = 1; i < dY; i++) {
                squares.add(start - 8 * i);
            }
        } else if (vertical && dY < 1) {            // Down
            for (int i = -1; i > dY; i--) {
                squares.add(start - 8 * i);
            }
        } else if (horizontal && dX > 1) {          // Right
            for (int i = 1; i < dX; i++) {
                squares.add(start + i);
            }
        } else if (horizontal && dX < 1) {          // Left
            for (int i = -1; i > dX; i--) {
                squares.add(start + i);
            }
        } else if (diagonal && dY > 1 && dX > 1) {  // Up Right
            for (int i = 1; i < Math.abs(dY); i++) {
                squares.add(start - 7 * i);
            }
        } else if (diagonal && dY > 1 && dX < 1) {  // Up Left
            for (int i = 1; i < Math.abs(dY); i++) {
                squares.add(start - 9 * i);
            }
        } else if (diagonal && dY < 1 && dX > 1) {  // Down Right
            for (int i = 1; i < Math.abs(dY); i++) {
                squares.add(start + 9 * i);
            }
        } else if (diagonal && dY < 1 && dX < 1) {  // Down Left
            for (int i = 1; i < Math.abs(dY); i++) {
                squares.add(start + 7 * i);
            }
        }
        return squares;
    }


    static int moveToLocation(String move) {
        int num = (8 - Integer.parseInt(move.substring(2, 3))) * 8;
        int letter = move.charAt(1) - 97;
        return num + letter;

    }
    static Piece moveToPiece(Board board, String move) {
        int num = (8 - Integer.parseInt(move.substring(2, 3))) * 8;
        int letter = move.charAt(1) - 97;
        int location = num + letter;
        return board.getSquare(location);
    }
    static String getMove(int turn) {
        String move = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (turn == Utility.WHITE) {
            System.out.println("Enter a move white:");

        } else if (turn == Utility.BLACK){
            System.out.println("Enter a move black:");
        }
        try {
            move = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: convert string to move
        return move;
    }
    //public static Board toBoard(String board);
    //public static void promote(Pawn pawn, Piece piece);

}
