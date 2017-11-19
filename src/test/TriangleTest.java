package test;

import junit.framework.TestCase;
import org.junit.Test;
import org.ulco.*;

public class TriangleTest extends TestCase {

    @Test
    public void testType() throws Exception {
        Triangle s = new Triangle(new Point(0, 0), 12, 10);

        assertTrue(s instanceof Triangle);
        assertTrue(s instanceof GraphicsObject);
    }

    @Test
    public void testJson() throws Exception {
        Triangle s = new Triangle(new Point(0, 0), 12, 10, new Color("BLUE"), new Color("BLACK"));

        assertEquals(JSON.parsable2json(s), "{ type: triangle, center: { type: point, x: 0.0, y: 0.0 }, inner: { type: color, name: BLUE }, outer: { type: color, name: BLACK }, length: 12.0, height: 10.0 }");
    }

    @Test
    public void testJson1() throws Exception {
        Triangle s = new Triangle(new Point(0, 0), 12, 10);

        assertEquals(JSON.parsable2json(s), "{ type: triangle, center: { type: point, x: 0.0, y: 0.0 }, length: 12.0, height: 10.0 }");
    }

    @Test
    public void testCopy() throws Exception {
        Triangle s = new Triangle(new Point(0, 0), 12, 10);

        assertEquals(JSON.parsable2json(s), JSON.parsable2json(s.copy()));
    }

    @Test
    public void testCopy2() throws Exception {
        Triangle s = new Triangle(new Point(0, 0), 12, 2, new Color("BLUE"), new Color("BLACK"));
        assertEquals(JSON.parsable2json(s), JSON.parsable2json(new Triangle(JSON.parsable2json(s))));
    }
}