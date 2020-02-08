package com.valkryst.VGeometry;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

@ToString
public class Circle implements Serializable {
    private static final long serialVersionUID = 1;

    /** @serial The midpoint. */
    @Getter private Point midpoint = new Point(0, 0);
    /** @serial The radius. */
    @Getter private double radius = 0;

    /** The area. */
    @Getter private double area = 0;
    /** The circumference. */
    @Getter private double circumference = 0;
    /** The diameter. */
    @Getter private double diameter = 0;

    /**
     * Constructs a new circle.
     *
     * @param midpoint
     *          The midpoint.
     *
     * @param radius
     *          The radius.
     */
    public Circle(final @NonNull Point midpoint, final double radius) {
        setMidpoint(midpoint);
        setRadius(radius);
    }

    /**
     * Constructs a new circle, using another circle.
     *
     * @param circle
     *          The other circle.
     */
    public Circle(final @NonNull Circle circle) {
        setMidpoint(circle.getMidpoint());
        setRadius(circle.getRadius());
    }

    /**
     * Constructs a new circle, using the JSON representation of a circle.
     *
     * @param json
     *      The JSON representation of a circle.
     */
    public Circle(final @NonNull JSONObject json) {
        setMidpoint(new Point(json.getJSONObject("midpoint")));
        setRadius(json.getDouble("radius"));
    }

    /**
     * Retrieves the JSON representation of this circle.
     *
     * @return
     *      The JSON representation of this circle.
     */
    public JSONObject toJson() {
        final var object = new JSONObject();
        object.put("midpoint", midpoint.toJson());
        object.put("radius", radius);
        return object;
    }

    /**
     * Loads this circle from its serialized form.
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
        updateDiameter();
    }

    /** Recalculates the area. */
    private void updateArea() {
        area = Math.PI * radius * radius;
    }

    /** Recalculates the circumference. */
    private void updateCircumference() {
        circumference = 2 * Math.PI * radius;
    }

    /** Recalculates the diameter. */
    private void updateDiameter() {
        diameter = 2 * radius;
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
     * Sets a new radius.
     *
     * @param radius
     *          The radius.
     */
    public void setRadius(final double radius) {
        this.radius = Math.abs(radius);
        updateArea();
        updateCircumference();
        updateDiameter();
    }
}
