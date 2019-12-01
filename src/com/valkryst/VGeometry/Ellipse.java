package com.valkryst.VGeometry;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

@ToString
public class Ellipse implements Serializable {
    private static final long serialVersionUID = 1;

    /** @serial The midpoint. */
    @Getter private Point midpoint = new Point(0, 0);
    /** @serial The horizontal radius. */
    @Getter private double horizontalRadius = 0;
    /** @serial The vertical radius. */
    @Getter private double verticalRadius = 0;

    /** The area. */
    @Getter private double area = 0;
    /** The circumference. */
    @Getter private double circumference = 0;
    /** The horizontal diameter. */
    @Getter private double horizontalDiameter = 0;
    /** The vertical diameter. */
    @Getter private double verticalDiameter = 0;

    /**
     * Constructs a new ellipse.
     *
     * @param midpoint
     *          The midpoint.
     *
     * @param horizontalRadius
     *          The horizontal radius.
     *
     * @param verticalRadius
     *          The vertical radius.
     */
    public Ellipse(final @NonNull Point midpoint, final double horizontalRadius, final double verticalRadius) {
        setMidpoint(midpoint);
        setHorizontalRadius(horizontalRadius);
        setVerticalRadius(verticalRadius);
    }

    /**
     * Constructs a new ellipse, using another ellipse.
     *
     * @param ellipse
     *          The other ellipse.
     */
    public Ellipse(final @NonNull Ellipse ellipse) {
        setMidpoint(ellipse.getMidpoint());
        setHorizontalRadius(ellipse.getHorizontalRadius());
        setVerticalRadius(ellipse.getVerticalRadius());
    }

    /**
     * Loads this ellipse from its serialized form.
     *
     * @param is
     *          The input stream.
     *
     * @throws ClassNotFoundException
     *          If the class of a serialized object cannot be found.
     *
     * @throws IOException
     *          If an I/O error occurs.
     */
    private void readObject(final ObjectInputStream is) throws ClassNotFoundException, IOException {
        is.defaultReadObject();
        updateArea();
        updateCircumference();
        updateDiameters();
    }

    /** Recalculates the area. */
    private void updateArea() {
        area = Math.PI * horizontalRadius * verticalRadius;
    }

    /** Recalculates the circumference. */
    private void updateCircumference() {
        final double h = Math.pow(verticalRadius - horizontalRadius, 2) / Math.pow(verticalRadius + horizontalRadius, 2);
        final double numerator = 3 * h;
        final double denominator = 10 + Math.sqrt(4 - (3 * h));
        circumference = Math.PI * (verticalRadius + horizontalRadius) * (1 + (numerator / denominator));
    }

    /** Recalculates the diameters. */
    private void updateDiameters() {
        horizontalDiameter = horizontalRadius * 2;
        verticalDiameter = verticalRadius * 2;
    }

    /**
     * Sets a new midpoint.
     *
     * @param midpoint
     *          The midpoint.
     */
    public void setMidpoint(final @NonNull Point midpoint) {
        this.midpoint.setX(midpoint.getX());
        this.midpoint.setY(midpoint.getY());
    }

    /**
     * Sets a new horizontal radius.
     *
     * @param horizontalRadius
     *          The horizontal radius.
     */
    public void setHorizontalRadius(final double horizontalRadius) {
        this.horizontalRadius = Math.abs(horizontalRadius);
        updateArea();
        updateCircumference();
        updateDiameters();
    }

    /**
     * Sets a new vertical radius.
     *
     * @param verticalRadius
     *          The vertical radius.
     */
    public void setVerticalRadius(final double verticalRadius) {
        this.verticalRadius = Math.abs(verticalRadius);
        updateArea();
        updateCircumference();
        updateDiameters();
    }
}
