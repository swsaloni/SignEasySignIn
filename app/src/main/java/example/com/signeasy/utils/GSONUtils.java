package example.com.signeasy.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Saloni on 5/5/2016.
 */

    public class GSONUtils {

        private static GSONUtils instance = new GSONUtils();
        private Gson gson;

        private GSONUtils() {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }

        public static GSONUtils getInstance() {
            return instance;
        }

        public Gson getGson(){
            return this.gson;
        }
    }

