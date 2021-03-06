package eurymachus.mts.core;

import java.io.File;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.Configuration;
import slimevoid.lib.ICommonProxy;
import slimevoid.lib.ICore;
import slimevoid.lib.core.Core;
import slimevoid.lib.core.SlimevoidCore;
import eurymachus.mts.tileentities.TileEntityMTSign;

public class MTSInit {
	public static ICore MTS;
	public static boolean initialized = false;

	public static String woodenSign = "/item/sign.png";
	public static String ironSign = "item/mtsIronSign.png";
	public static String goldSign = "item/mtsGoldSign.png";
	public static String diamondSign = "item/mtsDiamondSign.png";

	public static String getSignTexture(int textureData) {
		switch (textureData) {
		case 0:
			return woodenSign;
		case 1:
			return MTS.getModDir() + ironSign;
		case 2:
			return MTS.getModDir() + goldSign;
		case 3:
			return MTS.getModDir() + diamondSign;
		default:
			return MTS.getModDir() + ironSign;
		}
	}

	public static void initialize(ICommonProxy proxy) {
		if (initialized)
			return;
		initialized = true;
		MTS = new Core(proxy);
		MTS.setModName("MultiTexturedSigns");
		MTS.setModChannel("MTS");
		MTSCore.configFile = new File(
				MTSInit.MTS.getProxy().getMinecraftDir(),
					"config/MultiTexturedSigns.cfg");
		MTSCore.configuration = new Configuration(MTSCore.configFile);
		load();
	}

	public static void load() {
		SlimevoidCore.console(MTS.getModName(), "Registering items...");
		MTSCore.addItems();
		SlimevoidCore.console(MTS.getModName(), "Registering blocks...");
		MTSCore.registerBlocks();
		MTS.getProxy().registerRenderInformation();
		MTS
				.getProxy()
					.registerTileEntitySpecialRenderer(TileEntityMTSign.class);
		SlimevoidCore.console(MTS.getModName(), "Naming items...");
		MTSCore.addItemNames();
		SlimevoidCore.console(MTS.getModName(), "Registering recipes...");
		MTSCore.addRecipes();
	}

	public static int getDamageValue(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntity tileentity = blockAccess.getBlockTileEntity(x, y, z);
		if (tileentity != null && tileentity instanceof TileEntityMTSign) {
			TileEntityMTSign tileentitymtsign = (TileEntityMTSign) tileentity;
			return tileentitymtsign.getTextureValue();
		}
		return 0;
	}
}
