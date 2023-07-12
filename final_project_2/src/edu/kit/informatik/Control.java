package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Iterator;

import edu.kit.informatik.command.BuyFireEngineCommand;
import edu.kit.informatik.command.Command;
import edu.kit.informatik.command.ExtinguishCommand;
import edu.kit.informatik.command.FireToRollCommand;
import edu.kit.informatik.command.MoveCommand;
import edu.kit.informatik.command.QuitCommand;
import edu.kit.informatik.command.RefillCommand;
import edu.kit.informatik.command.ResetCommand;
import edu.kit.informatik.command.ShowBoradCommad;
import edu.kit.informatik.command.ShowFieldCommand;
import edu.kit.informatik.command.ShowPlayerCommand;
import edu.kit.informatik.command.TurnCommand;
import edu.kit.informatik.exeption.IllegalInputExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.Player;
import edu.kit.informatik.model.State;
import edu.kit.informatik.model.Field.Field;
import edu.kit.informatik.model.Field.FirePonds;
import edu.kit.informatik.model.Field.FireStation;
import edu.kit.informatik.model.Field.ForestField;
import edu.kit.informatik.resourcess.ErrorMassanges;
import edu.kit.informatik.resourcess.Massenges;
import edu.kit.informatik.vehicle.FireTruck;
import edu.kit.informatik.vehicle.Vehicle;

/**
 * @author Björn Malzacher
 * @version 1.0
 * 
 *          This class is the main Control of the Game. Control is mainly used
 *          to give overall information to the commands and creates a connection
 *          to the Execution CLass.
 *
 */
public class Control {
    private ArrayList<Player> playerList;
    private Matchfield matchField;
    private ArrayList<Command> commandList;
    private int currentPlayer;
    private int reloadN;
    private int reloadM;
    private ArrayList<String> reloadList;
    private boolean newRound;
    private boolean isGameWon;

    /**
     * this constructor is used to create a List of Commands and Player.
     */
    public Control() {

        commandList = new ArrayList<Command>();
        commandList.add(new BuyFireEngineCommand(this));
        commandList.add(new ExtinguishCommand(this));
        commandList.add(new FireToRollCommand(this));
        commandList.add(new MoveCommand(this));
        commandList.add(new QuitCommand(this));
        commandList.add(new RefillCommand(this));
        commandList.add(new RefillCommand(this));
        commandList.add(new ShowBoradCommad(this));
        commandList.add(new TurnCommand(this));
        commandList.add(new ShowFieldCommand(this));
        commandList.add(new ShowPlayerCommand(this));
        commandList.add(new ResetCommand(this));
        isGameWon = false;
        currentPlayer = 0;
        newRound = false;
    }

    /**
     * decides which player is next and makes him to the current Player
     * 
     * @return returns the current player
     */
    public Player nextPlayer() {
        if (playerList.isEmpty()) {
            throw new IllegalMoveExeption(Massenges.LOSE);
        }
        currentPlayer += 1;
        if (currentPlayer < playerList.size()) {

            return playerList.get(currentPlayer);
        } else {
            currentPlayer = 0;
            Player player = playerList.remove(0);
            playerList.add(player);
            newRound = true;
            return playerList.get(currentPlayer);

        }
    }

    /**
     * Sets a boolean which is used to check if the next round has started. this
     * Parameter is mainly used to check if some commands should be restricted.
     * 
     * @param round -true if the round has started false if not.
     */
    public void setNewRound(boolean round) {
        newRound = round;
    }

    /**
     * returns a boolean which is used to check if the next round has started.
     * 
     * @return true if a new round has started false if not.
     */
    public boolean isNewRound() {
        return newRound;
    }

    /**
     * deletes Every vehicle on a Strong burning Field.
     */
    public void clearBurnedTrucks() {
        for (Player player : playerList) {
            Iterator<Vehicle> vehicleIter = player.getVehicleList().iterator();

            while (vehicleIter.hasNext()) {
                Vehicle vehicle = vehicleIter.next();

                if (isVehicleBurned(vehicle)) {
                    vehicleIter.remove();
                }
            }
        }
    }

    /**
     * throws out every player with no vehicles.
     * 
     * @return true if a player was thrown out false if not.
     */
    public boolean throwOutPlayer() {
        Iterator<Player> playerIter = playerList.iterator();
        boolean playerdied = false;
        while (playerIter.hasNext()) {
            Player player = playerIter.next();

            if (player.getVehicleList().isEmpty()) {
                playerIter.remove();
                playerdied = true;
            }
        }
        return playerdied;
    }

    /**
     * @return returns the player who is next.
     */
    public Player getNextPlayer() {
        if (playerList.isEmpty()) {
            Terminal.printLine(Massenges.LOSE);
        } else {

            return playerList.get(0);

        }
        return null;
    }

    /**
     * Reloads the match.
     */
    public void reloadMatch() {

        createMatch(reloadM, reloadN, reloadList);
        currentPlayer = 0;
        newRound = false;
    }

    /**
     * Creates a new Match. the Map has the Size mXn and the kind of Fields is given
     * in the MapList String.
     * 
     * @param m       - Y-size of matchfield.
     * @param n       - X-size of matchfield.
     * @param mapList - List of StringValues which represent Field Values in Correct
     *                order.
     * @throws IllegalInputExeption - throw IllegalInputExeption if the Map can not
     *                              be created with this input values.
     */
    public void createMatch(int m, int n, ArrayList<String> mapList) throws IllegalInputExeption {
        reloadN = n;
        reloadM = m;
        reloadList = mapList;
        createPlayerList();
        if ((m % 2 == 0 || n % 2 == 0) || (m * n) != mapList.size()) {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_LENGHT);
        } else if (!InitializationValues.isPlayingboard(m, n, mapList)) {

            throw new IllegalInputExeption(ErrorMassanges.WRONG_VALUES);
        }

