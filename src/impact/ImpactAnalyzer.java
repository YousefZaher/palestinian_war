package impact;

public interface ImpactAnalyzer {
    void analyzeImpact();
    void add(int placesDestroyed, int peopleAffected);
    void update(int placesDestroyed, int peopleAffected);
}
