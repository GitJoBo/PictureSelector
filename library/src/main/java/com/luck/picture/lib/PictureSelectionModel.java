package com.luck.picture.lib;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressEngine;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnCameraEventInterceptListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.tools.DoubleUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author：luck
 * @date：2017-5-24 21:30
 * @describe：PictureSelectionModel
 */

public class PictureSelectionModel {
    private final PictureSelectionConfig selectionConfig;
    private final PictureSelector selector;

    public PictureSelectionModel(PictureSelector selector, int chooseMode) {
        this.selector = selector;
        selectionConfig = PictureSelectionConfig.getCleanInstance();
        selectionConfig.chooseMode = chooseMode;
    }

    public PictureSelectionModel(PictureSelector selector, int chooseMode, boolean isOnlyCamera) {
        this.selector = selector;
        selectionConfig = PictureSelectionConfig.getCleanInstance();
        selectionConfig.isOnlyCamera = isOnlyCamera;
        selectionConfig.chooseMode = chooseMode;
    }

    /**
     * @param locale Language
     * @return PictureSelectionModel
     */
    public PictureSelectionModel setLanguage(int language) {
        selectionConfig.language = language;
        return this;
    }

    /**
     * @param engine Image Load the engine
     * @return
     */
    public PictureSelectionModel imageEngine(ImageEngine engine) {
        if (PictureSelectionConfig.imageEngine != engine) {
            PictureSelectionConfig.imageEngine = engine;
        }
        return this;
    }

    /**
     * @param engine Image Compress the engine
     * @return
     */
    public PictureSelectionModel compressEngine(CompressEngine engine) {
        if (PictureSelectionConfig.compressEngine != engine) {
            PictureSelectionConfig.compressEngine = engine;
        }
        return this;
    }

    /**
     * Intercept camera click events, and users can implement their own camera framework
     *
     * @param listener
     * @return
     */
    public PictureSelectionModel setCameraInterceptListener(OnCameraEventInterceptListener listener) {
        PictureSelectionConfig.interceptCameraListener = listener;
        return this;
    }

    /**
     * Change the desired orientation of this activity.  If the activity
     * is currently in the foreground or otherwise impacting the screen
     * orientation, the screen will immediately be changed (possibly causing
     * the activity to be restarted). Otherwise, this will be used the next
     * time the activity is visible.
     *
     * @param requestedOrientation An orientation constant as used in
     *                             {@link android.content.pm.ActivityInfo.screenOrientation ActivityInfo.screenOrientation}.
     */
    public PictureSelectionModel setRequestedOrientation(int requestedOrientation) {
        selectionConfig.requestedOrientation = requestedOrientation;
        return this;
    }


    /**
     * @param selectionMode PictureSelector Selection model and PictureConfig.MULTIPLE or PictureConfig.SINGLE
     * @return
     */
    public PictureSelectionModel selectionMode(int selectionMode) {
        selectionConfig.selectionMode = selectionMode;
        return this;
    }


    /**
     * @param enablePreviewAudio
     * @return
     */
    public PictureSelectionModel isEnablePreviewAudio(boolean enablePreviewAudio) {
        selectionConfig.isEnablePreviewAudio = enablePreviewAudio;
        return this;
    }


    /**
     * You can select pictures and videos at the same time
     *
     * @param isWithVideoImage Whether the pictures and videos can be selected together
     * @return
     */
    public PictureSelectionModel isWithSelectVideoImage(boolean isWithVideoImage) {
        selectionConfig.isWithVideoImage =
                selectionConfig.selectionMode != PictureConfig.SINGLE
                        && selectionConfig.chooseMode == SelectMimeType.ofAll() && isWithVideoImage;
        return this;
    }

    /**
     * Choose between photographing and shooting in ofAll mode
     *
     * @param ofAllCameraType {@link PictureMimeType.ofImage or PictureMimeType.ofVideo}
     *                        The default is ofAll() mode
     * @return
     */
    public PictureSelectionModel setOfAllCameraType(int ofAllCameraType) {
        selectionConfig.ofAllCameraType = ofAllCameraType;
        return this;
    }

    /**
     * When the maximum number of choices is reached, does the list enable the mask effect
     *
     * @param isMaxSelectEnabledMask
     * @return
     */
    public PictureSelectionModel isMaxSelectEnabledMask(boolean isMaxSelectEnabledMask) {
        selectionConfig.isMaxSelectEnabledMask = isMaxSelectEnabledMask;
        return this;
    }

