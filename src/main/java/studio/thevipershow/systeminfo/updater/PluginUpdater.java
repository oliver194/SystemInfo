package studio.thevipershow.systeminfo.updater;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import studio.thevipershow.systeminfo.SystemInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public final class PluginUpdater {

    private static PluginUpdater instance = null;
    private final SystemInfo plugin;
    private final VersionData currentVersion;
    private VersionCompareResult lastCompareResult = null;
    private Long lastCompareTime = null;

    private PluginUpdater(SystemInfo plugin) {
        this.plugin = plugin;
        this.currentVersion = parseString(plugin.getDescription().getVersion());
    }

    public static synchronized PluginUpdater getInstance(SystemInfo plugin) {
        if (instance == null) {
            instance = new PluginUpdater(plugin);
        }
        return instance;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public static final class VersionData {
        private final int major;
        private final int minor;
        private final int patch;

        public VersionData(int major, int minor, int patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }

        public final int getMajor() {
            return major;
        }

        public final int getMinor() {
            return minor;
        }

        public final int getPatch() {
            return patch;
        }

        @Override
        public final String toString() {
            return major + "." + minor + "." + patch;
        }
    }

    public static final class VersionCompareResult {
        private final VersionData currentCompared;
        private final VersionData latestCompared;
        private final boolean isOutdated;

        public VersionCompareResult(VersionData currentCompared, VersionData latestCompared, boolean isOutdated) {
            this.currentCompared = currentCompared;
            this.latestCompared = latestCompared;
            this.isOutdated = isOutdated;
        }

        public VersionData getCurrentCompared() {
            return currentCompared;
        }

        public VersionData getLatestCompared() {
            return latestCompared;
        }

        public boolean isOutdated() {
            return isOutdated;
        }
    }

    public interface VersionCheckHandler {

        void onResult(VersionCompareResult compareResult);

        void onError(Exception e);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public final static String REQUEST_URL = "https://api.spigotmc.org/simple/0.1/index.php?action=getResource&id=70905";

    public final static Pattern VERSION_PATTERN = Pattern.compile("^[0-9]+\\.[0-9]+\\.[0-9]+$");

    private final Set<VersionCheckHandler> handlers = new HashSet<>(1);

    public final boolean addHandler(VersionCheckHandler handler) {
        return this.handlers.add(handler);
    }

    public final boolean removeHandler(VersionCheckHandler handler) {
        return this.handlers.remove(handler);
    }

    public static VersionData parseString(String version) throws IllegalArgumentException {
        final Matcher result = VERSION_PATTERN.matcher(version);
        if (!result.matches()) {
            throw new IllegalArgumentException("Invalid version scheme -> " + version);
        }
        final String[] stringDigits = version.split("\\.");
        final int[] realDigits = new int[3];
        for (int i = 0; i < 3; i++) {
            realDigits[i] = Integer.parseInt(stringDigits[i]);
        }
        return new VersionData(realDigits[0], realDigits[1], realDigits[2]);
    }

    public final boolean isCurrentVersionLower(VersionData latestVersion) {
        if (currentVersion.major >= latestVersion.major) {
            if (currentVersion.minor >= latestVersion.minor) {
                return currentVersion.patch > latestVersion.patch;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private final OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(1L, TimeUnit.SECONDS)
            .build();

    public final void performVersionChecks() {
        if (lastCompareResult != null && lastCompareTime != null && System.currentTimeMillis() - lastCompareTime <= TimeUnit.HOURS.toMillis(12L)) {
            this.handlers.forEach(handler -> handler.onResult(this.lastCompareResult));
            return;
        }

        final Request req = new Request.Builder()
                .url(REQUEST_URL)
                .header("User-Agent", "SystemInfo-Version-Fetcher")
                .get()
                .build();

        client.newCall(req).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final JsonObject jsonObject = new JsonParser().parse(new String(response.body().bytes(), (StandardCharsets.UTF_8))).getAsJsonObject();
                final String currentVersionStr = jsonObject.get("current_version").getAsString();
                final VersionData latestVersion = parseString(currentVersionStr);
                final VersionCompareResult compareResult = new VersionCompareResult(currentVersion, latestVersion, !isCurrentVersionLower(latestVersion));
                setLastCompareResult(Objects.requireNonNull(compareResult, "The compareResult is null"));
                setLastCompareTime(System.currentTimeMillis());
                handlers.forEach(handler -> handler.onResult(compareResult));
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handlers.forEach(handler -> handler.onError(e));
            }
        });
    }

    public final VersionCompareResult getLastCompareResult() {
        return lastCompareResult;
    }

    public final Long getLastCompareTime() {
        return lastCompareTime;
    }

    private void setLastCompareResult(VersionCompareResult lastCompareResult) {
        this.lastCompareResult = lastCompareResult;
    }

    private void setLastCompareTime(Long lastCompareTime) {
        this.lastCompareTime = lastCompareTime;
    }
}
