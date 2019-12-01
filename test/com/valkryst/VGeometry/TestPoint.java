package com.valkryst.VGeometry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import com.valkryst.VGeometry.Point;

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
        final Point p = new Point();
        Assert.assertNotNull(p);
        Assert.assertEquals(0, p.getX());
        Assert.assertEquals(0, p.getY());
    }

    @Test
    public void testConstructor_withInts() {
        final Point p = new Point(123, 456);
        Assert.assertNotNull(p);
        Assert.assertEquals(123, p.getX());
        Assert.assertEquals(456, p.getY());
    }

    @Test
    public void testConstructor_withExistingPoint() {
        final Point original = new Point(123, 456);
        Assert.assertNotNull(original);
        Assert.assertEquals(123, original.getX());
        Assert.assertEquals(456, original.getY());

        final Point clone = new Point(original);
        Assert.assertNotNull(clone);
        Assert.assertEquals(123, clone.getX());
        Assert.assertEquals(456, clone.getY());

        Assert.assertNotSame(original, clone);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullPoint() {
        new Point(null);
    }

    @Test
    public void testSerializationAndDeserialization() {
        final Point originalPoint = new Point(123, 456);

        try (
            final FileOutputStream fos = new FileOutputStream("temp.ser");
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(originalPoint);
            oos.flush();
        } catch(final Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        try (
            final FileInputStream fis = new FileInputStream("temp.ser");
            final ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            final Point loadedPoint = (Point) ois.readObject();
            Assert.assertEquals(originalPoint.getX(), loadedPoint.getX());
            Assert.assertEquals(originalPoint.getY(), loadedPoint.getY());
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testToString() {
        final Point p = new Point(123, 456);
        Assert.assertNotNull(p.toString());
        Assert.assertTrue(p.toString().length() > 0);
    }

    @Test
    public void testGetX() {
        final Point p = new Point(123, 456);
        Assert.assertEquals(123, p.getX());
    }

    @Test
    public void testGetY() {
        final Point p = new Point(123, 456);
        Assert.assertEquals(456, p.getY());
    }

    @Test
    public void testSetX() {
        final Point p = new Point(123, 456);
        Assert.assertEquals(123, p.getX());

        p.setX(789);
        Assert.assertEquals(789, p.getX());
    }

    @Test
    public void testSetY() {
        final Point p = new Point(123, 456);
        Assert.assertEquals(456, p.getY());

        p.setY(789);
        Assert.assertEquals(789, p.getY());
    }
}