    /**
     * If SyncCover
     *
     * @param isSyncCover
     * @return
     */
    public PictureSelectionModel isSyncCover(boolean isSyncCover) {
        selectionConfig.isSyncCover = isSyncCover;
        return this;
    }

    /**
     * @param maxSelectNum PictureSelector max selection
     * @return
     */
    public PictureSelectionModel maxSelectNum(int maxSelectNum) {
        selectionConfig.maxSelectNum = maxSelectNum;
        return this;
    }

    /**
     * @param minSelectNum PictureSelector min selection
     * @return
     */
    public PictureSelectionModel minSelectNum(int minSelectNum) {
        selectionConfig.minSelectNum = minSelectNum;
        return this;
    }

    /**
     * @param maxVideoSelectNum PictureSelector video max selection
     * @return
     */
    public PictureSelectionModel maxVideoSelectNum(int maxVideoSelectNum) {
        selectionConfig.maxVideoSelectNum = selectionConfig.chooseMode == SelectMimeType.ofVideo() ? 0 : maxVideoSelectNum;
        return this;
    }

    /**
     * @param minVideoSelectNum PictureSelector video min selection
     * @return
     */
    public PictureSelectionModel minVideoSelectNum(int minVideoSelectNum) {
        selectionConfig.minVideoSelectNum = minVideoSelectNum;
        return this;
    }


    /**
     * By clicking the title bar consecutively, RecyclerView automatically rolls back to the top
     *
     * @param isAutomaticTitleRecyclerTop
     * @return
     */
    public PictureSelectionModel isAutomaticTitleRecyclerTop(boolean isAutomaticTitleRecyclerTop) {
        selectionConfig.isAutomaticTitleRecyclerTop = isAutomaticTitleRecyclerTop;
        return this;
    }


    /**
     * @param Select whether to return directly
     * @return
     */
    public PictureSelectionModel isDirectReturnSingle(boolean isDirectReturn) {
        selectionConfig.isDirectReturnSingle = selectionConfig.selectionMode
                == PictureConfig.SINGLE && isDirectReturn;
        selectionConfig.isOriginalControl = (selectionConfig.selectionMode != PictureConfig.SINGLE || !isDirectReturn) && selectionConfig.isOriginalControl;
        return this;
    }

    /**
     * Whether to turn on paging mode
     *
     * @param isPageStrategy
     * @param pageSize       Maximum number of pages {@link PageSize is preferably no less than 20}
     * @return
     */
    public PictureSelectionModel isPageStrategy(boolean isPageStrategy, int pageSize) {
        selectionConfig.isPageStrategy = isPageStrategy;
        selectionConfig.pageSize = pageSize < PictureConfig.MIN_PAGE_SIZE ? PictureConfig.MAX_PAGE_SIZE : pageSize;
        return this;
    }

    /**
     * Whether to turn on paging mode
     *
     * @param isPageStrategy
     * @param pageSize            Maximum number of pages {@link  PageSize is preferably no less than 20}
     * @param isFilterInvalidFile Whether to filter invalid files {@link Some of the query performance is consumed,Especially on the Q version}
     * @return
     */
    public PictureSelectionModel isPageStrategy(boolean isPageStrategy, int pageSize, boolean isFilterInvalidFile) {
        selectionConfig.isPageStrategy = isPageStrategy;
        selectionConfig.pageSize = pageSize < PictureConfig.MIN_PAGE_SIZE ? PictureConfig.MAX_PAGE_SIZE : pageSize;
        selectionConfig.isFilterInvalidFile = isFilterInvalidFile;
        return this;
    }

    /**
     * Whether to turn on paging mode
     *
     * @param isPageStrategy
     * @return
     */
    public PictureSelectionModel isPageStrategy(boolean isPageStrategy) {
        selectionConfig.isPageStrategy = isPageStrategy;
        return this;
    }

    /**
     * Whether to turn on paging mode
     *
     * @param isPageStrategy
     * @param isFilterInvalidFile Whether to filter invalid files {@link Some of the query performance is consumed,Especially on the Q version}
     * @return
     */
    public PictureSelectionModel isPageStrategy(boolean isPageStrategy, boolean isFilterInvalidFile) {
        selectionConfig.isPageStrategy = isPageStrategy;
        selectionConfig.isFilterInvalidFile = isFilterInvalidFile;
        return this;
    }

    /**
     * @param videoQuality video quality and 0 or 1
     * @return
     */
    public PictureSelectionModel videoQuality(int videoQuality) {
        selectionConfig.videoQuality = videoQuality;
        return this;
    }


