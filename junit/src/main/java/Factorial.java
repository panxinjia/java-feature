/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO
 */
public class Factorial {

    public static int factorial(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n 不能小于 0");
        if (n == 1)
            return 1;
        return factorial(n - 1) * n;
    }
}
