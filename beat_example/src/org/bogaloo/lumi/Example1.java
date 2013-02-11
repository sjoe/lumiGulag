package org.bogaloo.lumi;


import java.awt.event.KeyEvent;
import java.util.ArrayList;

import processing.core.PApplet;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.effects.LowPassFS;
public class Example1 extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Minim minim;
	private AudioInput mic;
	private FFT fft;
	private String windowName;
	private int bufferSize;
	private AudioInput lowStuff;
	private FFT lowfft;
	private float[] partV;
	private float[] prevBass;
	private float rectThreshold = 30;
	private int lowPassFreq = 400;
	private static final int bassRectCount = 8;
	private static final int[] rect = new int[bassRectCount];
	private float circles;
	private int amp172;
	
	@Override
	public void setup() {
		size(1024, 500);
	    bufferSize = 1024;
	    minim = new Minim(this);
	    mic = minim.getLineIn(Minim.MONO, bufferSize);
	    lowStuff = minim.getLineIn(Minim.MONO, bufferSize);
	    
	    lowStuff.addEffect(new LowPassFS(lowPassFreq, mic.sampleRate()));
	    
	    fft = new FFT(mic.bufferSize(), mic.sampleRate());
	    lowfft = new FFT(mic.bufferSize(), mic.sampleRate());
	    System.out.println("buff-size:" + mic.bufferSize() + " sample-rate:" + mic.sampleRate());
	    textFont(createFont("SanSerif", 12));
	    
	    prevBass = new float[14];
	    circles = 0.0f;
	    
	    
	}
	
	
	@Override
	public void draw() {
		background(153, 153, 153);
		stroke(255);
		// perform a forward FFT on the samples in jingle's left buffer
		// note that if jingle were a MONO file, this would be the same as using jingle.right or jingle.left
		fft.forward(mic.mix);
		for(int i = 0; i < fft.specSize(); i++)
		{
		  // draw the line for frequency band i, scaling it by 4 so we can see it a bit better
		  line(i*2, height, i*2, height - fft.getBand(i)*4);
		}
		fill(255);
		// perform a forward FFT on the samples in jingle's left buffer
		// note that if jingle were a MONO file, this would be the same as using jingle.right or jingle.left
		lowfft.forward(lowStuff.mix);
		color(71, 214, 0,153);

		int x = 0;
		boolean bBass = false;
		line(0, 400-70,1024, 400 - 70);
		for(int i = 0; i < 14/*14 x 40Hz ~ 600Hz*/; i++)
		{
			float h = lowfft.getBand(i)*16;
		    // draw the line for frequency band i, scaling it by 4 so we can see it a bit better
			x = i *75;
			fill(71, 214, 0,153);
			rect(x, 400-h, 74, h) ;
			int fr = (int)lowfft.indexToFreq(i);
			fill(0,0,0,255);
			text(""+fr+"Hz", x+5 , 420);

			if(h > 70){
				bBass = true;
			} 
		}		
		
		draw172HzCircles(bBass);

		int sampleWidth = 200;
		float[] partV = new float[(sampleWidth / bassRectCount) + 1];
		for(int i = 0; i < sampleWidth; i++)
		{
			int index = (int) Math.floor(i / bassRectCount);
			// System.out.println("index="+ i % partSize);
			partV[index] += lowfft.getBand(i);
		}		
	
		
		fill(0, 179, 214,153);
		int bassRectHeight = 100;
		int bassRectWidth = getWidth() / bassRectCount;
		for(int i=0; i < bassRectCount; i++) {
			if(partV[i] > rectThreshold) {
				rect[i] = 100;
			}
			if(rect[i] > 10) {
				rect(i*bassRectWidth, 300-bassRectHeight, bassRectWidth, bassRectHeight) ;
			}
			rect[i] *= 0.95;
		}
		
		
		fill(255,255,255,255);
		// keep us informed about the window being used
		text("high specSize: " + fft.specSize()
				+ "\nAmplitude from 172Hz: " + amp172
				+ "\nlow fft bandwidth of each band: " + lowfft.getBandWidth()
				+ "\nrectThreshold: " + rectThreshold
				+"\npos of the last x: " + x, 5, 20);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			rectThreshold += 2;
		} else if (key == KeyEvent.VK_DOWN){
			rectThreshold -= 2;
		}
	}
	
	
	private void draw172HzCircles(boolean add){
		if(add){
			circles = 100f;
		}
		
		circles = circles * 0.96f;
		ellipse(100, 100, circles, circles);
		if(circles < 10){
			circles = 0f;
		}
				
	}

}
