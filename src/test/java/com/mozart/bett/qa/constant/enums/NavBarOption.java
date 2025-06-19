package com.mozart.bett.qa.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NavBarOption {
    SERIE("Serie"),
    GROSSE("Größe"),
    KOPFTEIL("Kopfteil"),
    FARBE("Farbe"),
    BOX("Box"),
    FUSSE("Füße"),
    MATRATZE("Matratze"),
    HARTGEGARD_AND_TOPPER("Härtegrad & Topper"),
    UPGRADES("Upgrades"),
    ZUBEHOR("Zubehör"),
    FERTIG("Fertig");


    private final String getValue;
}
