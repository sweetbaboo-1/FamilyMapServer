package Model;

import java.util.Random;

public class LocationData {
    Location[] data;

    public LocationData(Location[] data) {
        this.data = data;
    }

    public Location[] getData() {
        return data;
    }

    public Location getRandomLocation() {
        Random random = new Random();
        return data[random.nextInt(data.length)];
    }

    public void setData(Location[] data) {
        this.data = data;
    }
}
