/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

/**
 * 
 * @ClassName: BarCodeUtil
 * @Description: 条形码+文字 生成
 * @author Lenovo
 * @date 2020年6月29日
 *
 */
public class BarCodeUtil {
	/** 条形码宽度 */
	private static final int WIDTH = 300;

	/** 条形码高度 */
	private static final int HEIGHT = 50;

	/** 加文字 条形码 */
	private static final int WORDHEIGHT = 75;

	/**
	 * 设置 条形码参数
	 */
	private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
		private static final long serialVersionUID = 1L;
		{
			// 设置编码方式
			put(EncodeHintType.CHARACTER_SET, "utf-8");
		}
	};

	/**
	 * 生成 图片缓冲
	 * 
	 * @author fxbin
	 * @param vaNumber
	 *            VA 码
	 * @return 返回BufferedImage
	 */
	public static BufferedImage getBarCode(String vaNumber) {
		try {
			Code128Writer writer = new Code128Writer();
			// 编码内容, 编码类型, 宽度, 高度, 设置参数
			BitMatrix bitMatrix = writer.encode(vaNumber, BarcodeFormat.CODE_128, WIDTH, HEIGHT, hints);
			return MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把带logo的二维码下面加上文字
	 * 
	 * @author fxbin
	 * @param words
	 *            文字
	 * @return 返回BufferedImage
	 */
	public static BufferedImage insertWords(String words) {
		//条形码图片
		BufferedImage image = getBarCode(words);
		// 新的图片，把带logo的二维码下面加上文字
		if (StringUtils.isNotEmpty(words)) {
			BufferedImage outImage = new BufferedImage(WIDTH, WORDHEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = outImage.createGraphics();

			// 抗锯齿
			setGraphics2D(g2d);
			// 设置白色
			setColorWhite(g2d);

			// 画条形码到新的面板
			g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
			// 画文字到新的面板
			Color color = new Color(0, 0, 0);
			g2d.setColor(color);
			// 字体、字型、字号
			g2d.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			// 文字长度
			int strWidth = g2d.getFontMetrics().stringWidth(words);
			// 总长度减去文字长度的一半 （居中显示）
			int wordStartX = (WIDTH - strWidth) / 2;
			// height + (outImage.getHeight() - height) / 2 + 12
			int wordStartY = HEIGHT + 20;

			// 画文字
			g2d.drawString(words, wordStartX, wordStartY);
			g2d.dispose();
			outImage.flush();
			return outImage;
		}
		return null;
	}

	/**
	 * 设置 Graphics2D 属性 （抗锯齿）
	 * 
	 * @param g2d
	 *            Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
	 */
	private static void setGraphics2D(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
		Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
		g2d.setStroke(s);
	}

	/**
	 * 设置背景为白色
	 * 
	 * @param g2d
	 *            Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
	 */
	private static void setColorWhite(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		// 填充整个屏幕
		g2d.fillRect(0, 0, 600, 600);
		// 设置笔刷
		g2d.setColor(Color.BLACK);
	}
}
