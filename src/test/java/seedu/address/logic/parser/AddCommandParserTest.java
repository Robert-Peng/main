package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PARAMETER_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.COLOUR_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.COLOUR_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BLOODTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BREED_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COLOUR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OPTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_FION;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_OWNER;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_PET;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHECKUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DEPRESSION;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FIV;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_VACCINATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_FION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHECKUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FIV;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.petpatient.BloodType;
import seedu.address.model.petpatient.Breed;
import seedu.address.model.petpatient.Colour;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.Species;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PetPatientBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allPersonFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withNric(VALID_NRIC_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple names - last name accepted
        assertParseSuccess(parser,
                OPTION_OWNER + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withNric(VALID_NRIC_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_HUSBAND
            + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalPersonFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withNric(VALID_NRIC_AMY).withTags().build();
        assertParseSuccess(parser, OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + NRIC_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryPersonFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_PERSON);

        // missing name prefix
        assertParseFailure(parser, OPTION_OWNER + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, OPTION_OWNER + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + NRIC_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, OPTION_OWNER + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_ADDRESS_BOB + VALID_NRIC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidPersonValue_failure() {
        // invalid name
        assertParseFailure(parser, OPTION_OWNER + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_HUSBAND
            + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, OPTION_OWNER + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_HUSBAND
            + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
            + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_HUSBAND
            + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + INVALID_ADDRESS_DESC + NRIC_DESC_BOB + TAG_DESC_HUSBAND
            + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_NRIC_DESC + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Nric.MESSAGE_NRIC_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, OPTION_OWNER + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + NRIC_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, OPTION_OWNER + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + NRIC_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, OPTION_OWNER + PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + NRIC_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_PERSON));
    }

    //@@author aquarinte
    @Test
    public void parse_allPetPatientFieldsPresent_success() {

        PetPatient expectedPetPatient = new PetPatientBuilder().withName(VALID_NAME_JOKER)
                .withSpecies(VALID_SPECIES_JOKER).withBreed(VALID_BREED_JOKER).withColour(VALID_COLOUR_JOKER)
                .withBloodType(VALID_BLOODTYPE_JOKER).withTags(VALID_TAG_FIV).withOwnerNric(VALID_NRIC_FION).build();

        Nric fion = new Nric(VALID_NRIC_FION);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple names - last name accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_NERO + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                        + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple species - last species accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_NERO + SPECIES_DESC_JOKER
                        + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple breed - last breed accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_NERO
                        + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple colour - last colour accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_JOKER
                        + COLOUR_DESC_NERO + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple blood type - last blood type accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_JOKER
                        + COLOUR_DESC_JOKER + BLOODTYPE_DESC_NERO + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple tags - all accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_JOKER
                + COLOUR_DESC_JOKER + BLOODTYPE_DESC_NERO + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + TAG_DESC_DEPRESSION
                + OPTION_OWNER + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));
    }

    @Test
    public void parse_optionalPetPatientFieldsMissing_success() {
        // zero tags
        PetPatient expectedPetPatient = new PetPatientBuilder().withName(VALID_NAME_JOKER)
                .withSpecies(VALID_SPECIES_JOKER).withBreed(VALID_BREED_JOKER).withColour(VALID_COLOUR_JOKER)
                .withBloodType(VALID_BLOODTYPE_JOKER).withOwnerNric(VALID_NRIC_FION).build();

        Nric fion = new Nric(VALID_NRIC_FION);

        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));
    }

    @Test
    public void parse_compulsoryPetPatientFieldMissing_failure() {
        String invalidPetPatient = String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_PETPATIENT);
        String invalidAddCommand = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        String missingNricPrefix = String.format(MESSAGE_INVALID_PARAMETER_FORMAT,
                AddCommand.MESSAGE_MISSING_NRIC_PREFIX);

        // missing name prefix
        assertParseFailure(parser, OPTION_PET + VALID_NAME_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing species prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + VALID_SPECIES_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing breed prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + VALID_BREED_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing colour prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + VALID_COLOUR_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing blood type prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + VALID_BLOODTYPE_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing nric prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + VALID_NRIC_FION, missingNricPrefix);

        // missing all pet patient prefixes
        assertParseFailure(parser, OPTION_PET + VALID_NAME_JOKER + VALID_SPECIES_JOKER
                + VALID_BREED_JOKER + VALID_COLOUR_JOKER + VALID_BLOODTYPE_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing all prefixes
        assertParseFailure(parser, OPTION_PET + VALID_NAME_JOKER + VALID_SPECIES_JOKER
                + VALID_BREED_JOKER + VALID_COLOUR_JOKER + VALID_BLOODTYPE_JOKER + OPTION_OWNER
                + VALID_NRIC_FION, invalidPetPatient);

        // missing options
        assertParseFailure(parser, NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + NRIC_DESC_FION,
                invalidAddCommand);
    }

    @Test
    public void parse_invalidPetPatientValue_failure() {
        // invalid name
        assertParseFailure(parser, OPTION_PET + INVALID_NAME_DESC + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + INVALID_NRIC_DESC, Nric.MESSAGE_NRIC_CONSTRAINTS);

        // invalid breed
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + INVALID_BREED_DESC + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, Breed.MESSAGE_PET_BREED_CONSTRAINTS);

        // invalid species
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + INVALID_SPECIES_DESC
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, Species.MESSAGE_PET_SPECIES_CONSTRAINTS);

        // invalid colour
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + INVALID_COLOUR_DESC + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, Colour.MESSAGE_PET_COLOUR_CONSTRAINTS);

        // invalid blood type
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + INVALID_BLOODTYPE_DESC + OPTION_OWNER
                + NRIC_DESC_FION, BloodType.MESSAGE_PET_BLOODTYPE_CONSTRAINTS);
    }

    @Test
    public void parse_allAppointmentFieldsPresent_success() {
        Appointment appt = new AppointmentBuilder().withDateTime(VALID_DATE_ONE).withRemark(VALID_REMARK_ONE)
                .withAppointmentTags(VALID_TAG_CHECKUP).withOwnerNric(VALID_NRIC_FION)
                .withPetPatientName(VALID_NAME_JOKER).build();

        Nric fion = new Nric(VALID_NRIC_FION);
        PetPatientName joker = new PetPatientName(VALID_NAME_JOKER);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE
                + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER,
                new AddCommand(appt, fion, joker));

        // multiple date time - last one is accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_TWO + DATE_DESC_ONE + REMARK_DESC_ONE
                        + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER,
                new AddCommand(appt, fion, joker));

        // multiple remarks - last one is accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_TWO + REMARK_DESC_ONE
                        + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER,
                new AddCommand(appt, fion, joker));

        // multiple tags - all accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE + TAG_DESC_VACCINATION
                        + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER,
                new AddCommand(appt, fion, joker));

        // multiple nric - last one is accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE
                        + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_CHARLIE + NRIC_DESC_FION + OPTION_PET
                        + NAME_DESC_JOKER, new AddCommand(appt, fion, joker));

        // multiple pet name - last one is accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE
                + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET
                + NAME_DESC_NERO + NAME_DESC_JOKER, new AddCommand(appt, fion, joker));
    }

    @Test
    public void parse_compulsoryAppointmentFieldMissing_failure() {
        String invalidAppt = String.format(MESSAGE_INVALID_PARAMETER_FORMAT, AddCommand.MESSAGE_ERROR_APPOINTMENT);
        String invalidCommand = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing date time prefix
        assertParseFailure(parser, OPTION_APPOINTMENT + VALID_DATE_ONE + REMARK_DESC_ONE + TAG_DESC_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, invalidAppt);

        // missing remark prefix
        assertParseFailure(parser, OPTION_APPOINTMENT + DATE_DESC_ONE + VALID_REMARK_ONE + TAG_DESC_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, invalidAppt);

        // missing tag prefix
        assertParseFailure(parser, OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE + VALID_TAG_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, invalidAppt);

        // missing options
        assertParseFailure(parser, DATE_DESC_ONE + REMARK_DESC_ONE + TAG_DESC_CHECKUP + NRIC_DESC_FION
                + NAME_DESC_JOKER, invalidCommand);

        // missing -o, -p
        assertParseFailure(parser, OPTION_APPOINTMENT + VALID_DATE_ONE + REMARK_DESC_ONE + TAG_DESC_CHECKUP,
                invalidCommand);
    }

    @Test
    public void parse_invalidAppointmentValue_failure() {
        String invalidDateMsg = "Please give a valid date and time based on the format yyyy-MM-dd HH:mm!";
        String invalidDateTimeMsg = "Please give a valid date and time based on the format yyyy-MM-dd HH:mm!";

        // invalid remark
        assertParseFailure(parser, OPTION_APPOINTMENT + DATE_DESC_ONE + INVALID_REMARK_DESC + TAG_DESC_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, Remark.MESSAGE_REMARK_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, OPTION_APPOINTMENT + INVALID_DATETIME_DESC + REMARK_DESC_ONE + TAG_DESC_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, invalidDateMsg);

        // invalid time
        assertParseFailure(parser, OPTION_APPOINTMENT + INVALID_TIME_DESC + REMARK_DESC_ONE + TAG_DESC_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, invalidDateTimeMsg);
    }

    @Test
    public void parse_invalidCommandOption_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // invalid option
        assertParseFailure(parser, INVALID_OPTION + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, expectedMessage);

        // wrong order: -a, -p, -o
        assertParseFailure(parser, OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE + TAG_DESC_CHECKUP
                        + OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_JOKER + COLOUR_DESC_JOKER
                        + BLOODTYPE_DESC_JOKER + OPTION_OWNER + NRIC_DESC_FION, expectedMessage);
    }

}
