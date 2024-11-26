
public class CountryRecord {
	private String country;
    private double usersPercentage;

    public CountryRecord(String country, double usersPercentage) {
        this.country = country;
        this.usersPercentage = usersPercentage;
    }

    public String getCountry() {
        return country;
    }

    public double getUsersPercentage() {
        return usersPercentage;
    }

    @Override
    public String toString() {
        return "Country: " + country + ", Users Percentage: " + usersPercentage;
    }
}