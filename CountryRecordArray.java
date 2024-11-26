import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountryRecordArray {
    private CountryRecord[] records; // Array to store CountryRecord objects
    private int size; // Current number of records in the array

    public CountryRecordArray() {
        records = new CountryRecord[16]; // Initialize the array with a default size
        size = 0; // Initialize the size to 0
    }

    // Method to add a new CountryRecord to the array
    public void addRecord(CountryRecord record) {
        if (size == records.length) { // If the array is full, double its size
            int newSize = records.length * 2;
            CountryRecord[] newRecords = new CountryRecord[newSize];
            System.arraycopy(records, 0, newRecords, 0, records.length);
            records = newRecords; // Update the records array with the new size
        }
        records[size++] = record; // Add the new record and increment the size
    }

    // Method to find a CountryRecord by country name
    public CountryRecord findRecordByName(String countryName) {
        for (int i = 0; i < size; i++) {
            if (records[i].getCountry().equals(countryName)) {
                return records[i]; // Return the matching record
            }
        }
        return null; // If not found, return null
    }

    // Method to delete a CountryRecord by country name
    public void deleteRecordByName(String countryName) {
        for (int i = 0; i < size; i++) {
            if (records[i].getCountry().equals(countryName)) {
                for (int j = i; j < size - 1; j++) {
                    records[j] = records[j + 1];
                }
                records[size - 1] = null;
                size--; // Remove the record and decrement the size
                return;
            }
        }
    }

    // Method to search for records by a specific percentage of internet users
    public CountryRecord[] searchByPercentage(double percentage) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (records[i].getUsersPercentage() == percentage) {
                count++;
            }
        }
        CountryRecord[] result = new CountryRecord[count];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (records[i].getUsersPercentage() == percentage) {
                result[index++] = records[i];
            }
        }
        return result; // Return an array of matching records
    }

    // Method to search for records by country name (case-insensitive)
    public CountryRecord[] searchByName(String name) {
        List<CountryRecord> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (records[i].getCountry().equalsIgnoreCase(name)) {
                result.add(records[i]); // Add matching records to a list
            }
        }
        return result.toArray(new CountryRecord[0]); // Return an array of matching records
    }

    // Method to display all records in the array
    public void displayRecords() {
        for (int i = 0; i < size; i++) {
            System.out.println(records[i]); // Print each record to the console
        }
    }

    // Method to get an array of all stored records
    public CountryRecord[] getRecords() {
        CountryRecord[] result = new CountryRecord[size];
        System.arraycopy(records, 0, result, 0, size);
        return result; // Return an array containing all records
    }

    // Method to search for records by both country name and percentage
    public CountryRecord[] searchByNameAndPercentage(String searchText, double percentage) {
        List<CountryRecord> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (records[i].getCountry().equalsIgnoreCase(searchText) && records[i].getUsersPercentage() == percentage) {
                result.add(records[i]); // Add matching records to a list
            }
        }
        return result.toArray(new CountryRecord[0]); // Return an array of matching records
    }

    // Method to read data from a CSV file and populate the array
    public void readDataFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String country = parts[0].trim();
                    double usage = Double.parseDouble(parts[1].trim());
                    CountryRecord record = new CountryRecord(country, usage);
                    addRecord(record); // Add records from the file to the array
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
