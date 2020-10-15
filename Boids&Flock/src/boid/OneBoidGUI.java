package boid;

import processing.core.*;


public class OneBoidGUI extends PApplet {

	private RandomBoid b;

	public void setup() {
		b = new RandomBoid(this, 600, 400);
	}

	public void draw() {
		this.background(50);
		this.b.run();
	}

	public void settings() {
		size(1200, 800);
	}

	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new OneBoidGUI());
	}
}