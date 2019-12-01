package com.valkryst.VGeometry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import com.valkryst.VGeometry.Circle;
import com.valkryst.VGeometry.Point;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestCircle {
    @After
    public void after() {
        try {
            Files.deleteIfExists(Paths.get("temp.ser"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor_withPointAndRadius() {
        final Point midpoint = new Point(0, 0);
        final double radius = 10;

        final Circle circle = new Circle(midpoint, radius);
        Assert.assertNotNull(circle);
        Assert.assertEquals(midpoint, circle.getMidpoint());
        Assert.assertEquals(radius, circle.getRadius(), 1e-15);
        Assert.assertEquals(314.16, circle.getArea(), 0.001);
        Assert.assertEquals(62.83, circle.getCircumference(), 0.002);
        Assert.assertEquals(20, circle.getDiameter(), 1e-15);
        Assert.assertNotSame(midpoint, circle.getMidpoint());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullPoint() {
        new Circle(null, 10);
    }

    @Test
    public void testConstructor_withPointAndNegativeRadius() {
        final Point midpoint = new Point(0, 0);
        final double radius = -10;

        final Circle circle = new Circle(midpoint, radius);
        Assert.assertNotNull(circle);
        Assert.assertEquals(midpoint, circle.getMidpoint());
        Assert.assertEquals(10, circle.getRadius(), 1e-15);
        Assert.assertEquals(314.16, circle.getArea(), 0.001);
        Assert.assertEquals(62.83, circle.getCircumference(), 0.002);
        Assert.assertEquals(20, circle.getDiameter(), 1e-15);
        Assert.assertNotSame(midpoint, circle.getMidpoint());
    }

    @Test
    public void testConstructor_withExistingCircle() {
        final Point midpoint = new Point(0, 0);
        final double radius = 10;

        final Circle original = new Circle(new Point(0, 0), 10);
        Assert.assertNotNull(original);
        Assert.assertEquals(midpoint, original.getMidpoint());
        Assert.assertEquals(radius, original.getRadius(), 1e-15);
        Assert.assertEquals(314.16, original.getArea(), 0.001);
        Assert.assertEquals(62.83, original.getCircumference(), 0.002);
        Assert.assertEquals(20, original.getDiameter(), 1e-15);

        final Circle clone = new Circle(original);
        Assert.assertNotNull(clone);
        Assert.assertEquals(midpoint, clone.getMidpoint());
        Assert.assertEquals(radius, clone.getRadius(), 1e-15);
        Assert.assertEquals(314.16, clone.getArea(), 0.001);
        Assert.assertEquals(62.83, clone.getCircumference(), 0.002);
        Assert.assertEquals(20, clone.getDiameter(), 1e-15);

        Assert.assertNotSame(original, clone);
        Assert.assertNotSame(original.getMidpoint(), clone.getMidpoint());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullCircle() {
        new Circle(null);
    }

    @Test
    public void testSerializationAndDeserialization() {
        final Circle originalCircle = new Circle(new Point(0, 0), 10);

        try (
            final FileOutputStream fos = new FileOutputStream("temp.ser");
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(originalCircle);
            oos.flush();
        } catch(final Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        try (
            final FileInputStream fis = new FileInputStream("temp.ser");
            final ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            final Circle loadedCircle = (Circle) ois.readObject();
            Assert.assertEquals(originalCircle.getMidpoint(), loadedCircle.getMidpoint());
            Assert.assertEquals(originalCircle.getRadius(), loadedCircle.getRadius(), 1e-15);
            Assert.assertEquals(314.16, loadedCircle.getArea(), 0.001);
            Assert.assertEquals(62.83, loadedCircle.getCircumference(), 0.002);
            Assert.assertEquals(20, loadedCircle.getDiameter(), 1e-15);
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testToString() {
        final Circle circle = new Circle(new Point(0, 0), 10);
        Assert.assertNotNull(circle.toString());
        Assert.assertTrue(circle.toString().length() > 0);
    }

    @Test
    public void testSetMidpoint() {
        final Point midpoint = new Point(0, 0);

        final Circle circle = new Circle(midpoint, 10);
        circle.setMidpoint(new Point(1, 1));

        Assert.assertNotSame(midpoint, circle.getMidpoint());
    }

    @Test(expected = NullPointerException.class)
    public void testSetMidpoint_withNullPoint() {
        final Circle circle = new Circle(new Point(0, 0), 10);
        circle.setMidpoint(null);
    }

    @Test
    public void testSetRadius() {
        final Point midpoint = new Point(0, 0);
        final double radius = 10;

        final Circle circle = new Circle(midpoint, radius);
        circle.setRadius(20);

        Assert.assertEquals(20, circle.getRadius(), 1e-15);
    }

    @Test
    public void testSetRadius_ensureAreaIsUpdated() {
        final Point midpoint = new Point(0, 0);
        final double radius = 10;

        final Circle circle = new Circle(midpoint, radius);
        circle.setRadius(20);

        Assert.assertEquals(1256.64, circle.getArea(), 0.01);
    }

    @Test
    public void testSetRadius_ensureCircumferenceIsUpdated() {
        final Point midpoint = new Point(0, 0);
        final double radius = 10;

        final Circle circle = new Circle(midpoint, radius);
        circle.setRadius(20);

        Assert.assertEquals(125.66, circle.getCircumference(), 0.004);
    }

    @Test
    public void testSetRadius_ensureDiameterIsUpdated() {
        final Point midpoint = new Point(0, 0);
        final double radius = 10;

        final Circle circle = new Circle(midpoint, radius);
        circle.setRadius(20);

        Assert.assertEquals(40, circle.getDiameter(), 1e-15);
    }
}
