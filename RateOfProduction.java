import java.util.HashMap;
import java.util.Map;

public enum RateOfProduction {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);
    
    private static Map<Integer, RateOfProduction> map = new HashMap<Integer, RateOfProduction>();
    private int value;

    static {
        for (RateOfProduction r : RateOfProduction.values()) {
            map.put(r.value, r);
        }
    }

    public static RateOfProduction valueOf(int pageType) {
        return map.get(pageType);
    }


    private RateOfProduction(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}