package mokiyoki.enhancedanimals.entity;

public interface EnhancedAnimal {

    int getHunger();

    void decreaseHunger(int decreaseAmount);

    Boolean isAnimalSleeping();

    void awaken();

}
