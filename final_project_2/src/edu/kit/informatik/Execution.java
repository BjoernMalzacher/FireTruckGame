package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Parameter.FireTruckParameter;
import edu.kit.informatik.Parameter.NumberParameter;
import edu.kit.informatik.Parameter.ParameterBundle;
import edu.kit.informatik.Parameter.PossitionXParameter;
import edu.kit.informatik.Parameter.PossitionYParameter;
import edu.kit.informatik.command.Command;
import edu.kit.informatik.command.QuitCommand;
import edu.kit.informatik.command.RestrictedCommand;
import edu.kit.informatik.exeption.GameExeption;
import edu.kit.informatik.exeption.IllegalInputExeption;
import edu.kit.informatik.exeption.IllegalMoveExeption;
import edu.kit.informatik.model.Player;
import edu.kit.informatik.resourcess.ErrorMassanges;

/**
 * @author Bjoern Malzacher
 * @version 1.0 This Class is used to execute the game and to Parse the Input of
 *          the Users.
 *
 */
public class Execution {
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final int PARAM_POS = 1;
    private static final String SEPERATOR = ",";
    private Control control;
    private Pattern pattern;
    private Matcher match;

    /**
     * Standart Contructor
     */
    public Execution() {
        control = new Control();

    }

    /**
     * this Method is used to run the Program constantly until the quit command is
     * used. the Needed Parameter is Used to create the map of the Game.
     * 
     * @param input - String-Array representing the Map of the Game.
     * --> 5,5,A,+,L,+,D,+,A0,*,D0,+,L,*,d,*,L,+,C0,d,B0,+,C,+,L,+,B
     */
    public void start(String[] input) {
        createMatchField(input);
        while (QuitCommand.programmIsRunning) {
            run();
        }
    }

    private void createMatchField(String[] input) {
        if (input.length == 0) {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_INPUT);
        }
        ArrayList<String> inputList = new ArrayList<String>();
        for (String string : input[0].split(SEPERATOR)) {

            if (!string.equals("")) {
                inputList.add(string);
            }
        }

        String m = inputList.remove(0);
        String n = inputList.remove(0);
        if (m.matches(NUMBER_REGEX) && n.matches(NUMBER_REGEX)) {

            if (Long.parseLong(m) > Integer.MAX_VALUE && Long.parseLong(m) > Integer.MAX_VALUE) {
                throw new IllegalInputExeption("false field");
            }

            control.createMatch(Integer.parseInt(m), Integer.parseInt(n), inputList);
        } else {
            throw new IllegalInputExeption(ErrorMassanges.FALSE_INPUT);
        }

    }

    /**
     * The Main Method.
     * 
     * @param args -Input Arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Execution e = new Execution();
        String value = scanner.nextLine();
        String[] input = new String[1];
        input[0] = value;
        try {
            e.start(input);
        } catch (GameExeption exeption) {
            Terminal.printError(exeption.getMessage());

        }

    }

    // private-Methods

    private Command matchingInputtoCommand(String input) {
        if (input != null) {

            for (int i = 0; i < control.getCommandList().size(); i++) {
                pattern = Pattern.compile(control.getCommandList().get(i).getRegex());
                match = pattern.matcher(input);
                if (match.matches()) {

                    return control.getCommandList().get(i);
                }
            }
        }

        throw new IllegalInputExeption(ErrorMassanges.NO_COMMAND);
    }

    private ParameterBundle findeParamter(String input, String regex) {

        ParameterBundle bundle = new ParameterBundle();
        pattern = Pattern.compile(regex);
        match = pattern.matcher(input);
        while (match.find()) {
            if (regex.equals(FireTruckParameter.getRegex())) {
                Player p = control.getPlayerByName(match.group(PARAM_POS).charAt(0) + "");
                int vehicleNumber = Integer.parseInt(match.group(PARAM_POS).charAt(1) + "");
                FireTruckParameter paramter = new FireTruckParameter();
                paramter.setParameter(control.getFireTruck(p, vehicleNumber));
                bundle.add(paramter);
            }
            if (regex.equals(NumberParameter.getRegex())) {
                NumberParameter p = new NumberParameter();
                p.setParameter(Integer.parseInt(match.group(PARAM_POS)));
                bundle.add(p);
            }
            if (regex.equals(PossitionXParameter.getRegex())) {
                PossitionXParameter p = new PossitionXParameter();
                if (Long.parseLong(match.group(PARAM_POS)) > Integer.MAX_VALUE) {
                    throw new IllegalInputExeption(ErrorMassanges.FALSE_INPUT);
                }
                p.setParameter(Integer.parseInt(match.group(PARAM_POS)));
                bundle.add(p);
            }
            if (regex.equals(PossitionYParameter.getRegex())) {
                PossitionYParameter p = new PossitionYParameter();
                if (Long.parseLong(match.group(PARAM_POS)) > Integer.MAX_VALUE) {
                    throw new IllegalInputExeption(ErrorMassanges.WRONG_VALUES);
                }
                p.setParameter(Integer.parseInt(match.group(PARAM_POS)));
                bundle.add(p);
            }
        }
        return bundle;
    }

    private void checkrestriction(Command c, String input) {

        if (control.isNewRound()) {
            if (c instanceof RestrictedCommand) {
                throw new IllegalMoveExeption(ErrorMassanges.CAN_NOT_EXECUTE_COMMAND);
            }

        }
        c.apply(createBundle(input));
    }

    private void run() {
        try {
            String input = Terminal.readLine();
            Command c = matchingInputtoCommand(input);

            checkrestriction(c, input);
        } catch (GameExeption g) {
            Terminal.printError(g.getMessage());
        }
    }

    private ParameterBundle createBundle(String input) {
        ParameterBundle bundle = findeParamter(input, NumberParameter.getRegex());
        bundle.mergeBundles(findeParamter(input, PossitionXParameter.getRegex()));
        bundle.mergeBundles(findeParamter(input, FireTruckParameter.getRegex()));
        bundle.mergeBundles(findeParamter(input, PossitionYParameter.getRegex()));

        return bundle;
    }
}
