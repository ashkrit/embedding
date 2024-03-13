package org.genai.api;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class TypeAdapter {

    public static final Converter.Factory FACTORY =
            new Converter.Factory() {
                @Override
                public @Nullable Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

                    if (type == ModelInfo.class) {
                        return (r) -> {
                            Gson g = new Gson();
                            return g.fromJson(r.string(), ModelInfo.class);
                        };

                    }
                    return null;
                }
            };

}
