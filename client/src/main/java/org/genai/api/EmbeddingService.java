package org.genai.api;

import org.genai.protocol.Embedding;
import org.genai.protocol.Embedding.EmbeddingReply;
import org.genai.protocol.ModelInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EmbeddingService {
    @GET("list")
    @Headers({"Content-Type: application/json"})
    Call<ModelInfo> listModels();

    @POST("embedding")
    @Headers({"Content-Type: application/json"})
    Call<EmbeddingReply> embedding(@Body Embedding embedding);
}
