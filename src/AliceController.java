import Chess.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AliceController {

    public void go() {
        // Use JFileChooser to select a test file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

        // Instantiate Boards from the file.
        ArrayList<Board> boards = createBoards(input);

        int count = 1;
        for (Board board : boards) {
            // Print out the board
            board.print();
            // Create a new Alice instance
            Alice alice = new Alice(board);
            // Attempt to solve this board with Alice
            List<Piece> solution = alice.solve();

            // Output the result
            System.out.println();
            if (solution == null) {
                System.out.println("Board " + (count) + ": Alice is stuck!");
            } else {
                System.out.print("Board " + (count) + ": Alice should capture in this order: ");
                for (int j = 0; j < solution.size(); j++) {
                    System.out.print(solution.get(j).getSymbol());
                }
                System.out.println();
            }
            System.out.println();
            count++;
        }
    }

    ArrayList<Board> createBoards(Scanner input) {
        ArrayList<Board> boards = new ArrayList<>();

        // The input will begin with a positive integer N, signifying the number of input instances
        int numBoards = input.nextInt();
        input.nextLine();
        // For each provided board:
        for (int i = 0; i < numBoards; i++) {
            Board board = new Board();

            for (int j = 0; j < board.getHeight(); j++) {
                String line = input.nextLine();
                String[] pieces = line.split(" ");

                for (int k = 0; k < pieces.length; k++) {
                    switch (pieces[k]) {
                        case "P":
                            board.setPiece(j, k, new Pawn(j, k));
                            break;
                        case "B":
                            board.setPiece(j, k, new Bishop(j, k));
                            break;
                        case "R":
                            board.setPiece(j, k, new Rook(j, k));
                            break;
                        case "N":
                            board.setPiece(j, k, new Knight(j, k));
                            break;
                        case "Q":
                            board.setPiece(j, k, new Queen(j, k));
                            break;
                        case "K":
                            board.setPiece(j, k, new King(j, k));
                            break;
                        default:
                            board.setPiece(j, k, null);
                    }
                }
            }

            boards.add(board);
        }

        return boards;
    }
}