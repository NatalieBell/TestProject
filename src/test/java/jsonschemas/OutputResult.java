package jsonschemas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import java.io.IOException;

public class OutputResult {

    @JsonProperty @Getter
    int code;
    @JsonProperty @Getter
    int symbolsCount;
    @JsonProperty @Getter
    boolean isFileCreated;

    public boolean successful() { return code == 0 && isFileCreated == true; }

    public static OutputResult parse(String input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(input, OutputResult.class);
    }

    @JsonCreator
    OutputResult(@JsonProperty(value = "code", required = true) int code,
                 @JsonProperty(value = "symbols_count") int symbolsCount,
                 @JsonProperty(value = "is_file_created") boolean isFileCreated) {
        this.code = code;
        this.symbolsCount = symbolsCount;
        this.isFileCreated = isFileCreated;
    }
}
