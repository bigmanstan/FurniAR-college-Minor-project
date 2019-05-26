package android.support.transition;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import java.io.IOException;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {
    private static final ArrayMap<String, Constructor> CONSTRUCTORS = new ArrayMap();
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
    private final Context mContext;

    private TransitionInflater(@NonNull Context context) {
        this.mContext = context;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    public Transition inflateTransition(int resource) {
        XmlResourceParser parser = this.mContext.getResources().getXml(resource);
        try {
            Transition createTransitionFromXml = createTransitionFromXml(parser, Xml.asAttributeSet(parser), null);
            parser.close();
            return createTransitionFromXml;
        } catch (XmlPullParserException e) {
            throw new InflateException(e.getMessage(), e);
        } catch (IOException e2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(parser.getPositionDescription());
            stringBuilder.append(": ");
            stringBuilder.append(e2.getMessage());
            throw new InflateException(stringBuilder.toString(), e2);
        } catch (Throwable th) {
            parser.close();
        }
    }

    public TransitionManager inflateTransitionManager(int resource, ViewGroup sceneRoot) {
        InflateException ex;
        XmlResourceParser parser = this.mContext.getResources().getXml(resource);
        try {
            TransitionManager createTransitionManagerFromXml = createTransitionManagerFromXml(parser, Xml.asAttributeSet(parser), sceneRoot);
            parser.close();
            return createTransitionManagerFromXml;
        } catch (XmlPullParserException e) {
            ex = new InflateException(e.getMessage());
            ex.initCause(e);
            throw ex;
        } catch (IOException e2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(parser.getPositionDescription());
            stringBuilder.append(": ");
            stringBuilder.append(e2.getMessage());
            ex = new InflateException(stringBuilder.toString());
            ex.initCause(e2);
            throw ex;
        } catch (Throwable th) {
            parser.close();
        }
    }

    private Transition createTransitionFromXml(XmlPullParser parser, AttributeSet attrs, Transition parent) throws XmlPullParserException, IOException {
        Transition transition = null;
        int depth = parser.getDepth();
        TransitionSet transitionSet = parent instanceof TransitionSet ? (TransitionSet) parent : null;
        while (true) {
            int next = parser.next();
            int type = next;
            if ((next != 3 || parser.getDepth() > depth) && type != 1) {
                if (type == 2) {
                    String name = parser.getName();
                    if ("fade".equals(name)) {
                        transition = new Fade(this.mContext, attrs);
                    } else if ("changeBounds".equals(name)) {
                        transition = new ChangeBounds(this.mContext, attrs);
                    } else if ("slide".equals(name)) {
                        transition = new Slide(this.mContext, attrs);
                    } else if ("explode".equals(name)) {
                        transition = new Explode(this.mContext, attrs);
                    } else if ("changeImageTransform".equals(name)) {
                        transition = new ChangeImageTransform(this.mContext, attrs);
                    } else if ("changeTransform".equals(name)) {
                        transition = new ChangeTransform(this.mContext, attrs);
                    } else if ("changeClipBounds".equals(name)) {
                        transition = new ChangeClipBounds(this.mContext, attrs);
                    } else if ("autoTransition".equals(name)) {
                        transition = new AutoTransition(this.mContext, attrs);
                    } else if ("changeScroll".equals(name)) {
                        transition = new ChangeScroll(this.mContext, attrs);
                    } else if ("transitionSet".equals(name)) {
                        transition = new TransitionSet(this.mContext, attrs);
                    } else if ("transition".equals(name)) {
                        transition = (Transition) createCustom(attrs, Transition.class, "transition");
                    } else if ("targets".equals(name)) {
                        getTargetIds(parser, attrs, parent);
                    } else if ("arcMotion".equals(name)) {
                        if (parent != null) {
                            parent.setPathMotion(new ArcMotion(this.mContext, attrs));
                        } else {
                            throw new RuntimeException("Invalid use of arcMotion element");
                        }
                    } else if ("pathMotion".equals(name)) {
                        if (parent != null) {
                            parent.setPathMotion((PathMotion) createCustom(attrs, PathMotion.class, "pathMotion"));
                        } else {
                            throw new RuntimeException("Invalid use of pathMotion element");
                        }
                    } else if (!"patternPathMotion".equals(name)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown scene name: ");
                        stringBuilder.append(parser.getName());
                        throw new RuntimeException(stringBuilder.toString());
                    } else if (parent != null) {
                        parent.setPathMotion(new PatternPathMotion(this.mContext, attrs));
                    } else {
                        throw new RuntimeException("Invalid use of patternPathMotion element");
                    }
                    if (transition != null) {
                        if (!parser.isEmptyElementTag()) {
                            createTransitionFromXml(parser, attrs, transition);
                        }
                        if (transitionSet != null) {
                            transitionSet.addTransition(transition);
                            transition = null;
                        } else if (parent != null) {
                            throw new InflateException("Could not add transition to another transition.");
                        }
                    }
                }
            }
        }
        return transition;
    }

    private Object createCustom(AttributeSet attrs, Class expectedType, String tag) {
        String className = attrs.getAttributeValue(null, "class");
        if (className != null) {
            try {
                Object newInstance;
                synchronized (CONSTRUCTORS) {
                    Constructor constructor = (Constructor) CONSTRUCTORS.get(className);
                    if (constructor == null) {
                        Class<?> c = this.mContext.getClassLoader().loadClass(className).asSubclass(expectedType);
                        if (c != null) {
                            constructor = c.getConstructor(CONSTRUCTOR_SIGNATURE);
                            constructor.setAccessible(true);
                            CONSTRUCTORS.put(className, constructor);
                        }
                    }
                    newInstance = constructor.newInstance(new Object[]{this.mContext, attrs});
                }
                return newInstance;
            } catch (Exception e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not instantiate ");
                stringBuilder.append(expectedType);
                stringBuilder.append(" class ");
                stringBuilder.append(className);
                throw new InflateException(stringBuilder.toString(), e);
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(tag);
        stringBuilder2.append(" tag must have a 'class' attribute");
        throw new InflateException(stringBuilder2.toString());
    }

    private void getTargetIds(XmlPullParser parser, AttributeSet attrs, Transition transition) throws XmlPullParserException, IOException {
        int depth = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if ((next == 3 && parser.getDepth() <= depth) || type == 1) {
                return;
            }
            if (type == 2) {
                if (parser.getName().equals("target")) {
                    TypedArray a = this.mContext.obtainStyledAttributes(attrs, Styleable.TRANSITION_TARGET);
                    int id = TypedArrayUtils.getNamedResourceId(a, parser, "targetId", 1, 0);
                    if (id != 0) {
                        transition.addTarget(id);
                    } else {
                        int namedResourceId = TypedArrayUtils.getNamedResourceId(a, parser, "excludeId", 2, 0);
                        id = namedResourceId;
                        if (namedResourceId != 0) {
                            transition.excludeTarget(id, true);
                        } else {
                            String namedString = TypedArrayUtils.getNamedString(a, parser, "targetName", 4);
                            String transitionName = namedString;
                            if (namedString != null) {
                                transition.addTarget(transitionName);
                            } else {
                                namedString = TypedArrayUtils.getNamedString(a, parser, "excludeName", 5);
                                transitionName = namedString;
                                if (namedString != null) {
                                    transition.excludeTarget(transitionName, true);
                                } else {
                                    String className = TypedArrayUtils.getNamedString(a, parser, "excludeClass", 3);
                                    if (className != null) {
                                        try {
                                            transition.excludeTarget(Class.forName(className), true);
                                        } catch (ClassNotFoundException e) {
                                            a.recycle();
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("Could not create ");
                                            stringBuilder.append(className);
                                            throw new RuntimeException(stringBuilder.toString(), e);
                                        }
                                    }
                                    String namedString2 = TypedArrayUtils.getNamedString(a, parser, "targetClass", 0);
                                    className = namedString2;
                                    if (namedString2 != null) {
                                        transition.addTarget(Class.forName(className));
                                    }
                                }
                            }
                        }
                    }
                    a.recycle();
                } else {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Unknown scene name: ");
                    stringBuilder2.append(parser.getName());
                    throw new RuntimeException(stringBuilder2.toString());
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.support.transition.TransitionManager createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser r8, android.util.AttributeSet r9, android.view.ViewGroup r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
        r7 = this;
        r0 = r8.getDepth();
        r1 = 0;
    L_0x0005:
        r2 = r8.next();
        r3 = r2;
        r4 = 3;
        if (r2 != r4) goto L_0x0013;
    L_0x000d:
        r2 = r8.getDepth();
        if (r2 <= r0) goto L_0x0056;
    L_0x0013:
        r2 = 1;
        if (r3 == r2) goto L_0x0056;
    L_0x0016:
        r2 = 2;
        if (r3 == r2) goto L_0x001a;
    L_0x0019:
        goto L_0x0005;
    L_0x001a:
        r2 = r8.getName();
        r4 = "transitionManager";
        r4 = r2.equals(r4);
        if (r4 == 0) goto L_0x002d;
    L_0x0026:
        r4 = new android.support.transition.TransitionManager;
        r4.<init>();
        r1 = r4;
        goto L_0x003a;
    L_0x002d:
        r4 = "transition";
        r4 = r2.equals(r4);
        if (r4 == 0) goto L_0x003b;
    L_0x0035:
        if (r1 == 0) goto L_0x003b;
    L_0x0037:
        r7.loadTransition(r9, r8, r10, r1);
    L_0x003a:
        goto L_0x0005;
    L_0x003b:
        r4 = new java.lang.RuntimeException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "Unknown scene name: ";
        r5.append(r6);
        r6 = r8.getName();
        r5.append(r6);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x0056:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.TransitionInflater.createTransitionManagerFromXml(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.ViewGroup):android.support.transition.TransitionManager");
    }

    private void loadTransition(AttributeSet attrs, XmlPullParser parser, ViewGroup sceneRoot, TransitionManager transitionManager) throws NotFoundException {
        TypedArray a = this.mContext.obtainStyledAttributes(attrs, Styleable.TRANSITION_MANAGER);
        int transitionId = TypedArrayUtils.getNamedResourceId(a, parser, "transition", 2, -1);
        int fromId = TypedArrayUtils.getNamedResourceId(a, parser, "fromScene", 0, -1);
        Scene toScene = null;
        Scene fromScene = fromId < 0 ? null : Scene.getSceneForLayout(sceneRoot, fromId, this.mContext);
        int toId = TypedArrayUtils.getNamedResourceId(a, parser, "toScene", 1, -1);
        if (toId >= 0) {
            toScene = Scene.getSceneForLayout(sceneRoot, toId, this.mContext);
        }
        if (transitionId >= 0) {
            Transition transition = inflateTransition(transitionId);
            if (transition != null) {
                if (toScene == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No toScene for transition ID ");
                    stringBuilder.append(transitionId);
                    throw new RuntimeException(stringBuilder.toString());
                } else if (fromScene == null) {
                    transitionManager.setTransition(toScene, transition);
                } else {
                    transitionManager.setTransition(fromScene, toScene, transition);
                }
            }
        }
        a.recycle();
    }
}
