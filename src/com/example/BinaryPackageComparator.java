package com.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryPackageComparator {

    public JSONObject comparePackageLists(List<PackageInfo> list1, List<PackageInfo> list2, String branch1, String branch2) {
        JSONObject result = new JSONObject();
        try {
            result.put("unique_in_" + branch1, getUniquePackages(list1, list2));
            result.put("unique_in_" + branch2, getUniquePackages(list2, list1));
            result.put("version_greater_in_" + branch1, getVersionGreaterPackages(list1, list2));
        }
        catch(JSONException ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    private JSONArray getUniquePackages(List<PackageInfo> sourceList, List<PackageInfo> targetList) {
        Map<String, Boolean> targetMap = new HashMap<>();
        for (PackageInfo pkg : targetList) {
            targetMap.put(pkg.getName(), true);
        }

        JSONArray uniquePackages = new JSONArray();
        for (PackageInfo pkg : sourceList) {
            if (!targetMap.containsKey(pkg.getName())) {
                uniquePackages.put(pkg.getName());
            }
        }
        return uniquePackages;
    }

    private JSONArray getVersionGreaterPackages(List<PackageInfo> list1, List<PackageInfo> list2) {
        Map<String, PackageInfo> list2Map = new HashMap<>();
        for (PackageInfo pkg : list2) {
            list2Map.put(pkg.getName(), pkg);
        }

        JSONArray greaterVersionPackages = new JSONArray();
        for (PackageInfo pkg : list1) {
            PackageInfo targetPkg = list2Map.get(pkg.getName());
            if (targetPkg != null && compareVersion(pkg.getVersion(), targetPkg.getVersion()) > 0) {
                greaterVersionPackages.put(pkg.getName());
            }
        }
        return greaterVersionPackages;
    }

    private int compareVersion(String version1, String version2) {
        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\."); 

        int maxLength = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < maxLength; i++) {
            String part1 = i < parts1.length ? parts1[i].replaceAll("\\D+", "") : "0";
            String part2 = i < parts2.length ? parts2[i].replaceAll("\\D+", "") : "0";

            long num1 = part1.isEmpty() ? 0 : Long.parseLong(part1);
            long num2 = part2.isEmpty() ? 0 : Long.parseLong(part2);

            // сравниваем числовые представления
            if (num1 != num2) {
                return Long.compare(num1, num2); // возвращаем результат сравнения
            }
        }

        return 0; // если все части равны, возвращаем 0
    }
}