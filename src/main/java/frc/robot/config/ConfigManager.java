package frc.robot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.wpilibj.Filesystem;
import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static final String CONFIG_DIR = "config";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T loadConfig(String filename, Class<T> valueType) {
        try {
            File file = new File(Filesystem.getDeployDirectory(), CONFIG_DIR + "/" + filename);
            return mapper.readValue(file, valueType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
