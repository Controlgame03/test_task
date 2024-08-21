package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class BinaryPackageComparator {

    public JSONObject comparePackageLists(List<PackageInfo> list1, List<PackageInfo> list2) {
        Map<String, PackageInfo> map1 = createPackageMap(list1);
        Map<String, PackageInfo> map2 = createPackageMap(list2);

        JSONArray onlyInFirst = new JSONArray();
        JSONArray onlyInSecond = new JSONArray();
        JSONArray versionHigherInFirst = new JSONArray();

        for (String key : map1.keySet()) {
            if (!map2.containsKey(key)) {
                onlyInFirst.put(map1.get(key).getName() + " (" + map1.get(key).getArch() + ")");
            } else {
                if (compareVersion(map1.get(key).getVersionRelease(), map2.get(key).getVersionRelease()) > 0) {
                    versionHigherInFirst.put(map1.get(key).getName() + " (" + map1.get(key).getArch() + ")");
                }
            }
        }

        for (String key : map2.keySet()) {
            if (!map1.containsKey(key)) {
                onlyInSecond.put(map2.get(key).getName() + " (" + map2.get(key).getArch() + ")");
            }
        }

        JSONObject result = new JSONObject();
        try {
        	result.put("onlyInFirstBranch", onlyInFirst);
        	result.put("onlyInSecondBranch", onlyInSecond);
        	result.put("versionHigherInFirstBranch", versionHigherInFirst);
        }
        catch(Exception ex) {
        	System.out.print(ex.getMessage());
        }
        return result;
    }

    private Map<String, PackageInfo> createPackageMap(List<PackageInfo> packageList) {
        Map<String, PackageInfo> packageMap = new HashMap<>();
        for (PackageInfo pkg : packageList) {
            packageMap.put(pkg.getName() + ":" + pkg.getArch(), pkg);
        }
        return packageMap;
    }

    private int compareVersion(String v1, String v2) {
        return v1.compareTo(v2);
    }
}