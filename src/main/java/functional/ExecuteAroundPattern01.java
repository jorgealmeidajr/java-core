package functional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAroundPattern01 {

    public String processFile1() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    @FunctionalInterface
    private static interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    public static String processFile2(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }

    public static void main(String[] args) {
        try {
            String oneLine = processFile2((BufferedReader br) -> br.readLine());
            String twoLines = processFile2((BufferedReader br) -> br.readLine() + br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
