package li.koly.io;

import java.io.*;

public class IoApp {
    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream(new File("/home/koly/program/mybatis-prac/src/main/java/li/koly/io/test.txt"));
            InputStreamReader reader = new InputStreamReader(inputStream);
            FileReader fileReader = new FileReader("/home/koly/program/mybatis-prac/src/main/java/li/koly/io/test.txt");
            BufferedReader bf = new BufferedReader(fileReader);
            String line = null;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
