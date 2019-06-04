import java.util.ArrayList;
import java.util.Objects;

public class Restaurant {
    private Boolean[] isOpen = new Boolean[24];
    private int restaurantId;
    private Boolean temporary = false;
    Restaurant(int restaurantId) {
        this.restaurantId = restaurantId;
        for(int i=0;i<24;i++)
            isOpen[i] = false;
    }

    void enterTimings (ArrayList<ArrayList<Integer>> timings) {
        for (ArrayList<Integer> timing : timings) {
            int start = timing.get(0);
            int end = timing.get(1);
            for (int j = start; j < end; j++) {
                isOpen[j] = true;
            }
        }

        for (int i = 1; i < 24; i++) {
            if(this.isOpen[i]!=this.isOpen[i-1]){
                if(this.isOpen[i])
                    Main.updateQueue.add(new UpdateRestaurantState(i,this.restaurantId, false));
                else
                    Main.updateQueue.add(new UpdateRestaurantState(i,this.restaurantId, true));
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return restaurantId == that.restaurantId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId);
    }

    Boolean[] getIsOpen() {
        return isOpen;
    }

    int getRestaurantId() {
        return restaurantId;
    }

    Boolean getTemporary() {
        return temporary;
    }

    void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }
}
