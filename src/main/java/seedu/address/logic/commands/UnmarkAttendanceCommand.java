package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class UnmarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove an attendance record from the person identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: \n"
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_ATTENDANCE_SUCCESS = "Removed attendance for %1$s on %2$s";

    private final Index targetIndex;
    private final LocalDateTime classDate;

    /**
     * Creates an AddCommand to mark the specified {@code Person} as present on
     * {@code Date}
     */
    public UnmarkAttendanceCommand(Index index, LocalDateTime date) {
        requireNonNull(index);
        requireNonNull(date);
        this.targetIndex = index;
        this.classDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person studentToUnmark = lastShownList.get(targetIndex.getZeroBased());
        Person studentUnmarked = studentToUnmark.removeAttendance(classDate);

        model.setPerson(studentToUnmark, studentUnmarked);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(
                String.format(MESSAGE_UNMARK_ATTENDANCE_SUCCESS, studentUnmarked.getName(), classDate));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkAttendanceCommand otherUnmarkAttendanceCommand)) {
            return false;
        }

        return targetIndex.equals(otherUnmarkAttendanceCommand.targetIndex)
                && classDate.equals(otherUnmarkAttendanceCommand.classDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Index", targetIndex)
                .add("Date", classDate)
                .toString();
    }
}