        matchField = new Matchfield(m, n, this);

        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Field f = createField(mapList.get(count + j));
                matchField.setField(j, i, f);
                // System.out.print(matchField.getField(j, i).toString());
            }
            // System.out.println();
            count += n;

        }

    }
    // Get/Set - Methods

    /**
     * @return returns a list of Commands
     */
    public ArrayList<Command> getCommandList() {
        return commandList;
    }

    /**
     * @return returns the Current Player
     */
    public Player getCurrentPlayer() {
        if (currentPlayer > playerList.size() || playerList.size() == 0) {
            return null;
        }
        return playerList.get(currentPlayer);
    }

    /**
     * gives back a Player object which represents the searched String value.
     * 
     * @param name - name of the Player.
     * @return returns the searched Player if this Object exists else returns null.
     */
    public Player getPlayerByName(String name) {
        for (Player player : playerList) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * 
     * @param pPlayer - Player which controls this Firetruck
     * @param number  - Number of the Firetruck
     * @return an Firetruck object which has the properties Player and Numer
     * @throws IllegalInputExeption - if this Firetruck does not exist an exeption
     *                              is thrown.
     */
    public FireTruck getFireTruck(Player pPlayer, int number) throws IllegalInputExeption {
        for (Player player : playerList) {
            if (player.equals(pPlayer)) {
                Vehicle vehicle = player.getVehicle(number);
                if (vehicle instanceof FireTruck) {
                    return (FireTruck) vehicle;
                }
            }
        }

        throw new IllegalInputExeption(ErrorMassanges.FIRETRUCK_NOT_EXISTING);

    }

    /**
     * 
     * @return returns a list of Players
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * 
     * @return returns true if the game is won false if not.
     */
    public boolean isGameWon() {
        return isGameWon;
    }

    /**
     * sets a boolean which checks if the game is already won
     * 
     * @param isGameWon true if the game is won false if not.
     */
    public void setGameWon(boolean isGameWon) {
        this.isGameWon = isGameWon;
    }

    /**
     * returns the current map of the Game.
     * 
     * @return returns an Matchfield Object.
     */
    public Matchfield getMatchField() {
        return matchField;
    }

    /**
     * returns the X coordinate from a Vehicle
     * 
     * @param vehicle -The Vehilce.
     * @return returns an Integer representing the X-coordinate on the map.
     */
    public int getXPossiton(Vehicle vehicle) {
        for (int i = 0; i < matchField.sizeY(); i++) {
            for (int j = 0; j < matchField.sizeX(); j++) {
                Field field = matchField.getField(j, i);
                if (field instanceof ForestField) {
                    ForestField fField = (ForestField) field;
                    if (fField.containsVehicle(vehicle)) {
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * returns the Y coordinate from a Vehicle
     * 
     * @param vehicle -The Vehilce.
     * @return returns an Integer representing the Y-coordinate on the map.
     */
    public int getYPossiton(Vehicle vehicle) {
        for (int i = 0; i < matchField.sizeY(); i++) {
            for (int j = 0; j < matchField.sizeX(); j++) {
                Field field = matchField.getField(j, i);
                if (field instanceof ForestField) {
                    ForestField fField = (ForestField) field;
                    if (fField.containsVehicle(vehicle)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    // Private Methods

    private Field createField(String m) {
        if (m.matches(FireStation.getRegex())) {
            Player p1 = new Player(m, reloadM, reloadN);
            if (playerList.contains(p1)) {
                for (Player player : playerList) {
                    if (p1.equals(player)) {
                        return new FireStation(player);
                    }
                }

            }
        } else if (m.matches(ForestField.getRegex())) {
            return new ForestField(State.getStateFromString(m));
        } else if (m.matches(FirePonds.getRegex())) {
            return new FirePonds();
        } else if (m.matches(InitializationValues.INITIALIZING_FIRETRUCKS_REGEX)) {
            ForestField f = new ForestField(State.dry);
            Player p = getPlayerByName(m.charAt(0) + "");
            Vehicle vehicle = p.createFireTruck();
            f.addVehicle(vehicle);
            return f;
        }
        throw new IllegalInputExeption(ErrorMassanges.WRONG_VALUES);
    }

    private boolean isVehicleBurned(Vehicle vehicle) {
        for (int i = 0; i < matchField.sizeY(); i++) {
            for (int j = 0; j < matchField.sizeX(); j++) {
                Field field = matchField.getField(j, i);
                if (field instanceof ForestField) {
                    ForestField fField = (ForestField) field;
                    for (Vehicle mapVehicle : fField.getVehicleList()) {
                        if (mapVehicle.equals(vehicle)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void createPlayerList() {
        playerList = new ArrayList<Player>();
        playerList.add(new Player(InitializationValues.PLAYER_A_NAME, reloadM, reloadN));
        playerList.add(new Player(InitializationValues.PLAYER_B_NAME, reloadM, reloadN));
        playerList.add(new Player(InitializationValues.PLAYER_C_NAME, reloadM, reloadN));
        playerList.add(new Player(InitializationValues.PLAYER_D_NAME, reloadM, reloadN));

    }
}
