package com.tj.skyone.ui.home.view

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.*
import com.tj.skyone.R
import com.tj.skyone.service.MyService
import com.tj.skyone.ui.login.LoginActivity
import com.tj.skyone.utils.CommonUtils
import com.tj.skyone.utils.TcpClient
import kotlinx.android.synthetic.main.activity_config.*

class ConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)


        CommonUtils.requestPermissions(this)


        if (!ObjectUtils.isEmpty(SPUtils.getInstance().getString("ip"))) {

            edt_ip.setText(SPUtils.getInstance().getString("ip"))
        }

        if (!ObjectUtils.isEmpty(SPUtils.getInstance().getString("port"))) {

            edt_port.setText(SPUtils.getInstance().getString("port"))
        }
        if (!ObjectUtils.isEmpty(SPUtils.getInstance().getString("path"))) {

            edt_path.setText(SPUtils.getInstance().getString("path"))
        }


        text_select.setOnClickListener {

//            val intent = Intent()
//            intent.type = "video/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent, 1);

            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            )

            startActivityForResult(intent, 1);


        }




        btn_submit.setOnClickListener {


            if (ObjectUtils.isEmpty(edt_ip.text.toString())) {

                ToastUtils.showLong("请输入服务器的ip地址")


                return@setOnClickListener

            }

            if (!RegexUtils.isIP(edt_ip.text.toString())) {

                ToastUtils.showLong("请输入正确ip地址")

                return@setOnClickListener

            }

            if (ObjectUtils.isEmpty(edt_port.text.toString())) {

                ToastUtils.showLong("请输入服务器端的端口")

                return@setOnClickListener

            }


            SPUtils.getInstance().put("ip", edt_ip.text.toString())
            SPUtils.getInstance().put("port", edt_port.text.toString())

            if (!StringUtils.isEmpty(edt_path.text.toString())) {

                SPUtils.getInstance().put("path", edt_path.text.toString())

            }else{

                SPUtils.getInstance().put("path", "")

            }


            if ( ActivityUtils.isActivityExistsInStack(LoginActivity::class.java)){

                ActivityUtils.finishActivity(LoginActivity::class.java)

            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            if (TcpClient.getInstance().isConnect){

                TcpClient.getInstance().disconnect()

            }

            if (!ServiceUtils.isServiceRunning(MyService::class.java)){

                ServiceUtils.startService(MyService::class.java)

            }

            finish()

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                val uri: Uri? = data!!.data
                val cursor: Cursor? = contentResolver.query(
                    uri!!, null, null,
                    null, null
                )


                if (cursor?.moveToFirst()!!) {

//                    val videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
//                    val title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                    val videoPath =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
//                    val duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
//                    val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
//                    val imagePath =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
//                    val imageId =cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))


                    edt_path.setText(videoPath.toString())

                }


                cursor?.close()

            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


}
