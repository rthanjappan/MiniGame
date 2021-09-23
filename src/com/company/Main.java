package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static ArrayList<String> roomRawData;//Stores data from Room.txt text file
    private static ArrayList<Room> rooms;//stores the room data
    private static Room currentRoom;//current room in the game loop
    private static Scanner keyboard;//reads the player input

    private static boolean endFlag=false;//indicates whether the game is ending or playing
    private static int commandType=1;//0:invalid direction,1:display current room,
    // 2:help,3:Quit,4:restart,5:main menu,Other:Invalid command
    private static int nextRoomID=1;//next room to display


    public static void main(String[] args) {
        /**
         * main game loop
         * first initialization is done.
         * then starts the game loop
         * The game loop is executed while the endFlag is true.
         * If the endFlag is false the game ends.
         * @param args
         */
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
        keyboard=new Scanner(System.in);
        roomRawData= new ArrayList<>();

        //reading the game data
        getRoomDataFromFile();
        //displays the content of Room.txt
        displayDataFile();

        //processing to create room data
        processRawRoomData();


        //displays the game logo
        displayLogo();

        //displays the main menu
        mainMenu();

        //displays the first room
        displayCurrentRoom();
    }
    /**
     * Displays the contents of the Room.txt file
     */
    private static void displayDataFile() {

        for(int i=0;i<roomRawData.size();i++){
            System.out.println(roomRawData.get(i));
        }
    }
    /**
     * Displays the game logo
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

        String command=getCommand();//gets the command from the player
        getRoomID(command);//figure out the next room to move

        switch(commandType){

            case 0://the room is not accessible
                System.out.print("You can’t go this way.\n");
                break;
            case 1://current room is displayed, a valid command
                displayCurrentRoom();
                break;
            case 2 ://help is displayed
                displayHelp();
                break;
            case 3://go through the steps to quit the game
                quitRoutine();
                break;
            case 4://go through the steps to restart the game
                restartRoutine();
                break;
            case 5://displays the main menu
                mainMenu();
                break;
            default:// the player has entered an invalid command, and it is notified to the player
                System.out.print("Invalid command.\n");
                break;
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
     * reads data from file Room.txt
     */

    private static void getRoomDataFromFile() {

        try {
            File roomFile = new File("Room.txt");
            Scanner myReader = new Scanner(roomFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                roomRawData.add(data);
           }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /**
     * Once the data is read from file,
     * the data is processed and placed to internal structure and
     * add to an Array list called Rooms.
     */
    private static void processRawRoomData() {

        rooms = new ArrayList<Room>();
        for(int i=0;i<roomRawData.size();i++){
            Room room=new Room();
            HashMap<String,Integer> navData= new HashMap<>();
            room.setRoomID(Integer.parseInt(roomRawData.get(i)));
            i++;
            room.setRoomName(roomRawData.get(i));
            i++;
            String str=roomRawData.get(i);
            ArrayList<String> descriptions = new ArrayList<>();
            while(str.compareTo("----")!=0){
                descriptions.add(str);
                i++;
                str=roomRawData.get(i);
            }
            i++;
            room.setRoomDescription(descriptions);
            StringTokenizer st = new StringTokenizer(roomRawData.get(i),",");
            while (st.hasMoreTokens()) {

                //Navigation Table
                navData = new HashMap<>();
                navData.put("NORTH",Integer.parseInt(st.nextToken()));
                navData.put("EAST",Integer.parseInt(st.nextToken()));
                navData.put("SOUTH",Integer.parseInt(st.nextToken()));
                navData.put("WEST",Integer.parseInt(st.nextToken()));
            }
            i++;
            //adding Navigation table to the room
            room.setNavTable(navData);
            //The room is not visited yet.
            room.setVisted(false);
            //adding the room to rooms arraylist
            rooms.add(room);

        }
    }

    /**
     * gets the next command from the player
     */
    private static String getCommand() {

        System.out.print("Which direction do you want to go? (N,S,E,W)\n>");
        String command=keyboard.nextLine();


        return command;
    }
    /**
     * figure out the next room to move
     */
    private static void getRoomID(String command) {
        HashMap<String, Integer> navTable = currentRoom.getNavTable();

        commandType=0;
        command=command.toUpperCase();
        if((command.compareTo("N") ==0)||
                (command.compareTo("NORTH") ==0)){

            nextRoomID= navTable.get("NORTH");
            commandType= (nextRoomID!=0)?1:0;


        }else if((command.compareTo("S") ==0)||
                (command.compareTo("SOUTH") ==0)){
            nextRoomID= navTable.get("SOUTH");
            commandType= (nextRoomID!=0)?1:0;


        }else
        if((command.compareTo("E") ==0)||
                (command.compareTo("EAST") ==0)){

            nextRoomID= navTable.get("EAST");
            commandType= (nextRoomID!=0)?1:0;

        }else
        if((command.compareTo("W") ==0)||
                (command.compareTo("WEST") ==0)){

            nextRoomID= navTable.get("WEST");
            commandType= (nextRoomID!=0)?1:0;

        }else

        if((command.compareTo("Q") ==0)||
                (command.compareTo("QUIT") ==0)){

            commandType=3;

        }else
        if((command.compareTo("H") ==0)||
                (command.compareTo("HELP") ==0)){

            commandType=2;

        }else
        if((command.compareTo("R") ==0)||
                (command.compareTo("RESTART") ==0)){

            commandType=4;

        }else
        if((command.compareTo("M") ==0)||
                (command.compareTo("MENU") ==0)){

            commandType=5;

        }else {

            commandType = -1;
        }

    }



    /**
     * Displays the current room details
     */
    private static void displayCurrentRoom() {

        currentRoom=rooms.get(nextRoomID-1);

        if(currentRoom.isVisted()){
            System.out.println("This look familiar");
        }
        else{
            currentRoom.setVisted(true);
        }
        //System.out.println("You are at Room "+currentRoom.getRoomID());
        System.out.println("You are at the "+currentRoom.getRoomName());
        ArrayList<String> descriptions = currentRoom.getRoomDescription();
        for(int i=0;i<descriptions.size();i++){
            System.out.println(descriptions.get(i));
        }


    }
    /**
     * Displays the help details
     */
    private static void displayHelp() {
        System.out.print("You are at room number "+ currentRoom.getRoomID());
        System.out.println(" The "+currentRoom.getRoomName());
        HashMap<String,Integer> navTable=currentRoom.getNavTable();
        String str="The valid directions :";
        if(navTable.get("NORTH")!=0){
            str+=" [North]";
        }
        if(navTable.get("EAST")!=0){
            str+=" [East]";
        }
        if(navTable.get("SOUTH")!=0){
            str+=" [South]";
        }
        if(navTable.get("WEST")!=0){
            str+=" [West]";
        }
        System.out.println(str);
        System.out.println("The valid commands are : [Quit] [Help] [Restart] [Menu]");
    }

    /**
     * go through the steps to restart the game
     */
    private static void restartRoutine() {
        displayLogo();
        mainMenu();
        for(int i=0;i<rooms.size();i++){
            rooms.get(i).setVisted(false);
        }
        //display the first room
        nextRoomID=1;
        displayCurrentRoom();
    }
    /**
     * Displays the main menu
     */
    private static void mainMenu() {
        System.out.println("+----------------------------------------------+");
        //System.out.println("| Welcome to Adventure Game Haunted Mansion!!! |");
        System.out.println("|           Main Menu                          |");
        System.out.println("+----------------------------------------------+");
        System.out.println("|           Restart                            |");
        System.out.println("|           Directions(North,East,South,West)  |");
        System.out.println("|           Help                               |");
        System.out.println("|           Quit                               |");
        System.out.println("+----------------------------------------------+");
    }
    /**
     * Displays the last message indicating game is ending
     */
    private static void end() {

        System.out.println("Thanks for playing....");
    }
}
