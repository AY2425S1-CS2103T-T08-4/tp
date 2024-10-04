package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {
    public static final String MESSAGE_CONSTRAINTS = "Remarks can take any values";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return otherRemark.value.equals(this.value);
    }
}
