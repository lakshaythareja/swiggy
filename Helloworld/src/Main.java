import java.util.*;

public class Main {

    static ArrayList<UpdateRestaurantState> updateQueue = new ArrayList<>();

    private static ArrayList<UpdateRestaurantState> toggleQueue = new ArrayList<>();

    private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static ArrayList<Restaurant> openRestaurants = new ArrayList<>();
    private static ArrayList<Restaurant> closedRestaurants = new ArrayList<>();

    public static void main(String[] args) {
        getInput();
    }

    private static void getInput() {
        System.out.println("What would you like to do");
        System.out.println("1. Enter new restaurant");
        System.out.println("2. Enter holiday timings");
        System.out.println("3. Check restaurants for current time");
        System.out.println("4. Update cache for the given time");
        System.out.println("5. Exit choose -1");
        Scanner sc = new Scanner(System.in);
        int choice;
        while((choice = sc.nextInt())!=-1) {
            switch (choice) {
                case 1:
                    enterNewRestaurant();
                    break;
                case 2:
                    enterHolidayTimings();
                    break;
                case 3:
                    checkRestaurants();
                    break;
                case 4:
                    updateCache();
                    break;
            }
        }
        System.exit(1);
    }


    private static void checkRestaurants() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the current time");
        int currentTime = sc.nextInt();
        for (Restaurant openRestaurant : openRestaurants) {
            System.out.println("Restaurant " + openRestaurant.getRestaurantId() + " open");
        }

        for (Restaurant closedRestaurant : closedRestaurants) {
            System.out.println("Restaurant " + closedRestaurant.getRestaurantId() + " closed will open at " + getNextOpen(closedRestaurant, currentTime));
        }
        getInput();
    }

    private static String getNextOpen(Restaurant restaurant, int currentTime) {
        Boolean[] isOpen = restaurant.getIsOpen();
        int i;
        if(restaurant.getTemporary())
            i = currentTime+1;
        else i= currentTime;
        for(;i<24;i++){
            if(isOpen[i])
                return String.valueOf(i);
        }
        return "Value unavailable";
    }

    private static void enterNewRestaurant(){
        System.out.println("Enter the restaurant id as int");
        Scanner sc = new Scanner(System.in);
        int restaurantId = sc.nextInt();
        Restaurant restaurant = new Restaurant(restaurantId);
        restaurants.add(restaurant);
        System.out.println("Please enter the timings of the restaurant");
        System.out.println("Enter the number of timing values");
        int numberOfTiming = sc.nextInt();
        ArrayList<ArrayList<Integer>> timings = new ArrayList<>();
        for(int i=0;i<numberOfTiming;i++){
            ArrayList<Integer> timing = new ArrayList<>();
            System.out.println("Enter the opening time");
            int start = sc.nextInt();
            timing.add(start);
            System.out.println("Enter the closing time");
            int end = sc.nextInt();
            timing.add(end);
            timings.add(timing);
        }
        restaurant.enterTimings(timings);
        getInput();
    }

    private static void enterHolidayTimings() {
        System.out.println("Enter the restaurant id as int");
        Scanner sc = new Scanner(System.in);
        int restaurantId = sc.nextInt();
        restaurants.get(restaurants.indexOf(new Restaurant(restaurantId)));
        System.out.println("Please enter the timings of the restaurant");
        int start, end;
        System.out.println("Enter the start time");
        start = sc.nextInt();
        System.out.println("Enter the end time");
        end = sc.nextInt();
        toggleQueue.add(new UpdateRestaurantState(start,restaurantId,false));
        toggleQueue.add(new UpdateRestaurantState(end,restaurantId,false));
        getInput();
    }

    private static void updateCache(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the time for which cache should be updated");
        int currentTime = sc.nextInt();
        System.out.println("Updating the cache for current time period");
        for (UpdateRestaurantState updateRestaurantState : updateQueue) {
            if (updateRestaurantState.starttime == currentTime) {
                Restaurant restaurant = restaurants.get(restaurants.indexOf(new Restaurant(updateRestaurantState.restaurantId)));
                if (updateRestaurantState.changeToClosed) {
                    openRestaurants.remove(restaurant);
                    closedRestaurants.add(restaurant);
                } else {
                    closedRestaurants.remove(restaurant);
                    openRestaurants.add(restaurant);
                }
            }
        }

        for (UpdateRestaurantState updateRestaurantState : toggleQueue) {
            if (updateRestaurantState.starttime == currentTime) {
                Restaurant restaurant = restaurants.get(restaurants.indexOf(new Restaurant(updateRestaurantState.restaurantId)));
                restaurant.setTemporary(!restaurant.getTemporary());
                if (openRestaurants.contains(restaurant)) {
                    openRestaurants.remove(restaurant);
                    closedRestaurants.add(restaurant);
                } else {
                    closedRestaurants.remove(restaurant);
                    openRestaurants.add(restaurant);
                }
            }
        }
        System.out.println("Cache updated");
        getInput();
    }
}
