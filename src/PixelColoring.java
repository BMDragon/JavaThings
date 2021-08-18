import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

public class PixelColoring {
    public static void main(String[] args) {
        String fileName = "Red Middle.png";
        int width = 512, height = 512, holdDex = Integer.MAX_VALUE, recentA = 0, recentD = 0, distHold = 0;
        int seedX = 256, seedY = 256;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Map<Integer, Boolean> free = new TreeMap<>();
        //Say map is empty
        for(int i = 0; i < width*height; i++){
            free.put(i, true);
        }

        boolean breaker = false;
        int x, y, r, g, b, rgb, distX, distY, seed = width*seedY + seedX, recentB = (width*height)-1;
        double dist2;
        // Generate algorithmically.
        for(int i = 0; i < width*height; i++){
            r = 4*(i/4096);
            g = 4*((i/64) % 64);
            b = 4*(i % 64);
            rgb = 256*256*r + 256*g + b;
            System.out.println("" + i + ", " + rgb);

            if(b >= g && b > r){
                for(int j = recentA; !breaker; j++){
                    holdDex = j; breaker = free.get(j);
                }
                recentA = holdDex;
            }

            else if(g > b && g > r){
                for(int j = recentB; !breaker; j--){
                    holdDex = j; breaker = free.get(j);
                }
                recentB = holdDex;
            }

            else{
                for(int dist = recentD; !breaker; dist++){
                    distHold = dist;
                    for(int pix = seed - 256*dist; pix <= seed + 256*dist && !breaker; pix++){
                        distX = Math.abs((pix % width)-seedX); distY = Math.abs((pix/height)-seedY);
                        dist2 = Math.sqrt(distX*distX + distY*distY);
                        if((0 <= pix && pix < width*height) && dist2 <= dist){
                            holdDex = pix; breaker = free.get(pix);
                        }
                    }
                }
                recentD = distHold;
            }
            free.replace(holdDex, false); breaker = false;

            x = holdDex % width;
            y = holdDex / height;
            img.setRGB(x, y, rgb);
        }

        // Save.
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName))){
            ImageIO.write(img, "png", out);
        }
        catch (IOException e){ e.printStackTrace(); }
    }
}