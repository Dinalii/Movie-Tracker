package com.example.main.validations;

import android.text.InputFilter;
import android.text.Spanned;

public class IFilter implements InputFilter {

    /**
     * This sets the range limits of the TextEdit
     * if the value os between then the integer must be in between of max and min values
     * if the value is min then the integer must be grater then or equal to min value
     * if the value is max then the integer must be less then or equal to max value
     */
    public enum Boundary {min, max, between}

    ;
    private int min, max;
    private Boundary border;

    //calling the filter function
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(input))
                return null;
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    public IFilter(int min, int max) {
        this.min = min;
        this.max = max;
        this.border = Boundary.between;
    }

    public IFilter(int val, Boundary border) {
        this.border = border;
        switch (border) {
            case between:
                this.min = this.max = val;
                break;
            case min:
                this.min = min;
                break;
            case max:
                this.max = max;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + border);
        }

    }


    /**
     * check that the given value is in between max and min
     *
     * @param c integer that needs to validate
     * @return true or false depending on in range or not in range
     */
    private boolean isInRange(int c) {
        switch (border) {
            case between:
                return min <= c && c <= max;
            case min:
                return min <= c;
            case max:
                return c <= max;
            default:
                throw new IllegalStateException("Unexpected value: " + border);
        }
    }
}
