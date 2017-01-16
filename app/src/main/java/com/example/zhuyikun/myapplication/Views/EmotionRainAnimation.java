package com.example.zhuyikun.myapplication.Views;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;


import com.example.zhuyikun.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 撒花 用到的知识点： 1、android属性动画 2、Path路径绘制 3、贝塞尔曲线
 */
public class EmotionRainAnimation extends View implements AnimatorUpdateListener {

    public static int[] mHeartArr = new int[]{
            R.drawable.emj_face_01,
            R.drawable.emj_face_02,
            R.drawable.emj_heart_01,
            R.drawable.emj_heart_02,
            R.drawable.emj_heart_03
    };

    /**
     * 动画改变的属性值
     */
    private float phase1 = 0f;
    private float phase2 = 0f;
    private float phase3 = 0f;
    private float phase4 = 0f;

    /**
     * 小球集合
     */
    private List<Emotion> fllowers1 = new ArrayList<Emotion>();
    private List<Emotion> fllowers2 = new ArrayList<Emotion>();
    private List<Emotion> fllowers3 = new ArrayList<Emotion>();
    private List<Emotion> fllowers4 = new ArrayList<Emotion>();

    /**
     * 动画播放的时间
     */
    private int time = 3000;
    /**
     * 动画间隔
     */
    private int delay = 400;

    int[] ylocations = {-100, -75, -50, -25, 0};

    public EmotionRainAnimation(Context context) {
        super(context);
        init(context);
    }

    @SuppressWarnings("deprecation")
    private void init(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = (int) (wm.getDefaultDisplay().getHeight() * 3 / 2f);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        pathMeasure = new PathMeasure();
        range = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10
                , getResources().getDisplayMetrics());

