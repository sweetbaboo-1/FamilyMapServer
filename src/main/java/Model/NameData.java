package Model;

import java.util.Random;

public class NameData {
    String[] data;

    public NameData(String[] data) {
        this.data = data;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getRandomName() {
        Random random = new Random();
        return data[random.nextInt(data.length)];
    }
}
