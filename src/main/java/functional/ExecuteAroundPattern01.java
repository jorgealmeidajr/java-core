package functional;

import java.io.BufferedReader;
import java.io.IOException;

public class ExecuteAroundPattern01 {

    public static String processFile1(BufferedReader br) throws IOException {
        return br.readLine();
    }

    @FunctionalInterface
    interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    public static String processFile2(BufferedReader br, BufferedReaderProcessor p) throws IOException {
        return p.process(br);
    }

}
