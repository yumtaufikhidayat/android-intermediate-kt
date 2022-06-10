package com.taufik.androidintemediate.advancedui.canvas

import android.graphics.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.databinding.ActivityCanvasBinding

class CanvasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCanvasBinding

    private val number1000 = 1000
    private val mBitmap = Bitmap.createBitmap(number1000, number1000, Bitmap.Config.ARGB_8888)
    private val mCanvas = Canvas(mBitmap)
    private val mPaint = Paint()

    private val halfOfWidth = (mBitmap.width / 2).toFloat()
    private val halfOfHeight = (mBitmap.height / 2).toFloat()

    private val left = 150F
    private val top = 250F
    private val right = mBitmap.width - left
    private val bottom = mBitmap.height.toFloat() - 50F

    private val message = "Apakah kamu suka bermain?"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCanvasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBitmap()
        showText()
        setActionListener()
    }

    private fun setBitmap() = with(binding) {
        imageView.setImageBitmap(mBitmap)
    }

    private fun setActionListener() = with(binding) {
        btnLike.setOnClickListener {
            showFace()
            showMouth(true)
            showEyes()
        }

        btnDislike.setOnClickListener {
            showFace()
            showMouth(false)
            showEyes()
        }
    }

    private fun showFace() {
        val face = RectF(left, top, right, bottom)

        mPaint.color = ResourcesCompat.getColor(resources, R.color.yellow_left_skin, null)
        mCanvas.drawArc(face, 90F, 180F, false, mPaint)

        mPaint.color = ResourcesCompat.getColor(resources, R.color.yellow_right_skin, null)
        mCanvas.drawArc(face, 270F, 180F, false, mPaint)
    }

    private fun showEyes() {
        mPaint.color = ResourcesCompat.getColor(resources, R.color.black, null)
        mCanvas.drawCircle(halfOfWidth - 100F, halfOfHeight - 10F, 50F, mPaint)
        mCanvas.drawCircle(halfOfWidth + 100F, halfOfHeight - 10F, 50F, mPaint)

        mPaint.color = ResourcesCompat.getColor(resources, R.color.white, null)
        mCanvas.drawCircle(halfOfWidth - 120F, halfOfHeight - 20F, 15F, mPaint)
        mCanvas.drawCircle(halfOfWidth + 80F, halfOfHeight - 20F, 15F, mPaint)
    }

    private fun showMouth(isHappy: Boolean) {
        when (isHappy) {
            true -> {
                mPaint.color = ResourcesCompat.getColor(resources, R.color.black, null)
                val lip = RectF(
                    halfOfWidth - 200F,
                    halfOfHeight - 100F,
                    halfOfWidth + 200F,
                    halfOfHeight + 400F
                )
                mCanvas.drawArc(lip, 25F, 130F, false, mPaint)

                mPaint.color = ResourcesCompat.getColor(resources, R.color.white, null)
                val mouth = RectF(
                    halfOfWidth - 180F,
                    halfOfHeight,
                    halfOfWidth + 180F,
                    halfOfHeight + 380F
                )
                mCanvas.drawArc(mouth, 25F, 130F, false, mPaint)
            }

            false -> {
                mPaint.color = ResourcesCompat.getColor(resources, R.color.black, null)
                val lip = RectF(
                    halfOfWidth - 200F,
                    halfOfHeight + 250F,
                    halfOfWidth + 200F,
                    halfOfHeight + 350F
                )
                mCanvas.drawArc(lip, 0F, -180F, false, mPaint)

                mPaint.color = ResourcesCompat.getColor(resources, R.color.white, null)
                val mouth = RectF(
                    halfOfWidth - 180F,
                    halfOfHeight + 260F,
                    halfOfWidth + 180F,
                    halfOfHeight + 330F
                )
                mCanvas.drawArc(mouth, 0F, -180F, false, mPaint)
            }
        }
    }

    private fun showText() {
        val mPaintText = Paint(Paint.FAKE_BOLD_TEXT_FLAG).apply {
            textSize = 50F
            color = ResourcesCompat.getColor(resources, R.color.black, null)
        }

        val mBounds = Rect()
        mPaintText.getTextBounds(message, 0, message.length, mBounds)

        val x = halfOfWidth - mBounds.centerX()
        val y = 50F
        mCanvas.drawText(message, x, y, mPaintText)
    }
}