package com.poncho.interview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class WebController {

    @GetMapping("/sitestatus")
    public String siteStatusSubmit(@ModelAttribute("sitestatus") SiteStatus siteStatus) {


        List<String> websites = new ArrayList<>();
        websites.add("www.netflix.com");
        websites.add("www.wikipedia.com");
        websites.add("www.yahoo.com");

        if (siteStatus.getWebAddress() != null && siteStatus.getWebAddress().length() !=0) {
            websites.add(siteStatus.getWebAddress());
        }

        siteStatus.setWebsitesStatus(getWebsiteStatus(websites));

        return "sitestatus";
    }

    private HashMap<String, String> getWebsiteStatus(List<String> websites){

        System.out.println(websites);

        SiteStatus siteStatus = new SiteStatus();

        LinkedHashMap<String, String> websitesStatus = new LinkedHashMap<>();

        for (int i = 0; i < websites.size(); i++) {

            ProcessBuilder processBuilder = new ProcessBuilder();
            String command = "curl -I "+websites.get(i)+" 2>&1 | awk '/HTTP\\// {print $2}'";
            processBuilder.command("bash", "-c", command);

            try {

                Process process = processBuilder.start();

                StringBuilder output = new StringBuilder();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }

                int exitVal = process.waitFor();
                if (exitVal == 0) {
                    if (output.toString().equals("200") || output.toString().equals("301")) {
                        System.out.println("Website "+websites.get(i) +" is Up");
                        websitesStatus.put(websites.get(i),"Up");
                    } else {
                        System.out.println("Website "+websites.get(i) +" is Down");
                        websitesStatus.put(websites.get(i),"Down");
                    }
                } else {
                    System.out.println("Failed!");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        siteStatus.setWebsitesStatus(websitesStatus);
        System.out.println("map is"+siteStatus.getWebsitesStatus());
        return siteStatus.getWebsitesStatus();
    }
}
