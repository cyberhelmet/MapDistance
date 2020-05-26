import java.util.Scanner;
import java.lang.Math;

/**
 * Map Distance, a map distance and travel time calculator.
 * @author Abhishek Tamang, [add your name in corrected version]
 */
public class MapDistance {
    
    public enum Planet {
        MERCURY (2.4397e6),
        VENUS   (6.0518e6),
        EARTH   (6.37814e6),
        MARS    (3.3972e6),
        JUPITER (7.1492e7),
        SATURN  (6.0268e7),
        URANUS  (2.5559e7),
        NEPTUNE (2.4746e7),
        PLUTO   (1.137e6);
        
        public final double RADIUS; //in metres
        
        Planet(double RADIUS) {
            this.RADIUS = RADIUS;
        }
        
        public String toString() {
            String originalName = this.name();
            return originalName.charAt(0) + originalName.substring(1).toLowerCase();
        }
    }
    
    private static boolean inRange(int value, int low, int high) {
        return value >= low && value <= high;
    }
    
    public static Planet selectPlanet(Scanner in) {
        Planet[] options = Planet.values();
        int selected;
        
        do {
            System.out.println("On which planet?");
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.print("Enter choice: ");
            selected = in.nextInt()-1;
            if (! inRange(selected, 1, options.length)) {
                System.err.println("Invalid selection\n");
            }
        } while (! inRange(selected, 1, options.length));
        
        return options[selected];
    }    

    public static double worldDistance(double lat1, double long1, double lat2,
                                       double long2, final double RADIUS)
    {
        double a;
        
        a = Math.pow(Math.sin(Math.toRadians(lat2 - lat1)/2), 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.pow(Math.sin(Math.toRadians(long2 - long1)/2), 2);
        
        return Math.round(2*RADIUS * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))/1000);
    }

    public static String formatTime(double hours) {
        long h = Math.round(Math.floor(hours));
        return String.format("%d hours %d minutes", h, Math.round(60*(hours - h)));
    }
        public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String origin, destination;
        double lat1, long1, lat2, long2;
        Planet where;
        double distance;
        double speed;
        
        System.out.println("Map Distance & Travel Time Calculator");
        System.out.println();
        System.out.print("Enter origin: ");
        origin = sc.nextLine();
        System.out.print("Enter destination: ");
        destination = sc.nextLine();
        System.out.println("Enter their respective latitude and longitude coordinates " +
                           "as decimal values,\nseparated by spaces (e.g., " + 
                           "write 42°30'00\"S as -42.5): ");
        
        lat1 = sc.nextDouble();
        long1 = sc.nextDouble();
        lat2 = sc.nextDouble();
        long2 = sc.nextDouble();
        
        System.out.println();
        where = selectPlanet(sc);
        
        distance = worldDistance(lat1, long1, lat2, long2, where.RADIUS);
        
        System.out.println();
        System.out.print("Enter average speed over the ground (km/h): ");
        speed = sc.nextDouble();
      
        System.out.println();
        System.out.println("Shortest distance from " + origin + " to " + destination +
                           " on " + where + ": " + distance + " km");
        System.out.println("Minimum travel time: " + formatTime(distance / speed) + "*");
        System.out.println("*Ignores gravitational and atmospheric effects");
    }

}