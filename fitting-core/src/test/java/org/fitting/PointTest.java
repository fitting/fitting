/*
 * Licensed to the Fitting Project under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Fitting Project licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fitting;

import org.fitting.Point;
import org.junit.Assert;
import org.junit.Test;

public class PointTest {

    @Test
    public void testEmptyConstructor() {
        Point point = new Point();
        Assert.assertEquals(0.0, point.getX(), 0);
        Assert.assertEquals(0.0, point.getY(), 0);
    }

    @Test
    public void testConstructorWithInt() {
        Point point = new Point(1, 2);
        Assert.assertEquals(1.0, point.getX(), 0);
        Assert.assertEquals(2.0, point.getY(), 0);
    }

    @Test
    public void testCopyConstructor() {
        Point toCopy = new Point(1, 2);
        Point point = new Point(toCopy);
        Assert.assertEquals(1.0, point.getX(), 0);
        Assert.assertEquals(2.0, point.getY(), 0);
    }

    @Test
    public void testGetLocation() {
        Assert.assertEquals(new Point(1, 2), new Point(1, 2).getLocation());
    }

    @Test
    public void testSetLocation() {
        Point point = new Point(1, 2);
        point.setLocation(3, 4);
        Assert.assertEquals(3.0, point.getX(), 0);
        Assert.assertEquals(4.0, point.getY(), 0);
        point.setLocation(new Point(5, 6));
        Assert.assertEquals(5.0, point.getX(), 0);
        Assert.assertEquals(6.0, point.getY(), 0);
        point.setLocation(8.4, 8.6);
        Assert.assertEquals(8.0, point.getX(), 0);
        Assert.assertEquals(9.0, point.getY(), 0);
    }

    @Test
    public void testMove() {
        Point point = new Point(1, 2);
        point.move(3, 4);
        Assert.assertEquals(3.0, point.getX(), 0);
        Assert.assertEquals(4.0, point.getY(), 0);
    }

    @Test
    public void testTranslate() {
        Point point = new Point(1, 2);
        point.translate(10, 10);
        Assert.assertEquals(11.0, point.getX(), 0);
        Assert.assertEquals(12.0, point.getY(), 0);
    }

    @Test
    public void testEquals() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(3, 4);
        Point point4 = new Point(1, 4);
        Assert.assertEquals(point1, point2);
        Assert.assertNotEquals(point1, point3);
        Assert.assertNotEquals(point2, point3);
        Assert.assertNotEquals(point1, point4);
        Assert.assertNotEquals(point3, point4);
        Assert.assertNotEquals(point1, new Object());
        Assert.assertNotEquals(point1, null);
    }

    @Test
    public void testHashcode() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);
        Point point3 = new Point(3, 4);
        Assert.assertEquals(point1.hashCode(), point2.hashCode());
        Assert.assertNotEquals(point1.hashCode(), point3.hashCode());
        Assert.assertNotEquals(point2.hashCode(), point3.hashCode());
    }

    @Test
    public void testToString() {
        String toString = new Point(123, 456).toString();
        Assert.assertTrue(toString.contains("123"));
        Assert.assertTrue(toString.contains("456"));
    }

}
