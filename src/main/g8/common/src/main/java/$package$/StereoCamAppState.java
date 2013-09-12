package $package$;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

import java.util.logging.Logger;
import projectkyoto.jme3.mmd.CartoonEdgeProcessor;
 
/**
 *
 * @author reden
 */
public class StereoCamAppState extends AbstractAppState{
 
    private Application app;
    private FilterPostProcessor ppLeft, ppRight;
    private BarrelDistortionFilter filterLeft, filterRight;
    Camera camLeft,camRight,camCenter;
    ViewPort viewPortLeft, viewPortRight;
    private int initialCamWidth;
    
    float eyeDistance = 1f;
    private static final Logger logger = Logger.getLogger(StereoCamAppState.class.getName());
 
    final CartoonEdgeProcessor cartoonEdgeProcessLeft = new CartoonEdgeProcessor();
    final CartoonEdgeProcessor cartoonEdgeProcessRight = new CartoonEdgeProcessor();
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        viewPortLeft = app.getViewPort();
        camCenter = app.getCamera().clone();
        camLeft = app.getCamera();
        initialCamWidth = camLeft.getWidth();
        camRight = camLeft.clone();
        camLeft.setViewPort(0.0f, 0.5f, 0.0f, 1f);
        logger.warning("camLeft width = "+camLeft.getWidth()+" heignth = "+camLeft.getHeight());
        viewPortLeft.setClearFlags(true, true, true);

        camRight.setViewPort(0.5f, 1f, 0f, 1f);
        viewPortRight = app.getRenderManager().createMainView("PiP", camRight);
        viewPortRight.setClearFlags(true, true, true);
        viewPortRight.attachScene(((SimpleApplication)app).getRootNode());
         viewPortRight.setClearFlags(true, true, true);

        ppRight =new FilterPostProcessor(app.getAssetManager());
        filterRight =new BarrelDistortionFilter(false);
        ppRight.addFilter(filterRight);
        viewPortRight.addProcessor(ppRight);
 
        ppLeft =new FilterPostProcessor(app.getAssetManager());
        filterLeft=new BarrelDistortionFilter(true);
        ppLeft.addFilter(filterLeft);
        app.getViewPort().addProcessor(ppLeft);
        
    }
 
    @Override
    public void update(float tpf) {
    }

    @Override
    public void render(RenderManager rm) {
        camRight.setLocation(camCenter.getLocation().add(camLeft.getRotation().mult(new Vector3f(-eyeDistance, 0, 0))));
        camRight.setRotation(camCenter.getRotation());
        camLeft.setLocation(camCenter.getLocation().add(camCenter.getRotation().mult(new Vector3f(eyeDistance, 0, 0))));
        camLeft.setRotation(camCenter.getRotation());
        super.render(rm);
    }

    public void doubleSize(){
    }
 
    public void changeProjectionShift(float difference){
    }

    public float getEyeDistance() {
        return eyeDistance;
    }

    public void setEyeDistance(float eyeDistance) {
        this.eyeDistance = eyeDistance;
    }
    public void setCartoonEdge(boolean b) {
        viewPortLeft.removeProcessor(cartoonEdgeProcessLeft);
        viewPortLeft.removeProcessor(ppLeft);
        viewPortRight.removeProcessor(cartoonEdgeProcessRight);
        viewPortRight.removeProcessor(ppRight);
        if (b) {
            viewPortLeft.addProcessor(cartoonEdgeProcessLeft);
            viewPortRight.addProcessor(cartoonEdgeProcessRight);
        }
        viewPortLeft.addProcessor(ppLeft);
        viewPortRight.addProcessor(ppRight);
    }

    public Camera getCamCenter() {
        return camCenter;
    }
}
