package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0015R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = new int[]{16843375};
    private final Object[] mConstructorArgs = new Object[2];

    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View hostView, @NonNull String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        public void onClick(@NonNull View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, new Object[]{v});
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String name) {
            String idText;
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        Method method = context.getClass().getMethod(this.mMethodName, new Class[]{View.class});
                        if (method != null) {
                            this.mResolvedMethod = method;
                            this.mResolvedContext = context;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            if (id == -1) {
                idText = "";
            } else {
                idText = new StringBuilder();
                idText.append(" with id '");
                idText.append(this.mHostView.getContext().getResources().getResourceEntryName(id));
                idText.append("'");
                idText = idText.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find method ");
            stringBuilder.append(this.mMethodName);
            stringBuilder.append("(View) in a parent or ancestor Context for android:onClick ");
            stringBuilder.append("attribute defined on view ");
            stringBuilder.append(this.mHostView.getClass());
            stringBuilder.append(idText);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    final View createView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs, boolean inheritContext, boolean readAndroidTheme, boolean readAppTheme, boolean wrapContext) {
        View view;
        Context originalContext = context;
        if (inheritContext && parent != null) {
            context = parent.getContext();
        }
        if (readAndroidTheme || readAppTheme) {
            context = themifyContext(context, attrs, readAndroidTheme, readAppTheme);
        }
        if (wrapContext) {
            context = TintContextWrapper.wrap(context);
        }
        Object obj = -1;
        switch (name.hashCode()) {
            case -1946472170:
                if (name.equals("RatingBar")) {
                    obj = 11;
                    break;
                }
                break;
            case -1455429095:
                if (name.equals("CheckedTextView")) {
                    obj = 8;
                    break;
                }
                break;
            case -1346021293:
                if (name.equals("MultiAutoCompleteTextView")) {
                    obj = 10;
                    break;
                }
                break;
            case -938935918:
                if (name.equals("TextView")) {
                    obj = null;
                    break;
                }
                break;
            case -937446323:
                if (name.equals("ImageButton")) {
                    obj = 5;
                    break;
                }
                break;
            case -658531749:
                if (name.equals("SeekBar")) {
                    obj = 12;
                    break;
                }
                break;
            case -339785223:
                if (name.equals("Spinner")) {
                    obj = 4;
                    break;
                }
                break;
            case 776382189:
                if (name.equals("RadioButton")) {
                    obj = 7;
                    break;
                }
                break;
            case 1125864064:
                if (name.equals("ImageView")) {
                    obj = 1;
                    break;
                }
                break;
            case 1413872058:
                if (name.equals("AutoCompleteTextView")) {
                    obj = 9;
                    break;
                }
                break;
            case 1601505219:
                if (name.equals("CheckBox")) {
                    obj = 6;
                    break;
                }
                break;
            case 1666676343:
                if (name.equals("EditText")) {
                    obj = 3;
                    break;
                }
                break;
            case 2001146706:
                if (name.equals("Button")) {
                    obj = 2;
                    break;
                }
                break;
            default:
                break;
        }
        switch (obj) {
            case null:
                view = createTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case 1:
                view = createImageView(context, attrs);
                verifyNotNull(view, name);
                break;
            case 2:
                view = createButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case 3:
                view = createEditText(context, attrs);
                verifyNotNull(view, name);
                break;
            case 4:
                view = createSpinner(context, attrs);
                verifyNotNull(view, name);
                break;
            case 5:
                view = createImageButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case 6:
                view = createCheckBox(context, attrs);
                verifyNotNull(view, name);
                break;
            case 7:
                view = createRadioButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case 8:
                view = createCheckedTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case 9:
                view = createAutoCompleteTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case 10:
                view = createMultiAutoCompleteTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case 11:
                view = createRatingBar(context, attrs);
                verifyNotNull(view, name);
                break;
            case 12:
                view = createSeekBar(context, attrs);
                verifyNotNull(view, name);
                break;
            default:
                view = createView(context, name, attrs);
                break;
        }
        if (view == null && originalContext != context) {
            view = createViewFromTag(context, name, attrs);
        }
        if (view != null) {
            checkOnClickListener(view, attrs);
        }
        return view;
    }

    @NonNull
    protected AppCompatTextView createTextView(Context context, AttributeSet attrs) {
        return new AppCompatTextView(context, attrs);
    }

    @NonNull
    protected AppCompatImageView createImageView(Context context, AttributeSet attrs) {
        return new AppCompatImageView(context, attrs);
    }

    @NonNull
    protected AppCompatButton createButton(Context context, AttributeSet attrs) {
        return new AppCompatButton(context, attrs);
    }

    @NonNull
    protected AppCompatEditText createEditText(Context context, AttributeSet attrs) {
        return new AppCompatEditText(context, attrs);
    }

    @NonNull
    protected AppCompatSpinner createSpinner(Context context, AttributeSet attrs) {
        return new AppCompatSpinner(context, attrs);
    }

    @NonNull
    protected AppCompatImageButton createImageButton(Context context, AttributeSet attrs) {
        return new AppCompatImageButton(context, attrs);
    }

    @NonNull
    protected AppCompatCheckBox createCheckBox(Context context, AttributeSet attrs) {
        return new AppCompatCheckBox(context, attrs);
    }

    @NonNull
    protected AppCompatRadioButton createRadioButton(Context context, AttributeSet attrs) {
        return new AppCompatRadioButton(context, attrs);
    }

    @NonNull
    protected AppCompatCheckedTextView createCheckedTextView(Context context, AttributeSet attrs) {
        return new AppCompatCheckedTextView(context, attrs);
    }

    @NonNull
    protected AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new AppCompatAutoCompleteTextView(context, attrs);
    }

    @NonNull
    protected AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new AppCompatMultiAutoCompleteTextView(context, attrs);
    }

    @NonNull
    protected AppCompatRatingBar createRatingBar(Context context, AttributeSet attrs) {
        return new AppCompatRatingBar(context, attrs);
    }

    @NonNull
    protected AppCompatSeekBar createSeekBar(Context context, AttributeSet attrs) {
        return new AppCompatSeekBar(context, attrs);
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClass().getName());
            stringBuilder.append(" asked to inflate view for <");
            stringBuilder.append(name);
            stringBuilder.append(">, but returned null");
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    @Nullable
    protected View createView(Context context, String name, AttributeSet attrs) {
        return null;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        View view;
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attrs;
            if (-1 == name.indexOf(46)) {
                for (View view2 : sClassPrefixList) {
                    view2 = createViewByPrefix(context, name, view2);
                    if (view2 != null) {
                        return view2;
                    }
                }
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return null;
            }
            View createViewByPrefix = createViewByPrefix(context, name, null);
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return createViewByPrefix;
        } catch (Exception e) {
            return null;
        } finally {
            view2 = this.mConstructorArgs;
            view2[0] = null;
            this.mConstructorArgs[1] = null;
        }
    }

    private void checkOnClickListener(View view, AttributeSet attrs) {
        Context context = view.getContext();
        if (context instanceof ContextWrapper) {
            if (VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(view)) {
                TypedArray a = context.obtainStyledAttributes(attrs, sOnClickAttrs);
                String handlerName = a.getString(null);
                if (handlerName != null) {
                    view.setOnClickListener(new DeclaredOnClickListener(view, handlerName));
                }
                a.recycle();
            }
        }
    }

    private View createViewByPrefix(Context context, String name, String prefix) throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = (Constructor) sConstructorMap.get(name);
        if (constructor == null) {
            try {
                String stringBuilder;
                ClassLoader classLoader = context.getClassLoader();
                if (prefix != null) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(prefix);
                    stringBuilder2.append(name);
                    stringBuilder = stringBuilder2.toString();
                } else {
                    stringBuilder = name;
                }
                constructor = classLoader.loadClass(stringBuilder).asSubclass(View.class).getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            } catch (Exception e) {
                return null;
            }
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.mConstructorArgs);
    }

    private static Context themifyContext(Context context, AttributeSet attrs, boolean useAndroidTheme, boolean useAppTheme) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0015R.styleable.View, 0, 0);
        int themeId = 0;
        if (useAndroidTheme) {
            themeId = a.getResourceId(C0015R.styleable.View_android_theme, 0);
        }
        if (useAppTheme && themeId == 0) {
            themeId = a.getResourceId(C0015R.styleable.View_theme, 0);
            if (themeId != 0) {
                Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
            }
        }
        a.recycle();
        if (themeId == 0) {
            return context;
        }
        if ((context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == themeId) {
            return context;
        }
        return new ContextThemeWrapper(context, themeId);
    }
}
