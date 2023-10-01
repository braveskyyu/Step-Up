package com.joey.step.project.collections;

import lombok.Getter;
import lombok.Setter;

public enum CountryEnum {
    QI(1, "齐国"),
    CHU(2, "楚国"),
    YAN(3, "燕国"),
    HAN(4, "韩国"),
    ZHAO(5, "赵国"),
    WEI(6, "魏国");

    @Getter @Setter private int countryCode;
    @Getter @Setter private String countryName;

    CountryEnum(int code, String name){
        this.countryCode = code;
        this.countryName = name;
    }

    static public CountryEnum getCountryEnum(int code){
        CountryEnum[] enums = CountryEnum.values();
        for (CountryEnum enu : enums) {
            if (enu.getCountryCode()==code){
                return enu;
            }
        }
        return null;
    }

}
