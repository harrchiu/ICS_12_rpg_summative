import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



class GUIComp1 extends JFrame implements ActionListener{

	Map map;
	Player player;
	JTextArea tA;
	JScrollPane tAScroll;
	JTextField enterTF;
	int curEnemy = 0;		// track enemy health
	boolean isInBattle = false;
	boolean isInShop = false;
	boolean didSquare = false;
	boolean didZomb = false;
	boolean didOhNo = false;
	public GUIComp1(Map inMap, Player player)
	{
		this.map = inMap;
		this.player = player;
		// 1... Create/initialize components
		JButton upBtn = new JButton ("W");							
		JButton leftBtn = new JButton ("A");							
		JButton downBtn = new JButton ("S");					
		JButton rightBtn = new JButton ("D");							
		JButton enterBtn = new JButton ("Enter");							

		upBtn.addActionListener (this);
		leftBtn.addActionListener (this);
		downBtn.addActionListener (this);
		rightBtn.addActionListener (this);
		enterBtn.addActionListener (this);

		// 2... Create content pane, set layout
		JPanel left = new JPanel ();       							    // Create a content pane
		left.setLayout (new BorderLayout ()); 							// Use BorderLayout for panel

		JPanel right = new JPanel (); 
		right.setLayout (new BoxLayout (right,BoxLayout.Y_AXIS)); 		// flow downwards

		//JPanel tfPanel = new JPanel (); 									// panel for tf
		tA = new JTextArea ("Event Log:\n", 30, 20); 	// the tf without scroll	
		tA.setEditable(false);
		tA.setLineWrap(true);

		tAScroll = new JScrollPane(tA);						// adding scroll
		tAScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		enterTF = new JTextField(3);

		JPanel btnRow1 = new JPanel (); 
		btnRow1.setLayout (new FlowLayout()); 						// buttons flow across
		JPanel btnRow2 = new JPanel (); 
		btnRow2.setLayout (new FlowLayout()); 						// buttons flow acorss
		JPanel btnRow3 = new JPanel (); 
		btnRow3.setLayout (new FlowLayout()); 						// buttons flow acorss

		// 3... Add the components to the input area.

		// LEFT (MAP) AREA
		DrawArea mapArea = new DrawArea(490,490);		// draw the graph for the first time
		left.add(mapArea, "West");

		// RIGHT AREA
		btnRow1.add (upBtn);
		btnRow2.add (leftBtn);
		btnRow2.add (downBtn);
		btnRow2.add (rightBtn);
		btnRow3.add(enterTF);
		btnRow3.add(enterBtn);
		right.add(tAScroll);
		right.add(btnRow1);
		right.add(btnRow2);
		right.add(btnRow3);

		// MAIN PANEL
		JPanel mainWindow = new JPanel ();       	 // Create a main pane
		mainWindow.setLayout (new FlowLayout());	 // left to right
		mainWindow.add(left);
		mainWindow.add(right, "East");

		// 4... Set this window's attributes.
		setContentPane (mainWindow);
		pack ();
		setTitle ("Game Window");
		setSize (900, 700);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo (null);           // Center window.
	}

	public void addTfString(String adding) {
		tA.setText(tA.getText() + "\n" + adding);
	}

