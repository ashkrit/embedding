package org.genai;


import org.genai.api.Embedding;
import org.genai.api.Embedding.EmbeddingReply;
import org.genai.api.EmbeddingService;
import org.genai.api.ModelInfo;
import org.genai.api.TypeAdapter;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" http://127.0.0.1:9090/")
                .addConverterFactory(TypeAdapter.FACTORY)
                .build();

        EmbeddingService service = retrofit.create(EmbeddingService.class);

        Call<ModelInfo> x = service.listModels();

        ModelInfo i = x.execute().body();
        i.models.forEach(System.out::println);
        System.out.println(i.models);

        Embedding text = new Embedding("google", "embedding-001", "How are you");
        Call<EmbeddingReply> y = service.embedding(text);
        EmbeddingReply reply = y.execute().body();

        System.out.println(reply.len);
    }
}
