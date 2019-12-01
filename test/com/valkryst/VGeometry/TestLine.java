package com.valkryst.VGeometry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.valkryst.VGeometry.Line;
import com.valkryst.VGeometry.Point;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestLine {
    private final Point startPoint = new Point();
    private final Point endPoint = new Point();

    @Before
    public void before() {
        startPoint.setX(0);
        startPoint.setY(0);

        endPoint.setX(10);
        endPoint.setY(10);
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
        final Line line = new Line(startPoint, endPoint);
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
        final Line original = new Line(startPoint, endPoint);
        Assert.assertNotNull(original);
        Assert.assertEquals(startPoint, original.getStartPoint());
        Assert.assertEquals(endPoint, original.getEndPoint());
        Assert.assertEquals(1, original.getSlope(), 1e-15);

        final Line clone = new Line(original);
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
        new Line(null);
    }

    @Test
    public void testSerializationAndDeserialization() {
        final Line originalLine = new Line(startPoint, endPoint);

        try (
            final FileOutputStream fos = new FileOutputStream("temp.ser");
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(originalLine);
            oos.flush();
        } catch(final Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        try (
            final FileInputStream fis = new FileInputStream("temp.ser");
            final ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            final Line loadedLine = (Line) ois.readObject();
            Assert.assertEquals(originalLine.getStartPoint(), loadedLine.getStartPoint());
            Assert.assertEquals(originalLine.getEndPoint(), loadedLine.getEndPoint());
            Assert.assertEquals(1, loadedLine.getSlope(), 1e-15);
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testToString() {
        final Line line = new Line(startPoint, endPoint);
        Assert.assertNotNull(line.toString());
        Assert.assertTrue(line.toString().length() > 0);
    }

    @Test
    public void testSetStartPoint() {
        final Point point = new Point(55, 55);
        final Line line = new Line(startPoint, endPoint);
        line.setStartPoint(point);

        Assert.assertNotSame(startPoint, line.getStartPoint());
        Assert.assertNotSame(point, line.getStartPoint());
    }

    @Test
    public void testSetStartPoint_ensureSlopeIsUpdated() {
        final Point point = new Point(7, 3);

        final Line line = new Line(startPoint, endPoint);
        Assert.assertEquals(1, line.getSlope(), 1e-15);

        line.setStartPoint(point);
        Assert.assertEquals(2.3333333333333, line.getSlope(), 1e-13);
    }

    @Test
    public void testSetEndPoint() {
        final Point point = new Point(55, 55);
        final Line line = new Line(startPoint, endPoint);
        line.setEndPoint(point);

        Assert.assertNotSame(endPoint, line.getEndPoint());
        Assert.assertNotSame(point, line.getEndPoint());
    }

    @Test
    public void testSetEndPoint_ensureSlopeIsUpdated() {
        final Point point = new Point(7, 3);

        final Line line = new Line(startPoint, endPoint);
        Assert.assertEquals(1, line.getSlope(), 1e-15);

        line.setEndPoint(point);
        Assert.assertEquals(0.42857142857143, line.getSlope(), 1e-13);
    }
}
