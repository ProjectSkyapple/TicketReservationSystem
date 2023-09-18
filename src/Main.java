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

        int selectedOption = 0;

        while (selectedOption != 2) {
            System.out.println("1. Reserve Seats");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            selectedOption = scnr.nextInt();

            if (selectedOption == 2) {
                break;
            }

            // Print seat letters to console.
            System.out.print("  ");
            for (int i = 0; i < numSeatsPerRow; i++) {
                System.out.print((char) ('A' + i));
            }

            System.out.println();

            // Print rows to console.
            for (int i = 1; i <= numRows; i++) {
                System.out.print(i + " ");

                for (int j = 0; j < numSeatsPerRow; j++) {
                    if (auditorium[i - 1][j] == '.') {
                        System.out.print('.');
                    } else {
                        System.out.print('#');
                    }
                }

                System.out.println();
            }

            int selectedRow = 0;
            char selectedSeat = ' ';
            int requestedNumAdultTickets = -1;
            int requestedNumChildTickets = -1;
            int requestedNumSeniorTickets = -1;

            while (selectedRow < 1 || selectedRow > numRows) {
                // Prompt for selectedRow.
                System.out.print("Select a row: ");
                selectedRow = scnr.nextInt();
            }

            while (selectedSeat < 'A' || selectedSeat > (char) ('A' + numSeatsPerRow - 1)) {
                // Prompt for selectedSeat.
                System.out.print("Select a starting seat: ");
                selectedSeat = scnr.next().charAt(0);
            }

            while (requestedNumAdultTickets < 0) {
                // Prompt for requestedNumAdultTickets.
                System.out.print("Enter the number of adults: ");
                requestedNumAdultTickets = scnr.nextInt();
            }

            while (requestedNumChildTickets < 0) {
                // Prompt for requestedNumChildTickets.
                System.out.print("Enter the number of children: ");
                requestedNumChildTickets = scnr.nextInt();
            }

            while (requestedNumSeniorTickets < 0) {
                // Prompt for requestedNumSeniorTickets.
                System.out.print("Enter the number of seniors: ");
                requestedNumSeniorTickets = scnr.nextInt();
            }
        }
    }
}