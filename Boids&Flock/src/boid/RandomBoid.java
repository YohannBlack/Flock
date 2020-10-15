package boid;

import processing.core.*;

import java.util.Random;

public class RandomBoid {

    private PVector position;
    private PVector velocity;
    private float maxVel;
    private float maxForce;
    private Random ranGen = new Random();
    private PApplet graphicalContext;

    public RandomBoid(PApplet boidGUI, int x, int y) {
        this.position = new PVector(x, y);
        this.maxVel = 2;
        this.maxForce = 0.05f;

        int a = ranGen.nextInt(1 + 1 + 1) - 1;
        int b = ranGen.nextInt(1 + 1 + 1) - 1;

        this.velocity = new PVector(a, b);
        this.graphicalContext = boidGUI;
    }

    public RandomBoid(int x, int y, float maxVel, float maxForce) {
        this.position = new PVector(x, y);
        this.maxVel = maxVel;
        this.maxForce = maxForce;

        int a = ranGen.nextInt(1 + 1 + 1) - 1;
        int b = ranGen.nextInt(1 + 1 + 1) - 1;

        this.velocity = new PVector(a, b);
    }

    public PVector randomForce(){
        int a = ranGen.nextInt(10 + 10 + 1) - 10;
        int b = ranGen.nextInt(10 + 10 + 1) - 10;

        return new PVector(a, b);
    }

    public void updatePosition(){
        PVector force = randomForce();
        this.velocity.add(force);
        this.velocity.limit(this.maxVel);
        this.position.add(this.velocity);
    }

    public void run() {
        this.updatePosition();
        this.borders();
        this.render();
    }

    public void render() {
        // Draw a triangle rotated in the direction of velocity
        float r = (float) 2.0;
        // this.velocity est la vitesse du Boid
        float theta = this.velocity.heading() + PConstants.PI / 2;
        this.graphicalContext.fill(200, 100);
        this.graphicalContext.stroke(255);
        this.graphicalContext.pushMatrix();
        this.graphicalContext.translate(this.position.x, this.position.y);
        this.graphicalContext.rotate(theta);
        this.graphicalContext.beginShape(PConstants.TRIANGLES);
        this.graphicalContext.vertex(0, -r * 2);
        this.graphicalContext.vertex(-r, r * 2);
        this.graphicalContext.vertex(r, r * 2);
        this.graphicalContext.endShape();
        this.graphicalContext.popMatrix();
    }

    public void borders() {
        float r = (float) 2.0;
        if (this.position.x < -r) {
            this.position.x = graphicalContext.width + r;
        }

        if (this.position.y < -r) {
            this.position.y = graphicalContext.height + r;
        }

        if (this.position.x > graphicalContext.width + r) {
            this.position.x = -r;
        }

        if (this.position.y > graphicalContext.height + r) {
            this.position.y = -r;
        }
    }


}
