package test;

import junit.framework.TestCase;
import org.junit.Test;
import org.ulco.*;

public class LayerTest extends TestCase {
    @Test
    public void testType() throws Exception {
        Document document = new Document();
        int oldID = ID.getInstance().current();
        Layer layer = document.createLayer();

        layer.add(new Square(new Point(2, 8), 10));

        assertEquals(layer.getID(), oldID + 1);
        assertEquals(layer.get(0).getID(), oldID + 2);
    }

    @Test
    public void testJSON() throws Exception {
        Layer l = new Layer();
        Square s = new Square(new Point(0, 0), 5);
        Circle c = new Circle(new Point(5, 5), 4);
        l.add(s);
        l.add(c);
        assertEquals(JSON.parsable2json(l), "{ type: layer, objects : { { type: square, center: { type: point, x: 0.0, y: 0.0 }, length: 5.0 }, " +
                "{ type: circle, center: { type: point, x: 5.0, y: 5.0 }, radius: 4.0 } }, groups : {  } }");
    }

    @Test
    public void testJSON2() throws Exception {
        Square s = new Square(new Point(0, 0), 5);
        Circle c = new Circle(new Point(5, 5), 4);
        Group g = new Group();
        g.add(s);
        g.add(c);
        Layer l = new Layer();
        l.add(g);
        Layer l2 = new Layer(JSON.parsable2json(l));
        assertEquals(JSON.parsable2json(l2), JSON.parsable2json(l));
    }


}