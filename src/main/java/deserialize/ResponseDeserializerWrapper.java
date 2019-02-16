package deserialize;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.logging.Logger;

public class ResponseDeserializerWrapper {
    private final static Logger logger = Logger.getLogger(ResponseDeserializerWrapper.class.getSimpleName());
    private final Gson gson;

    public ResponseDeserializerWrapper(final Gson gson) {
        this.gson = gson;
    }

    public <T> T deserialize(final String response, final ResponseDeserializer<T> deserializer) {
        validateStatus(response);
        return deserializer.deserialize(response);
    }

    private void validateStatus(final String response) {
        final JsonObject obj = gson.fromJson(response, JsonObject.class);
        logger.info(obj.toString());
        if (obj.get("status").getAsJsonObject().get("health").getAsInt() != 1) {
            throw new RuntimeException("Status is not OK");
        }
    }
}
