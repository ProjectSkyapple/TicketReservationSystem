public class Node<AnyType> {
    AnyType payload;
    Node<AnyType> next;
    Node<AnyType> previous;
    Node<AnyType> down;

    public Node(AnyType pl, Node<AnyType> n, Node<AnyType> p, Node<AnyType> d) {
        payload = pl;
        next = n;
        previous = p;
        down = d;
    }

    public AnyType getPayload() {
        return payload;
    }

    public Node<AnyType> getNext() {
        return next;
    }

    public Node<AnyType> getPrevious() {
        return previous;
    }

    public Node<AnyType> getDown() {
        return down;
    }

    public void setPayload(AnyType pl) {
        payload = pl;
    }

    public void setNext(Node<AnyType> n) {
        next = n;
    }

    public void setPrevious(Node<AnyType> p) {
        previous = p;
    }

    public void setDown(Node<AnyType> d) {
        down = d;
    }
}
