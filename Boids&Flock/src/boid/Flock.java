package boid;

import processing.core.*;
import java.util.ArrayList;

public class Flock {

    private PApplet graphicalContext;
    private ArrayList<Boid> flock;

    public Flock(int n, PApplet flockGUI) {
        this.graphicalContext = flockGUI;
        this.flock = new ArrayList<Boid>();
        for (int i = 0; i <= n; i++)
            addBoid((int)flockGUI.random(flockGUI.width), (int)flockGUI.random(flockGUI.height));
    }

    public void addBoid(int x, int y) {
        Boid b = new Boid(this.graphicalContext, this, x, y);
        this.flock.add(b);
    }

    public ArrayList<Boid> neighbors(Boid b, double neighborDist){
        ArrayList<Boid> neigh = new ArrayList<Boid>();

        for(Boid bird : flock)
            if (bird.distance(b) < neighborDist)
                neigh.add(bird);

        return neigh;
    }

    public void run() {
        for (Boid b : flock)
            b.run();
    }
}