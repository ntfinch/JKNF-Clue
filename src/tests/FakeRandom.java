package tests;

import java.util.Random;

@SuppressWarnings("serial")
public class FakeRandom extends Random {
	
	private int value;

	public FakeRandom(int value) {
		super();
	}
	
	@Override
	public int nextInt(){
		return value;
	}
	
	@Override
	public int nextInt(int bound){
		return value;
	}
}
