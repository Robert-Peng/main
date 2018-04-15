package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.Type.EDIT_APPOINTMENT;
import static seedu.address.logic.commands.EditCommand.Type.EDIT_PERSON;
import static seedu.address.logic.commands.EditCommand.Type.EDIT_PET_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PET_PATIENTS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.descriptors.EditAppointmentDescriptor;
import seedu.address.logic.descriptors.EditPersonDescriptor;
import seedu.address.logic.descriptors.EditPetPatientDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.ConcurrentAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.appointment.exceptions.PastAppointmentException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.petpatient.BloodType;
import seedu.address.model.petpatient.Breed;
import seedu.address.model.petpatient.Colour;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.Species;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;
import seedu.address.model.petpatient.exceptions.PetPatientNotFoundException;
import seedu.address.model.tag.Tag;

//@@author chialejing
/**
 * Edits the details of an existing person, pet patient or appointment in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "ed";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the contact / owner / appointment identified by their respective index numbers. "
            + "Existing values will be overwritten by the input values. "
            + "Note that INDEX must be a positive integer.\n"
            + "To edit the details of an existing contact: "
            + COMMAND_WORD + " -o " + "INDEX "
            + "[" + PREFIX_NAME + "CONTACT_NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "To edit the details of an existing pet patient: "
            + COMMAND_WORD + " -p " + "INDEX "
            + "[" + PREFIX_NAME + "PET_PATIENT_NAME] "
            + "[" + PREFIX_SPECIES + "SPECIES] "
            + "[" + PREFIX_BREED + "BREED] "
            + "[" + PREFIX_COLOUR + "COLOUR] "
            + "[" + PREFIX_BLOODTYPE + "BLOOD_TYPE] "
            + "[" + PREFIX_NRIC + "OWNER_NRIC] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "To edit the details of an existing appointment: "
            + COMMAND_WORD + " -a " + "INDEX "
            + "[" + PREFIX_DATE + "YYYY-MM-DD HH:MM] "
            + "[" + PREFIX_REMARK + "REMARK] "
            // + "[" + PREFIX_NRIC + "OWNER_NRIC] "
            // + "[" + PREFIX_NAME + "PET_PATIENT_NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "\n";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_EDIT_PET_PATIENT_SUCCESS = "Edited Pet Patient: %1$s";
    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This contact already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_PET_PATIENT = "This pet patient already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book.";
    public static final String MESSAGE_MISSING_PERSON = "The target contact cannot be missing.";
    public static final String MESSAGE_MISSING_PET_PATIENT = "The target pet patient cannot be missing";
    public static final String MESSAGE_MISSING_APPOINTMENT = "The target appointment cannot be missing.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT_TIMING = "Duplicate in appointment timing.";
    public static final String MESSAGE_PAST_APPOINTMENT = "Appointment cannot be in the past.";
    public static final String MESSAGE_CONCURRENT_APPOINTMENT = "Appointment cannot be concurrent "
            + "with other appointments.";
    /**
     * Enum to support the type of edit command that the user wishes to execute.
     */
    public enum Type { EDIT_PERSON, EDIT_PET_PATIENT, EDIT_APPOINTMENT };

    private Index index;
    private EditPersonDescriptor editPersonDescriptor;
    private EditPetPatientDescriptor editPetPatientDescriptor;
    private EditAppointmentDescriptor editAppointmentDescriptor;
    private Type type;

    private Person personToEdit; // original
    private Person editedPerson; // edited
    private PetPatient petPatientToEdit;
    private PetPatient editedPetPatient;
    private Appointment appointmentToEdit;
    private Appointment editedAppointment;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.type = EDIT_PERSON;
    }

    /**
     * @param index of the pet patient in the filtered pet patient list to edit
     * @param editPetPatientDescriptor details to edit the pet patient with
     */
    public EditCommand(Index index, EditPetPatientDescriptor editPetPatientDescriptor) {
        requireNonNull(index);
        requireNonNull(editPetPatientDescriptor);

        this.index = index;
        this.editPetPatientDescriptor = new EditPetPatientDescriptor(editPetPatientDescriptor);
        this.type = EDIT_PET_PATIENT;
    }

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
        this.type = EDIT_APPOINTMENT;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            switch (type) {
            case EDIT_PERSON:
                resolvePersonDependencies();
                model.updatePerson(personToEdit, editedPerson);
                break;
            case EDIT_PET_PATIENT:
                resolvePetPatientDependencies();
                model.updatePetPatient(petPatientToEdit, editedPetPatient);
                break;
            case EDIT_APPOINTMENT:
                checkForClashes();
                // checkForSameAppointmentTiming();
                checkForConcurrentAppointments();
                checkForPastAppointment();
                model.updateAppointment(appointmentToEdit, editedAppointment);
                break;
            default:
                break;
            }
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_MISSING_PERSON);
        } catch (DuplicatePetPatientException dppe) {
            throw new CommandException(MESSAGE_DUPLICATE_PET_PATIENT);
        } catch (PetPatientNotFoundException ppnfe) {
            throw new CommandException(MESSAGE_MISSING_PET_PATIENT);
        } catch (DuplicateDateTimeException ddte) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT_TIMING);
        } catch (DuplicateAppointmentException dae) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (AppointmentNotFoundException anfe) {
            throw new CommandException(MESSAGE_MISSING_APPOINTMENT);
        } catch (PastAppointmentException pae) {
            throw new CommandException(MESSAGE_PAST_APPOINTMENT);
        } catch (ConcurrentAppointmentException cae) {
            throw new CommandException(MESSAGE_CONCURRENT_APPOINTMENT);
        }
        switch (type) {
        case EDIT_PERSON:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
        case EDIT_PET_PATIENT:
            model.updateFilteredPetPatientList(PREDICATE_SHOW_ALL_PET_PATIENTS);
            return new CommandResult(String.format(MESSAGE_EDIT_PET_PATIENT_SUCCESS, editedPetPatient));
        case EDIT_APPOINTMENT:
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
            return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment));
        default:
            return null;
        }
    }

    /**
     * Checks whether person's NRIC has been modified
     * If yes, update all other relevant pet patients and appointments under the same person
     * If no, do nothing
     */
    private void resolvePersonDependencies() throws DuplicatePetPatientException, PetPatientNotFoundException,
            DuplicateAppointmentException, AppointmentNotFoundException {

        Nric oldNric = personToEdit.getNric();
        Nric newNric = editedPerson.getNric();

        if (!oldNric.equals(newNric)) {
            updatePetPatientsByOwnerNric(oldNric, newNric);
            updateAppointmentByOwnerNric(oldNric, newNric);
        }
    }

    /**
     * Checks whether pet patient's name or owner NRIC has been modified
     * If yes, update all other relevant appointments and also the update the new owner for the pet
     */
    private void resolvePetPatientDependencies() throws CommandException,
            AppointmentNotFoundException, DuplicateAppointmentException {

        Nric oldNric = petPatientToEdit.getOwner();
        Nric newNric = editedPetPatient.getOwner();
        PetPatientName oldPetName = petPatientToEdit.getName();
        PetPatientName newPetName = editedPetPatient.getName();

        if (!oldNric.equals(newNric)) { // nric edited, I want to change owner
            Person newOwner = model.getPersonWithNric(newNric); // new owner must exist
            if (newOwner == null) {
                throw new CommandException("New owner must exist first before updating pet patient's owner NRIC!");
            }
            // we only update nric for appointments for that specific pet patient!
            // this is because it might be an owner transfer. If there are some other pets under the previous owner,
            // he/she may still be holding on to these pets.
            updateAppointmentByOwnerNricForSpecificPetName(oldNric, newNric, oldPetName);
        }
        if (!oldPetName.equals(newPetName)) { // name edited
            updateAppointmentByPetPatientName(newNric, oldPetName, newPetName);
        }
    }

    /**
     * Checks whether there are clashes in appointment date and time (i.e. same timing with another appointment)
     */
    private void checkForClashes() throws DuplicateDateTimeException {
        LocalDateTime oldDateTime = appointmentToEdit.getDateTime();
        LocalDateTime newDateTime = editedAppointment.getDateTime();

        if (!oldDateTime.equals(newDateTime)) {
            Appointment appointmentWithClash = model.getClashingAppointment(newDateTime);
            if (appointmentWithClash != null) {
                throw new DuplicateDateTimeException();
            }
        }
    }

    /**
     * Checks whether there are clashes in appointment date and time (concurrent appointments)
     */
    private void checkForConcurrentAppointments() throws ConcurrentAppointmentException {
        LocalDateTime oldDateTime = appointmentToEdit.getDateTime();
        LocalDateTime newDateTime = editedAppointment.getDateTime();

        if (model.hasConcurrentAppointment(oldDateTime, newDateTime)) {
            throw new ConcurrentAppointmentException();
        }
    }

    /**
     * Checks whether appointment datetime given is in the past
     */
    private void checkForPastAppointment() throws PastAppointmentException {
        LocalDateTime newDateTime = editedAppointment.getDateTime();

        if (newDateTime.isBefore(LocalDateTime.now())) {
            throw new PastAppointmentException();
        }
    }

    /**
     * Checks whether the new timing for the appointment is equivalent to the old one
     */
    private void checkForSameAppointmentTiming() throws CommandException {
        LocalDateTime oldDateTime = appointmentToEdit.getDateTime();
        LocalDateTime newDateTime = editedAppointment.getDateTime();

        if (newDateTime.equals(oldDateTime)) {
            throw new CommandException("Appointment timing has not changed.");
        }
    }

    /**
     * Helper function to update pet patient's owner from an old nric to new nric
     */
    private void updatePetPatientsByOwnerNric(Nric oldNric, Nric newNric) throws
            PetPatientNotFoundException, DuplicatePetPatientException {

        ArrayList<PetPatient> petPatientArrayList = model.getPetPatientsWithNric(oldNric);
        EditPetPatientDescriptor eppd = new EditPetPatientDescriptor();
        eppd.setOwnerNric(newNric);

        for (PetPatient currPetPatient : petPatientArrayList) {
            PetPatient modifiedPetPatient = createEditedPetPatient(currPetPatient, eppd);
            model.updatePetPatient(currPetPatient, modifiedPetPatient);
            model.updateFilteredPetPatientList(PREDICATE_SHOW_ALL_PET_PATIENTS);
        }
    }

    /**
     * Helper function to update appointment's owner from an old nric to new nric
     */
    private void updateAppointmentByOwnerNric(Nric oldNric, Nric newNric) throws
            AppointmentNotFoundException, DuplicateAppointmentException {

        ArrayList<Appointment> appointmentArrayList = model.getAppointmentsWithNric(oldNric);
        EditAppointmentDescriptor ead = new EditAppointmentDescriptor();
        ead.setOwnerNric(newNric);

        for (Appointment currAppointment : appointmentArrayList) {
            Appointment modifiedAppointment = createEditedAppointment(currAppointment, ead);
            model.updateAppointment(currAppointment, modifiedAppointment);
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        }
    }

    /**
     * Helper function to update pet patient name in appointment
     */
    private void updateAppointmentByPetPatientName(Nric ownerNric, PetPatientName oldPetName,
                                                   PetPatientName newPetName) throws
            DuplicateAppointmentException, AppointmentNotFoundException {

        ArrayList<Appointment> appointmentArrayList =
                model.getAppointmentsWithNricAndPetName(ownerNric, oldPetName);
        EditAppointmentDescriptor ead = new EditAppointmentDescriptor();
        ead.setPetPatientName(newPetName);

        for (Appointment currAppointment : appointmentArrayList) {
            Appointment modifiedAppointment = createEditedAppointment(currAppointment, ead);
            model.updateAppointment(currAppointment, modifiedAppointment);
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        }
    }

    /**
     * Helper function to update the pet patient owner's NRIC for all its appointment
     */
    private void updateAppointmentByOwnerNricForSpecificPetName(Nric oldNric, Nric newNric, PetPatientName oldPetName)
            throws DuplicateAppointmentException, AppointmentNotFoundException {

        ArrayList<Appointment> appointmentArrayList =
                model.getAppointmentsWithNricAndPetName(oldNric, oldPetName);
        EditAppointmentDescriptor ead = new EditAppointmentDescriptor();
        ead.setOwnerNric(newNric);

        for (Appointment currAppointment : appointmentArrayList) {
            Appointment modifiedAppointment = createEditedAppointment(currAppointment, ead);
            model.updateAppointment(currAppointment, modifiedAppointment);
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        switch (type) {
        case EDIT_PERSON:
            preprocessUndoableCommandForPerson();
            break;
        case EDIT_PET_PATIENT:
            preprocessUndoableCommandForPetPatient();
            break;
        case EDIT_APPOINTMENT:
            preprocessUndoableCommandForAppointment();
            break;
        default:
            break;
        }
    }
    /**
     * Obtains the last shown person list.
     */
    protected void preprocessUndoableCommandForPerson() throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        }

        personToEdit = lastShownPersonList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Obtains the last shown pet patient list.
     */
    protected void preprocessUndoableCommandForPetPatient() throws CommandException {
        List<PetPatient> lastShownPetPatientList = model.getFilteredPetPatientList();

        if (index.getZeroBased() >= lastShownPetPatientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_PATIENT_DISPLAYED_INDEX);
        }

        petPatientToEdit = lastShownPetPatientList.get(index.getZeroBased());
        editedPetPatient = createEditedPetPatient(petPatientToEdit, editPetPatientDescriptor);
    }

    /**
     * Obtains the last shown appointment list.
     */
    protected void preprocessUndoableCommandForAppointment() throws CommandException {
        List<Appointment> lastShownAppointmentList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownAppointmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        appointmentToEdit = lastShownAppointmentList.get(index.getZeroBased());
        editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedNric, updatedTags);
    }

    /**
     * Creates and returns a {@code PetPatient} with the details of {@code petPatientToEdit}
     * edited with {@code editPetPatientDescriptor}.
     */
    private static PetPatient createEditedPetPatient(PetPatient petPatientToEdit,
                                                     EditPetPatientDescriptor editPetPatientDescriptor) {
        assert petPatientToEdit != null;

        PetPatientName updatedName = editPetPatientDescriptor.getName().orElse(petPatientToEdit.getName());
        Species updatedSpecies = editPetPatientDescriptor.getSpecies().orElse(petPatientToEdit.getSpecies());
        Breed updatedBreed = editPetPatientDescriptor.getBreed().orElse(petPatientToEdit.getBreed());
        Colour updatedColour = editPetPatientDescriptor.getColour().orElse(petPatientToEdit.getColour());
        BloodType updatedBloodType = editPetPatientDescriptor.getBloodType().orElse(petPatientToEdit.getBloodType());
        Nric updatedOwnerNric = editPetPatientDescriptor.getOwnerNric().orElse(petPatientToEdit.getOwner());
        Set<Tag> updatedTags = editPetPatientDescriptor.getTags().orElse(petPatientToEdit.getTags());

        return new PetPatient(
                updatedName,
                updatedSpecies,
                updatedBreed,
                updatedColour,
                updatedBloodType,
                updatedOwnerNric,
                updatedTags
        );
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        Nric updatedOwnerNric = editAppointmentDescriptor.getOwnerNric()
                .orElse(appointmentToEdit.getOwnerNric());
        PetPatientName updatedPetPatientName = editAppointmentDescriptor.getPetPatientName()
                .orElse(appointmentToEdit.getPetPatientName());
        Remark updatedRemark = editAppointmentDescriptor.getRemark()
                .orElse(appointmentToEdit.getRemark());
        LocalDateTime updatedLocalDateTime = editAppointmentDescriptor.getLocalDateTime()
                .orElse(appointmentToEdit.getDateTime());
        Set<Tag> updatedTags = editAppointmentDescriptor.getTags()
                .orElse(appointmentToEdit.getAppointmentTags());

        return new Appointment(
                updatedOwnerNric,
                updatedPetPatientName,
                updatedRemark,
                updatedLocalDateTime,
                updatedTags
        );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        switch (type) {
        case EDIT_PERSON:
            return index.equals(e.index)
                    && editPersonDescriptor.equals(e.editPersonDescriptor)
                    && Objects.equals(personToEdit, e.personToEdit);
        case EDIT_PET_PATIENT:
            return index.equals(e.index)
                    && editPetPatientDescriptor.equals(e.editPetPatientDescriptor)
                    && Objects.equals(petPatientToEdit, e.petPatientToEdit);
        case EDIT_APPOINTMENT:
            return index.equals(e.index)
                    && editAppointmentDescriptor.equals(e.editAppointmentDescriptor)
                    && Objects.equals(appointmentToEdit, e.appointmentToEdit);
        default:
            return false;
        }
    }
}
