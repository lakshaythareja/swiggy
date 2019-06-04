public class UpdateRestaurantState {
    int starttime;
    int restaurantId;
    Boolean changeToClosed;

    UpdateRestaurantState(int starttime, int restaurantId, Boolean changeToClosed) {
        this.starttime = starttime;
        this.restaurantId = restaurantId;
        this.changeToClosed = changeToClosed;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Boolean getChangeToClosed() {
        return changeToClosed;
    }

    public void setChangeToClosed(Boolean changeToClosed) {
        this.changeToClosed = changeToClosed;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }
}
