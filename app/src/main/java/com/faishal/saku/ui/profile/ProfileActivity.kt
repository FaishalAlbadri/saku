package com.faishal.saku.ui.profile

import android.app.ProgressDialog
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.faishal.saku.R
import com.faishal.saku.api.Server
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.user.UserItem
import com.faishal.saku.databinding.ActivityProfileBinding
import com.faishal.saku.di.LoginRepositoryInject
import com.faishal.saku.presenter.login.LoginContract
import com.faishal.saku.presenter.login.LoginPresenter
import com.faishal.saku.util.SessionManager

class ProfileActivity : BaseActivity(), LoginContract.loginView {


    private lateinit var sessionManager: SessionManager
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var pd: ProgressDialog

    private var _binding: ActivityProfileBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }

    private fun setView() {
        sessionManager = SessionManager(this)

        loginPresenter = LoginPresenter(LoginRepositoryInject.provideTo(this))
        loginPresenter.onAttachView(this)
        loginPresenter.profile(sessionManager.getIdUser()!!)

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()

        binding.apply {
            btnLogout.setOnClickListener {
                AlertDialog.Builder(this@ProfileActivity, R.style.AlertDialogTheme)
                    .setTitle("Logout Akun")
                    .setMessage("Yakinkah kamu ingin keluar dari akunmu?")
                    .setPositiveButton("Logout") { dialogInterface, i ->
                        sessionManager.logout()
                    }
                    .setNegativeButton("Batal") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                    .show()
            }

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun onSuccessProfile(userItemList: List<UserItem>, msg: String) {
        val userItem = userItemList.get(0)
        pd.cancel()
        binding.apply {
            txtNameValue.setText(userItem.userName)
            txtEmailValue.setText(userItem.userEmail)
            txtPhoneValue.setText(userItem.userPhone)
            txtCreateValue.setText(userItem.userCreate)
            imgProfile.loadCircularImage(Server.BASE_URL_IMG_USER + userItem.userImage, 8F)

        }
    }

    override fun onErrorProfile(msg: String) {
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        if (msg.equals("Gagal")) {
            sessionManager.logout()
        } else {
            pd.show()
            loginPresenter.profile(sessionManager.getIdUser()!!)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun <T> ImageView.loadCircularImage(
        model: T,
        borderSize: Float = 0F,
        borderColor: Int = Color.WHITE
    ) {
        Glide.with(context)
            .asBitmap()
            .thumbnail(0.1f)
            .load(model)
            .error(R.drawable.faishal)
            .apply(RequestOptions.circleCropTransform())
            .into(object : BitmapImageViewTarget(this) {
                override fun setResource(resource: Bitmap?) {
                    setImageDrawable(
                        resource?.run {
                            RoundedBitmapDrawableFactory.create(
                                resources,
                                if (borderSize > 0) {
                                    createBitmapWithBorder(borderSize, borderColor)
                                } else {
                                    this
                                }
                            ).apply {
                                isCircular = true
                            }
                        }
                    )
                }
            })
    }

    private fun Bitmap.createBitmapWithBorder(borderSize: Float, borderColor: Int): Bitmap {
        val borderOffset = (borderSize * 2).toInt()
        val halfWidth = width / 2
        val halfHeight = height / 2
        val circleRadius = Math.min(halfWidth, halfHeight).toFloat()
        val newBitmap = Bitmap.createBitmap(
            width + borderOffset,
            height + borderOffset,
            Bitmap.Config.ARGB_8888
        )

        val centerX = halfWidth + borderSize
        val centerY = halfHeight + borderSize

        val paint = Paint()
        val canvas = Canvas(newBitmap).apply {
            drawARGB(0, 0, 0, 0)
        }

        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, circleRadius, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(this, borderSize, borderSize, paint)

        paint.xfermode = null
        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        paint.strokeWidth = borderSize
        canvas.drawCircle(centerX, centerY, circleRadius, paint)
        return newBitmap
    }

    override fun onSuccessLogin(idUser: String, msg: String) {}

    override fun onErrorLogin(msg: String) {}

}