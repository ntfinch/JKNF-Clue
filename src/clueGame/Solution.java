package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	@Override
	public boolean equals(Object obj) {
		return 
				((Solution)obj).person == person &&
				((Solution)obj).room == room &&
				((Solution)obj).weapon == weapon;
	}
}
