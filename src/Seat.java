public class Seat {
    private int row;
    private char seat;
    private char ticketType;

    public Seat(int r, char s, char type) {
        row = r;
        seat = s;
        ticketType = type;
    }

    public int getRow() {
        return row;
    }

    public char getSeat() {
        return seat;
    }

    public char getTicketType() {
        return ticketType;
    }

    public void setRow(int r) {
        row = r;
    }

    public void setSeat(char s) {
        seat = s;
    }

    public void setTicketType(char type) {
        ticketType = type;
    }
}
