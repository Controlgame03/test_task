package com.example;

import java.util.List;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.out.println("Usage: java -cp .:lib/* com.example.Main <branch1> <branch2>");
//            return;
//        }
//
//        String branch1 = args[0];
//        String branch2 = args[1];
    	
    	String branch1 = "p10";
    	String branch2 = "p9";
        try {
            RdbApiClient client = new RdbApiClient();
            List<PackageInfo> packagesBranch1 = client.getPackagesForBranch(branch1);
            List<PackageInfo> packagesBranch2 = client.getPackagesForBranch(branch2);

            BinaryPackageComparator comparator = new BinaryPackageComparator();
            JSONObject result = comparator.comparePackageLists(packagesBranch1, packagesBranch2);

            System.out.println(result.toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}