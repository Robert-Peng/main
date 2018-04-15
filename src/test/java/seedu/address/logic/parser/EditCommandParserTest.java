package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.COLOUR_DESC_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.COLOUR_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_THREE;
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
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_OWNER;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_PET;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_THREE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHECKUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DEPRESSION;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FIV;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_VACCINATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHECKUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DEPRESSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FIV;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_VACCINATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PETPATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PETPATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PETPATIENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.descriptors.EditAppointmentDescriptor;
import seedu.address.logic.descriptors.EditPersonDescriptor;
import seedu.address.logic.descriptors.EditPetPatientDescriptor;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.petpatient.BloodType;
import seedu.address.model.petpatient.Breed;
import seedu.address.model.petpatient.Colour;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.Species;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditPetPatientDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private static final String MESSAGE_INVALID_LOCAL_DATE_TIME =
            "Please give a valid date and time based on the format yyyy-MM-dd HH:mm!";

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parseOwner_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, OPTION_OWNER + VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, OPTION_OWNER + "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, OPTION_OWNER + "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseOwner_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, OPTION_OWNER + "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, OPTION_OWNER + "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, OPTION_OWNER + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, OPTION_OWNER + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseOwner_invalidValue_failure() {
        assertParseFailure(parser, OPTION_OWNER + "1"
                + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, OPTION_OWNER + "1"
                + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, OPTION_OWNER + "1"
                + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS); // invalid email
        assertParseFailure(parser, OPTION_OWNER + "1"
                + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address
        assertParseFailure(parser, OPTION_OWNER + "1"
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, OPTION_OWNER + "1"
                + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, OPTION_OWNER + "1"
                + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, OPTION_OWNER + "1"
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, OPTION_OWNER + "1"
                + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, OPTION_OWNER + "1"
                + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, OPTION_OWNER + "1"
                + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                + VALID_PHONE_AMY, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parseOwner_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = OPTION_OWNER + targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseOwner_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = OPTION_OWNER + targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseOwner_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = OPTION_OWNER + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = OPTION_OWNER + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = OPTION_OWNER + targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = OPTION_OWNER + targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = OPTION_OWNER + targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseOwner_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = OPTION_OWNER + targetIndex.getOneBased()  + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + EMAIL_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseOwner_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = OPTION_OWNER + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = OPTION_OWNER + targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseOwner_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = OPTION_OWNER + targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    //@@author chialejing

    // Tests for PetPatients

    @Test
    public void parsePetPatient_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, OPTION_PET + VALID_NAME_HAZEL, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, OPTION_PET + "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, OPTION_PET + "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parsePetPatient_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, OPTION_PET + "-5" + NAME_DESC_HAZEL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, OPTION_PET + "0" + NAME_DESC_HAZEL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, OPTION_PET + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, OPTION_PET + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parsePetPatient_invalidValue_failure() {
        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_NAME_DESC, PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_SPECIES_DESC, Species.MESSAGE_PET_SPECIES_CONSTRAINTS); // invalid species
        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_BREED_DESC, Breed.MESSAGE_PET_BREED_CONSTRAINTS); // invalid breed
        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_COLOUR_DESC, Colour.MESSAGE_PET_COLOUR_CONSTRAINTS); // invalid colour
        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_BLOODTYPE_DESC, BloodType.MESSAGE_PET_BLOODTYPE_CONSTRAINTS); // invalid blood type
        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid species followed by valid breed
        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_SPECIES_DESC + BREED_DESC_JOKER, Species.MESSAGE_PET_SPECIES_CONSTRAINTS);

        // valid breed followed by invalid breed. The test case for invalid breed followed by valid breed
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, OPTION_PET + "1"
                + BREED_DESC_JOKER + INVALID_BREED_DESC, Breed.MESSAGE_PET_BREED_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code PetPatient} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, OPTION_PET + "1"
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, OPTION_PET + "1"
                + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, OPTION_PET + "1"
                + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        // Note: order matters!
        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_NAME_DESC
                + INVALID_BLOODTYPE_DESC
                + INVALID_COLOUR_DESC
                + VALID_BREED_JOKER, PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS);

        assertParseFailure(parser, OPTION_PET + "1"
                + INVALID_COLOUR_DESC
                + INVALID_BLOODTYPE_DESC
                + VALID_BREED_JOKER, Colour.MESSAGE_PET_COLOUR_CONSTRAINTS);
    }

    @Test
    public void parsePetPatient_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PETPATIENT;
        String userInput = OPTION_PET + targetIndex.getOneBased()
                + TAG_DESC_FIV
                + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER
                + COLOUR_DESC_JOKER
                + NAME_DESC_JOKER
                + TAG_DESC_DEPRESSION
                + BLOODTYPE_DESC_JOKER
                + NRIC_DESC_BOB;

        EditPetPatientDescriptor descriptor = new EditPetPatientDescriptorBuilder()
                .withName(VALID_NAME_JOKER)
                .withSpecies(VALID_SPECIES_JOKER)
                .withBreed(VALID_BREED_JOKER)
                .withColour(VALID_COLOUR_JOKER)
                .withBloodType(VALID_BLOODTYPE_JOKER)
                .withOwnerNric(VALID_NRIC_BOB)
                .withTags(VALID_TAG_FIV, VALID_TAG_DEPRESSION)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parsePetPatient_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PETPATIENT;
        String userInput = OPTION_PET + targetIndex.getOneBased() + SPECIES_DESC_JOKER + BLOODTYPE_DESC_JOKER;

        EditPetPatientDescriptor descriptor = new EditPetPatientDescriptorBuilder()
                .withSpecies(VALID_SPECIES_JOKER)
                .withBloodType(VALID_BLOODTYPE_JOKER)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parsePetPatient_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PETPATIENT;
        String userInput = OPTION_PET + targetIndex.getOneBased() + NAME_DESC_JOKER;
        EditPetPatientDescriptor descriptor = new EditPetPatientDescriptorBuilder()
                .withName(VALID_NAME_JOKER)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // species
        userInput = OPTION_PET + targetIndex.getOneBased() + SPECIES_DESC_JOKER;
        descriptor = new EditPetPatientDescriptorBuilder()
                .withSpecies(VALID_SPECIES_JOKER)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // breed
        userInput = OPTION_PET + targetIndex.getOneBased() + BREED_DESC_JOKER;
        descriptor = new EditPetPatientDescriptorBuilder()
                .withBreed(VALID_BREED_JOKER)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // colour
        userInput = OPTION_PET + targetIndex.getOneBased() + COLOUR_DESC_JOKER;
        descriptor = new EditPetPatientDescriptorBuilder()
                .withColour(VALID_COLOUR_JOKER)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // blood type
        userInput = OPTION_PET + targetIndex.getOneBased() + BLOODTYPE_DESC_JOKER;
        descriptor = new EditPetPatientDescriptorBuilder()
                .withBloodType(VALID_BLOODTYPE_JOKER)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // owner nric
        userInput = OPTION_PET + targetIndex.getOneBased() + NRIC_DESC_BOB;
        descriptor = new EditPetPatientDescriptorBuilder()
                .withOwnerNric(VALID_NRIC_BOB)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = OPTION_PET + targetIndex.getOneBased() + TAG_DESC_DEPRESSION;
        descriptor = new EditPetPatientDescriptorBuilder()
                .withTags(VALID_TAG_DEPRESSION)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parsePetPatient_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PETPATIENT;
        String userInput = OPTION_PET + targetIndex.getOneBased()
                + COLOUR_DESC_JOKER
                + BREED_DESC_JOKER
                + SPECIES_DESC_JOKER
                + TAG_DESC_DEPRESSION
                + COLOUR_DESC_JOKER
                + BREED_DESC_JOKER
                + SPECIES_DESC_JOKER
                + TAG_DESC_FIV
                + COLOUR_DESC_HAZEL
                + BREED_DESC_HAZEL
                + SPECIES_DESC_HAZEL
                + TAG_DESC_FIV;

        EditPetPatientDescriptor descriptor = new EditPetPatientDescriptorBuilder()
                .withColour(VALID_COLOUR_HAZEL)
                .withBreed(VALID_BREED_HAZEL)
                .withSpecies(VALID_SPECIES_HAZEL)
                .withTags(VALID_TAG_DEPRESSION, VALID_TAG_FIV)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parsePetPatient_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PETPATIENT;
        String userInput = OPTION_PET + targetIndex.getOneBased() + INVALID_NAME_DESC + NAME_DESC_JOKER;
        EditPetPatientDescriptor descriptor = new EditPetPatientDescriptorBuilder()
                .withName(VALID_NAME_JOKER)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = OPTION_PET + targetIndex.getOneBased()
                + SPECIES_DESC_JOKER
                + INVALID_COLOUR_DESC
                + BREED_DESC_JOKER
                + COLOUR_DESC_JOKER;
        descriptor = new EditPetPatientDescriptorBuilder()
                .withSpecies(VALID_SPECIES_JOKER)
                .withColour(VALID_COLOUR_JOKER)
                .withBreed(VALID_BREED_JOKER)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parsePetPatient_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PETPATIENT;
        String userInput = OPTION_PET + targetIndex.getOneBased() + TAG_EMPTY;

        EditPetPatientDescriptor descriptor = new EditPetPatientDescriptorBuilder()
                .withTags()
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // Tests for Appointment

    @Test
    public void parseAppointment_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, OPTION_APPOINTMENT + VALID_DATE_ONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, OPTION_APPOINTMENT + "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, OPTION_APPOINTMENT + "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseAppointment_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, OPTION_APPOINTMENT + "-5" + VALID_DATE_ONE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, OPTION_APPOINTMENT + "0" + VALID_DATE_ONE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, OPTION_APPOINTMENT + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, OPTION_APPOINTMENT + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseAppointment_invalidValue_failure() {
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + INVALID_DATETIME_DESC, MESSAGE_INVALID_LOCAL_DATE_TIME); // invalid datetime
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + INVALID_REMARK_DESC, Remark.MESSAGE_REMARK_CONSTRAINTS); // invalid remark
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid datetime followed by valid tag
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + INVALID_DATETIME_DESC + VALID_TAG_CHECKUP, MESSAGE_INVALID_LOCAL_DATE_TIME);

        // valid remark followed by invalid remark. The test case for invalid remark followed by valid remark
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + REMARK_DESC_ONE + INVALID_REMARK_DESC, Remark.MESSAGE_REMARK_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Appointment} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + TAG_DESC_CHECKUP + TAG_DESC_VACCINATION + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + TAG_DESC_CHECKUP + TAG_EMPTY + TAG_DESC_VACCINATION, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + TAG_EMPTY + TAG_DESC_CHECKUP + TAG_DESC_VACCINATION, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        // Note: order matters!
        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + INVALID_DATETIME_DESC + INVALID_REMARK_DESC + INVALID_TAG_DESC
                + VALID_REMARK_ONE, MESSAGE_INVALID_LOCAL_DATE_TIME);

        assertParseFailure(parser, OPTION_APPOINTMENT + "1"
                + INVALID_REMARK_DESC + INVALID_TAG_DESC
                + VALID_TAG_DEPRESSION, Remark.MESSAGE_REMARK_CONSTRAINTS);

    }

    @Test
    public void parseAppointment_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = OPTION_APPOINTMENT + targetIndex.getOneBased()
                + TAG_DESC_VACCINATION
                + TAG_DESC_CHECKUP
                + DATE_DESC_ONE
                + REMARK_DESC_ONE;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withLocalDateTime(VALID_DATE_ONE)
                .withRemark(VALID_REMARK_ONE)
                .withTags(VALID_TAG_CHECKUP, VALID_TAG_VACCINATION)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseAppointment_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_APPOINTMENT;
        String userInput = OPTION_APPOINTMENT + targetIndex.getOneBased() + REMARK_DESC_ONE + DATE_DESC_ONE;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withLocalDateTime(VALID_DATE_ONE)
                .withRemark(VALID_REMARK_ONE)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseAppointment_oneFieldSpecified_success() {
        // datetime
        Index targetIndex = INDEX_THIRD_APPOINTMENT;
        String userInput = OPTION_APPOINTMENT + targetIndex.getOneBased() + DATE_DESC_ONE;
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withLocalDateTime(VALID_DATE_ONE)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark
        userInput = OPTION_APPOINTMENT + targetIndex.getOneBased() + REMARK_DESC_ONE;
        descriptor = new EditAppointmentDescriptorBuilder()
                .withRemark(VALID_REMARK_ONE)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = OPTION_APPOINTMENT + targetIndex.getOneBased() + TAG_DESC_CHECKUP;
        descriptor = new EditAppointmentDescriptorBuilder()
                .withTags(VALID_TAG_CHECKUP)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseAppointment_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_SECOND_APPOINTMENT;
        String userInput = OPTION_APPOINTMENT + targetIndex.getOneBased()
                + DATE_DESC_ONE
                + REMARK_DESC_ONE
                + TAG_DESC_CHECKUP
                + DATE_DESC_TWO
                + REMARK_DESC_TWO
                + TAG_DESC_VACCINATION
                + DATE_DESC_THREE
                + REMARK_DESC_THREE
                + TAG_DESC_VACCINATION
                + TAG_DESC_CHECKUP;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withLocalDateTime(VALID_DATE_THREE)
                .withRemark(VALID_REMARK_THREE)
                .withTags(VALID_TAG_CHECKUP, VALID_TAG_VACCINATION)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseAppointment_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_SECOND_APPOINTMENT;
        String userInput = OPTION_APPOINTMENT + targetIndex.getOneBased() + INVALID_DATETIME_DESC + DATE_DESC_ONE;
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withLocalDateTime(VALID_DATE_ONE)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = OPTION_APPOINTMENT + targetIndex.getOneBased()
                + INVALID_DATETIME_DESC
                + DATE_DESC_ONE
                + REMARK_DESC_ONE
                + TAG_DESC_CHECKUP;
        descriptor = new EditAppointmentDescriptorBuilder()
                .withLocalDateTime(VALID_DATE_ONE)
                .withRemark(VALID_REMARK_ONE)
                .withTags(VALID_TAG_CHECKUP)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseAppointment_resetTags_success() {
        Index targetIndex = INDEX_SECOND_APPOINTMENT;
        String userInput = OPTION_APPOINTMENT + targetIndex.getOneBased() + TAG_EMPTY;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
