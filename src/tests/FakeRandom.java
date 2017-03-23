package tests;

import java.util.Random;

@SuppressWarnings("serial")
public class FakeRandom extends Random {
	
	private int[] values;
	int pos;

	public FakeRandom(int[] values) {
		this.values = values;
		pos = -1;
	}
	
	public FakeRandom(int value){
		this.values = new int[1];
		values[0] = value;
		pos = -1;
	}
	
	@Override
	public int nextInt(){
		pos++;
		if(pos >= values.length){
			pos = 0;
		}
		return values[pos];
	}
	
	@Override
	public int nextInt(int bound){
		//Can return things greater than bound, but if it does it should have a different list of values
		return nextInt();
	}
}
