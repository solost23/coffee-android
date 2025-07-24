package com.example.coffee.ui.layout;

import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.coffee.databinding.LayoutMaterialBinding;
import com.example.coffee.ui.MainActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;

import java.text.Format;
import java.util.Formatter;
import java.util.Locale;

public class MaterialLayout
{
    private static ExoPlayer player;
    private static boolean isSeeking = false;
    private static StringBuilder sb;
    private static Formatter fm;

    public static Void onCreate(MainActivity activity)
    {
        try {
            activity.getBinding().contentFrame.removeAllViews();
            Log.v("MaterialLayout", "物料管理");

            LayoutMaterialBinding binding = LayoutMaterialBinding.inflate(activity.getLayoutInflater());

            activity.getBinding().contentFrame.addView(binding.getRoot());

            sb = new StringBuilder();
            fm = new Formatter(sb, Locale.getDefault());

            // 初始化播放器
            initPlayer(binding, activity);
            // 初始化ui和监听器
            initViews(binding, activity);
        } catch (Exception e) {
            Log.e("MaterialLayout", "播放器初始化失败", e);
            activity.toastShort("播放器初始化失败");
        }

        return null;
    }

    private static void initPlayer(LayoutMaterialBinding binding, MainActivity activity)
    {
        player = new ExoPlayer.Builder(activity).build();
        binding.playerView.setPlayer(player);

        // 创建媒体项
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"));

        // 设置媒体项并准备播放
        player.setMediaItem(mediaItem);
        player.prepare();

        // 自动开始播放
        player.setPlayWhenReady(true);

        // 添加播放状态监听器
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state)
            {
                switch (state) {
                    case Player.STATE_IDLE:
                        Log.d("Player", "状态: 空闲");
                        break;
                    case Player.STATE_BUFFERING:
                        Log.d("Player", "状态: 缓冲中");
                        break;
                    case Player.STATE_READY:
                        Log.d("Player", "状态: 准备就绪");
                        updateProgress(binding);
                        break;
                    case Player.STATE_ENDED:
                        Log.d("Player", "状态: 播放结束");
                        player.seekTo(0);
                        player.setPlayWhenReady(false);
                        break;
                }
            }

            @Override
            public void onPlayerError(PlaybackException e)
            {
                Log.e("Player", "播放错误：" + e.getMessage());
                activity.toastLong("播放错误：" + e.getMessage());
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying)
            {
                // 更新按钮状态
                binding.btnPlay.setEnabled(!isPlaying);
                binding.btnPause.setEnabled(isPlaying);
                Log.d("Player", "播放状态：" + (isPlaying ? "正在播放" : "已暂停"));
            }
        });
    }

    private static void initViews(LayoutMaterialBinding binding, MainActivity activity)
    {
        // 播放按钮
        binding.btnPlay.setOnClickListener(v -> {
            if (player != null)
            {
                player.setPlayWhenReady(true);
            }
        });

        // 暂停按钮
        binding.btnPause.setOnClickListener(v -> {
            if (player != null)
            {
                player.setPlayWhenReady(false);
            }
        });

        // 停止按钮
        binding.btnStop.setOnClickListener(v -> {
            if (player != null)
            {
                player.stop();
                player.seekTo(0);
                player.setPlayWhenReady(false);
            }
        });

        // 进度条监听
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && player != null)
                {
                    binding.tvPosition.setText(stringForTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (player != null)
                {
                    player.seekTo(seekBar.getProgress());
                    isSeeking = false;
                }
            }
        });

        // 初始化进度显示
        if (player != null)
        {
            long duration = player.getDuration();
            if (duration != C.TIME_UNSET)
            {
                binding.seekBar.setMax((int)duration);
                binding.tvDuration.setText(stringForTime((int) duration));
            }
        }
    }

    private static void updateProgress(LayoutMaterialBinding binding)
    {
        if (player == null || isSeeking) return;

        long position = player.getCurrentPosition();
        long duration = player.getDuration();

        binding.seekBar.setProgress((int)position);
        binding.tvPosition.setText(stringForTime((int) position));

        if (duration != C.TIME_UNSET)
        {
            binding.seekBar.setMax((int)duration);
            binding.tvDuration.setText(stringForTime((int) duration));
        }

        // 每秒更新一次进度
        binding.playerView.postDelayed(() -> updateProgress(binding), 1000);
    }

    private static String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        sb.setLength(0);
        return hours > 0 ? fm.format("%d:%02d:%02d", hours, minutes, seconds).toString()
                : fm.format("%02d:%02d", minutes, seconds).toString();
    }

    public static void onDestroy() {
        if (player != null) {
            player.release();
            player = null;
        }
        if (fm != null) {
            fm.close();
        }
    }
}
