package com.valkryst.VGeometry;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

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
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final var ellipse = new Ellipse(midpoint, horizontalRadius, verticalRadius);
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
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final var original = new Ellipse(midpoint, horizontalRadius, verticalRadius);
        Assert.assertNotNull(original);
        Assert.assertEquals(midpoint, original.getMidpoint());
        Assert.assertEquals(horizontalRadius, original.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(verticalRadius, original.getVerticalRadius(), 1e-15);
        Assert.assertEquals(628.32, original.getArea(), 0.002);
        Assert.assertEquals(96.88, original.getCircumference(), 0.005);
        Assert.assertEquals(20, original.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(40, original.getVerticalDiameter(), 1e-15);
        Assert.assertNotSame(midpoint, original.getMidpoint());

        final var clone = new Ellipse(original);
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
        new Ellipse((Ellipse) null);
    }

    @Test
    public void testConstructor_withJson() {
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final var original = new Ellipse(midpoint, horizontalRadius, verticalRadius);
        Assert.assertNotNull(original);
        Assert.assertEquals(midpoint, original.getMidpoint());
        Assert.assertEquals(horizontalRadius, original.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(verticalRadius, original.getVerticalRadius(), 1e-15);
        Assert.assertEquals(628.32, original.getArea(), 0.002);
        Assert.assertEquals(96.88, original.getCircumference(), 0.005);
        Assert.assertEquals(20, original.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(40, original.getVerticalDiameter(), 1e-15);
        Assert.assertNotSame(midpoint, original.getMidpoint());

        final var newEllipse = new Ellipse(original.toJson());
        Assert.assertNotNull(newEllipse);
        Assert.assertEquals(midpoint, newEllipse.getMidpoint());
        Assert.assertEquals(horizontalRadius, newEllipse.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(verticalRadius, newEllipse.getVerticalRadius(), 1e-15);
        Assert.assertEquals(628.32, newEllipse.getArea(), 0.002);
        Assert.assertEquals(96.88, newEllipse.getCircumference(), 0.005);
        Assert.assertEquals(20, newEllipse.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(40, newEllipse.getVerticalDiameter(), 1e-15);
        Assert.assertNotSame(midpoint, newEllipse.getMidpoint());

        Assert.assertNotSame(original, newEllipse);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor_withNullJson() {
        new Ellipse((JSONObject) null);
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
            Assert.fail();
        }

        try (
            final var fis = new FileInputStream("temp.ser");
            final var ois = new ObjectInputStream(fis);
        ) {
            final var loadedEllipse = (Ellipse) ois.readObject();
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
    public void testToJson() {
        final var midpoint = new Point(0, 0);
        final double horizontalRadius = 10;
        final double verticalRadius = 20;

        final var ellipse = new Ellipse(midpoint, horizontalRadius, verticalRadius);

        final var json = ellipse.toJson();
        Assert.assertNotNull(json);
        Assert.assertEquals(horizontalRadius, json.getDouble("horizontalRadius"), 1e-15);
        Assert.assertEquals(verticalRadius, json.getDouble("verticalRadius"), 1e-15);
        Assert.assertFalse(json.has("area"));
        Assert.assertFalse(json.has("circumference"));
        Assert.assertFalse(json.has("horizontalDiameter"));
        Assert.assertFalse(json.has("verticalDiameter"));


        final var midpointJson = json.getJSONObject("midpoint");
        Assert.assertNotNull(midpointJson);
        Assert.assertEquals(midpoint.getX(), midpointJson.getInt("x"));
        Assert.assertEquals(midpoint.getY(), midpointJson.getInt("y"));
    }

    @Test
    public void testToString() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        Assert.assertNotNull(ellipse.toString());
        Assert.assertTrue(ellipse.toString().length() > 0);
    }

    @Test
    public void testSetMidpoint() {
        final var midpoint = new Point(0, 0);

        final var ellipse = new Ellipse(midpoint, 10, 20);
        ellipse.setMidpoint(new Point(10, 10));

        Assert.assertNotSame(midpoint, ellipse.getMidpoint());
    }

    @Test(expected = NullPointerException.class)
    public void testSetMidpoint_withNullPoint() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setMidpoint(null);
    }

    @Test
    public void testSetHorizontalRadius() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        Assert.assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
    }

    @Test
    public void testSetHorizontalRadius_ensureAreaIsUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        Assert.assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(1884.96, ellipse.getArea(), 0.01);
    }

    @Test
    public void testSetHorizontalRadius_ensureCircumferenceIsUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        Assert.assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(158.65, ellipse.getCircumference(), 0.01);
    }

    @Test
    public void testSetHorizontalRadius_ensureDiametersAreUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setHorizontalRadius(30);

        Assert.assertEquals(30, ellipse.getHorizontalRadius(), 1e-15);
        Assert.assertEquals(60, ellipse.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(40, ellipse.getVerticalDiameter(), 1e-15);
    }

    @Test
    public void testSetVerticalRadius() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        Assert.assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
    }

    @Test
    public void testSetVerticalRadius_ensureAreaIsUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        Assert.assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        Assert.assertEquals(942.48, ellipse.getArea(), 0.01);
    }

    @Test
    public void testSetVerticalRadius_ensureCircumferenceIsUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        Assert.assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        Assert.assertEquals(133.65, ellipse.getCircumference(), 0.01);
    }

    @Test
    public void testSetVerticalRadius_ensureDiametersAreUpdated() {
        final var ellipse = new Ellipse(new Point(0, 0), 10, 20);
        ellipse.setVerticalRadius(30);

        Assert.assertEquals(30, ellipse.getVerticalRadius(), 1e-15);
        Assert.assertEquals(20, ellipse.getHorizontalDiameter(), 1e-15);
        Assert.assertEquals(60, ellipse.getVerticalDiameter(), 1e-15);
    }
}
