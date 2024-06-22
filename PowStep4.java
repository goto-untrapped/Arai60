import java.util.stream.Stream;

/*
 * 下記を参考し、末尾再帰最適化で解いてみる
 * https://qiita.com/tosh_m/items/2be312ba08c8b7ecb3b6
 */
class Solution {    
    TailCallUtil tailCallUtil = new TailCallUtil();
    
    public double myPow(double x, int n) {
        if (n < 0) {
            return myPowHelperTailCall(1 / x, -(long)n, 1).call();
        } else {
            return myPowHelperTailCall(x, (long)n, 1).call();
        }
    }
    
    private TailCall<Double> myPowHelperTailCall(double x, long n, double result) {
        if (n == 0) return tailCallUtil.complete(result);

        if (n % 2 == 1) {
            return tailCallUtil.nextCall(() -> myPowHelperTailCall(x * x, n / 2, x * result));
        } else {
            return tailCallUtil.nextCall(() -> myPowHelperTailCall(x * x, n / 2, result));
        }
    }
}

class TailCallUtil {
    public <T> TailCall<T> nextCall(TailCall<T> function) {
        return function;
    }
    
    public <T> TailCall<T> complete(T value) {
        return new TailCall<T>() {
            @Override
            public TailCall<T> apply() {
                throw new RuntimeException("Not implemented.");
            }
            
            @Override
            public boolean isComplete() {
                return true;
            }
            
            @Override
            public T getResult() {
                return value;
            }
        };
    }
}

@FunctionalInterface
interface TailCall<T> {
    TailCall<T> apply();

    default boolean isComplete() {
        return false;
    }

    default T getResult() {
        throw new RuntimeException("Not implemented.");
    }

    default T call() {
        return Stream.iterate(this, TailCall::apply)
                .filter(TailCall::isComplete)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unreachable"))
                .getResult();
    }
}
