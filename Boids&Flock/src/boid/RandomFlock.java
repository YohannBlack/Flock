package boid;

import processing.core.*;
import java.util.ArrayList;

public class RandomFlock {

    private PApplet graphicalContext;
    private ArrayList<RandomBoid> flock;

    public RandomFlock(int n, PApplet flockGUI) {
        this.graphicalContext = flockGUI;
        this.flock = new ArrayList<RandomBoid>();
        for (int i = 0; i <= n; i++) addBoid(flockGUI.width / 2, flockGUI.height / 2);
    }

    public void addBoid(int x, int y) {
        flock.add(new RandomBoid(this.graphicalContext, x, y));
    }

    public void run() {
        for (RandomBoid b : flock)
            b.run();
    }
}



