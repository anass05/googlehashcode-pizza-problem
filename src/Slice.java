import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anass on 2018-01-30.
 */
public class Slice {
    int[] start;
    int[] end;
    String tag1, tag2;
    HashMap<String, Integer> ingredients;

    public Slice(int[] start, int[] end) {
        this.start = start;
        this.end = end;
        ingredients = new HashMap<>();
        tag1 = "";
        tag2 = "";
    }

    public Slice(int a, int b, int c, int d) {
        int[] start = {a, b};
        int[] end = {c, d};
        this.start = start;
        this.end = end;
        ingredients = new HashMap<>();
        tag1 = "";
        tag2 = "";
    }

    @Override
    public String toString() {
        return start[0] + "." + start[1] + "." + end[0] + "." + end[1]+"|"+tag1+"|"+tag2;
    }

    public String out(){
        return start[0] + " " + start[1] + " " + end[0] + " " + end[1];
    }

    public ArrayList<String> getPieces() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = start[1]; i <= end[1]; i++) {
            for (int j = start[0]; j <= end[0]; j++) {
                list.add(j + "." + i);
            }
        }
        return list;
    }

    public int getIntrest() {
        int intrest = 0;
        for (int i = start[1]; i <= end[1]; i++) {
            for (int j = start[0]; j <= end[0]; j++) {
                intrest++;
            }
        }
        return intrest;
    }

    float getPriority(float maxPieces) {
        float min = ingredients.get("T");
        if (ingredients.get("M") < min)
            min = ingredients.get("M");
        //System.out.println(this.getIntrest()+"|"+min+"|"+this.getIntrest()+"|"+maxPieces);
        return (this.getIntrest() / min + 17 * this.getIntrest() / maxPieces);
    }


    boolean intersect2(Slice s2) {
        //top
        if (this.end[0] < s2.start[0])
            return false;
        //right
        if (this.start[1] > s2.end[1])
            return false;
        //bottom
        if (this.start[0] > s2.end[0])
            return false;
        //left
        if (this.end[1] < s2.start[1])
            return false;

        return true;
    }
}
