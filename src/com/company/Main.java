package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static Input input;
    //private static HashMap<Integer,Room> rooms;//stores the room data
    private static Room currentRoom;//current room in the game loop
    private static Scanner keyboard;//reads the player input

    private static boolean endFlag=false;//indicates whether the game is ending or playing
    private static int commandType=1;//-2:no further action,-1:invalid command,1:valid command,
    // 2:help,3:Quit,4:restart,5:Menu,6:pickup Item,7:inspect item, 8 drop item,
    // 9: display inventory,10:explore current room
    private static int nextRoomID=1;//next room to display
    private static ArrayList<Item> items;

    private static Item currentItem;

    private static Player player;
    private static Puzzle currentPuzzle;
    private static String currentCommand="";

    /**
     * main game loop
     * first initialization is done.
     * then starts the game loop
     * The game loop is executed while the endFlag is true.
     * If the endFlag is false the game ends.
     * @param args
     */
    public static void main(String[] args) {

        //initializing the game
        initialization();

        //main game loop
        do{
            process();
        }while(!endFlag);

        //Game is ending
        end();

        System.exit(0);
    }

    /**
     * initialization of the game
     * Data is read from the file and data is processed into an internal data structure.
     * THe starting room is displayed.
     */

    public static void initialization(){

        input=new Input();//reads and creates the game data from file

        keyboard=new Scanner(System.in);

        player= new Player();
        player.map.getRoom(1);

        System.out.println(player.map);

        displayLogo();

        mainMenu();

        //display the first room
        displayCurrentRoom();
        processPuzzle();

    }

    /**
     * Displays welcome screen
     */

    private static void displayLogo() {
        System.out.println("+----------------------------------------------+");
        System.out.println("| Welcome to Adventure Game Haunted Mansion!!! |");
        System.out.println("+----------------------------------------------+");
    }

    /**
     * Main program execution.
     * The command type is validated and according to the command type
     * each process is executed.
     * If the command entered is invalid , it is notified to the player.
     */
    private static void process() {

        String command=getCommand();//reads the command from user
        getRoomID(command);//figure outs the next room to move and processes other commands

        switch(commandType){
            case -2://No further processing

                break;
            case -1:
                System.out.print("Invalid command.\n");
                break;
            case 1:
                displayCurrentRoom();//displays current room
                processPuzzle();//puzzle logic

                break;
            case 2 :
                displayHelp();//displays help
                break;
            case 3:
                quitRoutine();//quit command logic
                break;
            case 4:
                restartRoutine();//restart command logic
                break;
            case 5:
                mainMenu();//displays main menu
                break;
            case 6:
                pickupItem();//lets the player pick up the item specified
                break;
            case 7:
                inspectItem();//displays the description of the item
                break;
            case 8:
                dropItem();//removes the item from player inventory and places the item in the current room
                break;
            case 9:
                displayInventory();//displays the items in the inventory
                break;
            case 10:
                exploreCurrentRoom();//displays the description, items, puzzles and monsters in the room
                break;
            default:
                //System.out.print("Invalid command.\n");
                break;
        }


    }

    /**
     * displays the description, items, puzzles and monsters in the room
     */
    private static void exploreCurrentRoom() {
        System.out.println("You are at the "+currentRoom.getRoomName());
        ArrayList<String> descriptions = currentRoom.getRoomDescription();
        for(String s : descriptions){
            System.out.println(s);
        }
        System.out.println();
        ArrayList<Item> inventory = currentRoom.getRoomInventory();

        if(inventory.isEmpty()){
            if(currentCommand.compareTo("explore")==0){
                System.out.println("There is no items available in this room.");
            }


        }else{
            System.out.println("Items available in this room are : ");
            for(Item item : inventory){

                System.out.println(" - "+item.getItemName() + ": "+
                        item.getItemDescription());
            }
        }
        if(!currentRoom.getPuzzle().isSolved()) {
            currentPuzzle = currentRoom.getPuzzle();

            System.out.println("\nYou have a puzzle to solve : "+currentPuzzle.getPuzzleName());
        }
        currentCommand="";
    }
    /**
     * lets the player to pickup the item specified
     * The item is removed from room inventory and
     * added to Player Inventory
     */
    private static void pickupItem() {
        player.getPlayerInventory().add(currentItem);
        currentRoom.getRoomInventory().remove(currentItem);
        System.out.println(currentItem.getItemName()+" has been picked up from the "+currentRoom.getRoomName()+
                        " and successfully added to the player inventory.");
    }
    /**
     * Displays the description of the item
     */
    private static void inspectItem() {

        System.out.println(currentItem.getItemDescription());
    }

    /**
     * removes the item from player inventory and places the item in the current room
     */
    private static void dropItem() {
        currentRoom.getRoomInventory().add(currentItem);
        player.getPlayerInventory().remove(currentItem);
        System.out.println( currentItem.getItemName()+ " has been dropped successfully from " +
                "the player inventory and placed in the " + currentRoom.getRoomName());
    }

    /**
     * displays the items in the inventory
     */
    private static void displayInventory() {
        System.out.println("Your inventory has the following items: ");
        ArrayList<Item> playerInventory= player.getPlayerInventory();
        for(int i=0;i<playerInventory.size();i++){

            System.out.println(" - "+playerInventory.get(i).getItemName());


        }
    }

    /**
     * At any point The player can quit the game.
     * This quitRoutine make sure the player wants to quit and
     * indicates the game is ending with an endFlag.
     */
    private static void quitRoutine() {

        System.out.print("Are you sure you want to Quit? (Y/N) : ");
        String choice = keyboard.nextLine();
        choice = choice.toUpperCase();
        if(choice.compareTo("Y")==0 || choice.compareTo("YES")==0){

            endFlag=true;

        }
    }

    /**
     * reads the command from the user
     * @return the commandString
     */

    private static String getCommand() {

        System.out.print("Which direction do you want to go? (N,S,E,W)\n>");
        String command=keyboard.nextLine();


        return command;
    }

    /**
     * figure outs the next room to move and processes other commands
     * @param command
     */
    private static void getRoomID(String command) {
        HashMap<String, Integer> navTable = currentRoom.getNavTable();

        commandType = 0;
        command = command.toLowerCase();
        StringTokenizer st = new StringTokenizer(command, " ");
        //System.out.println(st.countTokens());

        if (st.countTokens() == 1) {
            if ((command.compareTo("n") == 0) ||
                    (command.compareTo("north") == 0)) {

                nextRoomID = navTable.get("north");
                player.map.getRoom(nextRoomID);
                commandType = (nextRoomID != 0) ? 1 : 0;


            } else if ((command.compareTo("s") == 0) ||
                    (command.compareTo("south") == 0)) {
                nextRoomID = navTable.get("south");
                player.map.getRoom(nextRoomID);
                commandType = (nextRoomID != 0) ? 1 : 0;


            } else if ((command.compareTo("e") == 0) ||
                    (command.compareTo("east") == 0)) {

                nextRoomID = navTable.get("east");
                player.map.getRoom(nextRoomID);
                commandType = (nextRoomID != 0) ? 1 : 0;

            } else if ((command.compareTo("w") == 0) ||
                    (command.compareTo("west") == 0)) {

                nextRoomID = navTable.get("west");
                player.map.getRoom(nextRoomID);
                commandType = (nextRoomID != 0) ? 1 : 0;

            } else if ((command.compareTo("q") == 0) ||
                    (command.compareTo("quit") == 0)) {

                commandType = 3;

            } else if ((command.compareTo("h") == 0) ||
                    (command.compareTo("help") == 0)) {

                commandType = 2;

            } else if ((command.compareTo("r") == 0) ||
                    (command.compareTo("restart") == 0)) {

                commandType = 4;

            } else if ((command.compareTo("m") == 0) ||
                    (command.compareTo("menu") == 0)) {

                commandType = 5;

            } else if ((command.compareTo("in") == 0) ||
                    (command.compareTo("inventory") == 0)) {
                ArrayList<Item> playerItems = player.getPlayerInventory();
                if(playerItems.size() ==0 ){
                    System.out.println("You didn’t pickup any items yet.");
                    commandType=-2;
                    return;
                }else {
                    commandType=9;

                }

            }else if ((command.compareTo("ex") == 0) ||
                    (command.compareTo("explore") == 0)) {
                currentCommand="explore";
                commandType=10;
                return;
            }
            else {

                commandType = -1;
            }

        } else if (st.countTokens() >1) {
            ArrayList<Item> roomItems=currentRoom.getRoomInventory();
            ArrayList<Item> playerItems = player.getPlayerInventory();
            commandType = 0;

            command=command.toLowerCase();
            //Scanner sc=new Scanner(command);
            command = st.nextToken();
            String itemName=st.nextToken();

            while(st.hasMoreTokens()){
                itemName+=" "+st.nextToken();
            }
            String puzzleName=itemName;
            itemName=itemName.toUpperCase();
            //System.out.println("The item :"+ itemName );
            //System.out.println("command : "+command + " itemName : "+ itemName);

            if ((command.compareTo("p") == 0) ||
                    (command.compareTo("pickup") == 0)) {


                for(int i=0;i<roomItems.size();i++){
                    if(roomItems.get(i).getItemName().compareTo(itemName)==0){
                        currentItem=roomItems.get(i);
                        commandType = 6;
                        return;
                    }
                }
                System.out.println("The item "+ itemName +" does not exist in this room.");
                commandType=-2;

            } else if ((command.compareTo("i") == 0) ||
                    (command.compareTo("inspect") == 0)) {

                for(int i=0;i<playerItems.size();i++){
                    if(playerItems.get(i).getItemName().compareTo(itemName)==0){
                        currentItem=playerItems.get(i);
                        commandType = 7;
                        return;
                    }
                }
                System.out.println("The "+ itemName +" is/are not picked up yet.");
                commandType=-2;


            } else if ((command.compareTo("d") == 0) ||
                    (command.compareTo("drop") == 0)) {
                for(int i=0;i<playerItems.size();i++){
                    if(playerItems.get(i).getItemName().compareTo(itemName)==0){
                        currentItem=playerItems.get(i);
                        commandType = 8;
                        return;
                    }
                }
                System.out.println("The "+ itemName +" is/are not picked up yet.");
                commandType=-2;


            } else if ((command.compareTo("solve") == 0) ||
                    (command.compareTo("so") == 0)) {
                if(puzzleName.compareTo("puzzle") == 0){
                    if(currentRoom.getPuzzle().isSolved()){
                        System.out.println("There are no puzzles to solve in this room.");
                    }else{
                        processPuzzle();
                    }

                }

                commandType=-2;
            }
            else {

                commandType = -1;
            }

        }

    }

    /**
     * displays the name of the current room
     */
    private static void displayCurrentRoom() {

        currentRoom=player.map.getRooms().get(player.getPlayerLocation());

        System.out.println("You are at the "+currentRoom.getRoomName());



    }

    /**
     * the puzzle logic
     */
    private static void processPuzzle() {

        if(!currentRoom.getPuzzle().isSolved()) {
            currentPuzzle = currentRoom.getPuzzle();

            int availableAttempts = currentPuzzle.getNoOfAttempts();
            System.out.println("\nYou have a puzzle to solve : (number of attempts available are " +
                    availableAttempts + ".)");
            System.out.println(currentPuzzle.getPuzzleDescription());
            do {
                System.out.print(">");
                String ans = keyboard.nextLine();
                //Possible commands :  Examine,solve,ignore and the answer
                int status=processPuzzleCommand(ans);
                if(status==-1){
                    return;
                }else if(status==0){
                    continue;
                }else {
                    ans.toLowerCase();

                    if (ans.compareTo(currentPuzzle.getAnswer()) == 0) {
                        System.out.println("you solved the puzzle correctly!");

                        currentPuzzle.setSolved(true);
                        return;
                    } else {
                        if (availableAttempts == 1) {
                            break;
                        }
                        System.out.println("Incorrect ." +
                                " You still have " + --availableAttempts + " attempt(s) left." +
                                " Try one more time.");
                    }
                }

            } while (availableAttempts > 0);

            System.out.println("You failed to solve the puzzle. You have no more attempts left.");
        }
    }

    /**
     * processes the commands entered by the user during the puzzle processing
     * @param ans
     * @return status
     */
    private static int processPuzzleCommand(String ans) {
            ans=ans.toLowerCase();
            StringTokenizer st = new StringTokenizer(ans," ");
            if(st.countTokens()>1){
                String command = st.nextToken();
                if(command.compareTo("examine")==0 ||command.compareTo("x")==0){
                    if(ans.compareTo("examine puzzle")==0|| ans.compareTo("x puzzle")==0){
                        System.out.println(currentPuzzle.getPuzzleDescription());
                        return 0;
                    }

                }

                else if(command.compareTo("ignore")==0 ||command.compareTo("ig")==0) {
                    if (ans.compareTo("ignore puzzle") == 0 || ans.compareTo("ig puzzle") == 0) {
                        System.out.println("You are leaving the " + currentPuzzle.getPuzzleName() + ".");
                        return -1;
                    }
                }
            }
            return 1;//this is an answer

    }

    /**
     * displays the help
     */
    private static void displayHelp() {
        System.out.println("You are at the "+currentRoom.getRoomName());
        System.out.println("+------------------------------------------------------------------------+");
        System.out.println("| 			                Help   			                             |");
        System.out.println("+------------------------------------------------------------------------+");
        System.out.println("| Player directions are North,South,East, and West	                     |");
        System.out.println("| You may pick up items by entering \"pickup <item name>\"	             |");
        System.out.println("| You may drop items by entering \"drop <item name>\"	                     |");
        System.out.println("| You may get description of the item by entering \"inspect <item name>\"  |");
        System.out.println("| You may check your inventory by entering \"inventory\"	                 |");
        System.out.println("| You may get description of the puzzle by entering \"examine <puzzle>\"   |");
        System.out.println("| You may leave the puzzle by entering \"ignore <puzzle name>\"            |");
        System.out.println("| Other valid commands are [Quit],[Restart],[Help],[Menu]                |");
        System.out.println("+------------------------------------------------------------------------+");

        HashMap<String,Integer> navTable=currentRoom.getNavTable();
        String str="The valid directions from this room are :";
        if(navTable.get("north")!=0){
            str+=" [North]";
        }
        if(navTable.get("east")!=0){
            str+=" [East]";
        }
        if(navTable.get("south")!=0){
            str+=" [South]";
        }
        if(navTable.get("west")!=0){
            str+=" [West]";
        }
        System.out.println(str);
    }

    /**
     * restarts the game resetting the rooms, items, puzzles and monsters
     */
    private static void restartRoutine() {
        displayLogo();
        mainMenu();
        player.getPlayerInventory().clear();

        player.map.getRooms().clear();

        input.processRawItemData();
        input.processRawPuzzleData();
        input.processRawRoomData();
        //input.processRawMonsterData();

        //display the first room
        player= new Player();
        player.map.getRoom(1);//setPlayerLocation(1);
        nextRoomID=1;
        displayCurrentRoom();
        processPuzzle();
    }

    /**
     * displays the main menu
     */
    private static void mainMenu() {
        System.out.println("+----------------------------------------------+");
        System.out.println("|           Main Menu                          |");
        System.out.println("+----------------------------------------------+");
        System.out.println("|           Restart                            |");
        System.out.println("|           Directions(North,East,South,West)  |");
        System.out.println("|           Help                               |");
        System.out.println("|           Quit                               |");
        System.out.println("+----------------------------------------------+");
    }

    /**
     * displays the ending message
     */
    private static void end() {

        System.out.println("Thanks for playing....");
    }
}
