public class Auditorium<AnyType> {
    private Node<AnyType> first;

    // Constructs Auditorium object containing reference to first node of empty 2D linked list.
    public Auditorium(int numRows, int numSeatsPerRow) {
        // Create empty first node.
        first = new Node<AnyType>(null, null, null, null);

        // Create remaining empty first column of singly linked nodes.
        Node<AnyType> current = first;

        for (int i = 0; i < numRows - 1; i++) {
            current.setDown(new Node<AnyType>(null, null, null, null));
            current = current.getDown();
        }

        // Create remaining empty rows of doubly linked lists.
        current = first;
        Node<AnyType> previousCurrent;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numSeatsPerRow - 1; j++) {
                current.setNext(new Node<AnyType>(null, null, null, null));
                previousCurrent = current;
                current = current.getNext();
                current.setPrevious(previousCurrent);
            }

            // Move current back to first node in current row.
            while (current.getPrevious() != null) {
                current = current.getPrevious();
            }

            // Go to next row.
            current = current.getDown();
        }
    }

    public Node<AnyType> getFirst() {
        return first;
    }

    public void setFirst(Node<AnyType> f) {
        first = f;
    }
}
