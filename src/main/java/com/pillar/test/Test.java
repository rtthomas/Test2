
package com.pillar.test;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, Object> parsedArgs = parseArgs(args);
        String name = (String) parsedArgs.getOrDefault("name", "World");
        System.out.println("Hello " + name);
    }

    private static Map<String, Object> parseArgs(String[] args) {
        Map<String, Object> map = new HashMap<>();
        for (String arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.substring(2).split("=", 2);
                String key = parts[0];
                if (parts.length == 2) {
                    map.put(key, parts[1]);
                } else {
                    map.put(key, true);
                }
            }
        }
        return map;
    }
}
