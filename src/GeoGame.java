import java.io.*;
import java.util.*;

public class GeoGame {
    public static TreeMap<Character, Set<Place>> map = new TreeMap<>();
    public static ArrayList<Place> list = new ArrayList<>();
    public static int total;
    public static int[] front = new int[26], back = new int[26];
    public static void main(String[] args){
        readCSV("C:\\Users\\bmwei\\Documents\\GeoGame.csv");
        ArrayList<Place> hold = new ArrayList<>(map.get('a'));
        Place temp = hold.get(0);
        list.add(temp); temp.use();
        char recent = temp.getLast();
        System.out.println(temp.getName() + " " + 1);
        for(int count = 1; count < total; count++){
            for(Place p: map.get(recent)){
                if(!p.isUsed()){
                    list.add(p); p.use();
                    recent = p.getLast();
                    System.out.println(p.getName() + " " + (count + 1));
                    break;
                }
            }
        }
        write();
        int sum = 0;
        for(char c: map.keySet()){
            sum += front[(int)c - 97];
            System.out.println(c + ": start-" + front[(int)c - 97] + " end-" + back[(int)c - 97]);
        }
        System.out.println(sum);
        /*
        for(Place p: map.get('y')){
            System.out.println(p.getName());
        }*/
    }
    public static void readCSV(String directory){
        BufferedReader br = null;
        String line; total = 0;
        Arrays.fill(front,0); Arrays.fill(back, 0);
        String cvsSplitBy = ",";
        try{
            br = new BufferedReader(new FileReader(directory));
            while ((line = br.readLine()) != null){
                String[] place = line.split(cvsSplitBy);
                Place temp = new Place(place[0]);
                if(!map.containsKey(temp.getFirst())){
                    map.put(temp.getFirst(), new TreeSet<>());
                }
                map.get(temp.getFirst()).add(temp);
                System.out.println(temp.getName());
                front[(int)temp.getFirst() - 97]++;
                back[(int)temp.getLast() - 97]++;
                total++;
            }
            System.out.println(total);
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            if(br != null){
                try{
                    br.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void write(){
        try{
            FileWriter writer = new FileWriter("GeoGame.txt", false);
            for(Place p: list){
                writer.write(p.getName() + "\n");
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static class Place implements Comparable<Place>{
        private String name;
        private char first, last;
        private boolean used;
        public Place(String place){
            name = place;
            first = place.toLowerCase().charAt(0);
            last = place.charAt(place.length() - 1);
            used = false;
            if((int)first > 500){
                first = place.toLowerCase().charAt(1);
            }
        }
        public char getFirst(){ return first; }
        public char getLast(){ return last; }
        public String getName(){ return name; }
        public void use(){ used = true; }
        public boolean isUsed(){ return used; }
        @Override
        public int compareTo(Place place){
            if(this.first != place.first){ return this.first - place.first; }
            if(this.first == this.last && place.first != place.last){ return -1; }
            if(this.first != this.last && place.first == place.last){ return 1; }
            if(this.first != this.last && this.last != place.last){ return this.last - place.last; }
            return this.name.compareTo(place.name);
        }
    }
}
