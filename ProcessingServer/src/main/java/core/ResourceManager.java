package core;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

public class ResourceManager {
    private final HashMap<String, String> resources;
    private final Object resourcesLock;
    private final ClassLoader classLoader;
    public ResourceManager() {
        resources = new HashMap<>();
        resourcesLock = new Object();
        classLoader = this.getClass().getClassLoader();
    }

    public String getResource(String resourceName) throws IOException {
        if (!resources.containsKey(resourceName)) {
            synchronized (resourcesLock) {
                if (!resources.containsKey(resourceName)) {
                    try (InputStream inputStream = classLoader.getResourceAsStream(resourceName)) {
                        byte[] bytes = IOUtils.toByteArray(Objects.requireNonNull(inputStream));
                        String resourceValue = new String(bytes, StandardCharsets.UTF_8);
                        resources.put(resourceName, resourceValue);
                    }
                }
            }
        }
        return resources.get(resourceName);
    }
}
