package com.rdev.tryp.blocks.reward_profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.airbnb.lottie.LottieAnimationView
import com.rdev.tryp.ContentActivity
import com.rdev.tryp.R
import com.rdev.tryp.blocks.helper.BaseFragment
import com.rdev.tryp.firebaseDatabase.model.Client
import com.rdev.tryp.model.RealmCallback
import com.rdev.tryp.model.RealmUtils
import com.rdev.tryp.storageDatabase.OnFileLoadCallback
import com.rdev.tryp.storageDatabase.utils.StorageUtils
import com.squareup.picasso.Picasso

import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout


class RewardProfileFragment : BaseFragment(), View.OnClickListener {

    companion object {
        const val PICK_IMAGE = 958
    }
    
    private var mainPhoto: ImageView? = null
    private var btnSettings: ImageView? = null
    private var btnSave: Button? = null
    private var btnCancel: ImageButton? = null
    private var btnChangePhoto: View? = null
    private var editLayout: CardView? = null
    private var mainLayout: CardView? = null
    private var photoLayout: ConstraintLayout? = null
    private var mainLayout2: LinearLayout? = null
    private var loadScreen: ConstraintLayout? = null
    private var pbLoader: LottieAnimationView? = null
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null

    private var tvName: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_reward_profile, container, false)

        val fab = root.findViewById<ImageView>(R.id.back_btn)
        fab.setOnClickListener(this)
        val cardView = root.findViewById<CardView>(R.id.top_card_view)
        cardView.setBackgroundResource(R.drawable.card_view_bg)
        loadScreen = root.findViewById(R.id.load_screen)
        pbLoader = root.findViewById(R.id.pb_loader)
        editLayout = root.findViewById(R.id.edit_layout)
        mainLayout = root.findViewById(R.id.main_layout)
        mainLayout2 = root.findViewById(R.id.other_main_layout)
        photoLayout = root.findViewById(R.id.layout_photo)
        tvName = root.findViewById(R.id.tvName)
        etFirstName = root.findViewById(R.id.etFirstName)
        etLastName = root.findViewById(R.id.etLastName)

        val rewardPoints = root.findViewById<CardView>(R.id.rewards_points_card_view)
        rewardPoints.setOnClickListener(this)
        val credits = root.findViewById<CardView>(R.id.credits_card_view)
        credits.setOnClickListener(this)

        btnSettings = root.findViewById(R.id.settings_img)
        btnSettings?.setOnClickListener(this)

        btnChangePhoto = root.findViewById(R.id.btnChangePhoto)
        btnChangePhoto?.setOnClickListener(this)

        btnSave = root.findViewById(R.id.btnSave)
        btnSave?.setOnClickListener(this)
        btnCancel = root.findViewById(R.id.btnCancel)
        btnCancel?.setOnClickListener(this)

        initUI(root)

        return root
    }

    private fun initUI(v: View) {
        val user = RealmUtils(v.context, null).getCurrentUser()
        val img = user?.image
        if (img != null && img != "null") {
            mainPhoto = v.findViewById(R.id.main_img)
            Picasso.get().load(img).into(mainPhoto)
        }

        val tempName = "${user?.firstName} ${user?.lastName}"
        tvName?.text = tempName

        etFirstName?.setText(user?.firstName)
        etLastName?.setText(user?.lastName)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_btn -> (activity as ContentActivity).goHome()
            R.id.credits_card_view -> Toast.makeText(context, "clicked credits", Toast.LENGTH_SHORT).show()
            R.id.rewards_points_card_view -> (activity as ContentActivity).startFragment(ContentActivity.TYPE_REWARD_POINTS)
            R.id.settings_img -> initEditor(true)
            R.id.btnCancel -> initEditor(false)
            R.id.btnChangePhoto -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
            }
        }
    }

    private fun initEditor(isShow: Boolean) {
        val iconAddPhoto = view?.findViewById<ImageView>(R.id.icon_add_photo)

        if (isShow) {
            btnSettings?.visibility = View.INVISIBLE
            mainLayout?.visibility = View.INVISIBLE
            mainLayout2?.visibility = View.INVISIBLE
            btnChangePhoto?.visibility = View.VISIBLE
            iconAddPhoto?.visibility = View.VISIBLE
            btnSave?.visibility = View.VISIBLE
            editLayout?.visibility = View.VISIBLE
            btnCancel?.visibility = View.VISIBLE
            mainPhoto?.scaleX = 1.1f
            mainPhoto?.scaleY = 1.1f
            btnSave?.setOnClickListener { pushOnlyName() }
            photoLayout?.layoutParams?.height = resources.getDimensionPixelOffset(R.dimen.height200)
            photoLayout?.requestLayout()
        } else {
            btnSettings?.visibility = View.VISIBLE
            mainLayout?.visibility = View.VISIBLE
            mainLayout2?.visibility = View.VISIBLE
            btnChangePhoto?.visibility = View.INVISIBLE
            iconAddPhoto?.visibility = View.INVISIBLE
            btnSave?.visibility = View.INVISIBLE
            editLayout?.visibility = View.INVISIBLE
            btnCancel?.visibility = View.INVISIBLE
            mainPhoto?.scaleX = 1.0f
            mainPhoto?.scaleY = 1.0f
            photoLayout?.layoutParams?.height = resources.getDimensionPixelOffset(R.dimen.height354)
            photoLayout?.requestLayout()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            try {
                val uriImage = data?.data
                val inputStream = activity?.contentResolver?.openInputStream(uriImage)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArray = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
                val b = byteArray.toByteArray()
                mainPhoto?.setImageBitmap(bitmap)

                btnSave?.setOnClickListener {
                    loadScreen?.visibility = View.VISIBLE
                    pbLoader?.playAnimation()

                    StorageUtils().pushPhoto(b, object : OnFileLoadCallback {
                        override fun dataUpdated(url: String) {
                            val client = Client()
                            client.id = RealmUtils(null, null).getCurrentUser()?.userId?.toString()
                            client.photo = url
                            client.first_name = etFirstName?.text.toString()
                            client.last_name = etLastName?.text.toString()
                            RealmUtils(context, object : RealmCallback {
                                override fun dataUpdated() {
                                    RealmUtils(null, null).getCurrentUser()?.let { user ->
                                        (activity as ContentActivity).database.updateUser(user, null, null)
                                    }
                                    initEditor(false)
                                    view?.let { v -> initUI(v) }

                                    (activity as ContentActivity).updateAvatar()

                                    loadScreen?.visibility = View.INVISIBLE
                                    pbLoader?.clearAnimation()
                                }

                                override fun error() {
                                    loadScreen?.visibility = View.INVISIBLE
                                    pbLoader?.clearAnimation()
                                }
                            }).updateUser(client)
                        }

                        override fun error(e: Exception) {
                            loadScreen?.visibility = View.INVISIBLE
                            pbLoader?.clearAnimation()
                        }
                    })
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(view?.context, "Image was not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pushOnlyName() {
        val client = Client()
        client.id = RealmUtils(null, null).getCurrentUser()?.userId?.toString()
        client.first_name = etFirstName?.text.toString()
        client.last_name = etLastName?.text.toString()
        RealmUtils(context, object : RealmCallback {
            override fun dataUpdated() {
                RealmUtils(null, null).getCurrentUser()?.let { user ->
                    (activity as ContentActivity).database.updateUser(user, null, null)
                }
                
                initEditor(false)
                view?.let { v -> initUI(v) }

                (activity as ContentActivity).updateAvatar()

                loadScreen?.visibility = View.INVISIBLE
                pbLoader?.clearAnimation()
            }

            override fun error() {
                loadScreen?.visibility = View.INVISIBLE
                pbLoader?.clearAnimation()
            }
        }).updateUser(client)
    }

}