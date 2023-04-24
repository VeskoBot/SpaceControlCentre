public class EvaluatedDay {
    private LaunchDay launchDay;
    private double evaluationScore;

    public EvaluatedDay(LaunchDay launchDay, double evaluationScore) {
        this.launchDay = launchDay;
        this.evaluationScore = evaluationScore;
    }

    public LaunchDay getLaunchDay() {
        return launchDay;
    }

    public double getEvaluationScore() {
        return evaluationScore;
    }
}
