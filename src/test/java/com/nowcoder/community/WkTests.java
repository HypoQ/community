package com.nowcoder.community;

public class WkTests {

    public static void main(String[] args) {
        String cmd = "e:/workproject/wkhtmltopdf/bin/wkhtmltoimage --quality 75 http://www.cqupt.edu.cn e:/work/wk-images/2.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
