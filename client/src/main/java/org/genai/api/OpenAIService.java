package org.genai.api;

import org.genai.protocol.OpenAIEmbedding;
import org.genai.protocol.OpenAIEmbedding.OpenAIEmbeddingReply;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIService {

    @POST("v1/embeddings")
    @Headers({"Content-Type: application/json"})
    Call<OpenAIEmbeddingReply> embedding(@Header("Authorization") String apiKey, @Body OpenAIEmbedding embedding);
}
