import org.junit.Before;
import org.junit.Test;

public class ALATest {

    private TestListener tl;

    @Before
    public void before(){
        tl = new TestListener();
    }

    @Test
    public void registeringMethodsTest(){
        assert tl.getAnnotatedMethods().size() == 9;
    }
}
