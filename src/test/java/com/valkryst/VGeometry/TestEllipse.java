package com.valkryst.VGeometry;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestEllipse {
    @AfterEach
    public void after() {
        try {
            Files.deleteIfExists(Paths.get("temp.ser"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor_withPointAndRadii() {
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final var ellipse = new Ellipse(midpoint, horizontalRadius, verticalRadius);
        assertNotNull(ellipse);
        assertEquals(midpoint, ellipse.getMidpoint());
        assertEquals(horizontalRadius, ellipse.getHorizontalRadius(), 1e-15);
        assertEquals(verticalRadius, ellipse.getVerticalRadius(), 1e-15);
        assertEquals(628.32, ellipse.getArea(), 0.002);
        assertEquals(96.88, ellipse.getCircumference(), 0.005);
        assertEquals(20, ellipse.getHorizontalDiameter(), 1e-15);
        assertEquals(40, ellipse.getVerticalDiameter(), 1e-15);
        assertNotSame(midpoint, ellipse.getMidpoint());
    }

    @Test
    public void testConstructor_withNullPoint() {
        assertThrows(NullPointerException.class, () -> new Ellipse(null, 10, 20));
    }

    @Test
    public void testConstructor_withExistingEllipse() {
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final var original = new Ellipse(midpoint, horizontalRadius, verticalRadius);
        assertNotNull(original);
        assertEquals(midpoint, original.getMidpoint());
        assertEquals(horizontalRadius, original.getHorizontalRadius(), 1e-15);
        assertEquals(verticalRadius, original.getVerticalRadius(), 1e-15);
        assertEquals(628.32, original.getArea(), 0.002);
        assertEquals(96.88, original.getCircumference(), 0.005);
        assertEquals(20, original.getHorizontalDiameter(), 1e-15);
        assertEquals(40, original.getVerticalDiameter(), 1e-15);
        assertNotSame(midpoint, original.getMidpoint());

        final var clone = new Ellipse(original);
        assertNotNull(clone);
        assertEquals(midpoint, clone.getMidpoint());
        assertEquals(horizontalRadius, clone.getHorizontalRadius(), 1e-15);
        assertEquals(verticalRadius, clone.getVerticalRadius(), 1e-15);
        assertEquals(628.32, clone.getArea(), 0.002);
        assertEquals(96.88, clone.getCircumference(), 0.005);
        assertEquals(20, clone.getHorizontalDiameter(), 1e-15);
        assertEquals(40, clone.getVerticalDiameter(), 1e-15);
        assertNotSame(midpoint, clone.getMidpoint());

        assertNotSame(original, clone);
        assertNotSame(original.getMidpoint(), clone.getMidpoint());
    }

    @Test
    public void testConstructor_withNullEllipse() {
        assertThrows(NullPointerException.class, () -> new Ellipse((Ellipse) null));
    }

    @Test
    public void testConstructor_withJson() {
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final var original = new Ellipse(midpoint, horizontalRadius, verticalRadius);
        assertNotNull(original);
        assertEquals(midpoint, original.getMidpoint());
        assertEquals(horizontalRadius, original.getHorizontalRadius(), 1e-15);
        assertEquals(verticalRadius, original.getVerticalRadius(), 1e-15);
        assertEquals(628.32, original.getArea(), 0.002);
        assertEquals(96.88, original.getCircumference(), 0.005);
        assertEquals(20, original.getHorizontalDiameter(), 1e-15);
        assertEquals(40, original.getVerticalDiameter(), 1e-15);
        assertNotSame(midpoint, original.getMidpoint());

        final var newEllipse = new Ellipse(original.toJson());
        assertNotNull(newEllipse);
        assertEquals(midpoint, newEllipse.getMidpoint());
        assertEquals(horizontalRadius, newEllipse.getHorizontalRadius(), 1e-15);
        assertEquals(verticalRadius, newEllipse.getVerticalRadius(), 1e-15);
        assertEquals(628.32, newEllipse.getArea(), 0.002);
        assertEquals(96.88, newEllipse.getCircumference(), 0.005);
        assertEquals(20, newEllipse.getHorizontalDiameter(), 1e-15);
        assertEquals(40, newEllipse.getVerticalDiameter(), 1e-15);
        assertNotSame(midpoint, newEllipse.getMidpoint());

        assertNotSame(original, newEllipse);
    }

    @Test
    public void testConstructor_withNullJson() {
        assertThrows(NullPointerException.class, () -> new Ellipse((JSONObject) null));
    }

    @Test
    public void testSerializationAndDeserialization() {
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;
        final var originalEllipse = new Ellipse(midpoint, horizontalRadius, verticalRadius);

        try (
            final var fos = new FileOutputStream("temp.ser");
            final var oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(originalEllipse);
            oos.flush();
        } catch(final Exception e) {
            e.printStackTrace();
            fail();
        }

        try (
            final var fis = new FileInputStream("temp.ser");
            final var ois = new ObjectInputStream(fis);
        ) {
            final var loadedEllipse = (Ellipse) ois.readObject();
            assertNotNull(loadedEllipse);
            assertEquals(midpoint, loadedEllipse.getMidpoint());
            assertEquals(horizontalRadius, loadedEllipse.getHorizontalRadius(), 1e-15);
            assertEquals(verticalRadius, loadedEllipse.getVerticalRadius(), 1e-15);
            assertEquals(628.32, loadedEllipse.getArea(), 0.002);
            assertEquals(96.88, loadedEllipse.getCircumference(), 0.005);
            assertEquals(20, loadedEllipse.getHorizontalDiameter(), 1e-15);
            assertEquals(40, loadedEllipse.getVerticalDiameter(), 1e-15);
            assertNotSame(midpoint, loadedEllipse.getMidpoint());
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testToJson() {
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final var ellipse = new Ellipse(midpoint, horizontalRadius, verticalRadius);

        final var json = ellipse.toJson();
        assertNotNull(json);
        assertEquals(horizontalRadius, json.getDouble("horizontalRadius"), 1e-15);
        assertEquals(verticalRadius, json.getDouble("verticalRadius"), 1e-15);
        assertFalse(json.has("area"));
        assertFalse(json.has("circumference"));
        assertFalse(json.has("horizontalDiameter"));
        assertFalse(json.has("verticalDiameter"));


        final var midpointJson = json.getJSONObject("midpoint");
        assertNotNull(midpointJson);
        assertEquals(midpoint.getX(), midpointJson.getInt("x"));
        assertEquals(midpoint.getY(), midpointJson.getInt("y"));
    }

    @Test
    public void testToString() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        assertNotNull(ellipse.toString());
        assertFalse(ellipse.toString().isEmpty());
    }

    @Test
    public void testSetMidpoint() {
        final var midpoint = new Point(0, 0);

        final var ellipse = new Ellipse(midpoint, 10, 20);
        ellipse.setMidpoint(new Point(10, 10));

        assertNotSame(midpoint, ellipse.getMidpoint());
    }

    @Test
    public void testSetMidpoint_withNullPoint() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        assertThrows(NullPointerException.class, () -> ellipse.setMidpoint(null));
    }

    @Test
    public void testSetHorizontalRadius() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
    }

    @Test
    public void testSetHorizontalRadius_ensureAreaIsUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        assertEquals(1884.96, ellipse.getArea(), 0.01);
    }

    @Test
    public void testSetHorizontalRadius_ensureCircumferenceIsUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        assertEquals(158.65, ellipse.getCircumference(), 0.01);
    }

    @Test
    public void testSetHorizontalRadius_ensureDiametersAreUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        assertEquals(60, ellipse.getHorizontalDiameter(), 1e-15);
        assertEquals(40, ellipse.getVerticalDiameter(), 1e-15);
    }

    @Test
    public void testSetVerticalRadius() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
    }

    @Test
    public void testSetVerticalRadius_ensureAreaIsUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        assertEquals(942.48, ellipse.getArea(), 0.01);
    }

    @Test
    public void testSetVerticalRadius_ensureCircumferenceIsUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        assertEquals(133.65, ellipse.getCircumference(), 0.01);
    }

    @Test
    public void testSetVerticalRadius_ensureDiametersAreUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        assertEquals(20, ellipse.getHorizontalDiameter(), 1e-15);
        assertEquals(60, ellipse.getVerticalDiameter(), 1e-15);
    }
}
