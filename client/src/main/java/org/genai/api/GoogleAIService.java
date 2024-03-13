package org.genai.api;

import org.genai.protocol.GoogleEmbedding;
import org.genai.protocol.GoogleEmbedding.GoogleEmbeddingReply;
import retrofit2.Call;
import retrofit2.http.*;

public interface GoogleAIService {

    @POST("/v1beta/models/embedding-001:embedContent")
    @Headers({"Content-Type: application/json"})
    Call<GoogleEmbeddingReply> embedding(@Query("key") String apiKey, @Body GoogleEmbedding embedding);

}
