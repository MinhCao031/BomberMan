package Bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {
	public final int SIZE_WIDTH;
	public final int SIZE_HEIGHT;
	public int[] _pixels;
	public BufferedImage image;

/* My version */

	public static SpriteSheet tiles = new SpriteSheet(256);
	public static SpriteSheet newTiles = new SpriteSheet(256, 160);

	public SpriteSheet(int size) {
		this.SIZE_WIDTH = size;
		this.SIZE_HEIGHT = size;
		this._pixels = new int[SIZE_WIDTH * SIZE_WIDTH];
		load("file:res/textures/classic.png");
	}

	public SpriteSheet(int width, int height) {
		this.SIZE_WIDTH = width;
		this.SIZE_HEIGHT = height;
		this._pixels = new int[SIZE_WIDTH * SIZE_HEIGHT];
		load("file:res/textures/enemy.png");
	}

	private void load(String URL) {
		try {
			image = ImageIO.read(new URL(URL));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, _pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(123);
		}
	}

/* Original Version (doesn't work on my device) */

//  private String _path;
// 	public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);
//	public SpriteSheet(String path, int size) {
//		_path = path;
//		SIZE = size;
//		_pixels = new int[SIZE * SIZE];
//		load();
//	}
//
//	private void load() {
//		try {
//			URL a = SpriteSheet.class.getResource(_path);
//			image = ImageIO.read(a);
//			int w = image.getWidth();
//			int h = image.getHeight();
//			image.getRGB(0, 0, w, h, _pixels, 0, w);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.exit(0);
//		}
//	}
}
