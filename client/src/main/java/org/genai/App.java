package org.genai;


import org.genai.api.*;
import org.genai.protocol.Embedding;
import org.genai.protocol.Embedding.EmbeddingReply;
import org.genai.protocol.GoogleEmbedding;
import org.genai.protocol.ModelInfo;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

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


        retrofit = new Retrofit.Builder()
                .baseUrl("https://generativelanguage.googleapis.com/")
                .addConverterFactory(TypeAdapter.FACTORY)
                .build();
        GoogleAIService googleAiService = retrofit.create(GoogleAIService.class);

        String q = System.getenv("gemma_key");

        GoogleEmbedding.ModelPart part = new GoogleEmbedding.ModelPart("how are you");
        GoogleEmbedding.ModelContent content = new GoogleEmbedding.ModelContent(Collections.singletonList(part));
        GoogleEmbedding embedding = new GoogleEmbedding("models/embedding-001", content);

        GoogleEmbedding.GoogleEmbeddingReply er1 = googleAiService.embedding(q, embedding).execute().body();

        System.out.println(Arrays.toString(er1.embedding.values));
    }
}
