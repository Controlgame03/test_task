 
package com.example;

import java.util.Arrays;

public class PackageCompare {

    // Метод сравнения двух пакетов
    public static int versionCompare(PackageInfo package1, PackageInfo package2, String priority) {
        // Сначала сравниваем эпохи
        int result = compareEpoch(package1.getEpoch(), package2.getEpoch());
        if (result != 0) return result;

        // Если эпохи равны, сравниваем версии
        result = compareVersion(package1.getVersion(), package2.getVersion());
        if (result != 0) return result;

        // Если версии равны, сравниваем релизы
        result = compareRelease(package1.getRelease(), package2.getRelease());
        if (result != 0) return result;

        // Если релизы равны, сравниваем дистрибутивные теги
        result = compareDisttag(package1.getDisttag(), package2.getDisttag(), priority);
        if (result != 0) return result;

        // Если дистрибутивные теги равны, сравниваем время сборки
        return compareBuildtime(package1.getBuildtime(), package2.getBuildtime());
    }

    // Сравнение эпох
    private static int compareEpoch(int epoch1, int epoch2) {
        return Integer.compare(epoch1, epoch2);
    }

    private static int compareVersion(String version1, String version2) {
        // Разбиваем строки на сегменты по специальным символам
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

    // Разбиваем версию на сегменты по специальным символам
    private static String[] splitVersion(String version) {
        return version.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|[\\.\\-\\+_~]");
    }

    // Сравнение отдельных сегментов версии
    private static int compareSegments(String seg1, String seg2) {
        if (seg1.isEmpty() && !seg2.isEmpty()) return -1;
        if (!seg1.isEmpty() && seg2.isEmpty()) return 1;

        // Сравнение, если оба сегмента числовые
        if (isNumeric(seg1) && isNumeric(seg2)) {
            return compareNumeric(seg1, seg2);
        }

        // Обработка случая, когда один сегмент числовой, а другой нет
        if (isNumeric(seg1)) return -1;
        if (isNumeric(seg2)) return 1;

        // Если сегменты содержат символы "~"
        if (seg1.equals("~") || seg2.equals("~")) {
            if (!seg1.equals(seg2)) {
                return seg1.equals("~") ? -1 : 1;
            }
        }

        //Если сегменты это равные по старшинству спецсимволы
        String[] symbols = new String[] {".", "_", "-", "+"};
        if ((Arrays.asList(symbols).contains(seg1) && Arrays.asList(symbols).contains(seg2))) {
        	return 0;
        }

        // Лексикографическое сравнение для других строк
        return seg1.compareTo(seg2);
    }

 // Метод для сравнения числовых сегментов типа Long
    private static int compareNumeric(String num1, String num2) {
        Long number1 = Long.parseLong(num1);
        Long number2 = Long.parseLong(num2);
        return Long.compare(number1, number2);
    }

    // Проверка, является ли строка числом
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    // Сравнение релизов
    private static int compareRelease(String release1, String release2) {
        return PackageCompare.compareVersion(release1, release2);
    }

    // Сравнение дистрибутивных тегов
    public static int compareDisttag(String fdt, String sdt, String priority) {
        // Пропуск опциональной части перед `:`
        int fpadIndex = fdt.indexOf(':');
        int spadIndex = sdt.indexOf(':');

        if (fpadIndex != -1) {
            fdt = fdt.substring(fpadIndex + 1);
        }
        if (spadIndex != -1) {
            sdt = sdt.substring(spadIndex + 1);
        }

        // Извлечение подстрок до первого `+`
        String one = extractBranch(fdt);
        String two = extractBranch(sdt);

        if (one.length() == 0 || two.length() == 0) return -1;

        // Сравнение подстрок
        int rc = PackageCompare.compareVersion(one, two);

        if (rc != 0) {
        	if (rc == 1) {
        		System.out.println("");
        	}
            // Учет приоритета ветки (если установлен)
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

    // Извлечение подстроки до первого `+` (или всей строки, если `+` нет)
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


    // Сравнение времени сборки
    private static int compareBuildtime(int buildtime1, int buildtime2) {
        return Integer.compare(buildtime1, buildtime2);
    }
}
