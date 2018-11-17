import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2018-11-17.
 */
public class UnmodifiableTest {
    
    @Test(expected = UnsupportedOperationException.class)
    public void isUnmodifiable() {
        Collections.unmodifiableCollection(new LinkedList<Integer>()).add(1);
    }
    
    @Test
    public void isMutable() {
//        given
        var list = new LinkedList<Integer>();

//        when
        Collection<Integer> integers = Collections.unmodifiableCollection(list);
//        then
        assertTrue(integers.isEmpty());

//        when
        list.add(1);
//        then
        assertFalse(integers.isEmpty());
    }

    @Test
    public void isMutable_fix() {
//        given
        var list = new LinkedList<Integer>();
//        when
        Collection<Integer> integers = Collections.unmodifiableCollection(new ArrayList<>(list));
//        then        
        assertTrue(integers.isEmpty());
        
//        when
        list.add(1);
//        then        
        assertTrue(integers.isEmpty());
    }
}
