package com.valkryst.VGeometry;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestLine {
    private Point startPoint;
    private Point endPoint;

    @Before
    public void before() {
        startPoint = new Point(0, 0);
        endPoint = new Point(10, 10);
    }

    @After
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
        Assert.assertNotNull(line);
        Assert.assertEquals(startPoint, line.getStartPoint());
        Assert.assertEquals(endPoint, line.getEndPoint());
        Assert.assertEquals(1, line.getSlope(), 1e-15);

        Assert.assertNotSame(startPoint, line.getStartPoint());
        Assert.assertNotSame(endPoint, line.getEndPoint());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullStartPoint() {
        new Line(null, endPoint);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullEndPoint() {
        new Line(startPoint, null);
    }

    @Test
    public void testConstructor_withExistingLine() {
        final var original = new Line(startPoint, endPoint);
        Assert.assertNotNull(original);
        Assert.assertEquals(startPoint, original.getStartPoint());
        Assert.assertEquals(endPoint, original.getEndPoint());
        Assert.assertEquals(1, original.getSlope(), 1e-15);

        final var clone = new Line(original);
        Assert.assertNotNull(clone);
        Assert.assertEquals(startPoint, clone.getStartPoint());
        Assert.assertEquals(endPoint, clone.getEndPoint());
        Assert.assertEquals(1, clone.getSlope(), 1e-15);

        Assert.assertNotSame(original, clone);
        Assert.assertNotSame(original.getStartPoint(), clone.getStartPoint());
        Assert.assertNotSame(original.getEndPoint(), clone.getEndPoint());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullLine() {
        new Line((Line) null);
    }

    @Test
    public void testConstructor_withJson() {
        final var original = new Line(startPoint, endPoint);
        Assert.assertNotNull(original);
        Assert.assertEquals(0, original.getStartPoint().getX());
        Assert.assertEquals(0, original.getStartPoint().getY());
        Assert.assertEquals(10, original.getEndPoint().getX());
        Assert.assertEquals(10, original.getEndPoint().getY());

        final var newLine = new Line(original.toJson());
        Assert.assertNotNull(newLine);
        Assert.assertEquals(0, newLine.getStartPoint().getX());
        Assert.assertEquals(0, newLine.getStartPoint().getY());
        Assert.assertEquals(10, newLine.getEndPoint().getX());
        Assert.assertEquals(10, newLine.getEndPoint().getY());

        Assert.assertNotSame(original, newLine);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullJson() {
        new Line((JSONObject) null);
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
            Assert.fail();
        }

        try (
            final var fis = new FileInputStream("temp.ser");
            final var ois = new ObjectInputStream(fis);
        ) {
            final var loadedLine = (Line) ois.readObject();
            Assert.assertEquals(originalLine.getStartPoint(), loadedLine.getStartPoint());
            Assert.assertEquals(originalLine.getEndPoint(), loadedLine.getEndPoint());
            Assert.assertEquals(1, loadedLine.getSlope(), 1e-15);
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testToJson() {
        final var line = new Line(startPoint, endPoint);
        final var json = line.toJson();

        Assert.assertNotNull(json);
        Assert.assertTrue(json.has("startPoint"));
        Assert.assertTrue(json.has("endPoint"));

        final var startPoint = json.getJSONObject("startPoint");
        Assert.assertNotNull(startPoint);
        Assert.assertEquals(0, startPoint.getInt("x"));
        Assert.assertEquals(0, startPoint.getInt("y"));

        final var endPoint = json.getJSONObject("endPoint");
        Assert.assertNotNull(endPoint);
        Assert.assertEquals(10, endPoint.getInt("x"));
        Assert.assertEquals(10, endPoint.getInt("y"));
    }

    @Test
    public void testToString() {
        final var line = new Line(startPoint, endPoint);
        Assert.assertNotNull(line.toString());
        Assert.assertTrue(line.toString().length() > 0);
    }

    @Test
    public void testSetStartPoint() {
        final var point = new Point(55, 55);
        final var line = new Line(startPoint, endPoint);
        line.setStartPoint(point);

        Assert.assertNotSame(startPoint, line.getStartPoint());
        Assert.assertNotSame(point, line.getStartPoint());
    }

    @Test
    public void testSetStartPoint_ensureSlopeIsUpdated() {
        final var point = new Point(7, 3);

        final var line = new Line(startPoint, endPoint);
        Assert.assertEquals(1, line.getSlope(), 1e-15);

        line.setStartPoint(point);
        Assert.assertEquals(2.3333333333333, line.getSlope(), 1e-13);
    }

    @Test
    public void testSetEndPoint() {
        final var point = new Point(55, 55);
        final var line = new Line(startPoint, endPoint);
        line.setEndPoint(point);

        Assert.assertNotSame(endPoint, line.getEndPoint());
        Assert.assertNotSame(point, line.getEndPoint());
    }

    @Test
    public void testSetEndPoint_ensureSlopeIsUpdated() {
        final var point = new Point(7, 3);

        final var line = new Line(startPoint, endPoint);
        Assert.assertEquals(1, line.getSlope(), 1e-15);

        line.setEndPoint(point);
        Assert.assertEquals(0.42857142857143, line.getSlope(), 1e-13);
    }
}
