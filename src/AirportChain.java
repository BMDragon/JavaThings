//Created by Brandon Weiss on 7/22/2020
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class AirportChain{
    public static TreeMap<String, Set<String>> child = new TreeMap<>();
    public static TreeMap<String, String> parent = new TreeMap<>();
    public static TreeMap<String, String> location = new TreeMap<>();
    public static void main(String[] args) throws IOException{
        String[] codes = generate();
        //String[] test = {"JFK", "LAX", "LHR", "PVG", "PEK", "YYZ", "YOW", "SFO", "SAN", "LGA", "ORD", "MCO", "ATL",
        //        "EWR", "MSP", "DEN", "PHX", "SEA", "FCO", "AMS", "CDG", "MAD", "BCN", "PDX"};
        String kids;
        for(String code: codes){
            airport(code);
        }
        FileWriter csvWriter = new FileWriter("AirportChain.csv", false);
        csvWriter.append("Code");
        csvWriter.append(",");
        csvWriter.append("Location");
        csvWriter.append(",");
        csvWriter.append("Parent");
        csvWriter.append(",");
        csvWriter.append("Children");
        csvWriter.append("\n");
        for(String code: location.keySet()){
            csvWriter.append(code);
            csvWriter.append(",");
            csvWriter.append(location.get(code));
            csvWriter.append(",");
            csvWriter.append(parent.get(code));
            csvWriter.append(",");
            if(child.get(code) == null){
                csvWriter.append("N/A");
            }
            else{
                kids = child.get(code).toString();
                kids = kids.replaceAll(",", ";");
                csvWriter.append(kids);
            }
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
    public static String[] generate(){
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "A"};
        ArrayList<String> codes = new ArrayList<>();
        int fi = 0, mi = 0, li = 0, leng = 26*26*26;
        String first = "A", mid = "A", last = "A", concat;
        for(int i = 0; i < leng; i++){
            concat = first + mid + last;
            codes.add(concat);
            if(!last.equals("Z")){
                li++;
                last = letters[li];
            }
            else if(!mid.equals("Z")){
                li = 0; mi++;
                last = letters[li];
                mid = letters[mi];
            }
            else{
                li = 0; mi = 0; fi++;
                last = letters[li];
                mid = letters[mi];
                first = letters[fi];

            }
        }
        return codes.toArray(new String[0]);
    }
    public static void airport(String code) throws IOException {
        // Make a URL to the web page
        URL url = new URL("https://www.flightsfrom.com/" + code);

        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();

        boolean marked = false;
        int dex, per, open, close, var;
        String loc = null, par = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null){
            if(line.contains("airport-text")){
                marked = true;
            }
            if(marked){
                if(!line.contains("<script>") &&
                        (line.contains("most departures") || line.contains("most frequently departed") ||
                                line.contains("one and only"))){
                    System.out.println(line);
                    if(line.contains("based in") && line.indexOf("based in") < 300) {
                        dex = line.indexOf("based in");
                        var = 9;
                    }
                    else{
                        dex = line.indexOf("in", line.indexOf(" "));
                        var = 3;
                    }
                    per = line.indexOf(".", dex);
                    loc = line.substring(dex + var, per);
                    if(line.contains("most departures")) {
                        dex = line.indexOf("most departures");
                        open = line.indexOf("(", dex);
                        close = line.indexOf(")", open);
                        par = line.substring(open+1, close);
                    }
                    else if(line.contains("one and only")){
                        dex = line.indexOf("one and only");
                        open = line.indexOf("<a href", dex);
                        close = line.indexOf("</a>", open);
                        par = line.substring(open+15, close);
                    }
                    else{
                        dex = line.indexOf("most frequently departed");
                        open = line.indexOf("(", dex);
                        close = line.indexOf(")", open);
                        par = line.substring(open+1, close);
                    }
                    marked = false;
                }
            }
        }
        System.out.println(loc + "    " + par);
        if(par != null){
            if(loc.contains(",")){
                loc = loc.replaceAll(",","");
            }
            parent.put(code, par);
            location.put(code, loc);
            if(!child.containsKey(par)){
                child.put(par, new TreeSet<>());
            }
            child.get(par).add(code);
        }
    }
}