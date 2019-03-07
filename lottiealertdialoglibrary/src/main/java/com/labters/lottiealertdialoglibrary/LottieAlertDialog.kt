package com.labters.lottiealertdialoglibrary

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class LottieAlertDialog : AlertDialog
{
    private var mContext : Context
    private var mType:Int?=null
    private var mTitle:String? = null
    private var mDescription:String? = null
    private var mPositiveText:String? = null
    private var mNegativeText:String? = null
    private var mNoneText:String? = null
    private var mPositiveListener : ClickListener? = null
    private var mNegativeListener : ClickListener? = null
    private var mNoneListener : ClickListener? = null

    private lateinit var lAnimation : LottieAnimationView
    private lateinit var tvTitle : TextView
    private lateinit var tvDescription : TextView
    private lateinit var btnPositive : Button
    private lateinit var btnNegative : Button
    private lateinit var btnNone : Button
    private lateinit var animationFadeIn : Animation
    private lateinit var animationFadeOut: Animation

    private constructor(
        context: Context,
        type: Int?,
        title: String?,
        description: String?,
        positiveText: String?,
        negativeText: String?,
        noneText: String?,
        positiveListener: ClickListener?,
        negativeListener: ClickListener?,
        noneListener: ClickListener?
    ) : super(context) {
        this.mContext = context
        this.mType = type
        this.mTitle = title
        this.mDescription = description
        this.mPositiveText = positiveText
        this.mNegativeText = negativeText
        this.mNoneText = noneText
        this.mPositiveListener = positiveListener
        this.mNegativeListener = negativeListener
        this.mNoneListener = noneListener
        animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        animationFadeIn.duration = 50
        animationFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        animationFadeOut.duration = 50
    }


    data class Builder(
        private val context: Context? = null,
        private val type:Int? = null
        )
    {
        private var title:String? = null
        private var description:String? = null
        private var positiveText:String? = null
        private var negativeText:String? = null
        private var noneText:String? = null
        private var positiveListener:ClickListener? = null
        private var negativeListener:ClickListener? = null
        private var noneListener:ClickListener? = null
        fun setTitle(title:String?) : Builder = apply { this.title = title ; return@apply}
        fun setDescription(description:String?) : Builder= apply { this.description = description ; return@apply}
        fun setPositiveText(positiveText:String?) : Builder= apply { this.positiveText = positiveText ; return@apply}
        fun setNegativeText(negativeText:String?) : Builder= apply { this.negativeText = negativeText ; return@apply}
        fun setNoneText(noneText:String?) : Builder= apply { this.noneText = noneText ; return@apply}
        fun setPositiveListener(positiveListener:ClickListener?) : Builder= apply { this.positiveListener = positiveListener ; return@apply}
        fun setNegativeListener(negativeListener:ClickListener?) : Builder= apply { this.negativeListener = negativeListener ; return@apply}
        fun setNoneListener(noneListener:ClickListener?): Builder = apply { this.noneListener = noneListener ; return@apply}
        fun build() = LottieAlertDialog(context!!,type,title, description, positiveText, negativeText,noneText,positiveListener,negativeListener,noneListener)

        fun getContext() : Context? {return context}
        fun getType() : Int? { return  type}
        fun getTitle() : String? { return  title}
        fun getDescription() : String? { return  description}
        fun getPositiveText() : String? { return  positiveText}
        fun getNegativeText() : String? { return  negativeText}
        fun getNoneText() : String? { return  noneText}
        fun getPositiveListener() : ClickListener? { return  positiveListener}
        fun getNegativeListener() : ClickListener? { return  negativeListener}
        fun getNoneListener() : ClickListener? { return  noneListener}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_alert_dialog)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        findView()
    }

    private fun findView()
    {
        lAnimation=findViewById(R.id.lAnimation)
        tvTitle=findViewById(R.id.tvTitle)
        tvDescription=findViewById(R.id.tvDescription)
        btnPositive=findViewById(R.id.btnPositive)
        btnNegative=findViewById(R.id.btnNegative)
        btnNone=findViewById(R.id.btnNone)
        setView()
    }

    private fun setView()
    {
        lAnimation.startAnimation(animationFadeIn)
        if (mTitle!=null)
        {
            tvTitle.setText(mTitle)
            tvTitle.visibility=View.VISIBLE
        }
        else
        {
            tvTitle.visibility=View.GONE
        }
        if (mDescription!=null)
        {
            tvDescription.setText(mDescription)
            tvDescription.visibility=View.VISIBLE
        }
        else
        {
            tvDescription.visibility=View.GONE
        }
        if (mPositiveText!=null)
        {
            btnPositive.setText(mPositiveText)
            btnPositive.visibility=View.VISIBLE
            btnPositive.setOnClickListener(View.OnClickListener { mPositiveListener?.onClick(LottieAlertDialog@this) })
        }
        else
        {
            btnPositive.visibility=View.GONE
        }
        if (mNegativeText!=null)
        {
            btnNegative.setText(mNegativeText)
            btnNegative.visibility=View.VISIBLE
            btnNegative.setOnClickListener(View.OnClickListener { mNegativeListener?.onClick(LottieAlertDialog@this) })
        }
        else
        {
            btnNegative.visibility=View.GONE
        }
        if (mNoneText!=null)
        {
            btnNone.setText(mNoneText)
            btnNone.visibility=View.VISIBLE
            btnNone.setOnClickListener(View.OnClickListener { mNoneListener?.onClick(LottieAlertDialog@this) })
        }
        else
        {
            btnNone.visibility=View.GONE
        }
        if (mType == DialogTypes.TYPE_LOADING)
        {
            lAnimation.setAnimation("loading.json")
            lAnimation.repeatCount=LottieDrawable.INFINITE
        }
        else if (mType == DialogTypes.TYPE_SUCCESS)
        {
            lAnimation.setAnimation("success.json")
            lAnimation.repeatCount=0
        }
        else if (mType == DialogTypes.TYPE_ERROR)
        {
            lAnimation.setAnimation("error.json")
            lAnimation.repeatCount=0
        }
        else if (mType == DialogTypes.TYPE_WARNING)
        {
            lAnimation.setAnimation("warning.json")
            lAnimation.repeatCount=0
        }
        else if (mType == DialogTypes.TYPE_QUESTION)
        {
            lAnimation.setAnimation("question.json")
            lAnimation.repeatCount=LottieDrawable.INFINITE
        }
        lAnimation.playAnimation()
    }

    fun changeVariables(builder : Builder)
    {
        mContext= builder.getContext()!!
        mType=builder.getType()
        mTitle=builder.getTitle()
        mDescription=builder.getDescription()
        mPositiveText=builder.getPositiveText()
        mNegativeText=builder.getNegativeText()
        mNoneText=builder.getNoneText()
        mPositiveListener=builder.getPositiveListener()
        mNegativeListener=builder.getNegativeListener()
        mNoneListener=builder.getNoneListener()
        lAnimation.startAnimation(animationFadeOut)
        Handler().postDelayed(Runnable { setView() },50)
    }
}