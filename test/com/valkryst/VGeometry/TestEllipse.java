package com.valkryst.VGeometry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import com.valkryst.VGeometry.Circle;
import com.valkryst.VGeometry.Ellipse;
import com.valkryst.VGeometry.Point;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestEllipse {
    @After
    public void after() {
        try {
            Files.deleteIfExists(Paths.get("temp.ser"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor_withPointAndRadii() {
        final Point midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final Ellipse ellipse = new Ellipse(midpoint, horizontalRadius, verticalRadius);
        Assert.assertNotNull(ellipse);
        Assert.assertEquals(midpoint, ellipse.getMidpoint());
        Assert.assertEquals(horizontalRadius, ellipse.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(verticalRadius, ellipse.getVerticalRadius(), 1e-15);
        Assert.assertEquals(628.32, ellipse.getArea(), 0.002);
        Assert.assertEquals(96.88, ellipse.getCircumference(), 0.005);
        Assert.assertEquals(20, ellipse.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(40, ellipse.getVerticalDiameter(), 1e-15);
        Assert.assertNotSame(midpoint, ellipse.getMidpoint());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullPoint() {
        new Ellipse(null, 10, 20);
    }

    @Test
    public void testConstructor_withExistingEllipse() {
        final Point midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final Ellipse original = new Ellipse(midpoint, horizontalRadius, verticalRadius);
        Assert.assertNotNull(original);
        Assert.assertEquals(midpoint, original.getMidpoint());
        Assert.assertEquals(horizontalRadius, original.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(verticalRadius, original.getVerticalRadius(), 1e-15);
        Assert.assertEquals(628.32, original.getArea(), 0.002);
        Assert.assertEquals(96.88, original.getCircumference(), 0.005);
        Assert.assertEquals(20, original.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(40, original.getVerticalDiameter(), 1e-15);
        Assert.assertNotSame(midpoint, original.getMidpoint());

        final Ellipse clone = new Ellipse(original);
        Assert.assertNotNull(clone);
        Assert.assertEquals(midpoint, clone.getMidpoint());
        Assert.assertEquals(horizontalRadius, clone.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(verticalRadius, clone.getVerticalRadius(), 1e-15);
        Assert.assertEquals(628.32, clone.getArea(), 0.002);
        Assert.assertEquals(96.88, clone.getCircumference(), 0.005);
        Assert.assertEquals(20, clone.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(40, clone.getVerticalDiameter(), 1e-15);
        Assert.assertNotSame(midpoint, clone.getMidpoint());

        Assert.assertNotSame(original, clone);
        Assert.assertNotSame(original.getMidpoint(), clone.getMidpoint());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullEllipse() {
        new Ellipse(null);
    }

    @Test
    public void testSerializationAndDeserialization() {
        final Point midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;
        final Ellipse originalEllipse = new Ellipse(midpoint, horizontalRadius, verticalRadius);

        try (
            final FileOutputStream fos = new FileOutputStream("temp.ser");
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(originalEllipse);
            oos.flush();
        } catch(final Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        try (
            final FileInputStream fis = new FileInputStream("temp.ser");
            final ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            final Ellipse loadedEllipse = (Ellipse) ois.readObject();
            Assert.assertNotNull(loadedEllipse);
            Assert.assertEquals(midpoint, loadedEllipse.getMidpoint());
            Assert.assertEquals(horizontalRadius, loadedEllipse.getHorizontalRadius(), 1e-15);
            Assert.assertEquals(verticalRadius, loadedEllipse.getVerticalRadius(), 1e-15);
            Assert.assertEquals(628.32, loadedEllipse.getArea(), 0.002);
            Assert.assertEquals(96.88, loadedEllipse.getCircumference(), 0.005);
            Assert.assertEquals(20, loadedEllipse.getHorizontalDiameter(), 1e-15);
            Assert.assertEquals(40, loadedEllipse.getVerticalDiameter(), 1e-15);
            Assert.assertNotSame(midpoint, loadedEllipse.getMidpoint());
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testToString() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        Assert.assertNotNull(ellipse.toString());
        Assert.assertTrue(ellipse.toString().length() > 0);
    }

    @Test
    public void testSetMidpoint() {
        final Point midpoint = new Point(0, 0);

        final Ellipse ellipse = new Ellipse(midpoint, 10, 20);
        ellipse.setMidpoint(new Point(10, 10));

        Assert.assertNotSame(midpoint, ellipse.getMidpoint());
    }

    @Test(expected = NullPointerException.class)
    public void testSetMidpoint_withNullPoint() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setMidpoint(null);
    }

    @Test
    public void testSetHorizontalRadius() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        Assert.assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
    }

    @Test
    public void testSetHorizontalRadius_ensureAreaIsUpdated() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        Assert.assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(1884.96, ellipse.getArea(), 0.01);
    }

    @Test
    public void testSetHorizontalRadius_ensureCircumferenceIsUpdated() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        Assert.assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(158.65, ellipse.getCircumference(), 0.01);
    }

    @Test
    public void testSetHorizontalRadius_ensureDiametersAreUpdated() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        Assert.assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(60, ellipse.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(40, ellipse.getVerticalDiameter(), 1e-15);
    }

    @Test
    public void testSetVerticalRadius() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        Assert.assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
    }

    @Test
    public void testSetVerticalRadius_ensureAreaIsUpdated() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        Assert.assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        Assert.assertEquals(942.48, ellipse.getArea(), 0.01);
    }

    @Test
    public void testSetVerticalRadius_ensureCircumferenceIsUpdated() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        Assert.assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        Assert.assertEquals(133.65, ellipse.getCircumference(), 0.01);
    }

    @Test
    public void testSetVerticalRadius_ensureDiametersAreUpdated() {
        final Ellipse ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        Assert.assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        Assert.assertEquals(20, ellipse.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(60, ellipse.getVerticalDiameter(), 1e-15);
    }
}
