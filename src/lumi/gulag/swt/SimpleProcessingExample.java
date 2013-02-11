package lumi.gulag.swt;

import processing.core.*;

//Launch this, the Processing thingy. This will open the UI thingy.

@SuppressWarnings("serial")
public class SimpleProcessingExample extends PApplet {
	public void setup() {
		size(300, 300);
		background(255);
		fill(0);
	}

	// position is public, to make it accessible from SwtUI
	public int position = 30;

	public void draw() {
		background(255);

		ellipse(position, 150, 25, 25);
	}
}