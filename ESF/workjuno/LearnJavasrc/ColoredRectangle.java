import javax.swing.*;
import java.awt.*;

public class ColoredRectangle {
	private int width;
	private int height;
	private int x;
	private int y;
	private JFrame window;
	private Color color;
	
	public ColoredRectangle(){
		window = new JFrame("Box Fun");
		window.setSize(200, 200);
		
		width = 40;
		height = 20;
		x = 80;
		y = 90;
		
		color = Color.BLUE;
		
		window.setVisible(true);
		
	}
	public void paint(){
		Graphics g = window.getGraphics();
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	public void setWidth(int w){
		width = w;
	}
	public void setVisible(){
		window.setVisible(true);
	}
	public void setInvisible(){
		window.setVisible(false);
	}
	public void setHeight(int h){
		height = h;
	}
	public void setX(int ulx) {
		x = ulx;
	}
	public void setY(int uly){
		y = uly;
	}
	public void setWindow(JFrame f){
		window = f;
	}
	public void setColor(Color c) {
		color = c;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public JFrame getWindow(){
		return window;
	}
	public Color getColor(){
		return color;
	}
    public ColoredRectangle(int w, int h, int ulx, int uly, JFrame f, Color c) {
    	setWidth(w);
    	setHeight(h);
    	setX(ulx);
    	setY(uly);
    	setWindow(f);
    	setColor(c);
    	setVisible();
    }
}

