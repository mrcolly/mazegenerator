import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;

public class MazeSolver {
    private PApplet parent;
    private Maze maze;

    private ArrayList<Node> path = new ArrayList<Node>();
    private LinkedList<Node> openSet= new LinkedList<Node>();
    private LinkedList<Node> closeSet= new LinkedList<Node>();

    private ArrayList<Node> nodes = new ArrayList<Node>();

    private Node finish;

    public MazeSolver(PApplet parent, Maze maze) {
        this.parent = parent;
        this.maze = maze;

        for(Cell c : maze.getCells()){
            nodes.add(new Node(c));
        }

        for(Node node : nodes){
            ArrayList<Integer> viciniIndex = maze.getAllConnectedCell(node.getCell());
            for(int i : viciniIndex){
                node.addVicino(nodes.get(i));
            }
        }

        this.finish = nodes.get(nodes.size()-1);

        openSet.push(nodes.get(0));
    }

    public void solveMaze(){
    boolean solved = false;
    while(!solved){
        if(!openSet.isEmpty()){
            Node closest=openSet.get(0);
            for(Node n : openSet){
                if(n.getF()<closest.getF()){
                    closest = n;
                }
            }

            if(closest.equals(finish)){
                Node temp = closest;
                while(temp.getPrevious()!=null){
                    path.add(temp.getPrevious());
                    temp = temp.getPrevious();
                }
                solved = true;
                System.out.println("solved");
            }

            openSet.remove(closest);
            closeSet.push(closest);

            calcFGH(closest);
        }
    }


    }

    private void calcFGH(Node closest) {
        for(Node n : closest.getVicini()){

            if(!closeSet.contains(n)){
                int tempG = closest.getG()+1;
                if(openSet.contains(n)){
                    if(tempG< n.getG())
                        n.setG(closest.getG()+1);
                }else{
                    n.setG(closest.getG()+1);
                    openSet.push(n);
                }

                n.setH(heuristic(n.getCell(), finish.getCell()));
                n.setF(n.getG()+n.getH());
                n.setPrevious(closest);
            }
        }
    }

    public int heuristic(Cell a, Cell b){
      return Math.abs(a.getCol_index()-b.getCol_index()) +  Math.abs(a.getRow_index()-b.getRow_index());
      //return (int)PApplet.dist(a.getCol_index(), a.getRow_index(), b.getCol_index(), b.getRow_index());
    };

    public void drawMazeSolver(){

        if(!openSet.isEmpty()){
            Node closest=openSet.get(0);
            for(Node n : openSet){
                if(n.getF()<closest.getF()){
                    closest = n;
                }
            }

            closest.getCell().highlight(255, 255, 255);

            if(closest.equals(finish)){

                parent.noLoop();
                System.out.println("solved");
            }

            openSet.remove(closest);
            closeSet.push(closest);

            calcFGH(closest);
            path.clear();
            Node temp = closest;
            while(temp.getPrevious()!=null){
                path.add(temp.getPrevious());
                temp = temp.getPrevious();
            }
        }

        drawMaze();
    }

    public void drawMaze(){
        for(Node n : nodes){
            if(path.contains(n)){
                n.getCell().highlight(0, 0, 255);
            }else if(closeSet.contains(n)){
                n.getCell().highlight(255, 0, 0);
            }else if(openSet.contains(n)){
                n.getCell().highlight(0, 255, 0);
            }

            for(Wall w : n.getCell().getWalls()){
                w.show();
            }
        }
    }


}


