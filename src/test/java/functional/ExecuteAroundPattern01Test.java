package functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExecuteAroundPattern01Test {

    @Test
    @DisplayName("Process file 1 no functional test")
    public void processFile1() {
        try {
            BufferedReader br = mock(BufferedReader.class);
            when(br.readLine()).thenReturn("line 1").thenReturn("line 2");
            assertEquals("line 1", ExecuteAroundPattern01.processFile1(br));
            verify(br, times(1)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Process file 2 one line functional test")
    public void processFile2OneLine() {
        try {
            BufferedReader brMocked = mock(BufferedReader.class);
            when(brMocked.readLine()).thenReturn("line 1").thenReturn("line 2");

            String oneLine = ExecuteAroundPattern01.processFile2(brMocked,
                    (BufferedReader br) -> br.readLine());
            assertEquals("line 1", oneLine);

            verify(brMocked, times(1)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Process file 2 two lines functional test")
    public void processFile2TwoLines() {
        try {
            BufferedReader brMocked = mock(BufferedReader.class);
            when(brMocked.readLine()).thenReturn("line 1").thenReturn("line 2");

            String twoLines = ExecuteAroundPattern01.processFile2(brMocked,
                    (BufferedReader br) -> br.readLine() + br.readLine());
            assertEquals("line 1line 2", twoLines);

            verify(brMocked, times(2)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
