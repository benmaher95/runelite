import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine.Info;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bc")
@Implements("DevicePcmPlayer")
public class DevicePcmPlayer extends PcmPlayer {
	@ObfuscatedName("n")
	@Export("format")
	AudioFormat format;
	@ObfuscatedName("v")
	@Export("line")
	SourceDataLine line;
	@ObfuscatedName("d")
	@ObfuscatedGetter(
		intValue = 1895572259
	)
	@Export("capacity2")
	int capacity2;
	@ObfuscatedName("c")
	@Export("byteSamples")
	byte[] byteSamples;

	DevicePcmPlayer() {
	} // L: 18

	@ObfuscatedName("n")
	@ObfuscatedSignature(
		descriptor = "(B)V",
		garbageValue = "0"
	)
	@Export("init")
	protected void init() {
		this.format = new AudioFormat((float)StructComposition.field3338, 16, PcmPlayer.PcmPlayer_stereo ? 2 : 1, true, false); // L: 21
		this.byteSamples = new byte[256 << (PcmPlayer.PcmPlayer_stereo ? 2 : 1)]; // L: 22
	} // L: 23

	@ObfuscatedName("v")
	@ObfuscatedSignature(
		descriptor = "(II)V",
		garbageValue = "-1929759603"
	)
	@Export("open")
	protected void open(int var1) throws LineUnavailableException {
		try {
			Info var2 = new Info(SourceDataLine.class, this.format, var1 << (PcmPlayer.PcmPlayer_stereo ? 2 : 1)); // L: 27
			this.line = (SourceDataLine)AudioSystem.getLine(var2); // L: 28
			this.line.open(); // L: 29
			this.line.start(); // L: 30
			this.capacity2 = var1; // L: 31
		} catch (LineUnavailableException var3) { // L: 33
			if (PrivateChatMode.method6104(var1) != 1) { // L: 34
				this.open(GameObject.method3452(var1)); // L: 35
			} else {
				this.line = null; // L: 38
				throw var3; // L: 39
			}
		}
	} // L: 36 41

	@ObfuscatedName("d")
	@ObfuscatedSignature(
		descriptor = "(S)I",
		garbageValue = "6543"
	)
	@Export("position")
	protected int position() {
		return this.capacity2 - (this.line.available() >> (PcmPlayer.PcmPlayer_stereo ? 2 : 1)); // L: 44
	}

	@ObfuscatedName("c")
	@Export("write")
	protected void write() {
		int var1 = 256; // L: 49
		if (PcmPlayer.PcmPlayer_stereo) { // L: 50
			var1 <<= 1;
		}

		for (int var2 = 0; var2 < var1; ++var2) { // L: 51
			int var3 = super.samples[var2]; // L: 52
			if ((var3 + 8388608 & -16777216) != 0) { // L: 53
				var3 = 8388607 ^ var3 >> 31; // L: 54
			}

			this.byteSamples[var2 * 2] = (byte)(var3 >> 8); // L: 56
			this.byteSamples[var2 * 2 + 1] = (byte)(var3 >> 16); // L: 57
		}

		this.line.write(this.byteSamples, 0, var1 << 1); // L: 59
	} // L: 60

	@ObfuscatedName("y")
	@ObfuscatedSignature(
		descriptor = "(B)V",
		garbageValue = "64"
	)
	@Export("close")
	protected void close() {
		if (this.line != null) { // L: 63
			this.line.close(); // L: 64
			this.line = null; // L: 65
		}

	} // L: 67

	@ObfuscatedName("h")
	@ObfuscatedSignature(
		descriptor = "(I)V",
		garbageValue = "-674709942"
	)
	@Export("discard")
	protected void discard() {
		this.line.flush(); // L: 70
	} // L: 71
}
