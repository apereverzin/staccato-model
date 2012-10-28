package com.creek.staccato.domain.group;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class GroupProfileNumericFieldValue extends GroupProfileFieldValue {
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
