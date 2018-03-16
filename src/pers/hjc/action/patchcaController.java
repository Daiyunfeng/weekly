package pers.hjc.action;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * patchca生成多彩验证码
 * 不包含数字 修改Characters来修改生成的验证码包含的字符
 */
@Controller
public class patchcaController
{
	private static final Logger logger = LoggerFactory.getLogger(patchcaController.class);
	private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
	private static Random random = new Random();

	static
	{
		// cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
		cs.setColorFactory(new ColorFactory()
		{
			@Override
			public Color getColor(int x)
			{
				int[] c = new int[3];
				int i = random.nextInt(c.length);
				for (int fi = 0; fi < c.length; fi++)
				{
					if (fi == i)
					{
						c[fi] = random.nextInt(71);
					}
					else
					{
						c[fi] = random.nextInt(256);
					}
				}
				return new Color(c[0], c[1], c[2]);
			}
		});
		RandomWordFactory wf = new RandomWordFactory();
		BackgroundFactory backgroundFactory = new BackgroundFactory()
		{

			@Override
			public void fillBackground(BufferedImage image)
			{
				Graphics graphics = image.getGraphics();

				// 验证码图片的宽高
				int imgWidth = image.getWidth();
				int imgHeight = image.getHeight();

				// 填充为白色背景
				graphics.setColor(new Color((float) 10.0 / 255, (float) 10.0 / 255, (float) 10.0 / 255, 0.77f));
				graphics.fillRect(0, 0, imgWidth, imgHeight);
			}
		};
		wf.setCharacters("abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
		wf.setMaxLength(4);
		wf.setMinLength(4);
		cs.setWordFactory(wf);
		cs.setBackgroundFactory(backgroundFactory);
	}

	@RequestMapping("/pcrimg")
	public void crimg(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		switch (random.nextInt(5))
		{
		case 0:
			cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
			break;
		case 1:
			cs.setFilterFactory(new MarbleRippleFilterFactory());
			break;
		case 2:
			cs.setFilterFactory(new DoubleRippleFilterFactory());
			break;
		case 3:
			cs.setFilterFactory(new WobbleRippleFilterFactory());
			break;
		case 4:
			cs.setFilterFactory(new DiffuseRippleFilterFactory());
			break;
		}
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			session = request.getSession();
		}
		setResponseHeaders(response);
		String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
		session.setAttribute("captchaToken", token);
		// System.out.println("当前的SessionID=" + session.getId() + "，验证码=" +
		// token);
	}

	protected void setResponseHeaders(HttpServletResponse response)
	{
		response.setContentType("image/png");
		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		long time = System.currentTimeMillis();
		response.setDateHeader("Last-Modified", time);
		response.setDateHeader("Date", time);
		response.setDateHeader("Expires", time);
	}
}
