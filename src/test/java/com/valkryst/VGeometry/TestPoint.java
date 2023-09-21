package com.valkryst.VGeometry;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestPoint {
    @AfterEach
    public void after() {
        try {
            Files.deleteIfExists(Paths.get("temp.ser"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor_withNoArgs() {
        final var p = new Point();
        assertNotNull(p);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    @Test
    public void testConstructor_withInts() {
        final var p = new Point(123, 456);
        assertNotNull(p);
        assertEquals(123, p.getX());
        assertEquals(456, p.getY());
    }

    @Test
    public void testConstructor_withExistingPoint() {
        final var original = new Point(123, 456);
        assertNotNull(original);
        assertEquals(123, original.getX());
        assertEquals(456, original.getY());

        final var clone = new Point(original);
        assertNotNull(clone);
        assertEquals(123, clone.getX());
        assertEquals(456, clone.getY());

        assertNotSame(original, clone);
    }

    @Test
    public void testConstructor_withNullPoint() {
        assertThrows(NullPointerException.class, () -> new Point((Point) null));
    }

    @Test
    public void testConstructor_withJson() {
        final var original = new Point(123, 456);
        assertNotNull(original);
        assertEquals(123, original.getX());
        assertEquals(456, original.getY());

        final var newPoint = new Point(original.toJson());
        assertNotNull(newPoint);
        assertEquals(123, newPoint.getX());
        assertEquals(456, newPoint.getY());

        assertNotSame(original, newPoint);
    }

    @Test
    public void testConstructor_withNullJson() {
        assertThrows(NullPointerException.class, () -> new Point((JSONObject) null));
    }

    @Test
    public void testSerializationAndDeserialization() {
        final var originalPoint = new Point(123, 456);

        try (
            final var fos = new FileOutputStream("temp.ser");
            final var oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(originalPoint);
            oos.flush();
        } catch(final Exception e) {
            e.printStackTrace();
            fail();
        }

        try (
            final var fis = new FileInputStream("temp.ser");
            final var ois = new ObjectInputStream(fis);
        ) {
            final var loadedPoint = (Point) ois.readObject();
            assertEquals(originalPoint.getX(), loadedPoint.getX());
            assertEquals(originalPoint.getY(), loadedPoint.getY());
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testToJson() {
        final var p = new Point(123, 456);
        final var json = p.toJson();

        assertNotNull(json);
        assertTrue(json.has("x"));
        assertTrue(json.has("y"));
        assertEquals(123, json.getInt("x"));
        assertEquals(456, json.getInt("y"));
    }

    @Test
    public void testToString() {
        final var p = new Point(123, 456);
        assertNotNull(p.toString());
        assertFalse(p.toString().isEmpty());
    }

    @Test
    public void testGetX() {
        final var p = new Point(123, 456);
        assertEquals(123, p.getX());
    }

    @Test
    public void testGetY() {
        final var p = new Point(123, 456);
        assertEquals(456, p.getY());
    }

    @Test
    public void testSetX() {
        final var p = new Point(123, 456);
        assertEquals(123, p.getX());

        p.setX(789);
        assertEquals(789, p.getX());
    }

    @Test
    public void testSetY() {
        final var p = new Point(123, 456);
        assertEquals(456, p.getY());

        p.setY(789);
        assertEquals(789, p.getY());
    }
}
