package app.revanced.bilibili.patches;

import android.view.View;
import android.widget.TextView;

import com.bapis.bilibili.app.playurl.v1.Stream;
import com.bapis.bilibili.app.playurl.v1.StreamInfoEx;
import com.bapis.bilibili.app.playurl.v1.VideoInfo;

import app.revanced.bilibili.utils.Utils;

public class TrialQualityPatch {

    private static int dp2px(float dp) {
        return (int) ((dp * Utils.getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void makeVipFree(VideoInfo videoInfo) {
        videoInfo.getStreamListList().stream().filter(Stream::hasDashVideo)
                .forEach(e -> {
                    var streamInfo = e.getStreamInfo();
                    if (streamInfo.getNeedVip()) {
                        StreamInfoEx.setNeedVip(streamInfo, false);
                        StreamInfoEx.setVipFree(streamInfo, true);
                    }
                });
    }

    public static void onBindOnline(boolean selected, TextView strokeBadge, TextView solidBadge) {
        if (!Utils.isEffectiveVip() && Utils.getString("try_listening_tips") // 限免中
                .equals(solidBadge.getText().toString())) {
            solidBadge.setVisibility(View.GONE);
            var strokeText = selected ? Utils.getString("player_try_watching") // 试看中
                    : Utils.getString("player_try_watch_enable"); // 可试看
            strokeBadge.setText(strokeText);
            strokeBadge.setVisibility(View.VISIBLE);
            if (strokeText.length() > 2) {
                strokeBadge.setPadding(dp2px(4f), dp2px(1f), dp2px(4f), dp2px(2f));
            } else {
                strokeBadge.setPadding(dp2px(8.5f), dp2px(1f), dp2px(8.5f), dp2px(2f));
            }
        }
    }
}
