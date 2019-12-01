package com.valkryst.VGeometry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

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
}
