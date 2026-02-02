package com.tiket.testbase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ignored {
    private static List<String> list = List.of(
            //"https://www.tiket.com"
    );

    public static Set<String> getLinks() {
        return new HashSet<>(list);
    }
}
