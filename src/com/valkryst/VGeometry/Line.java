package com.valkryst.VGeometry;

import lombok.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

@ToString
public class Line implements Serializable {
    private static final long serialVersionUID = 1;

    /** @serial The start point. */
    @Getter private Point startPoint = new Point(0, 0);
    /** @serial The end point. */
    @Getter private Point endPoint = new Point(0, 0);
    /** The slope. */
    @Getter private double slope = 0;

    /**
     * Constructs a new line.
     *
     * @param startPoint
     *          The start point.
     *
     * @param endPoint
     *          The end point.
     */
    public Line(final @NonNull Point startPoint, final @NonNull Point endPoint) {
        setStartPoint(startPoint);
        setEndPoint(endPoint);
    }

    /**
     * Constructs a new line, using another line.
     *
     * @param line
     *          The other line.
     */
    public Line(final @NonNull Line line) {
        setStartPoint(new Point(line.getStartPoint()));
        setEndPoint(new Point(line.getEndPoint()));
    }

    /**
     * Loads this line from its serialized form.
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
        updateSlope();
    }

    /** Recalculates the slope. */
    private void updateSlope() {
        final double numerator = endPoint.getY() - startPoint.getY();
        final double denominator = endPoint.getX() - startPoint.getX();

        if (numerator == 0 || denominator == 0) {
            slope = 0;
        } else {
            slope = numerator / denominator;
        }
    }

    /**
     * Sets a new start point.
     * 
     * @param startPoint
     *          The new start point.
     */
    public void setStartPoint(final @NonNull Point startPoint) {
        this.startPoint.setX(startPoint.getX());
        this.startPoint.setY(startPoint.getY());
        updateSlope();
    }

    /**
     * Sets a new end point.
     * 
     * @param endPoint
     *          The new end point.
     */
    public void setEndPoint(final @NonNull Point endPoint) {
        this.endPoint.setX(endPoint.getX());
        this.endPoint.setY(endPoint.getY());
        updateSlope();
    }
}
