import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Pair<T>{

    private T first;
    private T last;

    public Pair() {

    }
    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getFirst() {
        return this.first;
    }

    public void setLast(T last) {
        this.last = last;
    }

    public T getLast() {
         return this.last;
    }

    /**
     * 静态泛型方法, 泛型需要单独定义, class的泛型在创建实例时调用
     * @return  Pair
     */
    public static <E> Pair<E> create(E first, E last) {
         return new Pair<E>(first, last);
    }

}