	public void printShop () {
		String ownBoat = "do not ";
		String ownPlane = "do not ";
		if (player.items[0] == 1)
			ownBoat = "";
		if (player.items[1] == 1)
			ownPlane = "";

		tA.setText(tA.getText() + "\n" + "\n\n\nWELCOME TO THE SHOP!");
		tA.setText(tA.getText() + "\n" + "Your coins: " + Integer.toString(player.coins));
		tA.setText(tA.getText() + "\n" + "Your health: " + Integer.toString(player.health) + "/" + Integer.toString(player.max));
		tA.setText(tA.getText() + "\n" + "Your heals: " + Integer.toString(player.heals));
		tA.setText(tA.getText() + "\n" + "Your attack: " + Integer.toString(player.attack));
		tA.setText(tA.getText() + "\n" + "You " + ownBoat + "own a boat.");
		tA.setText(tA.getText() + "\n" + "You " + ownPlane + "own a plane.");

		tA.setText(tA.getText() + "\n" + "\nType letter in field and press enter to buy.\n---");
		tA.setText(tA.getText() + "\n" + "\nh: 1 heal (100 coins)");
		tA.setText(tA.getText() + "\n" + "a: + 1 attack (200 coins)");
		tA.setText(tA.getText() + "\n" + "m: + 10 max health (250 coins)");
		tA.setText(tA.getText() + "\n" + "b: 1 boat (400 coins)");
		tA.setText(tA.getText() + "\n" + "p: 1 plane (800 coins)\n");
	}
	public void actionPerformed (ActionEvent e)
	{
		// if the player is in battle, only check for enterTF
		if (isInBattle) {

			if (e.getActionCommand ().equals ("Enter")){
				System.out.println(enterTF.getText());
				if (enterTF.getText().equals("a")) {

					// deal damage for player to zombie
					int dmg = player.attack*((int)Math.random()*5 + 1);
					curEnemy -= dmg;

					if (Math.random() < 0.20) {		// critical hits
						curEnemy -= dmg;
						tA.setText(tA.getText() + "\n" + "\n\nYou dealt " + Integer.toString(dmg*2) + " damage!");
						tA.setText(tA.getText() + "\n" + "Critical hit!!!");
					}
					else {		// normal hits
						tA.setText(tA.getText() + "\n" + "\n\nYou dealt " + Integer.toString(dmg) + " damage!");
					}

					if (curEnemy <= 0) {  // if player won
						int drop = map.mapnum*((int) (Math.random()*20 + 2));
						player.coins += drop;
						tA.setText(tA.getText() + "\n" + "\n\nThe enemy has been defeated!");
						tA.setText(tA.getText() + "\n" + "It dropped " + Integer.toString(drop) + " coins.");
						tA.setText(tA.getText() + "\n" + "You now have " + Integer.toString(player.coins) + " coins.");
						tA.setText(tA.getText() + "\n" + "You can move now.");

						// set previous enemy square as grass tile
						this.map.getMap()[map.playerY][map.playerX] = 1;
						isInBattle = false;
						repaint();
						return;
					}
				}
				else if (enterTF.getText().equals("h")) {		// healing
					if (player.heals > 0) {
						player.health = player.max;
						player.heals --;
						tA.setText(tA.getText() + "\n" + "\n\n\nHealth successfully replenished.");
					}
					else
						tA.setText(tA.getText() + "\n" + "\n\n\nYou do not have any heals left!");
				}
				else if (enterTF.getText().equals("r")) {		// trying to run away
					if (Math.random() < .3) {
						tA.setText(tA.getText() + "\n" + "\n\n\nSuccessfully ran away.");
						tA.setText(tA.getText() + "\n" + "You can move now.");
						isInBattle = false;
						return;
					}
					else {
						tA.setText(tA.getText() + "\n" + "\n\n\nYou tried to run away, but the enemy");
						tA.setText(tA.getText() + "\n" + "stopped you!");
					}
				}
				else {
					tA.setText(tA.getText() + "\n" + "\nInvalid option.");
				}
				if (enterTF.getText().equals("a") || enterTF.getText().equals("h") || enterTF.getText().equals("r")) {
					// deal damage for zombie to player
					int dmg = map.mapnum*((int) (Math.random()*2 + 1));
					player.health -= dmg;

					tA.setText(tA.getText() + "\n" + "The enemy dealt " +  Integer.toString(dmg) + " damage.");

					if (player.health <= 0) {  // if enemy won
						tA.setText(tA.getText() + "\n" + "\n--\nYou fainted!");
						tA.setText(tA.getText() + "\n" + "The enemy must have brought you \nback to the start of the map...");
						tA.setText(tA.getText() + "\n" + "Your health has been restored.");

						// reset player position on current map
						int[] newCoor = map.locate_3(map.createMap(map.mapnum));

						// reheal player 
						player.health = 100;
						// set new position
						map.playerX = newCoor[0];
						map.playerY = newCoor[1];
						isInBattle = false;
						repaint();
						return;
					}

					tA.setText(tA.getText() + "\n" + "-----\nYour health: " + Integer.toString(player.health)  + "/" + Integer.toString(player.max));
					tA.setText(tA.getText() + "\n" + "Enemy health: " + Integer.toString(curEnemy));

					tA.setText(tA.getText() + "\n" + "-----\nWhat will you do? \n(type letter in field and press enter)");
					tA.setText(tA.getText() + "\n" + "\na - attack");
					tA.setText(tA.getText() + "\n" + "h - heal (" + Integer.toString(player.heals) + " remaining)");
					tA.setText(tA.getText() + "\n" + "r - run");
				}
			}
			return;
		}		

		// if the player is in shop, check for enterTF first and do appropriate action
		if (isInShop) {
			if (e.getActionCommand ().equals ("Enter")){
				if (enterTF.getText().equals("h")) {
					if (player.coins >= 100) {
						player.heals ++;
						player.coins -= 100;
						tA.setText(tA.getText() + "\n" + "You now have " + Integer.toString(player.heals) + " heals.");
						tA.setText(tA.getText() + "\n" + Integer.toString(player.coins) +" coins left.\n");
					}
					else
						tA.setText(tA.getText() + "\n" + "Inadequate coins for that.");
				}
				else if (enterTF.getText().equals("a")) {
					if (player.coins >= 200) {
						player.attack ++;
						player.coins -= 200;
						tA.setText(tA.getText() + "\n" + "Your attack is now " + Integer.toString(player.attack) + ".");
						tA.setText(tA.getText() + "\n" + Integer.toString(player.coins) +" coins left.\n");
					}
					else
						tA.setText(tA.getText() + "\n" + "Inadequate coins for that.");
				}
				else if (enterTF.getText().equals("m")) {
					if (player.coins >= 250) {
						player.max += 10;
						player.coins -= 250;
						tA.setText(tA.getText() + "\n" + "Your max health is now " + Integer.toString(player.max) + ".");
						tA.setText(tA.getText() + "\n" + Integer.toString(player.coins) +" coins left.\n");
					}
					else
						tA.setText(tA.getText() + "\n" + "Inadequate coins for that.\n");
				}
				else if (enterTF.getText().equals("b")) {
					if (player.items[0] == 1) {
						tA.setText(tA.getText() + "\n" + "You already have a boat.\n");
						return;
					}
					if (player.coins >= 400) {
						player.items[0] = 1;
						player.coins -= 400;
						tA.setText(tA.getText() + "\n" + "Bought a boat.");
						tA.setText(tA.getText() + "\n" + Integer.toString(player.coins) +" coins left.\n");
					}
					else
						tA.setText(tA.getText() + "\n" + "Inadequate coins for that.\n");
				}
				else if (enterTF.getText().equals("p")) {
					if (player.items[1] == 1) {
						tA.setText(tA.getText() + "\n" + "You already have a plane.\n");
						return;
					}
					if (player.coins >= 800) {
						player.items[1] = 1;
						player.coins -= 800;
						tA.setText(tA.getText() + "\n" + "Bought a plane.");
						tA.setText(tA.getText() + "\n" + Integer.toString(player.coins) +" coins left.\n");
					}
					else
						tA.setText(tA.getText() + "\n" + "Inadequate coins for that.");
				}
				else			
					tA.setText(tA.getText() + "\n" + "Invalid option.");
				return;

			}
		}
		// buttons for moving
		if (e.getActionCommand ().equals ("W")){
			map.moveUp();
		}
		else if (e.getActionCommand ().equals ("A")){
			map.moveLeft();
		}
		else if (e.getActionCommand ().equals ("S")){
			map.moveDown();
		}
		else if (e.getActionCommand ().equals ("D")){
			map.moveRight();
		}
		System.out.println("x: " + map.playerX + " y: " + map.playerY);

		// check if player should be moved to previous or following map (call map class and take map data field created in beginning)
		if (map.getMap()[map.playerY][map.playerX] == 3) {		// go to last map

			// get coordinates for starting next map (enter current map num - 1)
			int[] newCoor = map.locate_7(map.createMap(map.mapnum-1));

			// set new coordinates for next map (starting where you would leave the previous map, at tile '7')
			map.playerX = newCoor[0];
			map.playerY = newCoor[1];

			// set the current map (to be displayed) as the subsequent one to the current
			this.map.map = map.createMap(map.mapnum-1);

			System.out.println("MOVED back TO" + "x: " + map.playerX + " y: " + map.playerY);
			// update the map number currently on
			map.mapnum --;

		}
		else if (map.getMap()[map.playerY][map.playerX] == 7) {		// go to next map

			// do not let player cross until boat is bought for 3
			if (map.mapnum == 3 && player.items[0] == 0) {
				addTfString("\nIt looks like there's water to cross...");
				addTfString("A boat could be useful.");
				return;
			}
			// do not let player cross until plane is bought for 5
			if (map.mapnum == 5 && player.items[0] == 0) {
				addTfString("\nThe land runs out just about here...");
				addTfString("A plane could be useful.");
				return;
			}
			// get coordinates for starting next map (enter current map num + 1)
			int[] newCoor = map.locate_3(map.createMap(map.mapnum+1));

			// set new coordinates for next map (starting where you would leave the previous map, at tile '7')
			map.playerX = newCoor[0];
			map.playerY = newCoor[1];

			// set the current map (to be displayed) as the subsequent one to the current
			this.map.map = map.createMap(map.mapnum+1);
			// update the map number currently on
			map.mapnum ++;
			System.out.println("MOVED forwards TO" + "x: " + map.playerX + " y: " + map.playerY);
		}

		// check if player stepped on enemy
		else if (map.getMap()[map.playerY][map.playerX] == 4) {
			// toggle on that player is now in battle, set enemy health
			isInBattle = true;
			curEnemy = 10 + 10*map.mapnum + 5*(int) (Math.random()*3 - 2) ;

			// give player options
			tA.setText(tA.getText() + "\n" + "\n\nYou approached an enemy!");
			tA.setText(tA.getText() + "\n" + "-----\nYour health: " + Integer.toString(player.health)  + "/" + Integer.toString(player.max));
			tA.setText(tA.getText() + "\n" + "Enemy health: " + Integer.toString(curEnemy));
			tA.setText(tA.getText() + "\n" + "-----\nWhat will you do? \n(type letter in field and press enter)");
			tA.setText(tA.getText() + "\n" + "\na - attack");
			tA.setText(tA.getText() + "\n" + "h - heal (" + Integer.toString(player.heals) + " remaining)");
			tA.setText(tA.getText() + "\n" + "r - run");
		}
		else if (map.getMap()[map.playerY][map.playerX] == 2) {
			int foundCoins = 50 + (int) (Math.random()*11)-5;			
			player.coins += foundCoins;
			map.getMap()[map.playerY][map.playerX] = 1;
			tA.setText(tA.getText() + "\n" + "\nFound " + foundCoins + " coins.");
			tA.setText(tA.getText() + "\n" + "You now have " + player.coins + " coins.");
		}
		else if (map.getMap()[map.playerY][map.playerX] == 9) {
			if (map.mapnum == 0) {
				tA.setText(tA.getText() + "\n" + "[June 19 2:04 AM]");
				tA.setText(tA.getText() + "\n" + "\n\"Interesting...");
				tA.setText(tA.getText() + "\n" + "The last I remember there was a");
				tA.setText(tA.getText() + "\n" + "zombie outbreak... I guess I should");
				tA.setText(tA.getText() + "\n" + "keep going... Maybe I'll find a cure!\"");
			}
			else if (map.mapnum == 1) {
				if (didOhNo == false) {
					tA.setText(tA.getText() + "\n" + "\n[June 21 4:20 PM]");
					tA.setText(tA.getText() + "\n" + "\n\"Oh no! Zombies!");
					tA.setText(tA.getText() + "\n" + "The only way to pass through is");
					tA.setText(tA.getText() + "\n" + "to battle them!\"");
					didOhNo = true;
				}
			}
			else if (map.mapnum == 3) {
				tA.setText(tA.getText() + "\n" + "\n\n[July 12 2:44 PM]");
				tA.setText(tA.getText() + "\n" + "\n\"I wonder how many other people are out");
				tA.setText(tA.getText() + "\n" + "there. Oh well... guess I'll continue.\"");
			}
			else if (map.mapnum == 5) {
				tA.setText(tA.getText() + "\n" + "\n\n[September 20 11:52 PM]");
				tA.setText(tA.getText() + "\n" + "\n\"Getting tired of travelling so much");
				tA.setText(tA.getText() + "\n" + "Hope this ends soon...\"");
			}
			else if (map.mapnum == 6) {
				tA.setText(tA.getText() + "\n" + "\n\n[October 18 5:41 AM]");
				tA.setText(tA.getText() + "\n" + "\n\"Here we go... ");
				tA.setText(tA.getText() + "\n" + "One last stand.\"");
			}
			else if (map.mapnum == 7) {
				tA.setText(tA.getText() + "\n" + "\n\n[October 27 1:53 PM]");
				tA.setText(tA.getText() + "\n" + "\n\"Hooray!!! The cure is found!");
				tA.setText(tA.getText() + "\n" + "Humanity has won! It looks like");
				tA.setText(tA.getText() + "\n" + "my journey is over.\"");
			}
		}

		else if (map.getMap()[map.playerY][map.playerX] == 11) {
			if (map.mapnum == 0) {
				if (didSquare == false) {
					tA.setText(tA.getText() + "\n" + "\n[June 19 6:45 AM]");
					tA.setText(tA.getText() + "\n" + "\n\"Aha, there! I remember!");				
					tA.setText(tA.getText() + "\n" + "The red squares will bring me to the");			
					tA.setText(tA.getText() + "\n" + "next map. I just hope there's nothing");
					tA.setText(tA.getText() + "\n" + "too dangerous out there...\"");
					didSquare = true;
				}
			}
			else if (map.mapnum == 1) {
				if (didZomb == false) {
					tA.setText(tA.getText() + "\n" + "\n\n[June 21 8:38 AM]");
					tA.setText(tA.getText() + "\n" + "\n\"Whew! Good thing that was easy!\n");	
					tA.setText(tA.getText() + "\n" + "Looks like zombies drop coins when");	
					tA.setText(tA.getText() + "\n" + "they faint, but will bring me back ");			
					tA.setText(tA.getText() + "\n" + "to the start of the map if I lose!");	

					tA.setText(tA.getText() + "\n" + "\nI can also heal or run if I need");	
					tA.setText(tA.getText() + "\n" + "to, except running may not always");	
					tA.setText(tA.getText() + "\n" + "work!");	

					tA.setText(tA.getText() + "\n" + "\nIt seems that I can go back to");	
					tA.setText(tA.getText() + "\n" + "the last location by going to the");	
					tA.setText(tA.getText() + "\n" + "blue tiles.\"");

					didZomb = true;
				}
			}
		}
		else if (map.getMap()[map.playerY][map.playerX] == 13) {
			tA.setText(tA.getText() + "\n" + "\n\n[June 28 7:52 PM]");
			tA.setText(tA.getText() + "\n" + "\n\"Visiting the shop is always a good");	
			tA.setText(tA.getText() + "\n" + "idea. There may be some items that");	
			tA.setText(tA.getText() + "\n" + "I NEED to have for later...\"");	

		}

		if (map.getMap()[map.playerY][map.playerX] == 5) {
			isInShop = true;
			printShop();
		}
		else
			isInShop = false;

		System.out.println("you are now on map " + map.mapnum);
		repaint (); // do after each action 
	}



	class DrawArea extends JPanel
	{
		public DrawArea (int width, int height)
		{
			this.setPreferredSize (new Dimension (width, height)); // size
		}
		public void paintComponent (Graphics g)
		{
			map.drawMap(g);
		}
	}

	//======================================================== method main
	public static void main (String[] args)
	{
		Map curMap = new Map (0);
		curMap.playerX = 1;
		curMap.playerY = 3;

		Player curPlayer = new Player();
		GUIComp1 window = new GUIComp1 (curMap, curPlayer);
		window.setVisible (true);
	}
}

