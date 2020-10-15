package boid;

import com.sun.org.apache.xpath.internal.axes.OneStepIterator;
import processing.core.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Boid {

    private PVector position;
    private PVector velocity;
    private float maxVel;
    private float maxForce;
    private Flock flock;
    private Random ranGen = new Random();
    private PApplet graphicalContext;

    public Boid(PApplet boidGUI, Flock f, int x, int y) {
        this.position = new PVector(x, y);
        this.maxVel = 2.0f;
        this.maxForce = 0.05f;

        this.velocity = new PVector(ranGen.nextInt(3) - 1, ranGen.nextInt(3) - 1);
        this.graphicalContext = boidGUI;
        this.flock = f;
    }

//    public Boid(int x, int y, float maxVel, float maxForce) {
//        this.position = new PVector(x, y);
//        this.maxVel = maxVel;
//        this.maxForce = maxForce;
//
//        int a = ranGen.nextInt(1 + 1 + 1) - 1;
//        int b = ranGen.nextInt(1 + 1 + 1) - 1;
//
//        this.velocity = new PVector(a, b);
//    }

    public PVector randomForce(){
        int a = ranGen.nextInt(10 + 10 + 1) - 10;
        int b = ranGen.nextInt(10 + 10 + 1) - 10;

        return new PVector(a, b);
    }

    public void updatePosition(){
        PVector acceleration = new PVector();

        acceleration.add(this.align());
        acceleration.add(this.separate().mult(1.5f));
        acceleration.add(this.cohesion());

        this.velocity.add(acceleration);
        this.velocity.limit(this.maxVel);
        this.position.add(this.velocity);

        acceleration.mult(0);
    }

    public float distance(Boid b){
        return this.position.dist(b.position);
    }


    public PVector align() {
        PVector alignement = new PVector();
        ArrayList<Boid> nearBy = this.flock.neighbors(this, 25.0);

            for (Boid other : nearBy) {
                alignement.add(other.velocity);
            }

            alignement.div(nearBy.size());

            if (alignement.mag() > 0) {
                alignement.normalize();
                alignement.mult(this.maxVel);
                alignement.sub(this.velocity);
                alignement.limit(this.maxForce);
                return alignement;
            }

        return new PVector (0,0);
    }


    public PVector separate(){
        PVector separation = new PVector();
        ArrayList<Boid> birds = this.flock.neighbors(this, 20.0);

        for(Boid b : birds){
            PVector diffPos = PVector.sub(this.position, b.position);
            diffPos.normalize();
            if(this.distance(b) != 0)
                diffPos.div(this.distance(b));

            separation.add(diffPos);
        }

        separation.div(birds.size());

        if(separation.mag() > 0){
            separation.normalize();
            separation.mult(this.maxVel);
            separation.sub(this.velocity);
            separation.limit(this.maxForce);
            return separation;
        }

        return new PVector(0,0);
    }





//    private PVector separate(){
//        PVector res = new PVector(0, 0);
//        ArrayList<Boid> neighbors = this.flock.neighbors(this, 20.0);
//        if(neighbors.size() == 0)
//            return res;
//        for(Boid b : neighbors){
//            PVector temp = PVector.sub(this.position, b.position);
//            temp.normalize();
//            temp.div((float) b.distance(this));
//            res.add(temp);
//        }
//        res.div(neighbors.size());
//        if(res.mag() > 0) {
//            res.normalize();
//            res.mult(this.maxVel);
//            res.sub(this.velocity);
//            res.limit(this.maxForce);
//        }
//        return res;
//    }


    public PVector steer(PVector target, boolean slowDown) {
        PVector desired = PVector.sub(target, this.position);
        if (desired.mag() <= 0) {
            return new PVector(0, 0);
        }

        desired.normalize();
        if (slowDown && desired.mag() < 100.0) {
            desired.mult((float) (this.maxVel * (desired.mag() / 100.0)));
        } else {
            desired.mult(this.maxVel);
        }

        PVector steeringVector = PVector.sub(desired, this.velocity);
        steeringVector.limit(this.maxForce);
        return steeringVector;
    }

    public PVector cohesion() {
        PVector cohesion = new PVector();
        ArrayList<Boid> nearBy = this.flock.neighbors(this, 25.0);

        if(nearBy.size() != 0) {

            for (Boid b : nearBy)
                cohesion.add(b.position);

            cohesion.div(nearBy.size());

            return this.steer(cohesion, false);
        }
        return new PVector(0,0);
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
