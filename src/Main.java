import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);
        String inputFilename;

        System.out.print("Enter filename: ");
        inputFilename = scnr.nextLine();

        FileInputStream inputFileStream = new FileInputStream(inputFilename);
        Scanner inputFileScanner = new Scanner(inputFileStream);

        char[][] auditorium = new char[10][26];
        int numSeatsPerRow = 0;
        int numRows = 0;

        int whileCounter1 = 0;
        while (inputFileScanner.hasNext()) {
            String row = inputFileScanner.nextLine();

            for (int j = 0; j < row.length(); j++) {
                auditorium[whileCounter1][j] = row.charAt(j);
                numSeatsPerRow++;
            }

            numRows++;
            whileCounter1++;
        }

        numSeatsPerRow /= numRows;

        inputFileScanner.close();

        // Test to check if all characters from input file were correctly loaded into array.
        System.out.println("***TEST***");

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numSeatsPerRow; j++) {
                System.out.print(auditorium[i][j]);
            }

            System.out.println();
        }

        System.out.println("**********");
        // *******
    }
}