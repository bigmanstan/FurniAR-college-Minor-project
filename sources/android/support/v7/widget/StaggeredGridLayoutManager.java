package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager extends LayoutManager implements ScrollVectorProvider {
    static final boolean DEBUG = false;
    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "StaggeredGridLManager";
    public static final int VERTICAL = 1;
    private final AnchorInfo mAnchorInfo = new AnchorInfo();
    private final Runnable mCheckForGapsRunnable = new C03331();
    private int mFullSizeSpec;
    private int mGapStrategy = 2;
    private boolean mLaidOutInvalidFullSpan = false;
    private boolean mLastLayoutFromEnd;
    private boolean mLastLayoutRTL;
    @NonNull
    private final LayoutState mLayoutState;
    LazySpanLookup mLazySpanLookup = new LazySpanLookup();
    private int mOrientation;
    private SavedState mPendingSavedState;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    private int[] mPrefetchDistances;
    @NonNull
    OrientationHelper mPrimaryOrientation;
    private BitSet mRemainingSpans;
    boolean mReverseLayout = false;
    @NonNull
    OrientationHelper mSecondaryOrientation;
    boolean mShouldReverseLayout = false;
    private int mSizePerSpan;
    private boolean mSmoothScrollbarEnabled = true;
    private int mSpanCount = -1;
    Span[] mSpans;
    private final Rect mTmpRect = new Rect();

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$1 */
    class C03331 implements Runnable {
        C03331() {
        }

        public void run() {
            StaggeredGridLayoutManager.this.checkForGaps();
        }
    }

    class AnchorInfo {
        boolean mInvalidateOffsets;
        boolean mLayoutFromEnd;
        int mOffset;
        int mPosition;
        int[] mSpanReferenceLines;
        boolean mValid;

        AnchorInfo() {
            reset();
        }

        void reset() {
            this.mPosition = -1;
            this.mOffset = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
            this.mValid = false;
            if (this.mSpanReferenceLines != null) {
                Arrays.fill(this.mSpanReferenceLines, -1);
            }
        }

        void saveSpanReferenceLines(Span[] spans) {
            int spanCount = spans.length;
            if (this.mSpanReferenceLines == null || this.mSpanReferenceLines.length < spanCount) {
                this.mSpanReferenceLines = new int[StaggeredGridLayoutManager.this.mSpans.length];
            }
            for (int i = 0; i < spanCount; i++) {
                this.mSpanReferenceLines[i] = spans[i].getStartLine(Integer.MIN_VALUE);
            }
        }

        void assignCoordinateFromPadding() {
            int endAfterPadding;
            if (this.mLayoutFromEnd) {
                endAfterPadding = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
            } else {
                endAfterPadding = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            }
            this.mOffset = endAfterPadding;
        }

        void assignCoordinateFromPadding(int addedDistance) {
            if (this.mLayoutFromEnd) {
                this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - addedDistance;
            } else {
                this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + addedDistance;
            }
        }
    }

    static class LazySpanLookup {
        private static final int MIN_SIZE = 10;
        int[] mData;
        List<FullSpanItem> mFullSpanItems;

        static class FullSpanItem implements Parcelable {
            public static final Creator<FullSpanItem> CREATOR = new C03341();
            int mGapDir;
            int[] mGapPerSpan;
            boolean mHasUnwantedGapAfter;
            int mPosition;

            /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem$1 */
            static class C03341 implements Creator<FullSpanItem> {
                C03341() {
                }

                public FullSpanItem createFromParcel(Parcel in) {
                    return new FullSpanItem(in);
                }

                public FullSpanItem[] newArray(int size) {
                    return new FullSpanItem[size];
                }
            }

            FullSpanItem(Parcel in) {
                this.mPosition = in.readInt();
                this.mGapDir = in.readInt();
                boolean z = true;
                if (in.readInt() != 1) {
                    z = false;
                }
                this.mHasUnwantedGapAfter = z;
                int spanCount = in.readInt();
                if (spanCount > 0) {
                    this.mGapPerSpan = new int[spanCount];
                    in.readIntArray(this.mGapPerSpan);
                }
            }

            FullSpanItem() {
            }

            int getGapForSpan(int spanIndex) {
                return this.mGapPerSpan == null ? 0 : this.mGapPerSpan[spanIndex];
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.mPosition);
                dest.writeInt(this.mGapDir);
                dest.writeInt(this.mHasUnwantedGapAfter);
                if (this.mGapPerSpan == null || this.mGapPerSpan.length <= 0) {
                    dest.writeInt(0);
                    return;
                }
                dest.writeInt(this.mGapPerSpan.length);
                dest.writeIntArray(this.mGapPerSpan);
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FullSpanItem{mPosition=");
                stringBuilder.append(this.mPosition);
                stringBuilder.append(", mGapDir=");
                stringBuilder.append(this.mGapDir);
                stringBuilder.append(", mHasUnwantedGapAfter=");
                stringBuilder.append(this.mHasUnwantedGapAfter);
                stringBuilder.append(", mGapPerSpan=");
                stringBuilder.append(Arrays.toString(this.mGapPerSpan));
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
        }

        LazySpanLookup() {
        }

        int forceInvalidateAfter(int position) {
            if (this.mFullSpanItems != null) {
                for (int i = this.mFullSpanItems.size() - 1; i >= 0; i--) {
                    if (((FullSpanItem) this.mFullSpanItems.get(i)).mPosition >= position) {
                        this.mFullSpanItems.remove(i);
                    }
                }
            }
            return invalidateAfter(position);
        }

        int invalidateAfter(int position) {
            if (this.mData == null || position >= this.mData.length) {
                return -1;
            }
            int endPosition = invalidateFullSpansAfter(position);
            if (endPosition == -1) {
                Arrays.fill(this.mData, position, this.mData.length, -1);
                return this.mData.length;
            }
            Arrays.fill(this.mData, position, endPosition + 1, -1);
            return endPosition + 1;
        }

        int getSpan(int position) {
            if (this.mData != null) {
                if (position < this.mData.length) {
                    return this.mData[position];
                }
            }
            return -1;
        }

        void setSpan(int position, Span span) {
            ensureSize(position);
            this.mData[position] = span.mIndex;
        }

        int sizeForPosition(int position) {
            int len = this.mData.length;
            while (len <= position) {
                len *= 2;
            }
            return len;
        }

        void ensureSize(int position) {
            if (this.mData == null) {
                this.mData = new int[(Math.max(position, 10) + 1)];
                Arrays.fill(this.mData, -1);
            } else if (position >= this.mData.length) {
                int[] old = this.mData;
                this.mData = new int[sizeForPosition(position)];
                System.arraycopy(old, 0, this.mData, 0, old.length);
                Arrays.fill(this.mData, old.length, this.mData.length, -1);
            }
        }

        void clear() {
            if (this.mData != null) {
                Arrays.fill(this.mData, -1);
            }
            this.mFullSpanItems = null;
        }

        void offsetForRemoval(int positionStart, int itemCount) {
            if (this.mData != null) {
                if (positionStart < this.mData.length) {
                    ensureSize(positionStart + itemCount);
                    System.arraycopy(this.mData, positionStart + itemCount, this.mData, positionStart, (this.mData.length - positionStart) - itemCount);
                    Arrays.fill(this.mData, this.mData.length - itemCount, this.mData.length, -1);
                    offsetFullSpansForRemoval(positionStart, itemCount);
                }
            }
        }

        private void offsetFullSpansForRemoval(int positionStart, int itemCount) {
            if (this.mFullSpanItems != null) {
                int end = positionStart + itemCount;
                for (int i = this.mFullSpanItems.size() - 1; i >= 0; i--) {
                    FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(i);
                    if (fsi.mPosition >= positionStart) {
                        if (fsi.mPosition < end) {
                            this.mFullSpanItems.remove(i);
                        } else {
                            fsi.mPosition -= itemCount;
                        }
                    }
                }
            }
        }

        void offsetForAddition(int positionStart, int itemCount) {
            if (this.mData != null) {
                if (positionStart < this.mData.length) {
                    ensureSize(positionStart + itemCount);
                    System.arraycopy(this.mData, positionStart, this.mData, positionStart + itemCount, (this.mData.length - positionStart) - itemCount);
                    Arrays.fill(this.mData, positionStart, positionStart + itemCount, -1);
                    offsetFullSpansForAddition(positionStart, itemCount);
                }
            }
        }

        private void offsetFullSpansForAddition(int positionStart, int itemCount) {
            if (this.mFullSpanItems != null) {
                for (int i = this.mFullSpanItems.size() - 1; i >= 0; i--) {
                    FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(i);
                    if (fsi.mPosition >= positionStart) {
                        fsi.mPosition += itemCount;
                    }
                }
            }
        }

        private int invalidateFullSpansAfter(int position) {
            if (this.mFullSpanItems == null) {
                return -1;
            }
            FullSpanItem item = getFullSpanItem(position);
            if (item != null) {
                this.mFullSpanItems.remove(item);
            }
            int nextFsiIndex = -1;
            int count = this.mFullSpanItems.size();
            for (int i = 0; i < count; i++) {
                if (((FullSpanItem) this.mFullSpanItems.get(i)).mPosition >= position) {
                    nextFsiIndex = i;
                    break;
                }
            }
            if (nextFsiIndex == -1) {
                return -1;
            }
            FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(nextFsiIndex);
            this.mFullSpanItems.remove(nextFsiIndex);
            return fsi.mPosition;
        }

        public void addFullSpanItem(FullSpanItem fullSpanItem) {
            if (this.mFullSpanItems == null) {
                this.mFullSpanItems = new ArrayList();
            }
            int size = this.mFullSpanItems.size();
            for (int i = 0; i < size; i++) {
                FullSpanItem other = (FullSpanItem) this.mFullSpanItems.get(i);
                if (other.mPosition == fullSpanItem.mPosition) {
                    this.mFullSpanItems.remove(i);
                }
                if (other.mPosition >= fullSpanItem.mPosition) {
                    this.mFullSpanItems.add(i, fullSpanItem);
                    return;
                }
            }
            this.mFullSpanItems.add(fullSpanItem);
        }

        public FullSpanItem getFullSpanItem(int position) {
            if (this.mFullSpanItems == null) {
                return null;
            }
            for (int i = this.mFullSpanItems.size() - 1; i >= 0; i--) {
                FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(i);
                if (fsi.mPosition == position) {
                    return fsi;
                }
            }
            return null;
        }

        public FullSpanItem getFirstFullSpanItemInRange(int minPos, int maxPos, int gapDir, boolean hasUnwantedGapAfter) {
            if (this.mFullSpanItems == null) {
                return null;
            }
            int limit = this.mFullSpanItems.size();
            for (int i = 0; i < limit; i++) {
                FullSpanItem fsi = (FullSpanItem) this.mFullSpanItems.get(i);
                if (fsi.mPosition >= maxPos) {
                    return null;
                }
                if (fsi.mPosition >= minPos && (gapDir == 0 || fsi.mGapDir == gapDir || (hasUnwantedGapAfter && fsi.mHasUnwantedGapAfter))) {
                    return fsi;
                }
            }
            return null;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C03351();
        boolean mAnchorLayoutFromEnd;
        int mAnchorPosition;
        List<FullSpanItem> mFullSpanItems;
        boolean mLastLayoutRTL;
        boolean mReverseLayout;
        int[] mSpanLookup;
        int mSpanLookupSize;
        int[] mSpanOffsets;
        int mSpanOffsetsSize;
        int mVisibleAnchorPosition;

        /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$SavedState$1 */
        static class C03351 implements Creator<SavedState> {
            C03351() {
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        SavedState(Parcel in) {
            this.mAnchorPosition = in.readInt();
            this.mVisibleAnchorPosition = in.readInt();
            this.mSpanOffsetsSize = in.readInt();
            if (this.mSpanOffsetsSize > 0) {
                this.mSpanOffsets = new int[this.mSpanOffsetsSize];
                in.readIntArray(this.mSpanOffsets);
            }
            this.mSpanLookupSize = in.readInt();
            if (this.mSpanLookupSize > 0) {
                this.mSpanLookup = new int[this.mSpanLookupSize];
                in.readIntArray(this.mSpanLookup);
            }
            boolean z = false;
            this.mReverseLayout = in.readInt() == 1;
            this.mAnchorLayoutFromEnd = in.readInt() == 1;
            if (in.readInt() == 1) {
                z = true;
            }
            this.mLastLayoutRTL = z;
            this.mFullSpanItems = in.readArrayList(FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState other) {
            this.mSpanOffsetsSize = other.mSpanOffsetsSize;
            this.mAnchorPosition = other.mAnchorPosition;
            this.mVisibleAnchorPosition = other.mVisibleAnchorPosition;
            this.mSpanOffsets = other.mSpanOffsets;
            this.mSpanLookupSize = other.mSpanLookupSize;
            this.mSpanLookup = other.mSpanLookup;
            this.mReverseLayout = other.mReverseLayout;
            this.mAnchorLayoutFromEnd = other.mAnchorLayoutFromEnd;
            this.mLastLayoutRTL = other.mLastLayoutRTL;
            this.mFullSpanItems = other.mFullSpanItems;
        }

        void invalidateSpanInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mSpanLookupSize = 0;
            this.mSpanLookup = null;
            this.mFullSpanItems = null;
        }

        void invalidateAnchorPositionInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mAnchorPosition = -1;
            this.mVisibleAnchorPosition = -1;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mAnchorPosition);
            dest.writeInt(this.mVisibleAnchorPosition);
            dest.writeInt(this.mSpanOffsetsSize);
            if (this.mSpanOffsetsSize > 0) {
                dest.writeIntArray(this.mSpanOffsets);
            }
            dest.writeInt(this.mSpanLookupSize);
            if (this.mSpanLookupSize > 0) {
                dest.writeIntArray(this.mSpanLookup);
            }
            dest.writeInt(this.mReverseLayout);
            dest.writeInt(this.mAnchorLayoutFromEnd);
            dest.writeInt(this.mLastLayoutRTL);
            dest.writeList(this.mFullSpanItems);
        }
    }

    class Span {
        static final int INVALID_LINE = Integer.MIN_VALUE;
        int mCachedEnd = Integer.MIN_VALUE;
        int mCachedStart = Integer.MIN_VALUE;
        int mDeletedSize = 0;
        final int mIndex;
        ArrayList<View> mViews = new ArrayList();

        Span(int index) {
            this.mIndex = index;
        }

        int getStartLine(int def) {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            if (this.mViews.size() == 0) {
                return def;
            }
            calculateCachedStart();
            return this.mCachedStart;
        }

        void calculateCachedStart() {
            View startView = (View) this.mViews.get(0);
            LayoutParams lp = getLayoutParams(startView);
            this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(startView);
            if (lp.mFullSpan) {
                FullSpanItem fsi = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(lp.getViewLayoutPosition());
                if (fsi != null && fsi.mGapDir == -1) {
                    this.mCachedStart -= fsi.getGapForSpan(this.mIndex);
                }
            }
        }

        int getStartLine() {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                return this.mCachedStart;
            }
            calculateCachedStart();
            return this.mCachedStart;
        }

        int getEndLine(int def) {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            if (this.mViews.size() == 0) {
                return def;
            }
            calculateCachedEnd();
            return this.mCachedEnd;
        }

        void calculateCachedEnd() {
            View endView = (View) this.mViews.get(this.mViews.size() - 1);
            LayoutParams lp = getLayoutParams(endView);
            this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(endView);
            if (lp.mFullSpan) {
                FullSpanItem fsi = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(lp.getViewLayoutPosition());
                if (fsi != null && fsi.mGapDir == 1) {
                    this.mCachedEnd += fsi.getGapForSpan(this.mIndex);
                }
            }
        }

        int getEndLine() {
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                return this.mCachedEnd;
            }
            calculateCachedEnd();
            return this.mCachedEnd;
        }

        void prependToSpan(View view) {
            LayoutParams lp = getLayoutParams(view);
            lp.mSpan = this;
            this.mViews.add(0, view);
            this.mCachedStart = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (lp.isItemRemoved() || lp.isItemChanged()) {
                this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
        }

        void appendToSpan(View view) {
            LayoutParams lp = getLayoutParams(view);
            lp.mSpan = this;
            this.mViews.add(view);
            this.mCachedEnd = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            if (lp.isItemRemoved() || lp.isItemChanged()) {
                this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
        }

        void cacheReferenceLineAndClear(boolean reverseLayout, int offset) {
            int reference;
            if (reverseLayout) {
                reference = getEndLine(Integer.MIN_VALUE);
            } else {
                reference = getStartLine(Integer.MIN_VALUE);
            }
            clear();
            if (reference != Integer.MIN_VALUE) {
                if ((!reverseLayout || reference >= StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding()) && (reverseLayout || reference <= StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding())) {
                    if (offset != Integer.MIN_VALUE) {
                        reference += offset;
                    }
                    this.mCachedEnd = reference;
                    this.mCachedStart = reference;
                }
            }
        }

        void clear() {
            this.mViews.clear();
            invalidateCache();
            this.mDeletedSize = 0;
        }

        void invalidateCache() {
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void setLine(int line) {
            this.mCachedStart = line;
            this.mCachedEnd = line;
        }

        void popEnd() {
            int size = this.mViews.size();
            View end = (View) this.mViews.remove(size - 1);
            LayoutParams lp = getLayoutParams(end);
            lp.mSpan = null;
            if (lp.isItemRemoved() || lp.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(end);
            }
            if (size == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void popStart() {
            View start = (View) this.mViews.remove(0);
            LayoutParams lp = getLayoutParams(start);
            lp.mSpan = null;
            if (this.mViews.size() == 0) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (lp.isItemRemoved() || lp.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(start);
            }
            this.mCachedStart = Integer.MIN_VALUE;
        }

        public int getDeletedSize() {
            return this.mDeletedSize;
        }

        LayoutParams getLayoutParams(View view) {
            return (LayoutParams) view.getLayoutParams();
        }

        void onOffset(int dt) {
            if (this.mCachedStart != Integer.MIN_VALUE) {
                this.mCachedStart += dt;
            }
            if (this.mCachedEnd != Integer.MIN_VALUE) {
                this.mCachedEnd += dt;
            }
        }

        public int findFirstVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return findOneVisibleChild(this.mViews.size() - 1, -1, false);
            }
            return findOneVisibleChild(0, this.mViews.size(), false);
        }

        public int findFirstPartiallyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
            }
            return findOnePartiallyVisibleChild(0, this.mViews.size(), true);
        }

        public int findFirstCompletelyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return findOneVisibleChild(this.mViews.size() - 1, -1, true);
            }
            return findOneVisibleChild(0, this.mViews.size(), true);
        }

        public int findLastVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return findOneVisibleChild(0, this.mViews.size(), false);
            }
            return findOneVisibleChild(this.mViews.size() - 1, -1, false);
        }

        public int findLastPartiallyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return findOnePartiallyVisibleChild(0, this.mViews.size(), true);
            }
            return findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
        }

        public int findLastCompletelyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return findOneVisibleChild(0, this.mViews.size(), true);
            }
            return findOneVisibleChild(this.mViews.size() - 1, -1, true);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        int findOnePartiallyOrCompletelyVisibleChild(int r15, int r16, boolean r17, boolean r18, boolean r19) {
            /*
            r14 = this;
            r0 = r14;
            r1 = r16;
            r2 = android.support.v7.widget.StaggeredGridLayoutManager.this;
            r2 = r2.mPrimaryOrientation;
            r2 = r2.getStartAfterPadding();
            r3 = android.support.v7.widget.StaggeredGridLayoutManager.this;
            r3 = r3.mPrimaryOrientation;
            r3 = r3.getEndAfterPadding();
            r4 = -1;
            r5 = 1;
            r6 = r15;
            if (r1 <= r6) goto L_0x001a;
        L_0x0018:
            r7 = r5;
            goto L_0x001b;
        L_0x001a:
            r7 = r4;
        L_0x001b:
            r8 = r6;
        L_0x001c:
            if (r8 == r1) goto L_0x0075;
        L_0x001e:
            r9 = r0.mViews;
            r9 = r9.get(r8);
            r9 = (android.view.View) r9;
            r10 = android.support.v7.widget.StaggeredGridLayoutManager.this;
            r10 = r10.mPrimaryOrientation;
            r10 = r10.getDecoratedStart(r9);
            r11 = android.support.v7.widget.StaggeredGridLayoutManager.this;
            r11 = r11.mPrimaryOrientation;
            r11 = r11.getDecoratedEnd(r9);
            r12 = 0;
            if (r19 == 0) goto L_0x003f;
        L_0x0039:
            if (r10 > r3) goto L_0x003d;
        L_0x003b:
            r13 = r5;
            goto L_0x0042;
        L_0x003d:
            r13 = r12;
            goto L_0x0042;
        L_0x003f:
            if (r10 >= r3) goto L_0x003d;
        L_0x0041:
            goto L_0x003b;
        L_0x0042:
            if (r19 == 0) goto L_0x0049;
        L_0x0044:
            if (r11 < r2) goto L_0x0048;
        L_0x0046:
            r12 = r5;
            goto L_0x004c;
        L_0x0048:
            goto L_0x004c;
        L_0x0049:
            if (r11 <= r2) goto L_0x0048;
        L_0x004b:
            goto L_0x0046;
        L_0x004c:
            if (r13 == 0) goto L_0x0073;
        L_0x004e:
            if (r12 == 0) goto L_0x0073;
        L_0x0050:
            if (r17 == 0) goto L_0x005f;
        L_0x0052:
            if (r18 == 0) goto L_0x005f;
        L_0x0054:
            if (r10 < r2) goto L_0x0073;
        L_0x0056:
            if (r11 > r3) goto L_0x0073;
        L_0x0058:
            r4 = android.support.v7.widget.StaggeredGridLayoutManager.this;
            r4 = r4.getPosition(r9);
            return r4;
        L_0x005f:
            if (r18 == 0) goto L_0x0068;
        L_0x0061:
            r4 = android.support.v7.widget.StaggeredGridLayoutManager.this;
            r4 = r4.getPosition(r9);
            return r4;
        L_0x0068:
            if (r10 < r2) goto L_0x006c;
        L_0x006a:
            if (r11 <= r3) goto L_0x0073;
        L_0x006c:
            r4 = android.support.v7.widget.StaggeredGridLayoutManager.this;
            r4 = r4.getPosition(r9);
            return r4;
        L_0x0073:
            r8 = r8 + r7;
            goto L_0x001c;
        L_0x0075:
            return r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.Span.findOnePartiallyOrCompletelyVisibleChild(int, int, boolean, boolean, boolean):int");
        }

        int findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible) {
            return findOnePartiallyOrCompletelyVisibleChild(fromIndex, toIndex, completelyVisible, true, false);
        }

        int findOnePartiallyVisibleChild(int fromIndex, int toIndex, boolean acceptEndPointInclusion) {
            return findOnePartiallyOrCompletelyVisibleChild(fromIndex, toIndex, false, false, acceptEndPointInclusion);
        }

        public View getFocusableViewAfter(int referenceChildPosition, int layoutDir) {
            View candidate = null;
            int i;
            if (layoutDir != -1) {
                i = this.mViews.size() - 1;
                while (i >= 0) {
                    View view = (View) this.mViews.get(i);
                    if (!StaggeredGridLayoutManager.this.mReverseLayout || StaggeredGridLayoutManager.this.getPosition(view) < referenceChildPosition) {
                        if (StaggeredGridLayoutManager.this.mReverseLayout || StaggeredGridLayoutManager.this.getPosition(view) > referenceChildPosition) {
                            if (!view.hasFocusable()) {
                                break;
                            }
                            candidate = view;
                            i--;
                        } else {
                            break;
                        }
                    }
                    break;
                }
            }
            i = this.mViews.size();
            int i2 = 0;
            while (i2 < i) {
                View view2 = (View) this.mViews.get(i2);
                if (!StaggeredGridLayoutManager.this.mReverseLayout || StaggeredGridLayoutManager.this.getPosition(view2) > referenceChildPosition) {
                    if (StaggeredGridLayoutManager.this.mReverseLayout || StaggeredGridLayoutManager.this.getPosition(view2) < referenceChildPosition) {
                        if (!view2.hasFocusable()) {
                            break;
                        }
                        candidate = view2;
                        i2++;
                    } else {
                        break;
                    }
                }
                break;
            }
            return candidate;
        }
    }

    public static class LayoutParams extends android.support.v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        boolean mFullSpan;
        Span mSpan;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(android.support.v7.widget.RecyclerView.LayoutParams source) {
            super(source);
        }

        public void setFullSpan(boolean fullSpan) {
            this.mFullSpan = fullSpan;
        }

        public boolean isFullSpan() {
            return this.mFullSpan;
        }

        public final int getSpanIndex() {
            if (this.mSpan == null) {
                return -1;
            }
            return this.mSpan.mIndex;
        }
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Properties properties = LayoutManager.getProperties(context, attrs, defStyleAttr, defStyleRes);
        setOrientation(properties.orientation);
        setSpanCount(properties.spanCount);
        setReverseLayout(properties.reverseLayout);
        this.mLayoutState = new LayoutState();
        createOrientationHelpers();
    }

    public StaggeredGridLayoutManager(int spanCount, int orientation) {
        this.mOrientation = orientation;
        setSpanCount(spanCount);
        this.mLayoutState = new LayoutState();
        createOrientationHelpers();
    }

    public boolean isAutoMeasureEnabled() {
        return this.mGapStrategy != 0;
    }

    private void createOrientationHelpers() {
        this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }

    boolean checkForGaps() {
        if (!(getChildCount() == 0 || this.mGapStrategy == 0)) {
            if (isAttachedToWindow()) {
                int minPos;
                int maxPos;
                if (this.mShouldReverseLayout) {
                    minPos = getLastChildPosition();
                    maxPos = getFirstChildPosition();
                } else {
                    minPos = getFirstChildPosition();
                    maxPos = getLastChildPosition();
                }
                if (minPos == 0 && hasGapsToFix() != null) {
                    this.mLazySpanLookup.clear();
                    requestSimpleAnimationsInNextLayout();
                    requestLayout();
                    return true;
                } else if (!this.mLaidOutInvalidFullSpan) {
                    return false;
                } else {
                    int invalidGapDir = this.mShouldReverseLayout ? -1 : 1;
                    FullSpanItem invalidFsi = this.mLazySpanLookup.getFirstFullSpanItemInRange(minPos, maxPos + 1, invalidGapDir, true);
                    if (invalidFsi == null) {
                        this.mLaidOutInvalidFullSpan = false;
                        this.mLazySpanLookup.forceInvalidateAfter(maxPos + 1);
                        return false;
                    }
                    FullSpanItem validFsi = this.mLazySpanLookup.getFirstFullSpanItemInRange(minPos, invalidFsi.mPosition, invalidGapDir * -1, true);
                    if (validFsi == null) {
                        this.mLazySpanLookup.forceInvalidateAfter(invalidFsi.mPosition);
                    } else {
                        this.mLazySpanLookup.forceInvalidateAfter(validFsi.mPosition + 1);
                    }
                    requestSimpleAnimationsInNextLayout();
                    requestLayout();
                    return true;
                }
            }
        }
        return false;
    }

    public void onScrollStateChanged(int state) {
        if (state == 0) {
            checkForGaps();
        }
    }

    public void onDetachedFromWindow(RecyclerView view, Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        removeCallbacks(this.mCheckForGapsRunnable);
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].clear();
        }
        view.requestLayout();
    }

    View hasGapsToFix() {
        int firstChildIndex;
        int childLimit;
        int endChildIndex = getChildCount() - 1;
        BitSet mSpansToCheck = new BitSet(this.mSpanCount);
        mSpansToCheck.set(0, this.mSpanCount, true);
        int nextChildDiff = -1;
        int preferredSpanDir = (this.mOrientation == 1 && isLayoutRTL()) ? 1 : -1;
        if (r0.mShouldReverseLayout) {
            firstChildIndex = endChildIndex;
            childLimit = 0 - 1;
        } else {
            firstChildIndex = 0;
            childLimit = endChildIndex + 1;
        }
        if (firstChildIndex < childLimit) {
            nextChildDiff = 1;
        }
        for (int i = firstChildIndex; i != childLimit; i += nextChildDiff) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (mSpansToCheck.get(lp.mSpan.mIndex)) {
                if (checkSpanForGap(lp.mSpan)) {
                    return child;
                }
                mSpansToCheck.clear(lp.mSpan.mIndex);
            }
            if (!lp.mFullSpan) {
                if (i + nextChildDiff != childLimit) {
                    View nextChild = getChildAt(i + nextChildDiff);
                    boolean compareSpans = false;
                    int myEnd;
                    int nextEnd;
                    if (r0.mShouldReverseLayout) {
                        myEnd = r0.mPrimaryOrientation.getDecoratedEnd(child);
                        nextEnd = r0.mPrimaryOrientation.getDecoratedEnd(nextChild);
                        if (myEnd < nextEnd) {
                            return child;
                        }
                        if (myEnd == nextEnd) {
                            compareSpans = true;
                        }
                    } else {
                        nextEnd = r0.mPrimaryOrientation.getDecoratedStart(child);
                        myEnd = r0.mPrimaryOrientation.getDecoratedStart(nextChild);
                        if (nextEnd > myEnd) {
                            return child;
                        }
                        if (nextEnd == myEnd) {
                            compareSpans = true;
                        }
                    }
                    if (compareSpans) {
                        if ((lp.mSpan.mIndex - ((LayoutParams) nextChild.getLayoutParams()).mSpan.mIndex < 0 ? 1 : null) != (preferredSpanDir < 0 ? 1 : null)) {
                            return child;
                        }
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    private boolean checkSpanForGap(Span span) {
        if (this.mShouldReverseLayout) {
            if (span.getEndLine() < this.mPrimaryOrientation.getEndAfterPadding()) {
                return span.getLayoutParams((View) span.mViews.get(span.mViews.size() - 1)).mFullSpan ^ 1;
            }
        } else if (span.getStartLine() > this.mPrimaryOrientation.getStartAfterPadding()) {
            return span.getLayoutParams((View) span.mViews.get(0)).mFullSpan ^ 1;
        }
        return false;
    }

    public void setSpanCount(int spanCount) {
        assertNotInLayoutOrScroll(null);
        if (spanCount != this.mSpanCount) {
            invalidateSpanAssignments();
            this.mSpanCount = spanCount;
            this.mRemainingSpans = new BitSet(this.mSpanCount);
            this.mSpans = new Span[this.mSpanCount];
            for (int i = 0; i < this.mSpanCount; i++) {
                this.mSpans[i] = new Span(i);
            }
            requestLayout();
        }
    }

    public void setOrientation(int orientation) {
        if (orientation != 0) {
            if (orientation != 1) {
                throw new IllegalArgumentException("invalid orientation.");
            }
        }
        assertNotInLayoutOrScroll(null);
        if (orientation != this.mOrientation) {
            this.mOrientation = orientation;
            OrientationHelper tmp = this.mPrimaryOrientation;
            this.mPrimaryOrientation = this.mSecondaryOrientation;
            this.mSecondaryOrientation = tmp;
            requestLayout();
        }
    }

    public void setReverseLayout(boolean reverseLayout) {
        assertNotInLayoutOrScroll(null);
        if (!(this.mPendingSavedState == null || this.mPendingSavedState.mReverseLayout == reverseLayout)) {
            this.mPendingSavedState.mReverseLayout = reverseLayout;
        }
        this.mReverseLayout = reverseLayout;
        requestLayout();
    }

    public int getGapStrategy() {
        return this.mGapStrategy;
    }

    public void setGapStrategy(int gapStrategy) {
        assertNotInLayoutOrScroll(null);
        if (gapStrategy != this.mGapStrategy) {
            if (gapStrategy != 0) {
                if (gapStrategy != 2) {
                    throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
                }
            }
            this.mGapStrategy = gapStrategy;
            requestLayout();
        }
    }

    public void assertNotInLayoutOrScroll(String message) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(message);
        }
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public void invalidateSpanAssignments() {
        this.mLazySpanLookup.clear();
        requestLayout();
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation != 1) {
            if (isLayoutRTL()) {
                this.mShouldReverseLayout = this.mReverseLayout ^ true;
                return;
            }
        }
        this.mShouldReverseLayout = this.mReverseLayout;
    }

    boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public void setMeasuredDimension(Rect childrenBounds, int wSpec, int hSpec) {
        int width;
        int height;
        int horizontalPadding = getPaddingLeft() + getPaddingRight();
        int verticalPadding = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            int chooseSize = LayoutManager.chooseSize(hSpec, childrenBounds.height() + verticalPadding, getMinimumHeight());
            width = LayoutManager.chooseSize(wSpec, (this.mSizePerSpan * this.mSpanCount) + horizontalPadding, getMinimumWidth());
            height = chooseSize;
        } else {
            width = LayoutManager.chooseSize(wSpec, childrenBounds.width() + horizontalPadding, getMinimumWidth());
            height = LayoutManager.chooseSize(hSpec, (this.mSizePerSpan * this.mSpanCount) + verticalPadding, getMinimumHeight());
        }
        setMeasuredDimension(width, height);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        onLayoutChildren(recycler, state, true);
    }

    private void onLayoutChildren(Recycler recycler, State state, boolean shouldCheckForGaps) {
        AnchorInfo anchorInfo = this.mAnchorInfo;
        if (!(this.mPendingSavedState == null && this.mPendingScrollPosition == -1) && state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            anchorInfo.reset();
            return;
        }
        boolean recalculateAnchor;
        int i;
        boolean hasGaps;
        boolean needToCheckForGaps = true;
        if (anchorInfo.mValid && this.mPendingScrollPosition == -1) {
            if (this.mPendingSavedState == null) {
                recalculateAnchor = false;
                if (recalculateAnchor) {
                    anchorInfo.reset();
                    if (this.mPendingSavedState == null) {
                        applyPendingSavedState(anchorInfo);
                    } else {
                        resolveShouldLayoutReverse();
                        anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
                    }
                    updateAnchorInfoForLayout(state, anchorInfo);
                    anchorInfo.mValid = true;
                }
                if (this.mPendingSavedState == null && this.mPendingScrollPosition == -1 && !(anchorInfo.mLayoutFromEnd == this.mLastLayoutFromEnd && isLayoutRTL() == this.mLastLayoutRTL)) {
                    this.mLazySpanLookup.clear();
                    anchorInfo.mInvalidateOffsets = true;
                }
                if (getChildCount() > 0 && (this.mPendingSavedState == null || this.mPendingSavedState.mSpanOffsetsSize < 1)) {
                    if (anchorInfo.mInvalidateOffsets) {
                        if (!recalculateAnchor) {
                            if (this.mAnchorInfo.mSpanReferenceLines == null) {
                                for (i = 0; i < this.mSpanCount; i++) {
                                    Span span = this.mSpans[i];
                                    span.clear();
                                    span.setLine(this.mAnchorInfo.mSpanReferenceLines[i]);
                                }
                            }
                        }
                        for (i = 0; i < this.mSpanCount; i++) {
                            this.mSpans[i].cacheReferenceLineAndClear(this.mShouldReverseLayout, anchorInfo.mOffset);
                        }
                        this.mAnchorInfo.saveSpanReferenceLines(this.mSpans);
                    } else {
                        for (i = 0; i < this.mSpanCount; i++) {
                            this.mSpans[i].clear();
                            if (anchorInfo.mOffset != Integer.MIN_VALUE) {
                                this.mSpans[i].setLine(anchorInfo.mOffset);
                            }
                        }
                    }
                }
                detachAndScrapAttachedViews(recycler);
                this.mLayoutState.mRecycle = false;
                this.mLaidOutInvalidFullSpan = false;
                updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
                updateLayoutState(anchorInfo.mPosition, state);
                if (anchorInfo.mLayoutFromEnd) {
                    setLayoutStateDirection(1);
                    fill(recycler, this.mLayoutState, state);
                    setLayoutStateDirection(-1);
                    this.mLayoutState.mCurrentPosition = anchorInfo.mPosition + this.mLayoutState.mItemDirection;
                    fill(recycler, this.mLayoutState, state);
                } else {
                    setLayoutStateDirection(-1);
                    fill(recycler, this.mLayoutState, state);
                    setLayoutStateDirection(1);
                    this.mLayoutState.mCurrentPosition = anchorInfo.mPosition + this.mLayoutState.mItemDirection;
                    fill(recycler, this.mLayoutState, state);
                }
                repositionToWrapContentIfNecessary();
                if (getChildCount() > 0) {
                    if (this.mShouldReverseLayout) {
                        fixStartGap(recycler, state, true);
                        fixEndGap(recycler, state, false);
                    } else {
                        fixEndGap(recycler, state, true);
                        fixStartGap(recycler, state, false);
                    }
                }
                hasGaps = false;
                if (shouldCheckForGaps && !state.isPreLayout()) {
                    if (this.mGapStrategy != 0 || getChildCount() <= 0 || (!this.mLaidOutInvalidFullSpan && hasGapsToFix() == null)) {
                        needToCheckForGaps = false;
                    }
                    if (needToCheckForGaps) {
                        removeCallbacks(this.mCheckForGapsRunnable);
                        if (checkForGaps()) {
                            hasGaps = true;
                        }
                    }
                }
                if (state.isPreLayout()) {
                    this.mAnchorInfo.reset();
                }
                this.mLastLayoutFromEnd = anchorInfo.mLayoutFromEnd;
                this.mLastLayoutRTL = isLayoutRTL();
                if (hasGaps) {
                    this.mAnchorInfo.reset();
                    onLayoutChildren(recycler, state, false);
                }
            }
        }
        recalculateAnchor = true;
        if (recalculateAnchor) {
            anchorInfo.reset();
            if (this.mPendingSavedState == null) {
                resolveShouldLayoutReverse();
                anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
            } else {
                applyPendingSavedState(anchorInfo);
            }
            updateAnchorInfoForLayout(state, anchorInfo);
            anchorInfo.mValid = true;
        }
        this.mLazySpanLookup.clear();
        anchorInfo.mInvalidateOffsets = true;
        if (anchorInfo.mInvalidateOffsets) {
            if (recalculateAnchor) {
                if (this.mAnchorInfo.mSpanReferenceLines == null) {
                    for (i = 0; i < this.mSpanCount; i++) {
                        Span span2 = this.mSpans[i];
                        span2.clear();
                        span2.setLine(this.mAnchorInfo.mSpanReferenceLines[i]);
                    }
                }
            }
            for (i = 0; i < this.mSpanCount; i++) {
                this.mSpans[i].cacheReferenceLineAndClear(this.mShouldReverseLayout, anchorInfo.mOffset);
            }
            this.mAnchorInfo.saveSpanReferenceLines(this.mSpans);
        } else {
            for (i = 0; i < this.mSpanCount; i++) {
                this.mSpans[i].clear();
                if (anchorInfo.mOffset != Integer.MIN_VALUE) {
                    this.mSpans[i].setLine(anchorInfo.mOffset);
                }
            }
        }
        detachAndScrapAttachedViews(recycler);
        this.mLayoutState.mRecycle = false;
        this.mLaidOutInvalidFullSpan = false;
        updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
        updateLayoutState(anchorInfo.mPosition, state);
        if (anchorInfo.mLayoutFromEnd) {
            setLayoutStateDirection(1);
            fill(recycler, this.mLayoutState, state);
            setLayoutStateDirection(-1);
            this.mLayoutState.mCurrentPosition = anchorInfo.mPosition + this.mLayoutState.mItemDirection;
            fill(recycler, this.mLayoutState, state);
        } else {
            setLayoutStateDirection(-1);
            fill(recycler, this.mLayoutState, state);
            setLayoutStateDirection(1);
            this.mLayoutState.mCurrentPosition = anchorInfo.mPosition + this.mLayoutState.mItemDirection;
            fill(recycler, this.mLayoutState, state);
        }
        repositionToWrapContentIfNecessary();
        if (getChildCount() > 0) {
            if (this.mShouldReverseLayout) {
                fixStartGap(recycler, state, true);
                fixEndGap(recycler, state, false);
            } else {
                fixEndGap(recycler, state, true);
                fixStartGap(recycler, state, false);
            }
        }
        hasGaps = false;
        if (this.mGapStrategy != 0) {
        }
        needToCheckForGaps = false;
        if (needToCheckForGaps) {
            removeCallbacks(this.mCheckForGapsRunnable);
            if (checkForGaps()) {
                hasGaps = true;
            }
        }
        if (state.isPreLayout()) {
            this.mAnchorInfo.reset();
        }
        this.mLastLayoutFromEnd = anchorInfo.mLayoutFromEnd;
        this.mLastLayoutRTL = isLayoutRTL();
        if (hasGaps) {
            this.mAnchorInfo.reset();
            onLayoutChildren(recycler, state, false);
        }
    }

    public void onLayoutCompleted(State state) {
        super.onLayoutCompleted(state);
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo.reset();
    }

    private void repositionToWrapContentIfNecessary() {
        if (this.mSecondaryOrientation.getMode() != ErrorDialogData.SUPPRESSED) {
            int i;
            int childCount = getChildCount();
            int i2 = 0;
            float maxSize = 0.0f;
            for (i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                float size = (float) this.mSecondaryOrientation.getDecoratedMeasurement(child);
                if (size >= maxSize) {
                    if (((LayoutParams) child.getLayoutParams()).isFullSpan()) {
                        size = (1.0f * size) / ((float) this.mSpanCount);
                    }
                    maxSize = Math.max(maxSize, size);
                }
            }
            i = this.mSizePerSpan;
            int desired = Math.round(((float) this.mSpanCount) * maxSize);
            if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
                desired = Math.min(desired, this.mSecondaryOrientation.getTotalSpace());
            }
            updateMeasureSpecs(desired);
            if (this.mSizePerSpan != i) {
                while (i2 < childCount) {
                    View child2 = getChildAt(i2);
                    LayoutParams lp = (LayoutParams) child2.getLayoutParams();
                    if (!lp.mFullSpan) {
                        if (isLayoutRTL() && this.mOrientation == 1) {
                            child2.offsetLeftAndRight(((-((this.mSpanCount - 1) - lp.mSpan.mIndex)) * this.mSizePerSpan) - ((-((this.mSpanCount - 1) - lp.mSpan.mIndex)) * i));
                        } else {
                            int newOffset = lp.mSpan.mIndex * this.mSizePerSpan;
                            int prevOffset = lp.mSpan.mIndex * i;
                            if (this.mOrientation == 1) {
                                child2.offsetLeftAndRight(newOffset - prevOffset);
                            } else {
                                child2.offsetTopAndBottom(newOffset - prevOffset);
                            }
                        }
                    }
                    i2++;
                }
            }
        }
    }

    private void applyPendingSavedState(AnchorInfo anchorInfo) {
        if (this.mPendingSavedState.mSpanOffsetsSize > 0) {
            if (this.mPendingSavedState.mSpanOffsetsSize == this.mSpanCount) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    this.mSpans[i].clear();
                    int line = this.mPendingSavedState.mSpanOffsets[i];
                    if (line != Integer.MIN_VALUE) {
                        if (this.mPendingSavedState.mAnchorLayoutFromEnd) {
                            line += this.mPrimaryOrientation.getEndAfterPadding();
                        } else {
                            line += this.mPrimaryOrientation.getStartAfterPadding();
                        }
                    }
                    this.mSpans[i].setLine(line);
                }
            } else {
                this.mPendingSavedState.invalidateSpanInfo();
                this.mPendingSavedState.mAnchorPosition = this.mPendingSavedState.mVisibleAnchorPosition;
            }
        }
        this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
        setReverseLayout(this.mPendingSavedState.mReverseLayout);
        resolveShouldLayoutReverse();
        if (this.mPendingSavedState.mAnchorPosition != -1) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
            anchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
        } else {
            anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
        }
        if (this.mPendingSavedState.mSpanLookupSize > 1) {
            this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
            this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
        }
    }

    void updateAnchorInfoForLayout(State state, AnchorInfo anchorInfo) {
        if (!updateAnchorFromPendingData(state, anchorInfo) && !updateAnchorFromChildren(state, anchorInfo)) {
            anchorInfo.assignCoordinateFromPadding();
            anchorInfo.mPosition = 0;
        }
    }

    private boolean updateAnchorFromChildren(State state, AnchorInfo anchorInfo) {
        int findLastReferenceChildPosition;
        if (this.mLastLayoutFromEnd) {
            findLastReferenceChildPosition = findLastReferenceChildPosition(state.getItemCount());
        } else {
            findLastReferenceChildPosition = findFirstReferenceChildPosition(state.getItemCount());
        }
        anchorInfo.mPosition = findLastReferenceChildPosition;
        anchorInfo.mOffset = Integer.MIN_VALUE;
        return true;
    }

    boolean updateAnchorFromPendingData(State state, AnchorInfo anchorInfo) {
        boolean z = false;
        if (!state.isPreLayout()) {
            if (this.mPendingScrollPosition != -1) {
                if (this.mPendingScrollPosition >= 0) {
                    if (this.mPendingScrollPosition < state.getItemCount()) {
                        if (!(this.mPendingSavedState == null || this.mPendingSavedState.mAnchorPosition == -1)) {
                            if (this.mPendingSavedState.mSpanOffsetsSize >= 1) {
                                anchorInfo.mOffset = Integer.MIN_VALUE;
                                anchorInfo.mPosition = this.mPendingScrollPosition;
                                return true;
                            }
                        }
                        View child = findViewByPosition(this.mPendingScrollPosition);
                        if (child != null) {
                            int lastChildPosition;
                            if (this.mShouldReverseLayout) {
                                lastChildPosition = getLastChildPosition();
                            } else {
                                lastChildPosition = getFirstChildPosition();
                            }
                            anchorInfo.mPosition = lastChildPosition;
                            if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                                if (anchorInfo.mLayoutFromEnd) {
                                    anchorInfo.mOffset = (this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.getDecoratedEnd(child);
                                } else {
                                    anchorInfo.mOffset = (this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.getDecoratedStart(child);
                                }
                                return true;
                            } else if (this.mPrimaryOrientation.getDecoratedMeasurement(child) > this.mPrimaryOrientation.getTotalSpace()) {
                                if (anchorInfo.mLayoutFromEnd) {
                                    r2 = this.mPrimaryOrientation.getEndAfterPadding();
                                } else {
                                    r2 = this.mPrimaryOrientation.getStartAfterPadding();
                                }
                                anchorInfo.mOffset = r2;
                                return true;
                            } else {
                                r2 = this.mPrimaryOrientation.getDecoratedStart(child) - this.mPrimaryOrientation.getStartAfterPadding();
                                if (r2 < 0) {
                                    anchorInfo.mOffset = -r2;
                                    return true;
                                }
                                int endGap = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd(child);
                                if (endGap < 0) {
                                    anchorInfo.mOffset = endGap;
                                    return true;
                                }
                                anchorInfo.mOffset = Integer.MIN_VALUE;
                            }
                        } else {
                            anchorInfo.mPosition = this.mPendingScrollPosition;
                            if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                                if (calculateScrollDirectionForPosition(anchorInfo.mPosition) == 1) {
                                    z = true;
                                }
                                anchorInfo.mLayoutFromEnd = z;
                                anchorInfo.assignCoordinateFromPadding();
                            } else {
                                anchorInfo.assignCoordinateFromPadding(this.mPendingScrollPositionOffset);
                            }
                            anchorInfo.mInvalidateOffsets = true;
                        }
                        return true;
                    }
                }
                this.mPendingScrollPosition = -1;
                this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
                return false;
            }
        }
        return false;
    }

    void updateMeasureSpecs(int totalSpace) {
        this.mSizePerSpan = totalSpace / this.mSpanCount;
        this.mFullSizeSpec = MeasureSpec.makeMeasureSpec(totalSpace, this.mSecondaryOrientation.getMode());
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null;
    }

    public int[] findFirstVisibleItemPositions(int[] into) {
        if (into == null) {
            into = new int[this.mSpanCount];
        } else if (into.length < this.mSpanCount) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            stringBuilder.append(this.mSpanCount);
            stringBuilder.append(", array size:");
            stringBuilder.append(into.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            into[i] = this.mSpans[i].findFirstVisibleItemPosition();
        }
        return into;
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] into) {
        if (into == null) {
            into = new int[this.mSpanCount];
        } else if (into.length < this.mSpanCount) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            stringBuilder.append(this.mSpanCount);
            stringBuilder.append(", array size:");
            stringBuilder.append(into.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            into[i] = this.mSpans[i].findFirstCompletelyVisibleItemPosition();
        }
        return into;
    }

    public int[] findLastVisibleItemPositions(int[] into) {
        if (into == null) {
            into = new int[this.mSpanCount];
        } else if (into.length < this.mSpanCount) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            stringBuilder.append(this.mSpanCount);
            stringBuilder.append(", array size:");
            stringBuilder.append(into.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            into[i] = this.mSpans[i].findLastVisibleItemPosition();
        }
        return into;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] into) {
        if (into == null) {
            into = new int[this.mSpanCount];
        } else if (into.length < this.mSpanCount) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            stringBuilder.append(this.mSpanCount);
            stringBuilder.append(", array size:");
            stringBuilder.append(into.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            into[i] = this.mSpans[i].findLastCompletelyVisibleItemPosition();
        }
        return into;
    }

    public int computeHorizontalScrollOffset(State state) {
        return computeScrollOffset(state);
    }

    private int computeScrollOffset(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollOffset(state, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ 1), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ 1), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public int computeVerticalScrollOffset(State state) {
        return computeScrollOffset(state);
    }

    public int computeHorizontalScrollExtent(State state) {
        return computeScrollExtent(state);
    }

    private int computeScrollExtent(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollExtent(state, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ 1), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ 1), this, this.mSmoothScrollbarEnabled);
    }

    public int computeVerticalScrollExtent(State state) {
        return computeScrollExtent(state);
    }

    public int computeHorizontalScrollRange(State state) {
        return computeScrollRange(state);
    }

    private int computeScrollRange(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollRange(state, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ 1), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ 1), this, this.mSmoothScrollbarEnabled);
    }

    public int computeVerticalScrollRange(State state) {
        return computeScrollRange(state);
    }

    private void measureChildWithDecorationsAndMargin(View child, LayoutParams lp, boolean alreadyMeasured) {
        if (lp.mFullSpan) {
            if (this.mOrientation == 1) {
                measureChildWithDecorationsAndMargin(child, this.mFullSizeSpec, LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), lp.height, true), alreadyMeasured);
            } else {
                measureChildWithDecorationsAndMargin(child, LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), lp.width, true), this.mFullSizeSpec, alreadyMeasured);
            }
        } else if (this.mOrientation == 1) {
            measureChildWithDecorationsAndMargin(child, LayoutManager.getChildMeasureSpec(this.mSizePerSpan, getWidthMode(), 0, lp.width, false), LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), lp.height, true), alreadyMeasured);
        } else {
            measureChildWithDecorationsAndMargin(child, LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), lp.width, true), LayoutManager.getChildMeasureSpec(this.mSizePerSpan, getHeightMode(), 0, lp.height, false), alreadyMeasured);
        }
    }

    private void measureChildWithDecorationsAndMargin(View child, int widthSpec, int heightSpec, boolean alreadyMeasured) {
        boolean measure;
        calculateItemDecorationsForChild(child, this.mTmpRect);
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        widthSpec = updateSpecWithExtra(widthSpec, lp.leftMargin + this.mTmpRect.left, lp.rightMargin + this.mTmpRect.right);
        heightSpec = updateSpecWithExtra(heightSpec, lp.topMargin + this.mTmpRect.top, lp.bottomMargin + this.mTmpRect.bottom);
        if (alreadyMeasured) {
            measure = shouldReMeasureChild(child, widthSpec, heightSpec, lp);
        } else {
            measure = shouldMeasureChild(child, widthSpec, heightSpec, lp);
        }
        if (measure) {
            child.measure(widthSpec, heightSpec);
        }
    }

    private int updateSpecWithExtra(int spec, int startInset, int endInset) {
        if (startInset == 0 && endInset == 0) {
            return spec;
        }
        int mode = MeasureSpec.getMode(spec);
        if (mode != Integer.MIN_VALUE) {
            if (mode != ErrorDialogData.SUPPRESSED) {
                return spec;
            }
        }
        return MeasureSpec.makeMeasureSpec(Math.max(0, (MeasureSpec.getSize(spec) - startInset) - endInset), mode);
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            this.mPendingSavedState = (SavedState) state;
            requestLayout();
        }
    }

    public Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState state = new SavedState();
        state.mReverseLayout = this.mReverseLayout;
        state.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
        state.mLastLayoutRTL = this.mLastLayoutRTL;
        int i = 0;
        if (this.mLazySpanLookup == null || this.mLazySpanLookup.mData == null) {
            state.mSpanLookupSize = 0;
        } else {
            state.mSpanLookup = this.mLazySpanLookup.mData;
            state.mSpanLookupSize = state.mSpanLookup.length;
            state.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
        }
        if (getChildCount() > 0) {
            int lastChildPosition;
            if (this.mLastLayoutFromEnd) {
                lastChildPosition = getLastChildPosition();
            } else {
                lastChildPosition = getFirstChildPosition();
            }
            state.mAnchorPosition = lastChildPosition;
            state.mVisibleAnchorPosition = findFirstVisibleItemPositionInt();
            state.mSpanOffsetsSize = this.mSpanCount;
            state.mSpanOffsets = new int[this.mSpanCount];
            while (true) {
                lastChildPosition = i;
                if (lastChildPosition >= this.mSpanCount) {
                    break;
                }
                if (this.mLastLayoutFromEnd) {
                    i = this.mSpans[lastChildPosition].getEndLine(Integer.MIN_VALUE);
                    if (i != Integer.MIN_VALUE) {
                        i -= this.mPrimaryOrientation.getEndAfterPadding();
                    }
                } else {
                    i = this.mSpans[lastChildPosition].getStartLine(Integer.MIN_VALUE);
                    if (i != Integer.MIN_VALUE) {
                        i -= this.mPrimaryOrientation.getStartAfterPadding();
                    }
                }
                state.mSpanOffsets[lastChildPosition] = i;
                i = lastChildPosition + 1;
            }
        } else {
            state.mAnchorPosition = -1;
            state.mVisibleAnchorPosition = -1;
            state.mSpanOffsetsSize = 0;
        }
        return state;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View host, AccessibilityNodeInfoCompat info) {
        android.view.ViewGroup.LayoutParams lp = host.getLayoutParams();
        if (lp instanceof LayoutParams) {
            LayoutParams sglp = (LayoutParams) lp;
            int i = 1;
            if (this.mOrientation == 0) {
                int spanIndex = sglp.getSpanIndex();
                if (sglp.mFullSpan) {
                    i = this.mSpanCount;
                }
                info.setCollectionItemInfo(CollectionItemInfoCompat.obtain(spanIndex, i, -1, -1, sglp.mFullSpan, false));
            } else {
                int spanIndex2 = sglp.getSpanIndex();
                if (sglp.mFullSpan) {
                    i = this.mSpanCount;
                }
                int i2 = i;
                info.setCollectionItemInfo(CollectionItemInfoCompat.obtain(-1, -1, spanIndex2, i2, sglp.mFullSpan, false));
            }
            return;
        }
        super.onInitializeAccessibilityNodeInfoForItem(host, info);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        if (getChildCount() > 0) {
            View start = findFirstVisibleItemClosestToStart(false);
            View end = findFirstVisibleItemClosestToEnd(false);
            if (start != null) {
                if (end != null) {
                    int startPos = getPosition(start);
                    int endPos = getPosition(end);
                    if (startPos < endPos) {
                        event.setFromIndex(startPos);
                        event.setToIndex(endPos);
                    } else {
                        event.setFromIndex(endPos);
                        event.setToIndex(startPos);
                    }
                }
            }
        }
    }

    int findFirstVisibleItemPositionInt() {
        View first;
        if (this.mShouldReverseLayout) {
            first = findFirstVisibleItemClosestToEnd(true);
        } else {
            first = findFirstVisibleItemClosestToStart(true);
        }
        return first == null ? -1 : getPosition(first);
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        return super.getRowCountForAccessibility(recycler, state);
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        return super.getColumnCountForAccessibility(recycler, state);
    }

    View findFirstVisibleItemClosestToStart(boolean fullyVisible) {
        int boundsStart = this.mPrimaryOrientation.getStartAfterPadding();
        int boundsEnd = this.mPrimaryOrientation.getEndAfterPadding();
        int limit = getChildCount();
        View partiallyVisible = null;
        for (int i = 0; i < limit; i++) {
            View child = getChildAt(i);
            int childStart = this.mPrimaryOrientation.getDecoratedStart(child);
            if (this.mPrimaryOrientation.getDecoratedEnd(child) > boundsStart) {
                if (childStart < boundsEnd) {
                    if (childStart < boundsStart) {
                        if (fullyVisible) {
                            if (partiallyVisible == null) {
                                partiallyVisible = child;
                            }
                        }
                    }
                    return child;
                }
            }
        }
        return partiallyVisible;
    }

    View findFirstVisibleItemClosestToEnd(boolean fullyVisible) {
        int boundsStart = this.mPrimaryOrientation.getStartAfterPadding();
        int boundsEnd = this.mPrimaryOrientation.getEndAfterPadding();
        View partiallyVisible = null;
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            int childStart = this.mPrimaryOrientation.getDecoratedStart(child);
            int childEnd = this.mPrimaryOrientation.getDecoratedEnd(child);
            if (childEnd > boundsStart) {
                if (childStart < boundsEnd) {
                    if (childEnd > boundsEnd) {
                        if (fullyVisible) {
                            if (partiallyVisible == null) {
                                partiallyVisible = child;
                            }
                        }
                    }
                    return child;
                }
            }
        }
        return partiallyVisible;
    }

    private void fixEndGap(Recycler recycler, State state, boolean canOffsetChildren) {
        int maxEndLine = getMaxEnd(Integer.MIN_VALUE);
        if (maxEndLine != Integer.MIN_VALUE) {
            int gap = this.mPrimaryOrientation.getEndAfterPadding() - maxEndLine;
            if (gap > 0) {
                gap -= -scrollBy(-gap, recycler, state);
                if (canOffsetChildren && gap > 0) {
                    this.mPrimaryOrientation.offsetChildren(gap);
                }
            }
        }
    }

    private void fixStartGap(Recycler recycler, State state, boolean canOffsetChildren) {
        int minStartLine = getMinStart(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (minStartLine != ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
            int gap = minStartLine - this.mPrimaryOrientation.getStartAfterPadding();
            if (gap > 0) {
                gap -= scrollBy(gap, recycler, state);
                if (canOffsetChildren && gap > 0) {
                    this.mPrimaryOrientation.offsetChildren(-gap);
                }
            }
        }
    }

    private void updateLayoutState(int anchorPosition, State state) {
        boolean z = false;
        this.mLayoutState.mAvailable = 0;
        this.mLayoutState.mCurrentPosition = anchorPosition;
        int startExtra = 0;
        int endExtra = 0;
        if (isSmoothScrolling()) {
            int targetPos = state.getTargetScrollPosition();
            if (targetPos != -1) {
                if (this.mShouldReverseLayout == (targetPos < anchorPosition)) {
                    endExtra = this.mPrimaryOrientation.getTotalSpace();
                } else {
                    startExtra = this.mPrimaryOrientation.getTotalSpace();
                }
            }
        }
        if (getClipToPadding()) {
            this.mLayoutState.mStartLine = this.mPrimaryOrientation.getStartAfterPadding() - startExtra;
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEndAfterPadding() + endExtra;
        } else {
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEnd() + endExtra;
            this.mLayoutState.mStartLine = -startExtra;
        }
        this.mLayoutState.mStopInFocusable = false;
        this.mLayoutState.mRecycle = true;
        LayoutState layoutState = this.mLayoutState;
        if (this.mPrimaryOrientation.getMode() == 0 && this.mPrimaryOrientation.getEnd() == 0) {
            z = true;
        }
        layoutState.mInfinite = z;
    }

    private void setLayoutStateDirection(int direction) {
        this.mLayoutState.mLayoutDirection = direction;
        LayoutState layoutState = this.mLayoutState;
        int i = 1;
        if (this.mShouldReverseLayout != (direction == -1)) {
            i = -1;
        }
        layoutState.mItemDirection = i;
    }

    public void offsetChildrenHorizontal(int dx) {
        super.offsetChildrenHorizontal(dx);
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].onOffset(dx);
        }
    }

    public void offsetChildrenVertical(int dy) {
        super.offsetChildrenVertical(dy);
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].onOffset(dy);
        }
    }

    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        handleUpdate(positionStart, itemCount, 2);
    }

    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        handleUpdate(positionStart, itemCount, 1);
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.mLazySpanLookup.clear();
        requestLayout();
    }

    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        handleUpdate(from, to, 8);
    }

    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount, Object payload) {
        handleUpdate(positionStart, itemCount, 4);
    }

    private void handleUpdate(int positionStart, int itemCountOrToPosition, int cmd) {
        int affectedRangeStart;
        int affectedRangeEnd;
        int minPosition = this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition();
        if (cmd != 8) {
            affectedRangeStart = positionStart;
            affectedRangeEnd = positionStart + itemCountOrToPosition;
        } else if (positionStart < itemCountOrToPosition) {
            affectedRangeEnd = itemCountOrToPosition + 1;
            affectedRangeStart = positionStart;
        } else {
            affectedRangeEnd = positionStart + 1;
            affectedRangeStart = itemCountOrToPosition;
        }
        this.mLazySpanLookup.invalidateAfter(affectedRangeStart);
        if (cmd != 8) {
            switch (cmd) {
                case 1:
                    this.mLazySpanLookup.offsetForAddition(positionStart, itemCountOrToPosition);
                    break;
                case 2:
                    this.mLazySpanLookup.offsetForRemoval(positionStart, itemCountOrToPosition);
                    break;
                default:
                    break;
            }
        }
        this.mLazySpanLookup.offsetForRemoval(positionStart, 1);
        this.mLazySpanLookup.offsetForAddition(itemCountOrToPosition, 1);
        if (affectedRangeEnd > minPosition) {
            if (affectedRangeStart <= (this.mShouldReverseLayout ? getFirstChildPosition() : getLastChildPosition())) {
                requestLayout();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int fill(android.support.v7.widget.RecyclerView.Recycler r23, android.support.v7.widget.LayoutState r24, android.support.v7.widget.RecyclerView.State r25) {
        /*
        r22 = this;
        r11 = r22;
        r12 = r23;
        r13 = r24;
        r0 = r11.mRemainingSpans;
        r1 = r11.mSpanCount;
        r14 = 0;
        r15 = 1;
        r0.set(r14, r1, r15);
        r0 = r11.mLayoutState;
        r0 = r0.mInfinite;
        if (r0 == 0) goto L_0x0020;
    L_0x0015:
        r0 = r13.mLayoutDirection;
        if (r0 != r15) goto L_0x001d;
    L_0x0019:
        r0 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        goto L_0x002f;
    L_0x001d:
        r0 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        goto L_0x002f;
    L_0x0020:
        r0 = r13.mLayoutDirection;
        if (r0 != r15) goto L_0x002a;
    L_0x0024:
        r0 = r13.mEndLine;
        r1 = r13.mAvailable;
        r0 = r0 + r1;
        goto L_0x002f;
    L_0x002a:
        r0 = r13.mStartLine;
        r1 = r13.mAvailable;
        r0 = r0 - r1;
    L_0x002f:
        r10 = r0;
        r0 = r13.mLayoutDirection;
        r11.updateAllRemainingSpans(r0, r10);
        r0 = r11.mShouldReverseLayout;
        if (r0 == 0) goto L_0x0040;
    L_0x0039:
        r0 = r11.mPrimaryOrientation;
        r0 = r0.getEndAfterPadding();
        goto L_0x0046;
    L_0x0040:
        r0 = r11.mPrimaryOrientation;
        r0 = r0.getStartAfterPadding();
    L_0x0046:
        r9 = r0;
        r0 = r14;
    L_0x0048:
        r16 = r0;
        r0 = r24.hasMore(r25);
        r1 = -1;
        if (r0 == 0) goto L_0x01f5;
    L_0x0051:
        r0 = r11.mLayoutState;
        r0 = r0.mInfinite;
        if (r0 != 0) goto L_0x0065;
    L_0x0057:
        r0 = r11.mRemainingSpans;
        r0 = r0.isEmpty();
        if (r0 != 0) goto L_0x0060;
    L_0x005f:
        goto L_0x0065;
    L_0x0060:
        r3 = r9;
        r0 = r10;
        r7 = r14;
        goto L_0x01f8;
    L_0x0065:
        r8 = r13.next(r12);
        r0 = r8.getLayoutParams();
        r7 = r0;
        r7 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r7;
        r6 = r7.getViewLayoutPosition();
        r0 = r11.mLazySpanLookup;
        r5 = r0.getSpan(r6);
        if (r5 != r1) goto L_0x007e;
    L_0x007c:
        r0 = r15;
        goto L_0x007f;
    L_0x007e:
        r0 = r14;
    L_0x007f:
        r17 = r0;
        if (r17 == 0) goto L_0x0096;
    L_0x0083:
        r0 = r7.mFullSpan;
        if (r0 == 0) goto L_0x008c;
    L_0x0087:
        r0 = r11.mSpans;
        r0 = r0[r14];
        goto L_0x0090;
    L_0x008c:
        r0 = r11.getNextSpan(r13);
    L_0x0090:
        r2 = r11.mLazySpanLookup;
        r2.setSpan(r6, r0);
        goto L_0x009a;
    L_0x0096:
        r0 = r11.mSpans;
        r0 = r0[r5];
    L_0x009a:
        r3 = r0;
        r7.mSpan = r3;
        r0 = r13.mLayoutDirection;
        if (r0 != r15) goto L_0x00a5;
    L_0x00a1:
        r11.addView(r8);
        goto L_0x00a8;
    L_0x00a5:
        r11.addView(r8, r14);
    L_0x00a8:
        r11.measureChildWithDecorationsAndMargin(r8, r7, r14);
        r0 = r13.mLayoutDirection;
        if (r0 != r15) goto L_0x00db;
    L_0x00af:
        r0 = r7.mFullSpan;
        if (r0 == 0) goto L_0x00b8;
    L_0x00b3:
        r0 = r11.getMaxEnd(r9);
        goto L_0x00bc;
    L_0x00b8:
        r0 = r3.getEndLine(r9);
    L_0x00bc:
        r2 = r11.mPrimaryOrientation;
        r2 = r2.getDecoratedMeasurement(r8);
        r2 = r2 + r0;
        if (r17 == 0) goto L_0x00d7;
    L_0x00c5:
        r4 = r7.mFullSpan;
        if (r4 == 0) goto L_0x00d7;
    L_0x00c9:
        r4 = r11.createFullSpanItemFromEnd(r0);
        r4.mGapDir = r1;
        r4.mPosition = r6;
        r14 = r11.mLazySpanLookup;
        r14.addFullSpanItem(r4);
    L_0x00d7:
        r14 = r0;
        r18 = r2;
        goto L_0x0106;
    L_0x00db:
        r0 = r7.mFullSpan;
        if (r0 == 0) goto L_0x00e4;
    L_0x00df:
        r0 = r11.getMinStart(r9);
        goto L_0x00e8;
    L_0x00e4:
        r0 = r3.getStartLine(r9);
    L_0x00e8:
        r2 = r11.mPrimaryOrientation;
        r2 = r2.getDecoratedMeasurement(r8);
        r2 = r0 - r2;
        if (r17 == 0) goto L_0x0103;
    L_0x00f2:
        r4 = r7.mFullSpan;
        if (r4 == 0) goto L_0x0103;
    L_0x00f6:
        r4 = r11.createFullSpanItemFromStart(r0);
        r4.mGapDir = r15;
        r4.mPosition = r6;
        r14 = r11.mLazySpanLookup;
        r14.addFullSpanItem(r4);
    L_0x0103:
        r18 = r0;
        r14 = r2;
    L_0x0106:
        r0 = r7.mFullSpan;
        if (r0 == 0) goto L_0x0130;
    L_0x010a:
        r0 = r13.mItemDirection;
        if (r0 != r1) goto L_0x0130;
    L_0x010e:
        if (r17 == 0) goto L_0x0113;
    L_0x0110:
        r11.mLaidOutInvalidFullSpan = r15;
        goto L_0x0130;
    L_0x0113:
        r0 = r13.mLayoutDirection;
        if (r0 != r15) goto L_0x011d;
    L_0x0117:
        r0 = r22.areAllEndsEqual();
        r0 = r0 ^ r15;
        goto L_0x0122;
    L_0x011d:
        r0 = r22.areAllStartsEqual();
        r0 = r0 ^ r15;
    L_0x0122:
        if (r0 == 0) goto L_0x0130;
    L_0x0124:
        r1 = r11.mLazySpanLookup;
        r1 = r1.getFullSpanItem(r6);
        if (r1 == 0) goto L_0x012e;
    L_0x012c:
        r1.mHasUnwantedGapAfter = r15;
    L_0x012e:
        r11.mLaidOutInvalidFullSpan = r15;
    L_0x0130:
        r11.attachViewToSpans(r8, r7, r13);
        r0 = r22.isLayoutRTL();
        if (r0 == 0) goto L_0x0164;
    L_0x0139:
        r0 = r11.mOrientation;
        if (r0 != r15) goto L_0x0164;
    L_0x013d:
        r0 = r7.mFullSpan;
        if (r0 == 0) goto L_0x0148;
    L_0x0141:
        r0 = r11.mSecondaryOrientation;
        r0 = r0.getEndAfterPadding();
        goto L_0x0158;
    L_0x0148:
        r0 = r11.mSecondaryOrientation;
        r0 = r0.getEndAfterPadding();
        r1 = r11.mSpanCount;
        r1 = r1 - r15;
        r2 = r3.mIndex;
        r1 = r1 - r2;
        r2 = r11.mSizePerSpan;
        r1 = r1 * r2;
        r0 = r0 - r1;
    L_0x0158:
        r1 = r11.mSecondaryOrientation;
        r1 = r1.getDecoratedMeasurement(r8);
        r1 = r0 - r1;
        r4 = r0;
        r19 = r1;
        goto L_0x0185;
    L_0x0164:
        r0 = r7.mFullSpan;
        if (r0 == 0) goto L_0x016f;
    L_0x0168:
        r0 = r11.mSecondaryOrientation;
        r0 = r0.getStartAfterPadding();
        goto L_0x017b;
    L_0x016f:
        r0 = r3.mIndex;
        r1 = r11.mSizePerSpan;
        r0 = r0 * r1;
        r1 = r11.mSecondaryOrientation;
        r1 = r1.getStartAfterPadding();
        r0 = r0 + r1;
    L_0x017b:
        r1 = r11.mSecondaryOrientation;
        r1 = r1.getDecoratedMeasurement(r8);
        r1 = r1 + r0;
        r19 = r0;
        r4 = r1;
    L_0x0185:
        r0 = r11.mOrientation;
        if (r0 != r15) goto L_0x019e;
    L_0x0189:
        r0 = r22;
        r1 = r8;
        r2 = r19;
        r15 = r3;
        r3 = r14;
        r20 = r5;
        r5 = r18;
        r0.layoutDecoratedWithMargins(r1, r2, r3, r4, r5);
        r21 = r6;
        r1 = r7;
        r2 = r8;
        r3 = r9;
        r0 = r10;
        goto L_0x01b4;
    L_0x019e:
        r15 = r3;
        r20 = r5;
        r5 = r22;
        r0 = r6;
        r6 = r8;
        r1 = r7;
        r7 = r14;
        r2 = r8;
        r8 = r19;
        r3 = r9;
        r9 = r18;
        r21 = r0;
        r0 = r10;
        r10 = r4;
        r5.layoutDecoratedWithMargins(r6, r7, r8, r9, r10);
    L_0x01b4:
        r5 = r1.mFullSpan;
        if (r5 == 0) goto L_0x01c0;
    L_0x01b8:
        r5 = r11.mLayoutState;
        r5 = r5.mLayoutDirection;
        r11.updateAllRemainingSpans(r5, r0);
        goto L_0x01c7;
    L_0x01c0:
        r5 = r11.mLayoutState;
        r5 = r5.mLayoutDirection;
        r11.updateRemainingSpans(r15, r5, r0);
    L_0x01c7:
        r5 = r11.mLayoutState;
        r11.recycle(r12, r5);
        r5 = r11.mLayoutState;
        r5 = r5.mStopInFocusable;
        if (r5 == 0) goto L_0x01eb;
    L_0x01d2:
        r5 = r2.hasFocusable();
        if (r5 == 0) goto L_0x01eb;
    L_0x01d8:
        r5 = r1.mFullSpan;
        if (r5 == 0) goto L_0x01e2;
    L_0x01dc:
        r5 = r11.mRemainingSpans;
        r5.clear();
        goto L_0x01eb;
    L_0x01e2:
        r5 = r11.mRemainingSpans;
        r6 = r15.mIndex;
        r7 = 0;
        r5.set(r6, r7);
        goto L_0x01ec;
    L_0x01eb:
        r7 = 0;
    L_0x01ec:
        r1 = 1;
        r10 = r0;
        r0 = r1;
        r9 = r3;
        r14 = r7;
        r15 = 1;
        goto L_0x0048;
    L_0x01f5:
        r3 = r9;
        r0 = r10;
        r7 = r14;
    L_0x01f8:
        if (r16 != 0) goto L_0x01ff;
    L_0x01fa:
        r2 = r11.mLayoutState;
        r11.recycle(r12, r2);
    L_0x01ff:
        r2 = r11.mLayoutState;
        r2 = r2.mLayoutDirection;
        if (r2 != r1) goto L_0x0217;
    L_0x0205:
        r1 = r11.mPrimaryOrientation;
        r1 = r1.getStartAfterPadding();
        r1 = r11.getMinStart(r1);
        r2 = r11.mPrimaryOrientation;
        r2 = r2.getStartAfterPadding();
        r2 = r2 - r1;
        goto L_0x0229;
    L_0x0217:
        r1 = r11.mPrimaryOrientation;
        r1 = r1.getEndAfterPadding();
        r1 = r11.getMaxEnd(r1);
        r2 = r11.mPrimaryOrientation;
        r2 = r2.getEndAfterPadding();
        r2 = r1 - r2;
    L_0x0229:
        r1 = r2;
        if (r1 <= 0) goto L_0x0233;
    L_0x022c:
        r2 = r13.mAvailable;
        r14 = java.lang.Math.min(r2, r1);
        goto L_0x0234;
    L_0x0233:
        r14 = r7;
    L_0x0234:
        return r14;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.fill(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.LayoutState, android.support.v7.widget.RecyclerView$State):int");
    }

    private FullSpanItem createFullSpanItemFromEnd(int newItemTop) {
        FullSpanItem fsi = new FullSpanItem();
        fsi.mGapPerSpan = new int[this.mSpanCount];
        for (int i = 0; i < this.mSpanCount; i++) {
            fsi.mGapPerSpan[i] = newItemTop - this.mSpans[i].getEndLine(newItemTop);
        }
        return fsi;
    }

    private FullSpanItem createFullSpanItemFromStart(int newItemBottom) {
        FullSpanItem fsi = new FullSpanItem();
        fsi.mGapPerSpan = new int[this.mSpanCount];
        for (int i = 0; i < this.mSpanCount; i++) {
            fsi.mGapPerSpan[i] = this.mSpans[i].getStartLine(newItemBottom) - newItemBottom;
        }
        return fsi;
    }

    private void attachViewToSpans(View view, LayoutParams lp, LayoutState layoutState) {
        if (layoutState.mLayoutDirection == 1) {
            if (lp.mFullSpan) {
                appendViewToAllSpans(view);
            } else {
                lp.mSpan.appendToSpan(view);
            }
        } else if (lp.mFullSpan) {
            prependViewToAllSpans(view);
        } else {
            lp.mSpan.prependToSpan(view);
        }
    }

    private void recycle(Recycler recycler, LayoutState layoutState) {
        if (layoutState.mRecycle) {
            if (!layoutState.mInfinite) {
                if (layoutState.mAvailable == 0) {
                    if (layoutState.mLayoutDirection == -1) {
                        recycleFromEnd(recycler, layoutState.mEndLine);
                    } else {
                        recycleFromStart(recycler, layoutState.mStartLine);
                    }
                } else if (layoutState.mLayoutDirection == -1) {
                    scrolled = layoutState.mStartLine - getMaxStart(layoutState.mStartLine);
                    if (scrolled < 0) {
                        line = layoutState.mEndLine;
                    } else {
                        line = layoutState.mEndLine - Math.min(scrolled, layoutState.mAvailable);
                    }
                    recycleFromEnd(recycler, line);
                } else {
                    scrolled = getMinEnd(layoutState.mEndLine) - layoutState.mEndLine;
                    if (scrolled < 0) {
                        line = layoutState.mStartLine;
                    } else {
                        line = layoutState.mStartLine + Math.min(scrolled, layoutState.mAvailable);
                    }
                    recycleFromStart(recycler, line);
                }
            }
        }
    }

    private void appendViewToAllSpans(View view) {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            this.mSpans[i].appendToSpan(view);
        }
    }

    private void prependViewToAllSpans(View view) {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            this.mSpans[i].prependToSpan(view);
        }
    }

    private void updateAllRemainingSpans(int layoutDir, int targetLine) {
        for (int i = 0; i < this.mSpanCount; i++) {
            if (!this.mSpans[i].mViews.isEmpty()) {
                updateRemainingSpans(this.mSpans[i], layoutDir, targetLine);
            }
        }
    }

    private void updateRemainingSpans(Span span, int layoutDir, int targetLine) {
        int deletedSize = span.getDeletedSize();
        if (layoutDir == -1) {
            if (span.getStartLine() + deletedSize <= targetLine) {
                this.mRemainingSpans.set(span.mIndex, false);
            }
        } else if (span.getEndLine() - deletedSize >= targetLine) {
            this.mRemainingSpans.set(span.mIndex, false);
        }
    }

    private int getMaxStart(int def) {
        int maxStart = this.mSpans[0].getStartLine(def);
        for (int i = 1; i < this.mSpanCount; i++) {
            int spanStart = this.mSpans[i].getStartLine(def);
            if (spanStart > maxStart) {
                maxStart = spanStart;
            }
        }
        return maxStart;
    }

    private int getMinStart(int def) {
        int minStart = this.mSpans[0].getStartLine(def);
        for (int i = 1; i < this.mSpanCount; i++) {
            int spanStart = this.mSpans[i].getStartLine(def);
            if (spanStart < minStart) {
                minStart = spanStart;
            }
        }
        return minStart;
    }

    boolean areAllEndsEqual() {
        int end = this.mSpans[0].getEndLine(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; i++) {
            if (this.mSpans[i].getEndLine(Integer.MIN_VALUE) != end) {
                return false;
            }
        }
        return true;
    }

    boolean areAllStartsEqual() {
        int start = this.mSpans[0].getStartLine(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; i++) {
            if (this.mSpans[i].getStartLine(Integer.MIN_VALUE) != start) {
                return false;
            }
        }
        return true;
    }

    private int getMaxEnd(int def) {
        int maxEnd = this.mSpans[0].getEndLine(def);
        for (int i = 1; i < this.mSpanCount; i++) {
            int spanEnd = this.mSpans[i].getEndLine(def);
            if (spanEnd > maxEnd) {
                maxEnd = spanEnd;
            }
        }
        return maxEnd;
    }

    private int getMinEnd(int def) {
        int minEnd = this.mSpans[0].getEndLine(def);
        for (int i = 1; i < this.mSpanCount; i++) {
            int spanEnd = this.mSpans[i].getEndLine(def);
            if (spanEnd < minEnd) {
                minEnd = spanEnd;
            }
        }
        return minEnd;
    }

    private void recycleFromStart(Recycler recycler, int line) {
        while (getChildCount() > 0) {
            int j = 0;
            View child = getChildAt(0);
            if (this.mPrimaryOrientation.getDecoratedEnd(child) <= line && this.mPrimaryOrientation.getTransformedEndWithDecoration(child) <= line) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.mFullSpan) {
                    int j2 = 0;
                    while (j2 < this.mSpanCount) {
                        if (this.mSpans[j2].mViews.size() != 1) {
                            j2++;
                        } else {
                            return;
                        }
                    }
                    while (j < this.mSpanCount) {
                        this.mSpans[j].popStart();
                        j++;
                    }
                } else if (lp.mSpan.mViews.size() != 1) {
                    lp.mSpan.popStart();
                } else {
                    return;
                }
                removeAndRecycleView(child, recycler);
            } else {
                return;
            }
        }
    }

    private void recycleFromEnd(Recycler recycler, int line) {
        int i = getChildCount() - 1;
        while (i >= 0) {
            View child = getChildAt(i);
            if (this.mPrimaryOrientation.getDecoratedStart(child) >= line && this.mPrimaryOrientation.getTransformedStartWithDecoration(child) >= line) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.mFullSpan) {
                    int j = 0;
                    int j2 = 0;
                    while (j2 < this.mSpanCount) {
                        if (this.mSpans[j2].mViews.size() != 1) {
                            j2++;
                        } else {
                            return;
                        }
                    }
                    while (j < this.mSpanCount) {
                        this.mSpans[j].popEnd();
                        j++;
                    }
                } else if (lp.mSpan.mViews.size() != 1) {
                    lp.mSpan.popEnd();
                } else {
                    return;
                }
                removeAndRecycleView(child, recycler);
                i--;
            } else {
                return;
            }
        }
    }

    private boolean preferLastSpan(int layoutDir) {
        boolean z = false;
        if (this.mOrientation == 0) {
            if ((layoutDir == -1) != this.mShouldReverseLayout) {
                z = true;
            }
            return z;
        }
        if (((layoutDir == -1) == this.mShouldReverseLayout) == isLayoutRTL()) {
            z = true;
        }
        return z;
    }

    private Span getNextSpan(LayoutState layoutState) {
        int startIndex;
        int endIndex;
        int diff;
        if (preferLastSpan(layoutState.mLayoutDirection)) {
            startIndex = this.mSpanCount - 1;
            endIndex = -1;
            diff = -1;
        } else {
            startIndex = 0;
            endIndex = this.mSpanCount;
            diff = 1;
        }
        int minLine;
        int defaultLine;
        Span min;
        int i;
        Span other;
        int otherLine;
        if (layoutState.mLayoutDirection == 1) {
            minLine = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            defaultLine = this.mPrimaryOrientation.getStartAfterPadding();
            min = null;
            for (i = startIndex; i != endIndex; i += diff) {
                other = this.mSpans[i];
                otherLine = other.getEndLine(defaultLine);
                if (otherLine < minLine) {
                    min = other;
                    minLine = otherLine;
                }
            }
            return min;
        }
        minLine = Integer.MIN_VALUE;
        defaultLine = this.mPrimaryOrientation.getEndAfterPadding();
        min = null;
        for (i = startIndex; i != endIndex; i += diff) {
            other = this.mSpans[i];
            otherLine = other.getStartLine(defaultLine);
            if (otherLine > minLine) {
                min = other;
                minLine = otherLine;
            }
        }
        return min;
    }

    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    public int scrollHorizontallyBy(int dx, Recycler recycler, State state) {
        return scrollBy(dx, recycler, state);
    }

    public int scrollVerticallyBy(int dy, Recycler recycler, State state) {
        return scrollBy(dy, recycler, state);
    }

    private int calculateScrollDirectionForPosition(int position) {
        int i = -1;
        if (getChildCount() == 0) {
            if (this.mShouldReverseLayout) {
                i = 1;
            }
            return i;
        }
        if ((position < getFirstChildPosition()) == this.mShouldReverseLayout) {
            i = 1;
        }
        return i;
    }

    public PointF computeScrollVectorForPosition(int targetPosition) {
        int direction = calculateScrollDirectionForPosition(targetPosition);
        PointF outVector = new PointF();
        if (direction == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            outVector.x = (float) direction;
            outVector.y = 0.0f;
        } else {
            outVector.x = 0.0f;
            outVector.y = (float) direction;
        }
        return outVector;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) {
        LinearSmoothScroller scroller = new LinearSmoothScroller(recyclerView.getContext());
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }

    public void scrollToPosition(int position) {
        if (!(this.mPendingSavedState == null || this.mPendingSavedState.mAnchorPosition == position)) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = position;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        requestLayout();
    }

    public void scrollToPositionWithOffset(int position, int offset) {
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = position;
        this.mPendingScrollPositionOffset = offset;
        requestLayout();
    }

    @RestrictTo({Scope.LIBRARY})
    public void collectAdjacentPrefetchPositions(int dx, int dy, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int delta = this.mOrientation == 0 ? dx : dy;
        if (getChildCount() != 0) {
            if (delta != 0) {
                int i;
                prepareLayoutStateForDelta(delta, state);
                if (this.mPrefetchDistances == null || this.mPrefetchDistances.length < this.mSpanCount) {
                    this.mPrefetchDistances = new int[this.mSpanCount];
                }
                int i2 = 0;
                int itemPrefetchCount = 0;
                for (i = 0; i < this.mSpanCount; i++) {
                    int distance;
                    if (this.mLayoutState.mItemDirection == -1) {
                        distance = this.mLayoutState.mStartLine - this.mSpans[i].getStartLine(this.mLayoutState.mStartLine);
                    } else {
                        distance = this.mSpans[i].getEndLine(this.mLayoutState.mEndLine) - this.mLayoutState.mEndLine;
                    }
                    if (distance >= 0) {
                        this.mPrefetchDistances[itemPrefetchCount] = distance;
                        itemPrefetchCount++;
                    }
                }
                Arrays.sort(this.mPrefetchDistances, 0, itemPrefetchCount);
                while (true) {
                    i = i2;
                    if (i < itemPrefetchCount && this.mLayoutState.hasMore(state)) {
                        layoutPrefetchRegistry.addPosition(this.mLayoutState.mCurrentPosition, this.mPrefetchDistances[i]);
                        LayoutState layoutState = this.mLayoutState;
                        layoutState.mCurrentPosition += this.mLayoutState.mItemDirection;
                        i2 = i + 1;
                    }
                }
            }
        }
    }

    void prepareLayoutStateForDelta(int delta, State state) {
        int layoutDir;
        int referenceChildPosition;
        if (delta > 0) {
            layoutDir = 1;
            referenceChildPosition = getLastChildPosition();
        } else {
            layoutDir = -1;
            referenceChildPosition = getFirstChildPosition();
        }
        this.mLayoutState.mRecycle = true;
        updateLayoutState(referenceChildPosition, state);
        setLayoutStateDirection(layoutDir);
        this.mLayoutState.mCurrentPosition = this.mLayoutState.mItemDirection + referenceChildPosition;
        this.mLayoutState.mAvailable = Math.abs(delta);
    }

    int scrollBy(int dt, Recycler recycler, State state) {
        if (getChildCount() != 0) {
            if (dt != 0) {
                int totalScroll;
                prepareLayoutStateForDelta(dt, state);
                int consumed = fill(recycler, this.mLayoutState, state);
                if (this.mLayoutState.mAvailable < consumed) {
                    totalScroll = dt;
                } else if (dt < 0) {
                    totalScroll = -consumed;
                } else {
                    totalScroll = consumed;
                }
                this.mPrimaryOrientation.offsetChildren(-totalScroll);
                this.mLastLayoutFromEnd = this.mShouldReverseLayout;
                this.mLayoutState.mAvailable = 0;
                recycle(recycler, this.mLayoutState);
                return totalScroll;
            }
        }
        return 0;
    }

    int getLastChildPosition() {
        int childCount = getChildCount();
        return childCount == 0 ? 0 : getPosition(getChildAt(childCount - 1));
    }

    int getFirstChildPosition() {
        return getChildCount() == 0 ? 0 : getPosition(getChildAt(0));
    }

    private int findFirstReferenceChildPosition(int itemCount) {
        int limit = getChildCount();
        for (int i = 0; i < limit; i++) {
            int position = getPosition(getChildAt(i));
            if (position >= 0 && position < itemCount) {
                return position;
            }
        }
        return 0;
    }

    private int findLastReferenceChildPosition(int itemCount) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            int position = getPosition(getChildAt(i));
            if (position >= 0 && position < itemCount) {
                return position;
            }
        }
        return 0;
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return new LayoutParams(c, attrs);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams lp) {
        if (lp instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) lp);
        }
        return new LayoutParams(lp);
    }

    public boolean checkLayoutParams(android.support.v7.widget.RecyclerView.LayoutParams lp) {
        return lp instanceof LayoutParams;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    @Nullable
    public View onFocusSearchFailed(View focused, int direction, Recycler recycler, State state) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = this;
        State state2 = state;
        if (getChildCount() == 0) {
            return null;
        }
        View directChild = findContainingItemView(focused);
        if (directChild == null) {
            return null;
        }
        resolveShouldLayoutReverse();
        int layoutDir = convertFocusDirectionToLayoutDirection(direction);
        if (layoutDir == Integer.MIN_VALUE) {
            return null;
        }
        int referenceChildPosition;
        View view;
        LayoutParams prevFocusLayoutParams = (LayoutParams) directChild.getLayoutParams();
        boolean prevFocusFullSpan = prevFocusLayoutParams.mFullSpan;
        Span prevFocusSpan = prevFocusLayoutParams.mSpan;
        if (layoutDir == 1) {
            referenceChildPosition = getLastChildPosition();
        } else {
            referenceChildPosition = getFirstChildPosition();
        }
        updateLayoutState(referenceChildPosition, state2);
        setLayoutStateDirection(layoutDir);
        staggeredGridLayoutManager.mLayoutState.mCurrentPosition = staggeredGridLayoutManager.mLayoutState.mItemDirection + referenceChildPosition;
        staggeredGridLayoutManager.mLayoutState.mAvailable = (int) (((float) staggeredGridLayoutManager.mPrimaryOrientation.getTotalSpace()) * MAX_SCROLL_FACTOR);
        staggeredGridLayoutManager.mLayoutState.mStopInFocusable = true;
        int i = 0;
        staggeredGridLayoutManager.mLayoutState.mRecycle = false;
        fill(recycler, staggeredGridLayoutManager.mLayoutState, state2);
        staggeredGridLayoutManager.mLastLayoutFromEnd = staggeredGridLayoutManager.mShouldReverseLayout;
        if (!prevFocusFullSpan) {
            View view2 = prevFocusSpan.getFocusableViewAfter(referenceChildPosition, layoutDir);
            if (!(view2 == null || view2 == directChild)) {
                return view2;
            }
        }
        int i2;
        if (preferLastSpan(layoutDir)) {
            for (i2 = staggeredGridLayoutManager.mSpanCount - 1; i2 >= 0; i2--) {
                view = staggeredGridLayoutManager.mSpans[i2].getFocusableViewAfter(referenceChildPosition, layoutDir);
                if (view != null && view != directChild) {
                    return view;
                }
            }
        } else {
            for (i2 = 0; i2 < staggeredGridLayoutManager.mSpanCount; i2++) {
                view = staggeredGridLayoutManager.mSpans[i2].getFocusableViewAfter(referenceChildPosition, layoutDir);
                if (view != null && view != directChild) {
                    return view;
                }
            }
        }
        boolean shouldSearchFromStart = (staggeredGridLayoutManager.mReverseLayout ^ 1) == (layoutDir == -1);
        if (!prevFocusFullSpan) {
            int findFirstPartiallyVisibleItemPosition;
            if (shouldSearchFromStart) {
                findFirstPartiallyVisibleItemPosition = prevFocusSpan.findFirstPartiallyVisibleItemPosition();
            } else {
                findFirstPartiallyVisibleItemPosition = prevFocusSpan.findLastPartiallyVisibleItemPosition();
            }
            view = findViewByPosition(findFirstPartiallyVisibleItemPosition);
            if (!(view == null || view == directChild)) {
                return view;
            }
        }
        int i3;
        if (!preferLastSpan(layoutDir)) {
            while (true) {
                i3 = i;
                if (i3 >= staggeredGridLayoutManager.mSpanCount) {
                    break;
                }
                if (shouldSearchFromStart) {
                    i = staggeredGridLayoutManager.mSpans[i3].findFirstPartiallyVisibleItemPosition();
                } else {
                    i = staggeredGridLayoutManager.mSpans[i3].findLastPartiallyVisibleItemPosition();
                }
                view = findViewByPosition(i);
                if (view != null && view != directChild) {
                    return view;
                }
                i = i3 + 1;
            }
        } else {
            i = staggeredGridLayoutManager.mSpanCount - 1;
            while (true) {
                i3 = i;
                if (i3 < 0) {
                    break;
                }
                if (i3 != prevFocusSpan.mIndex) {
                    View unfocusableCandidate;
                    if (shouldSearchFromStart) {
                        unfocusableCandidate = staggeredGridLayoutManager.mSpans[i3].findFirstPartiallyVisibleItemPosition();
                    } else {
                        unfocusableCandidate = staggeredGridLayoutManager.mSpans[i3].findLastPartiallyVisibleItemPosition();
                    }
                    unfocusableCandidate = findViewByPosition(unfocusableCandidate);
                    if (unfocusableCandidate != null && unfocusableCandidate != directChild) {
                        return unfocusableCandidate;
                    }
                    view = unfocusableCandidate;
                }
                i = i3 - 1;
            }
        }
        return null;
    }

    private int convertFocusDirectionToLayoutDirection(int focusDirection) {
        int i = -1;
        int i2 = Integer.MIN_VALUE;
        if (focusDirection == 17) {
            if (this.mOrientation != 0) {
                i = Integer.MIN_VALUE;
            }
            return i;
        } else if (focusDirection == 33) {
            if (this.mOrientation != 1) {
                i = Integer.MIN_VALUE;
            }
            return i;
        } else if (focusDirection == 66) {
            if (this.mOrientation == 0) {
                i2 = 1;
            }
            return i2;
        } else if (focusDirection != 130) {
            switch (focusDirection) {
                case 1:
                    return (this.mOrientation != 1 && isLayoutRTL()) ? 1 : -1;
                case 2:
                    return (this.mOrientation != 1 && isLayoutRTL()) ? -1 : 1;
                default:
                    return Integer.MIN_VALUE;
            }
        } else {
            if (this.mOrientation == 1) {
                i2 = 1;
            }
            return i2;
        }
    }
}
