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
            result.put("version_greater_in_" + branch1, getVersionGreaterPackages(list1, list2, branch1, branch2));
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

    private JSONArray getVersionGreaterPackages(List<PackageInfo> list1, List<PackageInfo> list2, String branch1, String branch2) {
        Map<String, PackageInfo> list2Map = new HashMap<>();
        for (PackageInfo pkg : list2) {
            list2Map.put(pkg.getName(), pkg);
        }

        JSONArray greaterVersionPackages = new JSONArray();
        for (PackageInfo pkg : list1) {
            PackageInfo targetPkg = list2Map.get(pkg.getName());
            String priorityBranch = getPriorityBranch(branch1, branch2);
            if (targetPkg != null && PackageCompare.versionCompare(pkg, targetPkg, priorityBranch) > 0) {
                greaterVersionPackages.put(pkg.getName());
            }
        }
        return greaterVersionPackages;
    }

    private String getPriorityBranch(String str1, String str2) {
    	if (str1.matches("p\\d+") && str2.matches("p\\d+")) {
            // Извлечение числовых частей из строк
            int num1 = Integer.parseInt(str1.substring(1));
            int num2 = Integer.parseInt(str2.substring(1));

            // Сравнение числовых значений и возврат строки с большим числом
            if (num1 > num2) return str1;

            else return str2;
    	}
    	return "sisyphus";
    }
}
