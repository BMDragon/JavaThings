//Created by Brandon Weiss on 7/19/2021

import java.util.*;

public class TwentyFour{
    public static void main(String[] args){
        int[] cards = new int[52];
        for(int i=0; i<52; i++){ cards[i] = (i/4) + 1; }

        ArrayList<int[]> setList = new ArrayList<>();
        for(int i=0; i<cards.length-3; i++){
            for(int j=i+1; j<cards.length-2; j++){
                for(int k=j+1; k<cards.length-1; k++){
                    for(int l=k+1; l<cards.length; l++){
                        int[] temp = {cards[i], cards[j], cards[k], cards[l]};
                        setList.add(temp);
                    }
                }
            }
        } // setList.size() = 270725

        Map<int[], Integer> setToNum = new HashMap<>();
        for(int[] arr: setList){
            int hold = arr[0] * 2197 + arr[1] * 169 + arr[2] * 13 + arr[3];
            setToNum.put(arr, hold);
        } // setToNum.size() = 270725

        Map<Integer, int[]> hashes = new TreeMap<>();
        for(int[] arr: setToNum.keySet()){ hashes.putIfAbsent(setToNum.get(arr), arr); } // hashes.size() = 1820

        System.out.println("Cannot get 24:");
        Map<Integer, ArrayList<Double>> numToResults = new TreeMap<>();
        for(int num: hashes.keySet()){
            ArrayList<Double> res = calc(hashes.get(num));
            numToResults.putIfAbsent(num, res);
            if(!res.contains(24.)){ System.out.println(Arrays.toString(hashes.get(num))); }
        } // numToResults.size() = 1820

        System.out.println(" ");
        int max = 0, freq;
        int[] easy = new int[4];
        Map<int[], ArrayList<Double>> finals = new HashMap<>();
        for(int[] array: setList){
            ArrayList<Double> fin = numToResults.get(setToNum.get(array));
            finals.put(array, fin);
            freq = Collections.frequency(fin, 24.);
            if(freq > max){ max = freq; easy = array; }
            if(freq == 38){ System.out.println(Arrays.toString(array)); }
        } // finals.size() = 270725

        System.out.println("Easiest array to get 24: " + Arrays.toString(easy) + ", No. of ways: " + max);

        int tot = 0;
        Map<Double, Integer> counts = new TreeMap<>();
        for(int[] name: finals.keySet()){
            tot += finals.get(name).size();
            for(Double dub: finals.get(name)){
                if(counts.containsKey(dub)){
                    int par = counts.get(dub) + 1;
                    counts.put(dub, par);
                }
                else { counts.putIfAbsent(dub, 1); }
            }
        }
        /*
        for(int a = 0; a <= 100; a++){
            if(counts.containsKey((double)a)){
                System.out.println("" + counts.get((double)a));
            }
        }
        */
        System.out.println("Total: " + tot);
    }

