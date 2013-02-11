package org.bogaloo.lumi;

public class LED {
	
	private byte _r;
	private byte _g;
	private byte _b;
	
	
	public LED(byte r, byte g, byte b){
		_r = r;
		_g = g;
		_b = b;
	}
	
	public void setColor(byte r, byte g, byte b){
		_r = r;
		_g = g;
		_b = b;
	}
	
	
	public byte[] getColor(){
		return new byte[]{_r,_g,_b};
	}

}
