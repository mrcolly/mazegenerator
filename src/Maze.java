import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Maze {
    private boolean done = false;
    boolean removedRandom = false;

    private int width;
    private int height;
    private int cols;
    private int rows;
    private int lato;
    private Cell current;
    private PApplet parent;
    private int randomWallRemove;

    private ArrayList<Cell> cells = new ArrayList<Cell>();

    private LinkedList<Cell> stack = new LinkedList<Cell>();

    public Maze(int width, int height, int lato, int randomWallRemove, PApplet parent) {
        this.width = width;
        this.height = height;
        this.lato = lato;
        this.cols = width/lato;
        this.rows = height/lato;
        this.parent = parent;
        this.randomWallRemove = randomWallRemove;


        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                cells.add(new Cell(i,j,lato,parent));
            }
        }
        this.current = getCells().get(0);
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public LinkedList<Cell> getStack() {
        return stack;
    }

    public void setStack(LinkedList<Cell> stack) {
        this.stack = stack;
    }

    public Cell getUnvisitedCell(Cell c){
        ArrayList<Cell> vicini = new ArrayList<Cell>();

        Cell vicino = cellIndex(c.getCol_index(), c.getRow_index()-1);
        if(vicino!=null && !vicino.isVisited())
        vicini.add(vicino);

        vicino = cellIndex(c.getCol_index(), c.getRow_index()+1);
        if(vicino!=null && !vicino.isVisited())
            vicini.add(vicino);

        vicino = cellIndex(c.getCol_index()-1, c.getRow_index());
        if(vicino!=null && !vicino.isVisited())
            vicini.add(vicino);

        vicino = cellIndex(c.getCol_index()+1, c.getRow_index());
        if(vicino!=null && !vicino.isVisited())
            vicini.add(vicino);

        if(!vicini.isEmpty())
            return  vicini.get(new Random().nextInt(vicini.size()));
        else
            return null;
    }


    public Cell getRandomNearCell(Cell c){
        ArrayList<Cell> vicini = new ArrayList<Cell>();

        Cell vicino = cellIndex(c.getCol_index(), c.getRow_index()-1);
        if(vicino!=null)
            vicini.add(vicino);

        vicino = cellIndex(c.getCol_index(), c.getRow_index()+1);
        if(vicino!=null)
            vicini.add(vicino);

        vicino = cellIndex(c.getCol_index()-1, c.getRow_index());
        if(vicino!=null)
            vicini.add(vicino);

        vicino = cellIndex(c.getCol_index()+1, c.getRow_index());
        if(vicino!=null)
            vicini.add(vicino);

        if(!vicini.isEmpty())
            return  vicini.get(new Random().nextInt(vicini.size()));
        else
            return null;
    }

    public ArrayList<Integer> getAllConnectedCell(Cell c){
        ArrayList<Integer> vicini = new ArrayList<Integer>();

        Cell vicino = cellIndex(c.getCol_index(), c.getRow_index()-1);
        if(vicino!=null && !c.getWalls().get(0).isActive())
            vicini.add(index(c.getCol_index(), c.getRow_index()-1));

        vicino = cellIndex(c.getCol_index(), c.getRow_index()+1);
        if(vicino!=null && !c.getWalls().get(2).isActive())
            vicini.add(index(c.getCol_index(), c.getRow_index()+1));

        vicino = cellIndex(c.getCol_index()-1, c.getRow_index());
        if(vicino!=null && !c.getWalls().get(3).isActive())
            vicini.add(index(c.getCol_index()-1, c.getRow_index()));

        vicino = cellIndex(c.getCol_index()+1, c.getRow_index());
        if(vicino!=null && !c.getWalls().get(1).isActive())
            vicini.add(index(c.getCol_index()+1, c.getRow_index()));

        if(!vicini.isEmpty())
            return  vicini;
        else
            return null;
    }

    public Cell cellIndex(int col, int row){
        if(col>=0 && row >=0 && col<cols && row<rows)
        return cells.get(col + row * cols);
        else
        return null;
    }

    public int index(int col, int row){
        if(col>=0 && row >=0 && col<cols && row<rows)
            return col + row * cols;
        else
            return -1;
    }

    public void removeWalls(Cell a, Cell b){
        int x = a.getCol_index() - b.getCol_index();
        if(x==1){
            a.getWalls().get(3).setActive(false);
            b.getWalls().get(1).setActive(false);
        }else if(x==-1){
            a.getWalls().get(1).setActive(false);
            b.getWalls().get(3).setActive(false);
        }

        int y = a.getRow_index() - b.getRow_index();
        if(y==1){
            a.getWalls().get(0).setActive(false);
            b.getWalls().get(2).setActive(false);
        }else if(y==-1){
            a.getWalls().get(2).setActive(false);
            b.getWalls().get(0).setActive(false);
        }
    }

    public void removeRandomWalls(int percentage){
        for(int i = 0; i<(cells.size()*percentage/100); i++){
           Cell rand = cells.get(new Random().nextInt(cells.size()));
           Cell vicino = getRandomNearCell(rand);
           removeWalls(rand, vicino);
        }
    }

    public boolean drawMaze(){
            parent.background(51);
            for(Cell c : getCells()){
                c.show();
            }

            cells.get(0).highlight(0, 255, 0);
            cells.get(cells.size()-1).highlight(255, 0, 0);

            current.setVisited(true);
            current.highlight(0,0,255);

            Cell next = getUnvisitedCell(current);
            if(next!=null){
                stack.push(current);
                removeWalls(current, next);
                current = next;
            }else if(!stack.isEmpty()){
                current = stack.pop();
            }else if(!removedRandom){
                removeRandomWalls(randomWallRemove);
                removedRandom = true;
            }else{
                this.done = true;
                System.out.println("maze done");
                return true;
            }

            return false;
        }


    public void calcMaze(){
        boolean done = false;

        while(!done){
            current.setVisited(true);
            Cell next = getUnvisitedCell(current);
            if(next!=null){
                stack.push(current);
                removeWalls(current, next);
                current = next;
            }else if(!stack.isEmpty()){
                current = stack.pop();
            }else if(!removedRandom){
                removeRandomWalls(randomWallRemove);
                removedRandom = true;
            }else{
                done = true;
            }
        }

        System.out.println("maze calculated");

    }

    @Override
    public String toString() {
        return "Maze{" +
                "width=" + width +
                ", height=" + height +
                ", cols=" + cols +
                ", rows=" + rows +
                ", lato=" + lato +
                ", parent=" + parent +
                ", cells=" + cells +
                '}';
    }
}
