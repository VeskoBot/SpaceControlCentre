import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {
    public static Scanner scanner;

    public static double minTemperature = 2;
    public static double maxTemperature = 31;
    public static double maxWind = 10;
    public static double maxHumidity = 60;
    public static double maxPrecipitation = 0;
    public static String hasLightning = "No";

    public static Menu menu;

    public static List<AggregatedData> aggregatedData = new ArrayList<>();

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        setLanguage("en");
        int cmd;
        loop : while (true){
            printOptions();
            try {
                cmd = Integer.parseInt(scanner.nextLine());
                switch (cmd){
                    case 1:
                        changeLanguage();
                        break;
                    case 2:
                        launch();
                        break;
                    case 3:
                        changeCriteria();
                        break;
                    case 4:
                        break loop;
                    default:
                        System.out.println(menu.getInappropriateOption());
                }
            }catch (NumberFormatException e){
                System.out.println(menu.getInappropriateOption());

            }
        }
    }

    //Function to set the language from a JSON file
    public static void setLanguage(String language){
        if(language.equalsIgnoreCase("en")){
            try(BufferedReader reader = Files.newBufferedReader(Paths.get("Menu_EN.json"))) {
                // create Gson instance
                Gson gson = new Gson();

                // create a reader

                // convert a JSON string to a User object
                menu = gson.fromJson(reader,Menu.class);
            } catch (Exception ex) {
                System.out.println("Unable to read from JSON file");
            }
        } else if (language.equalsIgnoreCase("de")) {
            try(BufferedReader reader = Files.newBufferedReader(Paths.get("Menu_DE.json"))) {
                // create Gson instance
                Gson gson = new Gson();

                // create a reader

                // convert a JSON string to a User object
                menu = gson.fromJson(reader,Menu.class);
            } catch (Exception ex) {
                System.out.println("Unable to read from JSON file");
            }
        }
    }
//Function that allows the user to choose a language (DE/EN)
    public static void changeLanguage(){
        String language;
        //Asks the user to enter a language until a valid value is given
        while (true) {
            System.out.println(menu.getEnterLanguage());
            language = scanner.nextLine();
            if(language.equalsIgnoreCase("en") || language.equalsIgnoreCase("de")){
                setLanguage(language);
                break;
            }
            System.out.println("EN or DE");
        }
    }

    //Function that sends an email with the evaluated data
    public static void launch() {
        try {
            //"WeatherForecast.csv"
            System.out.println(menu.getEnterFile());
            String weatherFilePath = scanner.nextLine();
            System.out.println(menu.getEnterSender());
            String sender = scanner.nextLine();
            System.out.println(menu.getEnterPassword());
            String password = scanner.nextLine();
            System.out.println(menu.getEnterReceiver());
            String receiver = scanner.nextLine();

            //Calling the read function and then evaluating the days
            List<LaunchDay> launchDays = readCSV(weatherFilePath);
            List<LaunchDay> appropriateDays = evaluateDays(launchDays);
            writeCSV(appropriateDays);

            //Test email: "spacecontrolcentre@gmail.com"
            //Test app password:"wishuxgvzcnlegmj"

            //Sending the email
            MailSender.sendMail(sender, password, receiver,menu);
        }
        catch (FileNotFoundException e) {
            System.out.println(menu.getFileNotFound());
        } catch (IOException e) {
            System.out.println(menu.getFileReadExc());
        } catch (NumberFormatException e){
            System.out.println(menu.getNonNumerical());
        }
    //Test app password:"wishuxgvzcnlegmj"
    }

    //Function that allows the user to set the criteria for the launch day
    public static void changeCriteria(){
        try {
            System.out.println(menu.getEnterMinTemp());
            minTemperature = Double.parseDouble(scanner.nextLine());
            System.out.println(menu.getEnterMaxTemp());
            maxTemperature = Double.parseDouble(scanner.nextLine());
            System.out.println(menu.getEnterWind());
            maxWind = Double.parseDouble(scanner.nextLine());
            System.out.println(menu.getEnterHumidity());
            maxHumidity = Double.parseDouble(scanner.nextLine());
            System.out.println(menu.getEnterPrecipitation());
            maxPrecipitation = Double.parseDouble(scanner.nextLine());
            while (true) {
                System.out.println(menu.getEnterLightning());
                hasLightning = scanner.nextLine();
                if(hasLightning.equalsIgnoreCase("yes") || hasLightning.equalsIgnoreCase("no")){
                    break;
                }else{
                    System.out.println("Yes or No!");
                }
            }
        } catch (NumberFormatException e){
            System.out.println(menu.getInvalidNumber());
        }
    }

    //Function that prints the options available to the user
    public static void printOptions() {
        System.out.println("------------------------------------");
        System.out.println(menu.getOptions().get(0));
        System.out.println(menu.getOptions().get(1));
        System.out.println(menu.getOptions().get(2));
        System.out.println(menu.getOptions().get(3));
        System.out.println("------------------------------------");
    }

    //function that evaluates the days appropriate for launch
    public static List<LaunchDay> evaluateDays(List<LaunchDay> launchDays) {
        List<EvaluatedDay> evaluatedDays = new ArrayList<>();
        List<LaunchDay> appropriateDays = new ArrayList<>();
        double evaluationScore;

        //Going through all the launch days and evaluating those that meet the given criteria
        for (LaunchDay launchDay : launchDays) {
            if (launchDay.getTemperature() >= minTemperature && launchDay.getTemperature() <= maxTemperature && launchDay.getWind() <= maxWind
                    && launchDay.getHumidity() <= maxHumidity && launchDay.getPrecipitation() <= maxPrecipitation
                    && launchDay.getLightning().equalsIgnoreCase(hasLightning) && launchDay.getClouds().equalsIgnoreCase("cumulus")) {
                evaluationScore = (maxWind - launchDay.getWind()) + (maxHumidity - launchDay.getHumidity());
                evaluatedDays.add(new EvaluatedDay(launchDay, evaluationScore));
            }
        }

        //Sorting the evaluated days so the most appropriate days are first
        evaluatedDays.sort((e1, e2) -> Double.compare(e2.getEvaluationScore(), e1.getEvaluationScore()));

        //Checking if multiple days are equally appropriate for a launch
        for (EvaluatedDay evaluatedDay : evaluatedDays) {
            if (evaluatedDay.getEvaluationScore() == evaluatedDays.get(0).getEvaluationScore()) {
                appropriateDays.add(evaluatedDay.getLaunchDay());
            }
        }

        return appropriateDays;
    }
    //Function that writes the most appropriate day for launch and the aggregate data in the weather report file
    public static void writeCSV(List<LaunchDay> appropriateDays) {
        //Creates BufferedWriter that writes to the WeatherReport.csv file in a try-with-resources statement
        try (BufferedWriter resultFile = new BufferedWriter(new FileWriter("WeatherReport.csv"))) {

            //Adds information about the day to the file.
            resultFile.append("Day");
            for (LaunchDay launchDay : appropriateDays) {
                resultFile.append(",");
                resultFile.append(Integer.toString(launchDay.getDay()));
            }
            resultFile.append("\n");

            //Adds information about the day to the file.
            resultFile.append("Temperature (C)");
            for (LaunchDay launchDay : appropriateDays) {
                resultFile.append(",");
                resultFile.append(Double.toString(launchDay.getTemperature()));
            }
            resultFile.append(aggregatedData.get(0).toString());
            resultFile.append("\n");

            //Adds information about the wind to the file.
            resultFile.append("Wind (m/s)");
            for (LaunchDay launchDay : appropriateDays) {
                resultFile.append(",");
                resultFile.append(Double.toString(launchDay.getWind()));
            }
            resultFile.append(aggregatedData.get(1).toString());
            resultFile.append("\n");

            //Adds information about the humidity to the file.
            resultFile.append("Humidity (%)");
            for (LaunchDay launchDay : appropriateDays) {
                resultFile.append(",");
                resultFile.append(Double.toString(launchDay.getHumidity()));
            }
            resultFile.append(aggregatedData.get(2).toString());
            resultFile.append("\n");

            //Adds information about the precipitation to the file.
            resultFile.append("Precipitation (%)");
            for (LaunchDay launchDay : appropriateDays) {
                resultFile.append(",");
                resultFile.append(Double.toString(launchDay.getPrecipitation()));
            }
            resultFile.append(aggregatedData.get(3).toString());
            resultFile.append("\n");

            //Adds information about the lightning to the file.
            resultFile.append("Lightning");
            for (LaunchDay launchDay : appropriateDays) {
                resultFile.append(",");
                resultFile.append(launchDay.getLightning());
            }
            resultFile.append("\n");

            //Adds information about the clouds to the file.
            resultFile.append("Clouds");
            for (LaunchDay launchDay : appropriateDays) {
                resultFile.append(",");
                resultFile.append(launchDay.getClouds());
            }
            resultFile.append("\n");
            resultFile.flush();


        } catch (IOException e) {
            System.out.println(menu.getFileWriteExc());
        }
    }
