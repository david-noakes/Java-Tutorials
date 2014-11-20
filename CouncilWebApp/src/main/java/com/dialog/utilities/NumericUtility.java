package com.dialog.utilities;

import java.math.BigDecimal;


/**
 * Null-tolerant means of performing numeric operations.
 * 
 */
public class NumericUtility implements java.util.Comparator {

    private static final BigDecimal ZERO = new BigDecimal("0");

    public NumericUtility() {
    }

    /**
     * Sums two {@link Number}s or {@link Numeric} objects.
     * 
     * @param one
     *        A number to add.&nbsp; Nulls are assumed to be zero.
     * @param two
     *        The other number to add.&nbsp; Nulls are assumed to be zero.
     * @return The sum of the two numbers.
     * @throws ClassCastException
     *         if either of the two arguments are not a {@link Number} or
     *         {@link Numeric} object.
     * @throws NumberFormatException
     *         if a given value is expressed as a String that is not a valid
     *         number.
     */
    public static final BigDecimal add(Object one, Object two) {
        return cast(one).add(cast(two));
    }

    /**
     * Obtains the difference of two {@link Number}s or {@link Numeric}
     * objects.
     * 
     * @param one
     *        The first value.&nbsp; Nulls are assumed to be zero.
     * @param two
     *        The value to subtract from the first value.&nbsp; Nulls are
     *        assumed to be zero.
     * @return The difference of the two numbers.
     * @throws ClassCastException
     *         if either of the two arguments are not a {@link Number} or
     *         {@link Numeric} object.
     * @throws NumberFormatException
     *         if a given value is expressed as a String that is not a valid
     *         number.
     */
    public static final BigDecimal subtract(Object one, Object two) {
        return cast(one).subtract(cast(two));
    }

    /**
     * Compares two numeric objects for ordering.
     * 
     * @param o1
     *        One object to compare.&nbsp; Nulls are assumed to be zero.
     * @param o2
     *        The second object to compare.&nbsp; Nulls are assumed to be zero.
     * @throws ClassCastException
     *         if either of the two arguments are not {@link String},{@link Number}
     *         or {@link Numeric} objects.
     * @throws NumberFormatException
     *         if a given value is expressed as a String that is not a valid
     *         number.
     */
    public final int compare(Object o1, Object o2) {
        return cast(o1).compareTo(cast(o2));
    }

    /**
     * Compares the absolute values of two numbers for ordering.
     * 
     * @param o1
     *        A number to compare.
     * @param o2
     *        Another number to compare.
     * @return The results of comparing the absolute values of the respective
     *         numbers, as per the general contract of
     *         {@link java.util.Comparator#compare(Object, Object)}.
     */
    public final int compareAbsolute(Object o1, Object o2) {
        return cast(o1).abs().compareTo(cast(o2).abs());
    }

    /**
     * @param o
     *        The object to cast.
     * @return A non-null BigDecimal containing the numerical value represented
     *         by the given object.
     * @throws ClassCastException
     *         if the given object
     * @throws NumberFormatException
     *         if a given value is expressed as a String that is not a valid
     *         number.
     */
    private static final BigDecimal cast(Object o) {
        if (o == null) {
            return ZERO;
        }
        if (o instanceof Number) {
            if (o instanceof BigDecimal) {
                return (BigDecimal) o;
            } else {
                return cast(o.toString());
            }
        }
        if (o instanceof String) {
            return new BigDecimal(o.toString());
        }
        throw new ClassCastException(
                "Object is not a String, Number or Numeric object");
    }

    public final boolean equals(Object o) {
        return o != null && o instanceof NumericUtility;
    }


    /*
     * params b_d: BidDecimal value to round
     * nNbDecimal: int number of decimal places to which to round 
     */
    public static final BigDecimal roundBD(BigDecimal b_d, int nNbDecimal)
    {
        if(nNbDecimal >= 0)
        {
            return b_d.setScale(nNbDecimal, BigDecimal.ROUND_HALF_UP);
        } else {
            return b_d;
        }
    }
}
