package com.example.work_weixin;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class musicDetails extends AppCompatActivity {
    ImageButton imageButtonMusicList;
    RecyclerView recyclerViewMusicList;
    boolean isView = false;
    boolean isshoucang=false;
    Context context;
    ImageView imageView_shoucang;

    Music music;
    MusicList MusicList;

    TextView name;
    TextView author;

    Myadapter_playlist adapter;
    ArrayList<Music> list;

    SimpleExoPlayer player;
    //监测当前播放状态
    boolean isPlaying=false;

    String url;

    ImageView imageView_tuzi;

    ImageView previous,next;

    //当前播放歌曲的索引(数据库中)
    //数据库中的_id是从1开始,而Array中的是从0开始,注意转化
    int currentSongIndex ;

    //进度条显示功能:
    SeekBar seekBar;
    TextView begin;
    TextView end;

    //负责在主线程中更新UI
    Handler handler;

    //更新进度条
    Runnable  updateProgressRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);
        context = this;
        handler = new Handler(Looper.getMainLooper());

        Intent intent=getIntent();
        //取出从列表跳转到现在的数据歌曲,歌单
        music=(Music) intent.getSerializableExtra("music");
        MusicList=music.musicList;
        //将数据的内容展示在对应控件中
        name=findViewById(R.id.textMusicName);
        author=findViewById(R.id.textMusicAuthor);
        //当前歌曲所在歌单列表
        recyclerViewMusicList = findViewById(R.id.RecyclerViewMusicList);
        imageButtonMusicList = findViewById(R.id.imageButtonMusicList);
        list=MusicList.musicList;
        //将配适器设置给对应的 recyclerView
        adapter = new Myadapter_playlist(context, list);
        recyclerViewMusicList.setAdapter(adapter);
        //布局管理
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewMusicList.setLayoutManager(manager);
        //使用第三方流媒体库ExoPlayer处理音乐播放
        //ExoPlayer 播放网络音乐资源时，实际的解码和播放过程是在后台线程中完成的，而不是在主线程中。
        //从传进来的实例中获取音乐资源
        //播放当前索引歌曲(数据库索引-->数组索引):此时是由列表跳转的到的
        currentSongIndex=music.getId()-1;
        playCurrentSong();
        //播放按钮(兔子)按下改变颜色-->播放暂停功能的实现
        imageView_tuzi = findViewById(R.id.imageTUZI);
        imageView_tuzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlaying=!isPlaying;
                if (isPlaying) {
                    //播放
                    imageView_tuzi.setImageResource(R.drawable.tuzih);
                    player.setPlayWhenReady(true);
                } else {
                    //暂停
                    imageView_tuzi.setImageResource(R.drawable.tuzi);
                    player.setPlayWhenReady(false);
                }
            }
        });

        //播放上一首歌:
        previous=findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPreviousSong();
            }
        });
        //播放下一首歌:
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextSong();
            }
        });

        //进度条:显示
        seekBar=findViewById(R.id.seekBar);
        begin=findViewById(R.id.begin);
        end=findViewById(R.id.end);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // 用户通过拖动进度条调整位置
                    long duration = player.getDuration();
                    long newPosition = (duration * progress) / seekBar.getMax();
                    begin.setText(formatTime(newPosition));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 用户开始拖动进度条，暂停播放器
                player.setPlayWhenReady(false);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 用户停止拖动进度条，跳转到新的位置并恢复播放
                long duration = player.getDuration();
                long newPosition = (duration * seekBar.getProgress()) / seekBar.getMax();
                player.seekTo(newPosition);
                player.setPlayWhenReady(true);
            }
        });
        //当前正在播放的歌单
        // 设置初始状态下列表为隐藏
        recyclerViewMusicList.setVisibility(View.GONE);
        imageButtonMusicList.setOnClickListener(new View.OnClickListener() {
            @Override
            //点击按钮时，根据列表的可见性状态，显示或隐藏 RecyclerView 列表。
            public void onClick(View view) {
                if (!isView) {
                    recyclerViewMusicList.setVisibility(View.VISIBLE);
                    // 将RecyclerView移动到最顶层
                    recyclerViewMusicList.bringToFront();
                    isView = true;
                } else {
                    recyclerViewMusicList.setVisibility(View.GONE);
                    isView = false;
                }
            }
        });
        //收藏按钮:按下星星填满:具体功能尚未实现
        imageView_shoucang = findViewById(R.id.shoucang);
        imageView_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isshoucang) {
                    imageView_shoucang.setImageResource(R.drawable.shoucang);
                    isshoucang = true;
                } else {
                    imageView_shoucang.setImageResource(R.drawable.shoucangy);
                    isshoucang = false;
                }
            }
        });

