package test;

import junit.framework.TestCase;
import org.junit.Test;
import org.ulco.*;

public class ColorTest extends TestCase {

    @Test
    public void testColor() throws Exception {
        Circle s = new Circle(new Point(0, 0), 10, new Color("BLUE"), new Color("BLACK"));
        assertEquals(JSON.parsable2json(s), "{ type: circle, center: { type: point, x: 0.0, y: 0.0 }, inner: { type: color, name: BLUE }, outer: { type: color, name: BLACK }, radius: 10.0 }");
    }

    @Test
    public void testColor2() throws Exception {
        Circle s = new Circle(new Point(0, 0), 10, new Color("BLUE"), new Color("BLACK"));
        assertEquals(JSON.parsable2json(s), JSON.parsable2json(new Circle(JSON.parsable2json(s))));
    }

    @Test
    public void testColor3() throws Exception {
        Group g = new Group();
        Square s = new Square(new Point(0,0), 5);
        Circle c = new Circle(new Point(5,5), 4, new Color("BLUE"), new Color("BLACK"));
        Group g2 = new Group();
        Rectangle r = new Rectangle(new Point(-6,10), 5.2, 9);

        g.add(s);
        g.add(c);
        g2.add(g);
        g2.add(r);
        assertEquals(JSON.parsable2json(g2), "{ type: group, objects : { { type: rectangle, center: { type: point, x: -6.0, y: 10.0 }, " +
                "height: 5.2, width: 9.0 } }, groups : { { type: group, objects : { { type: square, center: { type: point, x: 0.0, " +
                "y: 0.0 }, length: 5.0 }, { type: circle, center: { type: point, x: 5.0, y: 5.0 }, " +
                "inner: { type: color, name: BLUE }, outer: { type: color, name: BLACK }, radius: 4.0 } }, " +
                "groups : {  } } } }");
    }
}