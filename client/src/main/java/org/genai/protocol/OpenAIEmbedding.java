package org.genai.protocol;

import java.util.List;

public class OpenAIEmbedding {
    public final String model;
    public final String input;


    public OpenAIEmbedding(String model, String input) {
        this.model = model;
        this.input = input;
    }

    public static class OpenAIEmbeddingReply {

        public final List<EmbeddingValue> data;

        public OpenAIEmbeddingReply(List<EmbeddingValue> data) {
            this.data = data;
        }

        public static class EmbeddingValue {
            public final float[] embedding;

            public EmbeddingValue(float[] embedding) {
                this.embedding = embedding;
            }
        }
    }
}
