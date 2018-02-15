import java.util.ArrayList;

/**
 * Created by Anass on 2018-01-30.
 */
public class Maths {
    public ArrayList<Integer[]> divider(int n){
        ArrayList<Integer[]> dividers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if( n % i == 0) {
                Integer[] tab = new Integer[2];
                tab[0] = i;
                tab[1] = n/i;
                dividers.add(tab);
            }
        }
        return dividers;
    }
}
