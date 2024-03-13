package org.genai.api;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class ModelInfoAdapter implements Converter<ResponseBody, ModelInfo> {

    public static final Converter.Factory FACTORY =
            new Converter.Factory() {
                @Override
                public @Nullable Converter<ResponseBody, ?> responseBodyConverter(
                        Type type, Annotation[] annotations, Retrofit retrofit) {
                    if (type == ModelInfo.class) return new ModelInfoAdapter();
                    return null;
                }
            };

    @Override
    public ModelInfo convert(ResponseBody responseBody) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(responseBody.string(), ModelInfo.class);
    }
}
