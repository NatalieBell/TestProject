import jsonschemas.OutputResult;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assume.assumeTrue;
import static performers.MethodToCheckDataFromFile.checkFileData;
import static performers.MethodToCheckDataFromWeb.checkWebData;

public class Tests {

    protected void checkResponseParameters(int expectedCode, int expectedSymbolsCount, OutputResult actualResult) {
        assertEquals("Код завершения операции (code) не совпадает с ожидаемым:", expectedCode, actualResult.getCode());
        assertEquals("Количество символов (symbols_count) не совпадает с ожидаемым:", expectedSymbolsCount, actualResult.getSymbolsCount());
    }

    protected void checkResponseParameters(int expectedCode, boolean isFileCreated, OutputResult actualResult) {
        assertEquals("Код завершения операции (code) не совпадает с ожидаемым:", expectedCode, actualResult.getCode());
        assertEquals("После подсчета символов файл не создан:", isFileCreated, actualResult.isFileCreated());
    }

    @Test
    public void positiveCheckFileIsCreatedWeb() throws Exception {

        OutputResult result = checkWebData("path_to_web");
        checkResponseParameters(0, true, result);
    }

    @Test
    public void positiveCheckCorrectCountWeb() throws Exception {
        int expectedValue = 10;

        OutputResult result = checkWebData("path_to_web");
        checkResponseParameters(0, expectedValue, result);
    }

    @Test
    public void positiveCheckFileContainsAllData() throws Exception {
        int expectedValue = 10;

        assumeTrue("При подготовке теста не удалось записать результат в файл",
                checkWebData("path_to_web").successful());
        OutputResult result = checkFileData("path_to_file");
        checkResponseParameters(0, expectedValue, result);
    }

    @Test
    public void positiveCheckCorrectCountFile() throws Exception {
        int expectedValue = 10;

        OutputResult result = checkFileData("path_to_file");
        checkResponseParameters(0, expectedValue, result);
    }
}
