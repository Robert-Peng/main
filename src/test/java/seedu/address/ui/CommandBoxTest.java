package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ClearCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Logic logic = new LogicManager(model);

        CommandBox commandBox = new CommandBox(logic);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run_withAutocomplete(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void handleKeyPress_startingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(KeyCode.DOWN, "");

        // one command
        commandBoxHandle.run_withAutocomplete(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");

        // two commands (latest command is failure)
        commandBoxHandle.run_withAutocomplete(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "help";
        commandBoxHandle.run_withAutocomplete(thirdCommand);
        assertInputHistory(KeyCode.UP, thirdCommand);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPress_startingWithDown() {
        // empty history
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // one command
        commandBoxHandle.run_withAutocomplete(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // two commands
        commandBoxHandle.run_withAutocomplete(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run_withAutocomplete(thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
        //assertInputHistory(KeyCode.UP, thirdCommand);
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run_withAutocomplete(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run_withAutocomplete(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }

    //@@author aquarinte
    @Test
    public void commandBox_autocompleteCommandWord() {
        //add command
        testAutocompleteForUserInput("    ", -1, "a", 1, "    add");
        testAutocompleteForUserInput("a", -1, "d", 1, "add");

        //clear command
        testAutocompleteForUserInput("cl", -1, "e", 1, "clear");

        //delete command
        testAutocompleteForUserInput(" ", -1, "d", 1, " delete");

        //edit command
        testAutocompleteForUserInput("  ", -1, "e", 1, "  edit");
        testAutocompleteForUserInput("e", -1, "d", 1, "edit");

        //exit command
        testAutocompleteForUserInput(" ", -1, "e", 2, " exit");
        testAutocompleteForUserInput("e", -1, "x", 1, "exit");

        //help command
        testAutocompleteForUserInput("", -1, "he", 1, "help");
        testAutocompleteForUserInput("h", -1, "e", 1, "help");
        testAutocompleteForUserInput("he", -1, "l", 1, "help");

        //history command
        testAutocompleteForUserInput(" ", -1, "h", 2, " history");
        testAutocompleteForUserInput("h", -1, "i", 1, "history");
        testAutocompleteForUserInput("hi", -1, "s", 1, "history");

        //list command
        testAutocompleteForUserInput(" ", -1, "l", 1, " list");

        //listappt command
        testAutocompleteForUserInput(" ", -1, "l", 2, " listappt");

        //theme command
        testAutocompleteForUserInput("t", -1, "h", 1, "theme");

        //undo
        testAutocompleteForUserInput("u", -1, "n", 1, "undo");

        //redo
        testAutocompleteForUserInput("r", -1, "e", 1, "redo");
    }

    @Test
    public void commandBox_commandNoAutocompleteOptionAndPrefix() {
        testAutocompleteForUserInput("clear ", -1, "n", 1, "");
        testAutocompleteForUserInput("clear ", -1, "-", 1, "");
        testAutocompleteForUserInput("list ", -1, " n", 1, "");
        testAutocompleteForUserInput("list ", -1, "-", 1, "");
        testAutocompleteForUserInput("exit ", -1, "n", 1, "");
        testAutocompleteForUserInput("exit ", -1, "-", 1, "");
        testAutocompleteForUserInput("help ", -1, "n", 1, "");
        testAutocompleteForUserInput("help ", -1, "-", 1, "");
        testAutocompleteForUserInput("history ", -1, "-", 1, "");
        testAutocompleteForUserInput("undo ", -1, "-", 1, "");
        testAutocompleteForUserInput("redo ", -1, "-", 1, "");

    }

    @Test
    public void commandBox_autocompleteOption() {
        testAutocompleteForUserInput("delete ", -1, "-", 3, "delete -fo");
        testAutocompleteForUserInput("delete ", -1, "-", 4, "delete -fp");
        testAutocompleteForUserInput("add ", -1, "-", 1, "add -a");
        testAutocompleteForUserInput("find ", -1, "-", 6, "find -o");
        testAutocompleteForUserInput("find ", -1, "-", 7, "find -p");
        testAutocompleteForUserInput("listappt ", -1, "-", 2,
                "listappt -d");
        testAutocompleteForUserInput("listappt ", -1, "-", 5,
                "listappt -m");
        testAutocompleteForUserInput("listappt ", -1, "-", 8,
                "listappt -w");
        testAutocompleteForUserInput("listappt ", -1, "-", 9,
                "listappt -y");
    }

    @Test
    public void commandBox_autocompletePrefix() {
        // prefix a/
        testAutocompleteForUserInput("add -o ", -1, "a", 1, "add -o a/");
        testAutocompleteForUserInput("add -o", -1, " ", 1, "add -o a/");

        // prefix b/
        testAutocompleteForUserInput("find -p ", -1, "b", 1,
                "find -p b/");

        // prefix bt/
        testAutocompleteForUserInput("add -p ", -1, "b", 2,
                "add -p bt/");

        // prefix c/
        testAutocompleteForUserInput("add -p ", -1, "c", 1,
                "add -p c/");

        // prefix d/
        testAutocompleteForUserInput("add -a ", -1, "d", 1,
                "add -a d/");

        // prefix n/
        testAutocompleteForUserInput("add -o ", -1, "n", 1,
                "add -o n/");

        // prefix nr/
        testAutocompleteForUserInput("add -o ", -1, "n", 2,
                "add -o nr/");
        testAutocompleteForUserInput("add -o ", -1, "nr", 1,
                "add -o nr/");

        // prefix r/
        testAutocompleteForUserInput("add -a ", -1, "r", 1,
                "add -a r/");

        // prefix s/
        testAutocompleteForUserInput("find -p ", -1, "s", 1,
                "find -p s/");

        // prefix t/
        testAutocompleteForUserInput("find -o ", -1, "t", 1,
                "find -o t/");
    }

    @Test
    public void commandBox_autocompleteNric() {
        // autocomplete suggestions for nric for add command that follows "-o nr/"
        testAutocompleteForUserInput("add -p -o ", -1, "nr/", 1,
                "add -p -o nr/F0184556R");
        testAutocompleteForUserInput("add -p -o ", -1, "nr/F018", 1,
                "add -p -o nr/F0184556R");
        testAutocompleteForUserInput("add -p -o ", -1, "nr/", 2,
                "add -p -o nr/F2345678U");
        testAutocompleteForUserInput("add -p -o ", -1, "nr/S", 1,
                "add -p -o nr/S0123456Q");

        // no nric autocomplete suggestions for nric if add command does not have "-o nr/"
        testAutocompleteForUserInput("add -p ", -1, "nr/S", 1,
                "add -p nr/S");
        testAutocompleteForUserInput("add -a ", -1, "nr/F", 1,
                "add -a nr/F");

        // autocomplete suggestions for nric for "edit -p" command
        testAutocompleteForUserInput("edit -p ", -1, "nr/", 1,
                "edit -p nr/F0184556R");
        testAutocompleteForUserInput("edit -p ", -1, "nr/F018", 1,
                "edit -p nr/F0184556R");
        testAutocompleteForUserInput("edit -p ", -1, "nr/", 2,
                "edit -p nr/F2345678U");
        testAutocompleteForUserInput("edit -p ", -1, "nr/S", 1,
                "edit -p nr/S0123456Q");

        // no nric autocomplete suggestions for nric if edit command does not start with "edit -p"
        testAutocompleteForUserInput("edit -o ", -1, "nr/", 1,
                "edit -o nr/");
        testAutocompleteForUserInput("edit -o ", -1, "nr/S", 1,
                "edit -o nr/S");

        // autocomplete suggestions for nric for "find -o" command
        testAutocompleteForUserInput("find -o ", -1, "nr/", 1,
                "find -o nr/F0184556R");
        testAutocompleteForUserInput("find -o ", -1, "nr/", 3,
                "find -o nr/G1111111B");
        testAutocompleteForUserInput("find -o ", -1, "nr/T", 3,
                "find -o nr/T0120956W");
        testAutocompleteForUserInput("find -o ", -1, "nr/T0", 2,
                "find -o nr/T0123456L");

        // no nric autocomplete suggestion if edit command does not start with "find -o"
        testAutocompleteForUserInput("find -p ", -1, "nr/T0", 2,
                "find -p nr/T0");
        testAutocompleteForUserInput("find -a ", -1, "nr/S", 2,
                "find -a nr/S");
    }

    @Test
    public void commandBox_autocompletePetPatientName() {
        testAutocompleteForUserInput("add -a -o -p", -1, " n/", 1,
                "add -a -o -p n/Jenn");
        testAutocompleteForUserInput("add -a -o -p", -1, " n/", 3,
                "add -a -o -p n/Joker");

        // autocomplete will not work
        testAutocompleteForUserInput("edit -p", -1, " n/", 1,
                "edit -p n/");
        testAutocompleteForUserInput("find -p", -1, " n/", 2,
                "find -p n/");
    }

    @Test
    public void commandBox_autocompleteSpecies() {
        testAutocompleteForUserInput("add -p ", -1, "s/C", 1,
                "add -p s/Cat");
        testAutocompleteForUserInput("add -p ", -1, "s/d", 1,
                "add -p s/Dog");
    }

    @Test
    public void commandBox_autocompleteBreed() {
        testAutocompleteForUserInput("add -p ", -1, "b/P", 1,
                "add -p b/Persian Ragdoll");
        testAutocompleteForUserInput("find -p ", -1, "b/G", 1,
                "find -p b/Golden Retriever");
    }

    @Test
    public void commandBox_autocompleteColour() {
        testAutocompleteForUserInput("find -p ", -1, "c/c", 1,
                "find -p c/calico");
        testAutocompleteForUserInput("find -p ", -1, "c/g", 1,
                "find -p c/golden");
    }

    @Test
    public void commandBox_autocompleteBloodType() {
        testAutocompleteForUserInput("find -p ", -1, "bt/", 1,
                "find -p bt/A");
        testAutocompleteForUserInput("find -p ", -1, "bt/D", 1,
                "find -p bt/DEA 4+");
    }

    @Test
    public void commandBox_autocompleteTag() {
        // person tags
        testAutocompleteForUserInput("add -o ", -1, "t/", 2,
                "add -o t/owesMoney");
        testAutocompleteForUserInput("add -o ", -1, "t/F", 1,
                "add -o t/friends");

        // pet patient tags
        testAutocompleteForUserInput("add -p ", -1, "t/d", 1,
                "add -p t/depression");
        testAutocompleteForUserInput("add -p ", -1, "t/", 1,
                "add -p t/3legged");

        // appointment tags
        testAutocompleteForUserInput("add -a ", -1, "t/", 1,
                "add -a t/checkup");
        testAutocompleteForUserInput("add -a ", -1, "t/", 2,
                "add -a t/vaccination");

        // no option: all tags
        testAutocompleteForUserInput("add ", -1, "t/", 2,
                "add t/checkup");
        testAutocompleteForUserInput("add ", -1, "t/", 3,
                "add t/depression");
        testAutocompleteForUserInput("add ", -1, "t/", 5,
                "add t/owesMoney");
    }

    @Test
    public void commandBox_autocompleteMiddleOfText() {
        testAutocompleteForUserInput("add -p -o t/", 9, " nr/G1", 1,
                "add -p -o nr/G1111111B t/");
        testAutocompleteForUserInput("add -a -o -p t/", 12, " n/Jo", 1,
                "add -a -o -p n/Joker t/");
        testAutocompleteForUserInput("add -p n/joker s/cat b/persian c/brown bt/AB -o nr/S9600666G",
                38, " t/D", 1,
                "add -p n/joker s/cat b/persian c/brown t/depression bt/AB -o nr/S9600666G");
    }

    /**
     * Sets commandbox text to {@code userInput}.
     * Sets caret position to {@code index} if {@code index} > 0.
     * Inserts {@code userInput2} to commandbox.
     *
     * Checks that {@code userInput1} + {@code userInput2} with the {@code numOfTabs} to select an option
     * on autocomplete's context menu will result in {@code actualCommand}.
     */
    private void testAutocompleteForUserInput(String userInput1, int index, String userInput2, int numOfTabs,
                                              String actualCommand) {
        commandBoxHandle.setText(userInput1);

        if (index > 0) {
            commandBoxHandle.setCaretPosition(index);
        }

        for (int i = 0; i < userInput2.length(); i++) {
            char c = userInput2.charAt(i);
            commandBoxHandle.insertText(Character.toString(c));
        }

        while (numOfTabs > 0) {
            guiRobot.push(KeyCode.TAB);
            numOfTabs--;
        }

        guiRobot.push(KeyCode.ENTER);
        assertEquals(actualCommand, commandBoxHandle.getInput());
    }
}
