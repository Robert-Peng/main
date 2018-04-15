package seedu.address.logic.parser;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListAppointmentCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {
    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_NRIC = new Prefix("nr/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_SPECIES = new Prefix("s/");
    public static final Prefix PREFIX_BREED = new Prefix("b/");
    public static final Prefix PREFIX_COLOUR = new Prefix("c/");
    public static final Prefix PREFIX_BLOODTYPE = new Prefix("bt/");

    //@@author aquarinte
    /* Prefix with description */
    public static final String PREFIX_NAME_DESC = PREFIX_NAME.toString() + "\t: name";
    public static final String PREFIX_PHONE_DESC = PREFIX_PHONE.toString() + "\t: phone";
    public static final String PREFIX_EMAIL_DESC = PREFIX_EMAIL.toString() + "\t: email";
    public static final String PREFIX_ADDRESS_DESC = PREFIX_ADDRESS.toString() + "\t: address";
    public static final String PREFIX_NRIC_DESC = PREFIX_NRIC.toString() + "\t: NRIC";
    public static final String PREFIX_TAG_DESC = PREFIX_TAG.toString() + "\t: tag";
    public static final String PREFIX_REMARK_DESC = PREFIX_REMARK.toString() + "\t: remark";
    public static final String PREFIX_DATE_DESC = PREFIX_DATE.toString() + "\t: yyyy-mm-dd hh:mm";
    public static final String PREFIX_SPECIES_DESC = PREFIX_SPECIES.toString() + "\t: species";
    public static final String PREFIX_BREED_DESC = PREFIX_BREED.toString() + "\t: breed";
    public static final String PREFIX_COLOUR_DESC = PREFIX_COLOUR.toString() + "\t: colour";
    public static final String PREFIX_BLOODTYPE_DESC = PREFIX_BLOODTYPE.toString() + "\t: blood type";

    /* Option definitions */
    public static final String OPTION_OWNER = "-o";
    public static final String OPTION_PETPATIENT = "-p";
    public static final String OPTION_APPOINTMENT = "-a";
    public static final String OPTIONFORCE_OWNER = "-fo";
    public static final String OPTIONFORCE_PETPATIENT = "-fp";
    public static final String OPTION_YEAR = "-y";
    public static final String OPTION_MONTH = "-m";
    public static final String OPTION_WEEK = "-w";
    public static final String OPTION_DAY = "-d";

    /* Option with description */
    public static final String OPTION_OWNER_DESC = OPTION_OWNER + "\t: person/owner";
    public static final String OPTION_PETPATIENT_DESC = OPTION_PETPATIENT + "\t: pet patient";
    public static final String OPTION_APPOINTMENT_DESC = OPTION_APPOINTMENT + "\t: appointment";
    public static final String OPTIONFORCE_OWNER_DESC = OPTIONFORCE_OWNER + "\t: force delete person/owner";
    public static final String OPTIONFORCE_PETPATIENT_DESC = OPTIONFORCE_PETPATIENT + "\t: force delete pet patient";
    public static final String OPTION_YEAR_DESC = OPTION_YEAR + "\t: calendar year view";
    public static final String OPTION_MONTH_DESC = OPTION_MONTH + "\t: calendar month view";
    public static final String OPTION_WEEK_DESC = OPTION_WEEK + "\t: calendar week view";
    public static final String OPTION_DAY_DESC = OPTION_DAY + "\t: calendar day view";

    private static CliSyntax instance;

    private static final Set<String> prefixes = Stream.of(
            PREFIX_NAME_DESC, PREFIX_PHONE_DESC, PREFIX_EMAIL_DESC, PREFIX_ADDRESS_DESC, PREFIX_NRIC_DESC,
            PREFIX_BREED_DESC, PREFIX_SPECIES_DESC, PREFIX_COLOUR_DESC, PREFIX_BLOODTYPE_DESC, PREFIX_DATE_DESC,
            PREFIX_REMARK_DESC, PREFIX_TAG_DESC)
            .collect(Collectors.toSet());

    private static final Set<String> commandWordsWithOptionPrefix = Stream.of(
            AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            ListAppointmentCommand.COMMAND_WORD).collect(Collectors.toSet());

    private static final Set<String> commandWords = Stream.of(
            AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD, ChangeThemeCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
            HistoryCommand.COMMAND_WORD, ListAppointmentCommand.COMMAND_WORD).collect(Collectors.toSet());

    private static final Set<String> options = Stream.of(OPTION_OWNER_DESC, OPTION_PETPATIENT_DESC,
            OPTION_APPOINTMENT_DESC, OPTIONFORCE_OWNER_DESC, OPTIONFORCE_PETPATIENT_DESC,
            OPTION_YEAR_DESC, OPTION_MONTH_DESC, OPTION_WEEK_DESC, OPTION_DAY_DESC)
            .collect(Collectors.toSet());

    public static final int MAX_SYNTAX_SIZE = Math.max(commandWords.size(), Math.max(prefixes.size(), options.size()));

    public static CliSyntax getInstance() {
        if (instance == null) {
            instance = new CliSyntax();
        }
        return instance;
    }

    public Set<String> getCommandWords() {
        return CliSyntax.commandWords;
    }

    public Set<String> getCommandWordsWithOptionPrefix() {
        return CliSyntax.commandWordsWithOptionPrefix;
    }

    public Set<String> getPrefixes() {
        return CliSyntax.prefixes;
    }

    public Set<String> getOptions() {
        return CliSyntax.options;
    }

}
