//Created by Brandon Weiss on 4/23/2020
import java.io.*; import java.util.*;
public class ChineseQuiz{
    public static class Vocab{
        private final String eng, ch, py;
        public Vocab(String english, String character, String pinyin){
            eng = english; ch = character; py = pinyin;
        }
        public String getEnglish(){ return eng; }
        public String getCharacter(){ return ch; }
        public String getPinyin(){ return py; }
        @Override
        public String toString(){
            return "Vocab{" + "English='" + eng + '\'' +
                    ", Character='" + ch + '\'' + ", Pinyin='" + py + '\'' + '}';
        }
    }
    public static ArrayList<Vocab> list = new ArrayList<>();
    public static String name;
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        System.out.print("What is the file name: ");
        name = scnr.nextLine();
        readCSV("C:\\Users\\bmwei\\IdeaProjects\\Stuff\\" + name + ".csv");
        for(Vocab v: list){ System.out.println(v.toString()); }
        write();
    }
    public static void readCSV(String directory){
        BufferedReader br;
        String line, cvsSplitBy = ",";
        try{
            br = new BufferedReader(new FileReader(directory));
            while((line = br.readLine()) != null){
                String[] vocab = line.split(cvsSplitBy);
                String cha = vocab[1].substring(0,vocab[1].indexOf(" "));
                String pin = vocab[1].substring(vocab[1].indexOf(" ") + 1);
                Vocab temp = new Vocab(vocab[0], cha, pin);
                list.add(temp);
            }
        }
        catch(IOException e){ e.printStackTrace(); }
    }
    public static void write(){
        try{
            FileWriter writer = new FileWriter(name + "_Quiz.txt", false);
            int ques = 1, n, a1, a2, a3, r, index;
            char a;
            writer.write("Name:                       Date:");
            Random rand = new Random();
            ArrayList<Integer> used1 = new ArrayList<>(), used2 = new ArrayList<>(),
                    used3 = new ArrayList<>(), used4 = new ArrayList<>();
            for(int i = 0; i < list.size()/2; i++){ //Character to English
                n = -1; used1.add(-1);
                while(used1.contains(n)){ n = rand.nextInt(list.size()); }
                used1.add(n);
                r = rand.nextInt(4);
                a1 = n; a2 = n; a3 = n; a = 'a'; index = 0;
                while(a1 == n){ a1 = rand.nextInt(list.size()); }
                while(a2 == n || a2 == a1){ a2 = rand.nextInt(list.size()); }
                while(a3 == n || a3 == a1 || a3 == a2){ a3 = rand.nextInt(list.size()); }
                int[] hold = {a1, a2, a3};
                writer.write("\n\n" + ques + ". What is \"" + list.get(n).getCharacter() + "\"?");
                for(int x = 0; x < 4; x++){
                    if(x == r){
                        writer.write("\n" + a + ". " + list.get(n).getEnglish());
                    }
                    else{
                        writer.write("\n" + a + ". " + list.get(hold[index]).getEnglish());
                        index++;
                    }
                    a++;
                }
                ques++;
            }
            for(int j = 0; j < list.size()/2; j++){ //English to Character
                n = -1; used2.add(-1);
                while(used2.contains(n)){ n = rand.nextInt(list.size()); }
                used2.add(n);
                r = rand.nextInt(4);
                a1 = n; a2 = n; a3 = n; a = 'a'; index = 0;
                while(a1 == n){ a1 = rand.nextInt(list.size()); }
                while(a2 == n || a2 == a1){ a2 = rand.nextInt(list.size()); }
                while(a3 == n || a3 == a1 || a3 == a2){ a3 = rand.nextInt(list.size()); }
                int[] hold = {a1, a2, a3};
                writer.write("\n\n" + ques + ". What is \"" + list.get(n).getEnglish() + "\"?");
                for(int x = 0; x < 4; x++){
                    if(x == r){
                        writer.write("\n" + a + ". " + list.get(n).getCharacter());
                    }
                    else{
                        writer.write("\n" + a + ". " + list.get(hold[index]).getCharacter());
                        index++;
                    }
                    a++;
                }
                ques++;
            }
            for(int k = 0; k < list.size()/2; k++){ //Character to Pin Yin
                n = -1; used3.add(-1);
                while(used3.contains(n)){ n = rand.nextInt(list.size()); }
                used3.add(n);
                r = rand.nextInt(4);
                a1 = n; a2 = n; a3 = n; a = 'a'; index = 0;
                while(a1 == n){ a1 = rand.nextInt(list.size()); }
                while(a2 == n || a2 == a1){ a2 = rand.nextInt(list.size()); }
                while(a3 == n || a3 == a1 || a3 == a2){ a3 = rand.nextInt(list.size()); }
                int[] hold = {a1, a2, a3};
                writer.write("\n\n" + ques + ". What is \"" + list.get(n).getCharacter() + "\"?");
                for(int x = 0; x < 4; x++){
                    if(x == r){
                        writer.write("\n" + a + ". " + list.get(n).getPinyin());
                    }
                    else{
                        writer.write("\n" + a + ". " + list.get(hold[index]).getPinyin());
                        index++;
                    }
                    a++;
                }
                ques++;
            }
            for(int l = 0; l < list.size()/2; l++){ //Pin Yin to Character
                n = -1; used4.add(-1);
                while(used4.contains(n)){ n = rand.nextInt(list.size()); }
                used4.add(n);
                r = rand.nextInt(4);
                a1 = n; a2 = n; a3 = n; a = 'a'; index = 0;
                while(a1 == n){ a1 = rand.nextInt(list.size()); }
                while(a2 == n || a2 == a1){ a2 = rand.nextInt(list.size()); }
                while(a3 == n || a3 == a1 || a3 == a2){ a3 = rand.nextInt(list.size()); }
                int[] hold = {a1, a2, a3};
                writer.write("\n\n" + ques + ". What is \"" + list.get(n).getPinyin() + "\"?");
                for(int x = 0; x < 4; x++){
                    if(x == r){
                        writer.write("\n" + a + ". " + list.get(n).getCharacter());
                    }
                    else{
                        writer.write("\n" + a + ". " + list.get(hold[index]).getCharacter());
                        index++;
                    }
                    a++;
                }
                ques++;
            }
            writer.close();
        }
        catch(IOException e){ e.printStackTrace(); }
    }
}