package com.valkryst.VGeometry;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestPoint {
    @After
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
        Assert.assertNotNull(p);
        Assert.assertEquals(0, p.getX());
        Assert.assertEquals(0, p.getY());
    }

    @Test
    public void testConstructor_withInts() {
        final var p = new Point(123, 456);
        Assert.assertNotNull(p);
        Assert.assertEquals(123, p.getX());
        Assert.assertEquals(456, p.getY());
    }

    @Test
    public void testConstructor_withExistingPoint() {
        final var original = new Point(123, 456);
        Assert.assertNotNull(original);
        Assert.assertEquals(123, original.getX());
        Assert.assertEquals(456, original.getY());

        final var clone = new Point(original);
        Assert.assertNotNull(clone);
        Assert.assertEquals(123, clone.getX());
        Assert.assertEquals(456, clone.getY());

        Assert.assertNotSame(original, clone);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullPoint() {
        new Point((Point) null);
    }

    @Test
    public void testConstructor_withJson() {
        final var original = new Point(123, 456);
        Assert.assertNotNull(original);
        Assert.assertEquals(123, original.getX());
        Assert.assertEquals(456, original.getY());

        final var newPoint = new Point(original.toJson());
        Assert.assertNotNull(newPoint);
        Assert.assertEquals(123, newPoint.getX());
        Assert.assertEquals(456, newPoint.getY());

        Assert.assertNotSame(original, newPoint);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullJson() {
        new Point((JSONObject) null);
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
            Assert.fail();
        }

        try (
            final var fis = new FileInputStream("temp.ser");
            final var ois = new ObjectInputStream(fis);
        ) {
            final var loadedPoint = (Point) ois.readObject();
            Assert.assertEquals(originalPoint.getX(), loadedPoint.getX());
            Assert.assertEquals(originalPoint.getY(), loadedPoint.getY());
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testToJson() {
        final var p = new Point(123, 456);
        final var json = p.toJson();

        Assert.assertNotNull(json);
        Assert.assertTrue(json.has("x"));
        Assert.assertTrue(json.has("y"));
        Assert.assertEquals(123, json.getInt("x"));
        Assert.assertEquals(456, json.getInt("y"));
    }

    @Test
    public void testToString() {
        final var p = new Point(123, 456);
        Assert.assertNotNull(p.toString());
        Assert.assertTrue(p.toString().length() > 0);
    }

    @Test
    public void testGetX() {
        final var p = new Point(123, 456);
        Assert.assertEquals(123, p.getX());
    }

    @Test
    public void testGetY() {
        final var p = new Point(123, 456);
        Assert.assertEquals(456, p.getY());
    }

    @Test
    public void testSetX() {
        final var p = new Point(123, 456);
        Assert.assertEquals(123, p.getX());

        p.setX(789);
        Assert.assertEquals(789, p.getX());
    }

    @Test
    public void testSetY() {
        final var p = new Point(123, 456);
        Assert.assertEquals(456, p.getY());

        p.setY(789);
        Assert.assertEquals(789, p.getY());
    }
}
