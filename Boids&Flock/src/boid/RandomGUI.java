package boid;

import processing.core.*;

public class RandomGUI extends PApplet {

	private RandomFlock f;

	public void setup() {
		this.f = new RandomFlock(100, this);
	}

	public void draw() {
		this.background(50);
		this.f.run();
	}

	public void settings() {
		size(1200, 800);
	}

	public void mousePressed() {
		this.f.addBoid(mouseX, mouseY);
	}

	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new RandomGUI());
	}
}