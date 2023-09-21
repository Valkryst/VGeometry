package com.valkryst.VGeometry;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestCircle {
    @AfterEach
    public void after() {
        try {
            Files.deleteIfExists(Paths.get("temp.ser"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor_withPointAndRadius() {
        final var midpoint = new Point(0, 0);
        final double radius = 10;

        final var circle = new Circle(midpoint, radius);
        assertNotNull(circle);
        assertEquals(midpoint, circle.getMidpoint());
        assertEquals(radius, circle.getRadius(), 1e-15);
        assertEquals(314.16, circle.getArea(), 0.001);
        assertEquals(62.83, circle.getCircumference(), 0.002);
        assertEquals(20, circle.getDiameter(), 1e-15);
        assertNotSame(midpoint, circle.getMidpoint());
    }

    @Test
    public void testConstructor_withNullPoint() {
        assertThrows(NullPointerException.class, () -> new Circle(null, 10));
    }

    @Test
    public void testConstructor_withPointAndNegativeRadius() {
        final var midpoint = new Point(0, 0);
        final double radius = -10;

        final var circle = new Circle(midpoint, radius);
        assertNotNull(circle);
        assertEquals(midpoint, circle.getMidpoint());
        assertEquals(10, circle.getRadius(), 1e-15);
        assertEquals(314.16, circle.getArea(), 0.001);
        assertEquals(62.83, circle.getCircumference(), 0.002);
        assertEquals(20, circle.getDiameter(), 1e-15);
        assertNotSame(midpoint, circle.getMidpoint());
    }

    @Test
    public void testConstructor_withExistingCircle() {
        final var midpoint = new Point(0, 0);
        final double radius = 10;

        final var original = new Circle(new Point(0, 0), 10);
        assertNotNull(original);
        assertEquals(midpoint, original.getMidpoint());
        assertEquals(radius, original.getRadius(), 1e-15);
        assertEquals(314.16, original.getArea(), 0.001);
        assertEquals(62.83, original.getCircumference(), 0.002);
        assertEquals(20, original.getDiameter(), 1e-15);

        final var clone = new Circle(original);
        assertNotNull(clone);
        assertEquals(midpoint, clone.getMidpoint());
        assertEquals(radius, clone.getRadius(), 1e-15);
        assertEquals(314.16, clone.getArea(), 0.001);
        assertEquals(62.83, clone.getCircumference(), 0.002);
        assertEquals(20, clone.getDiameter(), 1e-15);

        assertNotSame(original, clone);
        assertNotSame(original.getMidpoint(), clone.getMidpoint());
    }

    @Test
    public void testConstructor_withNullCircle() {
        assertThrows(NullPointerException.class, () -> new Circle((Circle) null));
    }

    @Test
    public void testConstructor_withJson() {
        final var midpoint = new Point(0, 0);
        final double radius = 10;

        final var original = new Circle(midpoint, radius);
        assertNotNull(original);
        assertEquals(midpoint, original.getMidpoint());
        assertEquals(radius, original.getRadius(), 1e-15);
        assertEquals(314.16, original.getArea(), 0.002);
        assertEquals(62.83, original.getCircumference(), 0.005);
        assertEquals(20, original.getDiameter(), 0.002);
        assertNotSame(midpoint, original.getMidpoint());

        final var newCircle = new Circle(original.toJson());
        assertNotNull(newCircle);
        assertEquals(midpoint, newCircle.getMidpoint());
        assertEquals(radius, newCircle.getRadius(), 1e-15);
        assertEquals(314.16, newCircle.getArea(), 0.002);
        assertEquals(62.83, newCircle.getCircumference(), 0.005);
        assertEquals(20, newCircle.getDiameter(), 0.002);
        assertNotSame(midpoint, newCircle.getMidpoint());

        assertNotSame(original, newCircle);
    }

    @Test
    public void testConstructor_withNullJson() {
        assertThrows(NullPointerException.class, () -> new Circle((JSONObject) null));
    }

    @Test
    public void testSerializationAndDeserialization() {
        final var originalCircle = new Circle(new Point(0, 0), 10);

        try (
            final var fos = new FileOutputStream("temp.ser");
            final var oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(originalCircle);
            oos.flush();
        } catch(final Exception e) {
            e.printStackTrace();
            fail();
        }

        try (
            final var fis = new FileInputStream("temp.ser");
            final var ois = new ObjectInputStream(fis);
        ) {
            final var loadedCircle = (Circle) ois.readObject();
            assertEquals(originalCircle.getMidpoint(), loadedCircle.getMidpoint());
            assertEquals(originalCircle.getRadius(), loadedCircle.getRadius(), 1e-15);
            assertEquals(314.16, loadedCircle.getArea(), 0.001);
            assertEquals(62.83, loadedCircle.getCircumference(), 0.002);
            assertEquals(20, loadedCircle.getDiameter(), 1e-15);
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testToJson() {
        final var midpoint = new Point(0, 0);
        final double radius = 10;

        final var circle = new Circle(midpoint, radius);

        final var json = circle.toJson();
        assertNotNull(json);
        assertEquals(radius, json.getDouble("radius"), 1e-15);
        assertFalse(json.has("area"));
        assertFalse(json.has("circumference"));
        assertFalse(json.has("diameter"));


        final var midpointJson = json.getJSONObject("midpoint");
        assertNotNull(midpointJson);
        assertEquals(midpoint.getX(), midpointJson.getInt("x"));
        assertEquals(midpoint.getY(), midpointJson.getInt("y"));
    }

    @Test
    public void testToString() {
        final var circle = new Circle(new Point(0, 0), 10);
        assertNotNull(circle.toString());
        assertFalse(circle.toString().isEmpty());
    }

    @Test
    public void testSetMidpoint() {
        final var midpoint = new Point(0, 0);

        final var circle = new Circle(midpoint, 10);
        circle.setMidpoint(new Point(1, 1));

        assertNotSame(midpoint, circle.getMidpoint());
    }

    @Test
    public void testSetMidpoint_withNullPoint() {
        final var circle = new Circle(new Point(0, 0), 10);
        assertThrows(NullPointerException.class, () -> circle.setMidpoint(null));
    }

    @Test
    public void testSetRadius() {
        final var midpoint = new Point(0, 0);
        final double radius = 10;

        final var circle = new Circle(midpoint, radius);
        circle.setRadius(20);

        assertEquals(20, circle.getRadius(), 1e-15);
    }

    @Test
    public void testSetRadius_ensureAreaIsUpdated() {
        final var midpoint = new Point(0, 0);
        final double radius = 10;

        final var circle = new Circle(midpoint, radius);
        circle.setRadius(20);

        assertEquals(1256.64, circle.getArea(), 0.01);
    }

    @Test
    public void testSetRadius_ensureCircumferenceIsUpdated() {
        final var midpoint = new Point(0, 0);
        final double radius = 10;

        final var circle = new Circle(midpoint, radius);
        circle.setRadius(20);

        assertEquals(125.66, circle.getCircumference(), 0.004);
    }

    @Test
    public void testSetRadius_ensureDiameterIsUpdated() {
        final var midpoint = new Point(0, 0);
        final double radius = 10;

        final var circle = new Circle(midpoint, radius);
        circle.setRadius(20);

        assertEquals(40, circle.getDiameter(), 1e-15);
    }
}
