package geogebra.gui.app;

import geogebra.CommandLineArguments;
import geogebra.main.AppD;
import geogebra3D.App3D;

import javax.swing.JFrame;

/**
 * Frame for geogebra 3D.
 * 
 * @author matthieu
 *
 */
public class GeoGebraFrame3D extends GeoGebraFrame {
	
	private static final long serialVersionUID = 1L;

	public static synchronized void main(CommandLineArguments args) {		
		GeoGebraFrame.init(args,new GeoGebraFrame3D());
	}
	
	@Override
	protected AppD createApplication(CommandLineArguments args, JFrame frame){	
		return new App3D(args, frame, true);
	}

}
