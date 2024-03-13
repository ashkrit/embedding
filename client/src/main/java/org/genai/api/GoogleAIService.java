package org.genai.api;

import org.genai.protocol.GoogleEmbedding;
import org.genai.protocol.GoogleEmbedding.GoogleEmbeddingReply;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GoogleAIService {

    @POST("/v1beta/models/embedding-001:embedContent")
    Call<GoogleEmbeddingReply> embedding(@Query("key") String apiKey, @Body GoogleEmbedding embedding);

}
