import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.lang.reflect.Array;
import java.util.*;
/*


class rohanUpload{		// essentially the main for now
	static Scanner in = new Scanner (System.in);
	public static void main (String[] args){
		String name = "             ";
		String limit = "0123456789";
		char chara = 'b';
		System.out.println("Choose attacker(a) or harvester(h): "); //Make button for this <-- this is just a test
		chara = in.next().charAt(0); 
		System.out.println(chara);
		System.out.println("Enter PLayer name (less than 9 characters pls): ");
		while(limit.compareTo(name) > 0) {
			name = in.nextLine();
			}
		Player me = new Player(name,chara);
	}
}

.//class Player {
	int coins = 100, health = 100, resources = 0;
	String name = "";
	public int rates[] = {5,5}; // Damage/hit, resources/hit
	int [][] map;
	int move;
	static int[] inventory = new int [9]; 
	// inventory: 
	// 0 is Skill Tokens, 
	// 1 is Health Potions, 
	// 2 is Boat, 
	// 3 is car, 
	// 4 is car repair, 
	// 5 is sled, 
	// 6 is winter gear, 
	// 7 is Airplane, 
	// 8 is Super Healing <-- Buttons for this
	public Player(String n, char chara){
		name = n;
		if (chara == 'a'){
			attacker(); 
		}
		if (chara == 'h'){
			harvester();
		}
	}

	public void map1(){
		Map.createMap(0);
		map = Map.getMap();
	}

	public void attacker(){
		rates[0] = 10;
		rates[1] = 2;
		inventory[0] = 2;
		inventory[1] = 5;
	}

	public void harvester(){
		health = 150;
		rates[0] = 2;
		rates[1] = 10;
		inventory[0] = 5;
		inventory[1] = 2;
	}
	public int miningrate(){
		return rates[1];
	}
	public int fightingrate(){
		return rates[0];
	}
}

class Playerinteraction extends Player{
	static Scanner in; 
	static int pos[] = {3,3};
	static int fpos[] = new int [2];
	int mapnum = 0;
	int keys = 0;
	int coinadd = 0;
	String pname = "";
	String code = "";
	String checkcode = "3142";
	int carhealth = 150;
	int sledhealth = 100;
	int tokennums = 0;
	public Playerinteraction(String n, char c){
		super(n,c);
		pname = n;
	}

	public void statup(char c){ //Button for this
		while (tokennums > 0){
			if (c == 'a') if(rates[0] < 20) {rates[0]++; tokennums--;} 
			if (c == 'h') if(rates[1] < 20) {rates[1]++; tokennums--;}
			if (c == 'q') break;
			System.out.println(super.fightingrate()+","+super.miningrate()); //Have a bar for this
		}
	} 
	//--------------------------------------------
	//MOVEMENT / INTERACTIONS
	public void intputs(char c){
		if(c == 'g'){
			heal();
		}
		if(c == 'h'){
			healplus();
		}
		else{
			Playerinteraction.futurePos(c);
		}
	}

	public static void heal(){
		inventory[1]--;
		health += 20;
	}
	public static void healplus(){
		inventory[8]--;
		health += 50;
	}

	public static void futurePos(char c){
		if (mapnum != 4 || mapnum != 6){
			if (c == 'w'){fpos[0] = pos[0]; fpos[1] = pos[1] + 1;}
			if (c == 'a'){fpos[0] = pos[0] - 1; fpos[1] = pos[1];}
			if (c == 's'){fpos[0] = pos[0]; fpos[1] = pos[1] - 1;}
			if (c == 'd'){fpos[0] = pos[0] + 1; fpos[1] = pos[1];}
			//Interaction
			if(map[fpos[0]][fpos[1]] == 0) interact_0();
			if(map[fpos[0]][fpos[1]] == 1) interact_1();
			if(map[fpos[0]][fpos[1]] == 2) interact_2();
			if(map[fpos[0]][fpos[1]] == 3) interact_3();
			if(map[fpos[0]][fpos[1]] == 4) interact_4();
			if(map[fpos[0]][fpos[1]] == 5) interact_5();
			if(map[fpos[0]][fpos[1]] == 6) interact_6();
			if(map[fpos[0]][fpos[1]] == 7) interact_7();
			if(map[fpos[0]][fpos[1]] == 8) interact_8();
			if(map[fpos[0]][fpos[1]] == 9) interact_9();
			if(map[fpos[0]][fpos[1]] == 91) code();
		}
		if (mapnum == 4){
			if (c == 'a'){fpos[0] = pos[0] - 1; fpos[1] = pos[1] + 1;}
			if (c == 'd'){fpos[0] = pos[0] + 1; fpos[1] = pos[1] + 1;}
			if(map[fpos[0]][fpos[1]] == 9) interact_9();
		}
		if (mapnum == 6){
			if (c == 'a'){fpos[0] = pos[0] - 1; fpos[1] = pos[1] - 1;}
			if (c == 'd'){fpos[0] = pos[0] + 1; fpos[1] = pos[1] - 1;}
			if(map[fpos[0]][fpos[1]] == 9) interact_9();
		}
		pos = fpos;//Update player position in map
	}

	public static void interact_0(){
		fpos = pos;
	}

	public static void interact_1(){
		pos = fpos;
		//PLAYER MOVES TO POS
	}

	public static void interact_2(){ //player interacts with resource block things
		resources += rates[1];
		fpos = pos;
	}

	public static void interact_3(){
		if (map[pos[0]][pos[1]] != 3){
			mapnum--;
			Map.createMap(mapnum);
			map = Map.getMap();
			locate_7(map);
			//Move to other map at 7
		}
		else pos = fpos;
	} 

	public static String interact_4(){ //Have buttons like pokemon for the options, Create a pkemon esque battle scene, no moving animation necessary
		char c;
		int zhealth = 50;
		while (zhealth > 0 || health > 0){
			c = in.next().charAt(0); 
			if (c == 'r') {
				if (runAway()){
					return "Ran Away";
				} //Run
				else {
					System.out.println("Could not run away!");
					health -= damageZomb();
				}
			}
			if (c == 'a') {
				zhealth -= rates[0]; 
				if (zhealth > 0){
					health -= damageZomb();
					System.out.println("Your health: "+health+", Zombie health: "+zhealth);
				}
			}
			if (c == 'h') {
				heal();
				health -= damageZomb(); 
				System.out.println("Your health: "+health+", Zombie health: "+zhealth);
			}
			if (c == 'g'){
				healplus();
				health -= damageZomb();
				System.out.println("Your health: "+health+", Zombie health: "+zhealth);
			}
		}
		if(health <= 0) return "OOF";
		if(zhealth <= 0){
			coinadd = coinZomb(); 
			coins+= coinadd; 
			map[fpos[0]][fpos[1]] = 1;
			return "Zombie down: +"+coinadd+" coins!";
		}
		return "You will probably never get here";
	}



	public static Boolean runAway(){
		int rand = 0;
		rand = (int) Math.random()*(20-mapnum*2);
		if (rand == 1)return false;
		else  return true;
	} 

	public static void interact_5(){//SHOP need quite a few buttons
		//ITEMS AND COSTS
		System.out.println("Which item would you like to buy?");
		System.out.println("Skill Token: 300 coins"); //Press 1
		System.out.println("5 Health Potions: 100 resources");//Press 2
		System.out.println("Boat: 100 coin, 100 resources");//Press 3
		System.out.println("Car: 200 coins, 300 resources");//Press 4
		System.out.println("Car repair token: 10 coins, 10 resources");//Press 5
		System.out.println("Sled: 15 coins, 20 resources");//Press 6
		System.out.println("Winter Gear: 100 coins, 50 resources");//Press 7
		System.out.println("Airplane: 10000 coin, 10000 resources");//Press 8 WRITE UNDER IT AS EASY WIN
		System.out.println("5 Super Healing: 200 resources");

		int snum = in.nextInt(); //REPLACE THIS WITH BUTTONS

		if (snum == 1){ // Skill tokens
			if (coins>= 400){
				inventory[0]++;
				coins-=400;
				System.out.println("You have: "+coins+" coins left");
			}
		}
		if (snum == 2){ // Health pots
			if (resources >= 100){
				inventory[1] += 5;
				resources -= 100;
				System.out.println("You have: "+resources+" resources left");
			}
		}
		if (snum == 3){ //Boats
			if (coins>= 300 && resources>=100){
				inventory[2]++;
				coins-=300;
				resources -= 100;
				System.out.println("You have: "+coins+" coins left and "+resources+" resources left");
			}
		}
		if (snum == 4){//Car
			if(coins>=200 && resources >=300){
				inventory[3]++;
				coins -= 200;
				resources -= 300;
				System.out.println("You have: "+coins+" coins left and "+resources+" resources left");
			}
		}
		if (snum == 5){//Car repair token
			if(coins>=10 && resources >=10){
				inventory[4]++;
				coins -= 10;
				resources -= 10;
				System.out.println("You have: "+coins+" coins left and "+resources+" resources left");
			}
		}
		if (snum == 6){//Sled
			if(coins>=15 && resources >=20){
				inventory[5]++;
				coins -= 15;
				resources -= 20;
				System.out.println("You have: "+coins+" coins left and "+resources+" resources left");
			}
		}
		if (snum == 7){//Winter Gear
			if(coins>=100 && resources >=50){
				inventory[6]++;
				coins -= 100;
				resources -= 50;
				System.out.println("You have: "+coins+" coins left and "+resources+" resources left");
			}
		}
		if (snum == 8){//Airplane
			if(coins>=10000 && resources >=10000){
				inventory[7]++;
				coins -= 10000;
				resources -= 10000;
				System.out.println("You have: "+coins+" coins left and "+resources+" resources left");
			}
		}
		if(snum == 9){
			if(resources >= 200){ 
				inventory[8]++;
				resources -= 200;
				System.out.println("You have: "+resources+" resources left");
			}
		}
	}
	public static void interact_6(){
		fpos = pos;
	}

	public static void interact_7(){
		if (map[pos[0]][pos[1]] != 7){
			mapnum++;
			Map.createMap(mapnum);
			map = Map.getMap();
			locate_3(map);
			//Move to other map at 7
		}
		else pos = fpos;
	} 

	public static String interact_8(){
		char c;
		int zlhealth = 100;
		while (zlhealth > 0 || health > 0){
			c = in.next().charAt(0); 
			if (c == 'r') {
				if (runAway()){
					return "Ran Away";
				} //Run
				else {
					System.out.println("Could not run away!");
					health -= damageZomb()*2;
				}
			}
			if (c == 'a') {
				zlhealth -= rates[0]; 
				if (zlhealth > 0){
					health -= damageZomb()*2;
					System.out.println("Your health: "+health+", Big Zombie health: "+zlhealth);
				}
				if (c == 'h') {
					heal();
					health -= damageZomb()*2; 
					System.out.println("Your health: "+health+", Big Zombie health: "+zlhealth);
				}
				if (c == 'g'){
					healplus();
					health -= damageZomb();
					System.out.println("Your health: "+health+", Zombie health: "+zlhealth);
				}
			}
			if(health <= 0) return "OOF";
			if(zlhealth <= 0){
				coinadd = coinZomb()*3; 
				coins+= coinadd; 
				map[fpos[0]][fpos[1]] = 1;
				keys++;
				return "Big Zombie down: +"+coinadd+" coins!\nYou got a key! You now have "+keys+" keys.";
			}
		}
		return "You will probably never get here";

	}

	public static void interact_9(){
		if (mapnum == 0){
			//TUTORIAL STUFF
			interact_4();
		}
		if (mapnum == 1){
			if ((fpos[0] == 6 || fpos[0] == 7 || fpos[0] == 8 || fpos[0] == 9) && fpos[1] == 1){ 
				System.out.println("Complete the game you must get resources for harvester that are above and below you. To get coins you must battle zombies. To get through this specific level you must be able to get through the maze.");
			}
			else{
				System.out.println("I recommend getting atleast 200 coins and 200 resources before going past this point.");
				if (coins<200 && resources<200){
					fpos = pos;
				}
			}
		}
		if (mapnum == 2){
			if ((fpos[0] == 1 || fpos[0] == 2 || fpos[0] == 3 || fpos[0] == 4) && fpos[1] == 1){ 
				System.out.println("To get through this level you need to buy a boat, for information about cost. Please visit the shop.");
			}
			else{
				if(inventory[2]<=0){
					fpos = pos;
				}
			}
		}
		if(mapnum == 3){
			if ((fpos[0] == 8 || fpos[0] == 9 || fpos[0] == 10) && fpos[1] == 1){
				System.out.println("To get through this level, you will need to solve the 4 digit code. Hint: The date is: March, 14 2002?"); //Code is 3142
			}
			if((fpos[0] == 5 || fpos[0] == 6) && fpos[1] == 23){
				if (!code.equals(checkcode)){
					fpos = pos;
					System.out.println("Wrong Code. Code has been Reset. Please Try Again.");
					code = "";
				}
			}
			if((fpos[0] == 5 || fpos[0] == 6) && fpos[1] == 27){
				if (inventory[6] == 0){
					System.out.println("Please buy some winter gear and a car.");
					fpos = pos;
				}     
			}
		}
		if(mapnum == 4){
			carhealth -= 20;
			if (carhealth <= 0){
				System.out.println("Your car got too much damage. Please repair the car to continue."); //Returns player to map prior
				mapnum--;
				Map.createMap(mapnum);
				map = Map.getMap();
				locate_3(map);
				fpos = pos;
			}
		}
		if(mapnum == 5){
			if((fpos[0] == 1 || fpos[0] == 2) && fpos[1] == 4){
				if(inventory[6] == 0){
					System.out.println("Please buy winter gear.");
					fpos = pos;
				}
			}
			else{
				if(inventory[5] == 0){
					System.out.println("To get past this point, please buy a sled.");
					fpos = pos;
				}
			}
		}
		if(mapnum == 6){
			sledhealth -= 15;
			if (sledhealth <= 0){
				System.out.println("Your sled got too much damage and broke. Please buy another sled."); //Returns player to map prior
				mapnum--;
				Map.createMap(mapnum);
				map = Map.getMap();
				locate_3(map);
				fpos = pos;
			}
		}
		if(mapnum == 7){
			if ((fpos[0] == 13 || fpos[0] == 14 || fpos[0] == 15 || fpos[0] == 16) && fpos[1] == 6){
				System.out.println("This is the final level before the zombie boss. To get to the zombie boss you have to defeat all of Big Zombies and get 7 keys.");
			}
			else{
				if (keys < 7){
					System.out.println("You are missing "+(7-keys)+" keys.");
					fpos = pos;
				}
				else{
					System.out.println("The Boss is up ahead be ready to fight some more zombies and Big Zombies.");
				}
			}
		}
		if(mapnum == 9){
			System.out.println("Thanks for playing the game. It would be really nice if you could give us a 100 on this. Thanks!");
			inventory[7]++;
			System.out.println("An Airplane was added to your inventory. You may now fly to any map you wish.\n To select the map click on one of the buttons.");
			mapnum = in.nextInt();
		}
	}

	public static String interact_10(){
		char c;
		int zbhealth = 200;
		while (zbhealth > 0 || health > 0){
			c = in.next().charAt(0); 
			if (zbhealth < 30){
				int lol = (int) Math.random()*5;
				if (lol == 1) zbhealth += rates[0]-3;
			}
			if (c == 'r') {
				if (runAway()){
					return "Ran Away";
				} //Run
				else {
					System.out.println("Could not run away!");
					health -= damageZomb()*2;
				}
			}
			if (c == 'a') {
				zbhealth -= rates[0]; 
				if (zbhealth > 0){
					health -= damageZomb()*2;
					System.out.println("Your health: "+health+", Boss Zombie health: "+zbhealth);
				}
			}
			if (c == 'h') {
				heal();
				health -= damageZomb()*2; 
				System.out.println("Your health: "+health+", Boss Zombie health: "+zbhealth);
			}
			if (c == 'g'){
				healplus();
				health -= damageZomb();
				System.out.println("Your health: "+health+", Zombie health: "+zbhealth);
			}
		}
		if(health <= 0) return "OOF";
		if(zbhealth <= 0){
			coinadd = coinZomb()*3; 
			coins+= coinadd; 
			map[fpos[0]][fpos[1]] = 1;
			return "Boss Zombie down: +"+coinadd+" coins!\nContinue through the level to get to Australia.";
		}
		return "You will probably never get here";
	}




	public static void code(){ // Code is 3142
		while(code.length()<4){
			if (fpos[0] == 6 && fpos[1] == 15){code = ""; System.out.println("Code Reset");}
			if (fpos[0] == 3 && fpos[1] == 20)code += "1";
			if (fpos[0] == 5 && fpos[1] == 20)code += "2";
			if (fpos[0] == 7 && fpos[1] == 20)code += "3";
			if (fpos[0] == 9 && fpos[1] == 20)code += "4";
		}
	}

	
	//--------------------------------------------
	//Zombie Damage and Drops
	public static int damageZomb(){
		return (int)Math.random()*(10+(mapnum*2))+2;
	}
	public static int coinZomb(){
		return (int)Math.random()*(5+(mapnum*7))+7;
	}
}

*/

