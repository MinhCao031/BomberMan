package Bomberman.graphics;

import Bomberman.X;
import javafx.scene.image.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class Sprite {
	private static final int TRANSPARENT_COLOR = 0xffff00ff;
	public static final int DEFAULT_SIZE = 16;
	public static final int SCALED_SIZE = DEFAULT_SIZE * 2;

	private SpriteSheet _sheet;
	private int _x, _y;
	protected int _realWidth;
	protected int _realHeight;
	public final int SIZE;
	public int[] _pixels;

	public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		_x = x * SIZE;
		_y = y * SIZE;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		Arrays.fill(_pixels, color);
	}

	private void load() {
		for (int y = 0; y < SIZE; ++y) {
			System.arraycopy(_sheet._pixels,  _x + (y + _y) * _sheet.SIZE_WIDTH, _pixels, y * SIZE, SIZE);
		}
	}

	/**
	 * Ảnh động tạo bởi nhiều Sprite luân phiên nhau theo thứ tự nhập vào, chỉ hiện ảnh trong 1 chu kỳ.
	 * @param timeOfEachSprite thời gian xuất hiện của mỗi Sprite, đơn vị miliSecond.
	 * @param timeStart thời điểm Sprite đầu tiên xuất hiện.
	 * @param sprites các Sprite với số lượng tùy ý.
	 * @return Sprite theo thứ tự nhập vào.
	 */
	public static Sprite movingSprite1PeriodOnly(int timeOfEachSprite, int timeStart, Sprite... sprites) {
		Sprite[] s = sprites.clone();
		int numOfSprites = s.length;
		int timePeriod = timeOfEachSprite * numOfSprites;
		int calc = X.currentTime - timeStart;

		int index = (calc < timePeriod? calc / timeOfEachSprite : numOfSprites - 1);
		return s[index];
	}

	/**
	 * Ảnh động tạo bởi nhiều Sprite luân phiên nhau theo thứ tự nhập vào.
	 * @param timeOfEachSprite thời gian xuất hiện của mỗi Sprite, đơn vị miliSecond.
	 * @param sprites các Sprite với số lượng tùy ý.
	 * @return Sprite nào đó.
	 */
	public static Sprite movingSprite(int timeOfEachSprite, Sprite... sprites) {
		Sprite[] s = sprites.clone();
		int numOfSprites = s.length;
		int timePeriod = timeOfEachSprite * numOfSprites;
		int calc = X.currentTime % timePeriod;

		//System.out.println(calc + "\t" + timePeriod + "\t" + calc/timeOfEachSprite);
		return s[calc / timeOfEachSprite];
	}
	
	public int getSize() {
		return SIZE;
	}

	public int getPixel(int i) {
		return _pixels[i];
	}

	/**
	 * Tạo ảnh từ Sprite.
	 * @return ảnh để GraphicContext có thể vẽ được.
	 */
	public Image getFxImage() {
        WritableImage wr = new WritableImage(SIZE, SIZE);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                if ( _pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, _pixels[x + y * SIZE]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return resample(input, SCALED_SIZE / this.SIZE);
    }

	/**
	 * phóng đại ảnh theo hệ số scaleFactor.
	 * @param input ảnh ban đầu (16*16).
	 * @param scaleFactor hệ số phóng đại.
	 * @return ảnh sau khi phóng đại (32*32).
	 */
    private Image resample(Image input, int scaleFactor) {
		if(scaleFactor == 1) return input;

		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(W * S,H * S);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; ++y) {
			for (int x = 0; x < W; ++x) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; ++dy) {
					for (int dx = 0; dx < S; ++dx) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}
		return output;
	}

	/**
	 * New Enemies
	 */
	public static Sprite[] custom_left3 = {
			new Sprite(SCALED_SIZE, 6, 0, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 6, 1, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 6, 2, SpriteSheet.newTiles, 32, 32),};
	public static Sprite[] custom_right3 = {
			new Sprite(SCALED_SIZE, 7, 0, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 7, 1, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 7, 2, SpriteSheet.newTiles, 32, 32),};
	public static Sprite[] custom_dead3 = {
			new Sprite(SCALED_SIZE, 6, 3, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 7, 3, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 6, 4, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 7, 4, SpriteSheet.newTiles, 32, 32),};

	public static Sprite[] custom_left2 = {
			new Sprite(SCALED_SIZE, 4, 0, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 4, 1, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 4, 2, SpriteSheet.newTiles, 32, 32),};
	public static Sprite[] custom_right2 = {
			new Sprite(SCALED_SIZE, 5, 0, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 5, 1, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 5, 2, SpriteSheet.newTiles, 32, 32),};
	public static Sprite[] custom_dead2 = {
			new Sprite(SCALED_SIZE, 4, 3, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 5, 3, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 4, 4, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 5, 4, SpriteSheet.newTiles, 32, 32),};

	public static Sprite[] custom_left1 = {
			new Sprite(SCALED_SIZE, 2, 0, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 2, 1, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 2, 2, SpriteSheet.newTiles, 32, 32),};
	public static Sprite[] custom_right1 = {
			new Sprite(SCALED_SIZE, 3, 0, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 3, 1, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 3, 2, SpriteSheet.newTiles, 32, 32),};
	public static Sprite[] custom_dead1 = {
			new Sprite(SCALED_SIZE, 2, 3, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 3, 3, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 2, 4, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 3, 4, SpriteSheet.newTiles, 32, 32),};

	public static Sprite[] custom_left0 = {
			new Sprite(SCALED_SIZE, 0, 0, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 0, 1, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 0, 2, SpriteSheet.newTiles, 32, 32),};
	public static Sprite[] custom_right0 = {
			new Sprite(SCALED_SIZE, 1, 0, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 1, 1, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 1, 2, SpriteSheet.newTiles, 32, 32),};
	public static Sprite[] custom_dead0 = {
			new Sprite(SCALED_SIZE, 0, 3, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 1, 3, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 0, 4, SpriteSheet.newTiles, 32, 32),
			new Sprite(SCALED_SIZE, 1, 4, SpriteSheet.newTiles, 32, 32),};

	/**
	 *--------------------------------------------------------------------------
	 * Board sprites
	 *-------------------------------------------------------------------------
	 */
	public static Sprite nothing = new Sprite(DEFAULT_SIZE, 3, 3, SpriteSheet.tiles, 16, 16);
	public static Sprite grass = new Sprite(DEFAULT_SIZE, 6, 0, SpriteSheet.tiles, 16, 16);
	public static Sprite brick = new Sprite(DEFAULT_SIZE, 7, 0, SpriteSheet.tiles, 16, 16);
	public static Sprite wall = new Sprite(DEFAULT_SIZE, 5, 0, SpriteSheet.tiles, 16, 16);
	public static Sprite portal = new Sprite(DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 14, 14);

	/**
	 *--------------------------------------------------------------------------
	 * Bomber Sprites
	 *-------------------------------------------------------------------------
	 */
	public static Sprite player_up = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.tiles, 12, 16);
	public static Sprite player_down = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.tiles, 12, 15);
	public static Sprite player_left = new Sprite(DEFAULT_SIZE, 3, 0, SpriteSheet.tiles, 10, 15);
	public static Sprite player_right = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.tiles, 10, 16);

	public static Sprite player_up_1 = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.tiles, 12, 16);
	public static Sprite player_up_2 = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.tiles, 12, 15);

	public static Sprite player_down_1 = new Sprite(DEFAULT_SIZE, 2, 1, SpriteSheet.tiles, 12, 15);
	public static Sprite player_down_2 = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.tiles, 12, 16);

	public static Sprite player_left_1 = new Sprite(DEFAULT_SIZE, 3, 1, SpriteSheet.tiles, 11, 16);
	public static Sprite player_left_2 = new Sprite(DEFAULT_SIZE, 3, 2, SpriteSheet.tiles, 12 ,16);

	public static Sprite player_right_1 = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.tiles, 11, 16);
	public static Sprite player_right_2 = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.tiles, 12, 16);

	public static Sprite player_dead1 = new Sprite(DEFAULT_SIZE, 4, 2, SpriteSheet.tiles, 14, 16);
	public static Sprite player_dead2 = new Sprite(DEFAULT_SIZE, 5, 2, SpriteSheet.tiles, 13, 15);
	public static Sprite player_dead3 = new Sprite(DEFAULT_SIZE, 6, 2, SpriteSheet.tiles, 16, 16);


	/**
	 * Balloon.
	 */
	public static Sprite[] balloom_left = {
			new Sprite(DEFAULT_SIZE, 9, 0, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 9, 1, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 9, 2, SpriteSheet.tiles, 16, 16)
	};
	public static Sprite[] balloom_right = {
			new Sprite(DEFAULT_SIZE, 10, 0, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 10, 1, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 10, 2, SpriteSheet.tiles, 16, 16)
	};
	public static Sprite[] balloom_dead = {
			new Sprite(DEFAULT_SIZE, 9, 3, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 10, 3, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 9, 4, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 10, 4, SpriteSheet.tiles, 16, 16),
	};

	/**
	 * Oneal.
	 */
	public static Sprite[] oneal_left = {
			new Sprite(DEFAULT_SIZE, 11, 0, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 11, 1, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 11, 2, SpriteSheet.tiles, 16, 16)
	};
	public static Sprite[] oneal_right = {
			new Sprite(DEFAULT_SIZE, 12, 0, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 12, 1, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 12, 2, SpriteSheet.tiles, 16, 16)
	};
	public static Sprite[] oneal_dead = {
			new Sprite(DEFAULT_SIZE, 11, 3, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 12, 3, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 11, 4, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 12, 4, SpriteSheet.tiles, 16, 16),
	};

	/**
	 * Bomb's Sprite, from smallest to biggest.
	 */
	public static Sprite[] bomb = {
			new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.tiles, 12, 14),
			new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.tiles, 12, 14),
			new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.tiles, 12, 14)};
	/**
	 * Bomb's center flame, from smallest to biggest.
	 */
	public static Sprite[] bomb_exploded = {
			new Sprite(DEFAULT_SIZE, 0, 4, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 0, 5, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 0, 6, SpriteSheet.tiles, 16, 16)};
	/**
	 * Bomb's vertical flame, from smallest to biggest.
	 */
	public static Sprite[] explosion_vertical = {
			new Sprite(DEFAULT_SIZE, 1, 5, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 2, 5, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 3, 5, SpriteSheet.tiles, 16, 16)};
	/**
	 * Bomb's vertical top last flame, from smallest to biggest.
	 */
	public static Sprite[] explosion_vertical_top_last = {
			new Sprite(DEFAULT_SIZE, 1, 4, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 2, 4, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 3, 4, SpriteSheet.tiles, 16, 16)};
	/**
	 * Bomb's vertical down last flame, from smallest to biggest.
	 */
	public static Sprite[] explosion_vertical_down_last = {
			new Sprite(DEFAULT_SIZE, 1, 6, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 2, 6, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 3, 6, SpriteSheet.tiles, 16, 16)};
	/**
	 * Bomb's horizontal flame, from smallest to biggest.
	 */
	public static Sprite[] explosion_horizontal = {
			new Sprite(DEFAULT_SIZE, 1, 7, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 1, 8, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 1, 9, SpriteSheet.tiles, 16, 16)};
	/**
	 * Bomb's horizontal left last flame, from smallest to biggest.
	 */
	public static Sprite[] explosion_horizontal_left_last = {
			new Sprite(DEFAULT_SIZE, 0, 7, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 0, 8, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 0, 9, SpriteSheet.tiles, 16, 16)};
	/**
	 * Bomb's horizontal left last flame, from smallest to biggest.
	 */
	public static Sprite[] explosion_horizontal_right_last = {
			new Sprite(DEFAULT_SIZE, 2, 7, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 2, 8, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 2, 9, SpriteSheet.tiles, 16, 16)};
	/**
	 * Brick FlameSegment, gradually disappear in order.
	 */
	public static Sprite[] brick_exploded = {
			new Sprite(DEFAULT_SIZE, 7, 1, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 7, 2, SpriteSheet.tiles, 16, 16),
			new Sprite(DEFAULT_SIZE, 7, 3, SpriteSheet.tiles, 16, 16),};

	/**
	 * Power-Ups
	 */
	public static Sprite powerup_bombs = new Sprite(DEFAULT_SIZE, 0, 10, SpriteSheet.tiles, 16, 16);
	public static Sprite powerup_flames = new Sprite(DEFAULT_SIZE, 1, 10, SpriteSheet.tiles, 16, 16);
	public static Sprite powerup_speed = new Sprite(DEFAULT_SIZE, 2, 10, SpriteSheet.tiles, 16, 16);
	public static Sprite powerup_brickpass = new Sprite(DEFAULT_SIZE, 3, 10, SpriteSheet.tiles, 16, 16);
	public static Sprite powerup_detonator = new Sprite(DEFAULT_SIZE, 4, 10, SpriteSheet.tiles, 16, 16);
	public static Sprite powerup_bombpush = new Sprite(DEFAULT_SIZE, 5, 10, SpriteSheet.tiles, 16, 16);
	public static Sprite powerup_flamepass = new Sprite(DEFAULT_SIZE, 6, 10, SpriteSheet.tiles, 16, 16);
	public static Sprite powerup_random = new Sprite(DEFAULT_SIZE, new Random().nextInt(7), 10, SpriteSheet.tiles, 16, 16);

}
