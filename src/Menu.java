import java.util.List;

public class Menu{
    private List<String> options;
    private String inappropriateOption;
    private String enterMinTemp;
    private String enterMaxTemp;
    private String enterWind;
    private String enterHumidity;
    private String enterPrecipitation;
    private String enterLightning;
    private String enterFile;
    private String enterSender;
    private String enterPassword;
    private String enterReceiver;
    private String enterLanguage;
    private String emailSubject;
    private String emailBody;
    private String sendingEmail;
    private String emailSent;
    private String emailNotSent;
    private String fileNotFound;
    private String fileReadExc;
    private String fileWriteExc;
    private String invalidNumber;
    private String nonNumerical;

    public Menu(){

    }
    public List<String> getOptions() {
        return options;
    }

    public String getInappropriateOption() {
        return inappropriateOption;
    }

    public String getEnterMinTemp() {
        return enterMinTemp;
    }

    public String getEnterMaxTemp() {
        return enterMaxTemp;
    }

    public String getEnterWind() {
        return enterWind;
    }

    public String getEnterHumidity() {
        return enterHumidity;
    }

    public String getEnterPrecipitation() {
        return enterPrecipitation;
    }

    public String getEnterLightning() {
        return enterLightning;
    }

    public String getEnterFile() {
        return enterFile;
    }

    public String getEnterSender() {
        return enterSender;
    }

    public String getEnterPassword() {
        return enterPassword;
    }

    public String getEnterReceiver() {
        return enterReceiver;
    }

    public String getEnterLanguage() {
        return enterLanguage;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public String getSendingEmail() {
        return sendingEmail;
    }

    public String getEmailSent() {
        return emailSent;
    }

    public String getEmailNotSent() {
        return emailNotSent;
    }

    public String getFileNotFound() {
        return fileNotFound;
    }

    public String getFileReadExc() {
        return fileReadExc;
    }

    public String getFileWriteExc() {
        return fileWriteExc;
    }

    public String getInvalidNumber() {
        return invalidNumber;
    }

    public String getNonNumerical() {
        return nonNumerical;
    }
}
