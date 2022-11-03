package Model;

import java.util.Objects;

/**
 * An object used to represent an event associated with a person
 */
public class Event {
    /**
     * The unique event ID
     */
    private String eventID;
    /**
     * The username of the person associated with this event
     */
    private String associatedUsername;
    /**
     * The ID of the person associated with this event
     */
    private String personID;
    /**
     * The latitude of where the event took place
     */
    private float latitude;
    /**
     * The longitude of where the event took place
     */
    private float longitude;
    /**
     * The country where the event took place
     */
    private String country;
    /**
     * The city where the event took place
     */
    private String city;

    /**
     * The type of the event
     */
    private String eventType;

    /**
     * The year that the event took place
     */
    private int year;


    /**
     * Constructor
     * @param eventID the ID of this event
     * @param associatedUsername the username of the person associated with this event
     * @param personID the ID of the person associated with this event
     * @param latitude the latitude of where this event occurred
     * @param longitude the longitude of where this event occurred
     * @param country the country where this event occurred
     * @param city the city where this event occurred
     * @param eventType the type of this event
     * @param year the year that this event occurred
     */
    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername) && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country) && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", associatedUsername='" + associatedUsername + '\'' +
                ", personID='" + personID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", eventType='" + eventType + '\'' +
                ", year=" + year +
                '}';
    }
}
