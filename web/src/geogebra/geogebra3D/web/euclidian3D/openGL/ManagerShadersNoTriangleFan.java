package geogebra.geogebra3D.web.euclidian3D.openGL;

import geogebra.common.geogebra3D.euclidian3D.EuclidianView3D;
import geogebra.common.geogebra3D.euclidian3D.openGL.ManagerShaders;
import geogebra.common.geogebra3D.euclidian3D.openGL.Renderer;
import geogebra.common.kernel.Matrix.Coords;

/**
 * Specific manager for browsers with no TRIANGLE_FAN geometry (e.g. IE)
 * @author mathieu
 *
 */
public class ManagerShadersNoTriangleFan extends ManagerShaders {

	/**
	 * constructor
	 * @param renderer GL renderer
	 * @param view3d 3D view
	 */
	public ManagerShadersNoTriangleFan(Renderer renderer, EuclidianView3D view3d) {
	    super(renderer, view3d);
    }
	
	private Coords triangleFanApex;


	@Override
    protected void triangleFanApex(Coords v){
		triangleFanApex = v.copyVector();
	}


	@Override
    protected void triangleFanVertex(Coords v){
		vertex(triangleFanApex);
		vertex(v);
	}
	
	@Override
	public int getLongitudeMax(){
		return 64;
	}
	
	@Override
	public int getLongitudeDefault(){
		return getLongitudeMax();
	}

}
