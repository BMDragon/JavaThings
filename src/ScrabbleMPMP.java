import java.util.*;

public class ScrabbleMPMP{
    public static class Letter implements Comparable<Letter>{
        private final int worth;
        private final String name;
        public Letter(String letter, int points){
            name = letter;
            worth = points;
        }

        @Override
        public int compareTo(Letter letter) {
            return this.name.compareTo(letter.name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static ArrayList<Letter> list = new ArrayList<>();
    public static ArrayList<String> store = new ArrayList<>();

    public static void main(String[] args){
        String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "_"};
        int[] points = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0},
                amount = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2};
        for(int i = 0; i < chars.length; i++){
            for (int j = 0; j < amount[i]; j++) {
                list.add(new Letter(chars[i], points[i]));
            }
        }
        Collections.sort(list);
        int count = 0;
        for (int a = 0; a < list.size() - 6; a++){
            for (int b = a+1; b < list.size() - 5; b++){
                for (int c = b+1; c < list.size() - 4; c++){
                    for (int d = c+1; d < list.size() - 3; d++){
                        for (int e = d+1; e < list.size() - 2; e++){
                            for (int f = e+1; f < list.size() - 1; f++){
                                for (int g = f+1; g < list.size(); g++){
                                    Letter one = list.get(a), two = list.get(b), three = list.get(c),
                                            four = list.get(d), five = list.get(e), six = list.get(f),
                                            seven = list.get(g);
                                    int sum = one.worth + two.worth + three.worth + four.worth +
                                            five.worth + six.worth + seven.worth;
                                    String hold = one.toString() + two.toString() + three.toString() +
                                            four.toString() + five.toString() + six.toString() + seven.toString();
                                    if(sum == 46 && !store.contains(hold)){
                                        count++;
                                        System.out.println(count + " " + hold);
                                        store.add(hold);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
