package pl.compo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuGroup implements Cloneable {
    private List<SudokuField> elementsList = Arrays.asList(new SudokuField[9]);

    public SudokuGroup(List<SudokuField> elementsList) {
        this.elementsList.replaceAll(e -> new SudokuField());
        for (int i = 0; i < 9; i++) {
            int currentValue = elementsList.get(i).getValue();
            this.elementsList.get(i).setValue(currentValue);
        }
    }

    public boolean verify() {
        List<Boolean> tmpList = Arrays.asList(new Boolean[9]);
        Collections.fill(tmpList, Boolean.FALSE);
        for (int i = 0; i < 9; i++) {
            int tmpIndex = elementsList.get(i).getValue() - 1;
            if (tmpIndex == -1 || tmpList.get(tmpIndex)) {
                return false;
            }
            tmpList.set(tmpIndex, true);
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("elementsList", elementsList)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuGroup that)) {
            return false;
        }

        return new EqualsBuilder().append(elementsList, that.elementsList).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(elementsList).toHashCode();
    }

    @Override
    public SudokuGroup clone() {
        try {
            SudokuGroup clone = (SudokuGroup) super.clone();
            clone.elementsList = Arrays.asList(new SudokuField[9]);
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            for (int i = 0; i < 9; i++) {
                clone.elementsList.set(i, this.elementsList.get(i).clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
