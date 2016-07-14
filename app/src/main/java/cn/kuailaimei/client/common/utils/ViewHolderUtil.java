package cn.kuailaimei.client.common.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by qianlu on 16/5/13.
 */
public class ViewHolderUtil {

        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View convertView, int resId) {
            SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                convertView.setTag(viewHolder);
            }
            View childView = viewHolder.get(resId);
            if (childView == null) {
                childView = convertView.findViewById(resId);
                viewHolder.put(resId, childView);
            }
            return (T) childView;
        }

}
