package team.teampotato.IFMODLOADWARN;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IFMODLOADWARN implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("ifmodloadboom");

	@Override
	public void onInitialize() {
		/*
		部分-1
		JSON生成
		 */
		// 获取配置文件夹路径
		File configDirectory = new File(FabricLoader.getInstance().getConfigDir().toFile(), "ifmodloadwarnConfig");

// 如果文件夹不存在则创建文件夹
		if (!configDirectory.exists()) {
			configDirectory.mkdirs();
		}

// 获取配置文件
		File configFile = new File(configDirectory, "IFMODLOADWARN.json");

// 如果配置文件不存在，则创建并写入默认配置
		if (!configFile.exists()) {
			try {
				// 默认配置
				JsonObject defaultConfig = new JsonObject();
				JsonArray modList = new JsonArray();
				modList.add("BlackMod-ID");
				defaultConfig.add("WarnModlist", modList);

				// 将 JSON 字符串写入文件
				FileWriter writer = new FileWriter(configFile);
				writer.write(defaultConfig.toString());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

/*
部分-2
读取黑名单并且崩溃
 */
		Path configPath = FabricLoader.getInstance().getConfigDir().resolve("ifmodloadwarnConfig");
		File configFile2 = new File(configPath.toFile(), "IFMODLOADWARN.json");

		if (configFile2.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
				// 将配置文件解析为JsonObject
				JsonObject config = new JsonParser().parse(reader).getAsJsonObject();

				// 获取要检查的Mod列表
				JsonArray modList = config.getAsJsonArray("WarnModlist");

				// 检查列表中的每个Mod是否已加载
				for (JsonElement modElement : modList) {
					String modId = modElement.getAsString();
					List<String> blacklistedMods = new ArrayList<>();

					if (FabricLoader.getInstance().isModLoaded(modId)) {
						//String message = String.format("You have loaded a blacklisted mod: %s. Click to exit Minecraft.", modId);
						MinecraftClient.getInstance().execute(() -> {
							MinecraftClient.getInstance().setScreen(new GUIS.GUI2());
							blacklistedMods.add(modId);
						});


						break;
					}

				}
			} catch (IOException e) {
				LOGGER.error("Failed to read config file", e);
			}
		}

		LOGGER.info("hElLo!iFmOdLoAdBoOmMoD! mOdLoAdEr!");
	}




	public static List<String> modId() {
		Path configPath = FabricLoader.getInstance().getConfigDir().resolve("ifmodloadwarnConfig");
		File configFile = new File(configPath.toFile(), "IFMODLOADWARN.json");

		List<String> modIdList = new ArrayList<>();

		if (configFile.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
				JsonObject config = new JsonParser().parse(reader).getAsJsonObject();
				JsonArray modList = config.getAsJsonArray("WarnModlist");

				for (JsonElement modElement : modList) {
					String modId = modElement.getAsString();
					if (FabricLoader.getInstance().isModLoaded(modId)) {
						modIdList.add(modId);
					}
				}
			} catch (IOException e) {
				LOGGER.error("Failed to read config file", e);
			}
		}
		return modIdList;
	}

}