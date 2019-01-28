import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Cell {
private int row_index;
private int col_index;
private int lato;
private boolean visited = false;
private PApplet parent;

private ArrayList<Wall> walls = new ArrayList<Wall>();

    public Cell(int row_index, int col_index, int lato, PApplet parent) {
        this.row_index = row_index;
        this.col_index = col_index;
        this.lato = lato;
        this.parent = parent;

        int x = this.col_index * lato;
        int y = this.row_index * lato;

        walls.add(new Wall(new PVector(x,y), new PVector(x+lato,y), parent)); //top
        walls.add(new Wall(new PVector(x+lato,y), new PVector(x+lato,y+lato), parent)); //right
        walls.add(new Wall(new PVector(x+lato,y+lato), new PVector(x,y+lato), parent)); //bottom
        walls.add(new Wall(new PVector(x,y+lato), new PVector(x,y), parent)); //left


    }

    public int getRow_index() {
        return row_index;
    }

    public void setRow_index(int row_index) {
        this.row_index = row_index;
    }

    public int getCol_index() {
        return col_index;
    }

    public void setCol_index(int col_index) {
        this.col_index = col_index;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void show() {
        for(Wall w : walls){
            w.show();
        }

        if(this.visited){
            int x = this.col_index * lato;
            int y = this.row_index * lato;
            parent.noStroke();
            parent.fill(255,255,255,100);
            parent.rect(x,y,lato,lato);
        }
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }

    public int getLato() {
        return lato;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row_index=" + row_index +
                ", col_index=" + col_index +
                ", lato=" + lato +
                ", parent=" + parent +
                '}';
    }

    public void highlight(int v1, int v2, float v3) {
        int x = this.col_index * lato;
        int y = this.row_index * lato;
        parent.noStroke();
        parent.fill(v1,v2,v3);
        parent.rect(x,y,lato,lato);
    }
}
