package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarViewEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author Robert-Peng-unused
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
