package boid;

import processing.core.PApplet;


public class BoidGUI extends PApplet {

    private Flock f;

    public void setup() {
        this.f = new Flock(100, this);
    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {
        this.background(50);
        this.f.run();
    }

    public void mousePressed() {
        this.f.addBoid(mouseX, mouseY);
    }

    public static void main(String[] args) {
        String[] a = { "MAIN" };
        PApplet.runSketch(a, new BoidGUI());
    }
}