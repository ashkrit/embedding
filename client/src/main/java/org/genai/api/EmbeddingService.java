package org.genai.api;

import org.genai.api.Embedding.EmbeddingReply;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EmbeddingService {
    @GET("list")
    Call<ModelInfo> listModels();

    @POST("embedding")
    Call<EmbeddingReply> embedding(@Body Embedding embedding);
}
