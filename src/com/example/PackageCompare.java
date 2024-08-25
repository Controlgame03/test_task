package com.example;

import java.util.Arrays;

public class PackageCompare {

    // Method for comparing two packages
    public static int versionCompare(PackageInfo package1, PackageInfo package2, String priority) {
        // First, compare epochs
        int result = compareEpoch(package1.getEpoch(), package2.getEpoch());
        if (result != 0) return result;

        // If epochs are equal, compare versions
        result = compareVersion(package1.getVersion(), package2.getVersion());
        if (result != 0) return result;

        // If versions are equal, compare releases
        result = compareRelease(package1.getRelease(), package2.getRelease());
        if (result != 0) return result;

        // If releases are equal, compare distribution tags
        result = compareDisttag(package1.getDisttag(), package2.getDisttag(), priority);
        if (result != 0) return result;

        // If distribution tags are equal, compare build times
        return compareBuildtime(package1.getBuildtime(), package2.getBuildtime());
    }

    // Compare epochs
    private static int compareEpoch(int epoch1, int epoch2) {
        return Integer.compare(epoch1, epoch2);
    }

    private static int compareVersion(String version1, String version2) {
        // Split strings into segments by special characters
        String[] segments1 = splitVersion(version1);
        String[] segments2 = splitVersion(version2);

        int length = Math.max(segments1.length, segments2.length);

        for (int i = 0; i < length; i++) {
            String seg1 = i < segments1.length ? segments1[i] : "";
            String seg2 = i < segments2.length ? segments2[i] : "";

            int comparisonResult = compareSegments(seg1, seg2);
            if (comparisonResult != 0) {
                return comparisonResult;
            }
        }

        return 0;
    }

    // Split version into segments by special characters
    private static String[] splitVersion(String version) {
        return version.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|[\\.\\-\\+_~]");
    }

    // Compare individual version segments
    private static int compareSegments(String seg1, String seg2) {
        if (seg1.isEmpty() && !seg2.isEmpty()) return -1;
        if (!seg1.isEmpty() && seg2.isEmpty()) return 1;

        // Comparison if both segments are numeric
        if (isNumeric(seg1) && isNumeric(seg2)) {
            return compareNumeric(seg1, seg2);
        }

        // Handle case when one segment is numeric and the other is not
        if (isNumeric(seg1)) return -1;
        if (isNumeric(seg2)) return 1;

        // Handle segments containing the "~" character
        if (seg1.equals("~") || seg2.equals("~")) {
            if (!seg1.equals(seg2)) {
                return seg1.equals("~") ? -1 : 1;
            }
        }

        // If the segments are equal in precedence special characters
        String[] symbols = new String[] {".", "_", "-", "+"};
        if (Arrays.asList(symbols).contains(seg1) && Arrays.asList(symbols).contains(seg2)) {
            return 0;
        }

        // Lexicographical comparison for other strings
        return seg1.compareTo(seg2);
    }

    // Method for comparing numeric segments of type Long
    private static int compareNumeric(String num1, String num2) {
        Long number1 = Long.parseLong(num1);
        Long number2 = Long.parseLong(num2);
        return Long.compare(number1, number2);
    }

    // Check if a string is numeric
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    // Compare releases
    private static int compareRelease(String release1, String release2) {
        return PackageCompare.compareVersion(release1, release2);
    }

    // Compare distribution tags
    public static int compareDisttag(String fdt, String sdt, String priority) {
        // Skip optional part before `:`
        int fpadIndex = fdt.indexOf(':');
        int spadIndex = sdt.indexOf(':');

        if (fpadIndex != -1) {
            fdt = fdt.substring(fpadIndex + 1);
        }
        if (spadIndex != -1) {
            sdt = sdt.substring(spadIndex + 1);
        }

        // Extract substrings up to the first `+`
        String one = extractBranch(fdt);
        String two = extractBranch(sdt);

        if (one.isEmpty() || two.isEmpty()) return -1;

        // Compare substrings
        int rc = PackageCompare.compareVersion(one, two);

        if (rc != 0) {
            // Consider branch priority (if set)
            String priBranch = priority;

            if (priBranch != null && !priBranch.isEmpty()) {
                if (one.equals(priBranch)) {
                    return 1;
                } else if (two.equals(priBranch)) {
                    return -1;
                }
            }
        }

        return rc;
    }

    // Extract substring up to the first `+` (or the entire string if `+` is absent)
    private static String extractBranch(String disttag) {
        int plusIndex = disttag.indexOf('+');

        if (plusIndex != -1) {
            return disttag.substring(0, plusIndex);
        }

        int countIndex = disttag.indexOf('.');

        if (countIndex != -1) {
            return disttag.substring(0, countIndex);
        }
        return disttag;
    }

    // Compare build times
    private static int compareBuildtime(int buildtime1, int buildtime2) {
        return Integer.compare(buildtime1, buildtime2);
    }
}
