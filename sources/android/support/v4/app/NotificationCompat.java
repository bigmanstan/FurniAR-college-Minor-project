package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.compat.C0004R;
import android.support.v4.text.BidiFormatter;
import android.support.v4.view.ViewCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    public static class Action {
        public PendingIntent actionIntent;
        public int icon;
        private boolean mAllowGeneratedReplies;
        private final RemoteInput[] mDataOnlyRemoteInputs;
        final Bundle mExtras;
        private final RemoteInput[] mRemoteInputs;
        public CharSequence title;

        public static final class Builder {
            private boolean mAllowGeneratedReplies;
            private final Bundle mExtras;
            private final int mIcon;
            private final PendingIntent mIntent;
            private ArrayList<RemoteInput> mRemoteInputs;
            private final CharSequence mTitle;

            public Builder(int icon, CharSequence title, PendingIntent intent) {
                this(icon, title, intent, new Bundle(), null, true);
            }

            public Builder(Action action) {
                this(action.icon, action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies());
            }

            private Builder(int icon, CharSequence title, PendingIntent intent, Bundle extras, RemoteInput[] remoteInputs, boolean allowGeneratedReplies) {
                ArrayList arrayList;
                this.mAllowGeneratedReplies = true;
                this.mIcon = icon;
                this.mTitle = Builder.limitCharSequenceLength(title);
                this.mIntent = intent;
                this.mExtras = extras;
                if (remoteInputs == null) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList(Arrays.asList(remoteInputs));
                }
                this.mRemoteInputs = arrayList;
                this.mAllowGeneratedReplies = allowGeneratedReplies;
            }

            public Builder addExtras(Bundle extras) {
                if (extras != null) {
                    this.mExtras.putAll(extras);
                }
                return this;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public Builder addRemoteInput(RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList();
                }
                this.mRemoteInputs.add(remoteInput);
                return this;
            }

            public Builder setAllowGeneratedReplies(boolean allowGeneratedReplies) {
                this.mAllowGeneratedReplies = allowGeneratedReplies;
                return this;
            }

            public Builder extend(Extender extender) {
                extender.extend(this);
                return this;
            }

            public Action build() {
                List<RemoteInput> dataOnlyInputs = new ArrayList();
                List<RemoteInput> textInputs = new ArrayList();
                if (this.mRemoteInputs != null) {
                    Iterator it = this.mRemoteInputs.iterator();
                    while (it.hasNext()) {
                        RemoteInput input = (RemoteInput) it.next();
                        if (input.isDataOnly()) {
                            dataOnlyInputs.add(input);
                        } else {
                            textInputs.add(input);
                        }
                    }
                }
                RemoteInput[] remoteInputArr = null;
                RemoteInput[] dataOnlyInputsArr = dataOnlyInputs.isEmpty() ? null : (RemoteInput[]) dataOnlyInputs.toArray(new RemoteInput[dataOnlyInputs.size()]);
                if (!textInputs.isEmpty()) {
                    remoteInputArr = (RemoteInput[]) textInputs.toArray(new RemoteInput[textInputs.size()]);
                }
                return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, remoteInputArr, dataOnlyInputsArr, this.mAllowGeneratedReplies);
            }
        }

        public interface Extender {
            Builder extend(Builder builder);
        }

        public static final class WearableExtender implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags = 1;
            private CharSequence mInProgressLabel;

            public WearableExtender(Action action) {
                Bundle wearableBundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (wearableBundle != null) {
                    this.mFlags = wearableBundle.getInt(KEY_FLAGS, 1);
                    this.mInProgressLabel = wearableBundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = wearableBundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = wearableBundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            public Builder extend(Builder builder) {
                Bundle wearableBundle = new Bundle();
                if (this.mFlags != 1) {
                    wearableBundle.putInt(KEY_FLAGS, this.mFlags);
                }
                if (this.mInProgressLabel != null) {
                    wearableBundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
                }
                if (this.mConfirmLabel != null) {
                    wearableBundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
                }
                if (this.mCancelLabel != null) {
                    wearableBundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
                return builder;
            }

            public WearableExtender clone() {
                WearableExtender that = new WearableExtender();
                that.mFlags = this.mFlags;
                that.mInProgressLabel = this.mInProgressLabel;
                that.mConfirmLabel = this.mConfirmLabel;
                that.mCancelLabel = this.mCancelLabel;
                return that;
            }

            public WearableExtender setAvailableOffline(boolean availableOffline) {
                setFlag(1, availableOffline);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            private void setFlag(int mask, boolean value) {
                if (value) {
                    this.mFlags |= mask;
                } else {
                    this.mFlags &= ~mask;
                }
            }

            public WearableExtender setInProgressLabel(CharSequence label) {
                this.mInProgressLabel = label;
                return this;
            }

            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            public WearableExtender setConfirmLabel(CharSequence label) {
                this.mConfirmLabel = label;
                return this;
            }

            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            public WearableExtender setCancelLabel(CharSequence label) {
                this.mCancelLabel = label;
                return this;
            }

            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            public WearableExtender setHintLaunchesActivity(boolean hintLaunchesActivity) {
                setFlag(2, hintLaunchesActivity);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            public WearableExtender setHintDisplayActionInline(boolean hintDisplayInline) {
                setFlag(4, hintDisplayInline);
                return this;
            }

            public boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }
        }

        public Action(int icon, CharSequence title, PendingIntent intent) {
            this(icon, title, intent, new Bundle(), null, null, true);
        }

        Action(int icon, CharSequence title, PendingIntent intent, Bundle extras, RemoteInput[] remoteInputs, RemoteInput[] dataOnlyRemoteInputs, boolean allowGeneratedReplies) {
            this.icon = icon;
            this.title = Builder.limitCharSequenceLength(title);
            this.actionIntent = intent;
            this.mExtras = extras != null ? extras : new Bundle();
            this.mRemoteInputs = remoteInputs;
            this.mDataOnlyRemoteInputs = dataOnlyRemoteInputs;
            this.mAllowGeneratedReplies = allowGeneratedReplies;
        }

        public int getIcon() {
            return this.icon;
        }

        public CharSequence getTitle() {
            return this.title;
        }

        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public RemoteInput[] getDataOnlyRemoteInputs() {
            return this.mDataOnlyRemoteInputs;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BadgeIconType {
    }

    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public ArrayList<Action> mActions;
        int mBadgeIcon;
        RemoteViews mBigContentView;
        String mCategory;
        String mChannelId;
        int mColor;
        boolean mColorized;
        boolean mColorizedSet;
        CharSequence mContentInfo;
        PendingIntent mContentIntent;
        CharSequence mContentText;
        CharSequence mContentTitle;
        RemoteViews mContentView;
        @RestrictTo({Scope.LIBRARY_GROUP})
        public Context mContext;
        Bundle mExtras;
        PendingIntent mFullScreenIntent;
        int mGroupAlertBehavior;
        String mGroupKey;
        boolean mGroupSummary;
        RemoteViews mHeadsUpContentView;
        Bitmap mLargeIcon;
        boolean mLocalOnly;
        Notification mNotification;
        int mNumber;
        @Deprecated
        public ArrayList<String> mPeople;
        int mPriority;
        int mProgress;
        boolean mProgressIndeterminate;
        int mProgressMax;
        Notification mPublicVersion;
        CharSequence[] mRemoteInputHistory;
        String mShortcutId;
        boolean mShowWhen;
        String mSortKey;
        Style mStyle;
        CharSequence mSubText;
        RemoteViews mTickerView;
        long mTimeout;
        boolean mUseChronometer;
        int mVisibility;

        public Builder(@NonNull Context context, @NonNull String channelId) {
            this.mActions = new ArrayList();
            this.mShowWhen = true;
            this.mLocalOnly = false;
            this.mColor = 0;
            this.mVisibility = 0;
            this.mBadgeIcon = 0;
            this.mGroupAlertBehavior = 0;
            this.mNotification = new Notification();
            this.mContext = context;
            this.mChannelId = channelId;
            this.mNotification.when = System.currentTimeMillis();
            this.mNotification.audioStreamType = -1;
            this.mPriority = 0;
            this.mPeople = new ArrayList();
        }

        @Deprecated
        public Builder(Context context) {
            this(context, null);
        }

        public Builder setWhen(long when) {
            this.mNotification.when = when;
            return this;
        }

        public Builder setShowWhen(boolean show) {
            this.mShowWhen = show;
            return this;
        }

        public Builder setUsesChronometer(boolean b) {
            this.mUseChronometer = b;
            return this;
        }

        public Builder setSmallIcon(int icon) {
            this.mNotification.icon = icon;
            return this;
        }

        public Builder setSmallIcon(int icon, int level) {
            this.mNotification.icon = icon;
            this.mNotification.iconLevel = level;
            return this;
        }

        public Builder setContentTitle(CharSequence title) {
            this.mContentTitle = limitCharSequenceLength(title);
            return this;
        }

        public Builder setContentText(CharSequence text) {
            this.mContentText = limitCharSequenceLength(text);
            return this;
        }

        public Builder setSubText(CharSequence text) {
            this.mSubText = limitCharSequenceLength(text);
            return this;
        }

        public Builder setRemoteInputHistory(CharSequence[] text) {
            this.mRemoteInputHistory = text;
            return this;
        }

        public Builder setNumber(int number) {
            this.mNumber = number;
            return this;
        }

        public Builder setContentInfo(CharSequence info) {
            this.mContentInfo = limitCharSequenceLength(info);
            return this;
        }

        public Builder setProgress(int max, int progress, boolean indeterminate) {
            this.mProgressMax = max;
            this.mProgress = progress;
            this.mProgressIndeterminate = indeterminate;
            return this;
        }

        public Builder setContent(RemoteViews views) {
            this.mNotification.contentView = views;
            return this;
        }

        public Builder setContentIntent(PendingIntent intent) {
            this.mContentIntent = intent;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent intent) {
            this.mNotification.deleteIntent = intent;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent intent, boolean highPriority) {
            this.mFullScreenIntent = intent;
            setFlag(128, highPriority);
            return this;
        }

        public Builder setTicker(CharSequence tickerText) {
            this.mNotification.tickerText = limitCharSequenceLength(tickerText);
            return this;
        }

        public Builder setTicker(CharSequence tickerText, RemoteViews views) {
            this.mNotification.tickerText = limitCharSequenceLength(tickerText);
            this.mTickerView = views;
            return this;
        }

        public Builder setLargeIcon(Bitmap icon) {
            this.mLargeIcon = icon;
            return this;
        }

        public Builder setSound(Uri sound) {
            this.mNotification.sound = sound;
            this.mNotification.audioStreamType = -1;
            if (VERSION.SDK_INT >= 21) {
                this.mNotification.audioAttributes = new android.media.AudioAttributes.Builder().setContentType(4).setUsage(5).build();
            }
            return this;
        }

        public Builder setSound(Uri sound, int streamType) {
            this.mNotification.sound = sound;
            this.mNotification.audioStreamType = streamType;
            if (VERSION.SDK_INT >= 21) {
                this.mNotification.audioAttributes = new android.media.AudioAttributes.Builder().setContentType(4).setLegacyStreamType(streamType).build();
            }
            return this;
        }

        public Builder setVibrate(long[] pattern) {
            this.mNotification.vibrate = pattern;
            return this;
        }

        public Builder setLights(@ColorInt int argb, int onMs, int offMs) {
            this.mNotification.ledARGB = argb;
            this.mNotification.ledOnMS = onMs;
            this.mNotification.ledOffMS = offMs;
            int i = 1;
            boolean showLights = (this.mNotification.ledOnMS == 0 || this.mNotification.ledOffMS == 0) ? false : true;
            Notification notification = this.mNotification;
            int i2 = this.mNotification.flags & -2;
            if (!showLights) {
                i = 0;
            }
            notification.flags = i | i2;
            return this;
        }

        public Builder setOngoing(boolean ongoing) {
            setFlag(2, ongoing);
            return this;
        }

        public Builder setColorized(boolean colorize) {
            this.mColorized = colorize;
            this.mColorizedSet = true;
            return this;
        }

        public Builder setOnlyAlertOnce(boolean onlyAlertOnce) {
            setFlag(8, onlyAlertOnce);
            return this;
        }

        public Builder setAutoCancel(boolean autoCancel) {
            setFlag(16, autoCancel);
            return this;
        }

        public Builder setLocalOnly(boolean b) {
            this.mLocalOnly = b;
            return this;
        }

        public Builder setCategory(String category) {
            this.mCategory = category;
            return this;
        }

        public Builder setDefaults(int defaults) {
            this.mNotification.defaults = defaults;
            if ((defaults & 4) != 0) {
                Notification notification = this.mNotification;
                notification.flags |= 1;
            }
            return this;
        }

        private void setFlag(int mask, boolean value) {
            if (value) {
                Notification notification = this.mNotification;
                notification.flags |= mask;
                return;
            }
            notification = this.mNotification;
            notification.flags &= ~mask;
        }

        public Builder setPriority(int pri) {
            this.mPriority = pri;
            return this;
        }

        public Builder addPerson(String uri) {
            this.mPeople.add(uri);
            return this;
        }

        public Builder setGroup(String groupKey) {
            this.mGroupKey = groupKey;
            return this;
        }

        public Builder setGroupSummary(boolean isGroupSummary) {
            this.mGroupSummary = isGroupSummary;
            return this;
        }

        public Builder setSortKey(String sortKey) {
            this.mSortKey = sortKey;
            return this;
        }

        public Builder addExtras(Bundle extras) {
            if (extras != null) {
                if (this.mExtras == null) {
                    this.mExtras = new Bundle(extras);
                } else {
                    this.mExtras.putAll(extras);
                }
            }
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public Bundle getExtras() {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            return this.mExtras;
        }

        public Builder addAction(int icon, CharSequence title, PendingIntent intent) {
            this.mActions.add(new Action(icon, title, intent));
            return this;
        }

        public Builder addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public Builder setStyle(Style style) {
            if (this.mStyle != style) {
                this.mStyle = style;
                if (this.mStyle != null) {
                    this.mStyle.setBuilder(this);
                }
            }
            return this;
        }

        public Builder setColor(@ColorInt int argb) {
            this.mColor = argb;
            return this;
        }

        public Builder setVisibility(int visibility) {
            this.mVisibility = visibility;
            return this;
        }

        public Builder setPublicVersion(Notification n) {
            this.mPublicVersion = n;
            return this;
        }

        public Builder setCustomContentView(RemoteViews contentView) {
            this.mContentView = contentView;
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews contentView) {
            this.mBigContentView = contentView;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews contentView) {
            this.mHeadsUpContentView = contentView;
            return this;
        }

        public Builder setChannelId(@NonNull String channelId) {
            this.mChannelId = channelId;
            return this;
        }

        public Builder setTimeoutAfter(long durationMs) {
            this.mTimeout = durationMs;
            return this;
        }

        public Builder setShortcutId(String shortcutId) {
            this.mShortcutId = shortcutId;
            return this;
        }

        public Builder setBadgeIconType(int icon) {
            this.mBadgeIcon = icon;
            return this;
        }

        public Builder setGroupAlertBehavior(int groupAlertBehavior) {
            this.mGroupAlertBehavior = groupAlertBehavior;
            return this;
        }

        public Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        @Deprecated
        public Notification getNotification() {
            return build();
        }

        public Notification build() {
            return new NotificationCompatBuilder(this).build();
        }

        protected static CharSequence limitCharSequenceLength(CharSequence cs) {
            if (cs == null) {
                return cs;
            }
            if (cs.length() > MAX_CHARSEQUENCE_LENGTH) {
                cs = cs.subSequence(0, MAX_CHARSEQUENCE_LENGTH);
            }
            return cs;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getContentView() {
            return this.mContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getBigContentView() {
            return this.mBigContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews getHeadsUpContentView() {
            return this.mHeadsUpContentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public long getWhenIfShowing() {
            return this.mShowWhen ? this.mNotification.when : 0;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getPriority() {
            return this.mPriority;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public int getColor() {
            return this.mColor;
        }
    }

    public interface Extender {
        Builder extend(Builder builder);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupAlertBehavior {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationVisibility {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamType {
    }

    public static abstract class Style {
        CharSequence mBigContentTitle;
        @RestrictTo({Scope.LIBRARY_GROUP})
        protected Builder mBuilder;
        CharSequence mSummaryText;
        boolean mSummaryTextSet = false;

        public void setBuilder(Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this);
                }
            }
        }

        public Notification build() {
            if (this.mBuilder != null) {
                return this.mBuilder.build();
            }
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor builder) {
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void addCompatExtras(Bundle extras) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        protected void restoreFromCompatExtras(Bundle extras) {
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews applyStandardTemplate(boolean showSmallIcon, int resId, boolean fitIn1U) {
            int i;
            boolean showLine2;
            int i2;
            int i3;
            Resources res = this.mBuilder.mContext.getResources();
            RemoteViews contentView = new RemoteViews(this.mBuilder.mContext.getPackageName(), resId);
            boolean showLine3 = false;
            int i4 = 0;
            boolean minPriority = this.mBuilder.getPriority() < -1;
            if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT < 21) {
                if (minPriority) {
                    contentView.setInt(C0004R.id.notification_background, "setBackgroundResource", C0004R.drawable.notification_bg_low);
                    contentView.setInt(C0004R.id.icon, "setBackgroundResource", C0004R.drawable.notification_template_icon_low_bg);
                } else {
                    contentView.setInt(C0004R.id.notification_background, "setBackgroundResource", C0004R.drawable.notification_bg);
                    contentView.setInt(C0004R.id.icon, "setBackgroundResource", C0004R.drawable.notification_template_icon_bg);
                }
            }
            if (r0.mBuilder.mLargeIcon != null) {
                if (VERSION.SDK_INT >= 16) {
                    contentView.setViewVisibility(C0004R.id.icon, 0);
                    contentView.setImageViewBitmap(C0004R.id.icon, r0.mBuilder.mLargeIcon);
                } else {
                    contentView.setViewVisibility(C0004R.id.icon, 8);
                }
                if (showSmallIcon && r0.mBuilder.mNotification.icon != 0) {
                    int backgroundSize = res.getDimensionPixelSize(C0004R.dimen.notification_right_icon_size);
                    int iconSize = backgroundSize - (res.getDimensionPixelSize(C0004R.dimen.notification_small_icon_background_padding) * 2);
                    if (VERSION.SDK_INT >= 21) {
                        contentView.setImageViewBitmap(C0004R.id.right_icon, createIconWithBackground(r0.mBuilder.mNotification.icon, backgroundSize, iconSize, r0.mBuilder.getColor()));
                    } else {
                        contentView.setImageViewBitmap(C0004R.id.right_icon, createColoredBitmap(r0.mBuilder.mNotification.icon, -1));
                    }
                    contentView.setViewVisibility(C0004R.id.right_icon, 0);
                }
            } else if (showSmallIcon && r0.mBuilder.mNotification.icon != 0) {
                contentView.setViewVisibility(C0004R.id.icon, 0);
                if (VERSION.SDK_INT >= 21) {
                    contentView.setImageViewBitmap(C0004R.id.icon, createIconWithBackground(r0.mBuilder.mNotification.icon, res.getDimensionPixelSize(C0004R.dimen.notification_large_icon_width) - res.getDimensionPixelSize(C0004R.dimen.notification_big_circle_margin), res.getDimensionPixelSize(C0004R.dimen.notification_small_icon_size_as_large), r0.mBuilder.getColor()));
                } else {
                    contentView.setImageViewBitmap(C0004R.id.icon, createColoredBitmap(r0.mBuilder.mNotification.icon, -1));
                }
            }
            if (r0.mBuilder.mContentTitle != null) {
                contentView.setTextViewText(C0004R.id.title, r0.mBuilder.mContentTitle);
            }
            if (r0.mBuilder.mContentText != null) {
                contentView.setTextViewText(C0004R.id.text, r0.mBuilder.mContentText);
                showLine3 = true;
            }
            boolean z = VERSION.SDK_INT < 21 && r0.mBuilder.mLargeIcon != null;
            if (r0.mBuilder.mContentInfo != null) {
                contentView.setTextViewText(C0004R.id.info, r0.mBuilder.mContentInfo);
                contentView.setViewVisibility(C0004R.id.info, 0);
                showLine3 = true;
                z = true;
            } else if (r0.mBuilder.mNumber > 0) {
                if (r0.mBuilder.mNumber > res.getInteger(C0004R.integer.status_bar_notification_info_maxnum)) {
                    contentView.setTextViewText(C0004R.id.info, res.getString(C0004R.string.status_bar_notification_info_overflow));
                } else {
                    contentView.setTextViewText(C0004R.id.info, NumberFormat.getIntegerInstance().format((long) r0.mBuilder.mNumber));
                }
                contentView.setViewVisibility(C0004R.id.info, 0);
                showLine3 = true;
                z = true;
            } else {
                contentView.setViewVisibility(C0004R.id.info, 8);
            }
            boolean showLine32 = showLine3;
            showLine3 = z;
            if (r0.mBuilder.mSubText == null || VERSION.SDK_INT < 16) {
                i = 8;
            } else {
                contentView.setTextViewText(C0004R.id.text, r0.mBuilder.mSubText);
                if (r0.mBuilder.mContentText != null) {
                    contentView.setTextViewText(C0004R.id.text2, r0.mBuilder.mContentText);
                    contentView.setViewVisibility(C0004R.id.text2, 0);
                    showLine2 = true;
                    i = 8;
                    if (showLine2 || VERSION.SDK_INT < 16) {
                        i2 = i;
                    } else {
                        if (fitIn1U) {
                            contentView.setTextViewTextSize(C0004R.id.text, 0, (float) res.getDimensionPixelSize(C0004R.dimen.notification_subtext_size));
                        }
                        i2 = i;
                        contentView.setViewPadding(C0004R.id.line1, 0, 0, 0, 0);
                    }
                    if (r0.mBuilder.getWhenIfShowing() != 0) {
                        if (r0.mBuilder.mUseChronometer || VERSION.SDK_INT < 16) {
                            contentView.setViewVisibility(C0004R.id.time, 0);
                            contentView.setLong(C0004R.id.time, "setTime", r0.mBuilder.getWhenIfShowing());
                        } else {
                            contentView.setViewVisibility(C0004R.id.chronometer, 0);
                            contentView.setLong(C0004R.id.chronometer, "setBase", r0.mBuilder.getWhenIfShowing() + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
                            contentView.setBoolean(C0004R.id.chronometer, "setStarted", true);
                        }
                        showLine3 = true;
                    }
                    contentView.setViewVisibility(C0004R.id.right_side, showLine3 ? 0 : i2);
                    i3 = C0004R.id.line3;
                    if (showLine32) {
                        i4 = i2;
                    }
                    contentView.setViewVisibility(i3, i4);
                    return contentView;
                }
                i = 8;
                contentView.setViewVisibility(C0004R.id.text2, 8);
            }
            showLine2 = false;
            if (showLine2) {
            }
            i2 = i;
            if (r0.mBuilder.getWhenIfShowing() != 0) {
                if (r0.mBuilder.mUseChronometer) {
                }
                contentView.setViewVisibility(C0004R.id.time, 0);
                contentView.setLong(C0004R.id.time, "setTime", r0.mBuilder.getWhenIfShowing());
                showLine3 = true;
            }
            if (showLine3) {
            }
            contentView.setViewVisibility(C0004R.id.right_side, showLine3 ? 0 : i2);
            i3 = C0004R.id.line3;
            if (showLine32) {
                i4 = i2;
            }
            contentView.setViewVisibility(i3, i4);
            return contentView;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Bitmap createColoredBitmap(int iconId, int color) {
            return createColoredBitmap(iconId, color, 0);
        }

        private Bitmap createColoredBitmap(int iconId, int color, int size) {
            Drawable drawable = this.mBuilder.mContext.getResources().getDrawable(iconId);
            int width = size == 0 ? drawable.getIntrinsicWidth() : size;
            int height = size == 0 ? drawable.getIntrinsicHeight() : size;
            Bitmap resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            drawable.setBounds(0, 0, width, height);
            if (color != 0) {
                drawable.mutate().setColorFilter(new PorterDuffColorFilter(color, Mode.SRC_IN));
            }
            drawable.draw(new Canvas(resultBitmap));
            return resultBitmap;
        }

        private Bitmap createIconWithBackground(int iconId, int size, int iconSize, int color) {
            Bitmap coloredBitmap = createColoredBitmap(C0004R.drawable.notification_icon_background, color == 0 ? 0 : color, size);
            Canvas canvas = new Canvas(coloredBitmap);
            Drawable icon = this.mBuilder.mContext.getResources().getDrawable(iconId).mutate();
            icon.setFilterBitmap(true);
            int inset = (size - iconSize) / 2;
            icon.setBounds(inset, inset, iconSize + inset, iconSize + inset);
            icon.setColorFilter(new PorterDuffColorFilter(-1, Mode.SRC_ATOP));
            icon.draw(canvas);
            return coloredBitmap;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void buildIntoRemoteViews(RemoteViews outerView, RemoteViews innerView) {
            hideNormalContent(outerView);
            outerView.removeAllViews(C0004R.id.notification_main_column);
            outerView.addView(C0004R.id.notification_main_column, innerView.clone());
            outerView.setViewVisibility(C0004R.id.notification_main_column, 0);
            if (VERSION.SDK_INT >= 21) {
                outerView.setViewPadding(C0004R.id.notification_main_column_container, 0, calculateTopPadding(), 0, 0);
            }
        }

        private void hideNormalContent(RemoteViews outerView) {
            outerView.setViewVisibility(C0004R.id.title, 8);
            outerView.setViewVisibility(C0004R.id.text2, 8);
            outerView.setViewVisibility(C0004R.id.text, 8);
        }

        private int calculateTopPadding() {
            Resources resources = this.mBuilder.mContext.getResources();
            float largeFactor = (constrain(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round(((1.0f - largeFactor) * ((float) resources.getDimensionPixelSize(C0004R.dimen.notification_top_pad))) + (((float) resources.getDimensionPixelSize(C0004R.dimen.notification_top_pad_large_text)) * largeFactor));
        }

        private static float constrain(float amount, float low, float high) {
            if (amount < low) {
                return low;
            }
            return amount > high ? high : amount;
        }
    }

    public static class BigPictureStyle extends Style {
        private Bitmap mBigLargeIcon;
        private boolean mBigLargeIconSet;
        private Bitmap mPicture;

        public BigPictureStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigPictureStyle setBigContentTitle(CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public BigPictureStyle bigPicture(Bitmap b) {
            this.mPicture = b;
            return this;
        }

        public BigPictureStyle bigLargeIcon(Bitmap b) {
            this.mBigLargeIcon = b;
            this.mBigLargeIconSet = true;
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 16) {
                android.app.Notification.BigPictureStyle style = new android.app.Notification.BigPictureStyle(builder.getBuilder()).setBigContentTitle(this.mBigContentTitle).bigPicture(this.mPicture);
                if (this.mBigLargeIconSet) {
                    style.bigLargeIcon(this.mBigLargeIcon);
                }
                if (this.mSummaryTextSet) {
                    style.setSummaryText(this.mSummaryText);
                }
            }
        }
    }

    public static class BigTextStyle extends Style {
        private CharSequence mBigText;

        public BigTextStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigTextStyle setBigContentTitle(CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public BigTextStyle bigText(CharSequence cs) {
            this.mBigText = Builder.limitCharSequenceLength(cs);
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 16) {
                android.app.Notification.BigTextStyle style = new android.app.Notification.BigTextStyle(builder.getBuilder()).setBigContentTitle(this.mBigContentTitle).bigText(this.mBigText);
                if (this.mSummaryTextSet) {
                    style.setSummaryText(this.mSummaryText);
                }
            }
        }
    }

    public static final class CarExtender implements Extender {
        private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String KEY_AUTHOR = "author";
        private static final String KEY_MESSAGES = "messages";
        private static final String KEY_ON_READ = "on_read";
        private static final String KEY_ON_REPLY = "on_reply";
        private static final String KEY_PARTICIPANTS = "participants";
        private static final String KEY_REMOTE_INPUT = "remote_input";
        private static final String KEY_TEXT = "text";
        private static final String KEY_TIMESTAMP = "timestamp";
        private int mColor = 0;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        public static class UnreadConversation {
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            public static class Builder {
                private long mLatestTimestamp;
                private final List<String> mMessages = new ArrayList();
                private final String mParticipant;
                private PendingIntent mReadPendingIntent;
                private RemoteInput mRemoteInput;
                private PendingIntent mReplyPendingIntent;

                public Builder(String name) {
                    this.mParticipant = name;
                }

                public Builder addMessage(String message) {
                    this.mMessages.add(message);
                    return this;
                }

                public Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
                    this.mRemoteInput = remoteInput;
                    this.mReplyPendingIntent = pendingIntent;
                    return this;
                }

                public Builder setReadPendingIntent(PendingIntent pendingIntent) {
                    this.mReadPendingIntent = pendingIntent;
                    return this;
                }

                public Builder setLatestTimestamp(long timestamp) {
                    this.mLatestTimestamp = timestamp;
                    return this;
                }

                public UnreadConversation build() {
                    return new UnreadConversation((String[]) this.mMessages.toArray(new String[this.mMessages.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, new String[]{this.mParticipant}, this.mLatestTimestamp);
                }
            }

            UnreadConversation(String[] messages, RemoteInput remoteInput, PendingIntent replyPendingIntent, PendingIntent readPendingIntent, String[] participants, long latestTimestamp) {
                this.mMessages = messages;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = readPendingIntent;
                this.mReplyPendingIntent = replyPendingIntent;
                this.mParticipants = participants;
                this.mLatestTimestamp = latestTimestamp;
            }

            public String[] getMessages() {
                return this.mMessages;
            }

            public RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }

            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            public String[] getParticipants() {
                return this.mParticipants;
            }

            public String getParticipant() {
                return this.mParticipants.length > 0 ? this.mParticipants[0] : null;
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }
        }

        public CarExtender(Notification notification) {
            if (VERSION.SDK_INT >= 21) {
                Bundle carBundle;
                if (NotificationCompat.getExtras(notification) == null) {
                    carBundle = null;
                } else {
                    carBundle = NotificationCompat.getExtras(notification).getBundle(EXTRA_CAR_EXTENDER);
                }
                if (carBundle != null) {
                    this.mLargeIcon = (Bitmap) carBundle.getParcelable(EXTRA_LARGE_ICON);
                    this.mColor = carBundle.getInt(EXTRA_COLOR, 0);
                    this.mUnreadConversation = getUnreadConversationFromBundle(carBundle.getBundle(EXTRA_CONVERSATION));
                }
            }
        }

        @RequiresApi(21)
        private static UnreadConversation getUnreadConversationFromBundle(@Nullable Bundle b) {
            Bundle bundle = b;
            RemoteInput remoteInput = null;
            if (bundle == null) {
                return null;
            }
            Parcelable[] parcelableMessages = bundle.getParcelableArray(KEY_MESSAGES);
            String[] messages = null;
            if (parcelableMessages != null) {
                String[] tmp = new String[parcelableMessages.length];
                boolean success = true;
                for (int i = 0; i < tmp.length; i++) {
                    if (!(parcelableMessages[i] instanceof Bundle)) {
                        success = false;
                        break;
                    }
                    tmp[i] = ((Bundle) parcelableMessages[i]).getString(KEY_TEXT);
                    if (tmp[i] == null) {
                        success = false;
                        break;
                    }
                }
                if (!success) {
                    return null;
                }
                messages = tmp;
            }
            PendingIntent onRead = (PendingIntent) bundle.getParcelable(KEY_ON_READ);
            PendingIntent onReply = (PendingIntent) bundle.getParcelable(KEY_ON_REPLY);
            RemoteInput remoteInput2 = (RemoteInput) bundle.getParcelable(KEY_REMOTE_INPUT);
            String[] participants = bundle.getStringArray(KEY_PARTICIPANTS);
            if (participants != null) {
                if (participants.length == 1) {
                    if (remoteInput2 != null) {
                        RemoteInput remoteInput3 = new RemoteInput(remoteInput2.getResultKey(), remoteInput2.getLabel(), remoteInput2.getChoices(), remoteInput2.getAllowFreeFormInput(), remoteInput2.getExtras(), null);
                    }
                    return new UnreadConversation(messages, remoteInput, onReply, onRead, participants, bundle.getLong(KEY_TIMESTAMP));
                }
            }
            return null;
        }

        @RequiresApi(21)
        private static Bundle getBundleForUnreadConversation(@NonNull UnreadConversation uc) {
            Bundle b = new Bundle();
            String author = null;
            int i = 0;
            if (uc.getParticipants() != null && uc.getParticipants().length > 1) {
                author = uc.getParticipants()[0];
            }
            Parcelable[] messages = new Parcelable[uc.getMessages().length];
            while (i < messages.length) {
                Bundle m = new Bundle();
                m.putString(KEY_TEXT, uc.getMessages()[i]);
                m.putString(KEY_AUTHOR, author);
                messages[i] = m;
                i++;
            }
            b.putParcelableArray(KEY_MESSAGES, messages);
            RemoteInput remoteInputCompat = uc.getRemoteInput();
            if (remoteInputCompat != null) {
                b.putParcelable(KEY_REMOTE_INPUT, new android.app.RemoteInput.Builder(remoteInputCompat.getResultKey()).setLabel(remoteInputCompat.getLabel()).setChoices(remoteInputCompat.getChoices()).setAllowFreeFormInput(remoteInputCompat.getAllowFreeFormInput()).addExtras(remoteInputCompat.getExtras()).build());
            }
            b.putParcelable(KEY_ON_REPLY, uc.getReplyPendingIntent());
            b.putParcelable(KEY_ON_READ, uc.getReadPendingIntent());
            b.putStringArray(KEY_PARTICIPANTS, uc.getParticipants());
            b.putLong(KEY_TIMESTAMP, uc.getLatestTimestamp());
            return b;
        }

        public Builder extend(Builder builder) {
            if (VERSION.SDK_INT < 21) {
                return builder;
            }
            Bundle carExtensions = new Bundle();
            if (this.mLargeIcon != null) {
                carExtensions.putParcelable(EXTRA_LARGE_ICON, this.mLargeIcon);
            }
            if (this.mColor != 0) {
                carExtensions.putInt(EXTRA_COLOR, this.mColor);
            }
            if (this.mUnreadConversation != null) {
                carExtensions.putBundle(EXTRA_CONVERSATION, getBundleForUnreadConversation(this.mUnreadConversation));
            }
            builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, carExtensions);
            return builder;
        }

        public CarExtender setColor(@ColorInt int color) {
            this.mColor = color;
            return this;
        }

        @ColorInt
        public int getColor() {
            return this.mColor;
        }

        public CarExtender setLargeIcon(Bitmap largeIcon) {
            this.mLargeIcon = largeIcon;
            return this;
        }

        public Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        public CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        public UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }
    }

    public static class DecoratedCustomViewStyle extends Style {
        private static final int MAX_ACTION_BUTTONS = 3;

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 24) {
                builder.getBuilder().setStyle(new android.app.Notification.DecoratedCustomViewStyle());
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT < 24 && this.mBuilder.getContentView() != null) {
                return createRemoteViews(this.mBuilder.getContentView(), false);
            }
            return null;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews bigContentView = this.mBuilder.getBigContentView();
            RemoteViews innerView = bigContentView != null ? bigContentView : this.mBuilder.getContentView();
            if (innerView == null) {
                return null;
            }
            return createRemoteViews(innerView, true);
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUp = this.mBuilder.getHeadsUpContentView();
            RemoteViews innerView = headsUp != null ? headsUp : this.mBuilder.getContentView();
            if (headsUp == null) {
                return null;
            }
            return createRemoteViews(innerView, true);
        }

        private RemoteViews createRemoteViews(RemoteViews innerView, boolean showActions) {
            int actionVisibility = 0;
            RemoteViews remoteViews = applyStandardTemplate(true, C0004R.layout.notification_template_custom_big, false);
            remoteViews.removeAllViews(C0004R.id.actions);
            boolean actionsVisible = false;
            if (showActions && this.mBuilder.mActions != null) {
                int numActions = Math.min(this.mBuilder.mActions.size(), 3);
                if (numActions > 0) {
                    actionsVisible = true;
                    for (int i = 0; i < numActions; i++) {
                        remoteViews.addView(C0004R.id.actions, generateActionButton((Action) this.mBuilder.mActions.get(i)));
                    }
                }
            }
            if (!actionsVisible) {
                actionVisibility = 8;
            }
            remoteViews.setViewVisibility(C0004R.id.actions, actionVisibility);
            remoteViews.setViewVisibility(C0004R.id.action_divider, actionVisibility);
            buildIntoRemoteViews(remoteViews, innerView);
            return remoteViews;
        }

        private RemoteViews generateActionButton(Action action) {
            boolean tombstone = action.actionIntent == null;
            RemoteViews button = new RemoteViews(this.mBuilder.mContext.getPackageName(), tombstone ? C0004R.layout.notification_action_tombstone : C0004R.layout.notification_action);
            button.setImageViewBitmap(C0004R.id.action_image, createColoredBitmap(action.getIcon(), this.mBuilder.mContext.getResources().getColor(C0004R.color.notification_action_color_filter)));
            button.setTextViewText(C0004R.id.action_text, action.title);
            if (!tombstone) {
                button.setOnClickPendingIntent(C0004R.id.action_container, action.actionIntent);
            }
            if (VERSION.SDK_INT >= 15) {
                button.setContentDescription(C0004R.id.action_container, action.title);
            }
            return button;
        }
    }

    public static class InboxStyle extends Style {
        private ArrayList<CharSequence> mTexts = new ArrayList();

        public InboxStyle(Builder builder) {
            setBuilder(builder);
        }

        public InboxStyle setBigContentTitle(CharSequence title) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(title);
            return this;
        }

        public InboxStyle setSummaryText(CharSequence cs) {
            this.mSummaryText = Builder.limitCharSequenceLength(cs);
            this.mSummaryTextSet = true;
            return this;
        }

        public InboxStyle addLine(CharSequence cs) {
            this.mTexts.add(Builder.limitCharSequenceLength(cs));
            return this;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 16) {
                android.app.Notification.InboxStyle style = new android.app.Notification.InboxStyle(builder.getBuilder()).setBigContentTitle(this.mBigContentTitle);
                if (this.mSummaryTextSet) {
                    style.setSummaryText(this.mSummaryText);
                }
                Iterator it = this.mTexts.iterator();
                while (it.hasNext()) {
                    style.addLine((CharSequence) it.next());
                }
            }
        }
    }

    public static class MessagingStyle extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence mConversationTitle;
        List<Message> mMessages = new ArrayList();
        CharSequence mUserDisplayName;

        public static final class Message {
            static final String KEY_DATA_MIME_TYPE = "type";
            static final String KEY_DATA_URI = "uri";
            static final String KEY_EXTRAS_BUNDLE = "extras";
            static final String KEY_SENDER = "sender";
            static final String KEY_TEXT = "text";
            static final String KEY_TIMESTAMP = "time";
            private String mDataMimeType;
            private Uri mDataUri;
            private Bundle mExtras = new Bundle();
            private final CharSequence mSender;
            private final CharSequence mText;
            private final long mTimestamp;

            public Message(CharSequence text, long timestamp, CharSequence sender) {
                this.mText = text;
                this.mTimestamp = timestamp;
                this.mSender = sender;
            }

            public Message setData(String dataMimeType, Uri dataUri) {
                this.mDataMimeType = dataMimeType;
                this.mDataUri = dataUri;
                return this;
            }

            public CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public CharSequence getSender() {
                return this.mSender;
            }

            public String getDataMimeType() {
                return this.mDataMimeType;
            }

            public Uri getDataUri() {
                return this.mDataUri;
            }

            private Bundle toBundle() {
                Bundle bundle = new Bundle();
                if (this.mText != null) {
                    bundle.putCharSequence(KEY_TEXT, this.mText);
                }
                bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
                if (this.mSender != null) {
                    bundle.putCharSequence(KEY_SENDER, this.mSender);
                }
                if (this.mDataMimeType != null) {
                    bundle.putString(KEY_DATA_MIME_TYPE, this.mDataMimeType);
                }
                if (this.mDataUri != null) {
                    bundle.putParcelable(KEY_DATA_URI, this.mDataUri);
                }
                if (this.mExtras != null) {
                    bundle.putBundle(KEY_EXTRAS_BUNDLE, this.mExtras);
                }
                return bundle;
            }

            static Bundle[] getBundleArrayForMessages(List<Message> messages) {
                Bundle[] bundles = new Bundle[messages.size()];
                int N = messages.size();
                for (int i = 0; i < N; i++) {
                    bundles[i] = ((Message) messages.get(i)).toBundle();
                }
                return bundles;
            }

            static List<Message> getMessagesFromBundleArray(Parcelable[] bundles) {
                List<Message> messages = new ArrayList(bundles.length);
                for (int i = 0; i < bundles.length; i++) {
                    if (bundles[i] instanceof Bundle) {
                        Message message = getMessageFromBundle((Bundle) bundles[i]);
                        if (message != null) {
                            messages.add(message);
                        }
                    }
                }
                return messages;
            }

            static Message getMessageFromBundle(Bundle bundle) {
                try {
                    if (bundle.containsKey(KEY_TEXT)) {
                        if (bundle.containsKey(KEY_TIMESTAMP)) {
                            Message message = new Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), bundle.getCharSequence(KEY_SENDER));
                            if (bundle.containsKey(KEY_DATA_MIME_TYPE) && bundle.containsKey(KEY_DATA_URI)) {
                                message.setData(bundle.getString(KEY_DATA_MIME_TYPE), (Uri) bundle.getParcelable(KEY_DATA_URI));
                            }
                            if (bundle.containsKey(KEY_EXTRAS_BUNDLE)) {
                                message.getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE));
                            }
                            return message;
                        }
                    }
                    return null;
                } catch (ClassCastException e) {
                    return null;
                }
            }
        }

        MessagingStyle() {
        }

        public MessagingStyle(@NonNull CharSequence userDisplayName) {
            this.mUserDisplayName = userDisplayName;
        }

        public CharSequence getUserDisplayName() {
            return this.mUserDisplayName;
        }

        public MessagingStyle setConversationTitle(CharSequence conversationTitle) {
            this.mConversationTitle = conversationTitle;
            return this;
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public MessagingStyle addMessage(CharSequence text, long timestamp, CharSequence sender) {
            this.mMessages.add(new Message(text, timestamp, sender));
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public MessagingStyle addMessage(Message message) {
            this.mMessages.add(message);
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public List<Message> getMessages() {
            return this.mMessages;
        }

        public static MessagingStyle extractMessagingStyleFromNotification(Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            if (extras != null && !extras.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)) {
                return null;
            }
            try {
                MessagingStyle style = new MessagingStyle();
                style.restoreFromCompatExtras(extras);
                return style;
            } catch (ClassCastException e) {
                return null;
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public void apply(NotificationBuilderWithBuilderAccessor builder) {
            if (VERSION.SDK_INT >= 24) {
                android.app.Notification.MessagingStyle style = new android.app.Notification.MessagingStyle(this.mUserDisplayName).setConversationTitle(this.mConversationTitle);
                for (Message message : this.mMessages) {
                    android.app.Notification.MessagingStyle.Message frameworkMessage = new android.app.Notification.MessagingStyle.Message(message.getText(), message.getTimestamp(), message.getSender());
                    if (message.getDataMimeType() != null) {
                        frameworkMessage.setData(message.getDataMimeType(), message.getDataUri());
                    }
                    style.addMessage(frameworkMessage);
                }
                style.setBuilder(builder.getBuilder());
                return;
            }
            Message latestIncomingMessage = findLatestIncomingMessage();
            if (this.mConversationTitle != null) {
                builder.getBuilder().setContentTitle(this.mConversationTitle);
            } else if (latestIncomingMessage != null) {
                builder.getBuilder().setContentTitle(latestIncomingMessage.getSender());
            }
            if (latestIncomingMessage != null) {
                CharSequence makeMessageLine;
                android.app.Notification.Builder builder2 = builder.getBuilder();
                if (this.mConversationTitle != null) {
                    makeMessageLine = makeMessageLine(latestIncomingMessage);
                } else {
                    makeMessageLine = latestIncomingMessage.getText();
                }
                builder2.setContentText(makeMessageLine);
            }
            if (VERSION.SDK_INT >= 16) {
                boolean showNames;
                int i;
                Message message2;
                CharSequence line;
                SpannableStringBuilder completeMessage = new SpannableStringBuilder();
                if (this.mConversationTitle == null) {
                    if (!hasMessagesWithoutSender()) {
                        showNames = false;
                        for (i = this.mMessages.size() - 1; i >= 0; i--) {
                            message2 = (Message) this.mMessages.get(i);
                            line = showNames ? makeMessageLine(message2) : message2.getText();
                            if (i != this.mMessages.size() - 1) {
                                completeMessage.insert(0, "\n");
                            }
                            completeMessage.insert(0, line);
                        }
                        new android.app.Notification.BigTextStyle(builder.getBuilder()).setBigContentTitle(null).bigText(completeMessage);
                    }
                }
                showNames = true;
                for (i = this.mMessages.size() - 1; i >= 0; i--) {
                    message2 = (Message) this.mMessages.get(i);
                    if (showNames) {
                    }
                    if (i != this.mMessages.size() - 1) {
                        completeMessage.insert(0, "\n");
                    }
                    completeMessage.insert(0, line);
                }
                new android.app.Notification.BigTextStyle(builder.getBuilder()).setBigContentTitle(null).bigText(completeMessage);
            }
        }

        @Nullable
        private Message findLatestIncomingMessage() {
            for (int i = this.mMessages.size() - 1; i >= 0; i--) {
                Message message = (Message) this.mMessages.get(i);
                if (!TextUtils.isEmpty(message.getSender())) {
                    return message;
                }
            }
            if (this.mMessages.isEmpty()) {
                return null;
            }
            return (Message) this.mMessages.get(this.mMessages.size() - 1);
        }

        private boolean hasMessagesWithoutSender() {
            for (int i = this.mMessages.size() - 1; i >= 0; i--) {
                if (((Message) this.mMessages.get(i)).getSender() == null) {
                    return true;
                }
            }
            return false;
        }

        private CharSequence makeMessageLine(Message message) {
            BidiFormatter bidi = BidiFormatter.getInstance();
            SpannableStringBuilder sb = new SpannableStringBuilder();
            boolean afterLollipop = VERSION.SDK_INT >= 21;
            int color = afterLollipop ? ViewCompat.MEASURED_STATE_MASK : -1;
            CharSequence replyName = message.getSender();
            if (TextUtils.isEmpty(message.getSender())) {
                replyName = this.mUserDisplayName == null ? "" : this.mUserDisplayName;
                int color2 = (!afterLollipop || this.mBuilder.getColor() == 0) ? color : this.mBuilder.getColor();
                color = color2;
            }
            CharSequence senderText = bidi.unicodeWrap(replyName);
            sb.append(senderText);
            sb.setSpan(makeFontColorSpan(color), sb.length() - senderText.length(), sb.length(), 33);
            sb.append("  ").append(bidi.unicodeWrap(message.getText() == null ? "" : message.getText()));
            return sb;
        }

        @NonNull
        private TextAppearanceSpan makeFontColorSpan(int color) {
            return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(color), null);
        }

        public void addCompatExtras(Bundle extras) {
            super.addCompatExtras(extras);
            if (this.mUserDisplayName != null) {
                extras.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUserDisplayName);
            }
            if (this.mConversationTitle != null) {
                extras.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                extras.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, Message.getBundleArrayForMessages(this.mMessages));
            }
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        protected void restoreFromCompatExtras(Bundle extras) {
            this.mMessages.clear();
            this.mUserDisplayName = extras.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            this.mConversationTitle = extras.getString(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            Parcelable[] parcelables = extras.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            if (parcelables != null) {
                this.mMessages = Message.getMessagesFromBundleArray(parcelables);
            }
        }
    }

    public static final class WearableExtender implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions = new ArrayList();
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex = -1;
        private int mContentIcon;
        private int mContentIconGravity = 8388613;
        private int mCustomContentHeight;
        private int mCustomSizePreset = 0;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags = 1;
        private int mGravity = 80;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages = new ArrayList();

        public WearableExtender(Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            Bundle wearableBundle = extras != null ? extras.getBundle(EXTRA_WEARABLE_EXTENSIONS) : null;
            if (wearableBundle != null) {
                ArrayList<Parcelable> parcelables = wearableBundle.getParcelableArrayList(KEY_ACTIONS);
                if (VERSION.SDK_INT >= 16 && parcelables != null) {
                    Action[] actions = new Action[parcelables.size()];
                    for (int i = 0; i < actions.length; i++) {
                        if (VERSION.SDK_INT >= 20) {
                            actions[i] = NotificationCompat.getActionCompatFromAction((android.app.Notification.Action) parcelables.get(i));
                        } else if (VERSION.SDK_INT >= 16) {
                            actions[i] = NotificationCompatJellybean.getActionFromBundle((Bundle) parcelables.get(i));
                        }
                    }
                    Collections.addAll(this.mActions, actions);
                }
                this.mFlags = wearableBundle.getInt(KEY_FLAGS, 1);
                this.mDisplayIntent = (PendingIntent) wearableBundle.getParcelable(KEY_DISPLAY_INTENT);
                Notification[] pages = NotificationCompat.getNotificationArrayFromBundle(wearableBundle, KEY_PAGES);
                if (pages != null) {
                    Collections.addAll(this.mPages, pages);
                }
                this.mBackground = (Bitmap) wearableBundle.getParcelable(KEY_BACKGROUND);
                this.mContentIcon = wearableBundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = wearableBundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = wearableBundle.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = wearableBundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = wearableBundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = wearableBundle.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = wearableBundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = wearableBundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = wearableBundle.getString(KEY_BRIDGE_TAG);
            }
        }

        public Builder extend(Builder builder) {
            Bundle wearableBundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                if (VERSION.SDK_INT >= 16) {
                    ArrayList<Parcelable> parcelables = new ArrayList(this.mActions.size());
                    Iterator it = this.mActions.iterator();
                    while (it.hasNext()) {
                        Action action = (Action) it.next();
                        if (VERSION.SDK_INT >= 20) {
                            parcelables.add(getActionFromActionCompat(action));
                        } else if (VERSION.SDK_INT >= 16) {
                            parcelables.add(NotificationCompatJellybean.getBundleForAction(action));
                        }
                    }
                    wearableBundle.putParcelableArrayList(KEY_ACTIONS, parcelables);
                } else {
                    wearableBundle.putParcelableArrayList(KEY_ACTIONS, null);
                }
            }
            if (this.mFlags != 1) {
                wearableBundle.putInt(KEY_FLAGS, this.mFlags);
            }
            if (this.mDisplayIntent != null) {
                wearableBundle.putParcelable(KEY_DISPLAY_INTENT, this.mDisplayIntent);
            }
            if (!this.mPages.isEmpty()) {
                wearableBundle.putParcelableArray(KEY_PAGES, (Parcelable[]) this.mPages.toArray(new Notification[this.mPages.size()]));
            }
            if (this.mBackground != null) {
                wearableBundle.putParcelable(KEY_BACKGROUND, this.mBackground);
            }
            if (this.mContentIcon != 0) {
                wearableBundle.putInt(KEY_CONTENT_ICON, this.mContentIcon);
            }
            if (this.mContentIconGravity != 8388613) {
                wearableBundle.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity);
            }
            if (this.mContentActionIndex != -1) {
                wearableBundle.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex);
            }
            if (this.mCustomSizePreset != 0) {
                wearableBundle.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset);
            }
            if (this.mCustomContentHeight != 0) {
                wearableBundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight);
            }
            if (this.mGravity != 80) {
                wearableBundle.putInt(KEY_GRAVITY, this.mGravity);
            }
            if (this.mHintScreenTimeout != 0) {
                wearableBundle.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout);
            }
            if (this.mDismissalId != null) {
                wearableBundle.putString(KEY_DISMISSAL_ID, this.mDismissalId);
            }
            if (this.mBridgeTag != null) {
                wearableBundle.putString(KEY_BRIDGE_TAG, this.mBridgeTag);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
            return builder;
        }

        @RequiresApi(20)
        private static android.app.Notification.Action getActionFromActionCompat(Action actionCompat) {
            Bundle actionExtras;
            android.app.Notification.Action.Builder actionBuilder = new android.app.Notification.Action.Builder(actionCompat.getIcon(), actionCompat.getTitle(), actionCompat.getActionIntent());
            if (actionCompat.getExtras() != null) {
                actionExtras = new Bundle(actionCompat.getExtras());
            } else {
                actionExtras = new Bundle();
            }
            actionExtras.putBoolean("android.support.allowGeneratedReplies", actionCompat.getAllowGeneratedReplies());
            if (VERSION.SDK_INT >= 24) {
                actionBuilder.setAllowGeneratedReplies(actionCompat.getAllowGeneratedReplies());
            }
            actionBuilder.addExtras(actionExtras);
            RemoteInput[] remoteInputCompats = actionCompat.getRemoteInputs();
            if (remoteInputCompats != null) {
                for (RemoteInput remoteInput : RemoteInput.fromCompat(remoteInputCompats)) {
                    actionBuilder.addRemoteInput(remoteInput);
                }
            }
            return actionBuilder.build();
        }

        public WearableExtender clone() {
            WearableExtender that = new WearableExtender();
            that.mActions = new ArrayList(this.mActions);
            that.mFlags = this.mFlags;
            that.mDisplayIntent = this.mDisplayIntent;
            that.mPages = new ArrayList(this.mPages);
            that.mBackground = this.mBackground;
            that.mContentIcon = this.mContentIcon;
            that.mContentIconGravity = this.mContentIconGravity;
            that.mContentActionIndex = this.mContentActionIndex;
            that.mCustomSizePreset = this.mCustomSizePreset;
            that.mCustomContentHeight = this.mCustomContentHeight;
            that.mGravity = this.mGravity;
            that.mHintScreenTimeout = this.mHintScreenTimeout;
            that.mDismissalId = this.mDismissalId;
            that.mBridgeTag = this.mBridgeTag;
            return that;
        }

        public WearableExtender addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public WearableExtender addActions(List<Action> actions) {
            this.mActions.addAll(actions);
            return this;
        }

        public WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        public List<Action> getActions() {
            return this.mActions;
        }

        public WearableExtender setDisplayIntent(PendingIntent intent) {
            this.mDisplayIntent = intent;
            return this;
        }

        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        public WearableExtender addPage(Notification page) {
            this.mPages.add(page);
            return this;
        }

        public WearableExtender addPages(List<Notification> pages) {
            this.mPages.addAll(pages);
            return this;
        }

        public WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        public List<Notification> getPages() {
            return this.mPages;
        }

        public WearableExtender setBackground(Bitmap background) {
            this.mBackground = background;
            return this;
        }

        public Bitmap getBackground() {
            return this.mBackground;
        }

        public WearableExtender setContentIcon(int icon) {
            this.mContentIcon = icon;
            return this;
        }

        public int getContentIcon() {
            return this.mContentIcon;
        }

        public WearableExtender setContentIconGravity(int contentIconGravity) {
            this.mContentIconGravity = contentIconGravity;
            return this;
        }

        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public WearableExtender setContentAction(int actionIndex) {
            this.mContentActionIndex = actionIndex;
            return this;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        public WearableExtender setGravity(int gravity) {
            this.mGravity = gravity;
            return this;
        }

        public int getGravity() {
            return this.mGravity;
        }

        public WearableExtender setCustomSizePreset(int sizePreset) {
            this.mCustomSizePreset = sizePreset;
            return this;
        }

        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        public WearableExtender setCustomContentHeight(int height) {
            this.mCustomContentHeight = height;
            return this;
        }

        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public WearableExtender setStartScrollBottom(boolean startScrollBottom) {
            setFlag(8, startScrollBottom);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean contentIntentAvailableOffline) {
            setFlag(1, contentIntentAvailableOffline);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        public WearableExtender setHintHideIcon(boolean hintHideIcon) {
            setFlag(2, hintHideIcon);
            return this;
        }

        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        public WearableExtender setHintShowBackgroundOnly(boolean hintShowBackgroundOnly) {
            setFlag(4, hintShowBackgroundOnly);
            return this;
        }

        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        public WearableExtender setHintAvoidBackgroundClipping(boolean hintAvoidBackgroundClipping) {
            setFlag(16, hintAvoidBackgroundClipping);
            return this;
        }

        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        public WearableExtender setHintScreenTimeout(int timeout) {
            this.mHintScreenTimeout = timeout;
            return this;
        }

        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        public WearableExtender setHintAmbientBigPicture(boolean hintAmbientBigPicture) {
            setFlag(32, hintAmbientBigPicture);
            return this;
        }

        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        public WearableExtender setHintContentIntentLaunchesActivity(boolean hintContentIntentLaunchesActivity) {
            setFlag(64, hintContentIntentLaunchesActivity);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        public WearableExtender setDismissalId(String dismissalId) {
            this.mDismissalId = dismissalId;
            return this;
        }

        public String getDismissalId() {
            return this.mDismissalId;
        }

        public WearableExtender setBridgeTag(String bridgeTag) {
            this.mBridgeTag = bridgeTag;
            return this;
        }

        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        private void setFlag(int mask, boolean value) {
            if (value) {
                this.mFlags |= mask;
            } else {
                this.mFlags &= ~mask;
            }
        }
    }

    static Notification[] getNotificationArrayFromBundle(Bundle bundle, String key) {
        Parcelable[] array = bundle.getParcelableArray(key);
        if (!(array instanceof Notification[])) {
            if (array != null) {
                Notification[] typedArray = new Notification[array.length];
                for (int i = 0; i < array.length; i++) {
                    typedArray[i] = (Notification) array[i];
                }
                bundle.putParcelableArray(key, typedArray);
                return typedArray;
            }
        }
        return (Notification[]) array;
    }

    public static Bundle getExtras(Notification notification) {
        if (VERSION.SDK_INT >= 19) {
            return notification.extras;
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification);
        }
        return null;
    }

    public static int getActionCount(Notification notification) {
        int i = 0;
        if (VERSION.SDK_INT >= 19) {
            if (notification.actions != null) {
                i = notification.actions.length;
            }
            return i;
        } else if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getActionCount(notification);
        } else {
            return 0;
        }
    }

    public static Action getAction(Notification notification, int actionIndex) {
        if (VERSION.SDK_INT >= 20) {
            return getActionCompatFromAction(notification.actions[actionIndex]);
        }
        if (VERSION.SDK_INT >= 19) {
            android.app.Notification.Action action = notification.actions[actionIndex];
            Bundle actionExtras = null;
            SparseArray<Bundle> actionExtrasMap = notification.extras.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
            if (actionExtrasMap != null) {
                actionExtras = (Bundle) actionExtrasMap.get(actionIndex);
            }
            return NotificationCompatJellybean.readAction(action.icon, action.title, action.actionIntent, actionExtras);
        } else if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getAction(notification, actionIndex);
        } else {
            return null;
        }
    }

    @RequiresApi(20)
    static Action getActionCompatFromAction(android.app.Notification.Action action) {
        RemoteInput[] remoteInputs;
        RemoteInput[] srcArray = action.getRemoteInputs();
        boolean z = false;
        if (srcArray == null) {
            remoteInputs = null;
        } else {
            remoteInputs = new RemoteInput[srcArray.length];
            for (int i = 0; i < srcArray.length; i++) {
                RemoteInput src = srcArray[i];
                remoteInputs[i] = new RemoteInput(src.getResultKey(), src.getLabel(), src.getChoices(), src.getAllowFreeFormInput(), src.getExtras(), null);
            }
        }
        if (VERSION.SDK_INT >= 24) {
            if (!action.getExtras().getBoolean("android.support.allowGeneratedReplies")) {
                if (action.getAllowGeneratedReplies()) {
                }
            }
            z = true;
        } else {
            z = action.getExtras().getBoolean("android.support.allowGeneratedReplies");
        }
        return new Action(action.icon, action.title, action.actionIntent, action.getExtras(), remoteInputs, null, z);
    }

    public static String getCategory(Notification notification) {
        if (VERSION.SDK_INT >= 21) {
            return notification.category;
        }
        return null;
    }

    public static boolean getLocalOnly(Notification notification) {
        boolean z = false;
        if (VERSION.SDK_INT >= 20) {
            if ((notification.flags & 256) != 0) {
                z = true;
            }
            return z;
        } else if (VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
        } else {
            if (VERSION.SDK_INT >= 16) {
                return NotificationCompatJellybean.getExtras(notification).getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
            }
            return false;
        }
    }

    public static String getGroup(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return notification.getGroup();
        }
        if (VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        return null;
    }

    public static boolean isGroupSummary(Notification notification) {
        boolean z = false;
        if (VERSION.SDK_INT >= 20) {
            if ((notification.flags & 512) != 0) {
                z = true;
            }
            return z;
        } else if (VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
        } else {
            if (VERSION.SDK_INT >= 16) {
                return NotificationCompatJellybean.getExtras(notification).getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
            }
            return false;
        }
    }

    public static String getSortKey(Notification notification) {
        if (VERSION.SDK_INT >= 20) {
            return notification.getSortKey();
        }
        if (VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        if (VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        return null;
    }

    public static String getChannelId(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getChannelId();
        }
        return null;
    }

    public static long getTimeoutAfter(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getTimeoutAfter();
        }
        return 0;
    }

    public static int getBadgeIconType(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getBadgeIconType();
        }
        return 0;
    }

    public static String getShortcutId(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getShortcutId();
        }
        return null;
    }

    public static int getGroupAlertBehavior(Notification notification) {
        if (VERSION.SDK_INT >= 26) {
            return notification.getGroupAlertBehavior();
        }
        return 0;
    }
}
