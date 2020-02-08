package com.valkryst.VGeometry;

import lombok.*;
import org.json.JSONObject;

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
     * Constructs a new line, using the JSON representation of a line.
     *
     * @param json
     *          The JSON representation of a line.
     */
    public Line(final @NonNull JSONObject json) {
        setStartPoint(new Point(json.getJSONObject("startPoint")));
        setEndPoint(new Point(json.getJSONObject("endPoint")));
    }

    /**
     * Retrieves the JSON representation of this line.
     *
     * @return
     *      The JSON representation of this line.
     */
    public JSONObject toJson() {
        final var object = new JSONObject();
        object.put("startPoint", startPoint.toJson());
        object.put("endPoint", endPoint.toJson());
        return object;
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
