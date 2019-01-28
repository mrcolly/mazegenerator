import processing.core.*;

import java.awt.*;
import java.util.LinkedList;

public class Wall {
    private boolean active = true;
    private PVector p1;
    private PVector p2;
    private PApplet parent;

    public Wall(PVector p1, PVector p2, PApplet parent) {
        this.p1 = p1;
        this.p2 = p2;
        this.parent = parent;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void show(){
        if(isActive()){
            parent.stroke(255);
            parent.line(p1.x, p1.y, p2.x, p2.y);
        }
    }

}
