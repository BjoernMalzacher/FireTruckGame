package edu.kit.informatik.command;

import edu.kit.informatik.Control;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.Parameter.PossitionXParameter;
import edu.kit.informatik.Parameter.PossitionYParameter;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalInputExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Matchfield;
import edu.kit.informatik.model.Player;
import edu.kit.informatik.model.Field.ForestField;
import edu.kit.informatik.resourcess.ErrorMassanges;

/**
 * @author Björn Malzacher
 * @version 1.0
 * this command class is used to execute the Buy-Fire-Engine command.
 *
 */
public class BuyFireEngineCommand extends RestrictedCommand {
    private static final String BUY_ENGINE_COMMAND_REGEX = "buy-fire-engine ([0-9]+),([0-9]+)$";
    private static final int FIRE_ENGINE_COST = 5;

    /**
     * Standard Constructor
     * @param control -creates a bidirectional connection to the control class.
     */
    public BuyFireEngineCommand(Control control) {
        super(control);
        // TODO Auto-generated constructor stub
    }

    @Override public String getRegex() {
        // TODO Auto-generated method stub
        return BUY_ENGINE_COMMAND_REGEX;
    }

    @Override public void apply(ParameterBundle bundle) throws GameExeption {
        int x = -1;
        int y = -1;
        for (int i = 0; i < bundle.size(); i++) {
            if (bundle.get(i) instanceof PossitionXParameter) {
                x = (int) bundle.get(i).getParameter();
            } else if (bundle.get(i) instanceof PossitionYParameter) {
                y = (int) bundle.get(i).getParameter();
            }
        }
        Matchfield matchField = control.getMatchField();
        if (x < 0 || y < 0 || x >= matchField.sizeX() || y >= matchField.sizeY()) {
            throw new GameExeption(ErrorMassanges.FALSE_INPUT);
        }
        
        Player currentPlayer = control.getCurrentPlayer();
        if (currentPlayer == null) {
            throw new IllegalMoveExeption(ErrorMassanges.GAME_OVER);
        }
        int playerPoints = control.getCurrentPlayer().getReputationPoints();
        if (playerPoints >= FIRE_ENGINE_COST) {
            currentPlayer.reduceReputationPoints(FIRE_ENGINE_COST);
        } else {
            throw new IllegalMoveExeption(ErrorMassanges.NO_REPUTATIONPOINTS + FIRE_ENGINE_COST);
        }
        if (matchField.getField(x, y) instanceof ForestField) {
            ForestField field = (ForestField) matchField.getField(x, y);
            field.addVehicle(currentPlayer.createFireTruck());
            Terminal.printLine(currentPlayer.getReputationPoints());
        } else {
            throw new IllegalInputExeption(ErrorMassanges.NO_FOREST_FIELD);
        }

    }

}
