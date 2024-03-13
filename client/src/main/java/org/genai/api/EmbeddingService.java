package org.genai.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmbeddingService {
    @GET("list")
    Call<ModelInfo> listModels();
}
