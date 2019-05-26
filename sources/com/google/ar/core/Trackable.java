package com.google.ar.core;

import java.util.Collection;

public interface Trackable {
    Anchor createAnchor(Pose pose);

    Collection<Anchor> getAnchors();

    TrackingState getTrackingState();
}
