package utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvReader {

    private static final Dotenv dotenv = Dotenv.configure()
                                                .directory("./")
                                                .ignoreIfMalformed()
                                                .ignoreIfMissing()
                                                .load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}