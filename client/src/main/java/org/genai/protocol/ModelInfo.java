package org.genai.protocol;

import java.util.List;


public class ModelInfo {

    public final List<Model> models;

    public ModelInfo(List<Model> models) {
        this.models = models;
    }


    public static class Model {
        public final String name;
        public final String provider;

        public Model(String name, String provider) {
            this.name = name;
            this.provider = provider;
        }

        @Override
        public String toString() {
            return name + " (" + provider + ")";

        }
    }


}
