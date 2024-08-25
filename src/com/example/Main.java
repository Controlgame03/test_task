package com.example;

import java.util.List;
import java.util.Arrays;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -cp .:lib/* com.example.Main <branch1> <branch2>");
            return;
        }

        // Check that arguments are passed
        if (args.length == 0) {
            System.out.println("Error: No branches specified.");
            return;
        }
        String branch1 = args[0];
        String branch2 = args[1];

        // List of allowed branches
        List<String> validBranches = Arrays.asList("sisyphus", "p9", "p10", "p11");

        // Check each branch against the allowed values
        for (String branch : args) {
            if (!validBranches.contains(branch)) {
                System.out.println("Error: Branch '" + branch + "' is not valid. " +
                                   "Allowed branches: " + validBranches);
                return;
            }
        }

    	// List of architectures
        List<String> architectures = Arrays.asList("x86_64", "i586", "noarch", "aarch64", "ppc64le", "armh");

        // Delete old output_{arch}.json files
        for (String arch : architectures) {
            String outputFileName = "output_" + arch + ".json";
            try {
                Files.deleteIfExists(Paths.get(outputFileName));
            } catch (Exception e) {
                e.printStackTrace(); // Error handling during file deletion
            }
        }

        for (String arch : architectures) {
            // Check conditions to skip execution for certain architectures
            if ((branch1.equals("sisyphus") || branch2.equals("sisyphus")) && arch.equals("armh")) {
                continue;
            }
            if ((branch1.equals("p11") || branch2.equals("p11")) && (arch.equals("armh") || arch.equals("ppc64le"))) {
                continue;
            }

            // Loop through architectures
            try {
                RdbApiClient client = new RdbApiClient();
                List<PackageInfo> packagesBranch1 = client.getPackagesForBranch(branch1, arch);
                List<PackageInfo> packagesBranch2 = client.getPackagesForBranch(branch2, arch);
                BinaryPackageComparator comparator = new BinaryPackageComparator();
                JSONObject resultJSON = comparator.comparePackageLists(packagesBranch1, packagesBranch2, branch1, branch2);

                String outputFileName = "output_" + arch + ".json";
                writeJsonToFile(resultJSON, outputFileName);
                System.out.println("Comparison completed for architecture: " + arch);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("End of program");
    }

    public static void writeJsonToFile(JSONObject jsonObject, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(jsonObject.toString(4)); // 4 - indentation for easy formatting
            fileWriter.flush(); // Force completion of writing
        } catch (Exception e) {
            e.printStackTrace(); // Exception handling
        }
    }
}
