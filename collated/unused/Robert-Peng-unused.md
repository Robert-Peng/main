# Robert-Peng-unused
###### \CalendarViewCommand.java
``` java
/**code unused as the function is integrated into listappt command
 + * Command to switch between calendar views such as day, week, month and year
 + */
public class CalendarViewCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": To change the calendar view between Day, "
        + "Week, Month, and Year \n"
        + COMMAND_ALIAS + ": Short for calendar. \n"
        + "Parameter: \n"
        + "Day view: d\n"
        + "Week view: w\n"
        + "Month view: m\n"
        + "Year view: y\n";

    public static final String MESSAGE_SUCCESS = "View changed.";

    private Character arg;

    public CalendarViewCommand(Character c) {
        this.arg = c;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeCalendarViewEvent(arg));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \CalendarViewCommandParser.java
``` java
/**code unused as the function is integrated into listappt command
 + * Parser for CalendarViewCommand
 + */
public class CalendarViewCommandParser implements Parser {


    @Override
    public Command parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        if (userInput.length() != 1 || !isValidCommand(userInput)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarViewCommand.MESSAGE_USAGE));
        }
        return new CalendarViewCommand(userInput.charAt(0));
    }

    /**
     * Check if the parameters is either w,d,y or m.
     */
    private boolean isValidCommand(String str) {

        assert(str.length() == 1);
        switch (str.charAt(0)) {
            case('w'):
            case('d'):
            case('y'):
            case('m'):
                return true;
            default:
                return false;
        }
    }
}

```
###### \CalendarViewCommandSystemTest.java
``` java

/**
 * code unused as the function is integrated into listappt command
 */
public class CalendarViewCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void changeCalendarView() {
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " d", CalendarViewCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " w", CalendarViewCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " m", CalendarViewCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " y", CalendarViewCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " q",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarViewCommand.MESSAGE_USAGE));
            }

    /**
     * Performs verification for command to calendarView
     */
    private void assertCommandSuccess(String command, String message) {
        executeCommand(command);
        assertEquals(getResultDisplay().getText() , message);
    }
}
```
###### \ChangeCalendarViewEvent.java
``` java
/** code unused as the function is integrated into listappt command
 + * Indicates a request to change calendar view
 + */
public class ChangeCalendarViewEvent extends BaseEvent {

    public final Character character;

    public ChangeCalendarViewEvent(Character character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
