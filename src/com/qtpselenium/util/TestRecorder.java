package com.qtpselenium.util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.monte.media.math.Rational;
import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import java.awt.*;

public class TestRecorder {
	
	public ScreenRecorder screenRecorder;
	public void startRecording() throws Exception
	{
	GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	  //Create a instance of ScreenRecorder with the required configurations
    ScreenRecorder screenRecorder = new ScreenRecorder(gc,
    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
    CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
    DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
    QualityKey, 1.0f,
    KeyFrameIntervalKey, (int) (15 * 60)),
    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
    FrameRateKey, Rational.valueOf(30)),
    null);
	}

}
