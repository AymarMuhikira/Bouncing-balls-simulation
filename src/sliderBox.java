import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import acm.gui.TableLayout;

public class sliderBox {//We create the class that will be used to initiate sliders and display
	//them correctly
	JPanel myPanel;
	JLabel nameLabel;
	JLabel minLabel;
	JLabel maxLabel;
	JSlider mySlider;
	JLabel sReadout;
	Integer imin;
	Integer imax;
	public sliderBox(String name, Integer min, Integer dValue,Integer max) {
		myPanel = new JPanel();
		nameLabel = new JLabel(name);
		minLabel = new JLabel(min.toString());
		maxLabel = new JLabel(max.toString());
		mySlider = new JSlider(min,max,dValue);
		sReadout = new JLabel(dValue.toString());
		sReadout.setForeground(Color.blue );
		myPanel.setLayout(new TableLayout(1,5));
		myPanel.add(nameLabel,"width=100");
		myPanel.add(minLabel,"width=25");
		myPanel.add(mySlider,"width=100");
		myPanel.add(maxLabel,"width=100");
		myPanel.add(sReadout,"width=80");
		imin=min;
		imax=max;
	}
	public sliderBox(String name, double min, double dValue, double max) {
		myPanel = new JPanel();
		nameLabel = new JLabel(name);
		minLabel = new JLabel(Double.toString(min));
		maxLabel = new JLabel(Double.toString(max));
		mySlider = new JSlider((int)(10*min),(int)(10*max),(int)(10*dValue));
		sReadout = new JLabel(Double.toString(dValue));
		sReadout.setForeground(Color.blue);
		myPanel.setLayout(new TableLayout(1,5));
		myPanel.add(nameLabel,"width=100");
		myPanel.add(minLabel,"width=25");
		myPanel.add(mySlider,"width=100");
		myPanel.add(maxLabel,"width=100");
		myPanel.add(sReadout,"width=80");
		imin=(int)min;
		imax=(int)max;
	}
	public sliderBox(Color ball_color) {
		myPanel = new JPanel();
		nameLabel = new JLabel("Color");
		minLabel = new JLabel("");
		maxLabel = new JLabel("");
		mySlider = new JSlider(0,12,5);
		sReadout = new JLabel("Color");
		sReadout.setForeground(ball_color);
		myPanel.setLayout(new TableLayout(1,5));
		myPanel.add(nameLabel,"width=100");
		myPanel.add(minLabel,"width=25");
		myPanel.add(mySlider,"width=100");
		myPanel.add(maxLabel,"width=100");
		myPanel.add(sReadout,"width=80");
	}
	public Integer getSlider() {
		return mySlider.getValue();
	}
	public Color getColor() {
		int value = mySlider.getValue();
		if(value == 0) {
			return Color.RED;
		}
		else if(value == 1) {
			return Color.GREEN;
		}
		else if(value == 2) {
			return Color.BLUE;
		}
		else if(value == 3) {
			return Color.YELLOW;
		}
		else if(value == 4) {
			return Color.CYAN;
		}
		else if(value == 5) {
			return Color.MAGENTA;
		}
		else if(value == 6) {
			return Color.WHITE;
		}
		else if(value == 7) {
			return Color.BLACK;
		}
		else if(value == 8) {
			return Color.GRAY;
		}
		else if(value == 9) {
			return Color.LIGHT_GRAY;
		}
		else if(value == 10) {
			return Color.DARK_GRAY;
		}
		else if(value == 11) {
			return Color.ORANGE;
		}
		else {
			return Color.PINK;
		}
	}
	public void setSlider(Integer val) {
		mySlider.setValue(val);
		sReadout.setText(val.toString());

	}
	public void setSlider(double val) {
		mySlider.setValue((int)(10*val));
		sReadout.setText(Double.toString(val));
	}
	public void setColor(Color col) {
		sReadout.setForeground(col);
	}
	
}
