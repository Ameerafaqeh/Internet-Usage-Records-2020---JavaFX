import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Record {
	public static void main(String[] args) {
		
	 String fileName = "internet_2020.csv.csv";
	    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] countries = new String[16];
            String[] usersPercentages = new String[16];
            int currentIndex = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 2) {
                    continue;
                }

                String country = parts[0];
                String usersPercentage = parts[1];

                // ÊÃßÏ ãä Ãä ÇáãÕÝæÝÉ áíÓÊ ããÊáÆÉ
                if (currentIndex >= countries.length) {
                    // ÅÐÇ ßÇäÊ ããÊáÆÉ¡ ÒÏ ÍÌãåÇ ÈãÞÏÇÑ ÖÚÝ ÇáÍÌã ÇáÍÇáí
                    int newSize = countries.length * 2;
                    countries = Arrays.copyOf(countries, newSize);
                    usersPercentages = Arrays.copyOf(usersPercentages, newSize);
                }

                // ÍÝÙ ÇáÈíÇäÇÊ Ýí ÇáãÕÝæÝÉ
                countries[currentIndex] = country;
                usersPercentages[currentIndex] = usersPercentage;
                currentIndex++;
            }

            // ØÈÇÚÉ ÇáÈíÇäÇÊ ÈÚÏ ÇáÞÑÇÁÉ
            for (int i = 0; i < currentIndex; i++) {
                System.out.println("Countries: " + countries[i] + ", users internet: " + usersPercentages[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
