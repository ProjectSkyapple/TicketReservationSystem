// Aaron Jacob
// AXJ210111

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    // getSeat method: Returns specified seat in specified Auditorium object.
    // Parameters: Auditorium object, row number, seat letter
    // Returns: Seat object
    public static Seat getSeat(Auditorium<Seat> auditorium, int r, char s) {
        Node<Seat> current = auditorium.getFirst();

        // Navigate to selected seat.
        for (int i = 0; i < r - 1; i++) {
            current = current.getDown();
        }

        for (int i = 0; i < s - 'A'; i++) {
            current = current.getNext();
        }

        return current.getPayload();
    }

    // setSeat method: Sets a specified Auditorium Seat's properties to the arguments provided.
    // Parameters: Auditorium object, row number, seat letter, ticket type
    // Returns: nothing
    public static void setSeat(Auditorium<Seat> auditorium, int r, char s, char type) {
        Node<Seat> current = auditorium.getFirst();

        for (int i = 0; i < r - 1; i++) {
            current = current.getDown();
        }

        for (int i = 0; i < s - 'A'; i++) {
            current = current.getNext();
        }

        if (current.getPayload() == null) {
            current.setPayload(new Seat(r, s, type));
        }
        else {
            current.getPayload().setRow(r);
            current.getPayload().setSeat(s);
            current.getPayload().setTicketType(type);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);
        String inputFilename;
        FileInputStream inputFileStream;
        Scanner inputFileScanner;

        while (true) {
            try {
                System.out.print("Enter filename: ");
                inputFilename = scnr.nextLine();

                inputFileStream = new FileInputStream(inputFilename);
                inputFileScanner = new Scanner(inputFileStream);

                break;
            }
            catch (FileNotFoundException fileError) {
                System.out.print("File not found. ");
            }
        }

        // char[][] auditorium = new char[10][26];
        Auditorium<Seat> auditoriumObject;
        int numSeatsPerRow = 0;
        int numRows = 0;

        // Read characters from input file into auditorium 2D array.
        // int whileCounter1 = 0;
        while (inputFileScanner.hasNext()) {
            String row = inputFileScanner.nextLine();

            for (int j = 0; j < row.length(); j++) {
                // auditorium[whileCounter1][j] = row.charAt(j);
                numSeatsPerRow++;
            }

            numRows++;
            // whileCounter1++;
        }

        numSeatsPerRow /= numRows;

        auditoriumObject = new Auditorium<>(numRows, numSeatsPerRow);

        inputFileScanner.close();
        inputFileStream = new FileInputStream(inputFilename);
        inputFileScanner = new Scanner(inputFileStream);

        int whileCounter1 = 0;
        while (inputFileScanner.hasNext()) {
            String row = inputFileScanner.nextLine();

            for (int j = 0; j < row.length(); j++) {
                // auditorium[whileCounter1][j] = row.charAt(j);
                setSeat(auditoriumObject, whileCounter1 + 1, (char) ('A' + j), row.charAt(j));
            }

            whileCounter1++;
        }

        inputFileScanner.close();

        int selectedOption = 0;

        while (true) {
            while (true) {
                System.out.println("1. Reserve Seats");
                System.out.println("2. Exit");
                System.out.print("Choose an option: ");

                try {
                    selectedOption = scnr.nextInt();
                    break;
                }
                catch (InputMismatchException typeError) {
                    // Loop again.
                    scnr.next();
                }
            }

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
                    if (getSeat(auditoriumObject, i, (char) ('A' + j)).getTicketType() == '.') {
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

                try {
                    selectedRow = scnr.nextInt();
                }
                catch (InputMismatchException typeError) {
                    // Loop again.
                    scnr.next();
                }
            }

            while (selectedSeat < 'A' || selectedSeat > (char) ('A' + numSeatsPerRow - 1)) {
                // Prompt for selectedSeat.
                System.out.print("Select a starting seat: ");

                try {
                    selectedSeat = scnr.next().charAt(0);
                }
                catch (InputMismatchException typeError) {
                    // Loop again.
                    scnr.next();
                }
            }

            while (requestedNumAdultTickets < 0) {
                // Prompt for requestedNumAdultTickets.
                System.out.print("Enter the number of adults: ");

                try {
                    requestedNumAdultTickets = scnr.nextInt();
                }
                catch (InputMismatchException typeError) {
                    // Loop again.
                    scnr.next();
                }
            }

            while (requestedNumChildTickets < 0) {
                // Prompt for requestedNumChildTickets.
                System.out.print("Enter the number of children: ");

                try {
                    requestedNumChildTickets = scnr.nextInt();
                }
                catch (InputMismatchException typeError) {
                    // Loop again.
                    scnr.next();
                }
            }

            while (requestedNumSeniorTickets < 0) {
                // Prompt for requestedNumSeniorTickets.
                System.out.print("Enter the number of seniors: ");

                try {
                    requestedNumSeniorTickets = scnr.nextInt();
                }
                catch (InputMismatchException typeError) {
                    // Loop again.
                    scnr.next();
                }
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
                if (getSeat(auditoriumObject, selectedRow, (char) ('A' + i)).getTicketType() != '.') {
                    isSeatTaken = true;
                    break;
                }
            }

            if (!isSeatTaken) {
                completeBooking(auditoriumObject, selectedRow, selectedSeat, requestedNumAdultTickets,
                                requestedNumChildTickets, requestedNumSeniorTickets);
            } else { // Find best available seats.
                double rowMidpoint = numSeatsPerRow / 2.0;
                double columnMidpoint = numRows / 2.0;
                ArrayList<Integer> possibleBestAvailableIndices = new ArrayList<>();
                ArrayList<Integer> possibleBestAvailableRowNumbers = new ArrayList<>();
                ArrayList<Double> possibleBestAvailableRowMiddles = new ArrayList<>();
                ArrayList<Double> possibleBestAvailableMidpoints = new ArrayList<>();
                int numPossibleBestAvailableIndices = 0;

                for (int k = 1; k <= numRows; k++) {
                    // For every possible seat selection in row, determine if any seat in selection is reserved.
                    for (int i = 0; i < numSeatsPerRow + 1 - totalRequestedNumTickets; i++) {
                        boolean innerIsSeatTaken = false;

                        for (int j = 0; j < totalRequestedNumTickets; j++) {
                            if (getSeat(auditoriumObject, k, (char) ('A' + i + j)).getTicketType() != '.') {
                                innerIsSeatTaken = true;
                                break;
                            }
                        }

                        // A possible seat selection has no seats reserved :). This is considered a possible "best
                        // available" seat selection.
                        // Add this seat selection's starting seat index and midpoint to the corresponding arrays.
                        if (!innerIsSeatTaken) {
                            // possibleBestAvailableIndices[numPossibleBestAvailableIndices] = i;
                            possibleBestAvailableIndices.add(i);
                            possibleBestAvailableRowNumbers.add(k);
                            possibleBestAvailableRowMiddles.add(((k - 1) + k                             ) / 2.0);
                            possibleBestAvailableMidpoints.add ((i       + (i + totalRequestedNumTickets)) / 2.0);
                            numPossibleBestAvailableIndices++;
                        }
                    }
                }

                if (numPossibleBestAvailableIndices == 0) {
                    System.out.println("no seats available");
                    continue;
                }

                int bestAvailableStartingSeatIndex = -1;
                int bestAvailableRowNumber = 0;
                double smallestDifference = Integer.MAX_VALUE;
                double correspondingCurrentDifferenceY = Integer.MAX_VALUE;

                // Find the smallest difference between a seat selection midpoint and the midpoint of the selected row.
                // The seat selection with this smallest difference is *the* best available seat selection.
                for (int i = 0; i < numPossibleBestAvailableIndices; i++) {
                    double currentDifferenceX = Math.abs(possibleBestAvailableMidpoints.get(i) - rowMidpoint);
                    double currentDifferenceY = Math.abs(possibleBestAvailableRowMiddles.get(i) - columnMidpoint);
                    double currentDifference = Math.sqrt(Math.pow(currentDifferenceX, 2) + Math.pow(currentDifferenceY, 2));

                    if ((currentDifference == smallestDifference && currentDifferenceY < correspondingCurrentDifferenceY) ||
                        currentDifference < smallestDifference) {
                        smallestDifference = currentDifference;
                        correspondingCurrentDifferenceY = currentDifferenceY;
                        bestAvailableStartingSeatIndex = possibleBestAvailableIndices.get(i);
                        bestAvailableRowNumber = possibleBestAvailableRowNumbers.get(i);
                    }
                }

                System.out.print("Your seat selection is not available. We recommend the following seat selection: ");
                System.out.println("" + bestAvailableRowNumber + ((char) ('A' + bestAvailableStartingSeatIndex)) + " - " +
                                   bestAvailableRowNumber + ((char) ('A' + bestAvailableStartingSeatIndex +
                                   totalRequestedNumTickets - 1)));

                System.out.print("Do you want to reserve this seat selection? Y/N: ");

                String reserveOption = scnr.next();

                if (reserveOption.equals("Y")) {
                    completeBooking(auditoriumObject, bestAvailableRowNumber, (char) ('A' + bestAvailableStartingSeatIndex),
                                    requestedNumAdultTickets, requestedNumChildTickets, requestedNumSeniorTickets);
                }
            }
        }

        System.out.println("Total Seats: " + (numSeatsPerRow * numRows));

        FileOutputStream outputFileStream = new FileOutputStream("A1.txt");
        PrintWriter outputFileWriter = new PrintWriter(outputFileStream);

        int totalTicketsSold = 0;
        int totalAdultTicketsSold = 0;
        int totalChildTicketsSold = 0;
        int totalSeniorTicketsSold = 0;

        // Output the auditorium to A1.
        for (int i = 1; i <= numRows; i++) {
            for (int j = 0; j < numSeatsPerRow; j++) {
                outputFileWriter.print(getSeat(auditoriumObject, i, (char) ('A' + j)).getTicketType());

                if (getSeat(auditoriumObject, i, (char) ('A' + j)).getTicketType() == 'A') {
                    totalAdultTicketsSold++;
                    totalTicketsSold++;
                } else if (getSeat(auditoriumObject, i, (char) ('A' + j)).getTicketType() == 'C') {
                    totalChildTicketsSold++;
                    totalTicketsSold++;
                } else if (getSeat(auditoriumObject, i, (char) ('A' + j)).getTicketType() == 'S') {
                    totalSeniorTicketsSold++;
                    totalTicketsSold++;
                }
            }

            outputFileWriter.println();
        }

        outputFileWriter.close();

        System.out.println("Total Tickets: " + totalTicketsSold);
        System.out.println("Adult Tickets: " + totalAdultTicketsSold);
        System.out.println("Child Tickets: " + totalChildTicketsSold);
        System.out.println("Senior Tickets: " + totalSeniorTicketsSold);
        System.out.printf("Total Sales: $%.2f", (10.00 * totalAdultTicketsSold + 5.00 * totalChildTicketsSold + 7.50 * totalSeniorTicketsSold));

    }

    public static void completeBooking(Auditorium<Seat> auditoriumObject, int row, char seat, int numAdults,
                                       int numChildren, int numSeniors) {
        // Reserve adults together...
        for (int i = seat - 'A'; i < seat - 'A' + numAdults; i++) {
            // auditorium[row - 1][i] = 'A';
            setSeat(auditoriumObject, row, (char) ('A' + i), 'A');
        }

        // ...then followed by children...
        for (int i = seat - 'A' + numAdults; i < seat - 'A' + numAdults + numChildren; i++) {
            // auditorium[row - 1][i] = 'C';
            setSeat(auditoriumObject, row, (char) ('A' + i), 'C');
        }

        // ...then finally seniors.
        for (int i = seat - 'A' + numAdults + numChildren; i < seat - 'A' + numAdults + numChildren + numSeniors; i++) {
            // auditorium[row - 1][i] = 'S';
            setSeat(auditoriumObject, row, (char) ('A' + i), 'S');
        }
    }
}