package org.genai;


import org.genai.api.*;
import org.genai.protocol.Embedding;
import org.genai.protocol.Embedding.EmbeddingReply;
import org.genai.protocol.GoogleEmbedding;
import org.genai.protocol.GoogleEmbedding.GoogleEmbeddingReply;
import org.genai.protocol.ModelInfo;
import org.genai.protocol.OpenAIEmbedding;
import org.genai.protocol.OpenAIEmbedding.OpenAIEmbeddingReply;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws IOException {

        //_local();
        //_google();
        _openAI();
    }

    private static void _local() throws IOException {
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

    private static void _openAI() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(TypeAdapter.FACTORY)
                .build();

        OpenAIService openAIService = retrofit.create(OpenAIService.class);
        String q = System.getenv("gpt_key");
        OpenAIEmbedding openAIEmbedding = new OpenAIEmbedding("text-embedding-3-small", "How are you");
        Response<OpenAIEmbeddingReply> execute = openAIService.embedding("Bearer " + q, openAIEmbedding).execute();

        OpenAIEmbeddingReply er1 = execute.body();

        System.out.println(Arrays.toString(er1.data.get(0).embedding));
    }

    private static void _google() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://generativelanguage.googleapis.com/")
                .addConverterFactory(TypeAdapter.FACTORY)
                .build();
        GoogleAIService googleAiService = retrofit.create(GoogleAIService.class);

        String q = System.getenv("gemma_key");

        GoogleEmbedding.ModelPart part = new GoogleEmbedding.ModelPart("how are you");
        GoogleEmbedding.ModelContent content = new GoogleEmbedding.ModelContent(Collections.singletonList(part));
        GoogleEmbedding embedding = new GoogleEmbedding("models/embedding-001", content);

        GoogleEmbeddingReply er1 = googleAiService.embedding(q, embedding).execute().body();

        System.out.println(Arrays.toString(er1.embedding.values));
    }
}
