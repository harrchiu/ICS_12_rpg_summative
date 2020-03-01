public class Player{
	int health;
	int coins;
	int attack;
	int[] items;
	int heals;
	int max;

	public Player() {
		max = 100;
		health = 100;
		coins = 0;
		attack = 15;
		items = new int[]{0, 0};	// ID 0 is for boat, ID 1 is for plane
		heals = 2;
	}
}