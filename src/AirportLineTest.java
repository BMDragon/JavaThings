//Created by Brandon Weiss on 5/16/2022
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AirportLineTest {
    public static class Airport{
        String code;
        double latitude, longitude;

        public int compareTo(Airport comp){
            return this.code.compareTo(comp.code);
        }
    }

    public static class Line{
        Airport from, to;
        double distance;
    }

    public static final double radius = 3958.8; //Radius of Earth in miles
    public static ArrayList<Airport> ports = new ArrayList<>();
    public static ArrayList<Line> lines = new ArrayList<>();
    public static void main(String[] args) throws NumberFormatException, IOException{
        /*
        Airport nyc = new Airport(), lax = new Airport(), chi = new Airport(),
                mia = new Airport(), mex = new Airport(), sng = new Airport();
        nyc.code = "JFK"; nyc.latitude = 40.63980103; nyc.longitude = -73.77890015;
        lax.code = "LAX"; lax.latitude = 33.94250107; lax.longitude = -118.4079971;
        chi.code = "ORD"; chi.latitude = 41.9786; chi.longitude = -87.9048;
        mia.code = "MIA"; mia.latitude = 25.79319953918457; mia.longitude = -80.29060363769531;
        mex.code = "MEX"; mex.latitude = 19.4363; mex.longitude = -99.072098;
        sng.code = "SIN"; sng.latitude = 1.35019; sng.longitude = 103.994003;
        ports.add(nyc); ports.add(lax); ports.add(chi); ports.add(mia); 
        ports.add(mex); ports.add(sng);

        System.out.println("Merge Sort test:\n  Before:");
        for (int i = 0; i < ports.size(); i++){
            System.out.println(ports.get(i).code);
        }
        ports = mergeSort(ports);
        System.out.println("  After:");
        for (int i = 0; i < ports.size(); i++){
            System.out.println(ports.get(i).code);
        }

        System.out.println("\nDistances");
        Line jfklax = new Line();
        jfklax.from = nyc; jfklax.to = lax;
        double dist1 = Distance(nyc, lax);
        System.out.println("JFK to LAX: " + dist1 + " mi");
        jfklax.distance = dist1;
        Line ordmia = new Line();
        ordmia.from = chi; ordmia.to = mia;
        double dist2 = Distance(chi, mia);
        System.out.println("ORD to MIA: " + dist2 + " mi");
        ordmia.distance = dist2;
        Line mexsin = new Line();
        mexsin.from = mex; mexsin.to = sng;
        double dist3 = Distance(mex, sng);
        System.out.println("MEX to SIN: " + dist3 + " mi");
        mexsin.distance = dist3;

        System.out.println("\nCrosses");
        boolean cross1 = cross(jfklax, ordmia);
        System.out.println("JFK->LAX and ORD->MIA cross: " + cross1);
        boolean cross2 = cross(ordmia, mexsin);
        System.out.println("ORD->MIA and MEX->SIN cross: " + cross2);
        boolean cross3 = cross(jfklax, mexsin);
        System.out.println("JFK->LAX and MEX->SIN cross: " + cross3);
        */

        BufferedReader data = new BufferedReader(new FileReader("GlobalAirportDatabase.txt"));
        
        String line = "";
        while ((line=data.readLine())!=null){
            String[] port = line.split(",");
            Airport temp = new Airport();
            temp.code = port[4].substring(1, port[4].length()-1);
            if (temp.code.length() != 3){ continue; }
            temp.latitude = Double.parseDouble(port[6]);
            temp.longitude = Double.parseDouble(port[7]);
            ports.add(temp);
        }
        ports = mergeSort(ports);
        
        Line starter = new Line();
        starter.from = ports.get(1);
        starter.to = ports.get(0);
        starter.distance = Distance(starter.from, starter.to);
        lines.add(starter);

        Airport val, temp;
        Line shortest, comp;
        double hold;
        boolean change;
        for (int i = 2; i < ports.size(); i++){
            shortest = new Line();
            comp = new Line();
            val = ports.get(i);
            shortest.distance = Math.PI*radius;
            shortest.from = val;
            shortest.to = val;
            for (int j = 0; j < i; j++){
                temp = ports.get(j);
                hold = Distance(temp, val);
                if (hold >= shortest.distance){
                    continue;
                }
                change = true;
                comp.distance = hold;
                comp.from = val; comp.to = temp;
                for (Line l : lines){
                    if (l.from.code.equals(comp.to.code) || 
                        l.to.code.equals(comp.to.code)){ continue; }
                    boolean ugh = cross(l, comp);
                    if (ugh){
                        System.out.println(comp.from.code + " to " + comp.to.code +
                                            " and " + l.from.code + " to " + l.to.code + ": " + ugh);
                        change = false; break;
                    }
                }
                if (change){ 
                    shortest.to = temp;
                    shortest.distance = hold;
                }
            }
            lines.add(shortest);
            System.out.println(shortest.from.code + " -> " + shortest.to.code);
            if (shortest.from.code.equals(shortest.to.code)){ break; }
        }
        data.close();
    }

    public static ArrayList<Airport> mergeSort(ArrayList<Airport> myList){
        if (myList.size() == 1){ return myList; }
        ArrayList<Airport> first = new ArrayList<>(), last = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++){
            if (i < myList.size()/2){ first.add(myList.get(i)); }
            else { last.add(myList.get(i)); }
        }
        first = mergeSort(first);
        last = mergeSort(last);
        int j = 0, k = 0;
        Airport temp, hold;
        ArrayList<Airport> ret = new ArrayList<>();
        while (j<first.size() && k<last.size()){
            temp = first.get(j);
            hold = last.get(k);
            if (temp.compareTo(hold) < 0){ ret.add(temp); j++; }
            else { ret.add(hold); k++; }
            if (j == first.size()){
                while (k < last.size()){ ret.add(last.get(k)); k++; }
            }
            else if (k == last.size()){
                while (j < first.size()){ ret.add(first.get(j)); j++; }
            }
        }
        return ret;
    }

    public static double Distance(Airport a, Airport b){
        double gamma1 = (b.latitude*Math.PI/180-a.latitude*Math.PI/180)/2,
               gamma2 = (b.longitude*Math.PI/180-a.longitude*Math.PI/180)/2;
        double radicand = Math.cos(a.latitude*Math.PI/180)*Math.cos(b.latitude*Math.PI/180);
        radicand *= Math.sin(gamma2)*Math.sin(gamma2);
        radicand += Math.sin(gamma1)*Math.sin(gamma1);
        return 2*radius*Math.asin(Math.sqrt(radicand));
    }

    public static boolean cross(Line a, Line b){
        double x1 = radius*Math.cos(a.from.latitude*Math.PI/180)*Math.cos(a.from.longitude*Math.PI/180),
               y1 = radius*Math.cos(a.from.latitude*Math.PI/180)*Math.sin(a.from.longitude*Math.PI/180),
               z1 = radius*Math.sin(a.from.latitude*Math.PI/180);
        double x2 = radius*Math.cos(a.to.latitude*Math.PI/180)*Math.cos(a.to.longitude*Math.PI/180),
               y2 = radius*Math.cos(a.to.latitude*Math.PI/180)*Math.sin(a.to.longitude*Math.PI/180),
               z2 = radius*Math.sin(a.to.latitude*Math.PI/180);
        double x3 = radius*Math.cos(b.from.latitude*Math.PI/180)*Math.cos(b.from.longitude*Math.PI/180),
               y3 = radius*Math.cos(b.from.latitude*Math.PI/180)*Math.sin(b.from.longitude*Math.PI/180),
               z3 = radius*Math.sin(b.from.latitude*Math.PI/180);
        double x4 = radius*Math.cos(b.to.latitude*Math.PI/180)*Math.cos(b.to.longitude*Math.PI/180),
               y4 = radius*Math.cos(b.to.latitude*Math.PI/180)*Math.sin(b.to.longitude*Math.PI/180),
               z4 = radius*Math.sin(b.to.latitude*Math.PI/180);

        double aa = y1*z2-z1*y2, bb = z1*x2-x1*z2, cc = x1*y2-y1*x2,
               dd = y3*z4-z3*y4, ee = z3*x4-x3*z4, ff = x3*y4-y3*x4;
        double len1 = Math.sqrt(aa*aa+bb*bb+cc*cc), len2 = Math.sqrt(dd*dd+ee*ee+ff*ff);
        aa = aa/len1; bb = bb/len1; cc = cc/len1;
        dd = dd/len2; ee = ee/len2; ff = ff/len2;
        double dx = bb*ff-ee*cc, dy = dd*cc-aa*ff, dz = aa*ee-dd*bb;
        double len3 = Math.sqrt(dx*dx+dy*dy+dz*dz);
        double sx = dx/len3, sy = dy/len3, sz = dz/len3;

        double lat1 = Math.asin(sz), lat2 = Math.asin(-1*sz);
        double long1=0., long2=0.;
        if (sx > 0){
            long1 = Math.atan(sy/sx);
            if (sy > 0){
                long2 = Math.atan(sy/sx)-Math.PI;
            }
            else {
                long2 = Math.atan(sy/sx)+Math.PI;
            }
        }
        else if (sx < 0){
            long2 = Math.atan(sy/sx);
            if (sy >= 0){
                long1 = Math.atan(sy/sx)+Math.PI;
            }
            else {
                long1 = Math.atan(sy/sx)-Math.PI;
            }
        }
        else {
            if (sy > 0){
                long1 = Math.PI/2;
                long2 = -1*Math.PI/2;
            }
            else {
                long1 = -1*Math.PI/2;
                long2 = Math.PI/2;
            }
        }

        lat1 *= 180/Math.PI; lat2 *= 180/Math.PI; long1 *= 180/Math.PI; long2 *= 180/Math.PI;

        System.out.println("(" + lat1 + ", " + long1 + "), (" + lat2 + ", " + long2 + "): " 
                            + a.from.code + " to " + a.to.code + " and " + b.from.code + " to " + b.to.code);

        boolean ww = (long1 > Math.min(a.from.longitude, a.to.longitude) && 
                      long1 < Math.max(a.from.longitude, a.to.longitude)),
                xx = (long2 > Math.min(a.from.longitude, a.to.longitude) && 
                      long2 < Math.max(a.from.longitude, a.to.longitude)),
                yy = (long1 > Math.min(b.from.longitude, b.to.longitude) && 
                      long1 < Math.max(b.from.longitude, b.to.longitude)),
                zz = (long2 > Math.min(b.from.longitude, b.to.longitude) && 
                      long2 < Math.max(b.from.longitude, b.to.longitude));
        boolean ret1 = (ww^xx) && (yy^zz), ret2 = (ww&&yy) ^ (xx&&zz);
        return ret1 && ret2;
    }
}
