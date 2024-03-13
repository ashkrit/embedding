package org.genai.api;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
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
                    return (r) -> {
                        Gson g = new Gson();
                        return g.fromJson(r.string(), type);
                    };
                }


                @Nullable
                public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations,
                                                                      Retrofit retrofit) {


                    return (r) -> {
                        Gson g = new Gson();
                        return RequestBody.create(MediaType.parse("application/json"), g.toJson(r));
                    };
                }


            };

}
