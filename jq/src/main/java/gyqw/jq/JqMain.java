package gyqw.jq;

import com.arakelian.jq.*;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * @author fred
 * 2019-05-24 5:35 PM
 */
public class JqMain {
    public static void main(String[] args) {
        JqMain jqMain = new JqMain();
        jqMain.foreachParseJsonNet();
    }

    private void foreachParseJsonNet() {
        for (int i = 0; i < 10000; i++) {
            try {
                long start = new Date().getTime();
                parseNetJson();
                System.out.println(i);
                System.out.println(new Date().getTime() - start);
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseNetJson() {
        try {
            // 读取json
            String json = readJson();
            JSONObject jsonObject = JSONObject.fromObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void foreachParseJsonJQ() {
        for (int i = 0; i < 10000; i++) {
            try {
                parseJsonJQ();
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJsonJQ() {
        long start = new Date().getTime();

        // 读取json
        String json = readJson();

        // 处理json
        JqLibrary library = ImmutableJqLibrary.of();
        final JqRequest request = ImmutableJqRequest.builder()
                .lib(library)
                .input(json)
                .filter(".raw_data.members.transactions[0].calls")
                .build();

        final JqResponse response = request.execute();
        if (!response.hasErrors()) {
            System.out.println("JQ output: " + response.getOutput());
            System.out.println("JQ output raw length: " + json.length());
            System.out.println("JQ output length: " + response.getOutput().length());
        }

        System.out.println(new Date().getTime() - start);
    }

    private String readJson() {
        // 读取json
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            FileInputStream fileInputStream = new FileInputStream("/home/fred/Downloads/jq-2mb-test-format.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
