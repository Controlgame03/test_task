package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class RdbApiClient {
    private static final String API_URL = "https://rdb.altlinux.org/api/export/branch_binary_packages/";

    public List<PackageInfo> getPackagesForBranch(String branch, String arch) throws Exception {
    	// Формируем URL
        String urlStr = API_URL + branch + "?arch=" + arch;
        try {
        	 URI uri = new URI(urlStr);
             URL url = uri.toURL();

             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         
             conn.setRequestMethod("GET");
             
             // Обработка ответа
             if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                 throw new RuntimeException("Failed to get response from API: HTTP code " + conn.getResponseCode());
             }

             BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             String inputLine;
             
             
             inputLine = in.readLine();
//             inputLine = in.readLine();
//             inputLine = in.readLine();
//             while ((inputLine = in.readLine()) != null) {
//                 content.append(inputLine);
//             }
             StringBuilder content = new StringBuilder(inputLine);
             in.close();
             conn.disconnect();

             return parsePackages(content.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URL: " + urlStr, e);
        }
    }

    private List<PackageInfo> parsePackages(String jsonStr) {
        List<PackageInfo> packages = new ArrayList<>();
        try {
	        JSONObject jsonObject = new JSONObject(jsonStr);
	        JSONArray jsonArray = jsonObject.getJSONArray("packages");
	
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject pkg = jsonArray.getJSONObject(i);
	            String name = pkg.getString("name");
	            String version = pkg.getString("version");
	            String release = pkg.getString("release");
	            String arch = pkg.getString("arch");
	
	            packages.add(new PackageInfo(name, version, release, arch));
	        }
        }
        catch(Exception ex) {
        	System.out.print(ex.getMessage());
        }

        return packages;
    }
}

