package android.support.v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig.Builder;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.Callback;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.util.DiffUtil.ItemCallback;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView.Adapter;
import java.util.Collections;
import java.util.List;

public class AsyncListDiffer<T> {
    private final AsyncDifferConfig<T> mConfig;
    @Nullable
    private List<T> mList;
    private int mMaxScheduledGeneration;
    @NonNull
    private List<T> mReadOnlyList = Collections.emptyList();
    private final ListUpdateCallback mUpdateCallback;

    public AsyncListDiffer(@NonNull Adapter adapter, @NonNull ItemCallback<T> diffCallback) {
        this.mUpdateCallback = new AdapterListUpdateCallback(adapter);
        this.mConfig = new Builder(diffCallback).build();
    }

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> config) {
        this.mUpdateCallback = listUpdateCallback;
        this.mConfig = config;
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.mReadOnlyList;
    }

    public void submitList(final List<T> newList) {
        if (newList != this.mList) {
            final int runGeneration = this.mMaxScheduledGeneration + 1;
            this.mMaxScheduledGeneration = runGeneration;
            if (newList == null) {
                int countRemoved = this.mList.size();
                this.mList = null;
                this.mReadOnlyList = Collections.emptyList();
                this.mUpdateCallback.onRemoved(0, countRemoved);
            } else if (this.mList == null) {
                this.mList = newList;
                this.mReadOnlyList = Collections.unmodifiableList(newList);
                this.mUpdateCallback.onInserted(0, newList.size());
            } else {
                final List<T> oldList = this.mList;
                this.mConfig.getBackgroundThreadExecutor().execute(new Runnable() {

                    /* renamed from: android.support.v7.recyclerview.extensions.AsyncListDiffer$1$1 */
                    class C04871 extends Callback {
                        C04871() {
                        }

                        public int getOldListSize() {
                            return oldList.size();
                        }

                        public int getNewListSize() {
                            return newList.size();
                        }

                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
                        }

                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
                        }

                        @Nullable
                        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                            return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
                        }
                    }

                    public void run() {
                        final DiffResult result = DiffUtil.calculateDiff(new C04871());
                        AsyncListDiffer.this.mConfig.getMainThreadExecutor().execute(new Runnable() {
                            public void run() {
                                if (AsyncListDiffer.this.mMaxScheduledGeneration == runGeneration) {
                                    AsyncListDiffer.this.latchList(newList, result);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    private void latchList(@NonNull List<T> newList, @NonNull DiffResult diffResult) {
        this.mList = newList;
        this.mReadOnlyList = Collections.unmodifiableList(newList);
        diffResult.dispatchUpdatesTo(this.mUpdateCallback);
    }
}
