package com.google.ar.sceneform;

import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class NodeParent {
    private final ArrayList<Node> children = new ArrayList();
    private boolean isIterableChildrenDirty;
    private final ArrayList<Node> iterableChildren = new ArrayList();
    private int iteratingCounter;
    private final List<Node> unmodifiableChildren = Collections.unmodifiableList(this.children);

    private ArrayList<Node> getIterableChildren() {
        if (this.isIterableChildrenDirty && !isIterating()) {
            this.iterableChildren.clear();
            this.iterableChildren.addAll(this.children);
            this.isIterableChildrenDirty = false;
        }
        return this.iterableChildren;
    }

    private boolean isIterating() {
        return this.iteratingCounter > 0;
    }

    static final /* synthetic */ boolean lambda$findByName$0$NodeParent(int i, String str, Node node) {
        String name = node.getName();
        return (node.getNameHash() != 0 && node.getNameHash() == i) || (name != null && name.equals(str));
    }

    private void startIterating() {
        this.iteratingCounter++;
    }

    private void stopIterating() {
        this.iteratingCounter--;
        if (this.iteratingCounter < 0) {
            throw new AssertionError("stopIteration was called without calling startIteration.");
        }
    }

    public final void addChild(Node node) {
        Preconditions.checkNotNull(node, "Parameter \"child\" was null.");
        AndroidPreconditions.checkUiThread();
        if (node.parent != this) {
            StringBuilder stringBuilder = new StringBuilder();
            if (canAddChild(node, stringBuilder)) {
                onAddChild(node);
                return;
            }
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public void callOnHierarchy(Consumer<Node> consumer) {
        Preconditions.checkNotNull(consumer, "Parameter \"consumer\" was null.");
        ArrayList iterableChildren = getIterableChildren();
        startIterating();
        for (int i = 0; i < iterableChildren.size(); i++) {
            ((Node) iterableChildren.get(i)).callOnHierarchy(consumer);
        }
        stopIterating();
    }

    protected boolean canAddChild(Node node, StringBuilder stringBuilder) {
        Preconditions.checkNotNull(node, "Parameter \"child\" was null.");
        Preconditions.checkNotNull(stringBuilder, "Parameter \"failureReason\" was null.");
        if (node != this) {
            return true;
        }
        stringBuilder.append("Cannot add child: Cannot make a node a child of itself.");
        return false;
    }

    public Node findByName(String str) {
        if (str != null) {
            if (!str.isEmpty()) {
                return findInHierarchy(new C0391c(str.hashCode(), str));
            }
        }
        return null;
    }

    public Node findInHierarchy(Predicate<Node> predicate) {
        Preconditions.checkNotNull(predicate, "Parameter \"condition\" was null.");
        ArrayList iterableChildren = getIterableChildren();
        startIterating();
        Node node = null;
        for (int i = 0; i < iterableChildren.size(); i++) {
            node = ((Node) iterableChildren.get(i)).findInHierarchy(predicate);
            if (node != null) {
                break;
            }
        }
        stopIterating();
        return node;
    }

    public final List<Node> getChildren() {
        return this.unmodifiableChildren;
    }

    protected void onAddChild(Node node) {
        Preconditions.checkNotNull(node, "Parameter \"child\" was null.");
        NodeParent nodeParent = node.getNodeParent();
        if (nodeParent != null) {
            nodeParent.removeChild(node);
        }
        this.children.add(node);
        node.parent = this;
        this.isIterableChildrenDirty = true;
    }

    protected void onRemoveChild(Node node) {
        Preconditions.checkNotNull(node, "Parameter \"child\" was null.");
        this.children.remove(node);
        node.parent = null;
        this.isIterableChildrenDirty = true;
    }

    public final void removeChild(Node node) {
        Preconditions.checkNotNull(node, "Parameter \"child\" was null.");
        AndroidPreconditions.checkUiThread();
        if (this.children.contains(node)) {
            onRemoveChild(node);
        }
    }
}
