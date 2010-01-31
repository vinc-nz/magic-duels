package jmegraphic.hud;

import com.jme.renderer.ColorRGBA;
import com.jme.util.Timer;
import com.jmex.angelfont.BitmapFont;
import com.jmex.angelfont.BitmapFontLoader;
import com.jmex.angelfont.BitmapText;


/*
 * TESTO DA MOSTRARE A VIDEO
 */
public class Notification extends HudObject {
	BitmapText txt;
	float expireTime;

	public Notification(String text) {
		super(text);
		BitmapFont fnt = BitmapFontLoader.loadDefaultFont();

        txt = new BitmapText(fnt, false);
//      Rectangle box = new Rectangle(10, -10, display.getWidth() - 20,
//                        display.getHeight() - 20);
//      txt.setBox(box);
        txt.setSize(32);
        txt.setDefaultColor(ColorRGBA.green.clone());
        txt.setText(text);
        txt.update();
        
        this.width = txt.getLineWidth()*BORDER_OFFSET;
        this.height = txt.getLineHeight()*BORDER_OFFSET;
        txt.getLocalTranslation().x = -width/2;
        txt.getLocalTranslation().y = height/2;
        this.attachChild(txt);
        
        this.expireTime = -1;
	}
	
	public void setExpireTime(float time) {
		expireTime = time + Timer.getTimer().getTimeInSeconds();
	}
	
	public void setText(String text) {
		txt.setText(text);
	}
	
	public void setColour(ColorRGBA col) {
		txt.setDefaultColor(col);
	}

	@Override
	public void update() {
		txt.update();
		this.width = txt.getLineWidth()*BORDER_OFFSET;
		txt.getLocalTranslation().x = -width/2;
	}
	
	@Override
	public boolean toRemove() {
		if (expireTime == -1)
			return false;
		float currentTime = Timer.getTimer().getTimeInSeconds();
		return (currentTime>=expireTime);
	}

}
