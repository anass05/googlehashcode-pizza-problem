import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Anass on 2018-01-29.
 */

public class Opps {
    ArrayList<ArrayList<String>> lists;
    int columns;
    int rows;
    int minIngredient;
    int maxSlices;
    String fileName;

    Slice TL, TR, BL, BR;

    ArrayList<Slice> solutionTL;
    ArrayList<Slice> solutionTR;
    ArrayList<Slice> solutionBL;
    ArrayList<Slice> solutionBR;
    ArrayList<Slice> solutionIN;

    Opps() {
        lists = new ArrayList<>();
    }

    void readFile(String fileName) {
        String line;
        this.fileName = fileName;

        try {
            FileReader fileReader =
                    new FileReader("file_in/"+fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (i == 0) {
                    String[] numbers = line.split(" ");
                    rows = Integer.parseInt(numbers[0]);
                    columns = Integer.parseInt(numbers[1]);
                    minIngredient = Integer.parseInt(numbers[2]);
                    maxSlices = Integer.parseInt(numbers[3]);
                } else {
                    ArrayList<String> ligne = new ArrayList<>();
                    for (int y = 0; y < columns; y++) {
                        ligne.add("" + line.charAt(y));
                    }
                    lists.add(ligne);
                }
                i++;
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TL = new Slice(0, 0, rows / 2, columns / 2);
        TR = new Slice(0, columns / 2 + 1, rows / 2, columns);
        BL = new Slice(rows / 2 + 1, 0, rows, columns / 2);
        BR = new Slice(rows / 2 + 1, columns / 2 + 1, rows, columns);
    }

    ArrayList<Slice> getAllPossibleSlices(int cellsNumber) {
        ArrayList<Slice> Slices = new ArrayList<>();
        ArrayList<Integer[]> possibleCuts = new Maths().divider(cellsNumber);
        //eq 4 :this returns an array in form of 1*4,2*2,4*1
        int rowsOffset = 0;
        int colsOffset = 0;
        //add a loop to go through maximum possible slices
        for (rowsOffset = 0; rowsOffset < rows; rowsOffset++) {
            for (colsOffset = 0; colsOffset < columns; colsOffset++) {
                for (Integer[] combination : possibleCuts) {
                    if (combination[0] + rowsOffset <= rows && combination[1] + colsOffset <= columns) {
                        int[] offset = {rowsOffset, colsOffset};
                        Slices.add(makeCut(combination[0], combination[1], offset));
                    }
                }
            }
        }
        return Slices;
    }

    Slice makeCut(int rows, int cols, int[] offset) {
        int[] start = {offset[0], offset[1]};
        int[] end = {offset[0] + rows - 1, offset[1] + cols - 1};
        Slice s = new Slice(start, end);

        if (s.intersect2(TL) && s.intersect2(BR)) {
            s.tag1 = "TL";
            s.tag2 = "BR";
            return s;
        }


        if (s.intersect2(TL))
            s.tag1 = "TL";

        if (s.intersect2(TR))
            if (s.tag1.equals(""))
                s.tag1 = "TR";
            else
                s.tag2 = "TR";

        if (s.intersect2(BL))
            if (s.tag1.equals(""))
                s.tag1 = "BL";
            else
                s.tag2 = "BL";

        if (s.intersect2(BR))
            if (s.tag1.equals(""))
                s.tag1 = "BR";
            else
                s.tag2 = "BR";

        return s;
    }

    HashMap<String, Integer> getIngredientInSlice(Slice slice) {
        HashMap<String, Integer> map = new HashMap<>();
        int colNumber = slice.end[1] - slice.start[1];
        int rowNumber = slice.end[0] - slice.start[0];
        for (int i = 0; i <= rowNumber; i++) {
            for (int j = 0; j <= colNumber; j++) {
                if (i + slice.start[0] < rows && j + slice.start[1] < columns) {
                    String ingredient = lists.get(i + slice.start[0]).get(j + slice.start[1]);
                    int counter = 0;
                    if (map.get(ingredient) == null)
                        counter++;
                    else
                        counter = map.get(ingredient) + 1;
                    map.put(ingredient, counter);
                }
            }
        }
        slice.ingredients = map;
        return map;
    }

    boolean checkValidityOfIngredients(HashMap<String, Integer> map) {
        if (map == null)
            return false;
        if (map.get("T") == null || map.get("M") == null)
            return false;
        if (map.get("T") >= minIngredient && map.get("M") >= minIngredient) {
            return true;
        }
        return false;
    }

    ArrayList<Slice> allPossibleCombinations() {
        ArrayList<Slice> validSlices = new ArrayList<>();
        for (int i = 2*minIngredient; i <= maxSlices; i++) {
            ArrayList<Slice> slices = this.getAllPossibleSlices(i);
            for (Slice s : slices) {
                if (this.checkValidityOfIngredients(this.getIngredientInSlice(s)))
                    validSlices.add(s);
            }
        }
        return validSlices;
    }

    void check() {
        ArrayList<Slice> all = this.allPossibleCombinations();
        Collections.sort(all, new PriorityComparator(this.maxSlices));

        solutionTL = new ArrayList<>();
        solutionTR = new ArrayList<>();
        solutionBL = new ArrayList<>();
        solutionBR = new ArrayList<>();
        solutionIN = new ArrayList<>();

        loop(all);

        ArrayList<Slice> finalSolution = new ArrayList<>();


        for (Slice s : solutionTL) {
            finalSolution.add(s);
        }
        for (Slice s : solutionTR) {
            finalSolution.add(s);
        }
        for (Slice s : solutionBL) {
            finalSolution.add(s);
        }
        for (Slice s : solutionBR) {
            finalSolution.add(s);
        }
        for (Slice s : solutionIN) {
            finalSolution.add(s);
        }

        writeFile(finalSolution);

        int score = 0;
        System.out.println(finalSolution.size() + " slices");
        for (Slice s : finalSolution) {
            score += s.getIntrest();
        }
        System.out.println(score + " score");
    }

    void writeFile(ArrayList<Slice> all){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("file_out/"+this.fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(all.size());
            for (Slice s: all)
                printWriter.println(s.out());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loop(ArrayList<Slice> all) {
        boolean intersects, doubleTag;
        for (int j = 0; j < all.size(); j++) {
            System.out.println((float) j * 100 / all.size() + "%");
            Slice s = all.get(j);
            intersects = false;
            doubleTag = false;


            for (int i = 0; i < solutionIN.size(); i++) {
                if (s.intersect2(solutionIN.get(i))) {
                    intersects = true;
                    break;
                }
            }

            if (!s.tag1.equals("") && !s.tag2.equals("") && !intersects) {
                doubleTag = true;

                for (int i = 0; i < solutionTL.size(); i++) {
                    if (s.intersect2(solutionTL.get(i))) {
                        intersects = true;
                        break;
                    }
                }

                if (!intersects)
                    for (int i = 0; i < solutionTR.size(); i++) {
                        if (s.intersect2(solutionTR.get(i))) {
                            intersects = true;
                            break;
                        }
                    }

                if (!intersects)
                    for (int i = 0; i < solutionBL.size(); i++) {
                        if (s.intersect2(solutionBL.get(i))) {
                            intersects = true;
                            break;
                        }
                    }

                if (!intersects)
                    for (int i = 0; i < solutionBR.size(); i++) {
                        if (s.intersect2(solutionBR.get(i))) {
                            intersects = true;
                            break;
                        }
                    }
                if (!intersects)
                    solutionIN.add(s);
            }

            if (doubleTag)
                continue;

            if (s.tag1.equals("TL") || s.tag2.equals("TL")) {
                for (int i = solutionTL.size() - 1; i >= 0; i--) {
                    if (s.intersect2(solutionTL.get(i))) {
                        intersects = true;
                        break;
                    }
                }
                if (!intersects)
                    solutionTL.add(s);
            }
            if (s.tag1.equals("TR") || s.tag2.equals("TR")) {
                for (int i = solutionTR.size() - 1; i >= 0; i--) {
                    if (s.intersect2(solutionTR.get(i))) {
                        intersects = true;
                        break;
                    }
                }
                if (!intersects)
                    solutionTR.add(s);
            }
            if (s.tag1.equals("BL") || s.tag2.equals("BL")) {
                for (int i = solutionBL.size() - 1; i >= 0; i--) {
                    if (s.intersect2(solutionBL.get(i))) {
                        intersects = true;
                        break;
                    }
                }
                if (!intersects)
                    solutionBL.add(s);
            }

            if (s.tag1.equals("BR") || s.tag2.equals("BR")) {
                for (int i = solutionBR.size() - 1; i >= 0; i--) {
                    if (s.intersect2(solutionBR.get(i))) {
                        intersects = true;
                        break;
                    }
                }
                if (!intersects)
                    solutionBR.add(s);
            }
        }
    }

    void simpleLoop(ArrayList<Slice> all) {
        boolean intersects;
        for (int j = 0; j < all.size(); j++) {
            System.out.println((float) j * 100 / all.size() + "%");
            Slice s = all.get(j);
            intersects = false;
            for (int i = solutionTL.size() - 1; i >= 0; i--) {
                if (s.intersect2(solutionTL.get(i))) {
                    intersects = true;
                    break;
                }
            }
            if (!intersects)
                solutionTL.add(s);
        }
    }


}
