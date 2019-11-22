[![Build Status](https://travis-ci.com/mtumilowicz/java11-unmodifiable-is-mutable.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-unmodifiable-is-mutable)

# java11-unmodifiable-is-mutable
Proof that unmodifiable collections are mutable.

# preface
In java we have `Collections.unmodifiableXXX` methods:
* `Collections.unmodifiableCollection`
* `Collections.unmodifiableList`
* `Collections.unmodifiableMap`
* `Collections.unmodifiableNavigableMap`
* `Collections.unmodifiableNavigableSet`
* `Collections.unmodifiableSet`
* `Collections.unmodifiableSortedMap`
* `Collections.unmodifiableSortedSet`

Java doc for `Collections.unmodifiableCollection`:
>Returns an unmodifiable view of the
specified collection. Query operations on the returned collection "read through"
to the specified collection, and attempts to modify the returned
collection, whether direct or via its iterator, result in an
UnsupportedOperationException

For example:
```
static class UnmodifiableList<E> extends UnmodifiableCollection<E>
                                  implements List<E> {
    
    // lots of code

    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }
    
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }
    
    // lots of code
}
```

# project description
We have tested (in `UnmodifiableTest`) that:
1. collection obtained from that method is unmodifiable 
(try to modify it ends in an exception - `UnsupportedOperationException`)
    ```
    @Test(expected = UnsupportedOperationException.class)
    public void isUnmodifiable() {
        Collections.unmodifiableCollection(new LinkedList<Integer>()).add(1);
    }    
    ```
1. collection obtained from that method is NOT immutable,
we could change its state by modifying source
    ```
    @Test
    public void isMutable() {
        var list = new LinkedList<Integer>();

        Collection<Integer> integers = Collections.unmodifiableCollection(list);
        assertTrue(integers.isEmpty());

        list.add(1);
        assertFalse(integers.isEmpty());
    }
    ```
1. but we could easily fix it by passing copy of source collection
to the method
    ```
    @Test
    public void isMutable_fix() {
        var list = new LinkedList<Integer>();
        Collection<Integer> integers = Collections.unmodifiableCollection(new ArrayList<>(list));     
        assertTrue(integers.isEmpty());
        
        list.add(1);    
        assertTrue(integers.isEmpty());
    }    
    ```