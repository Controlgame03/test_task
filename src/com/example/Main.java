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

        String branch1 = args[0];
        String branch2 = args[1];  	
    	
        // проверяем, что аргументы переданы
        if (args.length == 0) {
            System.out.println("Ошибка: Не указаны ветки.");
            return;
        }

        // список допустимых веток
        List<String> validBranches = Arrays.asList("sisyphus", "p9", "p10", "p11");
        
        // проверяем каждую ветку на соответствие допустимым значениям
        for (String branch : args) {
            if (!validBranches.contains(branch)) {
                System.out.println("Ошибка: Ветка '" + branch + "' не является допустимой. " +
                                   "Допустимые ветки: " + validBranches);
                return;
            }
        }
        
    	// список архитектур
        List<String> architectures = Arrays.asList("x86_64", "i586", "noarch", "aarch64", "ppc64le", "armh");

        // удаляем старые файлы output_{arch}.json
        for (String arch : architectures) {
            String outputFileName = "output_" + arch + ".json";
            try {
                Files.deleteIfExists(Paths.get(outputFileName));
            } catch (Exception e) {
                e.printStackTrace(); // Обработка ошибок при удалении файлов
            }
        }

        for (String arch : architectures) {
            // проверяем условия, чтобы пропустить выполнение для определенных архитектур
            if ((branch1.equals("sisyphus") || branch2.equals("sisyphus")) && arch.equals("armh")) {
                continue; 
            }
            if ((branch1.equals("p11") || branch2.equals("p11")) && (arch.equals("armh") || arch.equals("ppc64le"))) {
                continue;
            }

            // цикл по архитектурам
            try {
                RdbApiClient client = new RdbApiClient();
                List<PackageInfo> packagesBranch1 = client.getPackagesForBranch(branch1, arch);
                List<PackageInfo> packagesBranch2 = client.getPackagesForBranch(branch2, arch);
                BinaryPackageComparator comparator = new BinaryPackageComparator();
                JSONObject resultJSON = comparator.comparePackageLists(packagesBranch1, packagesBranch2, branch1, branch2);

                String outputFileName = "output_" + arch + ".json";
                writeJsonToFile(resultJSON, outputFileName);
                System.out.println("Сравнение завершено для архитектуры: " + arch);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Конец программы");
    }
    
    public static void writeJsonToFile(JSONObject jsonObject, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(jsonObject.toString(4)); // 4 - отступы для удобного форматирования
            fileWriter.flush(); // Принудительное завершение записи
        } catch (Exception e) {
            e.printStackTrace(); // Обработка возможных исключений
        }
    }
}