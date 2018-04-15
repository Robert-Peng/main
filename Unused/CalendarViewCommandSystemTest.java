package systemtests;

import static org.junit.Assert.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.CalendarViewCommand;

//@@author Robert-Peng-unused

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
