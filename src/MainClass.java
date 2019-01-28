import processing.core.*;

import java.util.LinkedList;

public class MainClass extends PApplet{
    Maze maze;
    MazeSolver solver;
    boolean createMaze = false;

    public static void main(String[] args) {
        PApplet.main("MainClass");
    }

    public void settings(){
        size(1000,600);
    }


    public void setup() {
        maze = new Maze(width, height, 20, 8,this);
        //solveMazeSequenca();
    }

    public void draw() {
       drawMazeSequence(1);
    }

    private void drawMazeSequence(int arg){

        if(!createMaze){
            if(arg == 1)
                maze.calcMaze();

                createMaze = maze.drawMaze();

        }else{
            if(solver == null){
                solver = new MazeSolver(this,maze);
            }
            solver.drawMazeSolver();
        }

    }

    private void solveMazeSequenca(){
        maze.calcMaze();
        solver = new MazeSolver(this,maze);
        maze.drawMaze();
        solver.solveMaze();
        solver.drawMaze();
    }
}
