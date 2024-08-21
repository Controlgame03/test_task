package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class RdbApiClient {
    private static final String API_URL = "https://rdb.altlinux.org/api/export/branch_binary_packages/";

    public List<PackageInfo> getPackagesForBranch(String branch) throws Exception {
        String urlStr = API_URL + branch;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        
        
        content.append(in.readLine());
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        

        in.close();
        conn.disconnect();

        return parsePackages(content.toString());
    }

    private List<PackageInfo> parsePackages(String jsonStr) {
        List<PackageInfo> packages = new ArrayList<>();
        try {
	        JSONObject jsonObject = new JSONObject(jsonStr);
	        JSONArray jsonArray = jsonObject.getJSONArray("packages");
	
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject pkg = jsonArray.getJSONObject(i);
	            String name = pkg.getString("name");
	            String versionRelease = pkg.getString("version") + "-" + pkg.getString("release");
	            String arch = pkg.getString("arch");
	
	            packages.add(new PackageInfo(name, versionRelease, arch));
	        }
        }
        catch(Exception ex) {
        	System.out.print(ex.getMessage());
        }

        return packages;
    }
}

