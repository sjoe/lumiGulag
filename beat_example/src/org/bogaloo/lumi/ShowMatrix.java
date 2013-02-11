package org.bogaloo.lumi;

import java.awt.Color;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;

import processing.core.PApplet;
import processing.core.PGraphics;



public class ShowMatrix extends PApplet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Random ram;
	private ArrayList<Circle> circles;
	private String usb_name = "/dev/cu.usbmodemfd121";
	private File usb;
	private AudioInput mic;
	private Minim minim;
	private int bufferSize;
	private BeatDetect beat;

	@Override
	public void setup() {
		setSize(800,800);
		circles = new ArrayList<Circle>();
		ram = new Random();
		frameRate(25);
		usb = new File(usb_name);
		
	    bufferSize = 1024;
	    minim = new Minim(this);
	    mic = minim.getLineIn(Minim.MONO, bufferSize);
	    beat = new BeatDetect(mic.bufferSize(), mic.sampleRate());
		
	}

	
	@Override
	public void draw() {
		beat.detect(mic.mix);
		background(0,0,0);
		if(beat.isKick()){
			circles.add(new Circle(32f, ram.nextInt(),abs( ram.nextInt()%32 ), abs(ram.nextInt()%32)));
		}
		
		
		ArrayList<Circle> toBeRemoved = new ArrayList<Circle>();
		for(Circle c : circles){
			fill(c.getColor());
			stroke(0);
			ellipse(c.getX(), c.getY(), c.getRadius(), c.getRadius());
			c.setRadius(c.getRadius()*0.96f);
			if(c.getRadius() <= 1){
				toBeRemoved.add(c);
			}
		}
		circles.removeAll(toBeRemoved);
		if(frameCount > 2 ){
			int[] pixels = getPixels();
			if(usb.canWrite()){
				FileOutputStream fout;
				try {
					fout = new FileOutputStream(usb);
					for(int y = 0; y < 32; y++){
						for(int x = 0; x < 32; x++){
							int index = y%2 == 0 ? y*32+x : y*32+(31-x); 
							Color c = new Color(pixels[index]);
							byte[] colors = new byte[3];
							colors[0] =(byte)c.getGreen();
							colors[1] = (byte)c.getRed();
							colors[2] = (byte)c.getBlue();
							fout.write(colors);
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
//		fill(0);
//		background(127,127,127);
//		text("Lumi Simulator", 10, 10);
//		int index = 0;
//		for(int i=0; i < 32; i++){
//			for(int j = 0; j < 32; j++){
//				fill(colors[index]);
//				rect(xOffset + j * width * 2, yOffset + i * width * 2, width, height);
//				index++;
//			}
//		}
		
		
		
	}
	
	
}