        builderFollower(1, fllowers1);
//        builderFollower(fllowerCount, fllowers2);
//        builderFollower(fllowerCount, fllowers3);
//        builderFollower(firstAndLastFllowerCount, fll owers4);

    }

    /**
     * 宽度
     */
    private int width = 0;
    /**
     * 高度
     */
    private int height = 0;

    /**
     * 曲线高度个数分割
     */
    private int quadCount = 3;
    /**
     * 曲度
     */
    private float intensity = 0.05f;

    /**
     * 第一批个数
     */
    private int fllowerCount = 7;

    /**
     * 最后三个小花
     */
    private int firstAndLastFllowerCount = 3;

    /**
     * 创建花
     */
    private void builderFollower(int count, List<Emotion> fllowers) {

        int max = (int) (width * 3 / 4f);
        int min = (int) (width / 4f);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int s = random.nextInt(max) % (max) + min/8 ;
            Log.d("random",random.nextInt(max)+"");
            Path path = new Path();
            CPoint CPoint = new CPoint(s, ylocations[random.nextInt(4)]);
            List<CPoint> points = builderPath(CPoint);
            drawFllowerPath(path, points);
            Emotion emotion = new Emotion();
            emotion.setPath(path);
            emotion.setImg(getRandomEmotion());
            fllowers.add(emotion);
        }
    }

    private Bitmap getRandomEmotion() {
        int index = new Random().nextInt(mHeartArr.length);
        return BitmapFactory.decodeResource(getResources(), mHeartArr[index]);
    }

    /**
     * 画曲线
     *
     * @param path
     * @param points
     */
    private void drawFllowerPath(Path path, List<CPoint> points) {
        if (points.size() > 1) {
            for (int j = 0; j < points.size(); j++) {

                CPoint point = points.get(j);

                if (j == 0) {
                    CPoint next = points.get(j + 1);
                    point.dx = ((next.x - point.x) * intensity);
                    point.dy = ((next.y - point.y) * intensity);
                } else if (j == points.size() - 1) {
                    CPoint prev = points.get(j - 1);
                    point.dx = ((point.x - prev.x) * intensity);
                    point.dy = ((point.y - prev.y) * intensity);
                } else {
                    CPoint next = points.get(j + 1);
                    CPoint prev = points.get(j - 1);
                    point.dx = ((next.x - prev.x) * intensity);
                    point.dy = ((next.y - prev.y) * intensity);
                }

                // create the cubic-spline path
                if (j == 0) {
                    path.moveTo(point.x, point.y);
                } else {
                    CPoint prev = points.get(j - 1);
//                    path.cubicTo(prev.x + prev.dx, (prev.y + prev.dy), point.x
//                            - point.dx, (point.y - point.dy), point.x, point.y);
                    path.quadTo(prev.x + prev.dx, (prev.y + prev.dy), point.x
                            - point.dx, (point.y - point.dy));
                }
//                Log.e("dx",point.dx+"");
            }
        }
    }

    /**
     * 曲线摇摆的幅度
     */
    private int range;

    /**
     * 画路径
     *
     * @param point
     * @return
     */
    private List<CPoint> builderPath(CPoint point) {
        List<CPoint> points = new ArrayList<CPoint>();
        Random random = new Random();
        for (int i = 0; i < quadCount; i++) {
            if (i == 0) {
                points.add(point);
            } else {
                CPoint tmp = new CPoint(0, 0);
                if (random.nextInt(100) % 2 == 0) {
                    tmp.x = point.x + 30*random.nextInt(range);
                } else {
                    tmp.x = point.x - 30*random.nextInt(range);
                }

                Log.d("pointx",tmp.x+"");
                tmp.y = (int) (height / (float) quadCount * i);
                points.add(tmp);
            }
        }
        return points;
    }

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 测量路径的坐标位置
     */
    private PathMeasure pathMeasure = null;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawFllower(canvas, fllowers1);
        drawFllower(canvas, fllowers2);
        drawFllower(canvas, fllowers3);
        drawFllower(canvas, fllowers4);

    }

    /**
     * 高度往上偏移量,把开始点移出屏幕顶部
     */
    private float dy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            40, getResources().getDisplayMetrics());

    /**
     * @param canvas
     * @param fllowers
     */
    private void drawFllower(Canvas canvas, List<Emotion> fllowers) {
        for (Emotion fllower : fllowers) {
            float[] pos = new float[2];
            // canvas.drawPath(fllower.getPath(),mPaint);
            pathMeasure.setPath(fllower.getPath(), false);
            pathMeasure.getPosTan(height * fllower.getValue(), pos, null);
            // canvas.drawCircle(pos[0], pos[1], 10, mPaint);
            canvas.drawBitmap(fllower.getImg(), pos[0], pos[1] - dy, null);
        }
    }

    ObjectAnimator mAnimator1;
    ObjectAnimator mAnimator2;
    ObjectAnimator mAnimator3;
    ObjectAnimator mAnimator4;

    public void startAnimation() {
        if (mAnimator1 != null && mAnimator1.isRunning()) {
            mAnimator1.cancel();
        }
        mAnimator1 = ObjectAnimator.ofFloat(this, "phase1", 0f, 1f);
        mAnimator1.setDuration(time);
        mAnimator1.addUpdateListener(this);
        mAnimator1.start();
        mAnimator1.setInterpolator(new AccelerateInterpolator(1f));

        if (mAnimator2 != null && mAnimator2.isRunning()) {
            mAnimator2.cancel();
        }
        mAnimator2 = ObjectAnimator.ofFloat(this, "phase2", 0f, 1f);
        mAnimator2.setDuration(time);
        mAnimator2.addUpdateListener(this);
        mAnimator2.start();
        mAnimator2.setInterpolator(new AccelerateInterpolator(1f));
        mAnimator2.setStartDelay(delay);

        if (mAnimator3 != null && mAnimator3.isRunning()) {
            mAnimator3.cancel();
        }
        mAnimator3 = ObjectAnimator.ofFloat(this, "phase3", 0f, 1f);
        mAnimator3.setDuration(time);
        mAnimator3.addUpdateListener(this);
        mAnimator3.start();
        mAnimator3.setInterpolator(new AccelerateInterpolator(1f));
        mAnimator3.setStartDelay(delay * 2);

        if (mAnimator4 != null && mAnimator4.isRunning()) {
            mAnimator4.cancel();
        }
        mAnimator4 = ObjectAnimator.ofFloat(this, "phase4", 0f, 1f);
        mAnimator4.setDuration(time);
        mAnimator4.addUpdateListener(this);
        mAnimator4.start();
        mAnimator4.setInterpolator(new AccelerateInterpolator(1f));
        mAnimator4.setStartDelay(delay * 3);
    }

    /**
     * f
     * 跟新小球的位置
     *
     * @param value
     * @param fllowers
     */
    private void updateValue(float value, List<Emotion> fllowers) {
        for (Emotion fllower : fllowers) {
            fllower.setValue(value);
        }
    }

    /**
     * 动画改变回调
     */
    @Override
    public void onAnimationUpdate(ValueAnimator arg0) {

        updateValue(getPhase1(), fllowers1);
        updateValue(getPhase2(), fllowers2);
        updateValue(getPhase3(), fllowers3);
        updateValue(getPhase4(), fllowers4);
        Log.i(tag, getPhase1() + "");
        invalidate();
    }

    public float getPhase1() {
        return phase1;
    }

    public void setPhase1(float phase1) {
        this.phase1 = phase1;
    }

    public float getPhase2() {
        return phase2;
    }

    public void setPhase2(float phase2) {
        this.phase2 = phase2;
    }

    public float getPhase3() {
        return phase3;
    }

    public void setPhase3(float phase3) {
        this.phase3 = phase3;
    }

    public float getPhase4() {
        return phase4;
    }

    public void setPhase4(float phase4) {
        this.phase4 = phase4;
    }

    private String tag = this.getClass().getSimpleName();

    private class CPoint {

        public float x = 0f;
        public float y = 0f;

        /**
         * x-axis distance
         */
        public float dx = 0f;

        /**
         * y-axis distance
         */
        public float dy = 0f;

        public CPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

}



