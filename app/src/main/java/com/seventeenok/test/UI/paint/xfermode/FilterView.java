package com.seventeenok.test.UI.paint.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.seventeenok.test.R;


public class FilterView extends View {

    Paint paint;

    Bitmap bitmap;

    private int progress;

    public FilterView(Context context) {
        super(context);
        
        init();
    }

    private void init() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.xyjy2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);


        RectF rectF = new RectF(200,100,bitmap.getWidth()+200,bitmap.getHeight());
        paint.reset();
        paint.setColor(Color.RED);
        //canvas.drawRect(rectF,paint);

        //canvas.translate(600,0);
        /**
         * Create a blur maskfilter.
         *
         * @param radius 阴影的半径
         * @param style  NORMOL -- 整个图像都被模糊掉
         *               SOLID -- 图像边界外产生一层与Paint颜色一致阴影效果，不影响图像的本身
         *               OUTER -- 图像边界外产生一层阴影，并且将图像变成透明效果
         *               INNER -- 在图像内部边沿产生模糊效果
         * @return
         */
        //paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));



        /**
         * Create an emboss maskfilter
         *
         * @param direction  指定光源的位置，长度为xxx的数组标量[x,y,z]
         * @param ambient    环境光的因子 （0~1），越接近0，环境光越暗
         * @param specular   镜面反射系数 越接近0，镜面反射越强
         * @param blurRadius 模糊半径 值越大，模糊效果越明显
         */
        //paint.setMaskFilter(new EmbossMaskFilter(new float[]{1,1,1},0.2f,60,80));

        //canvas.drawRect(rectF,paint);
        canvas.drawBitmap(bitmap,null, rectF,paint);
        // 平移运算---加法
        /*ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                1, 0,0,0,0,
                0,1,0,0,100,
                0,0,1,0,0,
                0,0,0,1,0,
        });*/

        // 反相效果 -- 底片效果
       /* ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                -1, 0,0,0,255,
                0,-1,0,0,255,
                0,0,-1,0,255,
                0,0,0,1,0,
        });*/
        // 缩放运算---乘法 -- 颜色增强
        /*ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                1.2f, 0,0,0,0,
                0,1.2f,0,0,0,
                0,0,1.2f,0,0,
                0,0,0,1.2f,0,
        });*/

        // 黑白照片
        //是将我们的三通道变为单通道的灰度模式
        // 去色原理：只要把R G B 三通道的色彩信息设置成一样，那么图像就会变成灰色，
        // 同时为了保证图像亮度不变，同一个通道里的R+G+B =1
        //
        /*ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                0.213f, 0.715f,0.072f,0,0,
                0.213f, 0.715f,0.072f,0,0,
                0.213f, 0.715f,0.072f,0,0,
                0,0,0,1,0,
        });*/

        

        // 发色效果---（比如红色和绿色交换）
        ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                1,0,0,0,0,
                0, 0,1,0,0,
                0,1,0,0,0,
                0,0,0,0.5F,0,
        });
        // 复古效果
        /*ColorMatrix colorMartrix = new ColorMatrix(new float[]{
                1/2f,1/2f,1/2f,0,0,
                1/3f, 1/3f,1/3f,0,0,
                1/4f,1/4f,1/4f,0,0,
                0,0,0,1,0,
        });*/
        // 颜色通道过滤
        //两个矩阵
        //本身颜色矩阵 A
        //过滤矩阵  c
        //a*c=out color
//        ColorMatrix colorMartrix = new ColorMatrix(new float[]{
//                1.3F,0,0,0,0,
//                0,1.3F,0,0,0,
//                0,0,1.3F,0,0,
//                0,0,0,1,0,
//        });

        RectF rectF2 = new RectF(200,100 + bitmap.getHeight(),bitmap.getWidth()+200,bitmap.getHeight() * 2);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMartrix));
        canvas.drawBitmap(bitmap,null, rectF2,paint);
    }


}