    /**
     * camera output image format
     *
     * @param imageFormat PictureSelector media format
     * @return
     */
    public PictureSelectionModel setCameraImageFormat(String imageFormat) {
        selectionConfig.cameraImageFormat = imageFormat;
        if (SdkVersionUtils.isQ() || SdkVersionUtils.isR()) {
            if (TextUtils.equals(imageFormat, PictureMimeType.PNG)) {
                selectionConfig.cameraImageFormatForQ = PictureMimeType.PNG_Q;
            }
            if (TextUtils.equals(imageFormat, PictureMimeType.JPG) || TextUtils.equals(imageFormat, PictureMimeType.JPEG)) {
                selectionConfig.cameraImageFormatForQ = PictureMimeType.JPEG_Q;
            }
        }
        return this;
    }

    /**
     * camera output video format
     *
     * @param videoFormat PictureSelector media format
     * @return
     */
    public PictureSelectionModel setCameraVideoFormat(String videoFormat) {
        selectionConfig.cameraVideoFormat = videoFormat;
        if (SdkVersionUtils.isQ() || SdkVersionUtils.isR()) {
            if (TextUtils.equals(videoFormat, PictureMimeType.MP4)) {
                selectionConfig.cameraVideoFormatForQ = PictureMimeType.MP4_Q;
            }
            if (TextUtils.equals(videoFormat, PictureMimeType.AVI)) {
                selectionConfig.cameraVideoFormatForQ = PictureMimeType.AVI_Q;
            }
        }
        return this;
    }


    /**
     * camera output audio format
     *
     * @param videoFormat PictureSelector media format
     * @return
     */
    public PictureSelectionModel setCameraAudioFormat(String audioFormat) {
        selectionConfig.cameraAudioFormat = audioFormat;
        if (SdkVersionUtils.isQ() || SdkVersionUtils.isR()) {
            if (TextUtils.equals(audioFormat, PictureMimeType.AMR)) {
                selectionConfig.cameraAudioFormatForQ = PictureMimeType.AMR_Q;
            }
            if (TextUtils.equals(audioFormat, PictureMimeType.WAV)) {
                selectionConfig.cameraAudioFormatForQ = PictureMimeType.WAV_Q;
            }
            if (TextUtils.equals(audioFormat, PictureMimeType.MP3)) {
                selectionConfig.cameraAudioFormatForQ = PictureMimeType.MP3_Q;
            }
        }
        return this;
    }


    /**
     * @param videoMaxSecond selection video max second
     * @return
     */
    public PictureSelectionModel videoMaxSecond(int videoMaxSecond) {
        selectionConfig.videoMaxSecond = (videoMaxSecond * 1000);
        return this;
    }

    /**
     * @param videoMinSecond selection video min second
     * @return
     */
    public PictureSelectionModel videoMinSecond(int videoMinSecond) {
        selectionConfig.videoMinSecond = videoMinSecond * 1000;
        return this;
    }


    /**
     * @param maxSecond video record second
     * @return
     */
    public PictureSelectionModel recordVideoMaxSecond(int maxSecond) {
        selectionConfig.recordVideoMaxSecond = maxSecond;
        return this;
    }

    /**
     * @param minSecond video record second
     * @return
     */
    public PictureSelectionModel recordVideoMinSecond(int minSecond) {
        selectionConfig.recordVideoMinSecond = minSecond;
        return this;
    }

    /**
     * @param imageSpanCount PictureSelector image span count
     * @return
     */
    public PictureSelectionModel imageSpanCount(int imageSpanCount) {
        selectionConfig.imageSpanCount = imageSpanCount;
        return this;
    }

    /**
     * @param isEmptyReturn No data can be returned
     * @return
     */
    public PictureSelectionModel isEmptyResultReturn(boolean isEmptyReturn) {
        selectionConfig.isEmptyResultReturn = isEmptyReturn;
        return this;
    }


    /**
     * After recording with the system camera, does it support playing the video immediately using the system player
     *
     * @param isQuickCapture
     * @return
     */
    public PictureSelectionModel isQuickCapture(boolean isQuickCapture) {
        selectionConfig.isQuickCapture = isQuickCapture;
        return this;
    }

    /**
     * @param isOriginalControl Whether the original image is displayed
     * @return
     */
    public PictureSelectionModel isOriginalImageControl(boolean isOriginalControl) {
        selectionConfig.isOriginalControl = !selectionConfig.isOnlyCamera
                && selectionConfig.chooseMode != SelectMimeType.ofVideo()
                && selectionConfig.chooseMode != SelectMimeType.ofAudio() && isOriginalControl;
        return this;
    }

