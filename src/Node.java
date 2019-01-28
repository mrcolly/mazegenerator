import java.util.ArrayList;

public class Node {
    private int f;
    private int g;
    private int h;
    private Node previous;
    private Cell cell;
    private ArrayList<Node> vicini = new ArrayList<Node>();

    public Node(Cell cell) {
        this.cell = cell;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public ArrayList<Node> getVicini() {
        return vicini;
    }

    public void setVicini(ArrayList<Node> vicini) {
        this.vicini = vicini;
    }

    public void addVicino(Node n){
        this.vicini.add(n);
    }

    @Override
    public String toString() {
        return "Node{" +
                "f=" + f +
                ", g=" + g +
                ", h=" + h +
                ", cell=" + cell +
                ", vicini=" + vicini.size()+
                '}';
    }
}
