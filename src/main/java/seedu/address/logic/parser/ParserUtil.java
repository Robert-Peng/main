package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.petpatient.BloodType;
import seedu.address.model.petpatient.Breed;
import seedu.address.model.petpatient.Colour;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.Species;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    //@@author aquarinte
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     * First character of each word will be set to upper case.
     * All other characters will be set to lower case.
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        String[] wordsInName = trimmedName.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String n : wordsInName) {
            formattedName = formattedName.append(n.substring(0, 1).toUpperCase())
                    .append(n.substring(1).toLowerCase()).append(" ");
        }
        return new Name(formattedName.toString().trim());
    }
    //@@author
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        String[] wordsInAddress = trimmedAddress.split(" ");
        StringBuilder formattedAddress = new StringBuilder();
        for (String s : wordsInAddress) {
            formattedAddress = formattedAddress
                    .append(s.substring(0, 1).toUpperCase())
                    .append(s.substring(1).toLowerCase())
                    .append(" ");
        }
        return new Address(formattedAddress.toString().trim());
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(parseAddress(address.get())) : Optional.empty();
    }

    //@@author wynonaK
    /**
     * Parses a {@code String remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws IllegalValueException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(parseRemark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String dateTime} into an {@code LocalDateTime} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws IllegalValueException {
        requireNonNull(dateTime);

        dateTime = dateTime.trim();

        try {
            String[] dateTimeArray = dateTime.split("\\s+");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(dateTimeArray[0]);
        } catch (ParseException e) {
            throw new IllegalValueException("Please give a valid date and time based on the format yyyy-MM-dd HH:mm!");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Please give a valid date and time based on the format yyyy-MM-dd HH:mm!");
        }

        return localDateTime;
    }

    /**
     * Parses {@code Optional<String> dateTime} into an {@code Optional<LocalDateTime>} if {@code dateTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LocalDateTime> parseDateTime(Optional<String> dateTime) throws IllegalValueException {
        requireNonNull(dateTime);
        return dateTime.isPresent() ? Optional.of(parseDateTime(dateTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String date} into an {@code LocalDate} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code dateTime} is invalid.
     */
    public static LocalDate parseDate(String date) throws IllegalValueException {
        LocalDate localDate = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        date = date.trim();

        if (date.isEmpty()) {
            localDate = LocalDate.now();
            return localDate;
        }

        try {
            df.setLenient(false);
            df.parse(date);
            localDate = LocalDate.parse(date, formatter);
        } catch (ParseException | DateTimeParseException e) {
            throw new IllegalValueException("Please give a valid date based on the format yyyy-MM-dd!");
        }

        return localDate;
    }

    /**
     * Parses {@code Optional<String> date} into an {@code Optional<LocalDate>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LocalDate> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String stringYear} into an {@code Year} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code stringYear} is invalid.
     */
    public static Year parseYear(String stringYear) throws IllegalValueException {
        Year year = null;
        DateFormat df = new SimpleDateFormat("yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        stringYear = stringYear.trim();

        if (stringYear.isEmpty()) {
            year = Year.now();
            return year;
        }

        try {
            df.setLenient(false);
            df.parse(stringYear);
            year = Year.parse(stringYear, formatter);
        } catch (ParseException | DateTimeParseException e) {
            throw new IllegalValueException("Please give a valid year based on the format yyyy!");
        }

        return year;
    }

    /**
     * Parses {@code Optional<String> month} into an {@code Optional<Year>} if {@code year} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Year> parseYear(Optional<String> year) throws IllegalValueException {
        requireNonNull(year);
        return year.isPresent() ? Optional.of(parseYear(year.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String stringMonth} into an {@code YearMonth} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code stringMonth} is invalid.
     */
    public static YearMonth parseMonth(String stringMonth) throws IllegalValueException {
        YearMonth yearMonth = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        stringMonth = stringMonth.trim();

        if (stringMonth.isEmpty()) {
            yearMonth = YearMonth.now();
            return yearMonth;
        }

        try {
            if (stringMonth.length() == 2) {
                int month = Integer.parseInt(stringMonth);
                yearMonth = YearMonth.now().withMonth(month);
                return yearMonth;
            }

            df.setLenient(false);
            df.parse(stringMonth);
            yearMonth = YearMonth.parse(stringMonth, formatter);
        } catch (ParseException e) {
            throw new IllegalValueException("Please give a valid year and month based on the format yyyy-MM!");
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException("Please input integer for month in the format MM!");
        } catch (DateTimeException dte) {
            throw new IllegalValueException("Please give a valid month based on the format MM!");
        }

        return yearMonth;
    }

    /**
     * Parses {@code Optional<String> month} into an {@code Optional<YearMonth>} if {@code month} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<YearMonth> parseMonth(Optional<String> month) throws IllegalValueException {
        requireNonNull(month);
        return month.isPresent() ? Optional.of(parseMonth(month.get())) : Optional.empty();
    }

    //@@author
    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(parseEmail(email.get())) : Optional.empty();
    }

    //@@author Robert-Peng
    /**
     * Parses a {@code String nric} into a {@code NRIC}.
     * Leading and trailing whitespaces will be trimmed.
     * @param nric
     * @return
     * @throws IllegalValueException
     */
    public static Nric parseNric(String nric) throws IllegalValueException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code Optional<String> nric} into an {@code Optional<NRIC>} if {@code nric} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     * @param nric
     * @return
     * @throws IllegalValueException
     */
    public static Optional<Nric> parseNric(Optional<String> nric) throws IllegalValueException {
        requireNonNull(nric);
        return nric.isPresent() ? Optional.of(parseNric(nric.get())) : Optional.empty();
    }

    //@@author
    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        trimmedTag = trimmedTag.toLowerCase();
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //@@author aquarinte
    /**
     * Parses a {@code String name} into a {@code PetPatientName}.
     * Leading and trailing whitespaces will be trimmed.
     * First character of each word will be set to upper case.
     * All other characters will be set to lower case.
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static PetPatientName parsePetPatientName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!PetPatientName.isValidName(trimmedName)) {
            throw new IllegalValueException(PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS);
        }
        String[] wordsInName = trimmedName.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String n : wordsInName) {
            formattedName = formattedName.append(n.substring(0, 1).toUpperCase())
                    .append(n.substring(1).toLowerCase()).append(" ");
        }
        return new PetPatientName(formattedName.toString().trim());
    }

    //@@author chialejing
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<PetPatientName> parsePetPatientName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parsePetPatientName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String species} into a {@code Species}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Species parseSpecies(String species) throws IllegalValueException {
        requireNonNull(species);
        String trimmedSpecies = species.trim();
        if (!Species.isValidSpecies(trimmedSpecies)) {
            throw new IllegalValueException(Species.MESSAGE_PET_SPECIES_CONSTRAINTS);
        }
        String[] wordsInSpecies = trimmedSpecies.split(" ");
        StringBuilder formattedSpecies = new StringBuilder();
        for (String s : wordsInSpecies) {
            formattedSpecies = formattedSpecies
                    .append(s.substring(0, 1).toUpperCase())
                    .append(s.substring(1).toLowerCase())
                    .append(" ");
        }
        return new Species(formattedSpecies.toString().trim());
    }

    /**
     * Parses a {@code Optional<String> species} into an {@code Optional<Species>} if {@code species} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Species> parseSpecies(Optional<String> species) throws IllegalValueException {
        requireNonNull(species);
        return species.isPresent() ? Optional.of(parseSpecies(species.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String breed} into a {@code Breed}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Breed parseBreed(String breed) throws IllegalValueException {
        requireNonNull(breed);
        String trimmedBreed = breed.trim();
        if (!Breed.isValidBreed(trimmedBreed)) {
            throw new IllegalValueException(Breed.MESSAGE_PET_BREED_CONSTRAINTS);
        }
        String[] wordsInBreed = trimmedBreed.split(" ");
        StringBuilder formattedBreed = new StringBuilder();
        for (String s : wordsInBreed) {
            formattedBreed = formattedBreed
                    .append(s.substring(0, 1).toUpperCase())
                    .append(s.substring(1).toLowerCase())
                    .append(" ");
        }
        return new Breed(formattedBreed.toString().trim());
    }

    /**
     * Parses a {@code Optional<String> breed} into an {@code Optional<Breed>} if {@code breed} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Breed> parseBreed(Optional<String> breed) throws IllegalValueException {
        requireNonNull(breed);
        return breed.isPresent() ? Optional.of(parseBreed(breed.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String colour} into a {@code Colour}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Colour parseColour(String colour) throws IllegalValueException {
        requireNonNull(colour);
        String trimmedColour = colour.trim();
        if (!Colour.isValidColour(trimmedColour)) {
            throw new IllegalValueException(Colour.MESSAGE_PET_COLOUR_CONSTRAINTS);
        }
        String[] wordsInColour = trimmedColour.split(" ");
        StringBuilder formattedColour = new StringBuilder();
        for (String s : wordsInColour) {
            formattedColour = formattedColour
                    .append(s.substring(0).toLowerCase())
                    .append(" ");
        }
        return new Colour(formattedColour.toString().trim());
    }

    /**
     * Parses a {@code Optional<String> colour} into an {@code Optional<Colour>} if {@code colour} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Colour> parseColour(Optional<String> colour) throws IllegalValueException {
        requireNonNull(colour);
        return colour.isPresent() ? Optional.of(parseColour(colour.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String bloodType} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static BloodType parseBloodType(String bloodType) throws IllegalValueException {
        requireNonNull(bloodType);
        String trimmedBloodType = bloodType.trim();
        if (!BloodType.isValidBloodType(trimmedBloodType)) {
            throw new IllegalValueException(BloodType.MESSAGE_PET_BLOODTYPE_CONSTRAINTS);
        }
        String[] wordsInBloodType = trimmedBloodType.split(" ");
        StringBuilder formattedBloodType = new StringBuilder();
        for (String s : wordsInBloodType) {
            formattedBloodType = formattedBloodType
                    .append(s.substring(0).toUpperCase())
                    .append(" ");
        }
        return new BloodType(formattedBloodType.toString().trim());
    }

    /**
     * Parses a {@code Optional<String> bloodType} into an {@code Optional<BloodType>} if {@code bloodType} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<BloodType> parseBloodType(Optional<String> bloodType) throws IllegalValueException {
        requireNonNull(bloodType);
        return bloodType.isPresent() ? Optional.of(parseBloodType(bloodType.get())) : Optional.empty();
    }
}