    /**
     * @param isDisplayOriginalSize Whether the original image size is displayed
     * @return
     */
    public PictureSelectionModel isDisplayOriginalSize(boolean isDisplayOriginalSize) {
        selectionConfig.isDisplayOriginalSize = !selectionConfig.isOnlyCamera && isDisplayOriginalSize;
        return this;
    }

    /**
     * @param isEditorImage is editor image
     * @return
     */
    public PictureSelectionModel isEditorImage(boolean isEditorImage) {
        selectionConfig.isEditorImage = isEditorImage;
        return this;
    }


    /**
     * Camera custom local file name
     * # Such as xxx.png
     *
     * @param fileName
     * @return
     */
    public PictureSelectionModel cameraFileName(String fileName) {
        selectionConfig.cameraFileName = fileName;
        return this;
    }


    /**
     * @param zoomAnim Picture list zoom anim
     * @return
     */
    public PictureSelectionModel isZoomAnim(boolean zoomAnim) {
        selectionConfig.zoomAnim = zoomAnim;
        return this;
    }


    /**
     * @param isAutoScalePreviewImage preview image width auto scale the screen
     * @return
     */
    public PictureSelectionModel isAutoScalePreviewImage(boolean isAutoScalePreviewImage) {
        selectionConfig.isAutoScalePreviewImage = isAutoScalePreviewImage;
        return this;
    }

    /**
     * @param isDisplayCamera Whether to open camera button
     * @return
     */
    public PictureSelectionModel isDisplayCamera(boolean isDisplayCamera) {
        selectionConfig.isDisplayCamera = isDisplayCamera;
        return this;
    }

    /**
     * @param outPutCameraPath Camera out path
     * @return
     */
    public PictureSelectionModel setOutputCameraPath(String outPutCameraPath) {
        selectionConfig.outPutCameraPath = outPutCameraPath;
        return this;
    }

    /**
     * Query the pictures or videos in the specified directory
     *
     * @param directoryPath Camera out path
     *                      <p>
     *                      Normally, it should be consistent with {@link PictureSelectionConfig.setOutputCameraPath()};
     *                      </p>
     *
     *                      <p>
     *                      If build.version.sdk_INT < 29,{@link PictureSelectionConfig.setQuerySandboxDirectory();}
     *                      Do not set the external storage path,
     *                      which may cause the problem of picture duplication
     *                      </p>
     * @return
     */
    public PictureSelectionModel setQuerySandboxDirectory(String directoryPath) {
        selectionConfig.sandboxFolderPath = directoryPath;
        return this;
    }

    /**
     * Only the resources in the specified directory are displayed
     * <p>
     * Only Display setQuerySandboxDirectory();  Source
     * <p/>
     *
     * @param isOnlySandboxDir true or Only Display {@link PictureSelectionConfig.setQuerySandboxDirectory();}
     * @return
     */
    public PictureSelectionModel isGetOnlySandboxDirectory(boolean isOnlySandboxDir) {
        selectionConfig.isOnlySandboxDir = isOnlySandboxDir;
        return this;
    }


    /**
     * # file size The unit is KB
     *
     * @param fileSize Filter max file size
     * @return
     */
    public PictureSelectionModel filterMaxFileSize(long fileKbSize) {
        if (fileKbSize >= PictureConfig.MB) {
            selectionConfig.filterMaxFileSize = fileKbSize;
        } else {
            selectionConfig.filterMaxFileSize = fileKbSize * 1024;
        }
        return this;
    }

    /**
     * # file size The unit is KB
     *
     * @param fileSize Filter min file size
     * @return
     */
    public PictureSelectionModel filterMinFileSize(long fileKbSize) {
        if (fileKbSize >= PictureConfig.MB) {
            selectionConfig.filterMinFileSize = fileKbSize;
        } else {
            selectionConfig.filterMinFileSize = fileKbSize * 1024;
        }
        return this;
    }

    /**
     * query specified mimeType
     *
     * @param mimeTypes Use example {@link { image/jpeg or image/png ... }}
     * @return
     */
    public PictureSelectionModel queryMimeTypeConditions(String... mimeTypes) {
        if (mimeTypes != null && mimeTypes.length > 0) {
            selectionConfig.queryMimeTypeHashSet = new HashSet<>(Arrays.asList(mimeTypes));
        } else {
            selectionConfig.queryMimeTypeHashSet = null;
        }
        return this;
    }

    /**
     * @param isGif Whether to open gif
     * @return
     */
    public PictureSelectionModel isGif(boolean isGif) {
        selectionConfig.isGif = isGif;
        return this;
    }

