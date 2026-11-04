package Entities;

/**
 * Apibrėžia funkcionalumą, leidžiantį objektui gauti laikinas galias arba efektus („power-up“).
 * <p>
 * Ši sąsaja turėtų būti įgyvendinama tų klasių, kurios gali įjungti arba išjungti
 * specialią būseną (pvz., nematomumą, greičio padidėjimą ir pan.), bei patikrinti,
 * ar šiuo metu ta būsena aktyvi.
 * </p>
 */
public interface Boostable {

    /**
     * Įjungia „power-up“ efektą.
     */
    void powerUp();

    /**
     * Išjungia „power-up“ efektą.
     */
    void powerDown();

    /**
     * Patikrina, ar „power-up“ efektas šiuo metu aktyvus.
     *
     * @return true, jei aktyvus, false – jei ne
     */
    boolean isPoweredUp();
}
