package mitri.list;

public abstract class BasicList<E> {
    public abstract int size();
    public abstract E get(int index);
    public abstract void add(E element);
}
