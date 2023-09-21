package com.valkryst.VGeometry;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestLine {
    private Point startPoint;
    private Point endPoint;

    @BeforeEach
    public void before() {
        startPoint = new Point(0, 0);
        endPoint = new Point(10, 10);
    }

    @AfterEach
    public void after() {
        try {
            Files.deleteIfExists(Paths.get("temp.ser"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor_withPoints() {
        final var line = new Line(startPoint, endPoint);
        assertNotNull(line);
        assertEquals(startPoint, line.getStartPoint());
        assertEquals(endPoint, line.getEndPoint());
        assertEquals(1, line.getSlope(), 1e-15);

        assertNotSame(startPoint, line.getStartPoint());
        assertNotSame(endPoint, line.getEndPoint());
    }

    @Test
    public void testConstructor_withNullStartPoint() {
        assertThrows(NullPointerException.class, () -> new Line(null, endPoint));
    }

    @Test
    public void testConstructor_withNullEndPoint() {
        assertThrows(NullPointerException.class, () -> new Line(startPoint, null));
    }

    @Test
    public void testConstructor_withExistingLine() {
        final var original = new Line(startPoint, endPoint);
        assertNotNull(original);
        assertEquals(startPoint, original.getStartPoint());
        assertEquals(endPoint, original.getEndPoint());
        assertEquals(1, original.getSlope(), 1e-15);

        final var clone = new Line(original);
        assertNotNull(clone);
        assertEquals(startPoint, clone.getStartPoint());
        assertEquals(endPoint, clone.getEndPoint());
        assertEquals(1, clone.getSlope(), 1e-15);

        assertNotSame(original, clone);
        assertNotSame(original.getStartPoint(), clone.getStartPoint());
        assertNotSame(original.getEndPoint(), clone.getEndPoint());
    }

    @Test
    public void testConstructor_withNullLine() {
        assertThrows(NullPointerException.class, () -> new Line((Line) null));
    }

    @Test
    public void testConstructor_withJson() {
        final var original = new Line(startPoint, endPoint);
        assertNotNull(original);
        assertEquals(0, original.getStartPoint().getX());
        assertEquals(0, original.getStartPoint().getY());
        assertEquals(10, original.getEndPoint().getX());
        assertEquals(10, original.getEndPoint().getY());

        final var newLine = new Line(original.toJson());
        assertNotNull(newLine);
        assertEquals(0, newLine.getStartPoint().getX());
        assertEquals(0, newLine.getStartPoint().getY());
        assertEquals(10, newLine.getEndPoint().getX());
        assertEquals(10, newLine.getEndPoint().getY());

        assertNotSame(original, newLine);
    }

    @Test
    public void testConstructor_withNullJson() {
        assertThrows(NullPointerException.class, () -> new Line((JSONObject) null));
    }

    @Test
    public void testSerializationAndDeserialization() {
        final var originalLine = new Line(startPoint, endPoint);

        try (
            final var fos = new FileOutputStream("temp.ser");
            final var oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(originalLine);
            oos.flush();
        } catch(final Exception e) {
            e.printStackTrace();
            fail();
        }

        try (
            final var fis = new FileInputStream("temp.ser");
            final var ois = new ObjectInputStream(fis);
        ) {
            final var loadedLine = (Line) ois.readObject();
            assertEquals(originalLine.getStartPoint(), loadedLine.getStartPoint());
            assertEquals(originalLine.getEndPoint(), loadedLine.getEndPoint());
            assertEquals(1, loadedLine.getSlope(), 1e-15);
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testToJson() {
        final var line = new Line(startPoint, endPoint);
        final var json = line.toJson();

        assertNotNull(json);
        assertTrue(json.has("startPoint"));
        assertTrue(json.has("endPoint"));

        final var startPoint = json.getJSONObject("startPoint");
        assertNotNull(startPoint);
        assertEquals(0, startPoint.getInt("x"));
        assertEquals(0, startPoint.getInt("y"));

        final var endPoint = json.getJSONObject("endPoint");
        assertNotNull(endPoint);
        assertEquals(10, endPoint.getInt("x"));
        assertEquals(10, endPoint.getInt("y"));
    }

    @Test
    public void testToString() {
        final var line = new Line(startPoint, endPoint);
        assertNotNull(line.toString());
        assertFalse(line.toString().isEmpty());
    }

    @Test
    public void testSetStartPoint() {
        final var point = new Point(55, 55);
        final var line = new Line(startPoint, endPoint);
        line.setStartPoint(point);

        assertNotSame(startPoint, line.getStartPoint());
        assertNotSame(point, line.getStartPoint());
    }

    @Test
    public void testSetStartPoint_ensureSlopeIsUpdated() {
        final var point = new Point(7, 3);

        final var line = new Line(startPoint, endPoint);
        assertEquals(1, line.getSlope(), 1e-15);

        line.setStartPoint(point);
        assertEquals(2.3333333333333, line.getSlope(), 1e-13);
    }

    @Test
    public void testSetEndPoint() {
        final var point = new Point(55, 55);
        final var line = new Line(startPoint, endPoint);
        line.setEndPoint(point);

        assertNotSame(endPoint, line.getEndPoint());
        assertNotSame(point, line.getEndPoint());
    }

    @Test
    public void testSetEndPoint_ensureSlopeIsUpdated() {
        final var point = new Point(7, 3);

        final var line = new Line(startPoint, endPoint);
        assertEquals(1, line.getSlope(), 1e-15);

        line.setEndPoint(point);
        assertEquals(0.42857142857143, line.getSlope(), 1e-13);
    }
}