//Function that reads the data from a csv file
    public static List<LaunchDay> readCSV(String filePath) throws IOException , NumberFormatException{
        //Creates BufferedReader that reads from the requested file in a try-with-resources statement
        try (BufferedReader weatherForecastFile = new BufferedReader(new FileReader(filePath))) {
            //List to store the data from the file
            List<String[]> data = new ArrayList<>();
            List<LaunchDay> launchDays = new ArrayList<>();
            String row;

            //Reading from the file until EOF
            while ((row = weatherForecastFile.readLine()) != null) {
                String[] rowData = row.split(",");
                data.add(rowData);
            }

            //Creating arrays that store the values for each field
            double[] temperatureValues = new double[data.get(0).length-1];
            double[] windValues = new double[data.get(0).length-1];
            double[] humidityValues  = new double[data.get(0).length-1];
            double[] precipitationValues = new double[data.get(0).length-1];

            //Assigning the values for each day
            for (int i = 1; i < data.get(0).length; i++) {
                int day = Integer.parseInt(data.get(0)[i]);
                double temperature = Double.parseDouble(data.get(1)[i]);
                temperatureValues[i-1] = temperature;
                double wind = Double.parseDouble(data.get(2)[i]);
                windValues[i-1] = wind;
                double humidity = Double.parseDouble(data.get(3)[i]);
                humidityValues[i-1] = humidity;
                double precipitation = Double.parseDouble(data.get(4)[i]);
                precipitationValues[i-1] = precipitation;
                String lightning = data.get(5)[i];
                String clouds = data.get(6)[i];
                launchDays.add(new LaunchDay(day, temperature, wind, humidity, precipitation, lightning, clouds));
            }

            //Adds the values from each field to the aggregateData class
            aggregatedData.add(new AggregatedData(temperatureValues));
            aggregatedData.add(new AggregatedData(windValues));
            aggregatedData.add(new AggregatedData(humidityValues));
            aggregatedData.add(new AggregatedData(precipitationValues));

            return launchDays;
        }
    }
}