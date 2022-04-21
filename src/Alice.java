import Chess.*;

import java.util.*;

public class Alice {
    private Board board;
    private Piece currentPiece = null;

    public Alice(Board board) {
        this.board = board;
    }

    public List<Piece> solve() {
        // find a starting piece in the bottom row
        int row = board.getHeight() - 1;
        int col = 0;
        Piece piece;
        while (col < board.getWidth()) {
            piece = board.getPiece(row, col);
            if (piece != null) {
                currentPiece = piece;
            }
            col++;
        }
        // if currentPiece is still null, Alice is stuck and this board can't be solved (must start on bottom row).
        if (currentPiece == null) {
            return null;
        }

        // use a Queue to store possible paths
        Queue<List<Piece>> paths = new LinkedList<>();

        // use a list to track an individual path
        List<Piece> path = new ArrayList<>();
        // add the currentPiece
        path.add(currentPiece);
        paths.offer(path);

        while (!paths.isEmpty()) {
            // remove a path from the queue
            path = paths.poll();
            // get last piece in the path
            Piece last = path.get(path.size() - 1);
            // if that piece is in the top row (0) and all pieces have been taken, this path is a solution
            if (last.getRow() == 0 && path.size() == board.countPieces()) {
                return path;
            }

            // get all possible moves for the last piece
            List<Piece> moves = getMoves(last, path);
            // for each move, add it to the path and add the path to the queue
            for (Piece move : moves) {
                List<Piece> newPath = new ArrayList<>(path);
                newPath.add(move);
                paths.offer(newPath);
            }
        }
        return null;
    }

    private List<Piece> getMoves(Piece piece, List<Piece> path) {
        List<Piece> moves = new ArrayList<>();
        int row = piece.getRow();
        int col = piece.getCol();
        // check all possible moves for the piece
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                // if the move is valid, add it to the list
                if (board.isValidMove(row, col, i, j) && !path.contains(board.getPiece(i, j))) {
                    moves.add(board.getPiece(i, j));
                }
            }
        }
        return moves;
    }
}