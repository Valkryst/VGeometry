package com.valkryst.VGeometry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.json.JSONObject;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class Point implements Serializable {
    private static final long serialVersionUID = 1;

    /** @serial The x coordinate. */
    private int x = 0;
    /** @serial The y coordinate. */
    private int y = 0;

    /**
     * Constructs a new point.
     *
     * @param x
     *          The x coordinate.
     *
     * @param y
     *          The y coordinate.
     */
    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new point, using another point.
     *
     * @param otherPoint
     *          The other point.
     */
    public Point(final @NonNull Point otherPoint) {
        this.x = otherPoint.x;
        this.y = otherPoint.y;
    }

    /**
     * Constructs a new point, using the JSON representation of a point.
     *
     * @param json
     *          The JSON representation of a point.
     */
    public Point(final @NonNull JSONObject json) {
        this.x = json.getInt("x");
        this.y = json.getInt("y");
    }

    /**
     * Retrieves the JSON representation of this point.
     *
     * @return
     *      The JSON representation of this point.
     */
    public JSONObject toJson() {
        final var object = new JSONObject();
        object.put("x", x);
        object.put("y", y);
        return object;
    }
}
