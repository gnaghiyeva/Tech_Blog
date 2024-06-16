package org.example.techblog.helpers;

public class SeoHelper {
    public String seoUrlHelper(String text){
        String[] change = text.toLowerCase()
                .replaceAll("ə", "e")
                .replaceAll("ç", "c")
                .replaceAll("ö", "o")
                .replaceAll("ö", "o")
                .split(" ");
        String result = String.join("-", change);
        return result.replaceAll("[^a-z0-9-]","");
    }
}