    public static ArrayList<Double> calc(int[] numbs){
        ArrayList<Double> ret = new ArrayList<>();
        double A = numbs[0], B = numbs[1], C = numbs[2], D = numbs[3], temp, aa, bb, cc, dd;
        ret.add(helper(A, 1, B, 1, C, 1, D, 0)); // A+B+C+D
        ret.add(helper(A, 3, B, 3, C, 3, D, 0)); // ABCD
        double[][] set3 = {{A, B, C, D}, {B, C, D, A}, {A, C, B, D}};
        double[][] set4 = {{A, B, C, D}, {B, C, D, A}, {C, D, A, B}, {D, A, B, C}};
        double[][] set6 = {{A, B, C, D}, {A, C, B, D}, {A, D, B, C}, {B, C, A, D}, {B, D, A, C}, {C, D, A, B}};
        double[][] set12 = {{A, B, C, D}, {A, C, B, D}, {A, D, B, C}, {B, A, C, D}, {B, C, A, D}, {B, D, A, C},
                {C, A, B, D}, {C, B, A, D}, {C, D, A, B}, {D, A, B, C}, {D, B, A, C}, {D, C, A, B}};
        double[][] set24 = {{A, B, C, D}, {A, C, B, D}, {A, D, B, C}, {B, A, C, D}, {B, C, A, D}, {B, D, A, C},
                {C, A, B, D}, {C, B, A, D}, {C, D, A, B}, {D, A, B, C}, {D, B, A, C}, {D, C, A, B},
                {A, B, D, C}, {A, C, D, B}, {A, D, C, B}, {B, A, D, C}, {B, C, D, A}, {B, D, C, A},
                {C, A, D, B}, {C, B, D, A}, {C, D, B, A}, {D, A, C, B}, {D, B, C, A}, {D, C, B, A}};

        for(double[] arr: set3){
            aa = arr[0]; bb = arr[1]; cc = arr[2]; dd = arr[3];
            temp = helper(aa, 1, bb, 3, cc, 1, dd, 1); // (A+B)(C+D)
            ret.add(temp);
            temp = helper(aa, 1, bb, 4, cc, 1, dd, 1); // (A+B)/(C+D)
            ret.add(temp); ret.add(1./temp);
            temp = helper(aa, 2, bb, 3, cc, 2, dd, 1); // (A-B)(C-D)
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 2, bb, 4, cc, 2, dd, 1); // (A-B)/(C-D)
            ret.add(temp); ret.add(-1*temp); ret.add(1./temp); ret.add(-1./temp);
            temp = helper(aa, 1, bb, 2, cc, 2, dd, 0); // A+B-C-D
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 3, bb, 4, cc, 4, dd, 0); // AB/C/D
            ret.add(temp); ret.add(1./temp);
            temp = helper(aa, 3, bb, 1, cc, 3, dd, 1); // AB+CD
            ret.add(temp);
            temp = helper(aa, 3, bb, 2, cc, 3, dd, 1); // AB-CD
            ret.add(temp); ret.add(-1*temp);
        }

        for(double[] arr: set4){
            aa = arr[0]; bb = arr[1]; cc = arr[2]; dd = arr[3];
            temp = helper(aa, 1, bb, 1, cc, 4, dd, 0); // (A+B+C)/D
            ret.add(temp); ret.add(1./temp);
            temp = helper(aa, 1, bb, 1, cc, 3, dd, 0); // (A+B+C)D
            ret.add(temp);
            temp = helper(aa, 1, bb, 1, cc, 2, dd, 0); // A+B+C-D
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 3, bb, 3, cc, 4, dd, 0); // ABC/D
            ret.add(temp); ret.add(1./temp);
            temp = helper(aa, 3, bb, 3, cc, 1, dd, 0); // ABC+D
            ret.add(temp);
            temp = helper(aa, 3, bb, 3, cc, 2, dd, 0); // ABC-D
            ret.add(temp); ret.add(-1*temp);
        }

        for(double[] arr: set6){
            aa = arr[0]; bb = arr[1]; cc = arr[2]; dd = arr[3];
            temp = helper(aa, 1, bb, 4, cc, 4, dd, 0); // (A+B)/C/D
            ret.add(temp); ret.add(1./temp);
            temp = helper(aa, 1, bb, 3, cc, 3, dd, 0); // (A+B)CD
            ret.add(temp);
            temp = helper(aa, 2, bb, 3, cc, 1, dd, 1); // (A-B)(C+D)
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 2, bb, 4, cc, 1, dd, 1); // (A-B)/(C+D)
            ret.add(temp); ret.add(-1*temp); ret.add(1./temp); ret.add(-1./temp);
            temp = helper(aa, 2, bb, 4, cc, 4, dd, 0); // (A-B)/C/D
            ret.add(temp); ret.add(-1*temp); ret.add(1./temp); ret.add(-1./temp);
            temp = helper(aa, 2, bb, 3, cc, 3, dd, 0); // (A-B)CD
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 3, bb, 1, cc, 1, dd, 0); // AB+C+D
            ret.add(temp);
            temp = helper(aa, 3, bb, 2, cc, 2, dd, 0); // AB-C-D
            ret.add(temp); ret.add(-1*temp);
        }

        for(double[] arr: set12){
            aa = arr[0]; bb = arr[1]; cc = arr[2]; dd = arr[3];
            temp = helper(aa, 2, cc, 2, dd, 4, bb, 0); // (A-C-D)/B
            ret.add(temp); ret.add(-1*temp); ret.add(1./temp); ret.add(-1./temp);
            temp = helper(cc, 1, dd, 4, aa, 1, bb, 0); // (C+D)/A+B
            ret.add(temp);
            temp = helper(cc, 1, dd, 4, aa, 2, bb, 0); // (C+D)/A-B
            ret.add(temp); ret.add(-1*temp);
            temp = helper(cc, 1, dd, 3, aa, 4, bb, 0); // (C+D)A/B
            ret.add(temp); ret.add(1./temp);
            temp = helper(cc, 1, dd, 3, aa, 1, bb, 0); // (C+D)A+B
            ret.add(temp);
            temp = helper(cc, 1, dd, 3, aa, 2, bb, 0); // (C+D)A-B
            ret.add(temp); ret.add(-1*temp);
            temp = helper(cc, 1, dd, 2, aa, 3, bb, 0); // (C+D-A)B
            ret.add(temp); ret.add(-1*temp);
            temp = helper(cc, 2, dd, 3, aa, 4, bb, 0); // (C-D)A/B
            ret.add(temp); ret.add(1./temp); ret.add(-1*temp); ret.add(-1./temp);
            temp = helper(cc, 3, dd, 1, aa, 4, bb, 0); // (CD+A)/B
            ret.add(temp); ret.add(1./temp);
            temp = helper(cc, 3, dd, 1, aa, 3, bb, 0); // (CD+A)B
            ret.add(temp);
            temp = helper(cc, 3, dd, 2, aa, 4, bb, 0); // (CD-A)/B
            ret.add(temp); ret.add(-1*temp); ret.add(1./temp); ret.add(-1./temp);
            temp = helper(cc, 3, dd, 2, aa, 3, bb, 0); // (CD-A)B
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 4, cc, 1, dd, 1, bb, 2); // A/(C+D)+B
            ret.add(temp);
            temp = helper(aa, 4, cc, 1, dd, 2, bb, 2); // A/(C+D)-B
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 4, bb, 1, cc, 1, dd, 0); // A/B+C+D
            ret.add(temp);
            temp = helper(aa, 4, bb, 1, cc, 3, dd, 1); // A/B+CD
            ret.add(temp);
            temp = helper(aa, 4, bb, 2, cc, 3, dd, 1); // A/B-CD
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 4, bb, 2, cc, 2, dd, 0); // A/B-C-D
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 4, cc, 4, dd, 1, bb, 0); // A/C/D+B
            ret.add(temp);
            temp = helper(aa, 4, cc, 4, dd, 2, bb, 0); // A/C/D-B
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 4, cc, 1, bb, 4, dd, 1); // A/C+B/D
            ret.add(temp);
            temp = helper(aa, 4, cc, 2, bb, 4, dd, 1); // A/C-B/D
            ret.add(temp); ret.add(-1*temp);
            temp = helper(cc, 3, dd, 4, aa, 1, bb, 0); // CD/A+B
            ret.add(temp);
            temp = helper(cc, 3, dd, 4, aa, 2, bb, 0); // CD/A-B
            ret.add(temp); ret.add(-1*temp);
            temp = helper(cc, 3, dd, 1, aa, 2, bb, 0); // CD+A-B
            ret.add(temp); ret.add(-1*temp);
        }

        for(double[] arr: set24){
            aa = arr[0]; bb = arr[1]; cc = arr[2]; dd = arr[3];
            temp = helper(aa, 4, bb, 1, cc, 4, dd, 0); // (A/B+C)/D
            ret.add(temp); ret.add(1./temp);
            temp = helper(aa, 4, bb, 1, cc, 3, dd, 0); // (A/B+C)D
            ret.add(temp);
            temp = helper(aa, 4, bb, 2, cc, 4, dd, 0); // (A/B-C)/D
            ret.add(temp); ret.add(1./temp); ret.add(-1*temp); ret.add(-1./temp);
            temp = helper(aa, 4, bb, 2, cc, 3, dd, 0); // (A/B-C)D
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 2, bb, 4, cc, 1, dd, 0); // (A-B)/C+D
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 2, bb, 3, cc, 1, dd, 0); // (A-B)C+D
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 4, bb, 1, cc, 2, dd, 0); // A/B+C-D
            ret.add(temp); ret.add(-1*temp);
            temp = helper(aa, 4, cc, 2, dd, 1, bb, 2); // A/(C-D)+B
            ret.add(temp); ret.add(-1*temp);
        }

        Collections.sort(ret);
        return ret;
    }

    public static double helper(double A, int op1, double B, int op2, double C, int op3, double D, int op4){
        double hold, hold2;
        if(op4 == 0) {
            if(op1 == 1){ hold = A + B; }
            else if(op1 == 2){ hold = A - B; }
            else if(op1 == 3){ hold = A * B; }
            else{ hold = A/B; }

            if (op2 == 1) { hold = hold + C; }
            else if (op2 == 2) { hold = hold - C; }
            else if (op2 == 3) { hold = hold * C; }
            else { hold = hold / C; }

            if (op3 == 1) { hold = hold + D; }
            else if (op3 == 2) { hold = hold - D; }
            else if (op3 == 3) { hold = hold * D; }
            else { hold = hold / D; }
        }
        else if(op4 == 1){
            if(op1 == 1){ hold = A + B; }
            else if(op1 == 2){ hold = A - B; }
            else if(op1 == 3){ hold = A * B; }
            else{ hold = A/B; }

            if (op3 == 1) { hold2 = C + D; }
            else if (op3 == 2) { hold2 = C - D; }
            else if (op3 == 3) { hold2 = C * D; }
            else { hold2 = C / D; }

            if (op2 == 1) { hold = hold + hold2; }
            else if (op2 == 2) { hold = hold - hold2; }
            else if (op2 == 3) { hold = hold * hold2; }
            else { hold = hold / hold2; }
        }
        else{
            if(op2 == 1){ hold = C + B; }
            else if(op2 == 2){ hold = B - C; }
            else if(op2 == 3){ hold = C * B; }
            else{ hold = B/C; }

            if (op1 == 1) { hold = hold + A; }
            else if (op1 == 2) { hold = A - hold; }
            else if (op1 == 3) { hold = hold * A; }
            else { hold = A / hold; }

            if (op3 == 1) { hold = hold + D; }
            else if (op3 == 2) { hold = hold - D; }
            else if (op3 == 3) { hold = hold * D; }
            else { hold = hold / D; }
        }
        return hold;
    }
}
