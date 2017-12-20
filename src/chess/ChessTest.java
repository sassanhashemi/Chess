package chess;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ChessTest {

    @Test
    public void testGameState() {
        // Test 1: in progress
        Board board = new Board();
        String gs1 = "in progress";
        board.setGameState(gs1);
        assertEquals("in progress", board.getGameState());
    }


    @Test
    public void testClearBoard() {
        Board board = new Board();
        board.clear();
        for (int i = 0; i < 64; i++) {
            assertEquals(true, board.getSquare(i) instanceof NullPiece);
        }

    }

    //TODO: Add a test to test for true
    @Test
    public void testInsufficientMaterial() {
        Board board = new Board();
        assertEquals(false, board.insufficientMaterial());
    }

    @Test
    public void testPawnIsLegalMove() {
        Board board = new Board();
        Pawn pawn = (Pawn) board.getSquare(48);

        Move move1 = new Move(pawn, pawn.getLocation(), 40); // Advance 1
        Move move2 = new Move(pawn, pawn.getLocation(), 32); // Advance 2
        Move move3 = new Move(pawn, pawn.getLocation(), 24); // Advance 3
        Move move4 = new Move(pawn, pawn.getLocation(), 41); // Move diagonally without capture
        Move move5 = new Move(pawn, pawn.getLocation(), 41, true); // Capture a piece
        Move move6 = new Move(pawn, pawn.getLocation(), 40); // Advance 1 with blocked square
        Move move7 = new Move(pawn, pawn.getLocation(), 32); // Advance 2 with blocked square


        assertEquals(true, pawn.isLegalMove(board, move1));
        assertEquals(true, pawn.isLegalMove(board, move2));
        assertEquals(false, pawn.isLegalMove(board, move3));
        assertEquals(false, pawn.isLegalMove(board, move4));
        board.setSquare(41, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, pawn.isLegalMove(board, move5));
        board.setSquare(40, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, pawn.isLegalMove(board, move6));
        board.setSquare(32, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, pawn.isLegalMove(board, move7));
        //TODO: test capturing en passant
        //TODO: test promotion
    }

    @Test
    public void testKnightIsLegalMove() {
        Board board = new Board();
        Knight knight = (Knight) board.getSquare(57);

        Move move1 = new Move(knight, knight.getLocation(), 42); // Nc3 first move
        Move move2 = new Move(knight, knight.getLocation(), 51); // Nd2 first move
        Move move3 = new Move(knight, knight.getLocation(), 42, true); // Capture a piece Nc3

        assertEquals(true, knight.isLegalMove(board, move1)); // Nc3 first move
        assertEquals(false, knight.isLegalMove(board, move2)); // Nd2 first move
        board.setSquare(42, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, knight.isLegalMove(board, move3)); // Capture a piece Nc3
        //TODO: test move outside of board range
    }

    @Test
    public void testBishopIsLegalMove() {
        Board board = new Board();
        Bishop bishop = (Bishop) board.getSquare(58);
        board.clear();
        board.setSquare(58, bishop);

        Move move1 = new Move(bishop, bishop.getLocation(), 51); // Bc1-d2: true
        Move move2 = new Move(bishop, bishop.getLocation(), 44); // Bc1-e3: true
        Move move3 = new Move(bishop, bishop.getLocation(), 36); // Bc1-e4: false
        Move move4 = new Move(bishop, bishop.getLocation(), 28); // Bc1-e5: false
        Move move5 = new Move(bishop, bishop.getLocation(), 51, true); // Bc1-d2 captures: true
        Move move6 = new Move(bishop, bishop.getLocation(), 51); // Bc1-e3 with piece between on d2: false

        assertEquals(true, bishop.isLegalMove(board, move1));
        assertEquals(true, bishop.isLegalMove(board, move2));
        assertEquals(false, bishop.isLegalMove(board, move3));
        assertEquals(false, bishop.isLegalMove(board, move4));
        board.setSquare(51, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, bishop.isLegalMove(board, move5));
        assertEquals(false, bishop.isLegalMove(board, move6));

    }

    @Test
    public void testQueenIsLegalMove() {
        Board board = new Board();
        Queen queen = (Queen) board.getSquare(59);
        queen.setLocation(60);
        board.clear();
        board.setSquare(60, queen);
        System.out.println(board.toString());

        Move move1 = new Move(queen, queen.getLocation(), 28); // Up 4: true
        Move move2 = new Move(queen, queen.getLocation(), 57); // Left 3: true
        Move move3 = new Move(queen, queen.getLocation(), 36); // Diagonal up right 2: false
        Move move4 = new Move(queen, queen.getLocation(), 28); // Bc1-e5: false
        Move move5 = new Move(queen, queen.getLocation(), 51, true); // diagonal up right 1 captures: true
        Move move6 = new Move(queen, queen.getLocation(), 39); // diagonal up right 3 with piece between: false

        assertEquals(true, queen.isLegalMove(board, move1));
        assertEquals(true, queen.isLegalMove(board, move2));
        assertEquals(false, queen.isLegalMove(board, move3));
        assertEquals(false, queen.isLegalMove(board, move4));
        board.setSquare(51, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, queen.isLegalMove(board, move5));
        board.setSquare(46, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, queen.isLegalMove(board, move6));
    }

}
