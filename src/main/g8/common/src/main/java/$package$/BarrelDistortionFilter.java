package $package$;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import java.util.logging.Logger;
 
/**
 *
 * @author reden
 */
public class BarrelDistortionFilter extends Filter{
    private static final Logger logger = Logger.getLogger(BarrelDistortionFilter.class.getName());
    private static float K0 = 1.0f;
    private static float K1 = 0.22f;
    private static float K2 = 0.24f;
    private static float K3 = 0.0f;
    private static Vector4f hdmWarp = new Vector4f(K0, K1, K2, K3);
 
    private Vector2f lensCenter;
    private Vector2f screenCenter;
    private Vector2f scale;
    private Vector2f scaleIn;
    private float eyeProjectionShift = 0.25f;
    private float sizeVert = 0.0935f;
    private float sizeHor = 0.14976f;
    private float eyeToScreenDistance = 0.03f;
    private float interPupillaryDistance = 0.1f;
    private boolean isLeft = true;
    private float scaleFactor = 0.88f;
    private float viewCenter;
 
    public BarrelDistortionFilter(boolean isLeft){
        this.isLeft = isLeft;
    }
 
    @Override
    protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort vp, int width, int height) {
        logger.info("scaleFactor = "+scaleFactor);
        material = new Material(manager, "MatDefs/BarrelDistortion.j3md");
        
 
        float aspectRatio = (float)(width * 1f) / (float)height;
 
        float halfScreenDistance = (sizeVert / 2);
        float yfov = 2.0f * FastMath.atan(halfScreenDistance/eyeToScreenDistance);
        vp.getCamera().setFrustumPerspective(FastMath.RAD_TO_DEG * yfov, aspectRatio, 0.1f, 10000f);
 
        float viewCenter = sizeHor * 0.5f;
        eyeProjectionShift = viewCenter - interPupillaryDistance*0.5f;
        float projectionCenterOffset = 4.0f * eyeProjectionShift / sizeHor;
        float w = 1f;
        float h = 1;
 float texoffset = -0.1f;
 
        if(isLeft){
        } else {
            texoffset *= -1;
            projectionCenterOffset = -projectionCenterOffset;
        }
 
        lensCenter = new Vector2f((w + projectionCenterOffset * 0.5f)*0.5f, h*0.5f);
 
        //lensCenter = new Vector2f((w + projectionCenterOffset * 0.5f)*0.5f, h*0.5f);//new Vector2f( 0.5f, 0.5f);//
        screenCenter = new Vector2f(0.5f, 0.5f);
        scale = new Vector2f(scaleFactor, scaleFactor * aspectRatio );
        scaleIn = new Vector2f(1f, 1f / aspectRatio );
        material.setVector2("LensCenter", lensCenter);
        material.setVector2("ScreenCenter", screenCenter);
        material.setVector2("Scale", scale);
        material.setVector2("ScaleIn", scaleIn);
        material.setVector4("HmdWarpParam", hdmWarp);
        material.setVector2("Texoffset", new Vector2f(texoffset, 0));
//        int w2 =getRenderedTexture().getImage().getWidth();
//        int h2 =getRenderedTexture().getImage().getHeight();
//        logger.info("Barrel = "+w2+" "+h2);
//        int w3 = vp.getOutputFrameBuffer().getWidth();
//        int h3 = vp.getOutputFrameBuffer().getHeight();
//        logger.info("Barrel outout = "+w3+ " "+h3);
    }
 
    @Override
    protected Material getMaterial() {
        return material;
    }
    @Override
    protected boolean isRequiresDepthTexture() {
        return false;
    }
 
}