    /**
     * @param isWebp Whether to open .webp
     * @return
     */
    public PictureSelectionModel isWebp(boolean isWebp) {
        selectionConfig.isWebp = isWebp;
        return this;
    }

    /**
     * @param isBmp Whether to open .isBmp
     * @return
     */
    public PictureSelectionModel isBmp(boolean isBmp) {
        selectionConfig.isBmp = isBmp;
        return this;
    }

    /**
     * @param enablePreview Do you want to preview the picture?
     * @return
     */
    public PictureSelectionModel isPreviewImage(boolean enablePreview) {
        selectionConfig.isEnablePreview = enablePreview;
        return this;
    }


    /**
     * @param enPreviewVideo Do you want to preview the video?
     * @return
     */
    public PictureSelectionModel isPreviewVideo(boolean enPreviewVideo) {
        selectionConfig.isEnPreviewVideo = enPreviewVideo;
        return this;
    }

    /**
     * @param isHidePreviewDownload Previews do not show downloads
     * @return
     */
    public PictureSelectionModel isHidePreviewDownload(boolean isHidePreviewDownload) {
        selectionConfig.isHidePreviewDownload = isHidePreviewDownload;
        return this;
    }


    /**
     * @param isClickSound Whether to open click voice
     * @return
     */
    public PictureSelectionModel isOpenClickSound(boolean isClickSound) {
        selectionConfig.isOpenClickSound = !selectionConfig.isOnlyCamera && isClickSound;
        return this;
    }

    /**
     * Set camera direction (after default image)
     */
    public PictureSelectionModel isCameraAroundState(boolean isCameraAroundState) {
        selectionConfig.isCameraAroundState = isCameraAroundState;
        return this;
    }

    /**
     * Camera image rotation, automatic correction
     */
    public PictureSelectionModel isCameraRotateImage(boolean isCameraRotateImage) {
        selectionConfig.isCameraRotateImage = isCameraRotateImage;
        return this;
    }

    /**
     * Compress image rotation, automatic correction
     */
    public PictureSelectionModel isAutoRotating(boolean isAutoRotating) {
        selectionConfig.isAutoRotating = isAutoRotating;
        return this;
    }

    /**
     * @param selectionData Select the selected picture set
     * @return
     */
    public PictureSelectionModel selectionData(List<LocalMedia> selectionData) {
        if (selectionConfig.selectionMode == PictureConfig.SINGLE && selectionConfig.isDirectReturnSingle) {
            selectionConfig.selectionMedias = null;
        } else {
            selectionConfig.selectionMedias = selectionData;
        }
        return this;
    }

    /**
     * Photo album list animation {}
     * Use {@link AnimationType#ALPHA_IN_ANIMATION or SLIDE_IN_BOTTOM_ANIMATION} directly.
     *
     * @param animationMode
     * @return
     */
    public PictureSelectionModel setRecyclerAnimationMode(int animationMode) {
        selectionConfig.animationMode = animationMode;
        return this;
    }

    /**
     * Start PictureSelector
     *
     * @param call
     */
    public void forResult(OnResultCallbackListener<LocalMedia> call) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Activity activity = selector.getActivity();
            if (activity == null) {
                throw new NullPointerException("getActivity is null");
            }
            if (call == null) {
                throw new NullPointerException("OnResultCallbackListener is null");
            }
            if (PictureSelectionConfig.imageEngine == null) {
                throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
            }
            // 绑定回调监听
            PictureSelectionConfig.resultCallListener = call;
            if (selectionConfig.isOnlyCamera) {
                FragmentManager fragmentManager = null;
                if (activity instanceof AppCompatActivity) {
                    fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
                } else if (activity instanceof FragmentActivity) {
                    fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                }
                if (fragmentManager == null) {
                    throw new NullPointerException(" FragmentManager is empty ");
                }
                PictureOnlyCameraFragment fragment = PictureOnlyCameraFragment.newInstance();
                fragmentManager.beginTransaction()
                        .add(android.R.id.content, fragment, PictureOnlyCameraFragment.TAG)
                        .addToBackStack(PictureOnlyCameraFragment.TAG)
                        .commitAllowingStateLoss();
            } else {
                Intent intent = new Intent(activity, PictureSelectorSupporterActivity.class);
                activity.startActivity(intent);
                PictureWindowAnimationStyle windowAnimationStyle = PictureSelectionConfig.selectorStyle.getWindowAnimationStyle();
                activity.overridePendingTransition(windowAnimationStyle.activityEnterAnimation, R.anim.ps_anim_fade_in);
            }
        }
    }
}
