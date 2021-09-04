package com.example.wangbotian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;


public class OpenEducation {
    static String id = "PoweredBy605";
    protected static String navticeEncode(String url) {
        String resultUrl= "";
        for (int i = 0; i < url.length(); i++) {
            char charIndex = url.charAt(i);
            if (isChinese(charIndex)) {
                try { resultUrl += URLEncoder.encode(url.charAt(i)+"","UTF-8"); }
                catch (Exception e) {}
            }else {
                resultUrl += url.charAt(i);
            }
        }
        return resultUrl;
    }
    protected static boolean isChinese(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(navticeEncode(urlNameString));
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            System.out.println(connection.toString());
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return "404";
        }
        finally{
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    private static void resetId(){
        String result = OpenEducation.sendPost("http://open.edukg.cn/opedukg/api/typeAuth/user/login", "password=poweredby605&phone=15070491616");
        int startIdx = result.indexOf("id"), endIdx = result.indexOf("}");
        if (startIdx > 0) id = result.substring(startIdx+5, endIdx-1);
    }
    private static boolean idCheck(String result){
        if (result.contains("请先登录"))
            return false;
        else return true;
    }
    public static String entitySearch(String course, String searchKey){
        String result = OpenEducation.sendGet("http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList", "course="+course+"&searchKey="+searchKey+"&id="+id);
        if (idCheck(result)) return result;
        else {
            resetId();
            result = OpenEducation.sendGet("http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList", "course="+course+"&searchKey="+searchKey+"&id="+id);
            return result;
        }
    }
    public static String entityDetail(String course, String name){
        String result = OpenEducation.sendGet("http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName", "course="+course+"&name="+name+"&id="+id);
        if (idCheck(result)) return result;
        else {
            resetId();
            result = OpenEducation.sendGet("http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName", "course="+course+"&name="+name+"&id="+id);
            return result;
        }
    }
    public static String answerQuestion(String course, String question){
        String result = OpenEducation.sendPost("http://open.edukg.cn/opedukg/api/typeOpen/open/inputQuestion", "course="+course+"&inputQuestion="+question+"&id="+id);
        if (idCheck(result)) return result;
        else {
            resetId();
            result = OpenEducation.sendPost("http://open.edukg.cn/opedukg/api/typeOpen/open/inputQuestion", "course="+course+"&inputQuestion="+question+"&id="+id);
            return result;
        }
    }
    public static String entityLink(String course, String context){
        String result = OpenEducation.sendPost("http://open.edukg.cn/opedukg/api/typeOpen/open/linkInstance", "context="+context+"&course="+course+"&id="+id);
        if (idCheck(result)) return result;
        else {
            resetId();
            result = OpenEducation.sendPost("http://open.edukg.cn/opedukg/api/typeOpen/open/linkInstance", "context="+context+"&course="+course+"&id="+id);
            return result;
        }
    }
    public static String entityExam(String uriName){
        String result = OpenEducation.sendGet("http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName", "uriName="+uriName+"&id="+id);
        if (idCheck(result)) return result;
        else {
            resetId();
            result = OpenEducation.sendGet("http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName", "uriName="+uriName+"&id="+id);
            return result;
        }
    }
}