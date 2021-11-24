package com.luck.picture.lib.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.MediaAdapterStyle;
import com.luck.picture.lib.utils.StyleUtils;

/**
 * @author：luck
 * @date：2021/11/20 3:59 下午
 * @describe：ImageViewHolder
 */
public class ImageViewHolder extends BaseRecyclerMediaHolder {
    private final ImageView ivEditor;
    private final TextView tvMediaTag;

    public ImageViewHolder(View itemView, PictureSelectionConfig config) {
        super(itemView, config);
        tvMediaTag = itemView.findViewById(R.id.tv_media_tag);
        ivEditor = itemView.findViewById(R.id.ivEditor);
        MediaAdapterStyle adapterStyle = PictureSelectionConfig.selectorStyle.getAdapterStyle();
        int imageEditorRes = adapterStyle.getAdapterImageEditorRes();
        if (StyleUtils.checkStyleValidity(imageEditorRes)) {
            ivEditor.setImageResource(imageEditorRes);
        }
        int[] editorGravity = adapterStyle.getAdapterImageEditorGravity();
        if (StyleUtils.checkArrayValidity(editorGravity)) {
            if (ivEditor.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) ivEditor.getLayoutParams()).removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                for (int i : editorGravity) {
                    ((RelativeLayout.LayoutParams) ivEditor.getLayoutParams()).addRule(i);
                }
            }
        }

        int[] tagGravity = adapterStyle.getAdapterTagGravity();
        if (StyleUtils.checkArrayValidity(tagGravity)) {
            if (tvMediaTag.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) tvMediaTag.getLayoutParams()).removeRule(RelativeLayout.ALIGN_PARENT_END);
                ((RelativeLayout.LayoutParams) tvMediaTag.getLayoutParams()).removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                for (int i : tagGravity) {
                    ((RelativeLayout.LayoutParams) tvMediaTag.getLayoutParams()).addRule(i);
                }
            }
        }
        int background = adapterStyle.getAdapterTagBackground();
        if (StyleUtils.checkStyleValidity(background)) {
            tvMediaTag.setBackgroundResource(background);
        }

        int textSize = adapterStyle.getAdapterTagTextSize();
        if (StyleUtils.checkSizeValidity(textSize)) {
            tvMediaTag.setTextSize(textSize);
        }

        int textColor = adapterStyle.getAdapterTagTextColor();
        if (StyleUtils.checkStyleValidity(textColor)) {
            tvMediaTag.setTextColor(textColor);
        }
    }


    @Override
    public void bindData(LocalMedia media, int position) {
        super.bindData(media, position);
        if (media.isEditorImage() && media.isCut()) {
            ivEditor.setVisibility(View.VISIBLE);
        } else {
            ivEditor.setVisibility(View.GONE);
        }
        tvMediaTag.setVisibility(View.VISIBLE);
        if (PictureMimeType.isGif(media.getMimeType())) {
            tvMediaTag.setText(mContext.getString(R.string.picture_gif_tag));
        } else if (PictureMimeType.isWebp(media.getMimeType())) {
            tvMediaTag.setText(mContext.getString(R.string.picture_webp_tag));
        } else if (PictureMimeType.isLongImage(media.getWidth(), media.getHeight())) {
            tvMediaTag.setText(mContext.getString(R.string.picture_webp_tag));
        } else {
            tvMediaTag.setVisibility(View.GONE);
        }
    }
}
