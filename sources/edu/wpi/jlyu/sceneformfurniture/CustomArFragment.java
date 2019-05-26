package edu.wpi.jlyu.sceneformfurniture;

import com.google.ar.core.Config;
import com.google.ar.core.Config.CloudAnchorMode;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomArFragment extends ArFragment {
    protected Config getSessionConfiguration(Session session) {
        getPlaneDiscoveryController().setInstructionView(null);
        Config config = super.getSessionConfiguration(session);
        config.setCloudAnchorMode(CloudAnchorMode.ENABLED);
        return config;
    }
}
