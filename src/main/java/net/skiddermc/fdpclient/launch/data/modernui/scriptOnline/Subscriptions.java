package net.skiddermc.fdpclient.launch.data.modernui.scriptOnline;

import net.skiddermc.fdpclient.FDPClient;

import java.util.ArrayList;
import java.util.List;

public class Subscriptions {
    public static boolean loadingCloud = false;
    public static String tempJs = "";
    public static List<ScriptSubscribe> subscribes = new ArrayList<>();

    public static void addSubscribes(ScriptSubscribe scriptSubscribe) {
        subscribes.add(scriptSubscribe);
        FDPClient.fileManager.getSubscriptsConfig().addSubscripts(scriptSubscribe.url, scriptSubscribe.name);
    }
}