//        //将ItemTouchHelper与RecyclerView关联
//        //监听删除动作:适配器通过onItemDeleteListener来回调监听器的方法。
//        adapter.setOnItemDeleteListener(this::onDelete);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(adapter.simpleCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerViewMusicList);

    }


    //释放当前播放实例,防止多个音乐同时播放
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
        releasePlayer();
        releasePlayerListener();
    }
    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
        releasePlayerListener();
    }
    //播放当前索引歌曲
    private void playCurrentSong() {
        if (list != null) {
            releasePlayer(); // 释放之前的播放器实例
            player = new SimpleExoPlayer.Builder(context).build();
            Music currentMusic =list.get(currentSongIndex);
            url=currentMusic.getUrl();
            MediaItem item = MediaItem.fromUri(url);
            player.setMediaItem(item);
            player.prepare();
            //监听器:负责UI的更新
            player.addListener(playerListener);
            name.setText(currentMusic.getName());
            author.setText(currentMusic.getAuthor());
        }
    }
    //播放上一首歌的方法
    private void playPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
        } else {
            // 如果已经是第一首歌，切换到最后一首
            currentSongIndex = list.size() - 1;
        }
        playCurrentSong();
        //切换后立即播放
        player.setPlayWhenReady(true);
    }
    //播放下一首歌的方法
    private void playNextSong() {
        if (currentSongIndex < list.size() - 1) {
            currentSongIndex++;
        } else {
            // 如果已经是最后一首歌，切换到第一首
            currentSongIndex = 0;
        }
        playCurrentSong();
        //切换后立即播放
        player.setPlayWhenReady(true);

    }

    //释放当前播放实例
    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
    //释放当前监听器
    private void releasePlayerListener() {
        if (player != null) {
            player.removeListener(playerListener);
        }
    }

    // 更新播放UI状态的方法(在主线程中更新)
    private void updateUI() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (player.getPlayWhenReady()) {
                    // 如果正在播放，更新为播放状态的UI
                    imageView_tuzi.setImageResource(R.drawable.tuzih);
                } else {
                    // 如果未播放，更新为暂停状态的UI
                    imageView_tuzi.setImageResource(R.drawable.tuzi);
                }
            }
        });
    }

    // 更新播放器进度条和时间的方法
    private void updateProgressBar() {
        Log.d("kk","change");
        long duration = player.getDuration(); // 音乐总时长
        long position = player.getCurrentPosition(); // 当前播放位置

        // 更新进度条和时间
        seekBar.setMax((int) duration / 1000);
        seekBar.setProgress((int) position / 1000);

        begin.setText(formatTime(position));
        end.setText(formatTime(duration));
    }
    // 格式化时间的辅助方法
    private String formatTime(long timeMs) {
        long totalSeconds = timeMs / 1000;
        long minutes = (totalSeconds / 60) % 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    //定时更新进度条
    private void startUpdatingProgressBar() {
        // 创建一个定时任务来更新进度条
        updateProgressRunnable = new Runnable() {
            @Override
            public void run() {
                if (player != null ) {
                    // 每隔一段时间更新一次
                    updateProgressBar();
                    handler.postDelayed(this, 500);
                }
            }
        };
        handler.post(updateProgressRunnable);
    }
    // 停止定时更新进度条
    private void stopUpdatingProgressBar() {
        // 移除定时任务，停止更新进度条
        handler.removeCallbacks(updateProgressRunnable);
    }
    //播放监听器
    private final Player.Listener playerListener = new Player.Listener() {
        @Override
        public void onPlaybackStateChanged(int state) {
            // 播放状态变化时调用
            if (state == Player.STATE_READY) {
                // 播放器准备好时，开始更新进度条
                startUpdatingProgressBar();

            } else {
                // 停止更新进度条
                stopUpdatingProgressBar();
            }
            if (state==Player.STATE_ENDED){
                playNextSong();
            }
            //更新UI
            updateUI();
        }
    };


//    //实现接口回调,选择在此处是因为我们要删除的数据源于此处
//    public void onDelete(int position) {
//        //扩展:窗口弹出询问是否删除
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);//在当前Activity中展示
//        builder.setTitle("DELETE")//设置标题
//                .setMessage("Are you sure?")//设置提示语
//                .setPositiveButton("YES!", new DialogInterface.OnClickListener() {//当确认时,发生点击事件删除&更新
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //删除
//                        //myLike.removeMusic();
//                        //更新
//                        adapter.notifyItemRemoved(position);
//                    }
//                })
//                .setNegativeButton("NO~", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })//当后悔时,点击了什么都不做
//                .show();//展示窗口
//    }
}