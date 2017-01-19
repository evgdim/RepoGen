package com.github.evgdim.repogen.commons;

import com.google.common.base.CaseFormat;

/**
 * Created by evgeni on 1/19/2017.
 */
public class Commons {
    public static String underscoreToCamelCase(String underscoreCaseStr){
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, underscoreCaseStr);
    }

    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
