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

            int totalRequestedNumTickets = requestedNumAdultTickets + requestedNumChildTickets + requestedNumSeniorTickets;

            // Return to main menu if totalRequestedNumTickets is more than numSeatsPerRow.
            if (totalRequestedNumTickets > numSeatsPerRow) {
                System.out.println("no seats available");
                continue;
            }

            boolean isSeatTaken = false;

            // Check if there is at least one reserved seat in user's desired seat selection.
            for (int i = selectedSeat - 'A'; i < selectedSeat - 'A' + totalRequestedNumTickets; i++) {
                if (auditorium[selectedRow - 1][i] != '.') {
                    isSeatTaken = true;
                    break;
                }
            }

            if (!isSeatTaken) {
                System.out.print("Ready to reserve " + selectedRow + selectedSeat + " - " + selectedRow +
                                 (char) (selectedSeat + totalRequestedNumTickets - 1) + "? Y/N: ");

                String reserveOption = scnr.next();

                if (reserveOption.equals("Y")) {
                    completeBooking(auditorium, selectedRow, selectedSeat, requestedNumAdultTickets,
                                    requestedNumChildTickets, requestedNumSeniorTickets);
                } else {
                    continue;
                }
            } else { // Find best available seats.
                double rowMidpoint = numSeatsPerRow / 2.0;
                int[] possibleBestAvailableIndices = new int[numSeatsPerRow - 1];
                double[] possibleBestAvailableMidpoints = new double[numSeatsPerRow - 1]; // Midpoints here correspond
                                                                                          // to indices in possible
                                                                                          // indices array.
                int numPossibleBestAvailableIndices = 0;

                // For every possible seat selection in row, determine if any seat in selection is reserved.
                for (int i = 0; i < numSeatsPerRow + 1 - totalRequestedNumTickets; i++) {
                    boolean innerIsSeatTaken = false;

                    for (int j = 0; j < totalRequestedNumTickets; j++) {
                        if (auditorium[selectedRow - 1][i + j] != '.') {
                            innerIsSeatTaken = true;
                        }
                    }

                    // A possible seat selection has no seats reserved :). This is considered a possible "best
                    // available" seat selection.
                    // Add this seat selection's starting seat index and midpoint to the corresponding arrays.
                    if (!innerIsSeatTaken) {
                        numPossibleBestAvailableIndices++;
                        possibleBestAvailableIndices[numPossibleBestAvailableIndices] = i;
                        possibleBestAvailableMidpoints[numPossibleBestAvailableIndices] = i + (i + totalRequestedNumTickets) / 2.0;
                    }
                }

                int bestAvailableStartingSeatIndex = -1;
                double smallestDifference = 50;

                // Find the smallest difference between a seat selection midpoint and the midpoint of the selected row.
                // The seat selection with this smallest difference is *the* best available seat selection.
                for (int i = 0; i < numPossibleBestAvailableIndices; i++) {
                    if (Math.abs(possibleBestAvailableMidpoints[i] - rowMidpoint) < smallestDifference) {
                        bestAvailableStartingSeatIndex = possibleBestAvailableIndices[i];
                    }
                }

                System.out.print("Your seat selection is not available. We recommend the following seat selection: ");
                System.out.println("" + selectedRow + ((char) ('A' + bestAvailableStartingSeatIndex)) + " - " +
                                   selectedRow + ((char) ('A' + bestAvailableStartingSeatIndex +
                                   totalRequestedNumTickets - 1)));

                System.out.print("Do you want to reserve this seat selection? Y/N: ");

                String reserveOption = scnr.next();

                if (reserveOption.equals("Y")) {
                    completeBooking(auditorium, selectedRow, (char) ('A' + bestAvailableStartingSeatIndex),
                                    requestedNumAdultTickets, requestedNumChildTickets, requestedNumSeniorTickets);
                } else {
                    continue;
                }
            }

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

            System.out.println();
        }
    }

    public static void completeBooking(char[][] auditorium, int row, char seat, int numAdults,
                                       int numChildren, int numSeniors) {
        // Reserve adults together...
        for (int i = seat - 'A'; i < seat - 'A' + numAdults; i++) {
            auditorium[row - 1][i] = 'A';
        }

        // ...then followed by children...
        for (int i = seat - 'A' + numAdults; i < seat - 'A' + numAdults + numChildren; i++) {
            auditorium[row - 1][i] = 'C';
        }

        // ...then finally seniors.
        for (int i = seat - 'A' + numAdults + numChildren; i < seat - 'A' + numAdults + numChildren + numSeniors; i++) {
            auditorium[row - 1][i] = 'S';
        }
    }
}