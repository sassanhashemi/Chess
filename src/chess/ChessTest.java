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

        assertEquals(true, pawn.isLegalMove(board, 40)); // Advance one
        assertEquals(true, pawn.isLegalMove(board, 32)); // Advance two
        assertEquals(false, pawn.isLegalMove(board, 24)); // Advance three
        assertEquals(false, pawn.isLegalMove(board, 41)); // Move diagonally without piece to capture
        board.setSquare(41, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, pawn.isLegalMove(board, 41)); // Capture a piece
        board.setSquare(40, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, pawn.isLegalMove(board, 40)); // Advance one with blocked square
        board.setSquare(32, Utility.pieces[Utility.BLACK][3]);
        assertEquals(false, pawn.isLegalMove(board, 32)); // Advance two with blocked square
        //TODO: test capturing en passant
        //TODO: test promotion
    }

    @Test
    public void testKnightIsLegalMove() {
        Board board = new Board();
        Knight knight = (Knight) board.getSquare(57);

        assertEquals(true, knight.isLegalMove(board, 42)); // Nc3 first move
        assertEquals(false, knight.isLegalMove(board, 51)); // Nd2 first move
        board.setSquare(42, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, knight.isLegalMove(board, 42)); // Capture a piece Nc3
        //TODO: test move outside of board range
    }

    @Test
    public void testBishopIsLegalMove() {
        Board board = new Board();
        Bishop bishop = (Bishop) board.getSquare(58);
        board.clear();
        board.setSquare(58, bishop);

        assertEquals(true, bishop.isLegalMove(board, 51)); // Bc1-d2: true
        assertEquals(true, bishop.isLegalMove(board, 44)); // Bc1-e3: true
        assertEquals(false, bishop.isLegalMove(board, 36)); // Bc1-e4: false
        assertEquals(false, bishop.isLegalMove(board, 28)); // Bc1-e5: false
        board.setSquare(51, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, bishop.isLegalMove(board, 51)); // Bc1-d2 captures: true
        assertEquals(true, bishop.isLegalMove(board, 51)); // Bc1-e3 with piece between on d2: false

    }

    @Test
    public void testQueenIsLegalMove() {
        Board board = new Board();
        Queen queen = (Queen) board.getSquare(60);
        board.clear();
        board.setSquare(60, queen);

        assertEquals(true, queen.isLegalMove(board, 51)); // Up 4: true
        assertEquals(true, queen.isLegalMove(board, 44)); // Right 3: true
        assertEquals(false, queen.isLegalMove(board, 36)); // Diagonal up right 2: false
        assertEquals(false, queen.isLegalMove(board, 28)); // Bc1-e5: false
        board.setSquare(51, Utility.pieces[Utility.BLACK][3]);
        assertEquals(true, queen.isLegalMove(board, 51)); // diagonal up right 1 captures: true
        assertEquals(true, queen.isLegalMove(board, 51)); // diagonal up right 3 with piece between: false
    }

}
