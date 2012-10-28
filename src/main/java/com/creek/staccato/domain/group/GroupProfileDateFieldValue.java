package com.creek.staccato.domain.group;

import java.util.Date;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class GroupProfileDateFieldValue extends GroupProfileFieldValue {
    private Date value;

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }
}
