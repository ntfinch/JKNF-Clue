package clueGame;

public class Solution {
	private String person;
	private String room;
	private String weapon;
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	public Solution(Card person, Card room, Card weapon) {
		this(person.getName(), room.getName(), weapon.getName());
	}

	public String getPerson() {
		return person;
	}

	public String getRoom() {
		return room;
	}

	public String getWeapon() {
		return weapon;
	}
}
