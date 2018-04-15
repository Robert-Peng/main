package seedu.address.logic.commands;
//@@author Robert-Peng
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PET_PATIENTS;

/**
 * Lists all persons and petpatients in Medeina to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "ls";

    public static final String MESSAGE_SUCCESS = "Listed all contacts and pet patients";


    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredPetPatientList(PREDICATE_SHOW_ALL_PET_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
