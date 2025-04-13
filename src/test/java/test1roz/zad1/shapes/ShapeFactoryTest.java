package test1roz.zad1.shapes;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import test1roz.zad1.models.Shape;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

public class ShapeFactoryTest {

    private ShapeFactory factory;

    @Before
    public void setUp() {
        factory = ShapeFactory.getInstance();
    }

    @Test
    public void shouldUseCacheWhenCreatingSameSquare() throws NoSuchFieldException, IllegalAccessException {
        Square s1 = factory.createSquare(10);
        Square s2 = factory.createSquare(10);

        Assertions.assertThat(s1).isSameAs(s2);

        Field field = ShapeFactory.class.getDeclaredField("cache");
        field.setAccessible(true);
        ConcurrentHashMap<String, Shape> cache = (ConcurrentHashMap<String, Shape>) field.get(factory);

        Assertions.assertThat(cache).containsKey("square:10.0");
    }

    @Test
    public void shouldUseCacheWhenCreatingSameCircle() throws NoSuchFieldException, IllegalAccessException {
        Circle c1 = factory.createCircle(5);
        Circle c2 = factory.createCircle(5);

        Assertions.assertThat(c1).isSameAs(c2);

        Field field = ShapeFactory.class.getDeclaredField("cache");
        field.setAccessible(true);
        ConcurrentHashMap<String, Shape> cache = (ConcurrentHashMap<String, Shape>) field.get(factory);

        Assertions.assertThat(cache).containsKey("circle:5.0");
    }

    @Test
    public void shouldUseCacheWhenCreatingSameRectangle() throws NoSuchFieldException, IllegalAccessException {
        Rectangle r1 = factory.createRectangle(3, 7);
        Rectangle r2 = factory.createRectangle(3, 7);

        Assertions.assertThat(r1).isSameAs(r2);

        Field field = ShapeFactory.class.getDeclaredField("cache");
        field.setAccessible(true);
        ConcurrentHashMap<String, Shape> cache = (ConcurrentHashMap<String, Shape>) field.get(factory);

        Assertions.assertThat(cache).containsKey("rectangle:3.0:7.0");
    }
}
